package org.jasig.ssp.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.GoalDao;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
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
public class GoalDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GoalDaoTest.class);

	@Autowired
	private GoalDao dao;

	@Autowired
	private ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	private ConfidentialityLevel testConfidentialityLevel;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
		testConfidentialityLevel = confidentialityLevelService
				.getAll(new SortingAndPaging(ObjectStatus.ACTIVE)).getRows()
				.iterator().next();
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		Goal obj = new Goal();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setConfidentialityLevel(testConfidentialityLevel);
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		List<Goal> all = (List<Goal>) dao.getAll(ObjectStatus.ACTIVE).getRows();
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		Goal goal = dao.get(id);

		assertNull(goal);
	}

	private void assertList(final List<Goal> objects) {
		for (Goal object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		Goal obj = new Goal();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setConfidentialityLevel(testConfidentialityLevel);
		dao.save(obj);

		Goal obj2 = new Goal();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		obj2.setConfidentialityLevel(testConfidentialityLevel);
		dao.save(obj2);

		LOGGER.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}
}
