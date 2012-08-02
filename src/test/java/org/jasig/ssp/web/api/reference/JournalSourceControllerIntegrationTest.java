package org.jasig.ssp.web.api.reference; // NOPMD by jon.adams

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.reference.JournalSourceTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * JournalSource controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class JournalSourceControllerIntegrationTest {

	@Autowired
	private transient JournalSourceController controller;

	private static final UUID JOURNALSOURCE_ID = UUID
			.fromString("b2d079a2-5056-a51a-8075-328971518ff0");

	private static final String JOURNALSOURCE_NAME = "Email";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the admin user for use by
	 * {@link #testControllerCreateAndDelete()} that checks that the Auditable
	 * auto-fill properties are correctly filled.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the {@link JournalSourceController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final JournalSourceTO obj = controller.get(JOURNALSOURCE_ID);

		assertNotNull(
				"Returned JournalSourceTO from the controller should not have been null.",
				obj);

		assertEquals("Returned JournalSource.Name did not match.",
				JOURNALSOURCE_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link JournalSourceController#get(UUID)} action returns
	 * the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final JournalSourceTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned JournalSourceTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link JournalSourceController#create(JournalSourceTO)} and
	 * {@link JournalSourceController#delete(UUID)} actions.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerCreateAndDelete() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final String testString1 = "testString1";
		final String testString2 = "testString1";

		// Check validation of 'no ID for create()'
		JournalSourceTO obj = new JournalSourceTO(UUID.randomUUID(),
				testString1,
				testString2);
		try {
			obj = controller.create(obj);
			fail("Calling create with an object with an ID should have thrown a validation excpetion.");
		} catch (final ValidationException exc) {
			/* expected */
		}

		// Now create a valid JournalSource
		obj = new JournalSourceTO(null, testString1, testString2);
		obj = controller.create(obj);

		assertNotNull(
				"Returned JournalSourceTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned JournalSourceTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned JournalSourceTO.Name from the controller did not match.",
				testString1, obj.getName());
		assertEquals(
				"Returned JournalSourceTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link JournalSourceController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<JournalSourceTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test that getLogger() returns the matching log class name for the current
	 * class under test.
	 */
	@Test
	public void testLogger() {
		final Logger logger = controller.getLogger();
		assertEquals("Log class name did not match.", controller.getClass()
				.getName(), logger.getName());
	}
}