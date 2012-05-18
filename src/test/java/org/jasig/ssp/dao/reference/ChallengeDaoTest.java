package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
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
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ChallengeDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeDaoTest.class);

	private static final UUID CONFIDENTIALITYLEVEL_ID = UUID
			.fromString("afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c");

	private static final String CONFIDENTIALITYLEVEL_NAME = "EVERYONE";

	@Autowired
	transient private ChallengeDao dao;

	@Autowired
	transient private ConfidentialityLevelDao confidentialityLevelDao;

	@Autowired
	transient private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		Challenge obj = new Challenge();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setShowInSelfHelpSearch(false);
		obj.setShowInStudentIntake(false);
		obj.setDefaultConfidentialityLevel(confidentialityLevelDao
				.load(CONFIDENTIALITYLEVEL_ID));
		dao.save(obj);

		assertNotNull("obj.id should not have been null.", obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());
		assertEquals("Confidentiality level name did not match.",
				CONFIDENTIALITYLEVEL_NAME, obj.getDefaultConfidentialityLevel()
						.getName());

		final Collection<Challenge> all = dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertFalse("GetAll() list should not be empty.", all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Challenge challenge = dao.get(id);

		assertNull(challenge);
	}

	private void assertList(final Collection<Challenge> objects) {
		for (final Challenge object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		final Challenge obj = new Challenge();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		final Challenge obj2 = new Challenge();
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
		final List<Challenge> challenges = dao.searchByQuery("issue");
		assertList(challenges);
		assertFalse("Search list should have returned some items.",
				challenges.isEmpty());

		LOGGER.debug(Integer.toString(challenges.size()));
	}

	@Test
	public void getAllInStudentIntake() {
		final List<Challenge> challenges = dao
				.getAllInStudentIntake(new SortingAndPaging(ObjectStatus.ACTIVE));
		assertList(challenges);
		assertFalse(challenges.isEmpty());
	}

	@Test
	public void selectAffirmativeBySelfHelpGuideResponseId() {
		final List<Challenge> challenges = dao
				.selectAffirmativeBySelfHelpGuideResponseId(UUID.randomUUID());
		assertList(challenges);
	}
}
