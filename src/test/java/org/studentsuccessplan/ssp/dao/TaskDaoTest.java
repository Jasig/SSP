package org.studentsuccessplan.ssp.dao;

import java.util.Calendar;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.ChallengeDao;
import org.studentsuccessplan.ssp.dao.reference.ChallengeReferralDao;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class TaskDaoTest extends AbstractTaskDaoTest<Task> {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(TaskDaoTest.class);

	@Autowired
	private TaskDao dao;

	@Override
	protected TaskDao getDao() {
		return dao;
	}

	@Autowired
	private ChallengeDao challengeDao;

	@Autowired
	private ChallengeReferralDao challengeReferralDao;

	private Challenge testChallenge;
	private ChallengeReferral testChallengeReferral;
	private Task testTask;

	@Override
	@Before
	public void setUp() {
		super.setUp();

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

	@Test
	public void getAllForTaskGroupId() {
		assertList(dao.getAllForTaskGroupId(UUID.randomUUID(), true,
				UUID.randomUUID(), null));
	}
}
