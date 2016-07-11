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
package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.PersonCourseStatusDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonCourseStatus;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.service.external.SpecialServiceGroupCourseWithdrawalAdvisorEmailTask;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.jasig.ssp.transferobject.messagetemplate.CoachPersonLiteMessageTemplateTO;
import org.jasig.ssp.transferobject.messagetemplate.CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO;
import org.jasig.ssp.transferobject.messagetemplate.StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class SpecialServiceGroupCourseWithdrawalAdvisorEmailTaskImpl implements SpecialServiceGroupCourseWithdrawalAdvisorEmailTask {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialServiceGroupCourseWithdrawalAdvisorEmailTaskImpl.class);

	private static final Class<Pair<Long, Long>> BATCH_RETURN_TYPE =
			(Class<Pair<Long, Long>>) new Pair<Long,Long>(null,null).getClass();

	private static final int DEFAULT_MAX_BATCHES_PER_EXECUTION = -1; // unlimited
	private static final int DEFAULT_BATCH_SIZE = 10;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient ConfigService configService;

	@Autowired
	private transient PersonSearchService personSearchService;

	@Autowired
	private transient ExternalStudentTranscriptCourseService externalStudentTranscriptCourseService;

    @Autowired
    private transient SpecialServiceGroupService specialServiceGroupService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private transient MessageTemplateService messageTemplateService;

	@Autowired
	private transient PersonCourseStatusDao personCourseStatusDao;

	@Autowired
	private WithTransaction withTransaction;

	public Class<Pair<Long, Long>> getBatchExecReturnType() {
		return BATCH_RETURN_TYPE;
	}

	private transient long nextCoachIndex = 0;

	// intentionally not transactional... this is the main loop, each iteration
	// of which should be its own transaction.
	@Override
	public void exec(CallableExecutor<Pair<Long, Long>> batchExecutor) {
		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning Special Service Group Course Withdrawal Advisor Email Task because of thread interruption");
			return;
		}
		int recordsProcessed = 0;
		int batch = 0;
		Exception error = null;
		while ( true ) {
			final SortingAndPaging sAndP = SortingAndPaging.createForSingleSortWithPaging(
					ObjectStatus.ACTIVE,
					new BigDecimal(nextCoachIndex).intValueExact(),  // API mismatch... see more comments below
					DEFAULT_BATCH_SIZE,
					"id",
					SortDirection.ASC.toString(), null);
			Pair<Long,Long> processedOfTotal = null;

			try {
				if ( batchExecutor == null ) {
					processedOfTotal = processCoachInTransaction(sAndP);
				} else {
					processedOfTotal = batchExecutor.exec(new Callable<Pair<Long, Long>>() {
						@Override
						public Pair<Long, Long> call() throws Exception {
							return processCoachInTransaction(sAndP);
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
						LOGGER.error("Abandoning Special Service Group Course Withdrawal Advisor Email Task at"
										+ " position [{}] and batch [{}] because of a"
										+ " processing error. Will resume at that"
										+ " position at the next execution.",
								new Object[] {nextCoachIndex, batch - 1, error });
						break;
					}
					if ( Thread.currentThread().isInterrupted() ) {
						LOGGER.error("Abandoning Special Service Group Course Withdrawal Advisor Email Task at"
										+ " position [{}] and batch [{}] because of an"
										+ " InterruptionException. Will resume at that"
										+ " position at the next execution.",
								nextCoachIndex, batch - 1);
						break;
					}
					// programmer error, no clue what to do so let the NPE's fly...
				}

				nextCoachIndex += processedOfTotal.getFirst();
				recordsProcessed += processedOfTotal.getFirst();

				LOGGER.info("Processed [{}] of [{}] candidate coach records"
								+ " as of batch [{}] of [{}]. Total records processed [{}].",
						new Object[] {nextCoachIndex, processedOfTotal.getSecond(),
								batch, DEFAULT_MAX_BATCHES_PER_EXECUTION, recordsProcessed });

				if ( processedOfTotal.getFirst() == 0 ) {
					// shouldn't happen but want to guard against endless loops
					LOGGER.debug("Appear to be more records to process but"
							+ " last batch processed zero records. Exiting"
							+ " Special Service Group Course Withdrawal Advisor Email Task.");
					nextCoachIndex = 0;
					break;
				}

				if ( DEFAULT_MAX_BATCHES_PER_EXECUTION > 0 && batch >= DEFAULT_MAX_BATCHES_PER_EXECUTION ) {
					LOGGER.debug("No more batches allowed for this execution."
							+ " Exiting person sync task. Will resume at"
							+ " index [{}] on next execution.", nextCoachIndex);
					break;
				}

				if ( nextCoachIndex >= processedOfTotal.getSecond() ) {
					nextCoachIndex = 0;
					LOGGER.debug("Reached the end of the list of candidate"
							+ " coaches for sync. More batches are allowed, so"
							+ " starting over at index 0.");
					// no break!!
				}

				if ( recordsProcessed >= processedOfTotal.getSecond() ) {
					LOGGER.debug("More batches allowed, but all candidate" +
							" coach records have already been processed"
							+ " in this execution. Will resume at index"
							+ " [{}] on next execution.", nextCoachIndex);
					break;
				}

				// Mismatch between the PagedResponse and SortingAndPaging
				// APIs mean we can't actually deal with total result sets
				// larger than Integer.MAX_VALUE
				if ( nextCoachIndex > Integer.MAX_VALUE ) {
					LOGGER.warn("Cannot process more than {} total persons,"
									+ " even across executions. Abandoning and"
									+ " resetting sync task.",
							Integer.MAX_VALUE);
					nextCoachIndex = 0;
					break;
				}
			}
		}
	}

	protected Pair<Long, Long> processCoachInTransaction(final SortingAndPaging sAndP) throws Exception {
		return withTransaction.withNewTransaction(new Callable<Pair<Long, Long>>() {
			@Override
			public Pair<Long, Long> call() throws Exception {
				return processCoach(sAndP);
			}
		});
	}

	public Pair<Long, Long> processCoach(SortingAndPaging sAndP) {
		long coachCnt = 0;
		final PagingWrapper<Person> coaches = personService.getAllAssignedCoaches(sAndP);
		for (final Person coach : coaches.getRows()) {
			coachCnt++;
			String courseEnrollmentStatusCodesChanges = configService.getByNameEmpty("course_enrollment_status_code_changes").trim();
			if (courseEnrollmentStatusCodesChanges.equals("")) {
				LOGGER.info("Special Service Group Course Withdrawal Advisor Email Task will not execute because the property course_enrollment_status_code_changes is not set");
				return new Pair(0L, 0L);
			}

			String[] courseEnrollmentStatusCodesChangesArray = courseEnrollmentStatusCodesChanges.split(",");

			LOGGER.info("BEGIN : SpecialServiceGroupCourseWithdrawalAdvisorEmailTask");

			Calendar startTime = Calendar.getInstance();

			List<SpecialServiceGroup> specialServiceGroups = getSpecialServiceGroupsToNotify();
			if (specialServiceGroups.size() > 0) {
				List<StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO> students = new ArrayList<>();
				for (PersonSearchResult2 personSearchResult2 : getAllStudentsForCoach(coach, specialServiceGroups)) {
					try {
						List<CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO> courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs = new ArrayList<>();
						Person person = personService.getBySchoolId(personSearchResult2.getSchoolId(), false);
						Collection<PersonCourseStatus> personCourseStatuses = personCourseStatusDao.getAllForPerson(person).getRows();
						for (ExternalStudentTranscriptCourse externalStudentTranscriptCourse : externalStudentTranscriptCourseService.getTranscriptsBySchoolId(person.getSchoolId())) {
							PersonCourseStatus personCourseStatus = getPersonCourseStatus(externalStudentTranscriptCourse, personCourseStatuses);
							if (personCourseStatus != null) {
								if (isCourseWithdrawn(externalStudentTranscriptCourse, personCourseStatus, courseEnrollmentStatusCodesChangesArray)) {
									courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs
											.add(new CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO(externalStudentTranscriptCourse, personCourseStatus.getStatusCode()));
								}
								if (!personCourseStatus.getStatusCode().equals(externalStudentTranscriptCourse.getStatusCode())) {
									personCourseStatus.setPreviousStatusCode(personCourseStatus.getStatusCode());
									personCourseStatus.setStatusCode(externalStudentTranscriptCourse.getStatusCode());
									personCourseStatusDao.save(personCourseStatus);
								}
							} else {
								personCourseStatus = createPersonCourseStatus(externalStudentTranscriptCourse, person);
								personCourseStatusDao.save(personCourseStatus);
							}
						}
						if (courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs.size() > 0) {
							students.add(new StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO(person, courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs));
						}
					} catch (ObjectNotFoundException e) {
						LOGGER.info("Person record not found for search result person id: " + personSearchResult2.getId());
					}
				}
				if (students.size() > 0) {
					sendEmail(coach, students);
				}
			} else {
				LOGGER.info("No special service groups to notify on withdrawal found in SpecialServiceGroupCourseWithdrawalAdvisorEmailTask");
			}

			Calendar endTime = Calendar.getInstance();
			LOGGER.info("SpecialServiceGroupCourseWithdrawalAdvisorEmailTask REPORT RUNTIME: " + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) + " ms.");
			LOGGER.info("END : SpecialServiceGroupCourseWithdrawalAdvisorEmailTask");
		}
		return new Pair(coachCnt, coaches.getResults());
	}

	private PersonCourseStatus createPersonCourseStatus(ExternalStudentTranscriptCourse externalStudentTranscriptCourse, Person person) {
		PersonCourseStatus personCourseStatus = new PersonCourseStatus();
		personCourseStatus.setPerson(person);
		personCourseStatus.setObjectStatus(ObjectStatus.ACTIVE);
		personCourseStatus.setTermCode(externalStudentTranscriptCourse.getTermCode());
		personCourseStatus.setFormattedCourse(externalStudentTranscriptCourse.getFormattedCourse());
		personCourseStatus.setSectionCode(externalStudentTranscriptCourse.getSectionCode());
		personCourseStatus.setStatusCode(externalStudentTranscriptCourse.getStatusCode());
		return personCourseStatus;
	}

	private PersonCourseStatus getPersonCourseStatus(ExternalStudentTranscriptCourse externalStudentTranscriptCourse, Collection<PersonCourseStatus> personCourseStatuses) {
		for (PersonCourseStatus personCourseStatus : personCourseStatuses) {
			if (externalStudentTranscriptCourse.getTermCode().equals(personCourseStatus.getTermCode()) &&
				externalStudentTranscriptCourse.getFormattedCourse().equals(personCourseStatus.getFormattedCourse()) &&
				externalStudentTranscriptCourse.getSectionCode().equals(personCourseStatus.getSectionCode())) {
				return personCourseStatus;
			}
		}
		return null;
	}

	private List<SpecialServiceGroup> getSpecialServiceGroupsToNotify() {
		try {
			return specialServiceGroupService.getByNotifyOnWithdraw(true);
		} catch (ObjectNotFoundException e) {
			LOGGER.info("Special Service Group Course Withdrawal Advisor Email Task has no special service groups to notify.");
		}
		return null;
	}

	private Collection<PersonSearchResult2> getAllStudentsForCoach (Person coach, List<SpecialServiceGroup> specialServiceGroups) {
		PersonSearchRequest personSearchRequest = new PersonSearchRequest();
		personSearchRequest.setCoach(coach);
		personSearchRequest.setSpecialServiceGroup(specialServiceGroups);
		personSearchRequest.setPersonTableType(PersonSearchRequest.PERSON_TABLE_TYPE_SSP_ONLY);
		personSearchRequest.setSortAndPage(SortingAndPaging
				.createForSingleSortWithPaging(ObjectStatus.ALL, 0, -1, "dp.lastName",
						SortDirection.ASC.toString(), null));
		return personSearchService.searchPersonDirectory(personSearchRequest).getRows();
	}

	private boolean isCourseWithdrawn(ExternalStudentTranscriptCourse externalStudentTranscriptCourse, PersonCourseStatus personCourseStatus, String[] courseEnrollmentStatusCodesChanges) {
		for (String courseEnrollmentStatusCodeChange : courseEnrollmentStatusCodesChanges) {
			if ((personCourseStatus.getStatusCode() + "|" + externalStudentTranscriptCourse.getStatusCode()).equals(courseEnrollmentStatusCodeChange.trim()) ) {
				return true;
			}
		}
		return false;
	}

	private void sendEmail(Person coach, List<StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO> students) {
		SubjectAndBody subjectAndBody = messageTemplateService.createSpecialServiceGroupCourseWithdrawalCoachMessage(new CoachPersonLiteMessageTemplateTO(coach), students);
		try {
			messageService.createMessage(coach.getPrimaryEmailAddress(), null, subjectAndBody);
		} catch ( ObjectNotFoundException | ValidationException e) {
			LOGGER.error("Failed to send Special Service Group Course Withdrawal Advisor Email to coach at address {}",
					new Object[] {coach.getPrimaryEmailAddress(), e});
		}
	}
}