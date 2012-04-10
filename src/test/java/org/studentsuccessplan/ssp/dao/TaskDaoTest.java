package org.studentsuccessplan.ssp.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class TaskDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskDaoTest.class);

	@Autowired
	private TaskDao dao;

	@Autowired
	private PersonService personService;

	private Person ken;

	private Task testTask;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		try {
			ken = personService.personFromUsername("ken");
		} catch (ObjectNotFoundException e) {
			LOGGER.error("can't find one of either sysadmin or ken");
		}
		securityService.setCurrent(ken);

		testTask = new Task();

		dao.save(testTask);
	}

	@After
	public void destroy() {
		dao.delete(testTask);
	}

	@Test
	public void getAllForPersonId() {
		assertList(dao.getAllForPersonId(ken.getId()));
	}

	@Test
	public void getAllForPersonId_complete() {
		assertList(dao.getAllForPersonId(ken.getId(), true));
	}

	@Test
	public void getAllForPersonId_incomplete() {
		assertList(dao.getAllForPersonId(ken.getId(), false));
	}

	@Test
	public void getAllForSessionId() {
		assertList(dao.getAllForSessionId("test session id"));
	}

	@Test
	public void getAllForSessionId_complete() {
		assertList(dao.getAllForSessionId("test session id", true));
	}

	@Test
	public void getAllForSessionId_incomplete() {
		assertList(dao.getAllForSessionId("test session id", false));
	}

	@Test
	public void getAllWhichNeedRemindersSent() {
		assertList(dao.getAllWhichNeedRemindersSent());
	}

	@Test
	public void crud() {
		// save
		// delete
		fail("Not yet implemented");
	}

	@Test
	public void markTask() {
		// markTaskComplete
		// markTaskInComplete
		fail("Not yet implemented");
	}

	@Test
	public void setReminderSentDateToToday() {
		fail("Not yet implemented");
	}

	@Test
	public void getAllForPersonIdAndChallengeReferralId() {
		fail("Not yet implemented");
	}

	@Test
	public void getAllForSessionIdAndChallengeReferralId() {
		fail("Not yet implemented");
	}

	private void assertList(List<Task> objects) {
		for (Task object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}
}
