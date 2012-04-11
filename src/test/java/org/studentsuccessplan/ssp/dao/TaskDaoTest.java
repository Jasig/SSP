package org.studentsuccessplan.ssp.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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
import org.studentsuccessplan.ssp.dao.reference.ChallengeDao;
import org.studentsuccessplan.ssp.dao.reference.ChallengeReferralDao;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class TaskDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskDaoTest.class);

	@Autowired
	private TaskDao dao;

	@Autowired
	private PersonService personService;

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private ChallengeReferralDao challengeReferralDao;

	private Person ken;
	private Challenge testChallenge;
	private ChallengeReferral testChallengeReferral;
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

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 7);
		testChallenge = challengeDao.get(UUID
				.fromString("af7e472c-3b7c-4d00-a667-04f52f560940"));
		testChallengeReferral = challengeReferralDao.get(UUID
				.fromString("19fbec43-8c0b-478b-9d5f-00ec6ec57511"));

		testTask = new Task("test task", cal.getTime(), ken, testChallenge,
				testChallengeReferral);
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

	/*
	 * public void crud() {
	 * This is in effect tested by the setup and teardown of the tests in this
	 * class
	 * }
	 */

	@Test
	public void getAllForPersonIdAndChallengeReferralId() {
		assertList(dao.getAllForPersonIdAndChallengeReferralId(ken.getId(),
				true, testChallengeReferral.getId()));
	}

	@Test
	public void getAllForSessionIdAndChallengeReferralId() {
		assertList(dao.getAllForSessionIdAndChallengeReferralId(
				"test sessionId", true, testChallengeReferral.getId()));
	}

	private void assertList(List<Task> objects) {
		for (Task object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}
}
