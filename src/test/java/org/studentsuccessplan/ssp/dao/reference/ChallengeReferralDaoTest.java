package org.studentsuccessplan.ssp.dao.reference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

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
import org.studentsuccessplan.ssp.dao.PersonDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ChallengeReferralDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeReferralDaoTest.class);

	@Autowired
	private transient ChallengeReferralDao dao;

	@Autowired
	private transient ChallengeDao challengeDao;

	private transient Challenge testChallenge;

	@Autowired
	private transient PersonDao personDao;

	private transient Person testStudent;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));

		testChallenge = challengeDao.getAll(ObjectStatus.ACTIVE).get(0);
		testStudent = personDao.fromUsername("ken");
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		ChallengeReferral obj = new ChallengeReferral();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		final List<ChallengeReferral> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		final UUID id = UUID.randomUUID();
		final ChallengeReferral challengeReferral = dao.get(id);

		assertNull(challengeReferral);
	}

	private void assertList(List<ChallengeReferral> objects) {
		for (ChallengeReferral object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}

	@Test
	public void uuidGeneration() {
		final ChallengeReferral obj = new ChallengeReferral();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		final ChallengeReferral obj2 = new ChallengeReferral();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj2);

		LOGGER.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

	@Test
	public void byChallengeId() {
		List<ChallengeReferral> crs =
				dao.byChallengeId(testChallenge.getId());
		assertList(crs);
	}

	@Test
	public void byChallengeIdAndQuery() {
		List<ChallengeReferral> crs =
				dao.byChallengeIdAndQuery(testChallenge.getId(), "issue");
		assertList(crs);
	}

	@Test
	public void countByChallengeIdNotOnActiveTaskList() {
		long count =
				dao.countByChallengeIdNotOnActiveTaskList(
						testChallenge.getId(), testStudent, "testSessionId");
		assertTrue(count > -1);
	}

	@Test
	public void byChallengeIdNotOnActiveTaskList() {
		List<ChallengeReferral> crs =
				dao.byChallengeIdNotOnActiveTaskList(testChallenge.getId(),
						testStudent, "testSessionId");
		assertList(crs);
	}

}
