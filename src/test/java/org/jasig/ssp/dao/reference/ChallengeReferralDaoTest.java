package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
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
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));

		testChallenge = challengeDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next();
		testStudent = personDao.fromUsername("ken");
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		ChallengeReferral obj = new ChallengeReferral();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull("Saved object should not have been null.", obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull("Saved object should not have been null.", obj);
		assertNotNull("Saved object ID should not have been null.", obj.getId());
		assertNotNull("Saved object Name should not have been null.",
				obj.getName());

		final List<ChallengeReferral> all = (List<ChallengeReferral>) dao
				.getAll(
						ObjectStatus.ACTIVE).getRows();
		assertNotNull("GetAll result should not have been null.", all);
		assertFalse("GetAll list should not have been empty.", all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ChallengeReferral challengeReferral = dao.get(id);

		assertNull("Get result should not have been null.", challengeReferral);
	}

	private void assertList(final Collection<ChallengeReferral> objects) {
		for (final ChallengeReferral object : objects) {
			assertNotNull("List object should not have been null.",
					object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		final ChallengeReferral obj = new ChallengeReferral();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull("Transient instance was not assigned a new identifier.",
				obj.getId());

		final ChallengeReferral obj2 = new ChallengeReferral();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj2);

		assertNotNull("Transient instance was not assigned a new identifier.",
				obj2.getId());

		dao.delete(obj);
		dao.delete(obj2);
	}

	@Test
	public void byChallengeId() {
		final List<ChallengeReferral> crs = dao.byChallengeId(testChallenge
				.getId());
		assertList(crs);
	}

	@Test
	public void byChallengeIdAndQuery() {
		final List<ChallengeReferral> crs = dao.byChallengeIdAndQuery(
				testChallenge.getId(), "issue");
		assertList(crs);
	}

	@Test
	public void countByChallengeIdNotOnActiveTaskList() {
		final long count = dao.countByChallengeIdNotOnActiveTaskList(
				testChallenge.getId(), testStudent, "testSessionId");
		assertTrue("Ensure count result is non-negative.", count > -1);
	}

	@Test
	public void byChallengeIdNotOnActiveTaskList() {
		final List<ChallengeReferral> crs = dao
				.byChallengeIdNotOnActiveTaskList(
						testChallenge.getId(), testStudent, "testSessionId");
		assertList(crs);
	}

	@Test
	public void getAllForChallenge() {
		final PagingWrapper<ChallengeReferral> challengeReferrals = dao
				.getAllForChallenge(
						UUID.randomUUID(), new SortingAndPaging(
								ObjectStatus.ACTIVE));
		assertList(challengeReferrals.getRows());
	}

}
