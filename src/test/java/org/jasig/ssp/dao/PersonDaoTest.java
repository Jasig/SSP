package org.jasig.ssp.dao; // NOPMD by jon.adams

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
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
	private transient PersonDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user for use by
	 * {@link #testSaveNew()} that checks that the Auditable auto-fill
	 * properties are correctly filled.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAll() {
		final Collection<Person> list = dao.getAll(ObjectStatus.ALL).getRows();
		assertNotNull(list);
		assertTrue("List should have included multiple entities.",
				list.size() > 1);
	}

	@Test
	public void testGetAllWithRowFilter() {
		final Collection<Person> listAll = dao.getAll(ObjectStatus.ALL)
				.getRows();
		final Collection<Person> listFiltered = dao.getAll(
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

		assertNotEquals(
				"The filtered list should have included a different number of entities then the unfiltered list.",
				listFiltered.size(), listAll.size());
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID,
				dao.get(Person.SYSTEM_ADMINISTRATOR_ID).getId());
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		// arrange
		final Person obj = new Person();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setFirstName("System");
		obj.setLastName("User");
		obj.setUsername("username");
		obj.setPrimaryEmailAddress("user@sinclair.edu");
		obj.setStrengths("strengths");

		// act
		final Person saved = dao.save(obj);

		// assert
		assertNotNull("", obj.getId());

		LOGGER.debug(obj.toString());

		final Person loaded = dao.get(saved.getId());
		assertNotNull(loaded);
		assertNotNull(loaded.getId());
		assertEquals("Strengths property did not match.", "strengths",
				loaded.getStrengths());

		final Collection<Person> all = dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(loaded);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Person person = dao.get(id);

		assertNull(person);
	}

	@Test
	public void testFromUsername() {
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID, dao.fromUsername("system")
				.getId());
	}

	@Test
	public void getPeopleInList() {
		final List<UUID> personIds = Lists.newArrayList();
		personIds.add(UUID.randomUUID());
		personIds.add(UUID.randomUUID());
		personIds.add(UUID.randomUUID());
		assertList(dao.getPeopleInList(personIds, new SortingAndPaging(
				ObjectStatus.ACTIVE)));
	}

	private void assertList(final Collection<Person> objects) {
		for (final Person object : objects) {
			assertNotNull(object.getId());
		}
	}
}
