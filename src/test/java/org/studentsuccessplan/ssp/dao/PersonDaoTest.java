package org.studentsuccessplan.ssp.dao;

import static org.junit.Assert.assertEquals;
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
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonDaoTest.class);

	@Autowired
	private PersonDao dao;

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
	public void testGetAll() {
		List<Person> list = dao.getAll(ObjectStatus.ALL);
		assertNotNull(list);
		assertTrue("List should have included multiple entities.",
				list.size() > 1);
	}

	@Test
	public void testGetAllWithRowFilter() {
		List<Person> listAll = dao.getAll(ObjectStatus.ALL);
		List<Person> listFiltered = dao.getAll(ObjectStatus.ALL, 1, 2, null,
				null);

		assertNotNull(listAll);
		assertTrue("List should have included multiple entities.",
				listAll.size() > 2);

		assertNotNull(listFiltered);
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());

		assertTrue(
				"The filtered list should have included a different number of entities then the unfiltered list.",
				listFiltered.size() != listAll.size());
	}

	@Test
	public void testGet() {
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID,
				dao.get(Person.SYSTEM_ADMINISTRATOR_ID).getId());
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		Person obj = new Person();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setFirstName("System");
		obj.setLastName("User");
		obj.setPrimaryEmailAddress("user@sinclair.edu");
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());

		List<Person> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		UUID id = UUID.randomUUID();
		Person person = dao.get(id);

		assertNull(person);
	}

	@Test
	public void testFromUsername() {
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID, dao.fromUsername("system")
				.getId());
	}

	private void assertList(List<Person> objects) {
		for (Person object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}
}
