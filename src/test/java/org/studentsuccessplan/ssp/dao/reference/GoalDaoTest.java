package org.studentsuccessplan.ssp.dao.reference;

import static org.junit.Assert.assertFalse;
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
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.model.reference.Goal;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.studentsuccessplan.ssp.service.reference.ConfidentialityLevelService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class GoalDaoTest {

	private static final Logger logger = LoggerFactory
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
	public void testSaveNew() {
		UUID saved;

		Goal obj = new Goal();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setConfidentialityLevel(testConfidentialityLevel);
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		logger.debug(obj.toString());

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

	@Test
	public void testNull() {
		UUID id = UUID.randomUUID();
		Goal goal = dao.get(id);

		assertNull(goal);
	}

	private void assertList(List<Goal> objects) {
		for (Goal object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
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

		logger.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

}
