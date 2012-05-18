package org.jasig.ssp.service.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

/**
 * MessageTemplate service tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class MessageTemplateServiceIntegrationTest {

	@Autowired
	private transient MessageTemplateService service;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testVelocityEngineUsingMessageTemplates()
			throws ObjectNotFoundException {
		final String subjectText = "My subject"; // NOPMD
		final String facultyMemberText = "Faculty Full Name"; // NOPMD

		final MessageTemplate template = service
				.get(MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID);

		assertNotNull("Template for testing should not have returned null.",
				template);

		// Try to process with the template engine
		final Map<String, Object> templateParameters = Maps.newHashMap();
		templateParameters.put("subj", subjectText);
		templateParameters.put("FacultyMember", facultyMemberText);
		final Message message = messageService.createMessage(
				"test@example.com",
				MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID,
				templateParameters);

		assertNotNull("Generated message should not have been null.", message);
		assertEquals("Templated subject did not match.", subjectText,
				message.getSubject());
		assertTrue("Templated body text did not match.", message.getBody()
				.contains("Faculty Member: " + facultyMemberText));
	}
}