package org.studentsuccessplan.ssp.dao.reference;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ConfidentialityLevelDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfidentialityLevelDaoTest.class);

	@Autowired
	private transient ConfidentialityLevelDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		ConfidentialityLevel obj = new ConfidentialityLevel();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setAcronym("acro");
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		List<ConfidentialityLevel> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		UUID id = UUID.randomUUID();
		ConfidentialityLevel confidentialityLevel = dao.get(id);

		assertNull(confidentialityLevel);
	}

	private void assertList(final List<ConfidentialityLevel> objects) {
		for (ConfidentialityLevel object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		ConfidentialityLevel obj = new ConfidentialityLevel();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setAcronym("acro");
		dao.save(obj);

		ConfidentialityLevel obj2 = new ConfidentialityLevel();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		obj2.setAcronym("acro2");
		dao.save(obj2);

		LOGGER.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

}
