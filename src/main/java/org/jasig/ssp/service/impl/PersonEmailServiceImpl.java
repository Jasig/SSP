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
import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonEmailService;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.jobqueue.AbstractPersonSearchBasedJobExecutor;
import org.jasig.ssp.service.jobqueue.AbstractPersonSearchBasedJobQueuer;
import org.jasig.ssp.service.jobqueue.BasePersonSearchBasedJobExecutionState;
import org.jasig.ssp.service.jobqueue.JobExecutionResult;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.EmailRequestTO;
import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;
import org.jasig.ssp.transferobject.MessageTO;
import org.jasig.ssp.transferobject.form.BulkEmailJobSpec;
import org.jasig.ssp.transferobject.form.BulkEmailStudentRequestForm;
import org.jasig.ssp.transferobject.form.EmailAddress;
import org.jasig.ssp.transferobject.form.EmailStudentRequestForm;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.SendFailedException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class PersonEmailServiceImpl implements PersonEmailService {

	private static final Logger OUTER_CLASS_LOGGER = LoggerFactory
			.getLogger(PersonEmailServiceImpl.class);

	private static final ThreadLocal<Logger> CURRENT_LOGGER = new ThreadLocal<Logger>();

	public static final String BULK_EMAIL_JOB_EXECUTOR_NAME = "bulk-email-executor";
	private static final String BULK_MESSAGES_MAX_MESSAGES_CONFIG_NAME = "mail_bulk_message_limit";
	private static final String BULK_MESSAGES_BATCH_SIZE_CONFIG_NAME = "mail_bulk_message_batch_size";
	private static final String BULK_MESSAGES_MAX_DLQ_SIZE_CONFIG_NAME = "mail_bulk_message_max_dlq_size";
	private static final String BULK_MESSAGES_FAIL_ON_DLQ_OVERFLOW_CONFIG_NAME = "mail_bulk_message_fail_on_dlq_overflow";

	// Careful when changing these messages; might be code looking at them to figure out what happened when
	// a ValidationException occurs
	private static final String MISSING_ANY_VALID_DELIVERY_ADDR_ERROR_MSG = "At least one valid email address must be provided.";
	private static final String MISSING_NON_CC_DELIVERY_ADDR_ERROR_MSG = "At least one valid non-CC email address must be provided.";
	private static final String MISSING_SUBJECT_ERROR_MSG =  "Email subject must be provided";
	private static final String MISSING_BODY_ERROR_MSG = "Email body must be provided";
	private static final String MESSAGE_ID_CREATED_FIELD_NAME = "messageId";
	private static final String JOURNAL_ENTRY_ID_CREATED_FIELD_NAME = "journalEntryId";

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient JournalEntryService journalEntryService;

	@Autowired
	private transient JournalSourceService journalSourceService;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient ConfigService configService;

	@Autowired
	private transient JobService jobService;

	@Autowired
	private transient PlatformTransactionManager transactionManager;

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient PersonSearchRequestTOFactory personSearchRequestFactory;

	@Autowired
	private transient PersonSearchService personSearchService;

	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient MessageTemplateService messageTemplateService;

	@Autowired
	private transient VelocityTemplateService velocityTemplateService;

	private static class BulkEmailJobExecutionState extends BasePersonSearchBasedJobExecutionState {
		public int emailSentCount;
		public int journalEntriesCreatedCount;
	}

	private AbstractPersonSearchBasedJobExecutor<BulkEmailJobSpec, BulkEmailJobExecutionState> bulkEmailJobExecutor;

	private AbstractPersonSearchBasedJobQueuer<BulkEmailStudentRequestForm, BulkEmailJobSpec> bulkEmailJobQueuer;

	@Override
	@Transactional
	public Map<String, UUID> emailStudent(EmailStudentRequestForm emailRequest) throws ObjectNotFoundException, ValidationException {
		return doEmailStudent(emailRequest, EmailVolume.SINGLE, null);
	}

	private Map<String,UUID> doEmailStudent(EmailStudentRequestForm emailRequest, EmailVolume originalRequestVolume,
											ImmutablePersonIdentifiersTO studentIds) throws ObjectNotFoundException, ValidationException {
		boolean sendMessage = true;
		try {
			validateSingleEmailInput(emailRequest, originalRequestVolume);
		} catch ( ValidationException e ) {
			// fatal unless you're in bulk mode and we're just missing non-cc delivery addrs; in that case we
			// still want to try to generate a journal entry
			if ( originalRequestVolume != EmailVolume.BULK || !(MISSING_NON_CC_DELIVERY_ADDR_ERROR_MSG.equals(e.getMessage())) ) {
				throw e;
			} else {
				getCurrentLogger().debug("Skipping email message creation for person [{}] because their record" +
						" lacks the necessary email delivery address/es. This will not necessarily prevent Journal" +
						" Entry creation.",
						(emailRequest.getStudentId() == null ? studentIds : emailRequest.getStudentId()));
				sendMessage = false;
			}
		}

		final Map<String,UUID> rslt = Maps.newLinkedHashMap();
		final boolean createJournalEntry = shouldCreateJournalEntryFor(emailRequest, originalRequestVolume);
		final boolean buildMessage = createJournalEntry || sendMessage;

		final Message message = buildMessage ? buildStudentEmail(emailRequest, originalRequestVolume, sendMessage) : null;
		rslt.put(MESSAGE_ID_CREATED_FIELD_NAME, message == null ? null : message.getId());

		if ( createJournalEntry ) {
			final JournalEntry journalEntry = buildJournalEntry(emailRequest, message, originalRequestVolume);
			rslt.put(JOURNAL_ENTRY_ID_CREATED_FIELD_NAME, journalEntry == null ? null : journalEntry.getId());
		} else {
			getCurrentLogger().debug("Skipping Journal Entry creation for person [{}] either because they have no" +
					" operational record or because the original messaging request declined Journal Entry creation.",
					(emailRequest.getStudentId() == null ? studentIds : emailRequest.getStudentId()));
		}

		return rslt;
	}

	@Override
	@Transactional(rollbackFor = {ObjectNotFoundException.class, IOException.class, ValidationException.class})
	public JobTO emailStudentsInBulk(BulkEmailStudentRequestForm emailRequest) throws ObjectNotFoundException, IOException, ValidationException, SecurityException {
		return bulkEmailJobQueuer.enqueueJob(emailRequest);
	}

	/**
	 * Intentionally private b/c we do not want this to participate in this service's "transactional-by-default"
	 * public interface. Its transaction is managed by the {@code JobExecutor} assumed to be invoking it.
	 *
	 */
	private Map<String, ?> sendSingleBulkEmail(ImmutablePersonIdentifiersTO deliveryTargetIds, BulkEmailStudentRequestForm spec)
			throws ObjectNotFoundException, ValidationException {
		final Person student;
		if (deliveryTargetIds.getId() != null) {
			student = personService.get(deliveryTargetIds.getId());
			if ( student == null ) {
				throw new ObjectNotFoundException(deliveryTargetIds.getId(), Person.class.getName());
			}
		} else {
			student = personService.getBySchoolId(deliveryTargetIds.getSchoolId(), false);
			if ( student == null ) {
				throw new ObjectNotFoundException(deliveryTargetIds.getSchoolId(), Person.class.getName());
			}
		}
		EmailStudentRequestForm emailStudentRequestForm = new EmailStudentRequestForm(spec,student);
		return doEmailStudent(emailStudentRequestForm, EmailVolume.BULK, deliveryTargetIds);
	}

	private boolean shouldCreateJournalEntryFor(EmailStudentRequestForm emailRequest, EmailVolume originalRequestVolume) {
		return emailRequest.getCreateJournalEntry() && emailRequest.getStudentId() != null;
	}

	private String buildJournalEntryCommentFromEmail(
			EmailStudentRequestForm emailRequest, Message message) throws ObjectNotFoundException {
		MessageTO mTO = new MessageTO(message);
		
		EmailRequestTO erTO = new EmailRequestTO();
		erTO.setEmailSubject(emailRequest.getEmailSubject());
		erTO.setEmailBody(emailRequest.getEmailBody());
		
		final MessageTemplate messageTemplate = messageTemplateService
				.get(MessageTemplate.EMAIL_JOURNAL_ENTRY_ID);

		Map<String, Object> templateParameters = new HashMap<String, Object>();
		templateParameters.put("message", mTO);
		templateParameters.put("emailRequest", erTO);
		return velocityTemplateService
				.generateContentFromTemplate(
						messageTemplate.getBody(),
						messageTemplate.bodyTemplateId(), templateParameters);
	}
	
	private JournalEntry buildJournalEntry(
			EmailStudentRequestForm emailRequest, Message message, EmailVolume originalRequestVolume)
			throws ObjectNotFoundException, ValidationException {
		Person student = personService.get(emailRequest.getStudentId());
		
		JournalEntry journalEntry = new JournalEntry();
		journalEntry.setPerson(student);
		
		String commentFromEmail = buildJournalEntryCommentFromEmail(emailRequest, message);

		ConfidentialityLevel confidentialityLevel;
		if(emailRequest.getConfidentialityLevelId() == null)
		{
			confidentialityLevel = confidentialityLevelService.get(ConfidentialityLevel.CONFIDENTIALITYLEVEL_EVERYONE);
		}
		else
		{
			confidentialityLevel = confidentialityLevelService.get(emailRequest.getConfidentialityLevelId());
		}
		journalEntry.setConfidentialityLevel(confidentialityLevel);
		journalEntry.setComment(commentFromEmail);

		journalEntry.setEntryDate(new Date());
		journalEntry.setJournalSource(journalSourceService.get(JournalSource.JOURNALSOURCE_EMAIL_ID));
		journalEntry = journalEntryService.save(journalEntry);
		return journalEntry;
	}

	private Message buildStudentEmail(EmailStudentRequestForm emailRequest, EmailVolume originalRequestVolume, boolean andSend)
			throws ObjectNotFoundException {
		final EmailAddress addresses = emailRequest.getValidDeliveryAddresses(true);
		final SubjectAndBody subjectAndBody = new SubjectAndBody(emailRequest.getEmailSubject(), emailRequest.getEmailBody());
		return andSend ?
				messageService.createMessage(addresses.getTo(), addresses.getCc(), subjectAndBody) :
				messageService.createMessageNoSave(addresses.getTo(), addresses.getCc(), subjectAndBody);
	}

	/**
	 * When a message in a bulk email batch is sent, the spec is first translated from a
	 * {@link BulkEmailStudentRequestForm} into a {@link EmailStudentRequestForm}, which
	 * then passes through this validation mechanism. But the rules are different when considering a single-
	 * vs bulk message spec. For bulk messages we never want to send a message to the CC *only*, but this
	 * is not necessarily a fatal problem for the entire bulk messaging unit of work for that specific delivery
	 * target, e.g. might still want to create a Journal Entry.
	 *
	 * @param emailRequest
	 * @param originalRequestVolume
	 * @throws ValidationException
	 */
	private void validateSingleEmailInput(EmailStudentRequestForm emailRequest, EmailVolume originalRequestVolume) throws ValidationException {
		StringBuilder validationMsg = new StringBuilder();
		String EOL = System.getProperty("line.separator");

		// Removed a historical validation that required a studentId (UUID) on emailRequest. We don't actually need that
		// and we can't require it since we now reuse EmailStudentRequestForm when sending bulk email, which may
		// target external-only students, i.e. persons without UUIDs.
		//
		// Also, as hinted at in class comments and a comment attached to these MSG constants... be careful when
		// changing how error messages are emitted... there might be code looking at them to try to figure out
		// what failed to validate.

		if(!emailRequest.hasEmailSubject())
		{
			validationMsg.append(MISSING_SUBJECT_ERROR_MSG).append(EOL);
		}
		if(!emailRequest.hasEmailBody())
		{
			validationMsg.append(MISSING_BODY_ERROR_MSG).append(EOL);
		}

		if ( originalRequestVolume == null ) {
			throw new IllegalArgumentException("Must specify an EmailVolume"); // programmer error
		}

		if(originalRequestVolume == EmailVolume.SINGLE && !emailRequest.hasValidDeliveryAddresses()){
			validationMsg.append(MISSING_ANY_VALID_DELIVERY_ADDR_ERROR_MSG).append(EOL);
		} else if ( originalRequestVolume == EmailVolume.BULK && !emailRequest.hasValidNonCcDeliveryAddress() ) {
			validationMsg.append(MISSING_NON_CC_DELIVERY_ADDR_ERROR_MSG);
		}

		String validation = validationMsg.toString();
		if(org.apache.commons.lang.StringUtils.isNotBlank(validation)){
			throw new ValidationException(validation);
		}
	}

	private void validateBulkEmailInput(BulkEmailStudentRequestForm emailRequest) throws ValidationException {
		StringBuilder validationMsg = new StringBuilder();
		String EOL = System.getProperty("line.separator");
		if(!emailRequest.hasEmailSubject())
		{
			validationMsg.append("Email subject must be provided").append(EOL);
		}
		if(!emailRequest.hasEmailBody())
		{
			validationMsg.append("Email body must be provided").append(EOL);
		}
		if(!emailRequest.hasNonCcDeliveryAddress()) {
			validationMsg.append("Non-cc email delivery addresses must be provided").append(EOL);
		}
		String validation = validationMsg.toString();
		if(org.apache.commons.lang.StringUtils.isNotBlank(validation)){
			throw new ValidationException(validation);
		}
	}

	@Override
	@Transactional
	public void sendCoachingAssignmentChangeEmail(Person model, UUID oldCoachId) throws ObjectNotFoundException, SendFailedException, ValidationException {

		if(oldCoachId == null || model.getCoach() == null || !model.getCoach().hasEmailAddresses())
			return;
		Person oldCoach = personService.get(oldCoachId);
		String appTitle = configService.getByNameEmpty("app_title");
		String serverExternalPath = configService.getByNameEmpty("serverExternalPath");

		String message = oldCoach.getFullName()+" has assigned "+model.getFullName()+" to your caseload in "+appTitle+". Please visit "+serverExternalPath+" to view the student's information in "+appTitle+".";
		String subject = "A coaching assignment has changed in "+appTitle;

		SubjectAndBody subjectAndBody = new SubjectAndBody(subject, message);
		if(oldCoach.hasEmailAddresses() && model.getWatcherEmailAddresses().isEmpty()){
			messageService.createMessage(model.getCoach(),
					StringUtils.arrayToCommaDelimitedString(oldCoach.getEmailAddresses()
							.toArray(new String[oldCoach.getEmailAddresses().size()])), subjectAndBody);
		} else
		if(oldCoach.hasEmailAddresses() && !model.getWatcherEmailAddresses().isEmpty()){
			Set<String> emails = new HashSet<String>();
			emails.addAll(oldCoach.getEmailAddresses());
			emails.addAll(model.getWatcherEmailAddresses());
			messageService.createMessage(model.getCoach(),
					StringUtils.arrayToCommaDelimitedString(emails
							.toArray(new String[emails.size()])), subjectAndBody);
		} else
		if(!oldCoach.hasEmailAddresses() && model.getWatcherEmailAddresses().isEmpty()){
			messageService.createMessage(model.getCoach(),
					StringUtils.arrayToCommaDelimitedString(model.getWatcherEmailAddresses()
							.toArray(new String[model.getWatcherEmailAddresses().size()])), subjectAndBody);
		}
		else{
			messageService.createMessage(model.getCoach(),"", subjectAndBody);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initBulkEmailJobExecutor();
		initBulkEmailJobQueuer();
	}

	private void initBulkEmailJobExecutor() {

		this.bulkEmailJobExecutor =  new AbstractPersonSearchBasedJobExecutor<BulkEmailJobSpec, BulkEmailJobExecutionState>(
				BULK_EMAIL_JOB_EXECUTOR_NAME, jobService, transactionManager, null, personSearchService, personSearchRequestFactory, configService
		) {

			private final Logger logger = LoggerFactory.getLogger(PersonEmailServiceImpl.this.getClass().getName() + ".BulkEmailJobExecutor");

			/**
			 * The actual 'important' override... all the rest of the overrides are mostly boilerplate.
			 */
			@Override
			protected Map<String, ?> executeForSinglePerson(ImmutablePersonIdentifiersTO personIds, BulkEmailJobSpec executionSpec,
															BulkEmailJobExecutionState executionState, UUID jobId) throws ValidationException, ObjectNotFoundException {
				return sendSingleBulkEmail(personIds, executionSpec.getCoreSpec());
			}

			@Override
			protected JobExecutionResult<BulkEmailJobExecutionState> executeJobDeserialized(BulkEmailJobSpec executionSpec,
																							BulkEmailJobExecutionState executionState, UUID jobId) {
				// TODO this copy pasted all over in these executor subclasses... abstract somehow
				try {
					PersonEmailServiceImpl.this.setCurrentLogger(logger);
					return super.executeJobDeserialized(executionSpec, executionState, jobId);
				} finally {
					PersonEmailServiceImpl.this.setCurrentLogger(null);
				}
			}

			@Override
			protected void recordSuccessful(ImmutablePersonIdentifiersTO personIds, Map<String, ?> results,
												 BulkEmailJobSpec executionSpec, BulkEmailJobExecutionState executionState, UUID jobId) {
				super.recordSuccessful(personIds, results, executionSpec, executionState, jobId);
				if ( results.get(MESSAGE_ID_CREATED_FIELD_NAME) != null ) {
					executionState.emailSentCount++;
				}
				if ( results.get(JOURNAL_ENTRY_ID_CREATED_FIELD_NAME) != null ) {
					executionState.journalEntriesCreatedCount++;
				}
			}

			@Override
			protected String decorateProcessingCompleteLogMessage(String baseMsg, BulkEmailJobExecutionState executionState) {
				return new StringBuilder(baseMsg).append(" Emails sent: [").append(executionState.emailSentCount)
						.append("]. Journal entries created: [").append(executionState.journalEntriesCreatedCount)
						.append("]").toString();
			}

			@Override
			protected BulkEmailJobExecutionState newBulkEmailJobExecutionState() {
				return new BulkEmailJobExecutionState();
			}

			@Override
			protected BulkEmailJobSpec deserializeJobSpecWithCheckedExceptions(String jobSpecStr) throws Exception {
				return getObjectMapper().readValue(jobSpecStr, BulkEmailJobSpec.class);
			}

			@Override
			protected BulkEmailJobExecutionState deserializeJobStateWithCheckedExceptions(String jobStateStr) throws Exception {
				return getObjectMapper().readValue(jobStateStr, BulkEmailJobExecutionState.class);
			}

			@Override
			protected Logger getCurrentLogger() {
				return PersonEmailServiceImpl.this.getCurrentLogger();
			}

			@Override
			protected String getPageSizeConfigName() {
				return BULK_MESSAGES_BATCH_SIZE_CONFIG_NAME;
			}

			@Override
			protected String getDlqSizeConfigName() {
				return BULK_MESSAGES_MAX_DLQ_SIZE_CONFIG_NAME;
			}

			@Override
			protected String getFailOnSlqOverflowConfigName() {
				return BULK_MESSAGES_FAIL_ON_DLQ_OVERFLOW_CONFIG_NAME;
			}
		};

		this.jobService.registerJobExecutor(this.bulkEmailJobExecutor);
	}

	private void initBulkEmailJobQueuer() {
		if ( this.bulkEmailJobExecutor == null ) {
			// programmer error
			throw new IllegalStateException("Bulk email JobExecutor not yet initialized");
		}
		bulkEmailJobQueuer = new AbstractPersonSearchBasedJobQueuer<BulkEmailStudentRequestForm, BulkEmailJobSpec>(this.bulkEmailJobExecutor,
				securityService, personSearchRequestFactory, personSearchService) {
			@Override
			protected BulkEmailStudentRequestForm validateJobRequest(BulkEmailStudentRequestForm jobRequest) throws ValidationException {
				validateBulkEmailInput(jobRequest);
				return jobRequest;
			}

			@Override
			protected void validatePersonSearchResults(long searchResultCount, PersonSearchRequest searchRequest, Person currentSspPerson)
					throws ValidationException {
				super.validatePersonSearchResults(searchResultCount, searchRequest, currentSspPerson);
				final int maxMessages = configService.getByNameExceptionOrDefaultAsInt(BULK_MESSAGES_MAX_MESSAGES_CONFIG_NAME);
				if ( maxMessages > 0 && searchResultCount > maxMessages ) {
					throw new ValidationException("Too many person search results: " + searchResultCount +
							". Limit: " + maxMessages + ". Can't send message.");
				}
			}

			@Override
			protected BulkEmailJobSpec newJobSpec(BulkEmailStudentRequestForm jobRequest, Person currentSspPerson, PersonSearchRequest searchRequest, long searchResultCount) {
				return new BulkEmailJobSpec(jobRequest);
			}
		};
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
