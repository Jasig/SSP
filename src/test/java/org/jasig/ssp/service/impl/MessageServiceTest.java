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
/**
 * 
 */
package org.jasig.ssp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import org.jasig.ssp.config.MockMailService;
import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.SendFailedException;

/**
 * @author jon.adams
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration()
@Transactional
public class MessageServiceTest {

	@Autowired
	private transient MessageService service;

	@Autowired
	private transient MessageDao messageDao;

	@Autowired
	private transient MockMailService mockMailService;

	@Autowired
	private transient ConfigService configService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Ensures mail testing config value is available and enabled, via
	 * {@link org.jasig.ssp.service.impl.MessageServiceImpl#shouldSendMail()}
	 */
	@Test
	public void testShouldSendMailIsEnabled() {
		assertTrue(
				"Send mail functionaility for testing not enabled in your ssp-config.xml configuration settings for \"send_mail\". This should always be enabled in the testing environment.",
				service.shouldSendMail());
	}

	@Test
	public void sendsMessageWithInvalidCc() throws ObjectNotFoundException,
			SendFailedException, UnsupportedEncodingException {

		final SimpleSmtpServer smtpServer = mockMailService.getSmtpServer();
		assertFalse("Faux mail server should be running but was not.",
				smtpServer.isStopped());

		final Message message =
				service.createMessage("to@email.com", "cc@invalid domain",
						new SubjectAndBody("Subject", "Message"));
		service.sendMessage(message);
		assertNotNull("Message not flagged as sent",
				messageDao.get(message.getId()).getSentDate());
		assertEquals("Message wasn't actually sent.", 1,
				smtpServer.getReceivedEmailSize());
		final SmtpMessage receivedMessage = (SmtpMessage) smtpServer
				.getReceivedEmail()
				.next();
		assertEquals(
				"Unexpected sent message. Subject was wrong.", "Subject",
				receivedMessage.getHeaderValue("Subject"));
	}

	@Test
	public void sendsMessageWithInvalidBcc() throws ObjectNotFoundException,
			SendFailedException, ValidationException, UnsupportedEncodingException {

		// setup
		final SimpleSmtpServer smtpServer = mockMailService.getSmtpServer();
		assertFalse("Faux mail server should be running but was not.",
				smtpServer.isStopped());

		// even more setup
		SortingAndPaging sap = new SortingAndPaging(null, 0,
				SortingAndPaging.MAXIMUM_ALLOWABLE_RESULTS, null, null, null);
		PagingWrapper<Config> allConfigs = configService.getAll(null);
		for ( Config config : allConfigs ) {
			if ( config.getName().equalsIgnoreCase("bcc_email_address")) {
				config.setValue("bcc@invalid domain");
				configService.save(config);
				break;
			}
		}

		// setup setup setup (no cc b/c bcc not sent unless cc unset)
		final Message message =
				service.createMessage("to@email.com", null,
						new SubjectAndBody("Subject", "Message"));

		// actual test
		service.sendMessage(message);
		assertNotNull("Message not flagged as sent",
				messageDao.get(message.getId()).getSentDate());
		assertEquals("Message wasn't actually sent.", 1,
				smtpServer.getReceivedEmailSize());
		final SmtpMessage receivedMessage = (SmtpMessage) smtpServer
				.getReceivedEmail()
				.next();
		assertEquals(
				"Unexpected sent message. Subject was wrong.", "Subject",
				receivedMessage.getHeaderValue("Subject"));
	}

	@Test
	public void sendQueuedMessagesDoesNotGetStuckOnErroredMessages()
			throws ObjectNotFoundException {

		// setup
		final SimpleSmtpServer smtpServer = mockMailService.getSmtpServer();
		assertFalse("Faux mail server should be running but was not.",
				smtpServer.isStopped());

		// we know the batch size on sendQueuedMessages() is 25. So create
		// 26 messages, the first 25 of which are bad. The 26th should still
		// be sent, either in the first or second call to sendQueuedMessages()
		// (we don't really care how the impl "unstucks" itself from completely
		// errored batches)
		Message validMsg = null;
		for ( int i = 0; i < 26; i++ ) {
			if ( i == 25 ) {
				// valid message
				validMsg =
						service.createMessage("to@email.com", null,
								new SubjectAndBody("Subject " + i, "Message " + i));
			} else {
				// invalid message
				service.createMessage("to@invalid domain", null,
								new SubjectAndBody("Subject " + i, "Message " + i));
			}
		}

		// intentionally call twice. see comments above.
		service.sendQueuedMessages(null);
		service.sendQueuedMessages(null);

		assertNotNull("Valid message not flagged as sent",
				messageDao.get(validMsg.getId()).getSentDate());
		assertEquals("Unexpected number of sent messages.", 1,
				smtpServer.getReceivedEmailSize());
		final SmtpMessage receivedMessage = (SmtpMessage) smtpServer
				.getReceivedEmail()
				.next();
		assertEquals(
				"Unexpected sent message. Subject was wrong.", "Subject 25", // 0-based naming
				receivedMessage.getHeaderValue("Subject"));
	}
}