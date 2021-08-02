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

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.util.transaction.WithTransaction;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Message service implementation for sending e-mails (messages) to various
 * parties.
 */
@Service
public class MessageServiceImpl implements MessageService {

	private static final Integer QUEUE_BATCH_SIZE = 25;

	private static final long INTER_QUEUE_BATCH_SLEEP = 200;

	@Autowired
	private transient JavaMailSender javaMailSender;

	@Autowired
	private transient MessageDao messageDao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient ConfigService configService;

	@Autowired
	private transient WithTransaction withTransaction;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageServiceImpl.class);

	@Value("#{contextProperties.applicationMode}")
	private transient String applicationMode;

    @Value("#{configProperties.reroute_all_mail_to_address}")
    private transient String routeAllMailToAddress;

    @Value("#{configProperties.system_id}")
    private  String systemId = "";



    /**
     * Gets all Messages useful for viewing what what is sent/not-sent
     * @return Messages based on paging and supplied option
     */
    @Transactional(readOnly = true)
    public PagingWrapper<Message> getMessages(final SortingAndPaging sAndP) {
        return messageDao.getAll(sAndP);
    }

    /**
     * Gets the global BCC e-mail address from the application configuration
     * information.
     *
     * @return the global BCC e-mail address from the application configuration
     *         information
     */
	@Transactional(readOnly = true)
	public String getBcc() {
		final String bcc = configService.getByNameEmpty("bcc_email_address");
		if (!bcc.isEmpty() && !bcc.equalsIgnoreCase("noone@test.com")) {
			return bcc;
		}
		return null;
	}

	@Override
	/**
	 * Always returns true in TEST applicationMode
	 */
	@Transactional(readOnly = true)
	public boolean shouldSendMail() {
		if ("TEST".equals(applicationMode)) {
			return true;
		}

		final String shouldSendMail = configService.getByNameNull("send_mail");
		if (shouldSendMail != null) {
			return Boolean.valueOf(shouldSendMail);
		}

		return false;
	}

	/**
	 * Create a new message.
	 * 
	 * @param subjAndBody
	 *            SubjectAndBody subjAndBody
	 * @return A new message for the specified SubjectAndBody
	 * @throws ObjectNotFoundException
	 *             If the current user or administrator could not be loaded.
	 */
	private Message createMessage(
			final SubjectAndBody subjAndBody)
			throws ObjectNotFoundException {

		final Message message = new Message(subjAndBody);

		Person person = null; // NOPMD by jon.adams on 5/17/12 9:42 AM
		if (securityService.isAuthenticated()) {
			person = securityService.currentUser().getPerson();
		} else {
			// E-mails sent by anonymous users are sent by the administrator
			person = personService.get(Person.SYSTEM_ADMINISTRATOR_ID);
		}

		message.setSender(person);
		message.setCreatedBy(new AuditPerson(person.getId()));
		return message;
	}

	@Override
	@Transactional(readOnly = false)
	public Message createMessage(@NotNull final Person to,
			final String emailCC, final SubjectAndBody subjAndBody)
			throws ObjectNotFoundException, ValidationException {

		if (to == null) {
			throw new ValidationException("Recipient missing.");
		}
		String toAddress = to.getPrimaryEmailAddress();
		// do *not* fall back to secondary email addr. might be a personal addr and we never know when we're allowed to
		// *automatically* generate messages to such addr.

		if (StringUtils.isBlank(toAddress)) {
			throw new ValidationException(
					"Recipient e-mail address is missing.");
		}

		if(!validateEmail(toAddress)){
			throw new ValidationException(
				"Recipient e-mail address is invalid.");
		}

		final Message message = createMessage(subjAndBody);

		message.setRecipient(to);
		message.setCarbonCopy(emailCC);

		return save(message);
	}

	@Override
	@Transactional(readOnly = false)
	public Message createMessage(@NotNull final String to,
			final String emailCC,
			@NotNull final SubjectAndBody subjAndBody)
			throws ObjectNotFoundException, ValidationException {

		final Message message = createMessage(subjAndBody);

		message.setRecipientEmailAddress(to);
		message.setCarbonCopy(emailCC);

		return save(message);
	}

	@Override
	public Message createMessageNoSave(@NotNull final String to,
								 final String emailCC,
								 @NotNull final SubjectAndBody subjAndBody)
			throws ObjectNotFoundException {

		final Message message = createMessage(subjAndBody);

		message.setRecipientEmailAddress(to);
		message.setCarbonCopy(emailCC);

		return message;
	}

    /**
     * Method to re-route all mail in the case of configuration set on
     *  the file system in ssp-configuration.properties. This can eliminate
     *   issues with DEV and UAT servers sending mail that is hard to
     *    distinguish from valid PROD mail.
     * @param message
     * @return
     * @throws ValidationException
     */
    private Message save (final Message message) throws ValidationException {
        if (StringUtils.isNotBlank(routeAllMailToAddress) &&
                !StringUtils.equalsIgnoreCase(routeAllMailToAddress.trim(), message.getRecipientEmailAddress().trim())) {

            if (!validateEmail(routeAllMailToAddress)) {
                LOGGER.error("Configured ReRoute All Mail Address in ssp configuration properties is invalid!");
                throw new ValidationException(
                        "Configured e-mail address that all SSP mail should be delivered to is invalid!.");
            }

            message.setBody("<html><body>\n***\nThis message was re-routed based on server " + systemId + " configuration.\n<br />"
                    + "*Original Recipient: [" + message.getRecipientEmailAddress()
                    +"]\n<br />*Original CC's: [" + message.getCarbonCopy()  + "]\n***\n\n<br /><br />"
                    + message.getBody().replace("<html><body>", ""));
            message.setCarbonCopy(null);
            message.setRecipientEmailAddress(routeAllMailToAddress);
            message.setRecipient(null); //this will override recipient address if set
                                     //as of now only aware of EA using this, use of recipient below probably needs a refactor
                                      // since specific rules on setting recipient addrs lies above and elsewhere
        }

        return messageDao.save(message);
    }

	@Override
	public Pair<PagingWrapper<Message>, Collection<Throwable>> getSendQueuedMessagesBatchExecReturnType() {
		return new Pair<PagingWrapper<Message>, Collection<Throwable>>(null,null);
	}

	@Override
	public void sendQueuedMessages(CallableExecutor<Pair<PagingWrapper<Message>, Collection<Throwable>>> batchExec) {

		LOGGER.info("BEGIN : sendQueuedMessages()");

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning sendQueuedMessages because of thread interruption");
		}

		int startRow = 0;
		final AtomicReference<SortingAndPaging> sap =
				new AtomicReference<SortingAndPaging>();
		sap.set(new SortingAndPaging(ObjectStatus.ACTIVE, startRow, QUEUE_BATCH_SIZE,
				null, null, null));
		// process each batch in its own transaction... don't want to hold
		// a single transaction open while processing what is effectively
		// an unbounded number of messages.
		while (true) {

			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Abandoning sendQueuedMessages because of thread interruption");
				break;
			}

			LOGGER.info("Before message queue processing transaction at start row {}",
					sap.get().getFirstResult());
			Pair<PagingWrapper<Message>, Collection<Throwable>> rslt = null;

			try {
				if ( batchExec == null ) {
					rslt = sendQueuedMessageBatchInTransaction(sap.get());
				} else {
					rslt = batchExec.exec(new Callable<Pair<PagingWrapper<Message>, Collection<Throwable>>>() {
						@Override
						public Pair<PagingWrapper<Message>, Collection<Throwable>> call() throws Exception {
							return sendQueuedMessageBatchInTransaction(sap.get());
						}
					});
				}
			} catch (RuntimeException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Abandoning sendQueuedMessages because of thread interruption");
				break;
			}

			PagingWrapper<Message> msgsHandled = rslt.getFirst();
			int msgHandledCnt = msgsHandled.getRows() == null ? 0 : msgsHandled.getRows().size();
			if ( msgHandledCnt == 0 || msgHandledCnt < QUEUE_BATCH_SIZE ) {
				LOGGER.info("Stop message queue processing. Transaction at"
						+ " start row {} processed fewer messages ({}) than"
						+ " allowed batch size {}.",
						new Object[] { startRow, msgHandledCnt, QUEUE_BATCH_SIZE });
				break;
			}

			// Are potentially more msgs to handle and we know at least one
			// msg in the previous batch errored out. go ahead and grab another
			// full batch. Grabbing a full batch avoids slowdown when enough
			// errors accumulate to dramatically reduce the number of
			// *potentially* valid messages in the previous batch.
			Collection<Throwable> errors = rslt.getSecond();
			if ( errors != null && !(errors.isEmpty())) {
				startRow += msgsHandled.getRows().size();
				sap.set(new SortingAndPaging(ObjectStatus.ACTIVE, startRow,
						QUEUE_BATCH_SIZE,
						null, null, null));
				LOGGER.info("Need to advance past message queue processing errors."
						+ " Transaction at start row {} processed all {}"
						+ " messages in a max batch size of {}, but were"
						+ " errors.",
						new Object[] { startRow, msgHandledCnt, QUEUE_BATCH_SIZE });
				// lets not get into an excessively tight email loop
				maybePauseBetweenQueueBatches();
			} else {
				LOGGER.info("Stop message queue processing. Transaction at"
						+ " start row {} processed all {} messages in a max"
						+ " batch size of {}, but were zero errors. Waiting for"
						+ " next scheduled execution before processing"
						+ " additional messages.",
						new Object[] { startRow, msgHandledCnt, QUEUE_BATCH_SIZE });
				break;
			}
		}

		LOGGER.info("END : sendQueuedMessages()");
	}

	private Pair<PagingWrapper<Message>, Collection<Throwable>> sendQueuedMessageBatchInTransaction(final SortingAndPaging sap) {
		return withTransaction.withTransactionAndUncheckedExceptions(
				new Callable<Pair<PagingWrapper<Message>, Collection<Throwable>>>() {
					@Override
					public Pair<PagingWrapper<Message>, Collection<Throwable>> call()
							throws Exception {
						return sendQueuedMessageBatch(sap);
					}
				});
	}

	private Pair<PagingWrapper<Message>, Collection<Throwable>>
	sendQueuedMessageBatch(SortingAndPaging sap) throws UnsupportedEncodingException, ValidationException {
		LinkedList<Throwable> errors = Lists.newLinkedList();
		LOGGER.info("Looking for queued message batch at start row {}, batch size {}",
				sap.getFirstResult(), sap.getMaxResults());
		final PagingWrapper<Message> messages = messageDao.queued(sap);
		LOGGER.info("Start processing {} queued messages in batchstart"
				+ " row {}, max batch size {}",
				new Object[] { messages.getRows() == null ? 0 : messages.getRows().size(),
						sap.getFirstResult(), sap.getMaxResults() });
		for (final Message message : messages ) {
			if ( Thread.currentThread().isInterrupted() ) {
				LOGGER.info("Abandoning sendQueuedMessageBatch because of thread interruption");
				break;
			}
			try {
				sendMessage(message);
			} catch (final ObjectNotFoundException e) {
				LOGGER.error("Could not load current user or administrator.", e);
				errors.add(e);
			} catch (final SendFailedException e) {
				LOGGER.error("Could not send queued message.", e);
				errors.add(e);
			}
		}
		return new Pair<PagingWrapper<Message>, Collection<Throwable>>(messages, errors);
	}

	private void maybePauseBetweenQueueBatches() {
		if ( INTER_QUEUE_BATCH_SLEEP > 0 ) {
			try {
				LOGGER.info("Pausing for {} ms between message queue batches.",
						INTER_QUEUE_BATCH_SLEEP);
				Thread.sleep(INTER_QUEUE_BATCH_SLEEP);
			} catch ( InterruptedException e ) {
				// reassert
				Thread.currentThread().interrupt();
				throw new RuntimeException("Abandoning message queue"
						+ " processing because job thread was interrupted.", e);
			}
		}
	}

	/**
	 * Validate e-mail address via {@link EmailValidator}.
	 * 
	 * @param email
	 *            E-mail address to validate
	 * @return True if the e-mail is valid
	 */
	protected boolean validateEmail(final String email) {
		final EmailValidator emailValidator = EmailValidator.getInstance();
		if(email.indexOf("<") != -1){
			email.split("<");
		}
		return emailValidator.isValid(email);
	}
	 
	/**
	 * Validate e-mail address via {@link EmailValidator}.
	 * 
	 * @param emails
	 *            E-mail address to validate
	 * @return True if the e-mail is valid
	 */
	protected boolean validateEmails(final List<String> emails) {
		final EmailValidator emailValidator = EmailValidator.getInstance();
		for (final String email : emails) {
            if ( !emailValidator.isValid(email) ) {
                return false;
            }
        }
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean sendMessage(@NotNull final Message message)
			throws ObjectNotFoundException, SendFailedException, UnsupportedEncodingException, ValidationException {

		LOGGER.info("BEGIN : sendMessage()");
		LOGGER.info(addMessageIdToError(message) + "Sending message: {}" , message.toString());

		try {
			final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
					mimeMessage);
			
			// process FROM addresses
			InternetAddress from;
			String appName = configService.getByName("app_title").getValue();
			
			//We used the configured outbound email address for every outgoing message
			//If a message was initiated by an end user, their name will be attached to the 'from' while
			//the configured outbound address will be the actual address used for example "Amy Aministrator (SSP) <myconfiguredaddress@foobar.com>"
			String fromName = appName + " Administrator";
			InternetAddress[] replyToAddrs = null;
			final boolean isEndUserMessage = message.getSender() != null &&
					!message.getSender().getEmailAddresses().isEmpty() && 
					!message.getSender().getId().equals(Person.SYSTEM_ADMINISTRATOR_ID);
			if ( isEndUserMessage ) {
				replyToAddrs = getEmailAddresses( message.getSender(), "from:",message.getId());
				if(replyToAddrs.length > 0){
					fromName = message.getSender().getFullName() + " ("+appName+")";
				}
			}

			from = new InternetAddress(configService.getByName("outbound_email_address").getValue(), fromName);
			if (!this.validateEmail(from.getAddress())) {
				throw new AddressException("Invalid from: email address [" + from.getAddress() + "]");
			}

			if ( !(isEndUserMessage) ) {
				replyToAddrs = new InternetAddress[] { from };
			}
			
			 mimeMessageHelper.setFrom(from);
			 message.setSentFromAddress(from.toString());
			 mimeMessageHelper.setReplyTo(replyToAddrs[0]);
			 message.setSentReplyToAddress(replyToAddrs[0].toString());
			
			// process TO addresses
			InternetAddress[] tos = null;
			if ( message.getRecipient() != null && message.getRecipient().hasEmailAddresses()) { // NOPMD by jon.adams			
				tos = getEmailAddresses(message.getRecipient(), "to:",message.getId());
			} else { 
				tos = getEmailAddresses(message.getRecipientEmailAddress(), "to:", message.getId());
			}
			if(tos.length > 0){
				mimeMessageHelper.setTo(tos);
				message.setSentToAddresses(StringUtils.join(tos,",").trim());
			}else {
				StringBuilder errorMsg = new StringBuilder();
				
				errorMsg.append(addMessageIdToError(message) + " Message " + message.toString() 
						+" could not be sent. No valid recipient email address found: '");				
				
				if (message.getRecipient() != null) {
						errorMsg.append(message.getRecipient().getPrimaryEmailAddress());
				} else {
					errorMsg.append(message.getRecipientEmailAddress());
				}
				LOGGER.error(errorMsg.toString());
				throw new MessagingException(errorMsg.toString());
			}
			
			// process BCC addresses
			try{
				InternetAddress[] bccs = getEmailAddresses(getBcc(), "bcc:", message.getId());
				if (bccs.length > 0 && StringUtils.isBlank(routeAllMailToAddress)) {
					mimeMessageHelper.setBcc(bccs);
					message.setSentBccAddresses(StringUtils.join(bccs,",").trim());
				}
			}catch(Exception exp){
				LOGGER.warn("Unrecoverable errors were generated adding carbon copy to message: " + message.getId() + "Attempt to send message still initiated.", exp);
			}
			
			// process CC addresses
			try{	
				InternetAddress[] carbonCopies = getEmailAddresses(message.getCarbonCopy(), "cc:", message.getId());
				if(carbonCopies.length > 0){
					mimeMessageHelper.setCc(carbonCopies);
					message.setSentCcAddresses(StringUtils.join(carbonCopies,",").trim());
				}
			}catch(Exception exp){
				LOGGER.warn("Unrecoverable errors were generated adding bcc to message: " + message.getId() + "Attempt to send message still initiated.", exp);
			}
			
			mimeMessageHelper.setSubject(message.getSubject());
			mimeMessageHelper.setText(message.getBody());
			mimeMessage.setContent(message.getBody(), "text/html");

			send(mimeMessage);

			message.setSentDate(new Date());
			save(message);
		} catch (final MessagingException e) {
			LOGGER.error("ERROR : sendMessage() : {}", e);
			handleSendMessageError(message);
			throw new SendFailedException(
					addMessageIdToError(message) + "The message parameters were invalid.", e);
		}

		LOGGER.info("END : sendMessage()");
		return true;
	}
	
	private void handleSendMessageError(Message message) {
		message.setRetryCount((message.getRetryCount() == null) ? 1 : message.getRetryCount() + 1);
		
	}

	private String addMessageIdToError(Message message){
		return "Message Id: " + message.getId().toString() + ": ";
	}

	private void send(final MimeMessage mimeMessage) throws SendFailedException {
		if (shouldSendMail()) {
			LOGGER.debug("_ : JavaMailSender.send()");
			try {
				javaMailSender.send(mimeMessage);
			} catch (final MailSendException e) {
				try {
					LOGGER.warn("Send failed, going to wait and try again");
					Thread.sleep(20 * 1000L);
					javaMailSender.send(mimeMessage);
				} catch (final InterruptedException e1) {
					LOGGER.error("Thread error", e1);
				} catch (final MailSendException e2) {
					throw new SendFailedException("Unable to send message.", e2);
				}
			}
		} else {
			
			LOGGER.warn("_ : JavaMailSender was not called; message was marked sent but was not actually sent.  To enable mail, update the configuration of the app.");
		}
	}
	
	private InternetAddress[] getEmailAddresses(List<String> emailAddressses, String type, UUID messageId){
		List<InternetAddress> validAddresses = new ArrayList<InternetAddress>();
		for(String emailAddress:emailAddressses){
			validAddresses.addAll(Lists.newArrayList(getEmailAddresses(emailAddress,  type,  messageId)));
		}
		
		return validAddresses.toArray(new InternetAddress[validAddresses.size()]);
	}
	
	private InternetAddress[] getEmailAddresses(String emailAddress, String type, UUID messageId){
		if(StringUtils.isBlank(emailAddress)){
			return new InternetAddress[0];
		}
		List<InternetAddress> emailAddresses = new ArrayList<InternetAddress>();
		if(emailAddress.indexOf(",") != -1)
		{
			StringTokenizer tokenizer = new StringTokenizer(emailAddress,",");
			while(tokenizer.hasMoreTokens())
			{
				String token = tokenizer.nextToken();
				if (StringUtils.isBlank(token)) {
                    continue;
                }
				InternetAddress address = getInternetAddress(token, type, messageId);
				if(address != null){
					if(validateEmail(address.getAddress())) {
                        emailAddresses.add(address);
                    } else {
                        LOGGER.warn("Invalid email address found: " + token + " for " + type + "of message " + messageId);
                    }
				}
			}
		}
		else
		{
			InternetAddress address = getInternetAddress(emailAddress, type, messageId);
			if(address != null){
				if(validateEmail(address.getAddress())) {
                    emailAddresses.add(address);
                } else {
                    LOGGER.warn("Invalid email address found: " + emailAddress + " for " + type + "of message " + messageId);
                }
			}
		}
		
		return emailAddresses.toArray(new InternetAddress[emailAddresses.size()]);
	}
	
	private InternetAddress[] getEmailAddresses(Person person, String type, UUID messageId){
		List<InternetAddress> validAddresses = new ArrayList<InternetAddress>();
		
		if(person.hasEmailAddresses()){
			InternetAddress[] addresses = getEmailAddresses(person.getEmailAddresses(), "to:", messageId);
			 for(InternetAddress address:addresses){
				 try {
					validAddresses.add(new InternetAddress(address.getAddress(), person.getFullName()));
				} catch (UnsupportedEncodingException e) {
					LOGGER.warn("Invalid email address found: " + address.toString() + " for " + type +  "of message " + messageId, e);
				}
			 }
		}
		
		return validAddresses.toArray(new InternetAddress[validAddresses.size()]);
	}
	
	InternetAddress getInternetAddress(String address, String type, UUID messageId){
		try {
			String emailAddress = address;
			String personal = "";
			if(address.indexOf("<") != -1 && address.indexOf(">") > address.indexOf("<"))
			{
				String[] components = address.split("<");
				//replace quotes
				personal = components[0].replace("\"", "").replace("'", "");
				personal = components[0].trim();
				emailAddress = components[1].split(">")[0].trim();
			}
			if ( StringUtils.isBlank(personal) ) {
				return new InternetAddress(emailAddress);
			} else {
				return new InternetAddress(emailAddress, personal);
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.warn("Invalid email address found: " + address + " for " + type +  "of message " + messageId, e);
		} catch (AddressException e) {
			LOGGER.warn("Invalid email address found: " + address + " for " + type +  "of message " + messageId, e);
		}
		return null;
	}

	@Override
	public int archiveAndPruneMessages() {
		Integer messageAgeInDays = Integer.MAX_VALUE;
		try{
			 messageAgeInDays = Integer.parseInt(configService.getByNameEmpty("mail_age_in_days_limit"));
		}
		catch (Exception e)
		{
			LOGGER.error("Config value 'mail_age_in_days_limit' cannot be parsed into an integer");
		}
		return messageDao.archiveAndPruneMessages(messageAgeInDays);
	}
}
