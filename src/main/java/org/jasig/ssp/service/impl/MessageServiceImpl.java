package org.jasig.ssp.service.impl; // NOPMD

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.VelocityTemplateService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private transient VelocityTemplateService velocityTemplateService;

	@Autowired
	private transient MessageTemplateDao messageTemplateDao;

	@Autowired
	private transient ConfigService configService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageServiceImpl.class);

	/**
	 * Gets the global BCC e-mail address from the application configuration
	 * information.
	 * 
	 * @return the global BCC e-mail address from the application configuration
	 *         information
	 */
	public String getBcc() {
		return configService.getByNameEmpty("bcc_email_address");
	}

	@Override
	public boolean shouldSendMail() {
		final String shouldSendMail = configService.getByNameNull("send_mail");
		if (shouldSendMail != null) {
			return Boolean.valueOf(shouldSendMail);
		}

		return false;
	}

	/**
	 * Create a new message.
	 * 
	 * @param messageTemplateId
	 *            Message template
	 * @param templateParameters
	 * @return A new message for the specified template
	 * @throws ObjectNotFoundException
	 *             If the current user or administrator could not be loaded.
	 */
	private Message createMessage(
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws ObjectNotFoundException {

		final MessageTemplate messageTemplate = messageTemplateDao
				.get(messageTemplateId);

		final String subject = velocityTemplateService
				.generateContentFromTemplate(messageTemplate.getSubject(),
						messageTemplate.subjectTemplateId(), templateParameters);

		final String body = velocityTemplateService
				.generateContentFromTemplate(messageTemplate.getBody(),
						messageTemplate.bodyTemplateId(), templateParameters);

		final Message message = new Message(subject, body, null, null, null);
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
			final String emailCC, @NotNull final UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException {
		if (to == null) {
			throw new ValidationException("Recipient missing.");
		}

		if (to.getPrimaryEmailAddress() == null) {
			throw new ValidationException(
					"Recipient primary e-mail address is missing.");
		}

		final Message message = createMessage(messageTemplateId,
				templateParameters);
		message.setRecipient(to);

		if (StringUtils.isEmpty(emailCC)) {
			return messageDao.save(message);
		} else {
			// An extra CC needs added, only API available is sendMessage()
			sendMessage(message, emailCC);
			return message;
		}
	}

	@Override
	public Message createMessage(@NotNull final String to,
			@NotNull final UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws ObjectNotFoundException {
		final Message message = createMessage(messageTemplateId,
				templateParameters);
		message.setRecipientEmailAddress(to);
		return messageDao.save(message);
	}

	@Override
	@Transactional(readOnly = false)
	@Scheduled(fixedDelay = 300000)
	// run 5 minutes after the end of the last invocation
	public void sendQueuedMessages() {
		LOGGER.info("BEGIN : sendQueuedMessages()");

		final List<Message> messages = messageDao.queued();
		for (final Message message : messages) {
			try {
				sendMessage(message, null);
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
	public boolean sendMessage(@NotNull final Message message,
			final String emailCC)
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

			if (!StringUtils.isEmpty(emailCC)) { // NOPMD
				mimeMessageHelper.setBcc(emailCC);
			} else if (!StringUtils.isEmpty(getBcc())) {
				mimeMessageHelper.setBcc(getBcc());
			}

			mimeMessageHelper.setSubject(message.getSubject());
			mimeMessageHelper.setText(message.getBody());
			mimeMessage.setContent(message.getBody(), "text/html");

			if (shouldSendMail()) {
				javaMailSender.send(mimeMessage);
			}

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
}