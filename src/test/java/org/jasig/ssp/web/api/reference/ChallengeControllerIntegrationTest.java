package org.jasig.ssp.web.api.reference; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
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
 * Challenge controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ChallengeControllerIntegrationTest {

	private static final UUID CHALLENGE_ID = UUID
			.fromString("f5bb0a62-1756-4ea2-857d-5821ee44a1d0");

	private static final UUID CHALLENGE_DELETED_ID = UUID
			.fromString("01bb0a62-1756-4ea2-857d-5821ee54a1b9");

	private static final UUID CHALLENGE_ID_WTH_REFERRAL = UUID
			.fromString("43719c57-ec92-4e4a-9fb6-25208936fd18");

	private static final UUID CHALLENGE_REFERRAL_ID = UUID
			.fromString("19fbec43-8c0b-478b-9d5f-00ec6ec57511");

	private static final String CHALLENGE_NAME = "Test Challenge";

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString1";

	@Autowired
	private transient ChallengeController controller;

	@Autowired
	private transient SessionFactory sessionFactory;

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
	 * Test the {@link ChallengeController#get(UUID)} action.
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

		final ChallengeTO obj = controller.get(CHALLENGE_ID);

		assertNotNull(
				"Returned ChallengeTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Challenge.Name did not match.", CHALLENGE_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link ChallengeController#get(UUID)} action returns the
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

		final ChallengeTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned ChallengeTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link ChallengeController#create(ChallengeTO)} and
	 * {@link ChallengeController#delete(UUID)} actions. * @throws
	 * ValidationException If validation error occurred.
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
		final ChallengeTO invalid = new ChallengeTO(UUID.randomUUID(),
				TEST_STRING1,
				TEST_STRING2);
		try {
			controller.create(invalid);
			fail("Calling create with an object with an ID should have thrown a validation excpetion."); // NOPMD
		} catch (final ValidationException exc) { // NOPMD
			/* expected */
		}

		// Now create a valid Challenge
		final ChallengeTO challenge = new ChallengeTO(null, TEST_STRING1,
				TEST_STRING2);
		final ChallengeTO saved = controller.create(challenge);

		assertNotNull(
				"Returned ChallengeTO from the controller should not have been null.",
				saved);
		assertNotNull(
				"Returned ChallengeTO.ID from the controller should not have been null.",
				saved.getId());
		assertEquals(
				"Returned ChallengeTO.Name from the controller did not match.",
				TEST_STRING1, saved.getName());
		assertEquals(
				"Returned ChallengeTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, saved.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(saved.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link ChallengeController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<ChallengeTO> list = controller.getAll(
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test the
	 * {@link ChallengeController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllResults() {
		final Collection<ChallengeTO> list = controller.getAll(
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();

		final Iterator<ChallengeTO> iter = list.iterator();

		ChallengeTO challenge = iter.next();
		assertTrue("Name should have been longer than 0 characters.", challenge
				.getName().length() > 0);
		assertFalse("ModifiedBy id should not have been empty.", challenge
				.getModifiedBy().getId().equals(UUID.randomUUID()));
		assertTrue("ShowInStudentIntake should have been true.",
				challenge.isShowInStudentIntake());

		challenge = iter.next();
		assertTrue("Description should have been longer than 0 characters.",
				challenge.getDescription().length() > 0);
		assertFalse("CreatedBy id should not have been empty.", challenge
				.getCreatedBy().getId().equals(UUID.randomUUID()));
		assertTrue("ShowInSelfHelpSearch should have been true.",
				challenge.isShowInSelfHelpSearch());
	}

	/**
	 * Test the {@link ChallengeController#delete(UUID)} action for already
	 * deleted Challenges.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerDeleteAlreadyDeleted()
			throws ObjectNotFoundException, ValidationException {
		assertTrue("Delete action did not return success.",
				controller.delete(CHALLENGE_DELETED_ID).isSuccess());

		try {
			controller.get(CHALLENGE_DELETED_ID);
			fail("Get should have thrown exception for a deleted item."); // NOPMD
		} catch (final ObjectNotFoundException exc) { // NOPMD
			/*
			 * Expected. Can use @Test(expected) because the delete() call above
			 * should _not_ throw that exception.
			 */
		}
	}

	@Test(expected = ValidationException.class)
	public void testContollerSaveWithoutId() throws ValidationException,
			ObjectNotFoundException {
		final ChallengeTO challenge = controller.get(CHALLENGE_ID);
		challenge.setDescription("New description");
		challenge.setId(null); // set invalid ID
		controller.save(null, challenge);
		fail("Invalid ID should have thrown an exception.");
	}

	@Test
	public void testContollerSave() throws ValidationException,
			ObjectNotFoundException {
		final ChallengeTO challenge = controller.get(CHALLENGE_ID);
		challenge.setDescription("New description");
		final ChallengeTO saved = controller.save(CHALLENGE_ID, challenge);
		assertEquals("Description does not match.", "New description",
				saved.getDescription());
	}

	@Test
	public void testLogger() {
		final Logger logger = controller.getLogger();
		assertNotNull("Logger should not have been null.", logger);
	}

	@Test
	public void testGetChallengeReferralsForChallenge()
			throws ObjectNotFoundException {
		final PagedResponse<ChallengeReferralTO> referrals = controller
				.getChallengeReferralsForChallenge(CHALLENGE_ID_WTH_REFERRAL,
						null, null, null, null, null);
		assertTrue("Referrals list should not have been empty.",
				referrals.getResults() > 0);
	}

	@Test
	public void testAddChallengeReferralsFromChallenge()
			throws ObjectNotFoundException {
		final Session session = sessionFactory.getCurrentSession();

		final ServiceResponse response = controller
				.addChallengeReferralToChallenge(CHALLENGE_ID,
						CHALLENGE_REFERRAL_ID);
		assertTrue("Add should have returned success.", response.isSuccess());

		session.flush();
		session.clear();

		boolean found = false;
		final PagedResponse<ChallengeReferralTO> addedList = controller
				.getChallengeReferralsForChallenge(CHALLENGE_ID, null, null,
						null, null, null);
		assertTrue("Should have found results in list.",
				addedList.getResults() > 0);
		for (final ChallengeReferralTO to : addedList.getRows()) {
			if (CHALLENGE_REFERRAL_ID.equals(to.getId())) {
				found = true;
			}
		}

		assertTrue("Could not find added challenge_challenge_referral.", found);
	}

	@Test
	public void testAddAndDeleteChallengeReferralsFromChallenge()
			throws ObjectNotFoundException {
		// arrange, act
		final ServiceResponse deletion = controller
				.removeChallengeReferralFromChallenge(CHALLENGE_ID,
						CHALLENGE_REFERRAL_ID);

		// assert
		assertTrue("Delete should have returned success.", deletion.isSuccess());
	}
}