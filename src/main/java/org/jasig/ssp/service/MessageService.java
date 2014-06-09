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
package org.jasig.ssp.service;

import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.util.CallableExecutor;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

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
	 * @param emailCC
	 * @param subjAndBody
	 *            SubjectAndBody subjAndBody
	 * @return Generated message.
	 * 
	 *         <p>
	 *         Already saved to queue, does not need further processing.)
	 * @throws ObjectNotFoundException
	 *             If the current user or administrator could not be loaded.
	 * @throws SendFailedException
	 *             If message could not be created and put in the message queue.
	 * @throws ValidationException
	 *             If any data was missing or invalid.
	 */
	Message createMessage(@NotNull Person to, String emailCC,
			@NotNull SubjectAndBody subjAndBody)
			throws ObjectNotFoundException, SendFailedException,
			ValidationException;

	/**
	 * Create and save an email message to the queue to send.
	 * 
	 * @param to
	 *            Email address to send this message
	 * @param emailCC
	 * 
	 * @param subjAndBody
	 *            SubjectAndBody subjAndBody
	 * @return Generated message.
	 * 
	 *         <p>
	 *         Already saved to queue, does not need further processing.)
	 * @throws ObjectNotFoundException
	 *             If the current user or administrator could not be loaded.
	 */
	Message createMessage(@NotNull String to, String emailCC,
			@NotNull SubjectAndBody subjAndBody)
			throws ObjectNotFoundException;

	/**
	 * Send any queued messages. Should be set to run regularly via some kind of
	 * scheduling system.
	 */
	void sendQueuedMessages(CallableExecutor<Pair<PagingWrapper<Message>, Collection<Throwable>>> batchExec);

	Pair<PagingWrapper<Message>, Collection<Throwable>> getSendQueuedMessagesBatchExecReturnType();

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
	 * @param emailCC
	 *            Additional CC to add to the message; optional, can be null
	 * @return True if the mail provider accepted the message for sending. A
	 *         true response from this method is not a guarantee of delivery to
	 *         the recipient!
	 * @throws SendFailedException
	 *             If the e-mail (message) could not be sent.
	 * @throws ObjectNotFoundException
	 *             If current user or the administrator info could not be
	 *             loaded.
	 * @throws UnsupportedEncodingException 
	 */
	boolean sendMessage(Message message) throws SendFailedException,
			ObjectNotFoundException, UnsupportedEncodingException;

}