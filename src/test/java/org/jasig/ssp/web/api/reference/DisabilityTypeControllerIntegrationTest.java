package org.jasig.ssp.web.api.reference; // NOPMD

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
import org.jasig.ssp.transferobject.reference.DisabilityTypeTO;
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
 * DisabilityType controller tests
 * 
 * @author TemplateAuthorName
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class DisabilityTypeControllerIntegrationTest {

	@Autowired
	private transient DisabilityTypeController controller;

	private static final UUID DISABILITY_TYPE_ID = UUID
			.fromString("914d2329-28d0-4c79-b81e-c421af56476f");

	private static final String DISABILITY_TYPE_NAME = "Test Disability Type";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString1";

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
	 * Test the {@link DisabilityTypeController#get(UUID)} action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final DisabilityTypeTO obj = controller.get(DISABILITY_TYPE_ID);

		assertNotNull(
				"Returned DisabilityTypeTO from the controller should not have been null.",
				obj);

		assertEquals("Returned DisabilityType.Name did not match.",
				DISABILITY_TYPE_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link DisabilityTypeController#get(UUID)} action returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final DisabilityTypeTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned DisabilityTypeTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link DisabilityTypeController#create(DisabilityTypeTO)} and
	 * {@link DisabilityTypeController#delete(UUID)} actions.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test
	public void testControllerCreateAndDelete() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		// Check validation of 'no ID for create()'
		try {
			controller.create(new DisabilityTypeTO(UUID
					.randomUUID(), TEST_STRING1,
					TEST_STRING2));
			fail("Calling create with an object with an ID should have thrown a validation excpetion."); // NOPMD
		} catch (final ValidationException exc) { // NOPMD
			/* expected */
		}

		// Now create a valid DisabilityType
		final DisabilityTypeTO saved = controller.create(new DisabilityTypeTO(null,
				TEST_STRING1, TEST_STRING2));

		assertNotNull(
				"Returned DisabilityTypeTO from the controller should not have been null.",
				saved);
		assertNotNull(
				"Returned DisabilityTypeTO.ID from the controller should not have been null.",
				saved.getId());
		assertEquals(
				"Returned DisabilityTypeTO.Name from the controller did not match.",
				TEST_STRING1, saved.getName());
		assertEquals(
				"Returned DisabilityTypeTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, saved.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(saved.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link DisabilityTypeController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<DisabilityTypeTO> list = controller.getAll(
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
