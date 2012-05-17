package org.jasig.ssp.service;

import java.util.Map;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.springframework.transaction.annotation.Transactional;

/**
 * Message service
 */
public interface MessageService {
	/**
	 * Gets the configuration value indicating whether e-mails (message) should
	 * be sent.
	 * 
	 * @return True if messages should be sent by the system; false if messages
	 *         should be ignored.
	 */
	boolean shouldSendMail();

	/**
	 * Create and save an email message to the queue to send.
	 * 
	 * @param to
	 *            Send e-mail to this {@link Person}
	 * @param messageTemplateId
	 *            Message template
	 * @param templateParameters
	 *            Template parameters to use to fill the template
	 * @throws ObjectNotFoundException
	 *             If the current user or administrator could not be loaded.
	 */
	@Transactional(readOnly = false)
	void createMessage(Person to, UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws ObjectNotFoundException;

	/**
	 * Create and save an email message to the queue to send.
	 * 
	 * @param to
	 *            Email address to send this message
	 * @param messageTemplateId
	 *            Message template
	 * @param templateParameters
	 *            Template parameters to use to fill the template
	 * @return Generated message.
	 * 
	 *         <p>
	 *         Already saved to queue, does not need further processing.)
	 * @throws ObjectNotFoundException
	 *             If the current user or administrator could not be loaded.
	 */
	Message createMessage(String to, UUID messageTemplateId,
			Map<String, Object> templateParameters)
			throws ObjectNotFoundException;

	/**
	 * Send any queued messages. Should be set to run regularly via some kind of
	 * scheduling system.
	 * 
	 * @throws SendFailedException
	 *             If the e-mail (message) could not be sent.
	 * @throws ObjectNotFoundException
	 *             If current user or the administrator info could not be
	 *             loaded.
	 */
	@Transactional(readOnly = false)
	void sendQueuedMessages() throws ObjectNotFoundException,
			SendFailedException;

	/**
	 * Send a specific e-mail message immediately instead of relying on a
	 * scheduled call to {@link #sendQueuedMessages()}.
	 * 
	 * <p>
	 * The use of this method is discouraged unless it is called by the
	 * implementation of {@link #sendQueuedMessages()}.
	 * 
	 * @param message
	 *            Email message to send
	 * @return True if the mail provider accepted the message for sending. A
	 *         true response from this method is not a guarantee of delivery to
	 *         the recipient!
	 * @throws SendFailedException
	 *             If the e-mail (message) could not be sent.
	 * @throws ObjectNotFoundException
	 *             If current user or the administrator info could not be
	 *             loaded.
	 */
	boolean sendMessage(Message message) throws SendFailedException,
			ObjectNotFoundException;
}