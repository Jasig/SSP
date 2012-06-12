package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
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

/**
 * Tests for {@link ConfigDao}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration
@Transactional
public class ConfigDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigDaoTest.class);

	@Autowired
	private transient ConfigDao dao;

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
	 * Test {@link ConfigDao#save(Config)}
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID savedId;

		Config obj = new Config();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setDefaultValue("123");
		obj = dao.save(obj);

		savedId = obj.getId();

		LOGGER.debug(obj.toString());

		final Config saved = dao.get(savedId);
		assertNotNull("Reloading did not return the correct saved data.", saved);
		assertNotNull("Reloaded data did not have a set Name property.",
				saved.getName());
		assertEquals("Reloaded data did not have matching data.",
				obj.getName(), saved.getName());

		final Collection<Config> all = dao.getAll(ObjectStatus.ACTIVE)
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
	 * Test that {@link ConfigDao#get(UUID)} returns null if identifier is not
	 * found.
	 * 
	 * @throws ObjectNotFoundException
	 *             expected
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testNotFoundIdReturnsNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Config config = dao.get(id);

		assertNull(
				"Get() did not return null when a missing identifier was used.",
				config);
	}

	/**
	 * Asserts that list contains objects with non-null identifiers.
	 * 
	 * @param objects
	 *            Collection of objects to assert have non-null identifiers.
	 */
	private void assertList(final Collection<Config> objects) {
		assertFalse("List should not have been empty.", objects.isEmpty());

		for (final Config object : objects) {
			assertNotNull("List item should not have a null id.",
					object.getId());
		}
	}

	/**
	 * Test UUID generation, {@link ConfigDao#save(Config)}.
	 */
	@Test
	public void uuidGeneration() {
		final Config obj = new Config();
		obj.setName("A name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setDefaultValue("");
		dao.save(obj);

		assertNotNull("Transient instance was not assigned a new identifier.",
				obj.getId());
	}
}