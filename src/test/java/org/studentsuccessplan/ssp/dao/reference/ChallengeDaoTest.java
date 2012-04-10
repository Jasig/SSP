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

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ChallengeDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeDaoTest.class);

	@Autowired
	transient private ChallengeDao dao;

	@Autowired
	transient private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		Challenge obj = new Challenge();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setShowInSelfHelpSearch(false);
		obj.setShowInStudentIntake(false);
		dao.save(obj);

		assertNotNull("obj.id should not have been null.", obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		final List<Challenge> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		UUID id = UUID.randomUUID();
		Challenge challenge = dao.get(id);

		assertNull(challenge);
	}

	private void assertList(List<Challenge> objects) {
		for (Challenge object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		Challenge obj = new Challenge();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		Challenge obj2 = new Challenge();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj2);

		LOGGER.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

	@Test
	public void searchByQuery() {
		String filter = "issue";

		List<Challenge> challenges = dao.searchByQuery(filter);
		assertList(challenges);
		assertTrue(!challenges.isEmpty());

		LOGGER.debug(Integer.toString(challenges.size()));
	}

	@Test
	public void getAllInStudentIntake() {
		List<Challenge> challenges = dao.getAllInStudentIntake();
		assertList(challenges);
		assertTrue(!challenges.isEmpty());
	}

	@Test
	public void selectAffirmativeBySelfHelpGuideResponseId() {
		List<Challenge> challenges = dao
				.selectAffirmativeBySelfHelpGuideResponseId(UUID.randomUUID());
		assertList(challenges);
	}
}
