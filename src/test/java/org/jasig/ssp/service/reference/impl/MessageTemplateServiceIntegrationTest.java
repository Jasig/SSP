package org.jasig.ssp.service.reference.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.reference.MessageTemplate;
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
		final String termToRepresentEarlyAlert = "termToRepresentEarlyAlert"; // NOPMD
		final EarlyAlert earlyAlert = new EarlyAlert();
		earlyAlert.setCourseName("CourseNameHere");

		final MessageTemplate template = service
				.get(MessageTemplate.JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID);

		assertNotNull("Template for testing should not have returned null.",
				template);

		// Try to process with the template engine
		final SubjectAndBody subjAndBody = service
				.createJournalNoteForEarlyAlertResponseMessage(
						termToRepresentEarlyAlert, earlyAlert);

		assertNotNull("Generated message should not have been null.",
				subjAndBody);
		assertTrue("Templated subject did not match.", subjAndBody.getSubject()
				.contains("CourseNameHere"));
		assertTrue("Templated body text did not match.", subjAndBody.getBody()
				.contains("CourseNameHere"));
	}
}