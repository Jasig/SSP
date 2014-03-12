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
package org.jasig.ssp.service.impl; // NOPMD

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ConfigService;
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

import com.google.common.collect.Lists;

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
		message.setCreatedBy(person);
		return message;
	}

	@Override
	@Transactional(readOnly = false)
	public Message createMessage(@NotNull final Person to,
			final String emailCC, final SubjectAndBody subjAndBody)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException {

		if (to == null) {
			throw new ValidationException("Recipient missing.");
		}

		if (to.getPrimaryEmailAddress() == null) {
			throw new ValidationException(
					"Recipient primary e-mail address is missing.");
		}

		final Message message = createMessage(subjAndBody);

		message.setRecipient(to);
		message.setCarbonCopy(emailCC);

		return messageDao.save(message);
	}

	@Override
	@Transactional(readOnly = false)
	public Message createMessage(@NotNull final String to,
			final String emailCC,
			@NotNull final SubjectAndBody subjAndBody)
			throws ObjectNotFoundException {

		final Message message = createMessage(subjAndBody);

		message.setRecipientEmailAddress(to);
		message.setCarbonCopy(emailCC);

		return messageDao.save(message);
	}

	@Override
	public void sendQueuedMessages() {

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
			Pair<PagingWrapper<Message>, Collection<Throwable>> rslt =
					withTransaction.withTransactionAndUncheckedExceptions(
					new Callable<Pair<PagingWrapper<Message>, Collection<Throwable>>>() {
				@Override
				public Pair<PagingWrapper<Message>, Collection<Throwable>> call()
						throws Exception {
					return sendQueuedMessageBatch(sap.get());
				}
			});

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

	private Pair<PagingWrapper<Message>, Collection<Throwable>>
	sendQueuedMessageBatch(SortingAndPaging sap) {
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
		return emailValidator.isValid(email);
	}
	
	/**
	 * Validate e-mail address via {@link EmailValidator}.
	 * 
	 * @param email
	 *            E-mail address to validate
	 * @return True if the e-mail is valid
	 */
	protected boolean validateEmails(final List<String> emails) {
		final EmailValidator emailValidator = EmailValidator.getInstance();
		for(String email:emails)
			if( !emailValidator.isValid(email)) return false;
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean sendMessage(@NotNull final Message message)
			throws ObjectNotFoundException, SendFailedException {

		LOGGER.info("BEGIN : sendMessage()");
		LOGGER.info(addMessageIdToError(message) + "Sending message: {}" , message.toString());

		try {
			final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
					mimeMessage);
 
			String adminAddress = personService.get(
					Person.SYSTEM_ADMINISTRATOR_ID).getEmailAddressWithName();
			mimeMessageHelper.setFrom(adminAddress);
			message.setSentFromAddress(adminAddress);
			// We just happen to know that getEmailAddressWithName() uses the
			// primary address. This could probably be handled better. W/o
			// the blank string check, though, javax.mail will blow up
			// w/ a AddressException
			if(message.getSender().hasEmailAddresses()){
				String replyTo = message.getSender()
						.getEmailAddressesWithName().get(0);
				mimeMessageHelper.setReplyTo(replyTo);
				message.setSentReplyToAddress(replyTo);
			}
			
			if (message.getRecipient() != null && 
					!validateEmails(message.getRecipient().getEmailAddresses())) {
				throw new SendFailedException(addMessageIdToError(message) + "A Recipient Email Address '"
						+ StringUtils.join(message.getRecipient().getEmailAddresses(),", ") + "' is invalid");
			}
			else if (!validateEmail(message.getRecipientEmailAddress())) {
				throw new SendFailedException(addMessageIdToError(message) + "Recipient Email Address '"
						+ message.getRecipientEmailAddress() + "' is invalid");
			}
			
			//As per SSP-693 we set the configured BCC on all outbound messages
			String configBCC = getBcc(); 
			if(StringUtils.isNotBlank(configBCC))
			{
				mimeMessageHelper.addBcc(configBCC);
				message.setSentBccAddresses(configBCC);
			}
			if ( message.getRecipient() != null && message.getRecipient().hasEmailAddresses()) { // NOPMD by jon.adams			{
				    List<String> addresseses = message.getRecipient().getEmailAddressesWithName();
					mimeMessageHelper.setTo(addresseses.toArray(new String[addresseses.size()]));
					message.setSentToAddresses(StringUtils.join(message.getRecipient().getEmailAddresses(), ","));
			} else if ( StringUtils.isNotBlank(message.getRecipientEmailAddress()) ) { // NOPMD
				mimeMessageHelper.setTo(message.getRecipientEmailAddress());
				message.setSentToAddresses(message.getRecipientEmailAddress());
			} else {
				StringBuilder errorMsg = new StringBuilder();
				
				errorMsg.append(addMessageIdToError(message) + " Message " +message.toString() 
						+" could not be sent. Invalid recipient email address of '");				
				
				if (message.getRecipient() != null) {
						errorMsg.append(message.getRecipient().getPrimaryEmailAddress());
				} else {
					errorMsg.append(message.getRecipientEmailAddress());
				}
				errorMsg.append("'.");
				LOGGER.error(errorMsg.toString());
				
				return false;
			}

			String carbonCopy = message.getCarbonCopy();
			if (!StringUtils.isEmpty(carbonCopy)) { // NOPMD
				try {
					
					//check for multiple addresses seperated by a comma
					if(carbonCopy.indexOf(",") != -1)
					{
						StringTokenizer tokenizer = new StringTokenizer(carbonCopy,",");
						List<String> carbonCopies = new ArrayList<String>();
						while(tokenizer.hasMoreTokens())
						{
							String token = tokenizer.nextToken();
							mimeMessageHelper.addCc(token);
							carbonCopies.add(token);
						}
						message.setSentCcAddresses(StringUtils.join(carbonCopies,","));
					}
					else
					{
						mimeMessageHelper.setCc(carbonCopy);
						message.setSentCcAddresses(carbonCopy);
					}
					
				} catch ( MessagingException e ) {
					LOGGER.warn(addMessageIdToError(message) + "Invalid carbon copy address: '{}'. Will"
							+ " attempt to send message anyway.",
							carbonCopy, e);
				}
			} else if (!StringUtils.isEmpty(configBCC)) {
				final String bcc = configBCC;
				try {
					mimeMessageHelper.setBcc(bcc);
					message.setSentBccAddresses(bcc);
				} catch ( MessagingException e ) {
					LOGGER.warn(addMessageIdToError(message) + "Invalid BCC address: '{}'. Will"
							+ " attempt to send message anyway.", bcc, e);
				}
			}

			mimeMessageHelper.setSubject(message.getSubject());
			mimeMessageHelper.setText(message.getBody());
			mimeMessage.setContent(message.getBody(), "text/html");

			send(mimeMessage);

			message.setSentDate(new Date());
			messageDao.save(message);
		} catch (final MessagingException e) {
			LOGGER.error("ERROR : sendMessage() : {}", e);
			throw new SendFailedException(
					addMessageIdToError(message) + "The message parameters were invalid.", e);
		}

		LOGGER.info("END : sendMessage()");
		return true;
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
}