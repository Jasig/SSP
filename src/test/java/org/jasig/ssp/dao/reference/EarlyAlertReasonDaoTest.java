package org.jasig.ssp.dao.reference; // NOPMD by jon.adams on 5/24/12 2:05 PM

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertReason;
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
 * Tests for {@link EarlyAlertReasonDao}.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration
@Transactional
public class EarlyAlertReasonDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertReasonDaoTest.class);

	@Autowired
	private transient EarlyAlertReasonDao dao;

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
	 * Test {@link EarlyAlertReasonDao#save(EarlyAlertReason)}
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID savedId;

		EarlyAlertReason obj = new EarlyAlertReason();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj = dao.save(obj);

		savedId = obj.getId();

		LOGGER.debug(obj.toString());

		final EarlyAlertReason saved = dao.get(savedId);
		assertNotNull("Reloading did not return the correct saved data.", saved);
		assertNotNull("Reloaded data did not have a set Name property.",
				saved.getName());
		assertEquals("Reloaded data did not have matching data.",
				obj.getName(), saved.getName());

		final Collection<EarlyAlertReason> all = dao
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
	 * Test that {@link EarlyAlertReasonDao#get(UUID)} returns null if
	 * identifier is not found.
	 * 
	 * @throws ObjectNotFoundException
	 *             expected
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testNotFoundIdReturnsNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EarlyAlertReason earlyAlertReason = dao.get(id);

		assertNull(
				"Get() did not return null when a missing identifier was used.",
				earlyAlertReason);
	}

	/**
	 * Asserts that list contains objects with non-null identifiers.
	 * 
	 * @param objects
	 *            Collection of objects to assert have non-null identifiers.
	 */
	private void assertList(final Collection<EarlyAlertReason> objects) {
		assertFalse("List should not have been empty.", objects.isEmpty());

		for (final EarlyAlertReason object : objects) {
			assertNotNull("List item should not have a null id.",
					object.getId());
		}
	}

	/**
	 * Test UUID generation, {@link EarlyAlertReasonDao#save(EarlyAlertReason)}.
	 */
	@Test
	public void uuidGeneration() {
		final EarlyAlertReason obj = new EarlyAlertReason();
		obj.setName("A name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull("Transient instance was not assigned a new identifier.",
				obj.getId());
	}

	@Test
	public void testSortingInGetAll() {
		// default sort order ("sortOrder ASC")
		final PagingWrapper<EarlyAlertReason> data = dao
				.getAll(new SortingAndPaging(ObjectStatus.ALL));
		assertNotNull("Reason data should not be null.", data);
		assertFalse("Reason data should not be empty.", data.getRows()
				.isEmpty());

		final EarlyAlertReason obj = data.getRows().iterator().next();
		assertEquals("Default sorting did not return the correct order.",
				UUID.fromString("b2d112a9-5056-a51a-8010-b510525ea3a8"),
				obj.getId());

		// custom sort order ("sortOrder DESC")
		final List<Pair<String, SortDirection>> sortFields = Lists
				.newArrayList();
		sortFields.add(new Pair<String, SortDirection>("sortOrder",
				SortDirection.DESC));
		final PagingWrapper<EarlyAlertReason> data2 = dao
				.getAll(new SortingAndPaging(ObjectStatus.ALL, null, null,
						sortFields, null, null));
		assertNotNull("Reason data should not be null.", data2);
		assertFalse("Reason data should not be empty.", data2.getRows()
				.isEmpty());

		final EarlyAlertReason obj2 = data2.getRows().iterator().next();
		assertEquals(
				"Descending sortOrder sorting did not return the correct order.",
				UUID.fromString("300d68ef-38c2-4b7d-ad46-7874aa5d34ac"),
				obj2.getId());
	}

	@Test
	public void testHashCode() throws ObjectNotFoundException {
		final EarlyAlertReason obj = new EarlyAlertReason(UUID.randomUUID(),
				"Name", "description", (short) 34); // NOPMD by jon.adams

		assertNotEquals("HashCodes should not have matched.", obj.hashCode(),
				new EarlyAlertReason().hashCode());
		assertEquals("HashCodes should have matched.",
				obj.hashCode(), obj.hashCode());
		assertEquals("HashCodes should have matched.",
				new EarlyAlertReason().hashCode(),
				new EarlyAlertReason().hashCode());
	}
}