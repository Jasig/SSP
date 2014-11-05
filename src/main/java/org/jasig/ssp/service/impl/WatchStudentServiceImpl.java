/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.impl;

import com.google.common.collect.Maps;
import org.hibernate.exception.ConstraintViolationException;
import org.jasig.ssp.dao.DirectoryPersonSearchDao;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.dao.WatchStudentDao;
import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.factory.WatchStudentTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.WatchStudent;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.WatchStudentService;
import org.jasig.ssp.service.jobqueue.AbstractJobExecutor;
import org.jasig.ssp.service.jobqueue.AbstractPersonSearchBasedJobExecutor;
import org.jasig.ssp.service.jobqueue.AbstractPersonSearchBasedJobQueuer;
import org.jasig.ssp.service.jobqueue.BasePersonSearchBasedJobExecutionState;
import org.jasig.ssp.service.jobqueue.JobExecutionResult;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;
import org.jasig.ssp.transferobject.WatchStudentTO;
import org.jasig.ssp.transferobject.form.BulkWatchChangeJobSpec;
import org.jasig.ssp.transferobject.form.BulkWatchChangeRequestForm;
import org.jasig.ssp.transferobject.form.BulkWatchChangeTO;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.util.csvwriter.CaseloadCsvWriterHelper;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * WatchStudent service implementation
 */
@Service
public class WatchStudentServiceImpl
		extends AbstractPersonAssocAuditableService<WatchStudent>
		implements WatchStudentService, InitializingBean {

	private static final Logger OUTER_CLASS_LOGGER = LoggerFactory
			.getLogger(PersonProgramStatusServiceImpl.class);

	private static final ThreadLocal<Logger> CURRENT_LOGGER = new ThreadLocal<Logger>();

	public static final String BULK_WATCH_CHANGE_JOB_EXECUTOR_NAME = "bulk-watch-executor";
	private static final String BULK_WATCH_CHANGE_BATCH_SIZE_CONFIG_NAME = "watch_bulk_change_batch_size";
	private static final String BULK_WATCH_CHANGE_MAX_DLQ_SIZE_CONFIG_NAME = "watch_bulk_change_max_dlq_size";
	private static final String BULK_WATCH_CHANGE_FAIL_ON_DLQ_OVERFLOW_CONFIG_NAME = "watch_bulk_change_fail_on_dlq_overflow";
	// could be created or deleted watch IDs
	private static final String WATCH_ID_FIELD_NAME = "watchId";

	@Autowired
	private transient WatchStudentDao dao;
	@Autowired
	protected transient WatchStudentTOFactory watchStudentTOFactory;
	@Autowired
	private transient DirectoryPersonSearchDao directoryPersonDao;
	@Autowired
	private transient PersonService personService;
	@Autowired
	private transient SecurityService securityService;
	@Autowired
	private transient PersonSearchRequestTOFactory personSearchRequestFactory;
	@Autowired
	private transient PersonSearchService personSearchService;
	@Autowired
	private transient JobService jobService;
	@Autowired
	private transient ConfigService configService;
	@Autowired
	private transient PlatformTransactionManager transactionManager;

	private static class BulkWatchChangeJobExecutionState extends BasePersonSearchBasedJobExecutionState {
		public int personsSkippedCount; // b/c they're external or already have the requested status
	}

	private AbstractJobExecutor<BulkWatchChangeJobSpec, BulkWatchChangeJobExecutionState> bulkJobExecutor;

	private AbstractPersonSearchBasedJobQueuer<BulkWatchChangeRequestForm, BulkWatchChangeJobSpec> bulkJobQueuer;

	@Override
	protected WatchStudentDao getDao() {
		return dao;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initBulkWatchChangeJobExecutor();
		initBulkWatchChangeJobQueuer();
	}

	@Override
	public WatchStudent get(UUID watcherId, UUID studentId) {
		return dao.getStudentWatcherRelationShip(watcherId,studentId);
	}

	@Override
	public PagingWrapper<PersonSearchResult2> watchListFor(ProgramStatus programStatus, Person person,
			SortingAndPaging sAndP) {
		PersonSearchRequest form = new PersonSearchRequest();
		form.setWatcher(person);
		form.setMyWatchList(true);
		form.setProgramStatus(programStatus);
		form.setSortAndPage(sAndP);
		return directoryPersonDao.search(form);
	}

	@Override
	public WatchStudent create(final WatchStudent obj) throws ObjectNotFoundException,
			ValidationException, ObjectExistsException {
		try {
			return super.create(obj);
		} catch (final ConstraintViolationException e) {
			if ( e.getConstraintName().equalsIgnoreCase("watch_student_watcher_id_student_id_key") ) {
				final LinkedHashMap<String, UUID> lookupKeys = Maps.newLinkedHashMap();
				lookupKeys.put("watcherId", obj.getPerson().getId());
				lookupKeys.put("studentId", obj.getStudent().getId());
				throw new ObjectExistsException(WatchStudent.class.getName(), lookupKeys, e);
			}
			throw new ObjectExistsException("Constraint violation trying to store a WatchStudent record", e);
		}
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final WatchStudent current = getDao().get(id);
		dao.delete(current);
	}

	@Override
	// Would really rather make this non-transactional, but @Transactional is being set
	// well above us in the inheritance hierarchy so the best we can do is readOnly
	@Transactional(readOnly = true)
	public void exportWatchListFor(PrintWriter writer,
			ProgramStatus programStatus, Person person,
			SortingAndPaging buildSortAndPage) throws IOException {
		PersonSearchRequest form = new PersonSearchRequest();
		form.setWatcher(person);
		form.setMyWatchList(true);
		form.setProgramStatus(programStatus);
		form.setSortAndPage(buildSortAndPage);
		directoryPersonDao.exportableSearch(new CaseloadCsvWriterHelper(writer), form);
	}

	@Override
	public Long watchListCountFor(ProgramStatus programStatus, Person person,
			SortingAndPaging buildSortAndPage) {
		PersonSearchRequest form = new PersonSearchRequest();
		form.setWatcher(person);
		form.setMyWatchList(true);
		form.setProgramStatus(programStatus);
		form.setSortAndPage(buildSortAndPage);
		return directoryPersonDao.getCaseloadCountFor(form, buildSortAndPage);
	}

	@Override
	@Transactional(rollbackFor = {ObjectNotFoundException.class, IOException.class, ValidationException.class})
	public JobTO changeInBulk(BulkWatchChangeRequestForm form) throws IOException, ObjectNotFoundException, ValidationException {
		return bulkJobQueuer.enqueueJob(form);
	}

	/**
	 * Intentionally private b/c we do not want this to participate in this service's "transactional-by-default"
	 * public interface. Its transaction is managed by the {@code JobExecutor} assumed to be invoking it.
	 *
	 */
	private Map<String, ?> changeSingleFromBulkRequest(ImmutablePersonIdentifiersTO personIds, BulkWatchChangeRequestForm coreSpec)
			throws ValidationException, ObjectNotFoundException {
		final Map<String,UUID> rslt = Maps.newLinkedHashMap();
		final UUID targetPersonId = personIds.getId();
		final UUID watcherId = coreSpec.getWatchSpec().getWatcherId();
		final BulkWatchChangeTO.BulkWatchOperation operation = coreSpec.getWatchSpec().getOperation();
		if ( targetPersonId == null ) {
			getCurrentLogger().debug("Skipping Watch change [{}] for target person [{}] and watcher [{}] " +
					"because that person has no operational record.", new Object[] { operation, personIds, watcherId });
			return rslt;
		}
		WatchStudent watch = this.get(watcherId, targetPersonId);
		if ( watch == null || watch.getObjectStatus() != ObjectStatus.ACTIVE ) {
			if ( operation == BulkWatchChangeTO.BulkWatchOperation.UNWATCH ) {
				getCurrentLogger().debug("Skipping UNWATCH operation for target person [{}] and watcher [{}] " +
						"because the change would be redudant.", personIds, watcherId );
				watch = null;
			} else {
				if ( watch != null ) {
					watch.setObjectStatus(ObjectStatus.ACTIVE);
					// Was hard to know whether to implement as a pure skip or just freshen the date b/c we don't
					// actually do anything with the date. Decided to go ahead and freshen so its a little bit easier
					// to correlate watch records with watch requests
					watch.setWatchDate(new Date());
					watch = save(watch);
				} else {
					final WatchStudentTO watchTO = new WatchStudentTO();
					watchTO.setStudentId(targetPersonId);
					watchTO.setWatcherId(watcherId);
					watchTO.setWatchDate(new Date());
					// will raise ObjectNotFoundException if either target or watcher not found
					watch = watchStudentTOFactory.from(watchTO);
					watch = this.create(watch);
				}
			}
		} else {
			if ( operation == BulkWatchChangeTO.BulkWatchOperation.UNWATCH ) {
				this.delete(watch.getId());
			} else {
				// Was hard to know whether to implement as a pure skip or just freshen the date b/c we don't actually
				// do anything with the date. Decided to go ahead and freshen so its a little bit easier to correlate
				// watch records with watch requests. (Same as above)
				watch.setWatchDate(new Date());
				watch = save(watch);
			}
		}
		rslt.put(WATCH_ID_FIELD_NAME, watch == null ? null : watch.getId());
		return rslt;
	}

	private void initBulkWatchChangeJobExecutor() {
		this.bulkJobExecutor = new AbstractPersonSearchBasedJobExecutor<BulkWatchChangeJobSpec, BulkWatchChangeJobExecutionState>(
				BULK_WATCH_CHANGE_JOB_EXECUTOR_NAME, jobService, transactionManager, null, personSearchService, personSearchRequestFactory, configService
		) {
			private final Logger logger = LoggerFactory.getLogger(WatchStudentServiceImpl.this.getClass().getName() + ".BulkWatchChangeJobExecutor");

			/**
			 * The actual 'important' override... all the rest of the overrides are mostly boilerplate.
			 */
			@Override
			protected Map<String, ?> executeForSinglePerson(ImmutablePersonIdentifiersTO personIds, BulkWatchChangeJobSpec executionSpec,
															BulkWatchChangeJobExecutionState executionState, UUID jobId) throws ValidationException, ObjectNotFoundException {
				return changeSingleFromBulkRequest(personIds, executionSpec.getCoreSpec());
			}

			@Override
			protected JobExecutionResult<BulkWatchChangeJobExecutionState> executeJobDeserialized(BulkWatchChangeJobSpec executionSpec,
																								  BulkWatchChangeJobExecutionState executionState, UUID jobId) {
				// TODO this copy pasted all over in these executor subclasses... abstract somehow
				try {
					WatchStudentServiceImpl.this.setCurrentLogger(logger);
					return super.executeJobDeserialized(executionSpec, executionState, jobId);
				} finally {
					WatchStudentServiceImpl.this.setCurrentLogger(null);
				}
			}

			@Override
			protected void recordSuccessful(ImmutablePersonIdentifiersTO personIds, Map<String, ?> results,
											BulkWatchChangeJobSpec executionSpec,
											BulkWatchChangeJobExecutionState executionState, UUID jobId) {
				super.recordSuccessful(personIds, results, executionSpec, executionState, jobId);
				if ( results.get(WATCH_ID_FIELD_NAME) == null ) {
					executionState.personsSkippedCount++;
				}
			}

			@Override
			protected BulkWatchChangeJobSpec deserializeJobSpecWithCheckedExceptions(String jobSpecStr) throws Exception {
				return getObjectMapper().readValue(jobSpecStr, BulkWatchChangeJobSpec.class);
			}

			@Override
			protected BulkWatchChangeJobExecutionState deserializeJobStateWithCheckedExceptions(String jobStateStr) throws Exception {
				return getObjectMapper().readValue(jobStateStr, BulkWatchChangeJobExecutionState.class);
			}

			@Override
			protected String decorateProcessingCompleteLogMessage(String baseMsg, BulkWatchChangeJobExecutionState executionState) {
				return new StringBuilder(baseMsg).append(" Persons skipped : [").append(executionState.personsSkippedCount)
						.append("].").toString();
			}

			@Override
			protected BulkWatchChangeJobExecutionState newJobExecutionState() {
				return new BulkWatchChangeJobExecutionState();
			}

			@Override
			protected Logger getCurrentLogger() {
				return WatchStudentServiceImpl.this.getCurrentLogger();
			}

			@Override
			protected String getPageSizeConfigName() {
				return BULK_WATCH_CHANGE_BATCH_SIZE_CONFIG_NAME;
			}

			@Override
			protected String getDlqSizeConfigName() {
				return BULK_WATCH_CHANGE_MAX_DLQ_SIZE_CONFIG_NAME;
			}

			@Override
			protected String getFailOnSlqOverflowConfigName() {
				return BULK_WATCH_CHANGE_FAIL_ON_DLQ_OVERFLOW_CONFIG_NAME;
			}
		};
		this.jobService.registerJobExecutor(this.bulkJobExecutor);
	}

	private void initBulkWatchChangeJobQueuer() {
		if ( this.bulkJobExecutor == null ) {
			// programmer error
			throw new IllegalStateException("Bulk Watch changing JobExecutor not yet initialized");
		}
		this.bulkJobQueuer = new AbstractPersonSearchBasedJobQueuer<BulkWatchChangeRequestForm, BulkWatchChangeJobSpec>
				(this.bulkJobExecutor, securityService, personSearchRequestFactory, personSearchService) {
			@Override
			protected BulkWatchChangeRequestForm validateJobRequest(BulkWatchChangeRequestForm jobRequest) throws ValidationException {
				validateBulkWatchChangeRequest(jobRequest);
				return jobRequest;
			}

			@Override
			protected BulkWatchChangeJobSpec newJobSpec(BulkWatchChangeRequestForm jobRequest, Person currentSspPerson,
														PersonSearchRequest searchRequest, long searchResultCount) {
				return new BulkWatchChangeJobSpec(jobRequest);
			}
		};
	}

	private void validateBulkWatchChangeRequest(BulkWatchChangeRequestForm jobRequest) throws ValidationException {
		final BulkWatchChangeTO watchSpec = jobRequest.getWatchSpec();
		if ( watchSpec == null ) {
			throw new ValidationException("Must specify a target watch state");
		}
		final BulkWatchChangeTO.BulkWatchOperation operation = watchSpec.getOperation();
		if ( operation == null ) {
			throw new ValidationException("Must specify a watch change operation");
		}
		if ( watchSpec.getWatcherId() == null ) {
			throw new ValidationException("Must specify a watcher ID");
		}
		Person watcher = null;
		try {
			watcher = personService.get(watchSpec.getWatcherId());
		} catch ( ObjectNotFoundException e ) {
			// nothing to do
		}
		if ( watcher == null ) {
			throw new ValidationException("Watcher ID [" + watchSpec.getWatcherId() + "] not on file");
		}
	}

	private Logger getCurrentLogger() {
		return CURRENT_LOGGER.get() == null ? OUTER_CLASS_LOGGER : CURRENT_LOGGER.get();
	}

	private void setCurrentLogger(Logger logger) {
		CURRENT_LOGGER.set(logger);
	}

	private static enum EmailVolume {
		SINGLE,BULK
	}


}