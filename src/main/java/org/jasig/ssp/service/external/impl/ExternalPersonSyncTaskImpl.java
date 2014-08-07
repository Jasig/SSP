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
package org.jasig.ssp.service.external.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.jasig.ssp.dao.external.ExternalPersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.service.external.ExternalPersonSyncTask;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

@Service
public class ExternalPersonSyncTaskImpl implements ExternalPersonSyncTask {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalPersonSyncTaskImpl.class);

	private static final Class<Pair<Long, Long>> BATCH_RETURN_TYPE =
			(Class<Pair<Long, Long>>) new Pair<Long,Long>(null,null).getClass();

	private static final String BATCH_SIZE_CONFIG_NAME = "task_external_person_sync_batch_size";
	private static final int DEFAULT_BATCH_SIZE = 100;
	private static final String MAX_BATCHES_PER_EXECUTION_CONFIG_NAME = "task_external_person_sync_max_batches_per_exec";
	private static final int DEFAULT_MAX_BATCHES_PER_EXECUTION = -1; // unlimited


	@Autowired
	private ExternalPersonService externalPersonService;
	
	@Autowired
	private PersonSearchService directoryPersonService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private transient ExternalPersonDao dao;

	@Autowired
	private WithTransaction withTransaction;

	private transient long nextPersonIndex = 0;

	// intentionally not transactional... this is the main loop, each iteration
	// of which should be its own transaction.
	@Override
	public void exec(CallableExecutor<Pair<Long,Long>> batchExec) {

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning external person sync because of thread interruption");
			return;
		}
 
		LOGGER.info("BEGIN : External person sync.");

		int recordsProcessed = 0;
		int batch = 0;
		Exception error = null;
		int maxBatchesAllowed = DEFAULT_MAX_BATCHES_PER_EXECUTION;
		while ( true ) {
			// Check this config every time in case someone wants to abort a
			// long-running execution.
			maxBatchesAllowed = getMaxBatchesAllowed();
			if ( maxBatchesAllowed == 0 ) {
				LOGGER.info("Abandoning external person sync at position [{}]"
						+ " and batch [{}] because the  batch limit has been"
						+ " set to zero. Records processed: [{}]",
						new Object[] {nextPersonIndex, batch, recordsProcessed });
				break;
			}

			error = null;
			batch++;
 
			// again, look up config every time to allow for relatively immediate
			// control over runnaway executions
			int batchSize = getBatchSize();
			final SortingAndPaging sAndP = SortingAndPaging.createForSingleSortWithPaging(
					ObjectStatus.ACTIVE,
					new BigDecimal(nextPersonIndex).intValueExact(),  // API mismatch... see more comments below
					batchSize,
					"username",
					SortDirection.ASC.toString(), null);

			Pair<Long,Long> processedOfTotal = null;
			try {
				if ( batchExec == null ) {
					processedOfTotal = syncWithPersonInTransaction(sAndP);
				} else {
					processedOfTotal = batchExec.exec(new Callable<Pair<Long, Long>>() {
						@Override
						public Pair<Long, Long> call() throws Exception {
							return syncWithPersonInTransaction(sAndP);
						}
					});
				}
			} catch ( InterruptedException e ) {
				Thread.currentThread().interrupt(); // reassert
			} catch (final Exception e) {
				error = e;
			} finally {

				if ( processedOfTotal == null ) {
					if ( error != null ) {
						LOGGER.error("Abandoning external person sync at"
								+ " position [{}] and batch [{}] because of a"
								+ " processing error. Will resume at that"
								+ " position at the next execution.",
								new Object[] {nextPersonIndex, batch - 1, error });
						break;
					}
					if ( Thread.currentThread().isInterrupted() ) {
						LOGGER.error("Abandoning external person sync at"
								+ " position [{}] and batch [{}] because of an"
								+ " InterruptionException. Will resume at that"
								+ " position at the next execution.",
								nextPersonIndex, batch - 1);
						break;
					}
					// programmer error, no clue what to do so let the NPE's fly...
				}

				nextPersonIndex += processedOfTotal.getFirst();
				recordsProcessed += processedOfTotal.getFirst();

				LOGGER.info("Processed [{}] of [{}] candidate person records"
						+ " as of batch [{}] of [{}]. Total records processed [{}].",
						new Object[] {nextPersonIndex, processedOfTotal.getSecond(),
								batch, maxBatchesAllowed, recordsProcessed });

				if ( processedOfTotal.getFirst() == 0 ) {
					// shouldn't happen but want to guard against endless loops
					LOGGER.debug("Appear to be more records to process but"
							+ " last batch processed zero records. Exiting"
							+ " person sync task.");
					nextPersonIndex = 0;
					break;
				}

				if ( maxBatchesAllowed > 0 && batch >= getMaxBatchesAllowed() ) {
					LOGGER.debug("No more batches allowed for this execution."
							+ " Exiting person sync task. Will resume at"
							+ " index [{}] on next execution.", nextPersonIndex);
					break;
				}

				if ( nextPersonIndex >= processedOfTotal.getSecond() ) {
					nextPersonIndex = 0;
					LOGGER.debug("Reached the end of the list of candidate"
							+ " persons for sync. More batches are allowed, so"
							+ " starting over at index 0.");

					// no break!!
				}

				if ( recordsProcessed >= processedOfTotal.getSecond() ) {
					LOGGER.debug("More batches allowed, but all candidate" +
							" person records have already been processed"
							+ " in this execution. Will resume at index"
							+ " [{}] on next execution.", nextPersonIndex);
				}

				// Mismatch between the PagedResponse and SortingAndPaging
				// APIs mean we can't actually deal with total result sets
				// larger than Integer.MAX_VALUE
				if ( nextPersonIndex > Integer.MAX_VALUE ) {
					LOGGER.warn("Cannot process more than {} total persons,"
							+ " even across executions. Abandoning and"
							+ " resetting sync task.",
							Integer.MAX_VALUE);
					nextPersonIndex = 0;
					break;
				}

			}
		}


		LOGGER.info("END : External person sync.");
	}

	@Override
	public Class<Pair<Long, Long>> getBatchExecReturnType() {
		return BATCH_RETURN_TYPE;
	}

	protected Pair<Long, Long> syncWithPersonInTransaction(final SortingAndPaging sAndP) throws Exception {
		return withTransaction.withNewTransaction(new Callable<Pair<Long, Long>>() {
			@Override
			public Pair<Long, Long> call() throws Exception {
				return syncWithPerson(sAndP);
			}
		});
	}

	protected Pair<Long,Long> syncWithPerson(final SortingAndPaging sAndP) throws InterruptedException {

		// Use InterruptedExceptions instead of manipulating return value b/c
		// the return value actually means "total number of possible processable"
		// rows, so if it is returned gracefully, the caller will likely
		// incorrectly update its internal state, as is the case with the
		// scheduled job call site.

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning external person sync because of thread interruption");
			throw new InterruptedException();
		}

		LOGGER.info("External person sync Selecting [{}] records starting at [{}]",
			sAndP.getMaxResults(), nextPersonIndex);

		final PagingWrapper<Person> people = personService.getAll(sAndP);

		if ( people.getRows().isEmpty() ) {
			LOGGER.info("External person sync found 0 records starting at [{}]", nextPersonIndex);
			return new Pair(0L, people.getResults());
		}

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning external person sync because of thread interruption");
			throw new InterruptedException();
		}

		// allow access to people by schoolId
		final Map<String, Person> peopleBySchoolId = Maps.newHashMap();
		long peopleCnt = 0;
		for (final Person person : people) {
			peopleBySchoolId.put(person.getSchoolId(), person);
			peopleCnt++;
		}

		Set<String> internalPeopleSchoolIds = peopleBySchoolId.keySet();
		if ( LOGGER.isDebugEnabled() ) {
			LOGGER.debug(
					"Candidate internal person schoolIds for sync with external persons {}",
					internalPeopleSchoolIds);
		}

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning external person sync because of thread interruption");
			throw new InterruptedException();
		}

		// fetch external people by schoolId
		final PagingWrapper<ExternalPerson> externalPeople =
				dao.getBySchoolIds(internalPeopleSchoolIds,SortingAndPaging.createForSingleSortWithPaging(
						ObjectStatus.ACTIVE, 0, sAndP.getMaxResults(),
						"username",
						SortDirection.ASC.toString(), null));

		for (final ExternalPerson externalPerson : externalPeople) {

			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Abandoning external person sync because of thread interruption on person {}", externalPerson.getUsername());
				throw new InterruptedException();
			}

			LOGGER.debug(
					"Looking for internal person by external person schoolId {}",
					externalPerson.getSchoolId());
			// get the previously fetched person
			final Person person = peopleBySchoolId.get(externalPerson
					.getSchoolId());
			// upate person from external person
			externalPersonService.updatePersonFromExternalPerson(person, externalPerson,true);

		}

		return new Pair(peopleCnt, people.getResults());
	}

	private int getMaxBatchesAllowed() {
		String maxBatchesStr =
				configService.getByNameNullOrDefaultValue(MAX_BATCHES_PER_EXECUTION_CONFIG_NAME);
		int maxBatches = DEFAULT_MAX_BATCHES_PER_EXECUTION;
		try {
			maxBatches = Integer.parseInt(maxBatchesStr);
			LOGGER.debug("Using [{}] configured batches-per-execution limit of"
					+ " [{}]. (Negative values treated as unlimited.)",
					MAX_BATCHES_PER_EXECUTION_CONFIG_NAME, maxBatches);
		} catch ( NumberFormatException e ) {
			// only info b/c this thing is potentially called so frequently and
			// the defaults are probably fine, so we'd just be spamming the logs
			// with a warn or higher
			LOGGER.info("Failed to parse [{}] config [{}] to an integer. Falling"
					+ " back to [{}]. (Negative values treated as unlimited.)",
					new Object[]{MAX_BATCHES_PER_EXECUTION_CONFIG_NAME,
							maxBatchesStr, maxBatches});
		}
		return maxBatches;
	}

	private int getBatchSize() {
		String batchSizeStr =
				configService.getByNameNullOrDefaultValue(BATCH_SIZE_CONFIG_NAME);
		int batchSize = DEFAULT_BATCH_SIZE;
		try {
			batchSize = Integer.parseInt(batchSizeStr);
			LOGGER.debug("Using [{}] configured batch size of [{}]."
					+ " (Negative values treated as unlimited.)",
					BATCH_SIZE_CONFIG_NAME, batchSize);
		} catch ( NumberFormatException e ) {
			// only info b/c this thing is potentially called so frequently and
			// the defaults are probably fine, so we'd just be spamming the logs
			// with a warn or higher
			LOGGER.info("Failed to parse [{}] config [{}] to an integer. Falling"
					+ " back to [{}]. (Negative values treated as unlimited.)",
					new Object[]{BATCH_SIZE_CONFIG_NAME,
							batchSizeStr, batchSize});
		}
		return batchSize;
	}

}
