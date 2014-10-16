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
package org.jasig.ssp.service.impl;

import com.google.common.collect.Maps;
import org.jasig.ssp.dao.PersonProgramStatusDao;
import org.jasig.ssp.factory.PersonProgramStatusTOFactory;
import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.ProgramStatusChangeReason;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.jobqueue.AbstractJobExecutor;
import org.jasig.ssp.service.jobqueue.AbstractPersonSearchBasedJobExecutor;
import org.jasig.ssp.service.jobqueue.AbstractPersonSearchBasedJobQueuer;
import org.jasig.ssp.service.jobqueue.BasePersonSearchBasedJobExecutionState;
import org.jasig.ssp.service.jobqueue.JobExecutionResult;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.ProgramStatusChangeReasonService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;
import org.jasig.ssp.transferobject.PersonProgramStatusTO;
import org.jasig.ssp.transferobject.form.BulkEmailJobSpec;
import org.jasig.ssp.transferobject.form.BulkProgramStatusChangeJobSpec;
import org.jasig.ssp.transferobject.form.BulkProgramStatusChangeRequestForm;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * PersonProgramStatus service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
public class PersonProgramStatusServiceImpl extends
		AbstractPersonAssocAuditableService<PersonProgramStatus> implements
		PersonProgramStatusService, InitializingBean {

	private static final Logger OUTER_CLASS_LOGGER = LoggerFactory
			.getLogger(PersonProgramStatusServiceImpl.class);

	private static final ThreadLocal<Logger> CURRENT_LOGGER = new ThreadLocal<Logger>();

	public static final String BULK_PROGRAM_STATUS_CHANGE_JOB_EXECUTOR_NAME = "bulk-program-status-executor";
	private static final String BULK_PROGRAM_STATUS_CHANGE_BATCH_SIZE_CONFIG_NAME = "program_staus_bulk_change_batch_size";
	private static final String BULK_PROGRAM_STATUS_CHANGE_MAX_DLQ_SIZE_CONFIG_NAME = "program_staus_bulk_change_max_dlq_size";
	private static final String BULK_PROGRAM_STATUS_CHANGE_FAIL_ON_DLQ_OVERFLOW_CONFIG_NAME = "program_staus_bulk_change_fail_on_dlq_overflow";
	private static final String PERSON_PROGRAM_STATUS_ID_CREATED_FIELD_NAME = "personProgramStatusId";

	@Autowired
	private transient PersonProgramStatusDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient ProgramStatusService programStatusService;

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

	@Autowired
	private transient PersonProgramStatusTOFactory personProgramStatusTOFactory;

	@Autowired
	private transient ProgramStatusChangeReasonService serviceProgramStatusChangeReasonService;

	private static class BulkProgramStatusChangeJobExecutionState extends BasePersonSearchBasedJobExecutionState {
		public int personsSkippedCount; // b/c they're external or already have the requested status
	}

	private AbstractJobExecutor<BulkProgramStatusChangeJobSpec, BulkProgramStatusChangeJobExecutionState> bulkJobExecutor;

	private AbstractPersonSearchBasedJobQueuer<BulkProgramStatusChangeRequestForm, BulkProgramStatusChangeJobSpec> bulkJobQueuer;

	@Override
	public void afterPropertiesSet() throws Exception {
		initBulkProgramStatusTransitionJobExecutor();
		initBulkProgramStatusTransitionJobQueuer();
	}

	@Override
	protected PersonProgramStatusDao getDao() {
		return dao;
	}

	@Override
	public PersonProgramStatus create(
			final PersonProgramStatus personProgramStatus)
			throws ObjectNotFoundException, ValidationException {
		return doCreate(personProgramStatus);
	}

	// private so it can be reused in non-default transaction contexts
	private PersonProgramStatus doCreate(PersonProgramStatus personProgramStatus) throws ValidationException {
		expireActive(personProgramStatus.getPerson(), personProgramStatus);

		try {
			return getDao().save(personProgramStatus);
		} catch (final ConstraintViolationException exc) {
			throw new ValidationException(
					"Invalid data. See cause for list of violations.", exc);
		}
	}

	@Override
	public PersonProgramStatus save(final PersonProgramStatus obj)
			throws ObjectNotFoundException, ValidationException {
		// ensure expirationDate is not removed that would allow too many active
		if (obj.getExpirationDate() == null) {
			final PersonProgramStatus pps = getActiveExcluding(obj.getPerson(), obj);
			if (pps != null && pps.getId().equals(obj.getId())) {
				getCurrentLogger().warn("Can not un-expire this instance while another is active. See PersonProgramStatus with ID "
						+ pps.getId());
				throw new ValidationException(
						"Can not un-expire this instance while another is active. See PersonProgramStatus with ID "
								+ pps.getId());
			}
		}

		return super.save(obj);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		final PersonProgramStatus current = getDao().get(id);

		if (!ObjectStatus.INACTIVE.equals(current.getObjectStatus())
				|| current.getExpirationDate() == null) {
			// Object found and is not already deleted, set it and save change.
			current.setObjectStatus(ObjectStatus.INACTIVE);
			current.setExpirationDate(new Date());
			try {
				save(current);
			} catch (final ValidationException exc) {
				// expirationDate is always set above, so a ValidationException
				// should never be thrown.
				getCurrentLogger().error("ValidationException should not have occured with a delete of ID "
						+ id);
			}
		}
	}

	/**
	 * Expire the existing active PersonProgramStatus for this person, if any.
	 * 
	 * @param person
	 *            the person
	 * @param savingStatus
	 *            the status association currently being saved
	 * @throws ValidationException if the current list of statuses is in an
	 *   invalid state
	 */
	@Override
	public void expireActive(final Person person, PersonProgramStatus savingStatus)
	throws ValidationException {
		PersonProgramStatus pps = getActiveExcluding(person, savingStatus);
		if ( pps != null ) {
			pps.setExpirationDate(new Date());
			// make sure this runs before a subsequent insert (if any), else
			// the next Hib session flush might run the insert before the
			// update and we'd get two active statuses at the same time,
			// which will break db triggers
			dao.saveAndFlush(pps);
		}
	}

	@Override
	@Transactional(rollbackFor = {ObjectNotFoundException.class, IOException.class, ValidationException.class})
	public JobTO changeInBulk(BulkProgramStatusChangeRequestForm form) throws IOException, ObjectNotFoundException, ValidationException, SecurityException {
		return bulkJobQueuer.enqueueJob(form);
	}

	/**
	 * Intentionally private b/c we do not want this to participate in this service's "transactional-by-default"
	 * public interface. Its transaction is managed by the {@code JobExecutor} assumed to be invoking it.
	 *
	 */
	private Map<String, ?> changeSingleFromBulkRequest(ImmutablePersonIdentifiersTO personIds, BulkProgramStatusChangeRequestForm coreSpec)
			throws ValidationException, ObjectNotFoundException {
		final Map<String,UUID> rslt = Maps.newLinkedHashMap();
		final UUID personId = personIds.getId();
		if ( personId == null ) {
			getCurrentLogger().debug("Skipping Program Status change for person [{}] to status [{}] and reason [{}] " +
					"because that person has no operational record.", new Object[] { personIds,
					coreSpec.getProgramStatusSpec().getProgramStatusId(),
					coreSpec.getProgramStatusSpec().getProgramStatusChangeReasonId() });
			return rslt;
		}
		final Person person = personService.get(personId);
		if ( person == null ) {
			// Really not clear whether or not this should be treated differently from the "no UUID" specified case
			// above, but we go with the ObjectNotFoundException because a) it's consistent with handling of
			// bulk email requests and b) this isn't a business rule like the check above; it's bad input
			throw new ObjectNotFoundException(personId, Person.class.getName());
		}
		final PersonProgramStatus currentStatus = getCurrent(personIds.getId());
		if ( currentStatus == null || !(isChangeRedundant(coreSpec, currentStatus)) ) {
			return doChangeSingleFromBulkRequest(person, coreSpec, rslt);
		} else {
			getCurrentLogger().debug("Skipping Program Status change for person [{}] to status [{}] and reason [{}] " +
					"because the change would be redudant.", new Object[] { personIds,
					coreSpec.getProgramStatusSpec().getProgramStatusId(),
					coreSpec.getProgramStatusSpec().getProgramStatusChangeReasonId(),  });
			return rslt;
		}
	}

	private boolean isChangeRedundant(BulkProgramStatusChangeRequestForm coreSpec, PersonProgramStatus currentStatus) {
		if ( currentStatus == null ) {
			return false;
		}
		PersonProgramStatusTO programStatusSpec = coreSpec.getProgramStatusSpec();
		if ( !(programStatusSpec.getProgramStatusId().equals(currentStatus.getProgramStatus().getId())) ) {
			return false;
		}
		ProgramStatusChangeReason currentProgramStatusChangeReason = currentStatus.getProgramStatusChangeReason();
		if ( currentProgramStatusChangeReason == null ) {
			return programStatusSpec.getProgramStatusChangeReasonId() == null;
		}
		return currentProgramStatusChangeReason.getId().equals(programStatusSpec.getId());
	}

	private Map<String, UUID> doChangeSingleFromBulkRequest(Person person, BulkProgramStatusChangeRequestForm coreSpec, Map<String, UUID> rslt)
			throws ObjectNotFoundException, ValidationException {
		PersonProgramStatusTO programStatusSpec = coreSpec.getProgramStatusSpec();
		// Spec really should be considered immutable (and these changes won't be persisted (which is what we want)),
		// but this is the easiest way to go from a TO to a persistent model w/o either code copy/paste or gutting
		// the factory.
		programStatusSpec.setPersonId(person.getId());
		// Delay setting the effective date as long as possible since a date calcuated at the beginning of job
		// might be invalid by the time we actually get around to using it.
		programStatusSpec.setEffectiveDate(new Date());
		programStatusSpec.setExpirationDate(null); // any other usage just won't work the way you want
		final PersonProgramStatus model = personProgramStatusTOFactory.from(programStatusSpec);
		final PersonProgramStatus personProgramStatus = doCreate(model);
		rslt.put(PERSON_PROGRAM_STATUS_ID_CREATED_FIELD_NAME, personProgramStatus == null ? null : personProgramStatus.getId());
		return rslt;
	}

	private PersonProgramStatus getActiveExcluding(final Person forPerson,
												   final PersonProgramStatus exclude)
			throws ValidationException {
		return getActiveExcluding(forPerson.getId(), exclude);
	}

	private PersonProgramStatus getActiveExcluding(final UUID forPersonId,
												   final PersonProgramStatus exclude)
			throws ValidationException {
		// Cannot just ask dao for a single active record b/c the status
		// currently being saved might be flushed to the db as a side-effect
		// of the dao call. Have to pull everything back that's not expired,
		// then filter out the status we're currently working on.
		final List<PersonProgramStatus> active = dao.getActive(forPersonId);
		if ( active == null || active.isEmpty() ) {
			return null;
		}
		if ( exclude != null ) {
			active.remove(exclude);
		}
		if ( active.isEmpty() ) {
			return null;
		}
		if ( active.size() > 1 ) {
			throw new ValidationException(
					"More than one unexpired ProgramStatus for person '"
							+ forPersonId + "'. Unable to determine"
							+ " which of those to expire.");
		}
		return active.iterator().next();
	}


	@Override
	public PersonProgramStatus getCurrent(final UUID personId)
			throws ObjectNotFoundException, ValidationException {
		return getActiveExcluding(personId, null);
	}

	@Override
	public void setTransitionForStudent(@NotNull final Person person)
			throws ObjectNotFoundException, ValidationException {
		final ProgramStatus transitioned = programStatusService
				.get(ProgramStatus.TRANSITIONED_ID);

		// check if transition needs done
		final PersonProgramStatus current = getCurrent(person.getId());
		if (current != null && transitioned.equals(current.getProgramStatus())) {
			// current status is already "Transitioned" - nothing to be done
			return;
		}

		final PersonProgramStatus ps = new PersonProgramStatus();
		ps.setEffectiveDate(new Date());
		ps.setPerson(person);
		ps.setProgramStatus(transitioned);
		ps.setProgramStatusChangeReason(null);
		create(ps);
	}

	private void initBulkProgramStatusTransitionJobExecutor() {
		this.bulkJobExecutor = new AbstractPersonSearchBasedJobExecutor<BulkProgramStatusChangeJobSpec, BulkProgramStatusChangeJobExecutionState>(
				BULK_PROGRAM_STATUS_CHANGE_JOB_EXECUTOR_NAME, jobService, transactionManager, null, personSearchService, personSearchRequestFactory, configService
		) {
			private final Logger logger = LoggerFactory.getLogger(PersonProgramStatusServiceImpl.this.getClass().getName() + ".BulkProgramStatusChangeJobExecutor");

			/**
			 * The actual 'important' override... all the rest of the overrides are mostly boilerplate.
			 */
			@Override
			protected Map<String, ?> executeForSinglePerson(ImmutablePersonIdentifiersTO personIds, BulkProgramStatusChangeJobSpec executionSpec,
															BulkProgramStatusChangeJobExecutionState executionState, UUID jobId) throws ValidationException, ObjectNotFoundException {
				return changeSingleFromBulkRequest(personIds, executionSpec.getCoreSpec());
			}

			@Override
			protected JobExecutionResult<BulkProgramStatusChangeJobExecutionState> executeJobDeserialized(BulkProgramStatusChangeJobSpec executionSpec,
																										  BulkProgramStatusChangeJobExecutionState executionState, UUID jobId) {
				// TODO this copy pasted all over in these executor subclasses... abstract somehow
				try {
					PersonProgramStatusServiceImpl.this.setCurrentLogger(logger);
					return super.executeJobDeserialized(executionSpec, executionState, jobId);
				} finally {
					PersonProgramStatusServiceImpl.this.setCurrentLogger(null);
				}
			}

			@Override
			protected void recordSuccessful(ImmutablePersonIdentifiersTO personIds, Map<String, ?> results,
											BulkProgramStatusChangeJobSpec executionSpec,
											BulkProgramStatusChangeJobExecutionState executionState, UUID jobId) {
				super.recordSuccessful(personIds, results, executionSpec, executionState, jobId);
				if ( results.get(PERSON_PROGRAM_STATUS_ID_CREATED_FIELD_NAME) == null ) {
					executionState.personsSkippedCount++;
				}
			}

			@Override
			protected BulkProgramStatusChangeJobSpec deserializeJobSpecWithCheckedExceptions(String jobSpecStr) throws Exception {
				return getObjectMapper().readValue(jobSpecStr, BulkProgramStatusChangeJobSpec.class);
			}

			@Override
			protected BulkProgramStatusChangeJobExecutionState deserializeJobStateWithCheckedExceptions(String jobStateStr) throws Exception {
				return getObjectMapper().readValue(jobStateStr, BulkProgramStatusChangeJobExecutionState.class);
			}

			@Override
			protected String decorateProcessingCompleteLogMessage(String baseMsg, BulkProgramStatusChangeJobExecutionState executionState) {
				return new StringBuilder(baseMsg).append(" Persons skipped : [").append(executionState.personsSkippedCount)
						.append("].").toString();
			}

			@Override
			protected BulkProgramStatusChangeJobExecutionState newBulkEmailJobExecutionState() {
				return new BulkProgramStatusChangeJobExecutionState();
			}

			@Override
			protected Logger getCurrentLogger() {
				return PersonProgramStatusServiceImpl.this.getCurrentLogger();
			}

			@Override
			protected String getPageSizeConfigName() {
				return BULK_PROGRAM_STATUS_CHANGE_BATCH_SIZE_CONFIG_NAME;
			}

			@Override
			protected String getDlqSizeConfigName() {
				return BULK_PROGRAM_STATUS_CHANGE_MAX_DLQ_SIZE_CONFIG_NAME;
			}

			@Override
			protected String getFailOnSlqOverflowConfigName() {
				return BULK_PROGRAM_STATUS_CHANGE_FAIL_ON_DLQ_OVERFLOW_CONFIG_NAME;
			}
		};
		this.jobService.registerJobExecutor(this.bulkJobExecutor);
	}

	private void initBulkProgramStatusTransitionJobQueuer() {
		if ( this.bulkJobExecutor == null ) {
			// programmer error
			throw new IllegalStateException("Bulk ProgramStatus changing JobExecutor not yet initialized");
		}
		this.bulkJobQueuer = new AbstractPersonSearchBasedJobQueuer<BulkProgramStatusChangeRequestForm, BulkProgramStatusChangeJobSpec>
				(this.bulkJobExecutor, securityService, personSearchRequestFactory, personSearchService) {
			@Override
			protected BulkProgramStatusChangeRequestForm validateJobRequest(BulkProgramStatusChangeRequestForm jobRequest) throws ValidationException {
				validateBulkProgramStatusChangeRequest(jobRequest);
				return jobRequest;
			}

			@Override
			protected BulkProgramStatusChangeJobSpec newJobSpec(BulkProgramStatusChangeRequestForm jobRequest, Person currentSspPerson, PersonSearchRequest searchRequest, long searchResultCount) {
				return new BulkProgramStatusChangeJobSpec(jobRequest);
			}
		};
	}

	private void validateBulkProgramStatusChangeRequest(BulkProgramStatusChangeRequestForm jobRequest) throws ValidationException {
		final PersonProgramStatusTO programStatusSpec = jobRequest.getProgramStatusSpec();
		if ( programStatusSpec == null ) {
			throw new ValidationException("Must specify a target program status");
		}
		final UUID programStatusId = programStatusSpec.getProgramStatusId();
		if ( programStatusId == null ) {
			throw new ValidationException("Must specify a target program status ID");
		}
		try {
			final ProgramStatus programStatus = programStatusService.get(programStatusId);
			if ( programStatus == null ) {
				throw new ValidationException("Program status [" + programStatusId + "] not on file");
			}
		} catch ( ObjectNotFoundException e ) {
			throw new ValidationException("Program status [" + programStatusId + "] not on file", e);
		}
		final UUID programStatusChangeReasonId = programStatusSpec.getProgramStatusChangeReasonId();
		if ( programStatusChangeReasonId != null ) {
			try {
				final ProgramStatusChangeReason programStatusChangeReason = serviceProgramStatusChangeReasonService.get(programStatusChangeReasonId);
				if ( programStatusChangeReason == null ) {
					throw new ValidationException("Program status change reason [" + programStatusChangeReasonId + "] not on file");
				}
			} catch ( ObjectNotFoundException e ) {
				throw new ValidationException("Program status change reason [" + programStatusChangeReasonId + "] not on file", e);
			}
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