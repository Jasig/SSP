package org.studentsuccessplan.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studentsuccessplan.mygps.business.TaskManager;
import org.studentsuccessplan.mygps.model.transferobject.TaskTO;
import org.studentsuccessplan.ssp.service.TaskService;

public class MyGpsTaskControllerTest {

	private MyGpsTaskController controller;
	private TaskManager manager;
	private TaskService service;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsTaskControllerTest.class);

	@Before
	public void setup() {
		manager = createMock(TaskManager.class);
		service = createMock(TaskService.class);

		controller = new MyGpsTaskController(manager, service);
	}

	@Ignore
	@Test
	public void createTaskForStudent() {
		fail("not implemented");
	}

	@Ignore
	@Test
	public void createCustom() {
		fail("not implemented");
	}

	@Test
	public void createForChallengeReferral() {
		UUID challengeId = UUID.randomUUID();
		UUID challengeReferralId = UUID.randomUUID();
		TaskTO task = new TaskTO();
		expect(
				manager.createTaskForChallengeReferral(challengeId,
						challengeReferralId)).andReturn(task);

		replay(manager);

		try {
			TaskTO response = controller.createForChallengeReferral(
					challengeId, challengeReferralId);

			verify(manager);
			assertEquals(task, response);
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
			assertFalse(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void email() {

		String emailAddress = "asdf@a.com";

		expect(manager.email(emailAddress)).andReturn(false);

		replay(manager);

		try {
			Boolean response = controller.email(emailAddress);

			verify(manager);
			assertFalse(response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void getAll() {
		List<TaskTO> tasks = new ArrayList<TaskTO>();
		expect(manager.getAllTasks()).andReturn(tasks);

		replay(manager);

		try {
			List<TaskTO> response = controller.getAll();

			verify(manager);
			assertEquals(tasks, response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void mark() {
		String taskId = "prefix-taskId-postfix";
		Boolean complete = false;
		TaskTO task = new TaskTO();

		try {
			expect(manager.markTask(taskId, complete)).andReturn(task);

			replay(manager);

			TaskTO response = controller.mark(taskId, complete);

			verify(manager);
			assertEquals(task, response);
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
