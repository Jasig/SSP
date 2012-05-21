package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.PagingWrapper;
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

import com.google.common.collect.Maps;

/**
 * Tests for {@link EarlyAlertOutcomeDao}.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration
@Transactional
public class EarlyAlertOutcomeDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertOutcomeDaoTest.class);

	@Autowired
	private transient EarlyAlertOutcomeDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service for the tests
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test {@link EarlyAlertOutcomeDao#save(EarlyAlertOutcome)}
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID savedId;

		EarlyAlertOutcome obj = new EarlyAlertOutcome();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj = dao.save(obj);

		savedId = obj.getId();

		LOGGER.debug(obj.toString());

		final EarlyAlertOutcome saved = dao.get(savedId);
		assertNotNull("Reloading did not return the correct saved data.", saved);
		assertNotNull("Reloaded data did not have a set Name property.",
				saved.getName());
		assertEquals("Reloaded data did not have matching data.",
				obj.getName(), saved.getName());

		final Collection<EarlyAlertOutcome> all = dao
				.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull("GetAll() should not have returned a null collection.",
				all);
		assertFalse(
				"GetAll() should not have returned an empty list. (This test assumes some sample reference data exists in the testing database.)",
				all.isEmpty());
		assertList(all);

		dao.delete(saved);
	}

	/**
	 * Test that {@link EarlyAlertOutcomeDao#get(UUID)} returns null if
	 * identifier is not found.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should be thrown for this test
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testNotFoundIdReturnsNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EarlyAlertOutcome earlyAlertOutcome = dao.get(id);

		assertNull(
				"Get() did not return null when a missing identifier was used.",
				earlyAlertOutcome);
	}

	/**
	 * Asserts that list contains objects with non-null identifiers.
	 * 
	 * @param objects
	 *            Collection of objects to assert have non-null identifiers.
	 */
	private void assertList(final Collection<EarlyAlertOutcome> objects) {
		assertFalse("List should not have been empty.", objects.isEmpty());

		for (final EarlyAlertOutcome object : objects) {
			assertNotNull("List item should not have a null id.",
					object.getId());
		}
	}

	/**
	 * Test UUID generation,
	 * {@link EarlyAlertOutcomeDao#save(EarlyAlertOutcome)}.
	 */
	@Test
	public void uuidGeneration() {
		final EarlyAlertOutcome obj = new EarlyAlertOutcome();
		obj.setName("A name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull("Transient instance was not assigned a new identifier.",
				obj.getId());
	}

	@Test
	public void testSortingInGetAll() {
		// default sort order ("sortOrder ASC")
		final PagingWrapper<EarlyAlertOutcome> data = dao
				.getAll(new SortingAndPaging(ObjectStatus.ALL));
		assertNotNull("Outcome data should not be null.", data);
		assertFalse("Outcome data should not be empty.", data.getRows()
				.isEmpty());

		final EarlyAlertOutcome obj = data.getRows().iterator().next();
		assertEquals("Default sorting did not return the correct order.",
				UUID.fromString("12a58804-45dc-40f2-b2f5-d7e4403acee1"),
				obj.getId());

		// custom sort order ("sortOrder DESC")
		final Map<String, SortDirection> sortFields = Maps.newHashMap();
		sortFields.put("sortOrder", SortDirection.DESC);
		final PagingWrapper<EarlyAlertOutcome> data2 = dao
				.getAll(new SortingAndPaging(ObjectStatus.ALL, null, null,
						sortFields, null, null));
		assertNotNull("Outcome data should not be null.", data2);
		assertFalse("Outcome data should not be empty.", data2.getRows()
				.isEmpty());

		final EarlyAlertOutcome obj2 = data2.getRows().iterator().next();
		assertEquals(
				"Descending sortOrder sorting did not return the correct order.",
				UUID.fromString("077a1d57-6c85-42f7-922b-7642be9f70eb"),
				obj2.getId());
	}
}
