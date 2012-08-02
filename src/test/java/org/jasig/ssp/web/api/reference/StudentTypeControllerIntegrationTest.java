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
import org.jasig.ssp.transferobject.reference.StudentTypeTO;
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
 * StudentType controller tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class StudentTypeControllerIntegrationTest {

	private static final String TESTSTRING1 = "testString1";

	private static final String TESTSTRING2 = "testString1";

	private static final UUID STUDENT_TYPE_ID = UUID
			.fromString("b2d058eb-5056-a51a-80a7-8a20c30d1e91");

	private static final String STUDENT_TYPE_NAME = "ILP";

	@Autowired
	private transient StudentTypeController controller;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user for use by
	 * {@link #testControllerCreateAndDelete()} that checks that the Auditable
	 * auto-fill properties are correctly filled.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the {@link StudentTypeController#get(UUID)} action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final StudentTypeTO obj = controller
				.byId(STUDENT_TYPE_ID);

		assertNotNull(
				"Returned StudentTypeTO from the controller should not have been null.",
				obj);

		assertEquals("Returned StudentType.Name did not match.",
				STUDENT_TYPE_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link StudentTypeController#get(UUID)} action returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final StudentTypeTO obj = controller.byId(UUID
				.randomUUID());

		assertNull(
				"Returned StudentTypeTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link StudentTypeController#create(StudentTypeTO)} and
	 * {@link StudentTypeController#delete(UUID)} actions.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCreateAndDelete() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		// Check validation of 'no ID for create()'
		StudentTypeTO obj = new StudentTypeTO( // NOPMD
				UUID.randomUUID(), TESTSTRING1,
				TESTSTRING2);
		try {
			obj = controller.create(obj);
			fail("Calling create with an object with an ID should have thrown a validation excpetion."); // NOPMD
		} catch (final ValidationException exc) { // NOPMD
			/* expected */
		}

		// Now create a valid StudentType
		obj = new StudentTypeTO(null, TESTSTRING1, TESTSTRING2); // NOPMD
		obj = controller.create(obj);

		assertNotNull(
				"Returned StudentTypeTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned StudentTypeTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned StudentTypeTO.Name from the controller did not match.",
				TESTSTRING1, obj.getName());
		assertEquals(
				"Returned StudentTypeTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link StudentTypeController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<StudentTypeTO> list = controller.getAll(
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