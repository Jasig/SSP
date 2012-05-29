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
import org.jasig.ssp.transferobject.reference.ProgramStatusChangeReasonTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProgramStatusChangeReason controller tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ProgramStatusChangeReasonControllerIntegrationTest {

	private static final String TESTSTRING1 = "testString1";

	private static final String TESTSTRING2 = "testString1";

	private static final UUID PROGRAM_STATUS_CHANGE_REASON_ID = UUID
			.fromString("b2d1271b-5056-a51a-8018-f5ed8477b481");

	private static final String PROGRAM_STATUS_CHANGE_REASON_NAME = "Financially unable to purchase books";

	@Autowired
	private transient ProgramStatusChangeReasonController controller;

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
	 * Test the {@link ProgramStatusChangeReasonController#get(UUID)} action.
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

		final ProgramStatusChangeReasonTO obj = controller
				.get(PROGRAM_STATUS_CHANGE_REASON_ID);

		assertNotNull(
				"Returned ProgramStatusChangeReasonTO from the controller should not have been null.",
				obj);

		assertEquals("Returned ProgramStatusChangeReason.Name did not match.",
				PROGRAM_STATUS_CHANGE_REASON_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link ProgramStatusChangeReasonController#get(UUID)}
	 * action returns the correct validation errors when an invalid ID is sent.
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

		final ProgramStatusChangeReasonTO obj = controller.get(UUID
				.randomUUID());

		assertNull(
				"Returned ProgramStatusChangeReasonTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link ProgramStatusChangeReasonController#create(ProgramStatusChangeReasonTO)}
	 * and {@link ProgramStatusChangeReasonController#delete(UUID)} actions.
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
		ProgramStatusChangeReasonTO obj = new ProgramStatusChangeReasonTO( // NOPMD
				UUID.randomUUID(), TESTSTRING1,
				TESTSTRING2);
		try {
			obj = controller.create(obj);
			fail("Calling create with an object with an ID should have thrown a validation excpetion."); // NOPMD
		} catch (final ValidationException exc) { // NOPMD
			/* expected */
		}

		// Now create a valid ProgramStatusChangeReason
		obj = new ProgramStatusChangeReasonTO(null, TESTSTRING1, TESTSTRING2); // NOPMD
		obj = controller.create(obj);

		assertNotNull(
				"Returned ProgramStatusChangeReasonTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned ProgramStatusChangeReasonTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned ProgramStatusChangeReasonTO.Name from the controller did not match.",
				TESTSTRING1, obj.getName());
		assertEquals(
				"Returned ProgramStatusChangeReasonTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link ProgramStatusChangeReasonController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<ProgramStatusChangeReasonTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}
}