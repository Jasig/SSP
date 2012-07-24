package org.jasig.ssp.service.impl; // NOPMD

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.EarlyAlertResponseDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertResponse service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional
public class EarlyAlertResponseServiceImpl extends // NOPMD by jon.adams
		AbstractAuditableCrudService<EarlyAlertResponse>
		implements EarlyAlertResponseService {

	@Autowired
	private transient EarlyAlertResponseDao dao;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient EarlyAlertOutcomeService earlyAlertOutcomeService;

	@Autowired
	private transient EarlyAlertOutreachService earlyAlertOutreachService;

	@Autowired
	private transient EarlyAlertReferralService earlyAlertReferralService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient JournalEntryService journalEntryService;

	@Autowired
	private transient JournalSourceService journalSourceService;

	@Autowired
	private transient JournalTrackService journalTrackService;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient MessageTemplateService messageTemplateService;

	@Autowired
	private transient VelocityTemplateService velocityTemplateService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertResponseServiceImpl.class);

	@Override
	protected EarlyAlertResponseDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertResponse create(
			@NotNull final EarlyAlertResponse earlyAlertResponse)
			throws ObjectNotFoundException, ValidationException {
		// Validate objects
		if (earlyAlertResponse == null) {
			throw new IllegalArgumentException(
					"EarlyAlertResponse must be provided.");
		}

		if (earlyAlertResponse.getEarlyAlert() == null) {
			throw new ValidationException(
					"EarlyAlert data must be provided.");
		}

		if (earlyAlertResponse.getEarlyAlert().getPerson() == null) {
			throw new ValidationException(
					"EarlyAlert Student data must be provided.");
		}

		// Create alert response
		final EarlyAlertResponse saved = getDao().save(earlyAlertResponse);

		// Send message response to Faculty from Coach
		try {
			sendMessageToFaculty(saved);
		} catch (final SendFailedException e) {
			throw new ValidationException(
					"Could not send response message to faculty. Response not saved.",
					e);
		}

		// Save a journal note for the Early Alert
		createJournalNoteForEarlyAlertResponse(saved);

		return saved;
	}

	@Override
	public EarlyAlertResponse save(final EarlyAlertResponse obj)
			throws ObjectNotFoundException {
		final EarlyAlertResponse current = getDao().get(obj.getId());

		current.setEarlyAlertOutcomeOtherDescription(obj
				.getEarlyAlertOutcomeOtherDescription());
		current.setComment(obj.getComment());

		if (obj.getEarlyAlert() == null) {
			current.setEarlyAlert(null);
		} else {
			current.setEarlyAlert(earlyAlertService.get(obj.getEarlyAlert()
					.getId()));
		}

		if (obj.getEarlyAlertOutcome() == null) {
			current.setEarlyAlertOutcome(null);
		} else {
			current.setEarlyAlertOutcome(earlyAlertOutcomeService.get(obj
					.getEarlyAlertOutcome().getId()));
		}

		final Set<EarlyAlertOutreach> earlyAlertOutreachs = new HashSet<EarlyAlertOutreach>();
		if (obj.getEarlyAlertOutreachIds() != null) {
			for (final EarlyAlertOutreach outreach : obj
					.getEarlyAlertOutreachIds()) {
				earlyAlertOutreachs.add(earlyAlertOutreachService.load(outreach
						.getId()));
			}
		}

		current.setEarlyAlertOutreachIds(earlyAlertOutreachs);

		final Set<EarlyAlertReferral> earlyAlertReferrals = new HashSet<EarlyAlertReferral>();
		if (obj.getEarlyAlertReferralIds() != null) {
			for (final EarlyAlertReferral outcome : obj
					.getEarlyAlertReferralIds()) {
				earlyAlertReferrals.add(earlyAlertReferralService.load(outcome
						.getId()));
			}
		}

		current.setEarlyAlertReferralIds(earlyAlertReferrals);

		return getDao().save(current);
	}

	@Override
	public PagingWrapper<EarlyAlertResponse> getAllForEarlyAlert(
			@NotNull final EarlyAlert earlyAlert, final SortingAndPaging sAndP) {
		return getDao().getAllForEarlyAlertId(earlyAlert.getId(), sAndP);
	}

	/**
	 * Send e-mail ({@link Message}) to the assigned advisor for the student.
	 * 
	 * @param earlyAlert
	 *            Early Alert
	 * @throws ObjectNotFoundException
	 *             If any referenced data could not be loaded.
	 * @throws SendFailedException
	 *             If the message could not be sent.
	 * @throws ValidationException
	 *             If any data or references were invalid.
	 */
	private void sendMessageToFaculty(
			@NotNull final EarlyAlertResponse earlyAlertResponse)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException {
		if (earlyAlertResponse == null) {
			throw new IllegalArgumentException(
					"Early alert response was missing.");
		}

		if (earlyAlertResponse.getEarlyAlert() == null) {
			throw new IllegalArgumentException("Early alert was missing.");
		}

		if (earlyAlertResponse.getEarlyAlert().getPerson() == null) {
			throw new IllegalArgumentException("EarlyAlert Person is missing.");
		}

		final Person person = earlyAlertResponse.getEarlyAlert().getPerson();
		final SubjectAndBody subjAndBody = messageTemplateService
				.createAdvisorConfirmationForEarlyAlertMessage(fillTemplateParameters(earlyAlertResponse));

		// Create and queue the message
		final Message message = messageService.createMessage(person, null,
				subjAndBody);

		LOGGER.info("Message {} created for EarlyAlertResponse {}", message,
				earlyAlertResponse);
	}

	/**
	 * Save a journal note for the Early Alert Response
	 * 
	 * @param earlyAlertResponse
	 *            Early Alert Response
	 * @throws ObjectNotFoundException
	 *             If any referenced data or message templates are missing or
	 *             could not be loaded.
	 * @throws ValidationException
	 *             If any data or references were invalid.
	 */
	private void createJournalNoteForEarlyAlertResponse(
			final EarlyAlertResponse earlyAlertResponse)
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlert earlyAlert = earlyAlertResponse.getEarlyAlert();
		final MessageTemplate messageTemplate = messageTemplateService
				.get(MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID);

		final Map<String, Object> templateParameters = fillTemplateParameters(earlyAlertResponse);

		final JournalEntry journalEntry = new JournalEntry();
		journalEntry.setPerson(earlyAlert.getPerson());
		journalEntry.setComment(velocityTemplateService
				.generateContentFromTemplate(
						messageTemplate.getBody(),
						messageTemplate.bodyTemplateId(), templateParameters));
		journalEntry.setEntryDate(new Date());
		journalEntry.setJournalSource(journalSourceService
				.get(JournalSource.JOURNALSOURCE_EARLYALERT_ID));
		journalEntry.setJournalTrack(journalTrackService
				.get(JournalTrack.JOURNALTRACK_EARLYALERT_ID));
		journalEntry.setConfidentialityLevel(confidentialityLevelService
				.get(ConfidentialityLevel.CONFIDENTIALITYLEVEL_EVERYONE));

		final JournalEntry saved = journalEntryService.create(journalEntry);

		LOGGER.info("JournalEntry {} created for EarlyAlertResponse {}", saved,
				earlyAlertResponse);
	}

	private Map<String, Object> fillTemplateParameters( // NOPMD by jon.adams
			@NotNull final EarlyAlertResponse earlyAlertResponse) {
		if (earlyAlertResponse == null) {
			throw new IllegalArgumentException(
					"EarlyAlertResponse was missing.");
		}

		final EarlyAlert earlyAlert = earlyAlertResponse.getEarlyAlert();

		// get basic template parameters from the early alert
		final Map<String, Object> templateParameters = earlyAlertService
				.fillTemplateParameters(earlyAlert);

		// add early alert response to the parameter list
		templateParameters.put("earlyAlertResponse", earlyAlertResponse);

		return templateParameters;
	}
}