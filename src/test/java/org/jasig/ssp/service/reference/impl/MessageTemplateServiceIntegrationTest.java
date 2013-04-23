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
package org.jasig.ssp.service.reference.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.GoalTOFactory;
import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.factory.reference.PlanTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.GoalService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.util.service.stub.Stubs;
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

	private static final String TEST_SUBJECT = "Test Subject";

	private static final String TEST_BODY = "Test Body";

	private static final UUID TEST_TASK_ID = UUID
			.fromString("f42f4970-b566-11e1-a224-0026b9e7ff4c");

	private static final String TEST_TASK_NAME = "Test Task Name";

	private static final UUID TEST_GOAL_ID = UUID
			.fromString("1B18BF52-BFC7-11E1-9CB8-0026B9E7FF4C");

	private static final String TEST_GOAL_NAME = "Test Goal Name";

	@Autowired
	private transient MessageTemplateService service;

	@Autowired
	private transient GoalService goalService;

	@Autowired
	private transient GoalTOFactory goalTOFactory;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient TaskService taskService;

	@Autowired
	private transient TaskTOFactory taskTOFactory;
	
	@Autowired
	private transient PlanService planService;
	
	@Autowired
	private transient PlanTOFactory planTOFactory;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

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
		final SubjectAndBody subjAndBody = service.createContactCoachMessage(
				TEST_BODY, TEST_SUBJECT, personService.get(UUID
						.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194")));
		assertSubjectAndBody(subjAndBody, "A Message from Your Advisee",
				TEST_BODY);
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
				.createCustomActionPlanTaskMessage(taskService
						.get(TEST_TASK_ID));
		assertSubjectAndBody(subjAndBody, "An Action Item is Due for Review",
				"Kenneth,<br/>An Action Item ");
	}

	@Test
	public void createActionPlanMessage() throws ObjectNotFoundException {
		final List<TaskTO> taskTOs = Lists.newArrayList();
		taskTOs.add(taskTOFactory.from(taskService.get(TEST_TASK_ID)));

		final List<GoalTO> goalTOs = Lists.newArrayList();
		goalTOs.add(goalTOFactory.from(goalService.get(TEST_GOAL_ID)));

		final SubjectAndBody subjAndBody = service.createActionPlanMessage(
				personService.get(UUID
						.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea")),
				taskTOs, goalTOs);
		assertSubjectAndBody(subjAndBody, "Action plan for Kenneth Thompson",
				"<html>\n<body>Dear Kenneth Thompson,<br/>");
		assertTrue("Body should contain the name of a task", subjAndBody
				.getBody().contains(TEST_TASK_NAME));
		assertTrue("Body should contain the name of a goal", subjAndBody
				.getBody().contains(TEST_GOAL_NAME));
	}

	@Test
	public void createStudentIntakeTaskMessage() throws ObjectNotFoundException {
		final SubjectAndBody subjAndBody = service
				.createStudentIntakeTaskMessage(taskService.get(TEST_TASK_ID));
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