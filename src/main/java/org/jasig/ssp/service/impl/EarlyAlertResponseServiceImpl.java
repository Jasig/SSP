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
package org.jasig.ssp.service.impl; // NOPMD

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.EarlyAlertResponseDao;
import org.jasig.ssp.factory.EarlyAlertResponseTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertOutreachTOFactory;
import org.jasig.ssp.factory.reference.EarlyAlertReferralTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.WatchStudent;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
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
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.EarlyAlertOutcomeService;
import org.jasig.ssp.service.reference.EarlyAlertOutreachService;
import org.jasig.ssp.service.reference.EarlyAlertReferralService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;
import org.jasig.ssp.transferobject.messagetemplate.EarlyAlertMessageTemplateTO;
import org.jasig.ssp.transferobject.messagetemplate.EarlyAlertOutcomeMessageTemplateTO;
import org.jasig.ssp.transferobject.messagetemplate.EarlyAlertResponseMessageTemplateTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertReferralTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertResponseCounts;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentOutreachReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentResponseOutcomeReportTO;
import org.jasig.ssp.transferobject.reports.EarlyAlertStudentSearchTO;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.DateTimeUtils;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


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

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertResponseServiceImpl.class);

    private static final String CONFIG_SHOW_SEND_CREATOR_EMAIL = "ear_show_send_faculty_email";
    private static final String CONFIG_SEND_ANY_EMAIL_TO_FACULTY = "send_faculty_mail";

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

	@Autowired
	private transient EarlyAlertResponseTOFactory earlyAlertResponseTOFactory;
	
	@Autowired
	private EarlyAlertReferralTOFactory earlyAlertReferralFactoryTO;
	
	@Autowired
	private EarlyAlertOutreachTOFactory earlyAlertOutreachFactoryTO;

	@Autowired
	private ConfigService configService;

	@Autowired
	private transient PersonService personService;
	
	@Override
	protected EarlyAlertResponseDao getDao() {
		return dao;
	}


	/**
	 *
	 * @deprecated Use {@link EarlyAlertResponseServiceImpl#create(org.jasig.ssp.transferobject.EarlyAlertResponseTO)}
	 * instead. <em>This has already been reimplemented to throw an
	 * <code>UnsupportedOperationException</code></em>
	 * @param earlyAlertResponse the EA response
	 * @return the newly created EA response
	 * @throws ObjectNotFoundException if data is not found
	 * @throws ValidationException if the data is invalid
	 */
	@Deprecated
	@Override
	public EarlyAlertResponse create(
			@NotNull final EarlyAlertResponse earlyAlertResponse)
			throws ObjectNotFoundException, ValidationException {

		throw new UnsupportedOperationException("Use the TO-based create() instead");

	}

	@Override
	@Transactional(rollbackFor = ValidationException.class)
	public EarlyAlertResponse create(@NotNull final EarlyAlertResponseTO obj)
			throws ValidationException {

		if (obj == null) {
			throw new IllegalArgumentException("Must specify an EarlyAlertResponseTO");
		}

		EarlyAlertResponse entity = null;
		try {

			entity = earlyAlertResponseTOFactory.from(obj);
			if ( entity.getEarlyAlert() == null ) {
				// might expect ObjectNotFoundException here, but impls
				// are not consistent in throwing that, plus we know this
				// is going to end up in the client, where an explicit
				// ValidationException results in a much better experience
				// (ObjectNotFoundException would likely be a nonsensical 404,
				// and NPEs are obviously obnoxious)
				throw new ValidationException(
						"Proposed EarlyAlertResponse did not reference a valid EarlyAlert record");
			}

		} catch ( ObjectNotFoundException e ) {
			throw new ValidationException(
					"Proposed EarlyAlertResponse referred to a non-existent record.", e);
		}

		// Might expect just a EaService.addResponse() call here, but
		// Hib assoc are EaResponse->Ea, not other way around. So really
		// EaService should know nothing about EaResponses, just that an Ea
		// needs to be closed (for whatever reason).
		final EarlyAlertResponse saved = getDao().save(entity);
		if ( obj.isClosed() ) {
			// Passing the ID instead of EA means the EaService will try to
			// look up the EA again, but that's cheap b/c we're in the same
			// Hib Session. So we stick w/ passing and ID b/c it's
			// consistent w/ our general rule of not accepting Domain
			// Entities in Service method args.
			try {
				earlyAlertService.closeEarlyAlert(saved.getEarlyAlert().getId());
			} catch ( ObjectNotFoundException e ) {
				throw new ValidationException(
						"Records related to the proposed EarlyAlertResponse " +
						"have gone missing. Probably deleted by another user.",
						e);
			}
		}else{
			try {
				earlyAlertService.openEarlyAlert(saved.getEarlyAlert().getId());
			} catch ( ObjectNotFoundException e ) {
				throw new ValidationException(
						"Records related to the proposed EarlyAlertResponse " +
						"have gone missing. Probably deleted by another user.",
						e);
			}
			
		}
		boolean sendCreatorEmail = true;
		if (configService.getByNameOrDefaultValue(CONFIG_SHOW_SEND_CREATOR_EMAIL)) {
			sendCreatorEmail = obj.isSendCreatorEmail();
		}
		afterEarlyAlertResponseCreate(saved, sendCreatorEmail);
		return saved;
	}

	private void afterEarlyAlertResponseCreate(EarlyAlertResponse saved, boolean sendCreatorEmail)
	throws ValidationException {
		// Save a journal note for the Early Alert. Do this first so all
		// related state is available when we go to issue notifications
		try {
			createJournalNoteForEarlyAlertResponse(saved);
		} catch (ObjectNotFoundException e) {
			throw new ValidationException(
					"Records necessary for generating a JournalNote for the " +
					"proposed EarlyAlertResponse have gone missing. " +
					"Probably deleted by another user.",
					e);
		}

		// Notifications stay here rather than in EaService again since
		// Ea's don't know about EaResponses and the use case is not that
		// Ea *closure* triggers mail, it's that any Ea *response* triggers
		// mail.
		try {
			sendEarlyAlertResponseNotifications(saved, sendCreatorEmail);
		} catch (final SendFailedException e) {
			throw new ValidationException(
					"Could not send EarlyAlertResponse notifications.",
					e);
		} catch ( final ObjectNotFoundException e ) {
			throw new ValidationException(
					"Records necessary for generating notifications for the " +
							"proposed EarlyAlertResponse have gone missing. " +
							"Probably deleted by another user.",
					e);
		}
	}

	@Override
	@Transactional(rollbackFor = ObjectNotFoundException.class)
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
	 * @param earlyAlertResponse
	 *            Early Alert
	 * @throws ObjectNotFoundException
	 *             If any referenced data could not be loaded.
	 * @throws SendFailedException
	 *             If the message could not be sent.
	 * @throws ValidationException
	 *             If any data or references were invalid.
	 */
	private void sendEarlyAlertResponseNotifications(
			@NotNull final EarlyAlertResponse earlyAlertResponse, boolean sendCreatorEmail)
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
		if (sendCreatorEmail) {
			final Person person = personService.get(earlyAlertResponse.getEarlyAlert().getCreatedBy().getId());
			if ( person == null ) {
				LOGGER.warn("EarlyAlert {} has no creator. Unable to send"
						+ " response email to faculty.",
						earlyAlertResponse.getEarlyAlert());
			} else {
				Set<String> watcherAddresses = new HashSet<String>(earlyAlertResponse.getEarlyAlert().getPerson().getWatcherEmailAddresses());
				String watcherEmails = org.springframework.util.StringUtils.arrayToCommaDelimitedString(watcherAddresses
						.toArray(new String[watcherAddresses.size()]));
				sendEarlyAlertResponseToFacultyMessageEmail(person, watcherEmails, earlyAlertResponse);
			}
		} else {

			for (WatchStudent watcher: earlyAlertResponse.getEarlyAlert().getPerson().getWatchers()) {
				sendEarlyAlertResponseToFacultyMessageEmail(watcher.getPerson(), null, earlyAlertResponse);
			}
		}

		Set<EarlyAlertReferral> referrals = earlyAlertResponse.getEarlyAlertReferralIds();
		
		for(EarlyAlertReferral referral:referrals){
			if(StringUtils.isNotBlank(referral.getRecipientEmailAddress()) || 
					StringUtils.isNotBlank(referral.getCarbonCopy())){
				final SubjectAndBody subjAndBody = messageTemplateService.createEarlyAlertResponseToReferralSourceMessage(fillReferralTemplateParameters(earlyAlertResponse, referral));
				
				final Message message = messageService.createMessage(referral.getRecipientEmailAddress(), referral.getCarbonCopy(),
						subjAndBody);

				LOGGER.info("Referral Message {} created for EarlyAlertResponse {} and Referral " + referral.getName(), message,
						earlyAlertResponse);
			}
		}
		
	}

	/**
	 * Common method to send the Early Alert Response message to the faculty and watchers
	 *
	 * @param to Person
	 * @param cc emailAddresses
	 * @param earlyAlertResponse EarlyAlertResponse
	 * @throws SendFailedException
	 * @throws ObjectNotFoundException
	 * @throws ValidationException
     */
	private void sendEarlyAlertResponseToFacultyMessageEmail (Person to, String cc, EarlyAlertResponse earlyAlertResponse) throws SendFailedException, ObjectNotFoundException, ValidationException {
		if (configService.getByNameOrDefaultValue(CONFIG_SEND_ANY_EMAIL_TO_FACULTY) == true) {
            final SubjectAndBody subjAndBody = messageTemplateService
                    .createEarlyAlertResponseToFacultyMessage(fillTemplateParameters(earlyAlertResponse));

            // Create and queue the message
            final Message message = messageService.createMessage(to, cc, subjAndBody);
            LOGGER.info("Message {} created for EarlyAlertResponse {}", message,
                    earlyAlertResponse);
        } else {
            LOGGER.debug("Skipping Faculty Early Alert Response Email: Config Turned Off");
            return;
        }
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
		journalEntry.setEntryDate(DateTimeUtils.midnight());
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
		Person creator = null;
		try {
			creator = personService.get(earlyAlertResponse.getCreatedBy().getId());
		} catch (ObjectNotFoundException e) {
			LOGGER.error("No creator found for early alert response :" + earlyAlertResponse.getCreatedBy().getId(), e);
		}
		EarlyAlertResponseMessageTemplateTO earTO = new EarlyAlertResponseMessageTemplateTO(earlyAlertResponse, creator);
		earTO.setEarlyAlertReferrals(earlyAlertReferralFactoryTO.asTOList(earlyAlertResponse.getEarlyAlertReferralIds()));
		earTO.setEarlyAlertOutreach(earlyAlertOutreachFactoryTO.asTOList(earlyAlertResponse.getEarlyAlertOutreachIds()));

		
		templateParameters.put("earlyAlertResponse", earTO);
		templateParameters.put("workPhone", earlyAlert.getPerson()
				.getWorkPhone());
		if ( earlyAlert.getPerson().getCoach() != null &&
				earlyAlert.getPerson().getCoach().getStaffDetails() != null ) {
			templateParameters.put("officeLocation", earlyAlert.getPerson()
					.getCoach().getStaffDetails().getOfficeLocation());
		}
 
		return templateParameters;
	}
	
	private Map<String, Object> fillReferralTemplateParameters(
			@NotNull final EarlyAlertResponse earlyAlertResponse, @NotNull final EarlyAlertReferral earlyAlertReferral) throws ObjectNotFoundException {
		if (earlyAlertResponse == null) {
			throw new IllegalArgumentException(
					"EarlyAlertResponse was missing.");
		}

		final EarlyAlert earlyAlert = earlyAlertResponse.getEarlyAlert();

		// get basic template parameters from the early alert
		final Map<String, Object> templateParameters = earlyAlertService
				.fillTemplateParameters(earlyAlert);
		
		Person creator = null;
		if(earlyAlert.getPerson().getCreatedBy() != null)
			creator = personService.get(earlyAlert.getCreatedBy().getId());
		
		templateParameters.put("earlyAlert", 
				new EarlyAlertMessageTemplateTO(earlyAlert, creator));
		// add early alert response to the parameter list
		templateParameters.put("earlyAlertResponse", new EarlyAlertResponseMessageTemplateTO(earlyAlertResponse, 
				personService.get(earlyAlertResponse.getCreatedBy().getId())));
		templateParameters.put("earlyAlertReferral", 
				new EarlyAlertReferralTO(earlyAlertReferral));
		
		EarlyAlertOutcome earlyAlertOutcome = earlyAlertResponse.getEarlyAlertOutcome();
		templateParameters.put("earlyAlertOutcome",  new EarlyAlertOutcomeMessageTemplateTO(earlyAlertOutcome, 
				personService.get(earlyAlertOutcome.getCreatedBy().getId())));
 
		return templateParameters;
	}


	@Override
	public Long getEarlyAlertResponseCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {		
		return dao.getEarlyAlertResponseCountForCoach(coach, createDateFrom, createDateTo, studentTypeIds);
	}
	
	@Override
	public Long getRespondedToEarlyAlertCountForResponseCreatedDateRange(Date createDateFrom, Date createDateTo, Campus campus, String rosterStatus) {
		return dao.getRespondedToEarlyAlertCountForResponseCreatedDateRange(createDateFrom, createDateTo, campus, rosterStatus);
	}

	@Override
	public Long getRespondedToEarlyAlertCountForEarlyAlertCreatedDateRange(String termCode, Date createDateFrom,
																		   Date createDateTo, Campus campus, String rosterStatus) {
		return dao.getRespondedToEarlyAlertCountForEarlyAlertCreatedDateRange(termCode, createDateFrom, createDateTo, campus, rosterStatus);
	}
	
	public Collection<EarlyAlertStudentOutreachReportTO> getEarlyAlertOutreachCountByOutcome(String alertTermCode, Date alertCreateDateFrom,
			Date alertCreateDateTo, Date responseCreateDateFrom, Date responseCreateDateTo, List<UUID> outcomes, String rosterStatus, Person coach){
		return dao.getEarlyAlertOutreachCountByOutcome(alertTermCode, alertCreateDateFrom, alertCreateDateTo,
                responseCreateDateFrom, responseCreateDateTo, outcomes, rosterStatus, coach);
	}
	
	@Override
	public List<EarlyAlertStudentReportTO> getPeopleByEarlyAlertReferralIds(EarlyAlertStudentSearchTO earlyAlertStudentSearchTO, SortingAndPaging sAndP)
			throws ObjectNotFoundException {
		
		return dao.getPeopleByEarlyAlertReferralIds(earlyAlertStudentSearchTO, sAndP);
	}
	
	@Override
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentEarlyAlertResponseCountByCoaches(EntityCountByCoachSearchForm form){
		return dao.getStudentEarlyAlertResponseCountByCoaches(form);
	}
	
	@Override
	public EarlyAlertResponseCounts getCountEarlyAlertRespondedToForEarlyAlerts(List<UUID> earlyAlertIds){
		return dao.getCountEarlyAlertRespondedToForEarlyAlerts(earlyAlertIds);
	}
	
	@Override
	public EarlyAlertResponseCounts getCountEarlyAlertRespondedToForEarlyAlertsByOutcome(List<UUID> earlyAlertIds, UUID outcomeId)
	{
		return dao.getCountEarlyAlertRespondedToForEarlyAlertsByOutcome(earlyAlertIds, outcomeId);
	}
	
	@Override
	public Long getEarlyAlertOutcomeTypeCountByCriteria(String outcomeType,UUID outcomeId,
			EarlyAlertStudentSearchTO searchForm) throws ObjectNotFoundException{
		return dao.getEarlyAlertOutcomeTypeCountByCriteria(outcomeType, outcomeId, searchForm);
	}
	
	@Override
	public 	List<EarlyAlertStudentResponseOutcomeReportTO> getEarlyAlertResponseOutcomeTypeForStudentsByCriteria(
			String outcomeType, EarlyAlertStudentSearchTO searchForm, SortingAndPaging sAndP, SortingAndPaging reportPersonSAndP){
		
		return dao.getEarlyAlertResponseOutcomeTypeForStudentsByCriteria(outcomeType, searchForm, sAndP, reportPersonSAndP);
	}
	
	public Long getEarlyAlertCountByOutcomeCriteria(EarlyAlertStudentSearchTO searchForm)  throws ObjectNotFoundException{
		return dao.getEarlyAlertCountByOutcomeCriteria(searchForm);
	}

}
