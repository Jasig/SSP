package org.jasig.ssp.web.api.reference; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.reference.ProgramStatusTO;
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
 * {@link ProgramStatusController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ProgramStatusControllerIntegrationTest {

	@Autowired
	private transient ProgramStatusController controller;

	private static final UUID PROGRAMSTATUS_ID = UUID
			.fromString("B2D12527-5056-A51A-8054-113116BAAB88");

	private static final String PROGRAMSTATUS_NAME = "Active";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString2";

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
	 * Test the {@link ProgramStatusController#get(UUID)} action.
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

		final ProgramStatusTO obj = controller.get(PROGRAMSTATUS_ID);

		assertNotNull(
				"Returned ProgramStatusTO from the controller should not have been null.",
				obj);

		assertEquals("Returned ProgramStatus.Name did not match.",
				PROGRAMSTATUS_NAME, obj.getName());
	}

	/**
	 * Test that the {@link ProgramStatusController#get(UUID)} action returns
	 * the correct validation errors when an invalid ID is sent.
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

		final ProgramStatusTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned ProgramStatusTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link ProgramStatusController#create(ProgramStatusTO)} and
	 * {@link ProgramStatusController#delete(UUID)} actions.
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
		try {
			final ProgramStatusTO obj = controller.create(new ProgramStatusTO(
					UUID
							.randomUUID(),
					TEST_STRING1, TEST_STRING2, true));
			assertNull(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					obj);
		} catch (final ValidationException exc) {
			assertNotNull("ValidatedException was expected to be thrown.", exc);
		}

		// Now create a valid ProgramStatus
		final ProgramStatusTO valid = controller.create(new ProgramStatusTO(
				null, TEST_STRING1, TEST_STRING2, false));

		assertNotNull(
				"Returned ProgramStatusTO from the controller should not have been null.",
				valid);
		assertNotNull(
				"Returned ProgramStatusTO.ID from the controller should not have been null.",
				valid.getId());
		assertEquals(
				"Returned ProgramStatusTO.Name from the controller did not match.",
				TEST_STRING1, valid.getName());
		assertEquals(
				"Returned ProgramStatusTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, valid.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(valid.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link ProgramStatusController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<ProgramStatusTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test the
	 * {@link ProgramStatusController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllResults() {
		final PagingTO<ProgramStatusTO, ProgramStatus> results = controller
				.getAll(ObjectStatus.ACTIVE, 0, 4, null, null);

		final Collection<ProgramStatusTO> list = results.getRows();

		assertNotNull("The list should not have been null.", list);
		assertFalse("There should have been some results.", list.isEmpty());
		assertTrue("Returned results (" + list.size()
				+ ") should have been fewer than the maximum available ("
				+ results.getResults() + ") in the database.",
				results.getResults() > list.size());

		final Iterator<ProgramStatusTO> iter = list.iterator();

		ProgramStatusTO programStatus = iter.next();
		assertEquals("Name should have been " + PROGRAMSTATUS_NAME,
				PROGRAMSTATUS_NAME, programStatus.getName());
		assertFalse("ModifiedBy id should not have been empty.", programStatus
				.getModifiedBy().getId().equals(UUID.randomUUID()));

		programStatus = iter.next();
		assertTrue("Description should have been longer than 0 characters.",
				programStatus.getDescription().length() > 0);
		assertFalse("CreatedBy id should not have been empty.", programStatus
				.getCreatedBy().getId().equals(UUID.randomUUID()));
	}

	/**
	 * Test that the getAll action rejects a filter of
	 * {@link ObjectStatus#DELETED}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testControllerGetAllRejectsDeletedFilter() {
		controller.getAll(ObjectStatus.DELETED, null, null, null, null);
	}
}