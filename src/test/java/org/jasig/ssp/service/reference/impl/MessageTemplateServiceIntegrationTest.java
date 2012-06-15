package org.jasig.ssp.service.reference.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.TaskTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

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

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient TaskService taskService;

	@Autowired
	private transient TaskTOFactory taskTOFactory;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	private void assertSubjectAndBody(final SubjectAndBody subjAndBody,
			final String subjMustContain, final String bodyMustContain) {
		assertNotNull("Generated message should not have been null.",
				subjAndBody);

		assertTrue("Templated subject did not match.  Subject was:  "
				+ subjAndBody.getSubject(), subjAndBody.getSubject()
				.contains(subjMustContain));
		assertTrue("Templated body text did not match.  Body was:  "
				+ subjAndBody.getBody(), subjAndBody.getBody()
				.contains(bodyMustContain));

		assertFalse(
				"Templated subject contains unfilled params.  Subject was:  "
						+ subjAndBody.getSubject(), subjAndBody.getSubject()
						.contains("${"));
		assertFalse(
				"Templated subject contains unfilled params.  Subject was:  "
						+ subjAndBody.getSubject(), subjAndBody.getSubject()
						.contains("$("));
		assertFalse("Templated body contains unfilled params.  Body was:  "
				+ subjAndBody.getBody(), subjAndBody.getBody().contains("${"));
		assertFalse("Templated body contains unfilled params.  Body was:  "
				+ subjAndBody.getBody(), subjAndBody.getBody().contains("$("));
	}

	@Test
	public void createContactCoachMessage() throws ObjectNotFoundException {
		final String testSubject = "Test Subject";
		final String testBody = "Test Body";
		final SubjectAndBody subjAndBody = service.createContactCoachMessage(
				testBody, testSubject, personService.get(UUID
						.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194")));
		assertSubjectAndBody(subjAndBody, "A Message from Your Advisee",
				testBody);
	}

	@Test
	public void createActionPlanStepMessage() throws ObjectNotFoundException {
		final SubjectAndBody subjAndBody = service.createActionPlanStepMessage(
				taskService.get(UUID
						.fromString("4a24c8c2-b568-11e1-b82e-0026b9e7ff4c")));
		assertSubjectAndBody(subjAndBody, "An Action Item is Due for Review",
				"Kenneth,<br/>An Action Item ");
	}

	@Test
	public void createCustomActionPlanTaskMessage()
			throws ObjectNotFoundException {
		final SubjectAndBody subjAndBody = service
				.createCustomActionPlanTaskMessage(
				taskService.get(UUID
						.fromString("f42f4970-b566-11e1-a224-0026b9e7ff4c")));
		assertSubjectAndBody(subjAndBody, "An Action Item is Due for Review",
				"Kenneth,<br/>An Action Item ");
	}

	@Test
	public void createActionPlanMessage() throws ObjectNotFoundException {
		final List<TaskTO> taskTOs = Lists.newArrayList();
		taskTOs.add(taskTOFactory.from(taskService.get(UUID
				.fromString("f42f4970-b566-11e1-a224-0026b9e7ff4c"))));

		final SubjectAndBody subjAndBody = service.createActionPlanMessage(
				personService.get(UUID
						.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea")),
				taskTOs);
		assertSubjectAndBody(subjAndBody, "Action plan for Kenneth Thompson",
				"<html><body>Dear Kenneth Thompson,");
		assertTrue("Body should contain the name of a task", subjAndBody
				.getBody().contains("Test Task Name"));
	}

	@Test
	public void createStudentIntakeTaskMessage() throws ObjectNotFoundException {
		final SubjectAndBody subjAndBody = service
				.createStudentIntakeTaskMessage(taskService.get(UUID
						.fromString("f42f4970-b566-11e1-a224-0026b9e7ff4c")));
		assertSubjectAndBody(subjAndBody, "Student Intake Form Request",
				"<html><body><p>Hello Kenneth Thompson");
	}

	@Test
	public void createJournalNoteForEarlyAlertResponseMessage() {

		final String termToRepresentEarlyAlert = "termToRepresentEarlyAlert"; // NOPMD
		final EarlyAlert earlyAlert = new EarlyAlert();
		earlyAlert.setCourseName("CourseNameHere");

		final SubjectAndBody subjAndBody = service
				.createJournalNoteForEarlyAlertResponseMessage(
						termToRepresentEarlyAlert, earlyAlert);

		assertSubjectAndBody(subjAndBody, "CourseNameHere", "CourseNameHere");
	}
}