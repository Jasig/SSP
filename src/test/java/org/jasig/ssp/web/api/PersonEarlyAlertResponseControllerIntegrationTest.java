package org.jasig.ssp.web.api; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
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

import com.google.common.collect.Sets;

/**
 * {@link PersonEarlyAlertResponseController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class PersonEarlyAlertResponseControllerIntegrationTest {

	@Autowired
	private transient PersonEarlyAlertResponseController controller;

	@Autowired
	private transient PersonEarlyAlertController earlyAlertController;

	@Autowired
	protected transient SessionFactory sessionFactory;

	private static final UUID EARLY_ALERT_RESPONSE_ID = UUID
			.fromString("99d80dc0-4ea0-4a73-a98e-24a47421ac63");

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final UUID EARLY_ALERT_ID = UUID
			.fromString("74891747-36aa-409c-8b1f-76d3eaf9028e");

	private static final UUID EARLY_ALERT_OUTCOME_ID = UUID
			.fromString("9a98ff78-92af-4681-8111-adb3300cbe1c");

	private static final UUID EARLY_ALERT_REFERRAL_ID = UUID
			.fromString("1f5729af-0337-4e58-a001-8a9f80dbf8aa");

	private static final UUID EARLY_ALERT_REFERRAL_ID2 = UUID
			.fromString("b2d11335-5056-a51a-80ea-074f8fef94ea");

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				"ROLE_PERSON_EARLY_ALERT_WRITE",
				"ROLE_PERSON_EARLY_ALERT_RESPONSE_READ",
				"ROLE_PERSON_EARLY_ALERT_RESPONSE_WRITE",
				"ROLE_PERSON_EARLY_ALERT_RESPONSE_DELETE");
	}

	/**
	 * Test that the {@link PersonEarlyAlertResponseController#get(UUID, UUID)}
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

		final EarlyAlertResponseTO obj = controller.get(UUID.randomUUID(),
				PERSON_ID);

		assertNull(
				"Returned EarlyAlertResponseTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link PersonEarlyAlertResponseController#getAll(UUID,UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerAll() throws ObjectNotFoundException,
			ValidationException {
		final PagedResponse<EarlyAlertResponseTO> result = controller.getAll(
				PERSON_ID, EARLY_ALERT_ID, ObjectStatus.ACTIVE, null, null,
				null, null);

		assertNotNull("List should not have been null.", result);
		assertFalse("List should not have been empty.", result.getRows()
				.isEmpty());
		assertEquals("List should have included one item.", 1,
				result.getResults());
		assertEquals("List should have included one item.", 1, result.getRows()
				.size());
	}

	@Test(expected = ValidationException.class)
	public void testControllerCreateWithInvalidDataGetId()
			throws ValidationException, ObjectNotFoundException {
		final EarlyAlertResponseTO obj = new EarlyAlertResponseTO();
		obj.setId(UUID.randomUUID());
		controller.create(UUID.randomUUID(), UUID.randomUUID(), obj);
		fail("Create with invalid Person UUID should have thrown exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testControllerCreateWithNullPerson()
			throws ValidationException, ObjectNotFoundException {
		controller.create(null, null, new EarlyAlertResponseTO());
		fail("Null values should have thrown an exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testControllerCreateWithNull()
			throws ValidationException, ObjectNotFoundException {
		controller.create(null, null, null);
		fail("Null values should have thrown an exception.");
	}

	@Test(expected = ValidationException.class)
	public void testControllerSaveWithNull()
			throws ValidationException, ObjectNotFoundException {
		controller.save(null, null, null, null);
		fail("Null values should have thrown an exception.");
	}

	/**
	 * Test the
	 * {@link PersonEarlyAlertResponseController#create(UUID, UUID, EarlyAlertResponseTO)}
	 * and {@link PersonEarlyAlertResponseController#delete(UUID, UUID)}
	 * actions.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test()
	public void testControllerCreateAndDelete() throws ValidationException,
			ObjectNotFoundException {
		final EarlyAlertTO earlyAlert = earlyAlertController.create(PERSON_ID,
				PersonEarlyAlertControllerIntegrationTest.createEarlyAlert());

		assertNotNull("Saved early alert should not have been null.",
				earlyAlert);

		// Now create EarlyAlertResponse for testing
		final EarlyAlertResponseTO obj = new EarlyAlertResponseTO();
		obj.setEarlyAlertId(earlyAlert.getId());
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setEarlyAlertOutcomeOtherDescription("some string");
		obj.setEarlyAlertOutcomeId(EARLY_ALERT_OUTCOME_ID);

		final Set<UUID> earlyAlertReferralIds = Sets.newHashSet();
		earlyAlertReferralIds.add(EARLY_ALERT_REFERRAL_ID);
		earlyAlertReferralIds.add(EARLY_ALERT_REFERRAL_ID2);
		obj.setEarlyAlertReferralIds(earlyAlertReferralIds);

		final EarlyAlertResponseTO saved = controller.create(PERSON_ID,
				earlyAlert.getId(), obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);

		assertEquals("Saved instance values did not match.", "some string",
				saved.getEarlyAlertOutcomeOtherDescription());
		assertEquals("Saved outcome identifiers did not match.",
				EARLY_ALERT_OUTCOME_ID, saved.getEarlyAlertOutcomeId());
		assertEquals("Referral counts did not match.", 2, saved
				.getEarlyAlertReferralIds().size());
		final UUID referral = saved.getEarlyAlertReferralIds().iterator()
				.next();
		assertTrue("Saved instance sets did not match.",
				EARLY_ALERT_REFERRAL_ID.equals(referral)
						|| EARLY_ALERT_REFERRAL_ID2.equals(referral));
		assertEquals("Saved instance parent Early Alert ID did not match.",
				earlyAlert.getId(), saved.getEarlyAlertId());

		session.clear();

		final ServiceResponse response = controller.delete(savedId, PERSON_ID);

		assertNotNull("Deletion response should not have been null.",
				response);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());

		try {
			// ObjectNotFoundException expected at this point
			final EarlyAlertResponseTO afterDeletion = controller.get(savedId,
					PERSON_ID);
			assertNull(
					"Instance should not be able to get loaded after it has been deleted.",
					afterDeletion);
		} catch (final ObjectNotFoundException exc) { // NOPMD by jon.adams
			// expected
		}
	}

	/**
	 * Test the
	 * {@link PersonEarlyAlertResponseController#save(UUID, UUID, UUID, EarlyAlertResponseTO)}
	 * action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test()
	public void testControllerSmokeTestSave() throws ObjectNotFoundException,
			ValidationException {
		// arrange
		final EarlyAlertTO earlyAlert = earlyAlertController.create(PERSON_ID,
				PersonEarlyAlertControllerIntegrationTest.createEarlyAlert());

		// Create EarlyAlertResponse for testing
		final EarlyAlertResponseTO obj = new EarlyAlertResponseTO();
		obj.setEarlyAlertId(earlyAlert.getId());
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setEarlyAlertOutcomeOtherDescription("some string");
		obj.setEarlyAlertOutcomeId(EARLY_ALERT_OUTCOME_ID);

		final Set<UUID> earlyAlertReferralIds = Sets.newHashSet();
		earlyAlertReferralIds.add(EARLY_ALERT_REFERRAL_ID);
		obj.setEarlyAlertReferralIds(earlyAlertReferralIds);

		final EarlyAlertResponseTO saved = controller.create(PERSON_ID,
				earlyAlert.getId(), obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		session.clear();

		saved.setComment("new comment");

		// act
		final EarlyAlertResponseTO updated = controller.save(saved.getId(),
				PERSON_ID, saved.getEarlyAlertId(), saved);
		session.flush();
		session.clear();
		final EarlyAlertResponseTO reloaded = controller.get(updated.getId(),
				PERSON_ID);

		// assert
		assertEquals("Updated data did not match.", "new comment",
				reloaded.getComment());
	}

	/**
	 * Test the {@link PersonEarlyAlertResponseController#get(UUID, UUID)}
	 * action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test()
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		// arrange, act
		final EarlyAlertResponseTO earlyAlert = controller.get(
				EARLY_ALERT_RESPONSE_ID, PERSON_ID);

		// assert
		assertEquals("Comment did not match.", "test comment here",
				earlyAlert.getComment());
		assertEquals("Outcomes did not match.", EARLY_ALERT_OUTCOME_ID,
				earlyAlert.getEarlyAlertOutcomeId());
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