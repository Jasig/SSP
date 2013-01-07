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

import java.util.Collection;
import java.util.Date;
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
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.EarlyAlertRoutingService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.jasig.ssp.service.reference.EarlyAlertSuggestionService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		current.setClosedById(obj.getClosedById());

		if (obj.getPerson() == null) {
			current.setPerson(null);
		} else {
			current.setPerson(personService.get(obj.getPerson().getId()));
		}

		final Set<EarlyAlertReason> earlyAlertReasons = new HashSet<EarlyAlertReason>();
		if (obj.getEarlyAlertReasonIds() != null) {
			for (final EarlyAlertReason reason : obj.getEarlyAlertReasonIds()) {
				earlyAlertReasons.add(earlyAlertReasonService.load(reason
						.getId()));
			}
		}

		current.setEarlyAlertReasonIds(earlyAlertReasons);

		final Set<EarlyAlertSuggestion> earlyAlertSuggestions = new HashSet<EarlyAlertSuggestion>();
		if (obj.getEarlyAlertSuggestionIds() != null) {
			for (final EarlyAlertSuggestion reason : obj
					.getEarlyAlertSuggestionIds()) {
				earlyAlertSuggestions.add(earlyAlertSuggestionService
						.load(reason
								.getId()));
			}
		}

		current.setEarlyAlertSuggestionIds(earlyAlertSuggestions);

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
				if ((earlyAlert.getEarlyAlertReasonIds() == null)
						|| !earlyAlert.getEarlyAlertReasonIds().contains(
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

		final Person person = earlyAlert.getCreatedBy();
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
		if ((earlyAlert.getCreatedBy().getFirstName() == null)
				|| (earlyAlert.getCreatedBy().getLastName() == null)) {
			if (earlyAlert.getCreatedBy().getId() == null) {
				throw new IllegalArgumentException(
						"EarlyAlert.CreatedBy.Id is missing.");
			}

			try {
				earlyAlert.setCreatedBy(personService.get(earlyAlert
						.getCreatedBy().getId()));
			} catch (final ObjectNotFoundException e) {
				throw new IllegalArgumentException(
						"EarlyAlert.CreatedBy.Id could not be loaded.", e);
			}
		}

		final Map<String, Object> templateParameters = Maps.newHashMap();

		final String courseName = earlyAlert.getCourseName();
		if ( StringUtils.isNotBlank(courseName) ) {
			final String facultySchoolId = earlyAlert.getCreatedBy().getSchoolId();
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
	public Map<UUID, Number> getCountOfActiveAlertsForPeopleIds(
			final Collection<UUID> peopleIds) {
		return dao.getCountOfActiveAlertsForPeopleIds(peopleIds);
	}

	@Override
	public Long getEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {
		return dao.getEarlyAlertCountForCoach(coach, createDateFrom,  createDateTo, studentTypeIds);
	}

	@Override
	public Long getStudentEarlyAlertCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {
		return dao.getStudentEarlyAlertCountForCoach(coach, createDateFrom,  createDateTo, studentTypeIds);
	}
}