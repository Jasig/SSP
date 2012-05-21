package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ChallengeCategory;
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
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ChallengeCategoryDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeCategoryDaoTest.class);

	@Autowired
	private transient ChallengeCategoryDao dao;

	@Autowired
	private transient ChallengeDao challengeDao;

	@Autowired
	private transient CategoryDao categoryDao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		ChallengeCategory obj = new ChallengeCategory();
		obj.setChallenge(challengeDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next());
		obj.setCategory(categoryDao.get(UUID
				.fromString("5d24743a-a11e-11e1-a9a6-0026b9e7ff4c")));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());

		final Collection<ChallengeCategory> all = dao.getAll(
				ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
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
	public void uuidGeneration() throws ObjectNotFoundException {
		final ChallengeCategory obj = new ChallengeCategory();
		obj.setChallenge(challengeDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next());
		obj.setCategory(categoryDao.get(UUID
				.fromString("5d24743a-a11e-11e1-a9a6-0026b9e7ff4c")));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		dao.delete(obj);
	}

	@Test
	public void getAllforCategory() {
		final PagingWrapper<ChallengeCategory> wrapper = dao.getAllForCategory(
				UUID.randomUUID(), new SortingAndPaging(ObjectStatus.ACTIVE));
		assertList(wrapper.getRows());
	}

	@Test
	public void getAllforChallengeAndCategory() {
		final PagingWrapper<ChallengeCategory> wrapper = dao
				.getAllForChallengeAndCategory(
						UUID.randomUUID(), UUID.randomUUID(),
						new SortingAndPaging(ObjectStatus.ACTIVE));
		assertList(wrapper.getRows());
	}
}
