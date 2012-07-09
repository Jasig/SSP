package org.jasig.ssp.service.impl; // NOPMD

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Message service implementation for sending e-mails (messages) to various
 * parties.
 */
@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

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
	@Transactional(readOnly = false)
	@Scheduled(fixedDelay = 150000)
	// run 2.5 minutes after the end of the last invocation
	public void sendQueuedMessages() {
		LOGGER.info("BEGIN : sendQueuedMessages()");

		final List<Message> messages = messageDao.queued();
		for (final Message message : messages) {
			try {
				sendMessage(message);
			} catch (final ObjectNotFoundException e) {
				LOGGER.error("Could not load current user or administrator.", e);
			} catch (final SendFailedException e) {
				LOGGER.error("Could not send queued message.", e);
			}
		}

		LOGGER.info("END : sendQueuedMessages()");
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

	@Override
	public boolean sendMessage(@NotNull final Message message)
			throws ObjectNotFoundException, SendFailedException {

		LOGGER.info("BEGIN : sendMessage()");
		LOGGER.info("Sending message: {}", message.toString());

		try {
			final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
					mimeMessage);

			mimeMessageHelper.setFrom(personService.get(
					Person.SYSTEM_ADMINISTRATOR_ID).getEmailAddressWithName());
			mimeMessageHelper.setReplyTo(message.getSender()
					.getEmailAddressWithName());

			if (message.getRecipient() != null) { // NOPMD by jon.adams
				mimeMessageHelper.setTo(message.getRecipient()
						.getEmailAddressWithName());
			} else if (message.getRecipientEmailAddress() != null) { // NOPMD
				mimeMessageHelper.setTo(message.getRecipientEmailAddress());
			} else {
				return false;
			}

			if (!validateEmail(message.getRecipientEmailAddress())) {
				throw new SendFailedException("Recipient Email Address '"
						+ message.getRecipientEmailAddress() + "' is invalid");
			}

			if (!StringUtils.isEmpty(message.getCarbonCopy())) { // NOPMD
				mimeMessageHelper.setCc(message.getCarbonCopy());
			} else if (!StringUtils.isEmpty(getBcc())) {
				mimeMessageHelper.setBcc(getBcc());
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
					"The message parameters were invalid.", e);
		}

		LOGGER.info("END : sendMessage()");
		return true;
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