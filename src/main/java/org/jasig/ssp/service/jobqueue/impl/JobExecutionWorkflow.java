/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.jobqueue.impl;

import com.google.common.collect.Lists;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ScheduledTaskWrapperService;
import org.jasig.ssp.service.external.BatchedTask;
import org.jasig.ssp.service.jobqueue.JobExecutionResult;
import org.jasig.ssp.service.jobqueue.JobExecutionStatus;
import org.jasig.ssp.service.jobqueue.JobExecutor;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.jobqueue.JobWorkflowStatusDescription;
import org.jasig.ssp.util.CallableExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Adapts between the overal {@link Job} workflow and the actual performing of its work while the workflow is in the
 * {@code EXECUTING} state.
 */
public class JobExecutionWorkflow implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutionWorkflow.class);
	private static final String NO_SUCH_EXECUTION_COMPONENT_MSG = "No job execution component registered under name {0}";
	private static final String JOB_EXECUTION_SYSTEM_ERROR_MSG = "Job execution exited abnormally {0}";
	public static final String JOB_EXECUTION_TASK_NAME = "job-execution";

	private final UUID jobId;
	private final ScheduledTaskWrapperService taskHelper;
	private final JobService jobService;

	public JobExecutionWorkflow(UUID jobId, ScheduledTaskWrapperService taskHelper, JobService jobService) {
		if ( jobId == null ) {
			throw new IllegalArgumentException("Must specify a job ID");
		}
		if ( taskHelper == null ) {
			throw new IllegalArgumentException("Must specify a ScheduledTaskWrapperServiceImpl");
		}
		if ( jobService == null ) {
			throw new IllegalArgumentException("Must specify a JobService");
		}
		this.jobId = jobId;
		this.taskHelper = taskHelper;
		this.jobService = jobService;
	}

	@Override
	public void run() {
		final Job job = markExecuting();
		if ( job == null ) {
			// problem already logged in markExecuting
			return;
		}

		JobExecutionResult<JobWorkflowStatusDescription> workResult = null;
		try {
			workResult = doWork(job);
		} catch ( Exception e ) {
			// should not happen
			LOGGER.error("Exception escaped scheduled job {} 'doWork' loop. This is a programmer error.", jobId, e);
			final String msg = MessageFormat.format(JOB_EXECUTION_SYSTEM_ERROR_MSG, jobId);
			workResult = new JobExecutionResult<JobWorkflowStatusDescription>(JobExecutionStatus.ERROR,
					new JobWorkflowStatusDescription(null, Lists.newArrayList(msg),null));
		}

		markTerminated(workResult);
	}

	protected JobExecutionResult<JobWorkflowStatusDescription> doWork(final Job job) {
		final String executionComponentName = job.getExecutionComponentName();
		final JobExecutor jobExecutor = jobService.findRegisteredJobExecutor(executionComponentName);
		if ( jobExecutor == null ) {
			final String msg = MessageFormat.format(NO_SUCH_EXECUTION_COMPONENT_MSG, executionComponentName);
			return new JobExecutionResult<JobWorkflowStatusDescription>(JobExecutionStatus.ERROR,
					new JobWorkflowStatusDescription(null, Lists.newArrayList(msg),null));
		}

		final AtomicReference<JobExecutionResult<JobWorkflowStatusDescription>> resultHolder =
			new AtomicReference<JobExecutionResult<JobWorkflowStatusDescription>>();

		taskHelper.execBatchedTaskWithName(JOB_EXECUTION_TASK_NAME, new BatchedTask<JobExecutionResult<JobWorkflowStatusDescription>>() {

			// 'taskHelper' will call back to this method after setting up a logging MDC context and optionally
			// recording task start in the db (we disable this with the 'false' param at the end of the
			// execBatchedTaskWithName() call). The callback includes this 'CallableExecutor' which can then
			// be called as many times as we need. Each time we call it, a new security and db context will be
			// set up. That's how we get per-batch Hibernate sessions. This is also how we ensure the "real work"
			// of a job is performed by user who originally submitted the job request.
			@Override
			public void exec(CallableExecutor<JobExecutionResult<JobWorkflowStatusDescription>> batchExecutor) {
				// TODO store workflow status on each iteration of the loop, e.g. to represent incremental progress
				// and/or accumulate warnings. See similar comments in AbstractJobExecutor.execute()
				while (true) {
					try {
						final JobExecutionResult<JobWorkflowStatusDescription> result = batchExecutor.exec(new Callable<JobExecutionResult<JobWorkflowStatusDescription>>() {
							@Override
							public JobExecutionResult<JobWorkflowStatusDescription> call() throws Exception {
								return jobExecutor.execute(job.getId());
							}
						});
						if ( result.getStatus() != JobExecutionStatus.PARTIAL && result.getStatus() != JobExecutionStatus.FAILED_PARTIAL ) {
							resultHolder.set(result);
							break;
						} else {
							LOGGER.info("Continuing incremental execution of job ", job.getId());
							continue;
						}
					} catch ( RuntimeException e ) {
						throw e;
					} catch ( Exception e ) {
						throw new RuntimeException(e);
					}
				}
			}

			@Override
			public Class<JobExecutionResult<JobWorkflowStatusDescription>> getBatchExecReturnType() {
				return (Class<JobExecutionResult<JobWorkflowStatusDescription>>) new JobExecutionResult<JobWorkflowStatusDescription>(null,null).getClass();
			}
		}, false, job.getRunAs().getId());

		return resultHolder.get();
	}

	protected Job markExecuting() {
		final AtomicReference<Job> jobHolder = new AtomicReference<Job>();
		taskHelper.execWithTaskContext(JOB_EXECUTION_TASK_NAME, new Runnable() {
			public void run() {
				try {
					jobHolder.set(jobService.markExecuting(jobId));
				} catch ( ObjectNotFoundException e ) {
					LOGGER.warn("Scheduled job {} persistent record no longer on file", jobId);
				}
			}
		}, false, null);
		return jobHolder.get();
	}

	protected Job markTerminated(final JobExecutionResult<JobWorkflowStatusDescription> result) {
		final AtomicReference<Job> jobHolder = new AtomicReference<Job>();
		taskHelper.execWithTaskContext(JOB_EXECUTION_TASK_NAME, new Runnable() {
			public void run() {
				try {
					jobHolder.set(jobService.markTerminated(jobId, result));
				} catch ( ObjectNotFoundException e ) {
					LOGGER.warn("Terminated scheduled job {} persistent record no longer on file", jobId);
				}
			}
		}, false, null);
		return jobHolder.get();
	}

	public UUID getJobId() {
		return jobId;
	}
}
