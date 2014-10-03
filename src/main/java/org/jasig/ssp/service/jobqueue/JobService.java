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
package org.jasig.ssp.service.jobqueue;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.transaction.annotation.Transactional;


/**
 * Server-side API for managing {@link Job}. Distinct from {@link org.jasig.ssp.service.ScheduledTaskWrapperService}
 * in that {@code JobService} is about end-user submitted, deferred-execution work, whereas
 * {@link org.jasig.ssp.service.ScheduledTaskWrapperService} is about a static set of asynchronously executing chunks
 * of logic. The latter typically drives the former, esp. w/r/t calling {@link #scheduleQueuedJobs()} periodically.}
 */
public interface JobService {

	/**
	 * Register the given {@link JobExecutor} under it's {@link org.jasig.ssp.service.jobqueue.JobExecutor#getName()},
	 * overwriting any other {@link JobExecutor} currently registered under that string.
	 *
	 * <p>Behavior is undefined if the {@link JobExecutor}'s name is subsequently changed.</p>
	 *
	 * <p>Implemention may choose to support {@code null} names, but this is discouraged.</p>
	 *
	 * @param jobExecutor
	 */
	void registerJobExecutor(JobExecutor jobExecutor);

	/**
	 * The opposite of {@link #registerJobExecutor(JobExecutor)}.
	 *
	 * @param name
	 * @return the deregistered executor, if any
	 */
	JobExecutor deregisterJobExecutor(String name);

	/**
	 * Get a reference to a {@link JobExecutor} registered under the given name
	 *
	 * @param name
	 * @return {@code null} if no executor registered under the given name
	 */
	JobExecutor findRegisteredJobExecutor(String name);

	/**
	 * Schedule some subset of schedulable jobs. Scheduling means "lock and place into an execution queue", which is
	 * typically some sort of in-memory thread pool.
	 *
	 * <p>Implemenation should not block while the scheduled jobs actually execute</p>
	 *
	 * <p>This method typically called from the applications 'main' background work scheduler and <em>should not
	 * typically be wrapped in a client transaction. Clients should assume this implementation will manage its
	 * own transactions.</em></p>
	 */
	void scheduleQueuedJobs();

	/**
	 * Create and enqueue a new {@link Job}
	 *
	 * @param ownerPersonId actual end user requesting the unit of work
	 * @param runAsPersonId the user as whom to perform the work
	 * @param executionComponentName the logical name of the {@link JobExecutor} which knows how to perform the work
	 *                               ({@link #registerJobExecutor(JobExecutor)})
	 * @param executionSpec serialized representation of the work parameters to be passed to the {@link JobExecutor}
	 * @param executionState initial state of the work to be performed; typically {@code null}
	 * @return the created {@link Job}
	 * @throws ValidationException
	 */
	Job queue(UUID ownerPersonId, UUID runAsPersonId, String executionComponentName,
			   String executionSpec, String executionState) throws ValidationException;

	/**
	 * Transition the given job to {@link WorkflowStatus#EXECUTING}.
	 *
	 * @param jobId
	 * @return
	 * @throws ObjectNotFoundException if the {@link Job} is not on file
	 */
	Job markExecuting(UUID jobId) throws ObjectNotFoundException;

	/**
	 * Transition the job to a workflow-terminated state described by the given {@link JobExecutionResult}
	 *
	 * @param jobId
	 * @param result
	 * @return
	 * @throws ObjectNotFoundException if the {@link Job} is not on file
	 */
	Job markTerminated(UUID jobId, JobExecutionResult<JobWorkflowStatusDescription> result)  throws ObjectNotFoundException;

	/**
	 * Transition the given {@link Job} to the given serialized {@code executionState}. Typically used to
	 * store incremental, batched progress against the total work requested, sometimes in a different transaction
	 * from the work itself.
	 *
	 * @param execState
	 * @param job
	 * @return
	 */
	Job updateExecutionState(String execState, Job job);

	/**
	 * Look up a {@link Job} by its PK.
	 *
	 * @param jobId
	 * @return
	 */
	Job get(UUID jobId);
}