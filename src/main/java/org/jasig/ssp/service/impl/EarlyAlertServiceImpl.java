package org.jasig.ssp.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.EarlyAlertDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.jasig.ssp.service.reference.EarlyAlertSuggestionService;
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
		AbstractAuditableCrudService<EarlyAlert>
		implements EarlyAlertService {

	@Autowired
	private transient EarlyAlertDao dao;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient EarlyAlertReasonService earlyAlertReasonService;

	@Autowired
	private transient EarlyAlertSuggestionService earlyAlertSuggestionService;

	@Autowired
	private transient PersonService personService;

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
			throw new ValidationException("EarlyAlert must be provided.");
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
				|| student.getCoach().getId().equals(assignedAdvisor)) {
			student.setCoach(personService.get(assignedAdvisor));
		}

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

		// TODO: Send e-mail to student if faculty requested it

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

		// TODO Send e-mail to any applicable routing/notification rule entry
		// email addresses
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
		if (earlyAlert.getPerson().getCoach() != null &&
				earlyAlert.getPerson().getCoach().getId() != null) {
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
			throw new IllegalArgumentException("EarlyAlert was missing.");
		}

		if (earlyAlert.getPerson() == null) {
			throw new IllegalArgumentException("EarlyAlert.Person is missing.");
		}

		final Person person = earlyAlert.getPerson();
		final Map<String, Object> templateParameters = fillTemplateParameters(earlyAlert);

		// Create and queue the message
		final Message message = messageService.createMessage(person, emailCC,
				MessageTemplate.EARLYALERT_CONFIRMATIONTOADVISOR_ID,
				templateParameters);

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

		final Person person = earlyAlert.getPerson();
		final Map<String, Object> templateParameters = fillTemplateParameters(earlyAlert);
		templateParameters.put("Comment", earlyAlert.getComment());

		// Create and queue the message
		final Message message = messageService.createMessage(person, null,
				MessageTemplate.EARLYALERT_CONFIRMATIONTOFACULTY_ID,
				templateParameters);

		LOGGER.info("Message {} created for EarlyAlert {}", message, earlyAlert);
	}

	private Map<String, Object> fillTemplateParameters(
			final EarlyAlert earlyAlert) {
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

		final Person person = earlyAlert.getPerson();
		final Person createdBy = earlyAlert.getCreatedBy();
		final StringBuilder reasons = new StringBuilder();
		final StringBuilder suggestions = new StringBuilder();

		for (final EarlyAlertReason reason : earlyAlert
				.getEarlyAlertReasonIds()) {
			if (reasons.length() > 0) {
				reasons.append(",");
			}

			reasons.append(reason.getName());
		}

		for (final EarlyAlertSuggestion suggestion : earlyAlert
				.getEarlyAlertSuggestionIds()) {
			if (suggestions.length() > 0) {
				suggestions.append(",");
			}

			suggestions.append(suggestion.getName());
		}

		final Map<String, Object> templateParameters = Maps.newHashMap();
		templateParameters.put("TermForEarlyAlert",
				"// TODO: TermForEarlyAlert");
		templateParameters.put("FirstName", person.getFirstName());
		templateParameters.put("LastName", person.getLastName());
		templateParameters.put("SchoolId", person.getSchoolId());
		templateParameters.put("HomePhone", person.getHomePhone());
		templateParameters.put("PrimaryEmailAddress",
				person.getPrimaryEmailAddress());
		templateParameters.put("AddressLine1", person.getAddressLine1());
		templateParameters.put("AddressLine2", person.getAddressLine2());
		templateParameters.put("City", person.getCity());
		templateParameters.put("State", person.getState());
		templateParameters.put("ZipCode", person.getZipCode());
		templateParameters.put("CourseName", earlyAlert.getCourseName());
		templateParameters.put("CreatedByFirstName", createdBy.getFirstName());
		templateParameters.put("CreatedByLastName", createdBy.getLastName());
		templateParameters.put("CampusName", earlyAlert.getCampus().getName());
		templateParameters.put("CreatedByOfficeLocation",
				"// TODO: OfficeLocation");
		templateParameters.put("CreatedByPrimaryEmailAddress",
				createdBy.getPrimaryEmailAddress());
		templateParameters.put("CreatedByWorkPhone", createdBy.getWorkPhone());
		templateParameters.put("CoachFirstName", person.getCoach()
				.getFirstName());
		templateParameters
				.put("CoachLastName", person.getCoach().getLastName());
		templateParameters.put("Reasons", reasons.toString());
		templateParameters.put("Suggestions", suggestions.toString());

		return templateParameters;
	}
}