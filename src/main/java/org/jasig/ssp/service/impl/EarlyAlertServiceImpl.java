package org.jasig.ssp.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.EarlyAlertDao;
import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.jasig.ssp.service.reference.EarlyAlertSuggestionService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
public class EarlyAlertServiceImpl extends
		AbstractAuditableCrudService<EarlyAlert>
		implements EarlyAlertService {

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient EarlyAlertDao dao;

	@Autowired
	private transient JournalEntryService journalEntryService;

	@Autowired
	private transient MessageTemplateDao messageTemplateDao;
	@Autowired
	private transient VelocityTemplateService velocityTemplateService;

	/*
	 * @Autowired
	 * 
	 * private transient CampusService campusService;
	 */

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
			throws ObjectNotFoundException {

		// Figure student advisor or early alert coordinator
		final UUID assignedAdvisor = getEarlyAlertAdvisor(earlyAlert);

		// TODO According to EarlyAlert.java comments, changes on this side of
		// the association are not persisted. Needs tested.
		earlyAlert.getPerson().setCoach(personService.get(assignedAdvisor));

		// Create alert
		final EarlyAlert saved = getDao().save(earlyAlert);

		// Save a journal note for the Early Alert
		try {
			createJournalNoteForEarlyAlert(saved);
		} catch (final ObjectNotFoundException e) {
			LOGGER.error("Could not create JournalEntry for EarlyAlert.", e);
			// Continue if JournalEntry creation failed
		}

		// TODO Send e-mail to assigned advisor (coach)
		sendMessageToAdvisor(saved, earlyAlert.getEmailCC());

		// TODO: Send e-mail CONFIRMATION to faculty
		sendConfirmationMessageToFaculty(saved);

		// TODO Send e-mail to any applicable notification rule entry email
		// address
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
	 * 
	 * @return The assigned advisor
	 */
	private UUID getEarlyAlertAdvisor(final EarlyAlert earlyAlert) {

		// Check for student already assigned to an advisor (aka coach)
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

		return null; // TODO getEarlyAlertAdvisor should never return null
	}

	/**
	 * Save a journal note for the Early Alert
	 * 
	 * @param earlyAlert
	 *            Early Alert
	 * @throws ObjectNotFoundException
	 *             If any referenced data or message templates are missing or
	 *             could not be loaded.
	 */
	private void createJournalNoteForEarlyAlert(final EarlyAlert earlyAlert)
			throws ObjectNotFoundException {
		final MessageTemplate messageTemplate = messageTemplateDao
				.get(MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID);

		// TODO: Finish the code below for createJournalNoteForEarlyAlert
		if (true) {
			return;
		}

		final Map<String, Object> templateParameters = Maps.newHashMap();
		final StringBuilder sbReasons = new StringBuilder();
		final StringBuilder sbSuggestions = new StringBuilder();

		for (final EarlyAlertReason reason : earlyAlert
				.getEarlyAlertReasonIds()) {
			if (sbReasons.length() > 0) {
				sbReasons.append("\n");
			}

			sbReasons.append(reason.getName());
		}

		for (final EarlyAlertSuggestion suggestion : earlyAlert
				.getEarlyAlertSuggestionIds()) {
			if (sbSuggestions.length() > 0) {
				sbSuggestions.append("\n");
			}

			sbSuggestions.append(suggestion.getName());
		}

		templateParameters.put("FacultyMember", earlyAlert.getCreatedBy()
				.getFullName());
		templateParameters.put("FacultyOfficeLocation",
				"// TODO Office Location");
		templateParameters.put("FacultyPhoneNumber",
				"// TODO Faculty phone number");
		templateParameters.put("CourseName", earlyAlert.getCourseName());
		templateParameters.put("CourseTitle", earlyAlert.getCourseTitle());
		templateParameters.put("ReferralReasons", sbReasons);
		templateParameters.put("FacultySuggestions", sbSuggestions);
		templateParameters.put("FacultyComments", earlyAlert.getComment());
		final JournalEntry journalEntry = new JournalEntry();
		final JournalStepDetail detail = new JournalStepDetail();
		detail.setDescription(velocityTemplateService
				.generateContentFromTemplate(
						messageTemplate.subjectTemplateId(),
						messageTemplate.getSubject(), templateParameters));

		journalEntry.setComment("// TODO Comment");
		journalEntry.setEntryDate(new Date());
		// TODO: journalEntryJournalStepDetails
		journalEntry.setJournalEntryJournalStepDetails(null);
		// TODO: Need JournalSource.EarlyAlertResponse reference object
		journalEntry.setJournalSource(null); // TODO JournalSource
		journalEntry.setJournalTrack(null); // TODO JournalTrack
		journalEntry.setPerson(earlyAlert.getPerson().getCoach());

		journalEntry.setConfidentialityLevel(confidentialityLevelService
				.get(ConfidentialityLevel.CONFIDENTIALITYLEVEL_EVERYONE));
		journalEntryService.create(journalEntry);
	}

	/**
	 * Send e-mail ({@link Message}) to the assigned advisor for the student.
	 * 
	 * @param earlyAlert
	 *            Early Alert
	 * @param emailCC
	 *            Email address to also CC this message
	 */
	private void sendMessageToAdvisor(final EarlyAlert earlyAlert,
			final String emailCC) {
		// TODO: Implement sendMessageToAdvisor
	}

	/**
	 * Send confirmation e-mail ({@link Message}) to the faculty who created
	 * this alert.
	 * 
	 * @param earlyAlert
	 *            Early Alert
	 */
	private void sendConfirmationMessageToFaculty(final EarlyAlert earlyAlert) {
		// TODO: Implement sendConfirmationMessageToFaculty
	}
}