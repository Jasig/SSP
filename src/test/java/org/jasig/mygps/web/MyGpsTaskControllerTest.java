package org.jasig.mygps.web; // NOPMD

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

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;

public class MyGpsTaskControllerTest {

	private transient MyGpsTaskController controller;

	private transient TaskService service;

	private transient ChallengeService challengeService;

	private transient ChallengeReferralService challengeReferralService;

	private transient PersonService personService;

	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		service = createMock(TaskService.class);
		challengeService = createMock(ChallengeService.class);
		challengeReferralService = createMock(ChallengeReferralService.class);
		personService = createMock(PersonService.class);

		securityService = new SecurityServiceInTestEnvironment();

		controller = new MyGpsTaskController(service, challengeService,
				challengeReferralService, personService, securityService);
	}

	@Test
	public void createTaskForStudent() throws Exception { // NOPMD
		final Person student = new Person();
		student.setUserId("student id");
		final String name = "custom task";
		final String description = "custom task desc";
		final String session = "123456";
		final Task task = new Task();

		expect(personService.personFromUserId(student.getUserId())).andReturn(
				student);
		securityService.setSessionId(session);
		expect(
				service.createCustomTaskForPerson(name, description, student,
						session)).andReturn(task);
		service.sendNoticeToStudentOnCustomTask(task);

		replay(personService);
		replay(service);

		assertTrue(
				"Task creation should have returned success.",
				controller.createTaskForStudent(name, description,
						student.getUserId(), new Date()));

		verify(personService);
		verify(service);
	}

	@Test
	public void createCustom() throws Exception { // NOPMD
		final String name = "custom task";
		final String description = "custom task desc";
		final Task task = new Task();
		final Person student = new Person();
		final String session = "1234";

		securityService.setCurrent(student);
		securityService.setSessionId(session);

		expect(
				service.createCustomTaskForPerson(name, description, student,
						session)).andReturn(task);

		replay(service);

		final TaskTO result = controller.createCustom(name, description);

		verify(service);
		assertNotNull("Custom Task should not have been null.", result);
	}

	@Test
	public void createForChallengeReferral() throws ObjectNotFoundException,
			ValidationException {
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
			final TaskTO response = controller.createForChallengeReferral(
					challengeId, challengeReferralId);

			verify(service);
			verify(challengeService);
			verify(challengeReferralService);
			assertEquals("IDs did not match.", task.getId(), response.getId());
		} catch (final Exception e) {
			fail("Some controller error thrown: " + e.getMessage());
		}
	}

	@Test
	public void delete() {
		final UUID taskId = UUID.randomUUID();

		try {
			service.delete(taskId);

			replay(service);

			final Boolean response = controller.delete(taskId);

			verify(service);
			assertTrue("Delete action should have returned success.", response);
		} catch (final Exception e) {
			fail("A controller error was thrown: " + e.getMessage());
		}
	}

	@Test
	public void email() throws Exception { // NOPMD
		final Person student = new Person();
		final String session = "12345";
		final String emailAddress = "asdf@a.com";
		final List<Task> tasks = Lists.newArrayList();

		final List<String> emailAddresses = Lists.newArrayList();
		emailAddresses.add(emailAddress);

		securityService.setCurrent(student);
		securityService.setSessionId(session);

		expect(
				service.getAllForPerson(eq(student), eq(false),
						eq(securityService.currentUser()),
						isA(SortingAndPaging.class))).andReturn(tasks);
		service.sendTasksForPersonToEmail(tasks, student, emailAddresses, null);

		replay(service);

		try {
			final Boolean response = controller.email(emailAddress);

			verify(service);
			assertTrue("Email action should have returned success.", response);
		} catch (final Exception e) {
			fail("controller error");
		}
	}

	@Test
	public void getAll() {
		final Person student = new Person();
		final String session = "12345";
		final PagingWrapper<Task> tasks = new PagingWrapper<Task>(
				new ArrayList<Task>());

		securityService.setCurrent(student);
		securityService.setSessionId(session);

		expect(
				service.getAllForPerson(eq(student),
						eq(securityService.currentUser()),
						isA(SortingAndPaging.class))).andReturn(tasks);

		replay(service);

		try {
			final List<TaskTO> response = controller.getAll();

			verify(service);
			assertNotNull("Task.getAll should not have return null.", response);
		} catch (final Exception e) {
			fail("controller error");
		}
	}

	@Test
	public void mark() {
		final UUID taskId = UUID.randomUUID();
		final Boolean complete = false;
		final Task task = new Task();
		task.setId(taskId);

		try {
			expect(service.get(taskId)).andReturn(task);
			service.markTaskCompletion(task, complete);

			replay(service);

			final TaskTO response = controller.mark(taskId, complete);

			verify(service);
			assertEquals("IDs did not match.", task.getId(), response.getId());
		} catch (final Exception e) {
			fail("controller error");
		}
	}

	@Ignore
	@Test
	public void print() {
		fail("not implemented");
	}
}
