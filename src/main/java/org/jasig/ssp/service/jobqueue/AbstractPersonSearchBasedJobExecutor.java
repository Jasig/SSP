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


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;
import org.jasig.ssp.transferobject.form.HasPersonSearchRequestCoreSpec;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class purpose described in method Javadoc for:
 * {@link #executeJobDeserialized(org.jasig.ssp.transferobject.form.HasPersonSearchRequestCoreSpec, BasePersonSearchBasedJobExecutionState, java.util.UUID)}
 *
 * @param <P>
 * @param <T>
 */
public abstract class AbstractPersonSearchBasedJobExecutor<P extends HasPersonSearchRequestCoreSpec,T extends BasePersonSearchBasedJobExecutionState>
		extends AbstractJobExecutor<P,T> {

	private PersonSearchService personSearchService;
	private PersonSearchRequestTOFactory personSearchRequestFactory;
	private final ConfigService configService;

	public AbstractPersonSearchBasedJobExecutor(String name, JobService jobService, PlatformTransactionManager transactionManager,
												ObjectMapper objectMapper,
												PersonSearchService personSearchService,
												PersonSearchRequestTOFactory personSearchRequestFactory,
												ConfigService configService) {
		super(name,jobService,transactionManager,objectMapper);
		this.personSearchService = personSearchService;
		this.personSearchRequestFactory = personSearchRequestFactory;
		this.configService = configService;
	}

	/**
	 * This method represents the primary Main purpose of this class, i.e. to override the hook in
	 * {@link AbstractJobExecutor} to implement a paginated, {@code Person} search-based template for the "actual" work
	 * a job executor is intended to perform. Still quite a few extension hooks for job-specific work, of course. The
	 * "template" here is much more complicated than in {@link AbstractJobExecutor} or
	 * {@link AbstractPersonSearchBasedJobQueuer} so the granularity of the extension hooks is somewhat coarser in an
	 * attempt to keep the internal API under control. Fully expect the extension hook design/quantity to change as more
	 * concrete job executors are introduced.
	 *
	 */
	@Override
	protected JobExecutionResult<T> executeJobDeserialized(P executionSpec, T executionState, UUID jobId) {
		try {

			if ( executionState == null ) {
				// Nested calls to ensure we don't persist a not-fully-confgured execution state
				executionState = configureNewExecutionState(newBulkEmailJobExecutionState());
			}

			if ( executionState.allPagesProcessed && executionState.retryQueue.isEmpty() ) {
				logCompletion(executionState, jobId);
				return new JobExecutionResult<T>(JobExecutionStatus.DONE, executionState, null);
			}

			// process retry queue first so it doesn't grow indefinitely and we hit fail threshholds as early
			// as possible
			if ( !(executionState.retryQueue.isEmpty()) ) {
				logProgress("Processing retry queue (size: [{}]) in Job [{}]. ", new Object[] { executionState.retryQueue.size(), jobId });
				// retry queue gets processed one element per transaction to try to weed out bad apples that failed
				// larger batches
				final ImmutablePersonIdentifiersTO personIds = executionState.retryQueue.remove(0);
				try {
					logProgress("Retry for person IDs [{}] in Job [{}].", new Object[] { personIds, jobId });
					final Map<String, ?> results = executeRetryForSinglePerson(personIds, executionSpec, executionState, jobId);
					recordSuccessfulRetry(personIds, results, executionSpec, executionState, jobId);
					logProgress("Successful retry for person IDs [{}] in Job [{}].",
							new Object[] { personIds, jobId });
					return new JobExecutionResult<T>(JobExecutionStatus.PARTIAL, executionState, null);
				} catch ( Exception e ) {
					recordUnsuccessfulRetry(personIds, e, executionSpec, executionState, jobId);
					if ( executionState.dlq.size() >= executionState.maxDlqLength ) {
						executionState.dlqOverflowed = true;
						if ( executionState.failOnDlqOverflow ) {
							final RuntimeException ee = new RuntimeException("Processing error in retry queue for person IDs ["
									+ personIds + "] and dead letter queue exceeded max length ["
									+ executionState.maxDlqLength
									+ "]. Failing Job [" + jobId + "]. See cause for most recent failure", e);
							return new JobExecutionResult<T>(JobExecutionStatus.FAILED, executionState, ee);
						} else {
							// no room in the dlq. exception will be logged, but otherwise silently drop this
							// person as an execution target
							logProgress("Processing error in retry queue for person IDs [{}] but no " +
									"room in DLQ in Job [{}].", new Object[] { personIds, jobId });
							return new JobExecutionResult<T>(JobExecutionStatus.FAILED_PARTIAL, executionState, e);
						}
					} else {
						executionState.dlq.add(personIds);
						logProgress("Processing error in retry queue. Added person IDs [{}] to DLQ " +
								"(size: [{}]) in Job [{}].",
								new Object[]{personIds, executionState.dlq.size(), jobId});
						return new JobExecutionResult<T>(JobExecutionStatus.FAILED_PARTIAL, executionState, e);
					}
				}

			} else {

				final PersonSearchRequest criteria = personSearchRequestFactory.from(executionSpec.getCoreSpec().getCriteria());
				final SortingAndPaging origSortAndPage = criteria.getSortAndPage();
				final SortingAndPaging nextSortAndPage;
				int page = 1;
				if ( executionState.prevPage != null ) {
					page = executionState.prevPage + 1;
				}

				logProgress("Processing result page [{}] (max page size: [{}]) in Job [{}].",
						new Object[]{page, executionState.pageSize, jobId});

				nextSortAndPage = new SortingAndPaging(origSortAndPage.getStatus(), (page - 1) * executionState.pageSize,
						executionState.pageSize, origSortAndPage.getSortFields(), origSortAndPage.getDefaultSortProperty(),
						origSortAndPage.getDefaultSortDirection());
				criteria.setSortAndPage(nextSortAndPage);

				final PagingWrapper<PersonSearchResult2> searchResults = personSearchService.searchPersonDirectory(criteria);
				if ( searchResults == null || searchResults.getResults() == 0L ) {
					logProgress("No results on page [{}] (max page size: [{}]) in Job [{}].",
							new Object[]{page, executionState.pageSize, jobId});
					executionState.prevPage = page;
					executionState.allPagesProcessed = true;
					// always return partial within this logic tree to ensure centralized completion logic at the top
					// of this method, i.e. let the job run us again to see if we're *really* done.
					return new JobExecutionResult<T>(JobExecutionStatus.PARTIAL, executionState, null);
				} else {

					logProgress("Processing [{}] persons on page [{}] (max page size: [{}], total persons: [{}]) in Job [{}].",
							new Object[]{searchResults.getRows().size(), page, executionState.pageSize, searchResults.getResults(), jobId});

					final List<ImmutablePersonIdentifiersTO> targetPersonIdentifiers =
							Lists.newArrayListWithCapacity((int) searchResults.getResults());

					// build target list outside of actual 'work loop' so we have the complete
					// list of failed targets if any one should fail (and thus rollback the whole transaction)
					for ( PersonSearchResult2 searchResult : searchResults ) {
						final ImmutablePersonIdentifiersTO searchResultIds =
								new ImmutablePersonIdentifiersTO(searchResult.getId(), searchResult.getSchoolId());
						targetPersonIdentifiers.add(searchResultIds);
					}

					ImmutablePersonIdentifiersTO targetPersonIdentifier = null;
					final LinkedHashMap<ImmutablePersonIdentifiersTO, Map<String, ?>> batchCreatedRecords = Maps.newLinkedHashMap();
					try {
						Iterator<ImmutablePersonIdentifiersTO> i = targetPersonIdentifiers.iterator();
						while ( i.hasNext() ) {
							targetPersonIdentifier = i.next();
							logProgress("Execution attempt for person IDs [{}] in Job [{}].", new Object[]{targetPersonIdentifier, jobId});
							batchCreatedRecords.put(targetPersonIdentifier,
									executeForSinglePerson(targetPersonIdentifier, executionSpec, executionState, jobId));
						}
					} catch ( Exception e ) {
						executionState.retryQueue.addAll(targetPersonIdentifiers);
						logProgress("Processing error for person IDs [{}] on page [{}] (max page size: [{}]) in Job [{}]. " +
								"Added this page to the retry queue (size: [{}])",
								new Object[] { targetPersonIdentifier, page, executionState.pageSize, jobId, executionState.retryQueue.size() }); // exception itself logged elsewhere
						return new JobExecutionResult<T>(JobExecutionStatus.FAILED_PARTIAL, executionState, e);
					} finally {
						executionState.prevPage = page;
						final long totalResults = searchResults.getResults();
						executionState.allPagesProcessed = (page * executionState.pageSize) >= totalResults;
					}

					for ( Map.Entry<ImmutablePersonIdentifiersTO, Map<String, ?>> createdRecords : batchCreatedRecords.entrySet() ) {
						recordSuccessful(createdRecords.getKey(), createdRecords.getValue(), executionSpec, executionState, jobId);
					}
					logProgress("Processed [{}] persons on page [{}] (max page size: [{}], total persons: [{}]) in Job [{}].",
							new Object[]{targetPersonIdentifiers.size(), page, executionState.pageSize, searchResults.getResults(), jobId});
					return new JobExecutionResult<T>(JobExecutionStatus.PARTIAL, executionState, null);

				}

			}

		} catch ( Exception e ) {
			return new JobExecutionResult<T>(JobExecutionStatus.ERROR, executionState, e);
		}
	}

	/**
	 * Logs 'progress' message at a consistent level (debug by default), and allowing subclasses to override/extend
	 * messages via {@link #decorateProgressLogMessage(String)}.
	 *
	 * @param msg
	 * @param msgArgs
	 */
	protected void logProgress(String msg, Object[] msgArgs) {
		getCurrentLogger().debug(decorateProgressLogMessage(msg), msgArgs);
	}

	/**
	 * Typically shouldn't need to override this, but exists for symmetry with
	 * {@link #decorateProcessingCompleteLogMessage(String, BasePersonSearchBasedJobExecutionState)} which <em>is</em>
	 * typically overridden.
	 *
	 * <p>Don't typically need to override this b/c a) it's not actually possible/worth the trouble to enforce
	 * consistent usage of {@link #logProgress(String, Object[])} for all messages having to do with job execution
	 * (most implementations rely on methods implemented outside the job executor subclass) and b) usually
	 * the idea here would be to add some sort of identifying token for the job itself, which is often redundant
	 * given that most messages already output the job ID and the {@code Logger} itself is typically in a namespace
	 * that identifies the executor, and that namespace is almost always included in log message formats. So if
	 * you do need consistent execution/job-identifying tokens, consider using the logging framework's native NDC
	 * cabilities and push your token onto that stack in {@link AbstractJobExecutor#execute(java.util.UUID)} or
	 * some other 'higher level' method in the execution template.</p>
	 *
	 * @param baseMsg
	 * @return
	 */
	protected String decorateProgressLogMessage(String baseMsg) {
		return baseMsg;
	}

	protected Map<String,?> executeRetryForSinglePerson(ImmutablePersonIdentifiersTO personIds, P executionSpec,
														T executionState, UUID jobId) throws ValidationException, ObjectNotFoundException {
		return executeForSinglePerson(personIds, executionSpec, executionState, jobId);
	}

	protected void recordSuccessfulRetry(ImmutablePersonIdentifiersTO personIds, Map<String, ?> results, P executionSpec,
										 T executionState, UUID jobId) {
		recordSuccessful(personIds, results, executionSpec, executionState, jobId);
	}

	protected void recordUnsuccessfulRetry(ImmutablePersonIdentifiersTO personIds, Exception e, P executionSpec, T executionState, UUID jobId) {
		executionState.personsFailedCount++;
	}

	protected abstract Map<String,?> executeForSinglePerson(ImmutablePersonIdentifiersTO personIds, P executionSpec,
															T executionState, UUID jobId) throws ValidationException, ObjectNotFoundException;

	protected void recordSuccessful(ImmutablePersonIdentifiersTO personIds, Map<String, ?> results, P executionSpec,
									T executionState, UUID jobId) {
		executionState.personsSucceededCount++;
	}

	protected void logCompletion(T executionState, UUID jobId) {
		logProgress(decorateProcessingCompleteLogMessage("Processing complete in Job [{}]. Successful persons " +
				"count: [{}]. Unsuccessful persons count: [{}]", executionState),
				new Object[] { jobId, executionState.personsSucceededCount, executionState.personsFailedCount });
	}

	protected String decorateProcessingCompleteLogMessage(String baseMsg, T executionState) {
		return baseMsg;
	}

	protected abstract T newBulkEmailJobExecutionState();

	protected T configureNewExecutionState(T executionState) {
		executionState.pageSize = configService.getByNameExceptionOrDefaultAsInt(getPageSizeConfigName());
		executionState.maxDlqLength = configService.getByNameExceptionOrDefaultAsInt(getDlqSizeConfigName());
		executionState.failOnDlqOverflow =
				Boolean.parseBoolean(configService.getByNameNullOrDefaultValue(getFailOnSlqOverflowConfigName()));
		return executionState;
	}

	protected abstract String getPageSizeConfigName();

	protected abstract String getDlqSizeConfigName();

	protected abstract String getFailOnSlqOverflowConfigName();

	public PersonSearchRequestTOFactory getPersonSearchRequestFactory() {
		return personSearchRequestFactory;
	}

	public void setPersonSearchRequestFactory(PersonSearchRequestTOFactory personSearchRequestFactory) {
		this.personSearchRequestFactory = personSearchRequestFactory;
	}

	public PersonSearchService getPersonSearchService() {
		return personSearchService;
	}

	public void setPersonSearchService(PersonSearchService personSearchService) {
		this.personSearchService = personSearchService;
	}

	public ConfigService getConfigService() {
		return configService;
	}
}
