package org.jasig.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Lists;

public class MyGpsTaskControllerTest {

	private MyGpsTaskController controller;

	private TaskService service;
	private ChallengeService challengeService;
	private ChallengeReferralService challengeReferralService;
	private PersonService personService;
	private SecurityServiceInTestEnvironment securityService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsTaskControllerTest.class);

	@Before
	public void setup() {
		service = createMock(TaskService.class);
		challengeService = createMock(ChallengeService.class);
		challengeReferralService = createMock(ChallengeReferralService.class);
		personService = createMock(PersonService.class);

		securityService = new SecurityServiceInTestEnvironment();

		controller = new MyGpsTaskController(service, challengeService,
				challengeReferralService, personService, securityService);
	}

	@Test
	public void createTaskForStudent() throws Exception {
		final Person student = new Person();
		student.setUserId("student id");
		final String name = "custom task";
		final String description = "custom task desc";
		final String session = "12345";
		final Task task = new Task();

		expect(personService.personFromUserId(student.getUserId())).andReturn(
				student);
		securityService.setSessionId(session);
		expect(
				service.createCustomTaskForPerson(name, description, student,
						session)).andReturn(task);
		service.sendNoticeToStudentOnCustomTask(task,
				MessageTemplate.ACTION_PLAN_EMAIL_ID);

		replay(personService);
		replay(service);

		assertTrue(controller.createTaskForStudent(name, description,
				student.getUserId(), new Date(),
				MessageTemplate.ACTION_PLAN_EMAIL_ID));

		verify(personService);
		verify(service);
	}

	@Test
	public void createCustom() throws Exception {
		final String name = "custom task";
		final String description = "custom task desc";
		final Task task = new Task();
		final Person student = new Person();
		final String session = "12345";

		securityService.setCurrent(student);
		securityService.setSessionId(session);

		expect(
				service.createCustomTaskForPerson(name, description, student,
						session)).andReturn(task);

		replay(service);

		TaskTO result = controller.createCustom(name, description);

		verify(service);
		assertNotNull(result);
	}

	@Test
	public void createForChallengeReferral() throws Exception {
		final UUID challengeId = UUID.randomUUID();
		final Challenge challenge = new Challenge(challengeId);
		final UUID challengeReferralId = UUID.randomUUID();
		final ChallengeReferral challengeReferral = new ChallengeReferral(
				challengeReferralId);
		final Task task = new Task();
		task.setId(UUID.randomUUID());
		final Person student = new Person();
		final String session = "12345";

		expect(challengeService.get(challengeId)).andReturn(challenge);
		expect(challengeReferralService.get(challengeReferralId)).andReturn(
				challengeReferral);
		securityService.setCurrent(student);
		securityService.setSessionId(session);
		expect(service.createForPersonWithChallengeReferral(challenge,
				challengeReferral, student, session)).andReturn(task);

		replay(service);
		replay(challengeService);
		replay(challengeReferralService);

		try {
			TaskTO response = controller.createForChallengeReferral(
					challengeId, challengeReferralId);

			verify(service);
			verify(challengeService);
			verify(challengeReferralService);
			assertEquals(task.getId(), response.getId());
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void delete() {
		UUID taskId = UUID.randomUUID();

		try {
			service.delete(taskId);

			replay(service);

			Boolean response = controller.delete(taskId);

			verify(service);
			assertTrue(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
			fail("controller error");
		}
	}

	@Test
	public void email() throws Exception {
		final Person student = new Person();
		final String session = "12345";
		String emailAddress = "asdf@a.com";
		List<Task> tasks = Lists.newArrayList();

		List<String> emailAddresses = Lists.newArrayList();
		emailAddresses.add(emailAddress);

		securityService.setCurrent(student);
		securityService.setSessionId(session);

		expect(
				service.getAllForPerson(eq(student), eq(false),
						isA(SortingAndPaging.class))).andReturn(tasks);
		service.sendTasksForPersonToEmail(tasks, student, emailAddresses, null);

		replay(service);

		try {
			Boolean response = controller.email(emailAddress);

			verify(service);
			assertTrue(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void getAll() {
		final Person student = new Person();
		final String session = "12345";
		List<Task> tasks = new ArrayList<Task>();

		securityService.setCurrent(student);
		securityService.setSessionId(session);

		expect(
				service.getAllForPerson(eq(student),
						isA(SortingAndPaging.class))).andReturn(tasks);

		replay(service);

		try {
			List<TaskTO> response = controller.getAll();

			verify(service);
			assertNotNull(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void mark() {
		UUID taskId = UUID.randomUUID();
		Boolean complete = false;
		Task task = new Task();
		task.setId(taskId);

		try {
			expect(service.get(taskId)).andReturn(task);
			service.markTaskCompletion(task, complete);

			replay(service);

			TaskTO response = controller.mark(taskId, complete);

			verify(service);
			assertEquals(task.getId(), response.getId());
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Ignore
	@Test
	public void print() {
		fail("not implemented");
	}
}
