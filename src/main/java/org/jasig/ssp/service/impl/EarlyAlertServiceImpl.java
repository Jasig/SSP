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
package org.jasig.ssp.service.impl; // NOPMD by jon.adams

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.EarlyAlertDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.EarlyAlertRoutingService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.jasig.ssp.service.reference.EarlyAlertSuggestionService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.messagetemplate.EarlyAlertMessageTemplateTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.DateTimeUtils;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * EarlyAlert service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional
public class EarlyAlertServiceImpl extends // NOPMD
		AbstractPersonAssocAuditableService<EarlyAlert>
		implements EarlyAlertService {

	@Autowired
	private transient EarlyAlertDao dao;

	@Autowired
	private transient ConfigService configService;

	@Autowired
	private transient EarlyAlertRoutingService earlyAlertRoutingService;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient MessageTemplateService messageTemplateService;

	@Autowired
	private transient EarlyAlertReasonService earlyAlertReasonService;

	@Autowired
	private transient EarlyAlertSuggestionService earlyAlertSuggestionService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient FacultyCourseService facultyCourseService;

	@Autowired
	private transient TermService termService;

	@Autowired
	private transient PersonProgramStatusService personProgramStatusService;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient StudentTypeService studentTypeService;

	@Autowired
	private transient SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertServiceImpl.class);

	@Override
	protected EarlyAlertDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlert create(@NotNull final EarlyAlert earlyAlert)
			throws ObjectNotFoundException, ValidationException {
		// Validate objects
		if (earlyAlert == null) {
			throw new IllegalArgumentException("EarlyAlert must be provided.");
		}

		if (earlyAlert.getPerson() == null) {
			throw new ValidationException(
					"EarlyAlert Student data must be provided.");
		}

		final Person student = earlyAlert.getPerson();

		// Figure student advisor or early alert coordinator
		final UUID assignedAdvisor = getEarlyAlertAdvisor(earlyAlert);
		if (assignedAdvisor == null) {
			throw new ValidationException(
					"Could not determine the Early Alert Advisor for student ID "
							+ student.getId());
		}

		if (student.getCoach() == null
				|| assignedAdvisor.equals(student.getCoach().getId())) {
			student.setCoach(personService.get(assignedAdvisor));
		}

		ensureValidAlertedOnPersonStateNoFail(student);

		// Create alert
		final EarlyAlert saved = getDao().save(earlyAlert);

		// Send e-mail to assigned advisor (coach)
		try {
			sendMessageToAdvisor(saved, earlyAlert.getEmailCC());
		} catch (final SendFailedException e) {
			LOGGER.warn(
					"Could not send Early Alert message to advisor.",
					e);
			throw new ValidationException(
					"Early Alert notification e-mail could not be sent to advisor. Early Alert was NOT created.",
					e);
		}

		// Send e-mail CONFIRMATION to faculty
		try {
			sendConfirmationMessageToFaculty(saved);
		} catch (final SendFailedException e) {
			LOGGER.warn(
					"Could not send Early Alert confirmation to faculty.",
					e);
			throw new ValidationException(
					"Early Alert confirmation e-mail could not be sent. Early Alert was NOT created.",
					e);
		}

		return saved;
	}

	@Override
	public void closeEarlyAlert(UUID earlyAlertId)
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlert earlyAlert = getDao().get(earlyAlertId);

		// DAOs don't implement ObjectNotFoundException consistently and we'd
		// rather they not implement it at all, so a small attempt at 'future
		// proofing' here
		if ( earlyAlert == null ) {
			throw new ObjectNotFoundException(earlyAlertId, EarlyAlert.class.getName());
		}

		if ( earlyAlert.getClosedDate() != null ) {
			// already closed
			return;
		}

		final SspUser sspUser = securityService.currentUser();
		if ( sspUser == null ) {
			throw new ValidationException("Early Alert cannot be closed by a null User.");
		}

		earlyAlert.setClosedDate(new Date());
		earlyAlert.setClosedBy(sspUser.getPerson());

		// This save will result in a Hib session flush, which works fine with
		// our current usage. Future use cases might prefer to delay the
		// flush and we can address that when the time comes. Might not even
		// need to change anything here if it turns out nothing actually
		// *depends* on the flush.
		getDao().save(earlyAlert);
	}
	
	@Override
	public void openEarlyAlert(UUID earlyAlertId)
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlert earlyAlert = getDao().get(earlyAlertId);

		// DAOs don't implement ObjectNotFoundException consistently and we'd
		// rather they not implement it at all, so a small attempt at 'future
		// proofing' here
		if ( earlyAlert == null ) {
			throw new ObjectNotFoundException(earlyAlertId, EarlyAlert.class.getName());
		}

		if ( earlyAlert.getClosedDate() == null ) {
			return;
		}

		final SspUser sspUser = securityService.currentUser();
		if ( sspUser == null ) {
			throw new ValidationException("Early Alert cannot be closed by a null User.");
		}

		earlyAlert.setClosedDate(null);
		earlyAlert.setClosedBy(null);

		// This save will result in a Hib session flush, which works fine with
		// our current usage. Future use cases might prefer to delay the
		// flush and we can address that when the time comes. Might not even
		// need to change anything here if it turns out nothing actually
		// *depends* on the flush.
		getDao().save(earlyAlert);
	}

	@Override
	public EarlyAlert save(@NotNull final EarlyAlert obj)
			throws ObjectNotFoundException {
		final EarlyAlert current = getDao().get(obj.getId());

		current.setCourseName(obj.getCourseName());
		current.setCourseTitle(obj.getCourseTitle());
		current.setEmailCC(obj.getEmailCC());
		current.setCampus(obj.getCampus());
		current.setEarlyAlertReasonOtherDescription(obj
				.getEarlyAlertReasonOtherDescription());
		current.setComment(obj.getComment());
		current.setClosedDate(obj.getClosedDate());
		if ( obj.getClosedById() == null ) {
			current.setClosedBy(null);
		} else {
			current.setClosedBy(personService.get(obj.getClosedById()));
		}

		if (obj.getPerson() == null) {
			current.setPerson(null);
		} else {
			current.setPerson(personService.get(obj.getPerson().getId()));
		}

		final Set<EarlyAlertReason> earlyAlertReasons = new HashSet<EarlyAlertReason>();
		if (obj.getEarlyAlertReasons() != null) {
			for (final EarlyAlertReason reason : obj.getEarlyAlertReasons()) {
				earlyAlertReasons.add(earlyAlertReasonService.load(reason
						.getId()));
			}
		}

		current.setEarlyAlertReasons(earlyAlertReasons);

		final Set<EarlyAlertSuggestion> earlyAlertSuggestions = new HashSet<EarlyAlertSuggestion>();
		if (obj.getEarlyAlertSuggestions() != null) {
			for (final EarlyAlertSuggestion reason : obj
					.getEarlyAlertSuggestions()) {
				earlyAlertSuggestions.add(earlyAlertSuggestionService
						.load(reason
								.getId()));
			}
		}

		current.setEarlyAlertSuggestions(earlyAlertSuggestions);

		return getDao().save(current);
	}

	@Override
	public PagingWrapper<EarlyAlert> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	/**
	 * Business logic to determine the advisor that is assigned to the student
	 * for this Early Alert.
	 * 
	 * @param earlyAlert
	 *            EarlyAlert instance
	 * @throws ValidationException
	 *             If Early Alert, Student, and/or system information could not
	 *             determine the advisor for this student.
	 * @return The assigned advisor
	 */
	private UUID getEarlyAlertAdvisor(final EarlyAlert earlyAlert)
			throws ValidationException {
		// Check for student already assigned to an advisor (a.k.a. coach)
		if ((earlyAlert.getPerson().getCoach() != null) &&
				(earlyAlert.getPerson().getCoach().getId() != null)) {
			return earlyAlert.getPerson().getCoach().getId();
		}

		// Get campus Early Alert coordinator
		if (earlyAlert.getCampus() == null) {
			throw new IllegalArgumentException("Campus ID can not be null.");
		}

		if (earlyAlert.getCampus().getEarlyAlertCoordinatorId() != null) {
			// Return Early Alert coordinator UUID
			return earlyAlert.getCampus().getEarlyAlertCoordinatorId();
		}

		// TODO If no campus EA Coordinator, assign to default EA Coordinator
		// (which is not yet implemented)

		// getEarlyAlertAdvisor should never return null
		throw new ValidationException(
				"Could not determined the Early Alert Coordinator for this student. Ensure that a default coordinator is set globally and for all campuses.");
	}

	private void ensureValidAlertedOnPersonStateNoFail(Person person) {
		try {
			ensureValidAlertedOnPersonStateOrFail(person);
		} catch ( Exception e ) {
			LOGGER.error("Unable to set a program status or student type on "
					+ "person '{}'. This is likely to prevent that person "
					+ "record from appearing in caseloads, student searches, "
					+ "and some reports.", person.getId(), e);
		}
	}

	private void ensureValidAlertedOnPersonStateOrFail(Person person)
			throws ObjectNotFoundException, ValidationException {

		if ( person.getObjectStatus() != ObjectStatus.ACTIVE ) {
			person.setObjectStatus(ObjectStatus.ACTIVE);
		}

		final ProgramStatus programStatus =  programStatusService.getActiveStatus();
		if ( programStatus == null ) {
			throw new ObjectNotFoundException(
					"Unable to find a ProgramStatus representing \"activeness\".",
					"ProgramStatus");
		}

		Set<PersonProgramStatus> programStatuses =
				person.getProgramStatuses();
		if ( programStatuses == null || programStatuses.isEmpty() ) {
			PersonProgramStatus personProgramStatus = new PersonProgramStatus();
			personProgramStatus.setEffectiveDate(new Date());
			personProgramStatus.setProgramStatus(programStatus);
			personProgramStatus.setPerson(person);
			programStatuses.add(personProgramStatus);
			person.setProgramStatuses(programStatuses);
			// save should cascade, but make sure custom create logic fires
			personProgramStatusService.create(personProgramStatus);
		}

		if ( person.getStudentType() == null ) {
			StudentType studentType = studentTypeService.get(StudentType.EAL_ID);
			if ( studentType == null ) {
				throw new ObjectNotFoundException(
						"Unable to find a StudentType representing an early "
								+ "alert-assigned type.", "StudentType");
			}
			person.setStudentType(studentType);
		}
	}

	/**
	 * Send e-mail ({@link Message}) to the assigned advisor for the student.
	 * 
	 * @param earlyAlert
	 *            Early Alert
	 * @param emailCC
	 *            Email address to also CC this message
	 * @throws ObjectNotFoundException
	 * @throws SendFailedException
	 * @throws ValidationException
	 */
	private void sendMessageToAdvisor(@NotNull final EarlyAlert earlyAlert, // NOPMD
			final String emailCC) throws ObjectNotFoundException,
			SendFailedException, ValidationException {
		if (earlyAlert == null) {
			throw new IllegalArgumentException("Early alert was missing.");
		}

		if (earlyAlert.getPerson() == null) {
			throw new IllegalArgumentException("EarlyAlert Person is missing.");
		}

		final Person person = earlyAlert.getPerson().getCoach();
		final SubjectAndBody subjAndBody = messageTemplateService
				.createEarlyAlertAdvisorConfirmationMessage(fillTemplateParameters(earlyAlert));

		if ( person == null ) {
			LOGGER.warn("Student {} had no coach when EarlyAlert {} was"
					+ " created. Unable to send message to coach.",
					earlyAlert.getPerson(), earlyAlert);
		} else {
			// Create and queue the message
			final Message message = messageService.createMessage(person, emailCC,
					subjAndBody);
			LOGGER.info("Message {} created for EarlyAlert {}", message, earlyAlert);
		}

		// Send same message to all applicable Campus Early Alert routing
		// entries
		final PagingWrapper<EarlyAlertRouting> routes = earlyAlertRoutingService
				.getAllForCampus(earlyAlert.getCampus(), new SortingAndPaging(
						ObjectStatus.ACTIVE));
		if (routes.getResults() > 0) {
			for (final EarlyAlertRouting route : routes.getRows()) {
				// Check that route applies
				if (route.getEarlyAlertReason() == null) {
					throw new ObjectNotFoundException(
							"EarlyAlertRouting missing EarlyAlertReason.",
							"EarlyAlertReason");
				}

				// Only routes that are for any of the Reasons in this
				// EarlyAlert should be applied.
				if ((earlyAlert.getEarlyAlertReasons() == null)
						|| !earlyAlert.getEarlyAlertReasons().contains(
								route.getEarlyAlertReason())) {
					continue;
				}

				// Send e-mail to specific person
				final Person to = route.getPerson();
				if ((to != null)
						&& !StringUtils.isEmpty(to.getPrimaryEmailAddress())) {
					final Message message = messageService
							.createMessage(to, null, subjAndBody);
					LOGGER.info(
							"Message {} for EarlyAlert {} also routed to {}",
							new Object[] { message, earlyAlert, to }); // NOPMD
				}

				// Send e-mail to a group
				if (!StringUtils.isEmpty(route.getGroupName())
						&& !StringUtils.isEmpty(route.getGroupEmail())) {
					final Message message = messageService
							.createMessage(route.getGroupEmail(), null,
									subjAndBody);
					LOGGER.info(
							"Message {} for EarlyAlert {} also routed to {}",
							new Object[] { message, earlyAlert, // NOPMD
									route.getGroupEmail() });
				}
			}
		}
	}

	@Override
	public void sendMessageToStudent(@NotNull final EarlyAlert earlyAlert)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException {
		if (earlyAlert == null) {
			throw new IllegalArgumentException("EarlyAlert was missing.");
		}

		if (earlyAlert.getPerson() == null) {
			throw new IllegalArgumentException("EarlyAlert.Person is missing.");
		}

		final Person person = earlyAlert.getPerson();
		final SubjectAndBody subjAndBody = messageTemplateService
				.createEarlyAlertToStudentMessage(fillTemplateParameters(earlyAlert));

		// Create and queue the message
		final Message message = messageService.createMessage(person, null,
				subjAndBody);

		LOGGER.info("Message {} created for EarlyAlert {}", message, earlyAlert);
	}

	/**
	 * Send confirmation e-mail ({@link Message}) to the faculty who created
	 * this alert.
	 * 
	 * @param earlyAlert
	 *            Early Alert
	 * @throws ObjectNotFoundException
	 * @throws SendFailedException
	 * @throws ValidationException
	 */
	private void sendConfirmationMessageToFaculty(final EarlyAlert earlyAlert)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException {
		if (earlyAlert == null) {
			throw new IllegalArgumentException("EarlyAlert was missing.");
		}

		if (earlyAlert.getPerson() == null) {
			throw new IllegalArgumentException("EarlyAlert.Person is missing.");
		}

		final UUID personId = earlyAlert.getCreatedBy();
		Person person = personService.get(personId);
		if ( person == null ) {
			LOGGER.warn("EarlyAlert {} has no creator. Unable to send"
					+ " confirmation message to faculty.", earlyAlert);
		} else {
			final SubjectAndBody subjAndBody = messageTemplateService
					.createEarlyAlertFacultyConfirmationMessage(fillTemplateParameters(earlyAlert));

			// Create and queue the message
			final Message message = messageService.createMessage(person, null,
					subjAndBody);

			LOGGER.info("Message {} created for EarlyAlert {}", message, earlyAlert);
		}
	}

	@Override
	public Map<String, Object> fillTemplateParameters(
			@NotNull final EarlyAlert earlyAlert) {
		if (earlyAlert == null) {
			throw new IllegalArgumentException("EarlyAlert was missing.");
		}

		if (earlyAlert.getPerson() == null) {
			throw new IllegalArgumentException("EarlyAlert.Person is missing.");
		}

		if (earlyAlert.getCreatedBy() == null) {
			throw new IllegalArgumentException(
					"EarlyAlert.CreatedBy is missing.");
		}

		if (earlyAlert.getCampus() == null) {
			throw new IllegalArgumentException("EarlyAlert.Campus is missing.");
		}

		// ensure earlyAlert.createdBy is populated
		if ((earlyAlert.getCreatedByFirstName() == null)
				|| (earlyAlert.getCreatedByFirstName() == null)) {
			if (earlyAlert.getCreatedBy() == null) {
				throw new IllegalArgumentException(
						"EarlyAlert.CreatedBy is missing.");
			}

			try {
				earlyAlert.setCreatedBy(personService.get(earlyAlert
						.getCreatedBy()).getId());
			} catch (final ObjectNotFoundException e) {
				throw new IllegalArgumentException(
						"EarlyAlert.CreatedBy.Id could not be loaded.", e);
			}
		}

		final Map<String, Object> templateParameters = Maps.newHashMap();

		final String courseName = earlyAlert.getCourseName();
		if ( StringUtils.isNotBlank(courseName) ) {
			Person creator;
			try {
				creator = personService.get(earlyAlert.getCreatedBy());
			} catch (ObjectNotFoundException e1) {
				throw new IllegalArgumentException(
						"EarlyAlert.CreatedBy.Id could not be loaded.", e1);
			}
			final String facultySchoolId = creator.getSchoolId();
			if ( (StringUtils.isNotBlank(facultySchoolId)) ) {
				String termCode = earlyAlert.getCourseTermCode();
				FacultyCourse course = null;
				try {
					if ( StringUtils.isBlank(termCode) ) {
						course = facultyCourseService.
								getCourseByFacultySchoolIdAndFormattedCourse(
										facultySchoolId, courseName);
					} else {
						course = facultyCourseService.
								getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(
										facultySchoolId, courseName, termCode);
					}
				} catch ( ObjectNotFoundException e ) {
					// Trace irrelevant. see below for logging. prefer to
					// do it there, after the null check b/c not all service
					// methods implement ObjectNotFoundException reliably.
				}
				if ( course != null ) {
					templateParameters.put("course", course);
					if ( StringUtils.isBlank(termCode) ) {
						termCode = course.getTermCode();
					}
					if ( StringUtils.isNotBlank(termCode) ) {
						Term term = null;
						try {
							term = termService.getByCode(termCode);
						} catch ( ObjectNotFoundException e ) {
							// Trace irrelevant. See below for logging.
						}
						if ( term != null ) {
							templateParameters.put("term", term);
						} else {
							LOGGER.info("Not adding term to message template"
									+ " params or early alert {} because"
									+ " the term code {} did not resolve to"
									+ " an external term record",
									earlyAlert.getId(), termCode);
						}
					}
				} else {
					LOGGER.info("Not adding course nor term to message template"
							+ " params for early alert {} because the associated"
							+ " course {} and faculty school id {} did not"
							+ " resolve to an external course record.",
							new Object[] { earlyAlert.getId(), courseName,
									facultySchoolId});
				}
			}
		}

		templateParameters.put("earlyAlert", earlyAlert);
		templateParameters.put("termToRepresentEarlyAlert",
				configService.getByNameEmpty("term_to_represent_early_alert"));
		templateParameters.put("linkToSSP",
				configService.getByNameEmpty("serverExternalPath"));
		templateParameters.put("applicationTitle",
				configService.getByNameEmpty("app_title"));
		templateParameters.put("institutionName",
				configService.getByNameEmpty("inst_name"));

		return templateParameters;
	}

	@Override
	public void applyEarlyAlertCounts(Person person) {
		if ( person == null ) {
			return; // can occur in some legit person lookup call paths
		}
//		Map<UUID,Number> activeCnts =
//				getCountOfActiveAlertsForPeopleIds(Sets.newHashSet(person.getId()));
//		if ( activeCnts == null || !(activeCnts.containsKey(person.getId())) ) {
//			person.setActiveAlertsCount(0);
//		} else {
//			person.setActiveAlertsCount(activeCnts.get(person.getId()));
//		}
//		Map<UUID,Number> closedCnts =
//				getCountOfClosedAlertsForPeopleIds(Sets.newHashSet(person.getId()));
//		if ( closedCnts == null || !(closedCnts.containsKey(person.getId())) ) {
//			person.setClosedAlertsCount(0);
//		} else {
//			person.setClosedAlertsCount(closedCnts.get(person.getId()));
//		}
	}

	@Override
	public Map<UUID, Number> getCountOfActiveAlertsForPeopleIds(
			final Collection<UUID> peopleIds) {
		return dao.getCountOfActiveAlertsForPeopleIds(peopleIds);
	}

	@Override
	public Map<UUID, Number> getCountOfClosedAlertsForPeopleIds(
			final Collection<UUID> peopleIds) {
		return dao.getCountOfClosedAlertsForPeopleIds(peopleIds);
	}
	
	@Override
	public Long getCountOfEarlyAlertsForSchoolIds(
			final Collection<String> shoolIds, Campus campus) {
		return dao.getCountOfAlertsForSchoolIds(shoolIds, campus);
	}

	@Override
	public Long getEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {
		return dao.getEarlyAlertCountForCoach(coach, createDateFrom,  createDateTo, studentTypeIds);
	}

	@Override
	public Long getStudentEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {
		return dao.getStudentEarlyAlertCountForCoach(coach, createDateFrom,  createDateTo, studentTypeIds);
	}
	
	@Override
	public Long getCountOfEarlyAlertsByCreatedDate(Date createDatedFrom, Date createdDateTo, Campus campus, String rosterStatus) {
		return dao.getCountOfEarlyAlertsByCreatedDate(createDatedFrom,  createdDateTo, campus, rosterStatus);
	}

	@Override
	public Long getCountOfEarlyAlertsClosedByDate(Date closedDateFrom, Date closedDateTo, Campus campus, String rosterStatus) {
		return dao.getCountOfEarlyAlertsClosedByDate(closedDateFrom,  closedDateTo, campus, rosterStatus);
	}

	@Override
	public Long getCountOfEarlyAlertStudentsByDate(Date createDatedFrom,
			Date createdDateTo, Campus campus, String rosterStatus) {
		return dao.getCountOfEarlyAlertStudentsByDate(createDatedFrom, createdDateTo, campus, rosterStatus);
	}

	@Override
	public PagingWrapper<EarlyAlertStudentReportTO> getStudentsEarlyAlertCountSetForCritera(
			EarlyAlertStudentSearchTO earlyAlertStudentSearchTO,
			SortingAndPaging createForSingleSort) {
		return dao.getStudentsEarlyAlertCountSetForCritera(earlyAlertStudentSearchTO, createForSingleSort);
	}
	
	@Override
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertCountByCoaches(EntityCountByCoachSearchForm form) {
		return dao.getStudentEarlyAlertCountByCoaches(form);
	}
	
	@Override
	public Long getEarlyAlertCountSetForCritera(EarlyAlertStudentSearchTO searchForm){
		return dao.getEarlyAlertCountSetForCritera(searchForm);
	}
	
	@Override
	public void sendAllEarlyAlertReminderNotifications() {
		Date lastResponseDate = getMinimumResponseComplianceDate();
		// if no responseDate is given no emails are sent
		if(lastResponseDate == null)
			return;
		List<EarlyAlert> eaOutOfCompliance = dao.getResponseDueEarlyAlerts(lastResponseDate);
		Map<Person, List<EarlyAlertMessageTemplateTO>> easByCoach = new HashMap<Person, List<EarlyAlertMessageTemplateTO>>();
		for(EarlyAlert earlyAlert: eaOutOfCompliance){
			Person coach = earlyAlert.getPerson().getCoach();
			//Only early alerts response late messages sent to coaches
			if(coach == null){
				try{
					// if no earlyAlert.getCampus()  error thrown by design, should never not be a campus.
					coach = personService.get(earlyAlert.getCampus().getEarlyAlertCoordinatorId());
				}catch(ObjectNotFoundException exp){
					LOGGER.error("Early Alert with id: " + earlyAlert.getId() + " does not have valid campus coordinator, no coach assigned: " + earlyAlert.getCampus().getEarlyAlertCoordinatorId(), exp);
				}
			}
			if(easByCoach.containsKey(coach)){
				List<EarlyAlertMessageTemplateTO> coachEarlyAlerts = easByCoach.get(coach);
				Person creator = null;
				try{
					 creator = personService.get(earlyAlert.getCreatedBy());
				}catch(ObjectNotFoundException exp){
					LOGGER.error("Early Alert with id: " + earlyAlert.getId() + " does not have valid creator: " + earlyAlert.getCreatedBy(), exp);
				}
				coachEarlyAlerts.add(new EarlyAlertMessageTemplateTO(earlyAlert, creator));
			}else{
				easByCoach.put(coach, Lists.newArrayList(new EarlyAlertMessageTemplateTO(earlyAlert)));
			}
		}
		for(Person coach: easByCoach.keySet()){
			Map<String,Object> messageParams = new HashMap<String,Object>();
			
			Collections.sort(easByCoach.get(coach), new Comparator<EarlyAlertTO>() {
		        @Override public int compare(EarlyAlertTO p1, EarlyAlertTO p2) {
		        	Date p1Date = p1.getLastResponseDate();
		        	if(p1Date == null)
		        		p1Date = p1.getCreatedDate();
		        	Date p2Date = p2.getLastResponseDate();
		        	if(p2Date == null)
		        		p2Date = p2.getCreatedDate();
		            return p1Date.compareTo(p2Date);
		        }

		    });
			
			Integer daysSince1900ResponseExpected =  DateTimeUtils.daysSince1900(lastResponseDate);
			List<Pair<EarlyAlertMessageTemplateTO,Integer>> earlyAlertTOPairs = new ArrayList<Pair<EarlyAlertMessageTemplateTO,Integer>>();
			for(EarlyAlertMessageTemplateTO ea:easByCoach.get(coach)){
				Integer daysOutOfCompliance;
				if(ea.getLastResponseDate() != null){
					daysOutOfCompliance = daysSince1900ResponseExpected - DateTimeUtils.daysSince1900(ea.getLastResponseDate());
				}else{
				    daysOutOfCompliance = daysSince1900ResponseExpected - DateTimeUtils.daysSince1900(ea.getCreatedDate());
				}
				
				// Just in case attempt to only send emails for EA full day out of compliance
				if(daysOutOfCompliance >= 0)
					earlyAlertTOPairs.add(new Pair<EarlyAlertMessageTemplateTO,Integer>(ea, daysOutOfCompliance));
			}
			messageParams.put("earlyAlertTOPairs", earlyAlertTOPairs);
			messageParams.put("coach", coach);
			messageParams.put("DateTimeUtils", DateTimeUtils.class);
			messageParams.put("termToRepresentEarlyAlert",
					configService.getByNameEmpty("term_to_represent_early_alert"));
			SubjectAndBody subjAndBody = messageTemplateService.createEarlyAlertResponseRequiredToCoachMessage(messageParams);
			try{
				messageService.createMessage(coach, null, subjAndBody);
			}catch(Exception exp){
				LOGGER.error("Unable to send reminder emails to coach: " + coach.getFullName() + "\n", exp);
			}
		}
		
	}
	
	public Map<UUID,Number> getResponsesDueCountEarlyAlerts(List<UUID> personIds){
		Date lastResponseDate = getMinimumResponseComplianceDate();
		if(lastResponseDate == null)
			return new HashMap<UUID,Number>();
		return dao.getResponsesDueCountEarlyAlerts(personIds, lastResponseDate);
	}
	
	private Date getMinimumResponseComplianceDate(){
		final String numVal = configService
				.getByNameNull("maximum_days_before_early_alert_response");
		if(StringUtils.isBlank(numVal))
			return null;
		Integer allowedDaysPastResponse = Integer.parseInt(numVal);
		
	    return DateTimeUtils.getDateOffsetInDays(new Date(), -allowedDaysPastResponse);
				
	}
}