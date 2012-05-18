package org.jasig.ssp.service.impl; // NOPMD

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.EarlyAlertResponseDao;
import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.JournalEntryJournalStepDetail;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.model.reference.EarlyAlertReferral;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * EarlyAlertResponse service implementation
 * 
 * @author jon.adams
 * 
 */
@Service
@Transactional
public class EarlyAlertResponseServiceImpl extends
		AbstractAuditableCrudService<EarlyAlertResponse>
		implements EarlyAlertResponseService {

	@Autowired
	private transient EarlyAlertResponseDao dao;

	@Autowired
	private transient EarlyAlertOutcomeService earlyAlertOutcomeService;

	@Autowired
	private transient EarlyAlertOutreachService earlyAlertOutreachService;

	@Autowired
	private transient EarlyAlertReferralService earlyAlertReferralService;

	@Autowired
	private transient EarlyAlertService earlyAlertService;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient JournalEntryService journalEntryService;

	@Autowired
	private transient JournalSourceService journalSourceService;

	@Autowired
	private transient JournalTrackService journalTrackService;

	@Autowired
	private transient MessageTemplateDao messageTemplateDao;

	@Autowired
	private transient VelocityTemplateService velocityTemplateService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertServiceImpl.class);

	@Override
	protected EarlyAlertResponseDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertResponse create(
			@NotNull final EarlyAlertResponse earlyAlert)
			throws ObjectNotFoundException, ValidationException {
		// Create alert response
		final EarlyAlertResponse saved = getDao().save(earlyAlert);

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
	public PagingWrapper<EarlyAlertResponse> getAllForPerson(
			final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
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
	 */
	private void createJournalNoteForEarlyAlertResponse(
			final EarlyAlertResponse earlyAlertResponse)
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlert earlyAlert = earlyAlertResponse.getEarlyAlert();
		final MessageTemplate messageTemplate = messageTemplateDao
				.get(MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID);

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
		detail.setName("Early Alert Response Sent");
		detail.setDescription(velocityTemplateService
				.generateContentFromTemplate(
						messageTemplate.bodyTemplateId(),
						messageTemplate.getSubject(), templateParameters));

		final Set<JournalEntryJournalStepDetail> journalEntryJournalStepDetails = Sets
				.newHashSet();
		journalEntryJournalStepDetails.add(new JournalEntryJournalStepDetail(
				journalEntry, detail));
		journalEntry
				.setJournalEntryJournalStepDetails(journalEntryJournalStepDetails);
		journalEntry.setComment("");
		journalEntry.setEntryDate(new Date());
		journalEntry.setJournalSource(journalSourceService
				.get(JournalSource.JOURNALSOURCE_EARLYALERT_ID));
		journalEntry.setJournalTrack(journalTrackService
				.get(JournalTrack.JOURNALTRACK_EARLYALERT_ID));
		journalEntry.setPerson(earlyAlert.getPerson().getCoach());

		journalEntry.setConfidentialityLevel(confidentialityLevelService
				.get(ConfidentialityLevel.CONFIDENTIALITYLEVEL_EVERYONE));

		journalEntryService.create(journalEntry);

		LOGGER.info("JournalEntry {} created for EarlyAlertResponse {}"
				, journalEntry, earlyAlertResponse);
	}
}