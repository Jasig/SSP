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
import org.jasig.ssp.model.reference.EarlyAlertReferral;
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
 * Tests for {@link EarlyAlertReferralDao}.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration
@Transactional
public class EarlyAlertReferralDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertReferralDaoTest.class);

	@Autowired
	private transient EarlyAlertReferralDao dao;

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
	 * Test {@link EarlyAlertReferralDao#save(EarlyAlertReferral)}
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID savedId;

		EarlyAlertReferral obj = new EarlyAlertReferral();
		obj.setName("new name");
		obj.setAcronym("ACCROOO");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj = dao.save(obj);

		savedId = obj.getId();

		LOGGER.debug(obj.toString());

		final EarlyAlertReferral saved = dao.get(savedId);
		assertNotNull("Reloading did not return the correct saved data.", saved);
		assertNotNull("Reloaded data did not have a set Name property.",
				saved.getName());
		assertEquals("Reloaded data did not have matching data.",
				obj.getName(), saved.getName());

		final Collection<EarlyAlertReferral> all = dao.getAll(
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
	 * Test that {@link EarlyAlertReferralDao#get(UUID)} returns null if
	 * identifier is not found.
	 * 
	 * @throws ObjectNotFoundException
	 *             expected
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testNotFoundIdReturnsNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EarlyAlertReferral earlyAlertReferral = dao.get(id);

		assertNull(
				"Get() did not return null when a missing identifier was used.",
				earlyAlertReferral);
	}

	/**
	 * Asserts that list contains objects with non-null identifiers.
	 * 
	 * @param objects
	 *            Collection of objects to assert have non-null identifiers.
	 */
	private void assertList(final Collection<EarlyAlertReferral> objects) {
		assertFalse("List should not have been empty.", objects.isEmpty());

		for (final EarlyAlertReferral object : objects) {
			assertNotNull("List item should not have a null id.",
					object.getId());
		}
	}

	/**
	 * Test UUID generation,
	 * {@link EarlyAlertReferralDao#save(EarlyAlertReferral)}.
	 */
	@Test
	public void uuidGeneration() {
		final EarlyAlertReferral obj = new EarlyAlertReferral();
		obj.setName("A name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setAcronym("ACC");
		dao.save(obj);

		assertNotNull("Transient instance was not assigned a new identifier.",
				obj.getId());
	}

	@Test
	public void testSortingInGetAll() {
		// default sort order ("sortOrder ASC")
		final PagingWrapper<EarlyAlertReferral> data = dao
				.getAll(new SortingAndPaging(ObjectStatus.ALL));
		assertNotNull("Referral data should not be null.", data);
		assertFalse("Referral data should not be empty.", data.getRows()
				.isEmpty());

		final EarlyAlertReferral obj = data.getRows().iterator().next();
		assertEquals("Default sorting did not return the correct order.",
				UUID.fromString("b2d112a9-5056-a51a-8010-b510525ea3a8"),
				obj.getId());

		// custom sort order ("sortOrder DESC")
		final Map<String, SortDirection> sortFields = Maps.newHashMap();
		sortFields.put("sortOrder", SortDirection.DESC);
		final PagingWrapper<EarlyAlertReferral> data2 = dao
				.getAll(new SortingAndPaging(ObjectStatus.ALL, null, null,
						sortFields, null, null));
		assertNotNull("Referral data should not be null.", data2);
		assertFalse("Referral data should not be empty.", data2.getRows()
				.isEmpty());

		final EarlyAlertReferral obj2 = data2.getRows().iterator().next();
		assertEquals(
				"Descending sortOrder sorting did not return the correct order.",
				UUID.fromString("300d68ef-38c2-4b7d-ad46-7874aa5d34ac"),
				obj2.getId());
	}
}
