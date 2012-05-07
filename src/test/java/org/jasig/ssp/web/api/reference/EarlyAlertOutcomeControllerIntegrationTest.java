package org.jasig.ssp.web.api.reference;

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
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.impl.EarlyAlertOutcomeServiceImpl;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertOutcomeTO;
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
 * {@link EarlyAlertOutcomeController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class EarlyAlertOutcomeControllerIntegrationTest {

	@Autowired
	private transient EarlyAlertOutcomeController controller;

	private static final UUID EARLYALERT_OUTCOME_ID = UUID
			.fromString("b2d10fca-5056-a51a-8088-8103ac163f13");

	private static final String EARLYALERT_OUTCOME_NAME = "Appointment Scheduled";

	private static final UUID EARLY_ALERT_OUTCOME_DELETED_ID = UUID
			.fromString("077A1D57-6C85-42F7-922B-7642BE9F70EB");

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
	 * Test the {@link EarlyAlertOutcomeController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final EarlyAlertOutcomeTO obj = controller.get(EARLYALERT_OUTCOME_ID);

		assertNotNull(
				"Returned EarlyAlertOutcomeTO from the controller should not have been null.",
				obj);

		assertEquals("Returned EarlyAlertOutcome.Name did not match.",
				EARLYALERT_OUTCOME_NAME, obj.getName());
	}

	/**
	 * Test that the {@link EarlyAlertOutcomeServiceImpl#get(UUID)} method does
	 * not return deleted items.
	 * 
	 * @throws Exception
	 *             If any unexpected exceptions were thrown by the controller.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetDeleted() throws Exception {
		final EarlyAlertOutcomeTO obj = controller
				.get(EARLY_ALERT_OUTCOME_DELETED_ID);

		assertNull(
				"Controller should not have returned a deleted item.",
				obj);
	}

	/**
	 * Test that the {@link EarlyAlertOutcomeController#get(UUID)} action
	 * returns the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final EarlyAlertOutcomeTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned EarlyAlertOutcomeTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link EarlyAlertOutcomeController#create(EarlyAlertOutcomeTO)}
	 * and {@link EarlyAlertOutcomeController#delete(UUID)} actions.
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
		try {
			final EarlyAlertOutcomeTO obj = controller
					.create(new EarlyAlertOutcomeTO(
							UUID
									.randomUUID(),
							testString1, testString2, (short) 1)); // NOPMD by
																	// jon.adams
			assertNull(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					obj);
		} catch (ValidationException exc) {
			assertNotNull("ValidatedException was expected to be thrown.", exc);
		}

		// Now create a valid EarlyAlertOutcome
		final EarlyAlertOutcomeTO valid = controller
				.create(new EarlyAlertOutcomeTO(
						null,
						testString1,
						testString2, (short) 1)); // NOPMD

		assertNotNull(
				"Returned EarlyAlertOutcomeTO from the controller should not have been null.",
				valid);
		assertNotNull(
				"Returned EarlyAlertOutcomeTO.ID from the controller should not have been null.",
				valid.getId());
		assertEquals(
				"Returned EarlyAlertOutcomeTO.Name from the controller did not match.",
				testString1, valid.getName());
		assertEquals(
				"Returned EarlyAlertOutcomeTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, valid.getCreatedById());

		assertTrue("Delete action did not return success.",
				controller.delete(valid.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link EarlyAlertOutcomeController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAll() throws Exception {
		final Collection<EarlyAlertOutcomeTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test the
	 * {@link EarlyAlertOutcomeController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action results.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGetAllResults() throws Exception {
		final PagingTO<EarlyAlertOutcomeTO, EarlyAlertOutcome> results = controller
				.getAll(ObjectStatus.ACTIVE, 0, 4, null, null);

		final Collection<EarlyAlertOutcomeTO> list = results.getRows();

		assertNotNull("The list should not have been null.", list);
		assertFalse("There should have been some results.", list.isEmpty());
		assertTrue("Returned results (" + list.size()
				+ ") should have been fewer than the maximum available ("
				+ results.getResults() + ") in the database.",
				results.getResults() > list.size());

		final Iterator<EarlyAlertOutcomeTO> iter = list.iterator();

		EarlyAlertOutcomeTO veteranStatus = iter.next();
		assertEquals("Name should have been " + EARLYALERT_OUTCOME_NAME,
				EARLYALERT_OUTCOME_NAME, veteranStatus.getName());
		assertFalse("ModifiedBy id should not have been empty.", veteranStatus
				.getModifiedById().equals(UUID.randomUUID()));

		veteranStatus = iter.next();
		assertTrue("Description should have been longer than 0 characters.",
				veteranStatus.getDescription().length() > 0);
		assertFalse("CreatedBy id should not have been empty.", veteranStatus
				.getCreatedById().equals(UUID.randomUUID()));
	}
}
