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
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.service.jobqueue.JobExecutionResult;
import org.jasig.ssp.service.jobqueue.JobExecutionStatus;
import org.jasig.ssp.service.jobqueue.JobExecutor;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.jobqueue.JobWorkflowStatusDescription;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.util.exception.RuntimeIoException;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * (Hopefully) generically useful common behavior for any {@link JobExecutor}
 * @param <P>
 * @param <T>
 */
public abstract class AbstractJobExecutor<P,T> implements JobExecutor<P> {

	private static final String INVALID_JOB_EXEC_CONFIG_MSG = "Invalid job execution configuration.  ID: {0}";
	private static final String MISSING_TRANSACTION_TEMPLATE_MSG = "Missing TransactionTemplate";
	private static final String NO_SUCH_JOB_CONFIG_MSG = "Job not found. ID: {0}";
	private static final String UNEXPECTED_EXECUTION_EXCEPTION_STATUS_MSG = "Job execution exited abnormally with an unexpected status: {0}. ID: {1}";
	private static final String UNHANDLED_EXECUTION_EXCEPTION_STATUS_MSG = "Job execution exited with a system failure. ID: {0}";
	private static final String JOB_DESERIALIZATION_ERROR_MSG = "Job {0} could not be deserialized. ID: {1}";
	private static final String JOB_SERIALIZATION_ERROR_MSG = "Job {0} could not be serialized. ID: {1}";
	private static final String JOB_SAVE_ERROR_MSG = "Job could not be saved. ID: {0}";
	private static final String PARTIAL_WORKFLOW_COMPLETION_MSG = "Partial completion";
	private static final String FAILED_PARTIAL_WORKFLOW_COMPLETION_MSG = "Partial completion with failures";
	private static final String DONE_WORKFLOW_COMPLETION_MSG = "Completed";
	private ObjectMapper objectMapper = new ObjectMapper();
	private PlatformTransactionManager transactionManager;
	private TransactionTemplate transactionTemplate;
	private JobService jobService;
	private String name;

	public AbstractJobExecutor(String name) {
		this(name,null,null,null);
	}

	public AbstractJobExecutor(String name, JobService jobService, PlatformTransactionManager transactionManager) {
		this(name,jobService,transactionManager,null);
	}

	/**
	 * Initialize this instance with {@code Job} management, JSON de/serialization and
	 * transaction management strategies. All can be null, but only {@code ObjectMapper}
	 * will be defaulted. Both can be accessed/set later. Expect problems downstream if
	 * {@code PlatformTransactionManager} and/or {@code JobService} are never set.
	 *
	 */
	public AbstractJobExecutor(String name, JobService jobService, PlatformTransactionManager transactionManager, ObjectMapper objectMapper) {
		if ( StringUtils.isBlank(name) ) {
			throw new IllegalArgumentException("Must specify a non-blank name");
		}
		this.name = name;
		this.transactionManager = transactionManager;
		initTransactionTemplate();
		this.jobService = jobService;
		this.objectMapper = objectMapper == null ? new ObjectMapper() : objectMapper;
	}

	@Override
	public Job queueNewJob(UUID ownerPersonId, UUID runAsPersonId, P jobSpec) throws RuntimeIoException, ValidationException {
		return jobService.queue(ownerPersonId, runAsPersonId, getName(), serializeJobSpec(jobSpec), serializeInitialJobState(jobSpec));
	}

	/**
	 * Finds and executes the given {@link Job} transactionally in {@link #executeInTransaction(java.util.UUID)}.
	 * Expects that method to raise exceptions if the transaction should be rolled back. In the case of a
	 * {@link JobExecutionException}, the {@link JobExecutionResult} is unpacked. If that object's and
	 * {@link JobExecutionStatus} indicates the job should be retried, {@link executionState} is written in
	 * a separate transaction {@link #prepareRetryInTransaction(java.util.UUID, org.jasig.ssp.service.jobqueue.JobExecutionResult)}.
	 * {@link #toNonExceptionalWorkflowResult(org.jasig.ssp.service.jobqueue.JobExecutionResult, java.util.UUID)}
	 * and {@link #toExceptionalWorkflowResult(org.jasig.ssp.service.jobqueue.JobExecutionResult, JobExecutionException, java.util.UUID)}
	 * can be overriden to control how execution results are translated to workflow results.
	 *
	 *
	 * @param jobId
	 * @return
	 */
	@Override
	public JobExecutionResult<JobWorkflowStatusDescription> execute(final UUID jobId) {
		final TransactionTemplate txnTemplate = getTransactionTemplate();
		if ( txnTemplate == null ) {
			return newWorkflowResultWithErrorMessage(JobExecutionStatus.ERROR, INVALID_JOB_EXEC_CONFIG_MSG, MISSING_TRANSACTION_TEMPLATE_MSG);
		}

		// TODO would be nice to extract workflow state msg ahead of time to support aggregation of
		// failures incrementally. Currently the job state would need to maintain a list of such
		// failures interally, then write them to the workflow status upon completion, which
		// limits visibility into job progress in any sort of generic way. (Same basic problem for
		// non-failure progress tracking as well.)
		try {

			final AtomicReference<JobExecutionResult<T>> rsltHolder = new AtomicReference<JobExecutionResult<T>>();
			txnTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					rsltHolder.set(executeInTransaction(jobId));
				}
			});
			return toNonExceptionalWorkflowResult(rsltHolder.get(), jobId);

		} catch ( JobExecutionException e ) {

			getLogger().warn("Job {} execution did not complete entirely successfully", jobId, e);

			final JobExecutionResult<T> exResult =  e.getJobExecutionResult();
			if ( exResult == null ) {
				// really isn't supposed to happen, hence relatively high log level
				getLogger().error("Job {} execution exited unexpectedly", jobId, e);
				return newWorkflowResultWithErrorMessage(JobExecutionStatus.ERROR, e.getMessage());
			}

			if ( exResult.getStatus() == JobExecutionStatus.ERROR ||
					exResult.getStatus() == JobExecutionStatus.FAILED ||
					exResult.getStatus() == JobExecutionStatus.INTERRUPTED ) {
				return toExceptionalWorkflowResult(exResult, e, jobId);
			} else if ( exResult.getStatus() == JobExecutionStatus.FAILED_PARTIAL ) {
				// 'main' execution transaction had to be rolled back, but still want to
				// update job state
				try {
					txnTemplate.execute(new TransactionCallbackWithoutResult() {
						@Override
						protected void doInTransactionWithoutResult(TransactionStatus status) {
							prepareRetryInTransaction(jobId, exResult);
						}
					});
					return toExceptionalWorkflowResult(exResult, e, jobId);
				} catch ( Exception ee ) {
					getLogger().error("Job {} could not store retry state", jobId, ee);
					return newWorkflowResultWithErrorMessage(JobExecutionStatus.ERROR, ee.getMessage());
				}
			} else {
				// programmer error
				return newWorkflowResultWithErrorMessage(JobExecutionStatus.ERROR, UNEXPECTED_EXECUTION_EXCEPTION_STATUS_MSG, exResult.getStatus(), jobId);
			}

		} catch ( Exception e ) {
			// really isn't supposed to happen, hence higher log level
			getLogger().error("Job {} execution exited unexpectedly", jobId, e);
			return newWorkflowResultWithErrorMessage(JobExecutionStatus.ERROR, UNHANDLED_EXECUTION_EXCEPTION_STATUS_MSG, jobId);
		}
	}

	/**
	 * Calls {@link #executeJob(org.jasig.ssp.model.jobqueue.Job)} and
	 * {@link #afterExecuteJob(org.jasig.ssp.model.jobqueue.Job, org.jasig.ssp.service.jobqueue.JobExecutionResult)}
	 * in a transaction opened by the caller. Ensures exceptions thrown by those methods are converted to
	 * {@link JobExecutionException}.
	 *
	 * <p>Non-success/retry results <em>must</em> be converted to exceptions before leaving this method. This
	 * is typically handled by
	 * {@link #afterExecuteJob(org.jasig.ssp.model.jobqueue.Job, org.jasig.ssp.service.jobqueue.JobExecutionResult)}</p>
	 *
	 * @param jobId
	 * @return
	 * @throws JobExecutionException
	 */
	protected JobExecutionResult<T> executeInTransaction(UUID jobId) throws JobExecutionException {
		final Job job = findJobOrFail(jobId);
		try {
			JobExecutionResult<T> resultWithState = executeJob(job);
			return afterExecuteJob(job, resultWithState);
		} catch ( JobExecutionException e ) {
			throw e;
		} catch ( RuntimeException e ) {
			// Really shouldn't happen b/c executeJob() is not supposed to exit exceptionally
			throw new JobExecutionException(MessageFormat.format(UNHANDLED_EXECUTION_EXCEPTION_STATUS_MSG, jobId), e);
		}
	}

	protected Job findJobOrFail(UUID jobId) throws JobExecutionBookkeepingException {
		final Job job = findJob(jobId);
		if ( job == null ) {
			throw new JobExecutionBookkeepingException(MessageFormat.format(NO_SUCH_JOB_CONFIG_MSG, jobId));
		}
		return job;
	}

	/**
	 * Deserializes the job spec and state and passes them to {@link #executeJobDeserialized(Object, Object)}
	 * @param job
	 * @return
	 * @throws JobExecutionException
	 */
	protected JobExecutionResult<T> executeJob(Job job) throws JobExecutionException {
		final String executionSpecStr = job.getExecutionSpec();
		final String executionStateStr = job.getExecutionState();
		final P executionSpec;
		try {
			executionSpec = executionSpecStr == null ? null : deserializeJobSpec(executionSpecStr);
		} catch ( RuntimeIoException e ) {
			throw new JobExecutionBookkeepingException(MessageFormat.format(JOB_DESERIALIZATION_ERROR_MSG, "spec", job.getId()), e);
		}
		final T executionState;
		try {
			executionState = executionStateStr == null ? null : deserializeJobState(executionStateStr);
		} catch ( RuntimeIoException e ) {
			throw new JobExecutionBookkeepingException(MessageFormat.format(JOB_DESERIALIZATION_ERROR_MSG, "state", job.getId()), e);
		}
		return executeJobDeserialized(executionSpec, executionState);
	}

	/**
	 * Responsible for translating non-success/retry statuses to exceptions.
	 *
	 * @param job
	 * @param result
	 * @return
	 */
	protected JobExecutionResult<T> afterExecuteJob(Job job, JobExecutionResult<T> result) {
		final JobExecutionStatus status = result.getStatus();
		switch ( status ) {
			case ERROR:
			case FAILED:
			case FAILED_PARTIAL:
			case INTERRUPTED:
				throw new JobExecutionException(result);
			case PARTIAL:
			case DONE:
				saveJobState(job, result);
				return result;
			default:
				throw new JobExecutionException(MessageFormat.format(UNEXPECTED_EXECUTION_EXCEPTION_STATUS_MSG, status, job.getId()));
		}
	}

	protected void prepareRetryInTransaction(UUID jobId, JobExecutionResult<T> exResult) throws JobExecutionException {
		saveJobState(findJobOrFail(jobId), exResult);
	}

	protected void saveJobState(Job job, JobExecutionResult<T> resultWithState) {
		try {
			jobService.updateExecutionState(serializeJobState(resultWithState.getDetail()), job);
		} catch ( RuntimeIoException e ) {
			throw new JobExecutionBookkeepingException(MessageFormat.format(JOB_SERIALIZATION_ERROR_MSG, "state", job.getId()), e);
		} catch ( Exception e ) {
			throw new JobExecutionBookkeepingException(MessageFormat.format(JOB_SAVE_ERROR_MSG, job.getId()), e);
		}
	}

	/**
	 * Main extension hook for adapting to an actual work-performing business service. Implementations are
	 * <em>strongly</em> discouraged from raising exceptions from this class. Use statuses in the
	 * {@link JobExecutionResult} to express errors/failures.
	 *
	 * @param executionSpec
	 * @param executionState
	 * @return
	 */
	protected abstract JobExecutionResult<T> executeJobDeserialized(P executionSpec, T executionState);

	protected String serializeInitialJobState(P spec) {
		return null;
	}

	protected String serializeJobSpec(P jobSpec) throws RuntimeIoException {
		return genericSerialize(jobSpec);
	}

	protected String serializeJobState(T jobState) throws RuntimeIoException {
		return genericSerialize(jobState);
	}

	protected String genericSerialize(Object toSerialize) throws RuntimeIoException {
		try {
			return toSerialize == null ? null : getObjectMapper().writeValueAsString(toSerialize);
		} catch ( RuntimeIoException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new RuntimeIoException(e);
		}
	}

	protected P deserializeJobSpec(String jobSpecStr) throws RuntimeIoException {
		try {
			return deserializeJobSpecWithCheckedExceptions(jobSpecStr);
		} catch ( RuntimeIoException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new RuntimeIoException(e);
		}
	}

	protected abstract P deserializeJobSpecWithCheckedExceptions(String jobSpecStr) throws Exception;

	protected T deserializeJobState(String jobStateStr) throws RuntimeIoException {
		try {
			return deserializeJobStateWithCheckedExceptions(jobStateStr);
		} catch ( Exception e ) {
			throw new RuntimeIoException(e);
		}
	}

	protected abstract T deserializeJobStateWithCheckedExceptions(String jobStateStr) throws Exception;

	/**
	 * Translate the result of {@link #execute(java.util.UUID)} into a workflow state representation suitable for
	 * storing on the current {@code Job} and where the {@link #execute(java.util.UUID)} call returned internally from
	 * {@link #executeInTransaction(java.util.UUID)} without the latter throwing an {@link Exception}. I.e. the
	 * actual work of executing the {@code Job} itself indicated that the {@link Job} is either done or requires
	 * additional work, but is not in some sort of failure/error state. Could use this to externalize progress
	 * information at the workflow level, for example.
	 *
	 * @param result
	 * @return
	 */
	protected JobExecutionResult<JobWorkflowStatusDescription> toNonExceptionalWorkflowResult(JobExecutionResult<T> result, UUID jobId) {
		return newWorkflowResultWithSuccessMessage(result.getStatus(), nonExceptionalCompletionMessageFor(result.getStatus()));
	}

	/**
	 * Same as {@link #toNonExceptionalWorkflowResult(org.jasig.ssp.service.jobqueue.JobExecutionResult)} but for
	 * the exceptional exit case.
	 *
	 * @param e
	 * @param jobId
	 * @return
	 */
	protected JobExecutionResult<JobWorkflowStatusDescription> toExceptionalWorkflowResult(JobExecutionResult<T> result, JobExecutionException e, UUID jobId) {
		return newWorkflowResultWithErrorMessage(result.getStatus(), e.getMessage());
	}

	private String nonExceptionalCompletionMessageFor(JobExecutionStatus status) {
		switch ( status ) {
			case PARTIAL:
				return PARTIAL_WORKFLOW_COMPLETION_MSG;
			case FAILED_PARTIAL:
				return FAILED_PARTIAL_WORKFLOW_COMPLETION_MSG;
			case DONE:
				return DONE_WORKFLOW_COMPLETION_MSG;
			default:
				// programmer error
				throw new IllegalArgumentException("Received exceptional execution status");
		}
	}

	protected JobExecutionResult<JobWorkflowStatusDescription> newWorkflowResultWithSuccessMessage(JobExecutionStatus status,
																								 String successMessage) {
		return new JobExecutionResult<JobWorkflowStatusDescription>(status,
				new JobWorkflowStatusDescription(successMessage,null,null));
	}

	protected JobExecutionResult<JobWorkflowStatusDescription> newWorkflowResultWithErrorMessage(JobExecutionStatus status,
																								 String errorMessage) {
		return new JobExecutionResult<JobWorkflowStatusDescription>(status,
				new JobWorkflowStatusDescription(null, Lists.newArrayList(errorMessage),null));
	}

	protected JobExecutionResult<JobWorkflowStatusDescription> newWorkflowResultWithErrorMessage(JobExecutionStatus status,
																								  String errorMessage,
																								  Object msgArg1) {
		return new JobExecutionResult<JobWorkflowStatusDescription>(status,
				new JobWorkflowStatusDescription(null, Lists.newArrayList(MessageFormat.format(errorMessage, msgArg1)),null));
	}

	protected JobExecutionResult<JobWorkflowStatusDescription> newWorkflowResultWithErrorMessage(JobExecutionStatus status,
																								 String errorMessage,
																								 Object msgArg1,
																								 Object msgArg2) {
		return new JobExecutionResult<JobWorkflowStatusDescription>(status,
				new JobWorkflowStatusDescription(null, Lists.newArrayList(MessageFormat.format(errorMessage, msgArg1, msgArg2)),null));
	}

	protected Job findJob(UUID jobId) {
		return jobService.get(jobId);
	}

	protected ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	protected void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	protected PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	protected void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
		initTransactionTemplate();
	}

	protected TransactionTemplate getTransactionTemplate() {
		return this.transactionTemplate;
	}

	protected void initTransactionTemplate() {
		final PlatformTransactionManager txnManager = getTransactionManager();
		if ( txnManager == null ) {
			this.transactionTemplate = null;
		}
		this.transactionTemplate = new TransactionTemplate(txnManager);
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	@Override
	public String getName() {
		return name;
	}

	protected abstract Logger getLogger();

}
