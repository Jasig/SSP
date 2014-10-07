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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonEmailService;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.jobqueue.JobExecutionResult;
import org.jasig.ssp.service.jobqueue.JobExecutionStatus;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.jobqueue.impl.AbstractJobExecutor;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.JournalSourceService;
import org.jasig.ssp.transferobject.ImmutablePersonIdentifiersTO;
import org.jasig.ssp.transferobject.form.BulkEmailJobSpec;
import org.jasig.ssp.transferobject.form.BulkEmailStudentRequestForm;
import org.jasig.ssp.transferobject.form.EmailAddress;
import org.jasig.ssp.transferobject.form.EmailStudentRequestForm;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class PersonEmailServiceImpl implements PersonEmailService {

	public static final String BULK_EMAIL_JOB_EXECUTOR_NAME = "bulk-email-executor";
	private static final String BULK_MESSAGES_MAX_MESSAGES_CONFIG_NAME = "mail_bulk_message_limit";
	private static final String BULK_MESSAGES_BATCH_SIZE_CONFIG_NAME = "mail_bulk_message_batch_size";
	private static final String BULK_MESSAGES_MAX_DLQ_SIZE_CONFIG_NAME = "mail_bulk_message_max_dlq_size";
	private static final String BULK_MESSAGES_FAIL_ON_DLQ_OVERFLOW_CONFIG_NAME = "mail_bulk_message_fail_on_dlq_overflow";

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


	private AbstractJobExecutor<BulkEmailJobSpec, BulkEmailJobExecutionState> bulkEmailJobExecutor;

	@Override
	public void afterPropertiesSet() throws Exception {
		registerBulkEmailJobExecutor();
	}

	@Override
	@Transactional
	public Map<String,UUID> emailStudent(EmailStudentRequestForm emailRequest) throws ObjectNotFoundException, ValidationException {
		validateInput(emailRequest);
		final Message message = buildAndSendStudentEmail(emailRequest);
		final JournalEntry journalEntry = buildJournalEntry(emailRequest, message);
		final Map<String,UUID> rslt = Maps.newLinkedHashMap();
		rslt.put("messageId", message.getId());
		if ( journalEntry != null ) {
			rslt.put("journalEntryId", journalEntry.getId());
		}
		return rslt;
	}

	@Override
	@Transactional
	public JobTO emailStudentsInBulk(BulkEmailStudentRequestForm emailRequest) throws ObjectNotFoundException, IOException, ValidationException, SecurityException {
		final SspUser currentSspUser = securityService.currentlyAuthenticatedUser();
		if ( currentSspUser == null ) {
			throw new SecurityException("Anonymous user cannot generate bulk email");
		}
		final Person currentSspPerson = currentSspUser.getPerson();
		validateInput(emailRequest);
		PersonSearchRequest criteria = personSearchRequestFactory.from(emailRequest.getCriteria());
		final SortingAndPaging origSortAndPage = criteria.getSortAndPage();
		SortingAndPaging validatedSortAndPage = origSortAndPage;
		if ( origSortAndPage == null ) {
			validatedSortAndPage = SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ACTIVE,
					0, 100, // these two don't matter, will be overwritten when the job actually runs
					"dp.lastName", SortDirection.ASC.name(), null);
		} else if ( !(origSortAndPage.isSorted()) && !(origSortAndPage.isDefaultSorted()) ) {
			validatedSortAndPage = SortingAndPaging.createForSingleSortWithPaging(origSortAndPage.getStatus(),
					origSortAndPage.getFirstResult(), origSortAndPage.getMaxResults(),
					"dp.lastName", SortDirection.ASC.name(), null);
		}
		criteria.setSortAndPage(validatedSortAndPage);
		final Long tmp = personSearchService.searchPersonDirectoryCount(criteria);
		final long deliveryTargetCnt = tmp == null ? 0L : tmp.longValue();
		if ( deliveryTargetCnt <= 0 ) {
			// TODO would be nice to have a better way of representing a no-op job, b/c an exception is really
			// overly unfriendly, but a null return is so non-descriptive as to be useless. So we opt for a
			// ValidationException so the client *knows* nothing really happened.
			throw new ValidationException("Person search parameters matched no records. Can't send message.");
		}
		if ( deliveryTargetCnt > (long)Integer.MAX_VALUE ) {
			throw new ValidationException("Too many person search results : " + deliveryTargetCnt +
					". Can't send message.");
		}
		final int maxMessages = configService.getByNameExceptionOrDefaultAsInt(BULK_MESSAGES_MAX_MESSAGES_CONFIG_NAME);
		if ( maxMessages > 0 && deliveryTargetCnt > maxMessages ) {
			throw new ValidationException("Too many person search results: " + deliveryTargetCnt +
					". Limit: " + maxMessages + ". Can't send message.");
		}
		emailRequest.getCriteria().setSortAndPage(validatedSortAndPage);
		final BulkEmailJobSpec jobSpec = new BulkEmailJobSpec(emailRequest);
		final Job job = this.bulkEmailJobExecutor.queueNewJob(currentSspPerson.getId(), currentSspPerson.getId(), jobSpec);
		return new JobTO(job);
	}

	private static class BulkEmailJobExecutionState {
		public Integer prevPage;
		public int pageSize = 25;
		public boolean allPagesProcessed;
		public List<ImmutablePersonIdentifiersTO> retryQueue = Lists.newArrayListWithExpectedSize(100);
		public List<ImmutablePersonIdentifiersTO> dlq = Lists.newArrayListWithExpectedSize(10);
		public int maxDlqLength = 100;
		public boolean failOnDlqOverflow;
		public boolean dlqOverflowed;
		public int emailSentCount;
		public int emailFailedCount;
	}

	private BulkEmailJobExecutionState newBulkEmailJobExecutionState() {
		final BulkEmailJobExecutionState state = new BulkEmailJobExecutionState();
		state.pageSize = configService.getByNameExceptionOrDefaultAsInt(BULK_MESSAGES_BATCH_SIZE_CONFIG_NAME);
		state.maxDlqLength = configService.getByNameExceptionOrDefaultAsInt(BULK_MESSAGES_MAX_DLQ_SIZE_CONFIG_NAME);
		state.failOnDlqOverflow =
				Boolean.parseBoolean(configService.getByNameNullOrDefaultValue(BULK_MESSAGES_FAIL_ON_DLQ_OVERFLOW_CONFIG_NAME));
		return state;
	}

	/**
	 * Intentionally private b/c we do not want this to participate in this service's "transactional-by-default"
	 * public interface. Its transaction is managed by the {@code JobExecutor} assumed to be invoking it.
	 *
	 * @param executionSpec
	 * @param executionState
	 * @return
	 */
	private JobExecutionResult<BulkEmailJobExecutionState> executeBulkEmailJob(BulkEmailJobSpec executionSpec,
																			   BulkEmailJobExecutionState executionState,
																			   UUID jobId,
																			   Logger logger) {

		// TODO note that all of this could probably be templatized for reuse in other batched functions
		try {

			if ( executionState == null ) {
				executionState = newBulkEmailJobExecutionState();
			}

			if ( executionState.allPagesProcessed && executionState.retryQueue.isEmpty() ) {
				logger.debug("Processing complete for Job {}.", jobId, executionState.retryQueue.size());
				return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.DONE, executionState, null);
			}

			// process retry queue first so it doesn't grow indefinitely and we hit fail threshholds as early
			// as possible
			if ( !(executionState.retryQueue.isEmpty()) ) {

				logger.debug("Processing retry queue for Job {}. Size: {}", jobId, executionState.retryQueue.size());
				// retry queue gets processed one element per transaction to try to weed out bad apples that failed
				// larger batches
				final ImmutablePersonIdentifiersTO personIds = executionState.retryQueue.remove(0);
				try {
					logger.debug("Retry for person IDs {} in Job {}.", personIds, jobId);
					doSendEmail(personIds, executionSpec.getCoreSpec());
					executionState.emailSentCount++;
					logger.debug("Successful retry for person IDs {} in Job {}. Total sent so far: {}",
							new Object[] { personIds, jobId, executionState.emailSentCount });
					return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.PARTIAL, executionState, null);
				} catch ( Exception e ) {
					executionState.emailFailedCount++;
					if ( executionState.dlq.size() >= executionState.maxDlqLength ) {
						executionState.dlqOverflowed = true;
						if ( executionState.failOnDlqOverflow ) {
							final RuntimeException ee = new RuntimeException("Processing error in retry queue for person IDs ["
									+ personIds + "] and bulk "
									+ "email dead letter queue exceeded max length ["
									+ executionState.maxDlqLength
									+ "]. Failing job. See cause for most recent failure", e);
							return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.FAILED, executionState, ee);
						} else {
							// no room in the dlq. exception will be logged, but otherwise silently drop this
							// delivery target
							logger.debug("Processing error in retry queue for person IDs {} but no room in DLQ in Job {}.", personIds, jobId);
							return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.FAILED_PARTIAL, executionState, e);
						}
					} else {
						executionState.dlq.add(personIds);
						logger.debug("Processing error in retry queue. Added person IDs {} to DLQ (size: {}) in Job {}. Failure count: {}",
								new Object[] { personIds, executionState.dlq.size(), jobId, executionState.emailFailedCount });
						return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.FAILED_PARTIAL, executionState, e);
					}
				}

			} else {

				final PersonSearchRequest criteria = personSearchRequestFactory.from(executionSpec.getCoreSpec().getCriteria());
				final SortingAndPaging origSortAndPage = criteria.getSortAndPage();
				final SortingAndPaging nextSortAndPage;
				int page = 1;
				if ( executionState.prevPage != null ) {
					page = executionState.prevPage + 1;
				}

				logger.debug("Processing result page {} for Job {}. Page size: {}.",
						new Object[] { page, jobId, executionState.pageSize });

				nextSortAndPage = new SortingAndPaging(origSortAndPage.getStatus(), (page - 1) * executionState.pageSize,
						executionState.pageSize, origSortAndPage.getSortFields(), origSortAndPage.getDefaultSortProperty(),
						origSortAndPage.getDefaultSortDirection());
				criteria.setSortAndPage(nextSortAndPage);

				final PagingWrapper<PersonSearchResult2> searchResults = personSearchService.searchPersonDirectory(criteria);
				if ( searchResults == null || searchResults.getResults() == 0L ) {
					logger.debug("No results on page {} for Job {}. Page size: {}",
							new Object[] { page, jobId, executionState.pageSize });
					executionState.prevPage = page;
					executionState.allPagesProcessed = true;
					// always return partial within this logic tree to ensure centralized completion logic at the top
					// of this method, i.e. let the job run us again to see if we're *really* done.
					return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.PARTIAL, executionState, null);
				} else {

					logger.debug("Processing {} results on page {} for Job {}. Page size: {}. Total results: {}",
							new Object[] { searchResults.getRows().size(), page, jobId, executionState.pageSize, searchResults.getResults() });

					final List<ImmutablePersonIdentifiersTO> deliveryTargetIdentifiers =
							Lists.newArrayListWithCapacity((int) searchResults.getResults());

					// build delivery target list outside of actual 'work loop' so we have the complete
					// list of failed delivery targets if any one should fail (and thus rollback the whole transaction)
					for ( PersonSearchResult2 searchResult : searchResults ) {
						final ImmutablePersonIdentifiersTO searchResultIds =
								new ImmutablePersonIdentifiersTO(searchResult.getId(), searchResult.getSchoolId());
						deliveryTargetIdentifiers.add(searchResultIds);
					}

					ImmutablePersonIdentifiersTO deliveryTargetIdentifier = null;
					try {
						Iterator<ImmutablePersonIdentifiersTO> i = deliveryTargetIdentifiers.iterator();
						while ( i.hasNext() ) {
							deliveryTargetIdentifier = i.next();
							logger.debug("Batched message creation attempt for person IDs {} in Job {}.", deliveryTargetIdentifier, jobId);
							doSendEmail(deliveryTargetIdentifier, executionSpec.getCoreSpec());
						}
					} catch ( Exception e ) {
						executionState.retryQueue.addAll(deliveryTargetIdentifiers);
						logger.debug("Processing error for person IDs {} on page {} for Job {}. Page size: {}. Added this page to the retry queue (size: {})",
								new Object[] { deliveryTargetIdentifier, page, jobId, executionState.pageSize, executionState.retryQueue.size() }); // exception itself logged elsewhere
						return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.FAILED_PARTIAL, executionState, e);
					} finally {
						executionState.prevPage = page;
						final long totalResults = searchResults.getResults();
						executionState.allPagesProcessed = (page * executionState.pageSize) >= totalResults;
					}

					executionState.emailSentCount += deliveryTargetIdentifiers.size();
					logger.debug("Processed {} results on page {} for Job {}. Page size: {}. Sent count: {}. ",
							new Object[] { deliveryTargetIdentifiers.size(), page, jobId, executionState.pageSize, executionState.emailSentCount });
					return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.PARTIAL, executionState, null);

				}

			}

		} catch ( Exception e ) {
			return new JobExecutionResult<BulkEmailJobExecutionState>(JobExecutionStatus.ERROR, executionState, e);
		}

	}

	private void doSendEmail(ImmutablePersonIdentifiersTO deliveryTargetIds, BulkEmailStudentRequestForm spec)
			throws ObjectNotFoundException, ValidationException {
		final Person student;
		if (deliveryTargetIds.getId() != null) {
			student = personService.get(deliveryTargetIds.getId());
		} else {
			student = personService.getBySchoolId(deliveryTargetIds.getSchoolId(), false);
		}
		EmailStudentRequestForm emailStudentRequestForm = new EmailStudentRequestForm(spec,student);
		emailStudent(emailStudentRequestForm);
	}

	private void registerBulkEmailJobExecutor() {
		this.bulkEmailJobExecutor = new AbstractJobExecutor<BulkEmailJobSpec, BulkEmailJobExecutionState>(BULK_EMAIL_JOB_EXECUTOR_NAME, jobService, transactionManager) {

			private final Logger logger = LoggerFactory.getLogger(PersonEmailServiceImpl.this.getClass().getName() + ".BulkEmailJobExecutor");

			@Override
			protected BulkEmailJobSpec deserializeJobSpecWithCheckedExceptions(String jobSpecStr) throws Exception {
				return getObjectMapper().readValue(jobSpecStr, BulkEmailJobSpec.class);
			}

			@Override
			protected BulkEmailJobExecutionState deserializeJobStateWithCheckedExceptions(String jobSpecStr) throws Exception {
				return getObjectMapper().readValue(jobSpecStr, BulkEmailJobExecutionState.class);
			}

			@Override
			protected JobExecutionResult<BulkEmailJobExecutionState> executeJobDeserialized(BulkEmailJobSpec executionSpec, BulkEmailJobExecutionState executionState, UUID jobId) {
				return executeBulkEmailJob(executionSpec, executionState, jobId, getLogger());
			}

			protected Logger getLogger() {
				return logger;
			}

		};
		this.jobService.registerJobExecutor(this.bulkEmailJobExecutor);
	}

	private JournalEntry buildJournalEntry(
			EmailStudentRequestForm emailRequest, Message message)
			throws ObjectNotFoundException, ValidationException {
		if(emailRequest.getCreateJournalEntry() && emailRequest.getStudentId() != null)
		{
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
		return null;
	}

	private String buildJournalEntryCommentFromEmail(
			EmailStudentRequestForm emailRequest, Message message) {
		StringBuilder journalEntryCommentBuilder = new StringBuilder();
		String EOL = System.getProperty("line.separator");
		journalEntryCommentBuilder.append("FROM: " + message.getSender().getFullName() + EOL);
		journalEntryCommentBuilder.append("TO: " + message.getRecipientEmailAddress() + EOL);
		if(message.getCarbonCopy() != null)
		{
			journalEntryCommentBuilder.append("CC: " + message.getCarbonCopy() + EOL);
		}
		journalEntryCommentBuilder.append(EOL);
		journalEntryCommentBuilder.append("Subject: "+emailRequest.getEmailSubject() + EOL);
		journalEntryCommentBuilder.append(EOL);
		journalEntryCommentBuilder.append("Email Message: "+emailRequest.getEmailBody() + EOL);
		journalEntryCommentBuilder.append(EOL);

		return journalEntryCommentBuilder.toString();
	}

	private Message buildAndSendStudentEmail(EmailStudentRequestForm emailRequest)
			throws ObjectNotFoundException, ValidationException {
		EmailAddress addresses = emailRequest.getValidDeliveryAddressesOrFail();
		SubjectAndBody subjectAndBody = new SubjectAndBody(emailRequest.getEmailSubject(), emailRequest.getEmailBody());
		return messageService.createMessage(addresses.getTo(), addresses.getCc(), subjectAndBody);
	}

	private void validateInput(EmailStudentRequestForm emailRequest) throws ValidationException {
		StringBuilder validationMsg = new StringBuilder();
		String EOL = System.getProperty("line.separator");

		// Removed a historical validation that required a studentId (UUID) on emailRequest. We don't actually need that
		// and we can't require it since we now reuse EmailStudentRequestForm when sending bulk email, which may
		// target external-only students, i.e. persons without UUIDs.

		if(!emailRequest.hasEmailSubject())
		{
			validationMsg.append("Email subject must be provided").append(EOL);
		}
		if(!emailRequest.hasEmailBody())
		{
			validationMsg.append("Email body must be provided").append(EOL);
		}

		if(!emailRequest.hasValidDeliveryAddresses()){
			validationMsg.append("At least one valid email address must be included.").append(EOL);
		}

		String validation = validationMsg.toString();
		if(org.apache.commons.lang.StringUtils.isNotBlank(validation)){
			throw new ValidationException(validation);
		}
	}

	private void validateInput(BulkEmailStudentRequestForm emailRequest) throws ValidationException {
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
}
