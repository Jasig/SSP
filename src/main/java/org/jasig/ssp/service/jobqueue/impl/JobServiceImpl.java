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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.ssp.dao.jobqueue.JobDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.model.jobqueue.WorkflowStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.ScheduledTaskWrapperService;
import org.jasig.ssp.service.impl.ScheduledTaskWrapperServiceImpl;
import org.jasig.ssp.service.jobqueue.JobExecutionResult;
import org.jasig.ssp.service.jobqueue.JobExecutor;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.jobqueue.JobWorkflowStatusDescription;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobServiceImpl implements JobService, ApplicationContextAware, BeanNameAware {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JobServiceImpl.class);

	private final long startupTime = new Date().getTime();

	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	@Value("#{configProperties.system_id}")
	private  String systemId = "";

	@Autowired
	private transient JobDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient WithTransaction withTransaction;

	@Autowired
	protected transient ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	protected transient ScheduledTaskWrapperService scheduledTaskWrapperService;

	private Map<String,JobExecutor> jobExecutorRegistry = Maps.newConcurrentMap();
	private ApplicationContext applicationContext;
	private String beanName;

	@Override
	@Transactional(rollbackFor = ValidationException.class)
	public Job queue(UUID ownerPersonId, UUID runAsPersonId, String executionComponentName,
					 String executionSpec, String executionState) throws ValidationException {

		if ( ownerPersonId == null ) {
			throw new ValidationException("Must specify an 'owner' Person's ID");
		}
		final Person owner;
		try {
			owner = personService.get(ownerPersonId);
		} catch ( ObjectNotFoundException e ) {
			throw new ValidationException("Proposed 'owner' Person ID [" + ownerPersonId +
					"] did not resolve to a Person", e);
		}

		if ( runAsPersonId == null ) {
			throw new ValidationException("Must specify a 'runAs' Person's ID");
		}
		final Person runAs;
		try {
			runAs = personService.get(runAsPersonId);
		} catch ( ObjectNotFoundException e ) {
			throw new ValidationException("Proposed 'runAs' Person ID [" + runAsPersonId +
					"] did not resolve to a Person", e);
		}

		if ( StringUtils.isBlank(executionComponentName) ) {
			// Specifically don't require that the name be associated with a currently
			// registered JobExecutor b/c the execution and thus lookup of the executor
			// is fundamentally asynchronous (so there still could be time to register
			// the executor). And we have retry logic in case the executor can't be
			// found.
			throw new ValidationException("Must specify an execution component name");
		}
		final Job job = new Job();
		job.setExecutionComponentName(executionComponentName);
		job.setRunAs(runAs);
		job.setOwner(owner);
		job.setWorkflowStatus(WorkflowStatus.QUEUED);
		job.setExecutionSpec(executionSpec);
		job.setExecutionState(executionState);
		return dao.save(job);
	}



	@Override
	public void scheduleQueuedJobs() {
		final List<JobExecutionWorkflow> jobExecutionWorkflows = Lists.newArrayListWithExpectedSize(10);

		withTransaction.withTransactionAndUncheckedExceptions(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				final List<Job> jobs = dao.getNextQueuedJobsForExecution(10,getProcessIdentifier());
				for ( Job job : jobs ) {
					if ( Thread.currentThread().isInterrupted() ) {
						LOGGER.info("Abandoning job scheduling because of thread interruption");
						// force rollback to make it slightly clearer that jobs already
						// iterated through won't actually be queued into the task executor
						throw new InterruptedException();
					}
					jobExecutionWorkflows.add(new JobExecutionWorkflow(job.getId(),
							(ScheduledTaskWrapperServiceImpl)scheduledTaskWrapperService, // yes, sucks
							applicationContext.getBean(beanName, JobService.class))); // make sure we get the proxied version
					markScheduling(job);
				}
				return null;
			}
		});

		final List<JobExecutionWorkflow> requeues = Lists.newArrayListWithCapacity(jobExecutionWorkflows.size());
		final List<Pair<JobExecutionWorkflow,Exception>> errors = Lists.newArrayListWithCapacity(jobExecutionWorkflows.size());
		for ( JobExecutionWorkflow jobExecutionWorkflow : jobExecutionWorkflows) {
			try {
				taskExecutor.execute(jobExecutionWorkflow);
			} catch ( TaskRejectedException e ) {
				LOGGER.info("Scheduling rejected, will requeue job {}", jobExecutionWorkflow.getJobId(), e);
				requeues.add(jobExecutionWorkflow);
			} catch ( Exception e ) {
				LOGGER.info("Scheduling rejected, will error out job {}", jobExecutionWorkflow.getJobId(), e);
				errors.add(new Pair<JobExecutionWorkflow, Exception>(jobExecutionWorkflow, e));
			}
		}

		for ( final JobExecutionWorkflow requeue : requeues ) {
			// transaction per job to try to avoid one *really* bad job preventing others
			// from requeue
			try {
				withTransaction.withNewTransactionAndUncheckedExceptions(new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						markQueued(dao.get(requeue.getJobId()));
						return null;
					}
				});
			} catch ( Exception e ) {
				// nothing much to be done. hopefully this is just b/c we're in the
				// middle of a shutdown, in which case a restart will effectively
				// requeue the job b/c the process ID will change so the scheduling
				// will appear to have been abandoned
				LOGGER.error("Could not requeue job {}", requeue.getJobId(), e);
			}
		}

		for ( final Pair<JobExecutionWorkflow, Exception> error : errors ) {
			// transaction per job to try to avoid one *really* bad job preventing others
			// from being marked as errored out
			final UUID jobId = error.getFirst().getJobId();
			try {
				withTransaction.withNewTransactionAndUncheckedExceptions(new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						markErrored(dao.get(jobId), error.getSecond());
						return null;
					}
				});
			} catch ( Exception e ) {
				// nothing much to be done. hopefully this is just b/c we're in the
				// middle of a shutdown, in which case a restart will effectively
				// requeue the job b/c the process ID will change so the scheduling
				// will appear to have been abandoned
				LOGGER.error("Could not mark job as errored out {}", jobId, e);
			}
		}
	}

	private Job markQueued(Job job) {
		job.setWorkflowStatus(WorkflowStatus.QUEUED);
		job.setSchedulingStartedDate(null);
		job.setScheduledByProcess(null);
		job.setExecutionStartedDate(null);
		return dao.save(job);
	}

	private Job markScheduling(Job job) {
		job.setWorkflowStatus(WorkflowStatus.SCHEDULING);
		job.setSchedulingStartedDate(new Date());
		job.setScheduledByProcess(getProcessIdentifier());
		job.setExecutionStartedDate(null);
		return dao.save(job);
	}

	private Job markErrored(Job job, Exception e) {
		return markErrored(job, new JobWorkflowStatusDescription(null, Lists.newArrayList(e.getMessage())));
	}

	private Job markErrored(Job job, JobWorkflowStatusDescription d) {
		job.setWorkflowStatus(WorkflowStatus.ERROR);
		job.setWorkflowStoppedDate(new Date());
		serializeWorkflowStatusDescriptionOnto(d, job);
		return dao.save(job);
	}

	private Job markFailed(Job job, JobWorkflowStatusDescription d) {
		job.setWorkflowStatus(WorkflowStatus.FAILURE);
		job.setWorkflowStoppedDate(new Date());
		serializeWorkflowStatusDescriptionOnto(d, job);
		return dao.save(job);
	}

	private Job markCompleted(Job job, JobWorkflowStatusDescription d) {
		job.setWorkflowStatus(WorkflowStatus.COMPLETED);
		job.setWorkflowStoppedDate(new Date());
		serializeWorkflowStatusDescriptionOnto(d, job);
		return dao.save(job);
	}

	private void serializeWorkflowStatusDescriptionOnto(JobWorkflowStatusDescription d, Job job) {
		try {
			job.setWorkflowStatusDesc(d == null ? null : JSON_MAPPER.writeValueAsString(d));
		} catch ( Exception e ) {
			// oh well
			LOGGER.error("Unable to serialize persistent workflow status description {} for job {}", d, job.getId());
		}
	}

	// some state transitions need to be public...

	@Override
	@Transactional()
	public Job markExecuting(UUID jobId) throws ObjectNotFoundException {
		Job job = get(jobId);
		if ( job == null ) {
			throw new ObjectNotFoundException(jobId, Job.class.getName());
		}
		job.setWorkflowStatus(WorkflowStatus.EXECUTING);
		job.setExecutionStartedDate(new Date());
		return dao.save(job);
	}

	@Override
	@Transactional
	public Job markTerminated(UUID jobId, JobExecutionResult<JobWorkflowStatusDescription> result) throws ObjectNotFoundException {
		Job job = get(jobId);
		if ( job == null ) {
			throw new ObjectNotFoundException(jobId, Job.class.getName());
		}
		switch ( result.getStatus() ) {
			case PARTIAL:
			case FAILED_PARTIAL:
				// programmer error
				throw new IllegalStateException("Job execution result indicates job should not be terminated");
			case DONE:
				return markCompleted(job, result.getDetail());
			case ERROR: // an error in *execution* is a failure from the workflow perspective
			case FAILED:
				return markFailed(job, result.getDetail());
			case INTERRUPTED:
				return markQueued(job);
			default: // have no idea what went on so some sort of infrastructure failure, let's assume with workflow rather than execution
				return markErrored(job, result.getDetail());
		}
	}

	@Override
	public void registerJobExecutor(JobExecutor jobExecutor) {
		if ( jobExecutor == null ) {
			throw new IllegalArgumentException("Must specify a JobExecutor");
		}
		if ( StringUtils.isBlank(jobExecutor.getName()) ) {
			throw new IllegalArgumentException("JobExecutors must be assigned a name prior to registration");
		}
		jobExecutorRegistry.put(jobExecutor.getName(), jobExecutor);
	}

	@Override
	public JobExecutor deregisterJobExecutor(String name) {
		return jobExecutorRegistry.remove(name);
	}

	@Override
	public JobExecutor findRegisteredJobExecutor(String name) {
		return jobExecutorRegistry.get(name);
	}

	@Override
	public Job get(UUID jobId) {
		try {
			return dao.get(jobId);
		} catch ( ObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	@Transactional
	public Job updateExecutionState(String execState, Job job) {
		job.setExecutionState(execState);
		return dao.save(job);
	}

	private String getProcessIdentifier() {
		return systemId+"-"+startupTime+"."+systemId;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}
}
