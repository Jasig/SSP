package org.studentsuccessplan.ssp.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import org.studentsuccessplan.ssp.model.SelfHelpGuideResponse;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class SelfHelpGuideResponseDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageDaoTest.class);

	@Autowired
	private SelfHelpGuideResponseDao dao;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user for use by
	 * {@link #testSaveNew()} that checks that the Auditable auto-fill
	 * properties are correctly filled.
	 */
	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void forEarlyAlert() {
		assertList(dao.forEarlyAlert());
	}

	private void assertList(List<SelfHelpGuideResponse> objects) {
		for (SelfHelpGuideResponse object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}

	@Test
	public void testGetAll() {
		assertList(dao.getAll(ObjectStatus.ALL));
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		Person person = personDao.getAll(ObjectStatus.ACTIVE).get(0);
		assertNotNull(
				"Needed random Person to use for testing, but none was found in the database.",
				person.getId());

		SelfHelpGuideResponse obj = new SelfHelpGuideResponse();
		obj.setSelfHelpGuide(new SelfHelpGuide(UUID
				.fromString("2597d6a8-c95e-40a5-a3fd-e0d95967b1a0")));
		obj.setPerson(person);
		obj.setCancelled(false);
		obj.setCompleted(false);
		obj.setEarlyAlertSent(true);
		dao.save(obj);

		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());

		List<SelfHelpGuideResponse> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}
}
