package org.jasig.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.SortDirection;
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

import com.google.common.collect.Lists;

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
		Collection<Person> list = dao.getAll(ObjectStatus.ALL).getRows();
		assertNotNull(list);
		assertTrue("List should have included multiple entities.",
				list.size() > 1);
	}

	@Test
	public void testGetAllWithRowFilter() {
		Collection<Person> listAll = dao.getAll(ObjectStatus.ALL).getRows();
		Collection<Person> listFiltered = dao.getAll(
				new SortingAndPaging(ObjectStatus.ALL, 1, 2, null, "lastName",
						SortDirection.ASC)).getRows();

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
	public void testGet() throws ObjectNotFoundException {
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID,
				dao.get(Person.SYSTEM_ADMINISTRATOR_ID).getId());
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		Person obj = new Person();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setFirstName("System");
		obj.setLastName("User");
		obj.setPrimaryEmailAddress("user@sinclair.edu");
		obj.setStrengths("strengths");
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertEquals("Strengths property did not match.", "strengths",
				obj.getStrengths());

		Collection<Person> all = dao.getAll(ObjectStatus.ACTIVE).getRows();
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		Person person = dao.get(id);

		assertNull(person);
	}

	@Test
	public void testFromUsername() {
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID, dao.fromUsername("system")
				.getId());
	}

	@Test
	public void getPeopleInList() {
		List<UUID> personIds = Lists.newArrayList();
		personIds.add(UUID.randomUUID());
		personIds.add(UUID.randomUUID());
		personIds.add(UUID.randomUUID());
		assertList(dao.getPeopleInList(personIds, new SortingAndPaging(
				ObjectStatus.ACTIVE)));
	}

	private void assertList(Collection<Person> objects) {
		for (Person object : objects) {
			assertNotNull(object.getId());
		}
	}
}
