package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertSuggestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.collections.Pair;
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

import com.google.common.collect.Lists;

/**
 * Tests for {@link EarlyAlertSuggestionDao}.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration
@Transactional
public class EarlyAlertSuggestionDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertSuggestionDaoTest.class);

	@Autowired
	private transient EarlyAlertSuggestionDao dao;

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
	 * Test {@link EarlyAlertSuggestionDao#save(EarlyAlertSuggestion)}
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID savedId;

		EarlyAlertSuggestion obj = new EarlyAlertSuggestion();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj = dao.save(obj);

		savedId = obj.getId();

		LOGGER.debug(obj.toString());

		final EarlyAlertSuggestion saved = dao.get(savedId);
		assertNotNull("Reloading did not return the correct saved data.", saved);
		assertNotNull("Reloaded data did not have a set Name property.",
				saved.getName());
		assertEquals("Reloaded data did not have matching data.",
				obj.getName(), saved.getName());

		final Collection<EarlyAlertSuggestion> all = dao.getAll(
				ObjectStatus.ACTIVE)
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
	 * Test that {@link EarlyAlertSuggestionDao#get(UUID)} returns null if
	 * identifier is not found.
	 * 
	 * @throws ObjectNotFoundException
	 *             expected
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testNotFoundIdReturnsNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EarlyAlertSuggestion earlyAlertSuggestion = dao.get(id);

		assertNull(
				"Get() did not return null when a missing identifier was used.",
				earlyAlertSuggestion);
	}

	/**
	 * Asserts that list contains objects with non-null identifiers.
	 * 
	 * @param objects
	 *            Collection of objects to assert have non-null identifiers.
	 */
	private void assertList(final Collection<EarlyAlertSuggestion> objects) {
		assertFalse("List should not have been empty.", objects.isEmpty());

		for (final EarlyAlertSuggestion object : objects) {
			assertNotNull("List item should not have a null id.",
					object.getId());
		}
	}

	/**
	 * Test UUID generation,
	 * {@link EarlyAlertSuggestionDao#save(EarlyAlertSuggestion)}.
	 */
	@Test
	public void uuidGeneration() {
		final EarlyAlertSuggestion obj = new EarlyAlertSuggestion();
		obj.setName("A name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull("Transient instance was not assigned a new identifier.",
				obj.getId());
	}

	@Test
	public void testSortingInGetAll() {
		// default sort order ("sortOrder ASC")
		final PagingWrapper<EarlyAlertSuggestion> data = dao
				.getAll(new SortingAndPaging(ObjectStatus.ALL));
		assertNotNull("Suggestion data should not be null.", data);
		assertFalse("Suggestion data should not be empty.", data.getRows()
				.isEmpty());

		final EarlyAlertSuggestion obj = data.getRows().iterator().next();
		assertEquals("Default sorting did not return the correct order.",
				UUID.fromString("b2d11141-5056-a51a-80c1-c1250ba820f8"),
				obj.getId());

		// custom sort order ("sortOrder DESC")
		final List<Pair<String, SortDirection>> sortFields = Lists
				.newArrayList();
		sortFields.add(new Pair<String, SortDirection>("sortOrder",
				SortDirection.DESC));
		final PagingWrapper<EarlyAlertSuggestion> data2 = dao
				.getAll(new SortingAndPaging(ObjectStatus.ALL, null, null,
						sortFields, null, null));
		assertNotNull("Suggestion data should not be null.", data2);
		assertFalse("Suggestion data should not be empty.", data2.getRows()
				.isEmpty());

		final EarlyAlertSuggestion obj2 = data2.getRows().iterator().next();
		assertEquals(
				"Descending sortOrder sorting did not return the correct order.",
				UUID.fromString("881df3dd-1aa6-4cb8-8817-e95daf49227a"),
				obj2.getId());
	}
}
