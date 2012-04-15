package org.studentsuccessplan.ssp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.MessageDao;
import org.studentsuccessplan.ssp.dao.reference.MessageTemplateDao;
import org.studentsuccessplan.ssp.model.Message;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.service.MessageService;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.VelocityTemplateService;

@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private PersonService personService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private VelocityTemplateService velocityTemplateService;

	@Autowired
	private MessageTemplateDao messageTemplateDao;

	@Value("#{configProperties.messageManager_bcc}")
	private String bcc;

	@Value("#{configProperties.send_mail}")
	private boolean sendMail = false;

	private final Logger LOGGER = LoggerFactory
			.getLogger(MessageServiceImpl.class);

	@Override
	public void setBcc(final String bcc) {
		this.bcc = bcc;
	}

	/**
	 * Send a message to the email address with the given MessageTemplate and
	 * parameters
	 */
	@Override
	public void createMessageFromTemplate(final String emailAddress,
			final UUID messageTemplateId,
			final Map<String, Object> templateParameters) throws Exception {
		MessageTemplate messageTemplate = messageTemplateDao
				.get(messageTemplateId);

		createMessage(emailAddress,
				velocityTemplateService.generateContentFromTemplate(
						messageTemplate.subjectTemplateId(),
						messageTemplate.getSubject(), templateParameters),
				velocityTemplateService.generateContentFromTemplate(
						messageTemplate.bodyTemplateId(),
						messageTemplate.getBody(), templateParameters));

	}

	@Override
	@Transactional(readOnly = false)
	public void createMessage(Person to, String subject, String body)
			throws Exception {

		Message message = new Message();

		message.setBody(body);

		if (securityService.isAuthenticated()) {
			message.setCreatedBy(securityService.currentUser()
					.getPerson());
		} else {
			message.setCreatedBy(personService
					.get(Person.SYSTEM_ADMINISTRATOR_ID));
		}

		message.setCreatedDate(new Date());
		message.setRecipient(to);

		if (securityService.isAuthenticated()) {
			message.setSender(securityService.currentUser()
					.getPerson());
		} else {
			message.setSender(personService.get(Person.SYSTEM_ADMINISTRATOR_ID));
		}

		message.setSubject(subject);

		messageDao.save(message);
	}

	@Override
	public void createMessage(String to, String subject, String body)
			throws Exception {

		Message message = new Message();

		message.setBody(body);

		if (securityService.isAuthenticated()) {
			message.setCreatedBy(securityService.currentUser()
					.getPerson());
		} else {
			message.setCreatedBy(personService
					.get(Person.SYSTEM_ADMINISTRATOR_ID));
		}

		message.setCreatedDate(new Date());
		message.setRecipientEmailAddress(to);

		if (securityService.isAuthenticated()) {
			message.setSender(securityService.currentUser()
					.getPerson());
		} else {
			message.setSender(personService.get(Person.SYSTEM_ADMINISTRATOR_ID));
		}

		message.setSubject(subject);

		messageDao.save(message);
	}

	@Override
	@Transactional(readOnly = false)
	public void sendQueuedMessages() {

		LOGGER.info("BEGIN : sendQueuedMessages()");

		try {

			List<Message> messages = messageDao.queued();

			for (Message message : messages) {
				sendMessage(message);
			}

		} catch (Exception e) {
			LOGGER.error("ERROR : sendQueuedMessages() : {}", e);
		}

		LOGGER.info("END : sendQueuedMessages()");
	}

	protected boolean validateEmail(String email) {
		EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(email);
	}

	@Override
	public boolean sendMessage(Message message) {

		LOGGER.info("BEGIN : sendMessage()");

		LOGGER.info("Sending message: {}", message.getId().toString());

		boolean retVal = true;

		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
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

			if ((bcc != null) && (bcc.length() > 0)) {
				mimeMessageHelper.setBcc(bcc);
			}

			mimeMessageHelper.setSubject(message.getSubject());
			mimeMessageHelper.setText(message.getBody());

			mimeMessage.setContent(message.getBody(), "text/html");

			if (sendMail) {
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
