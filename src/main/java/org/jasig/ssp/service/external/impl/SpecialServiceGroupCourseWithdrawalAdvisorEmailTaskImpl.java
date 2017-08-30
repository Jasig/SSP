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

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;


@Service
public class SpecialServiceGroupCourseWithdrawalAdvisorEmailTaskImpl implements SpecialServiceGroupCourseWithdrawalAdvisorEmailTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpecialServiceGroupCourseWithdrawalAdvisorEmailTaskImpl.class);

	private static final Class<Pair<Long, Long>> BATCH_RETURN_TYPE = (Class<Pair<Long, Long>>) new Pair<Long,Long>(null,null).getClass();

	private static final int DEFAULT_MAX_BATCHES_PER_EXECUTION = -1; // unlimited
	private static final int DEFAULT_BATCH_SIZE = 10;

	private static final String CONFIG_ADD_STUDENT_SSG_COURSE_WITHDRAWAL_EMAIL = "special_service_group_email_course_withdrawal_add_student_to_ssp";
	private static final String CONFIG_COURSE_ENROLLMENT_STATUS_CODE_CHANGES = "course_enrollment_status_code_changes";

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

    @Autowired
    protected transient SessionFactory sessionFactory;

	public Class<Pair<Long, Long>> getBatchExecReturnType() {
		return BATCH_RETURN_TYPE;
	}

	private transient long nextCoachIndex = 0;
	private transient String courseEnrollmentStatusCodesChanges;
	private transient String[] courseEnrollmentStatusCodesChangesArray;


	// intentionally not transactional... this is the main loop, each iteration
	// of which should be its own transaction.
	@Override
	public void exec(CallableExecutor<Pair<Long, Long>> batchExecutor) {
		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning Special Service Group Course Withdrawal Advisor Email Task because of thread interruption");
			return;
		}

		LOGGER.info("BEGIN : SpecialServiceGroupCourseWithdrawalAdvisorEmailTask");

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
							+ " Exiting Special Service Group Course Withdrawal Advisor Email Task. Will resume at"
							+ " index [{}] on next execution.", nextCoachIndex);
					break;
				}

				if ( nextCoachIndex >= processedOfTotal.getSecond() ) {
					nextCoachIndex = 0;
					LOGGER.debug("Reached the end of the list of candidate"
							+ " coaches for Special Service Group Course Withdrawal Advisor Email Task. " +
							"More batches are allowed, so starting over at index 0.");
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
									+ " resetting Special Service Group Course Withdrawal Advisor Email Task.",
							Integer.MAX_VALUE);
					nextCoachIndex = 0;
					break;
				}
			}
		}
		LOGGER.info("END : SpecialServiceGroupCourseWithdrawalAdvisorEmailTask");
	}

	protected Pair<Long, Long> processCoachInTransaction(final SortingAndPaging sAndP) throws Exception {
		return withTransaction.withNewTransaction(new Callable<Pair<Long, Long>>() {
			@Override
			public Pair<Long, Long> call() throws Exception {
				return processCoach(sAndP);
			}
		});
	}

	public Pair<Long, Long> processCoach(SortingAndPaging sAndP) throws InterruptedException {

        final Calendar startTime = Calendar.getInstance();
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.COMMIT);

        if (StringUtils.isBlank(courseEnrollmentStatusCodesChanges)) {
            courseEnrollmentStatusCodesChanges = configService.getByNameEmpty(CONFIG_COURSE_ENROLLMENT_STATUS_CODE_CHANGES).trim();

            if (StringUtils.isBlank(courseEnrollmentStatusCodesChanges)) {
                courseEnrollmentStatusCodesChanges = "NULL";
            } else {
                courseEnrollmentStatusCodesChangesArray = courseEnrollmentStatusCodesChanges.split(",");
            }
        }

        if (courseEnrollmentStatusCodesChanges.equals("NULL")) {
            LOGGER.info("Special Service Group Course Withdrawal Advisor Email Task will not execute because the property course_enrollment_status_code_changes is not set");
            return new Pair(0L, 0L);
        }

        final List<SpecialServiceGroup> specialServiceGroups = getSpecialServiceGroupsToNotify();
        if (CollectionUtils.isEmpty(specialServiceGroups)) {
            LOGGER.info("No special service groups to notify on withdrawal found in SpecialServiceGroupCourseWithdrawalAdvisorEmailTask");
            return new Pair(0L, 0L);
        }

        if ( Thread.currentThread().isInterrupted() ) {
            LOGGER.info("Abandoning Special Service Group Course Withdrawal task because of thread interruption");
            throw new InterruptedException();
        }

        LOGGER.info("Special Service Group Course Withdrawal task selecting [{}] records starting at [{}]", sAndP.getMaxResults(), nextCoachIndex);
		final PagingWrapper<Person> coaches = personService.getAllAssignedCoaches(sAndP);

        if ( coaches.getRows().isEmpty() ) {
            LOGGER.info("Special Service Group Course Withdrawal task found 0 records starting at [{}]", nextCoachIndex);
            return new Pair(0L, coaches.getResults());
        }

        if ( Thread.currentThread().isInterrupted() ) {
            LOGGER.info("Abandoning Special Service Group Course Withdrawal task because of thread interruption");
            throw new InterruptedException();
        }

        long coachCnt = 0;
		for (final Person coach : coaches.getRows()) {
            if ( Thread.currentThread().isInterrupted() ) {
                LOGGER.info("Abandoning Special Service Group Course Withdrawal task because of thread interruption on person {}",
                        coach.getSchoolId());
                throw new InterruptedException();
            }

            coachCnt++;
            LOGGER.debug("Processing SSG student withdrawals for coach: {}", coach.getSchoolId());

		    if (StringUtils.isBlank(coach.getPrimaryEmailAddress()) && StringUtils.isBlank(coach.getSecondaryEmailAddress())) {
                LOGGER.info("No student withdrawals will be processed for coach: {} because the coach's email addresses are empty!", coach.getSchoolId());
		    } else {

                final List<StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO> studentWithdrawalEmails = Lists.newArrayList();
                final Collection<PersonSearchResult2> coachAssignedStudents = getAllStudentsForCoach(coach, specialServiceGroups, configService.getByNameOrDefaultValue(CONFIG_ADD_STUDENT_SSG_COURSE_WITHDRAWAL_EMAIL));
                if (CollectionUtils.isEmpty(coachAssignedStudents)) {
                    LOGGER.debug("No students were found that were assigned to coach {}", coach.getSchoolId());
                } else {
                    LOGGER.debug("Processing {} students for coach {}", coachAssignedStudents.size(), coach.getSchoolId());
                }

                int studentCount = 0;
                for (PersonSearchResult2 personSearchResult2 : coachAssignedStudents) {
                    LOGGER.trace("Processing student: {}", personSearchResult2.getSchoolId());

                    if ( Thread.currentThread().isInterrupted() ) {
                        LOGGER.info("Abandoning Special Service Group Course Withdrawal task because of thread interruption for coach {} on student {}",
                                coach.getSchoolId(), personSearchResult2.getSchoolId());
                        throw new InterruptedException();
                    }

                    Person person = null;
                    boolean studentAddedToSSP = false;
                    try {
                        person = personService.getInternalOrExternalPersonBySchoolIdLite(personSearchResult2.getSchoolId());

                        if (person.getId() == null) {
                            LOGGER.trace("Internal person couldn't be found for {} attempting to add external student.", personSearchResult2.getSchoolId());
                            person = personService.getInternalOrExternalPersonBySchoolId(personSearchResult2.getSchoolId(), true, true); //valid use since adding external to ssp
                            studentAddedToSSP = true;
                        }

                    } catch (ObjectNotFoundException e) {
                        LOGGER.info("Person record not found for search result person id: " + personSearchResult2.getId());
                    }

                    if (person == null || StringUtils.isBlank(person.getSchoolId())) {
                        LOGGER.info("Failed to retrieve or add internal person by school_id: {}", personSearchResult2.getSchoolId());
                        break;
                    } else {
                        LOGGER.trace("Found person object for search result {} with internal school_id {}", personSearchResult2.getSchoolId(), person.getSchoolId());
                    }

                    LOGGER.debug("Evaluating courses for student: {}...", person.getSchoolId());
                    final Collection<PersonCourseStatus> personCourseStatuses = personCourseStatusDao.getAllForPerson(person);
                    final List<ExternalStudentTranscriptCourse> transcriptCourses = externalStudentTranscriptCourseService.getTranscriptsBySchoolId(person.getSchoolId());

                    if ( Thread.currentThread().isInterrupted() ) {
                        LOGGER.info("Abandoning Special Service Group Course Withdrawal task because of thread interruption for coach {} on student {}",
                                coach.getSchoolId(), personSearchResult2.getSchoolId());
                        throw new InterruptedException();
                    }

                    if (CollectionUtils.isEmpty(transcriptCourses)) {
                        LOGGER.debug("No transcript courses found, ssg course history will not be recorded!");
                    } else {
                        addOrUpdatePersonCourseHistory(person, transcriptCourses, personCourseStatuses, studentAddedToSSP, studentWithdrawalEmails);
                    }
                    studentCount++;
                }

                LOGGER.trace("Coach: " + coach.getSchoolId() + " complete! Processed: " + studentCount + " out of " + coachAssignedStudents.size() + " students.");

                if (studentWithdrawalEmails.size() > 0) {
                    LOGGER.trace("Sending Special Service Group Course Withdrawal Emails on {} students for coach {}...", studentWithdrawalEmails.size(), coach.getSchoolId());
                    sendEmail(coach, studentWithdrawalEmails);
                }
            }
		} //end loop for coaches

        final Calendar endTime = Calendar.getInstance();
        LOGGER.info("SpecialServiceGroupCourseWithdrawalAdvisorEmailTask BATCH RUNTIME: " + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) + " ms.");

        return new Pair(coachCnt, coaches.getResults());
	}

	private void addOrUpdatePersonCourseHistory(final Person student,
                    final List<ExternalStudentTranscriptCourse> transcriptCourses,
                    final Collection<PersonCourseStatus> personCourseStatuses, final boolean studentAddedToSSP,
                    final List<StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO> studentWithdrawalEmails) {

	    LOGGER.trace("Processing {} transcripts for course status and history...", transcriptCourses.size());
	    final List<CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO> courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs = Lists.newArrayList();
        for (ExternalStudentTranscriptCourse externalStudentTranscriptCourse : transcriptCourses) {
            PersonCourseStatus personCourseStatus = getPersonCourseStatus(externalStudentTranscriptCourse, personCourseStatuses);

            if (personCourseStatus != null) {
                if (StringUtils.isNotBlank(externalStudentTranscriptCourse.getStatusCode())) {
                    if (isCourseWithdrawn(externalStudentTranscriptCourse, personCourseStatus, courseEnrollmentStatusCodesChangesArray)) {
                        LOGGER.trace("Found withdrawn course adding to email list!");
                        courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs
                                .add(new CourseSpecialServiceGroupCourseWithdrawalMessageTemplateTO(externalStudentTranscriptCourse, personCourseStatus.getStatusCode()));
                    }

                    if (!personCourseStatus.getStatusCode().equals(externalStudentTranscriptCourse.getStatusCode().trim())) {
                        LOGGER.trace("Updating existing PersonCourseStatus Record... ");
                        personCourseStatus.setPreviousStatusCode(personCourseStatus.getStatusCode());
                        personCourseStatus.setStatusCode(externalStudentTranscriptCourse.getStatusCode().trim());
                        personCourseStatusDao.save(personCourseStatus);
                    }

                } else {
                    LOGGER.debug("DATA ERROR: Can't update an existing Person Course Status record since the external transcript course status_code is missing for:" +
                            " schoolId: [" + externalStudentTranscriptCourse.getSchoolId() +
                            "] formattedCourse: [" + externalStudentTranscriptCourse.getFormattedCourse() +
                            "] sectionCode: [" + externalStudentTranscriptCourse.getSectionCode() +
                            "] termCode: [" + externalStudentTranscriptCourse.getTermCode() +
                            "] and statusCode: [" + externalStudentTranscriptCourse.getStatusCode() +
                            "]!");
                }
            } else {
                LOGGER.trace("No previous course history found for: " +
                        " schoolId: [" + externalStudentTranscriptCourse.getSchoolId() +
                        "] formattedCourse: [" + externalStudentTranscriptCourse.getFormattedCourse() +
                        "] sectionCode: [" + externalStudentTranscriptCourse.getSectionCode() +
                        "] termCode: [" + externalStudentTranscriptCourse.getTermCode() +
                        "] and statusCode: [" + externalStudentTranscriptCourse.getStatusCode() +
                        "]!");

                LOGGER.trace("Creating new PersonCourseStatus Record...");
                personCourseStatus = createPersonCourseStatus(externalStudentTranscriptCourse, student);

                if (personCourseStatus != null) {
                    LOGGER.trace("Attempting to save personCourseStatus... ");
                    personCourseStatusDao.save(personCourseStatus);
                } else {
                    LOGGER.debug("DATA ERROR: Can't record a new Person Course Status since the external transcript course record is incomplete for:" +
                            " schoolId: [" + externalStudentTranscriptCourse.getSchoolId() +
                            "] formattedCourse: [" + externalStudentTranscriptCourse.getFormattedCourse() +
                            "] sectionCode: [" + externalStudentTranscriptCourse.getSectionCode() +
                            "] termCode: [" + externalStudentTranscriptCourse.getTermCode() +
                            "] and statusCode: [" + externalStudentTranscriptCourse.getStatusCode() +
                            "]!");
                }
            }
        }

        if (courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs.size() > 0 || studentAddedToSSP) {
            LOGGER.debug("Found " + courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs.size() +
                    " courses are withdrawn or student was added to SSP. Adding " + student.getSchoolId() +
                    " to email notification.");
            studentWithdrawalEmails.add(new StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO(student, studentAddedToSSP, courseSpecialServiceGroupCourseWithdrawalMessageTemplateTOs));
        }
    }

	private PersonCourseStatus createPersonCourseStatus(ExternalStudentTranscriptCourse externalStudentTranscriptCourse, Person person) {
		if (StringUtils.isNotBlank(externalStudentTranscriptCourse.getTermCode()) &&
            StringUtils.isNotBlank(externalStudentTranscriptCourse.getFormattedCourse()) &&
            StringUtils.isNotBlank(externalStudentTranscriptCourse.getSectionCode()) &&
            StringUtils.isNotBlank(externalStudentTranscriptCourse.getStatusCode())) {

            final PersonCourseStatus personCourseStatus = new PersonCourseStatus();
            personCourseStatus.setPerson(person);
            personCourseStatus.setObjectStatus(ObjectStatus.ACTIVE);
            personCourseStatus.setTermCode(externalStudentTranscriptCourse.getTermCode().trim());
            personCourseStatus.setFormattedCourse(externalStudentTranscriptCourse.getFormattedCourse().trim());
            personCourseStatus.setSectionCode(externalStudentTranscriptCourse.getSectionCode().trim());
            personCourseStatus.setStatusCode(externalStudentTranscriptCourse.getStatusCode().trim());

            return personCourseStatus;

		} else {
		    return null; //can't save as null constraints not met
        }
	}

	private PersonCourseStatus getPersonCourseStatus(ExternalStudentTranscriptCourse externalStudentTranscriptCourse, Collection<PersonCourseStatus> personCourseStatuses) {
		for (final PersonCourseStatus personCourseStatus : personCourseStatuses) {
			if (externalStudentTranscriptCourse.getTermCode().trim().equals(personCourseStatus.getTermCode()) &&
				externalStudentTranscriptCourse.getFormattedCourse().trim().equals(personCourseStatus.getFormattedCourse()) &&
				externalStudentTranscriptCourse.getSectionCode().trim().equals(personCourseStatus.getSectionCode())) {

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

	private Collection<PersonSearchResult2> getAllStudentsForCoach (Person coach, List<SpecialServiceGroup> specialServiceGroups, boolean addStudentToSSP) {
		final PersonSearchRequest personSearchRequest = new PersonSearchRequest();
		personSearchRequest.setCoach(coach);
		personSearchRequest.setSpecialServiceGroup(specialServiceGroups);

		if (addStudentToSSP) {
		    LOGGER.trace("Special Service Group Course Withdrawal is Searching Internal and External Students due to Configuration.");
			personSearchRequest.setPersonTableType(PersonSearchRequest.PERSON_TABLE_TYPE_ANYWHERE);
		} else {
            LOGGER.trace("Special Service Group Course Withdrawal is Searching Internal Students Only due to Configuration.");
            personSearchRequest.setPersonTableType(PersonSearchRequest.PERSON_TABLE_TYPE_SSP_ONLY);
		}

		personSearchRequest.setSortAndPage(SortingAndPaging
				.createForSingleSortWithPaging(ObjectStatus.ALL, 0, -1, "dp.lastName",
						SortDirection.ASC.toString(), null));

		return personSearchService.searchPersonDirectory(personSearchRequest).getRows();
	}

	private boolean isCourseWithdrawn(ExternalStudentTranscriptCourse externalStudentTranscriptCourse, PersonCourseStatus personCourseStatus, String[] courseEnrollmentStatusCodesChanges) {
		for (String courseEnrollmentStatusCodeChange : courseEnrollmentStatusCodesChanges) {
			if (StringUtils.isNotBlank(courseEnrollmentStatusCodeChange) && courseEnrollmentStatusCodeChange.contains("|")) {
                if ((personCourseStatus.getStatusCode() + "|" + externalStudentTranscriptCourse.getStatusCode().trim()).equals(courseEnrollmentStatusCodeChange.trim())) {
                    return true;
                }
            } else {
			    LOGGER.info("WARN: Possible error in configuration: [" + CONFIG_COURSE_ENROLLMENT_STATUS_CODE_CHANGES + "] " +
                        "for value: [" + courseEnrollmentStatusCodeChange + "]. Make sure each value is of format X|Y and " +
                        "multiple elements are separated by commas.");
            }
		}

		return false;
	}

	private void sendEmail(final Person coach, final List<StudentSpecialServiceGroupCourseWithdrawalMessageTemplateTO> students) {

	    String emailAddress = null;
	    if (StringUtils.isNotBlank(coach.getPrimaryEmailAddress())) {
            emailAddress = coach.getPrimaryEmailAddress();
        } else if (StringUtils.isNotBlank(coach.getSecondaryEmailAddress())) {
	        emailAddress = coach.getSecondaryEmailAddress();
        } else {
	        LOGGER.info("No email address found for coach: {} can't send notification!", coach.getSchoolId());
        }

        if (emailAddress != null) {
            final SubjectAndBody subjectAndBody = messageTemplateService.createSpecialServiceGroupCourseWithdrawalCoachMessage(new CoachPersonLiteMessageTemplateTO(coach), students);

            try {
                messageService.createMessage(emailAddress, null, subjectAndBody);
                LOGGER.trace("Special Service Group Course Withdrawal Emails Passed to Message Service!");
            } catch (Exception e) {
                LOGGER.error("Failed to send Special Service Group Course Withdrawal Advisor Email to coach {} at address {}",
                        new Object[]{coach.getSchoolId(), emailAddress, e});
            }
        }
	}
}