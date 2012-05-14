package org.jasig.ssp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public String getBcc() {
		return configService.getByNameEmpty("bcc_email_address");
	}

	public boolean shouldSendMail() {
		final String shouldSendMail = configService.getByNameNull("send_mail");
		if (shouldSendMail != null) {
			return Boolean.valueOf(shouldSendMail);
		}
		return false;
	}

	private Message createMessage(
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws ObjectNotFoundException {

		final MessageTemplate messageTemplate = messageTemplateDao
				.get(messageTemplateId);

		final Message message = new Message();

		message.setSubject(velocityTemplateService.generateContentFromTemplate(
				messageTemplate.subjectTemplateId(),
				messageTemplate.getSubject(), templateParameters));

		message.setBody(velocityTemplateService.generateContentFromTemplate(
				messageTemplate.bodyTemplateId(),
				messageTemplate.getBody(), templateParameters));

		Person person;
		if (securityService.isAuthenticated()) {
			person = securityService.currentUser().getPerson();
			message.setCreatedBy(person);
			message.setSender(person);
		} else {
			person = personService.get(Person.SYSTEM_ADMINISTRATOR_ID);
			message.setCreatedBy(person);
			message.setSender(person);
		}

		return message;
	}

	@Override
	@Transactional(readOnly = false)
	public void createMessage(final Person to,
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws Exception {
		final Message message = createMessage(messageTemplateId,
				templateParameters);
		message.setRecipient(to);
		messageDao.save(message);
	}

	@Override
	public void createMessage(final String to,
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws Exception {
		final Message message = createMessage(messageTemplateId,
				templateParameters);
		message.setRecipientEmailAddress(to);
		messageDao.save(message);
	}

	@Override
	@Transactional(readOnly = false)
	@Scheduled(fixedDelay = 300000)
	// run 5 mins after the end of the last invocation
	public void sendQueuedMessages() {

		LOGGER.info("BEGIN : sendQueuedMessages()");

		try {

			final List<Message> messages = messageDao.queued();

			for (Message message : messages) {
				sendMessage(message);
			}

		} catch (Exception e) {
			LOGGER.error("ERROR : sendQueuedMessages() : {}", e);
		}

		LOGGER.info("END : sendQueuedMessages()");
	}

	protected boolean validateEmail(final String email) {
		final EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(email);
	}

	@Override
	public boolean sendMessage(final Message message) {

		LOGGER.info("BEGIN : sendMessage()");

		LOGGER.info("Sending message: {}", message.getId().toString());

		boolean retVal = true;

		try {
			final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
					mimeMessage);

			mimeMessageHelper.setFrom(personService.get(
					Person.SYSTEM_ADMINISTRATOR_ID).getEmailAddressWithName());
			mimeMessageHelper.setReplyTo(message.getSender()
					.getEmailAddressWithName());

			if (message.getRecipient() != null) {
				mimeMessageHelper.setTo(message.getRecipient()
						.getEmailAddressWithName());
			} else if (message.getRecipientEmailAddress() != null) {
				mimeMessageHelper.setTo(message.getRecipientEmailAddress());
			} else {
				return false;
			}

			if (!validateEmail(message.getRecipientEmailAddress())) {
				throw new Exception("Recipient Email Address '"
						+ message.getRecipientEmailAddress() + "' is invalid");
			}

			if ((getBcc() != null) && (getBcc().length() > 0)) {
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

		} catch (MailException e) {
			retVal = false;
			LOGGER.error("ERROR : sendMessage() : {}", e);
		} catch (MessagingException e) {
			retVal = false;
			LOGGER.error("ERROR : sendMessage() : {}", e);
		} catch (Exception e) {
			retVal = false;
			LOGGER.error("ERROR : sendMessage() : {}", e);
		}

		LOGGER.info("END : sendMessage()");

		return retVal;
	}

}
