package org.jasig.ssp.web.api.reference; // NOPMD by jon.adams

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.EarlyAlertReferralTO;
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
 * {@link EarlyAlertReferralController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class EarlyAlertReferralControllerIntegrationTest {

	@Autowired
	private transient EarlyAlertReferralController controller;

	@Autowired
	private transient SessionFactory sessionFactory;

	private static final UUID EARLYALERT_REFERRAL_ID = UUID
			.fromString("300d68ef-38c2-4b7d-ad46-7874aa5d34ac");

	private static final String EARLYALERT_REFERRAL_NAME = "Registration";

	private static final UUID EARLYALERT_REFERRAL_FIRST_ID = UUID
			.fromString("b2d112a9-5056-a51a-8010-b510525ea3a8");

	private static final String TESTSTRING1 = "testString1";

	private static final String TESTSTRING2 = "testString2";

	private static final String TEST_ACRONYM = "ABC";

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
	 * Test the {@link EarlyAlertReferralController#get(UUID)} action.
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

		final EarlyAlertReferralTO obj = controller.get(EARLYALERT_REFERRAL_ID);

		assertNotNull(
				"Returned EarlyAlertReferralTO from the controller should not have been null.",
				obj);

		assertEquals("Returned EarlyAlertReferral.Name did not match.",
				EARLYALERT_REFERRAL_NAME, obj.getName());
	}

	/**
	 * Test that the {@link EarlyAlertReferralController#get(UUID)} action
	 * returns the correct validation errors when an invalid ID is sent.
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

		final EarlyAlertReferralTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned EarlyAlertReferralTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link EarlyAlertReferralController#create(EarlyAlertReferralTO)} and
	 * {@link EarlyAlertReferralController#delete(UUID)} actions.
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
			final EarlyAlertReferralTO obj = controller
					.create(new EarlyAlertReferralTO(UUID.randomUUID(),
							TESTSTRING1, TESTSTRING2, (short) 1, TEST_ACRONYM)); // NOPMD
			assertNull(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					obj);
		} catch (final ValidationException exc) {
			assertNotNull("ValidatedException was expected to be thrown.", exc);
		}

		// Create a valid EarlyAlertReferral
		final EarlyAlertReferralTO valid = controller
				.create(new EarlyAlertReferralTO(
						null,
						TESTSTRING1,
						TESTSTRING2, (short) 1, TEST_ACRONYM)); // NOPMD

		assertNotNull(
				"Returned EarlyAlertReferralTO from the controller should not have been null.",
				valid);
		assertNotNull(
				"Returned EarlyAlertReferralTO.ID from the controller should not have been null.",
				valid.getId());
		assertEquals(
				"Returned EarlyAlertReferralTO.Name from the controller did not match.",
				TESTSTRING1, valid.getName());
		assertEquals(
				"Returned EarlyAlertReferralTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, valid.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(valid.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link EarlyAlertReferralController#create(EarlyAlertReferralTO)}.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test(expected = ValidationException.class)
	public void testControllerCreateInvalid() throws ObjectNotFoundException,
			ValidationException {
		// Create an invalid EarlyAlertReferral
		controller.create(new EarlyAlertReferralTO(null, TESTSTRING1,
				TESTSTRING2, (short) 1, null)); // NOPMD

		sessionFactory.getCurrentSession().flush();

		fail("Exception should have been thrown by this point.");
	}

	/**
	 * Test the
	 * {@link EarlyAlertReferralController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final PagedResponse<EarlyAlertReferralTO> response = controller.getAll(
				ObjectStatus.ACTIVE, 0, 50, null, null);

		assertNotNull("List should not have been null.", response.getRows());
		assertFalse("List action should have returned some objects.",
				response.getRows().isEmpty());
		assertEquals("Non-page size did not match expected.", 10,
				response.getResults());
	}

	/**
	 * Test the
	 * {@link EarlyAlertReferralController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllResults() {
		final PagedResponse<EarlyAlertReferralTO> results = controller
				.getAll(ObjectStatus.ACTIVE, 0, 4, null, null);

		final Collection<EarlyAlertReferralTO> list = results.getRows();

		assertNotNull("The list should not have been null.", list);
		assertFalse("There should have been some results.", list.isEmpty());
		assertTrue("Returned results (" + list.size()
				+ ") should have been fewer than the maximum available ("
				+ results.getResults() + ") in the database.",
				results.getResults() > list.size());

		final Iterator<EarlyAlertReferralTO> iter = list.iterator();

		EarlyAlertReferralTO earlyAlertReferral = iter.next();
		assertEquals("First ID did not match.",
				EARLYALERT_REFERRAL_FIRST_ID, earlyAlertReferral.getId());
		assertFalse("ModifiedBy id should not have been empty.",
				earlyAlertReferral
						.getModifiedBy().getId().equals(UUID.randomUUID()));

		earlyAlertReferral = iter.next();
		assertTrue("Acronym should have been longer than 0 characters.",
				earlyAlertReferral.getAcronym().length() > 0);
		assertFalse("CreatedBy id should not have been empty.",
				earlyAlertReferral
						.getCreatedBy().getId().equals(UUID.randomUUID()));
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