package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ChallengeCategory;
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
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ChallengeCategoryDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeCategoryDaoTest.class);

	@Autowired
	private transient ChallengeCategoryDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		ChallengeCategory obj = new ChallengeCategory();
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

		final Collection<ChallengeCategory> all = dao.getAll(
				ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		final UUID id = UUID.randomUUID();
		final ChallengeCategory challengeCategory = dao.get(id);

		assertNull(challengeCategory);
	}

	private void assertList(final Collection<ChallengeCategory> objects) {
		for (ChallengeCategory object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		final ChallengeCategory obj = new ChallengeCategory();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		dao.delete(obj);
	}
}
