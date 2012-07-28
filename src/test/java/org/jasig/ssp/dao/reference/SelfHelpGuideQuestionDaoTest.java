package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
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
public class SelfHelpGuideQuestionDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SelfHelpGuideQuestionDaoTest.class);

	@Autowired
	private transient SelfHelpGuideQuestionDao dao;

	@Autowired
	private transient SelfHelpGuideDao selfHelpGuideDao;

	@Autowired
	private transient ChallengeDao challengeDao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private transient SelfHelpGuide testGuide;

	private transient Challenge testChallenge;

	@Before
	public void setUp() throws ObjectNotFoundException {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
		testGuide = selfHelpGuideDao.get(UUID
				.fromString("2597d6a8-c95e-40a5-a3fd-e0d95967b1a0"));
		testChallenge = challengeDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next();
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		SelfHelpGuideQuestion obj = new SelfHelpGuideQuestion();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setChallenge(testChallenge);
		obj.setSelfHelpGuide(testGuide);
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		final Collection<SelfHelpGuideQuestion> all = dao.getAll(
				ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final SelfHelpGuideQuestion selfHelpGuideQuestion = dao.get(id);

		assertNull(selfHelpGuideQuestion);
	}

	private void assertList(final Collection<SelfHelpGuideQuestion> objects) {
		for (final SelfHelpGuideQuestion object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		final SelfHelpGuideQuestion obj = new SelfHelpGuideQuestion();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setChallenge(testChallenge);
		obj.setSelfHelpGuide(testGuide);
		dao.save(obj);

		final SelfHelpGuideQuestion obj2 = new SelfHelpGuideQuestion();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		obj2.setChallenge(testChallenge);
		obj2.setSelfHelpGuide(testGuide);
		dao.save(obj2);

		LOGGER.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}
}