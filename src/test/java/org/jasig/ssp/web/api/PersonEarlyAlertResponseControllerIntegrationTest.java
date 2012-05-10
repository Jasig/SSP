package org.jasig.ssp.web.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
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
import org.jasig.ssp.transferobject.ServiceResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final UUID EARLY_ALERT_OUTCOME_ID = UUID
			.fromString("b2d11076-5056-a51a-80c1-f5813762ff0b");

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
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test that the {@link PersonEarlyAlertResponseController#get(UUID, UUID)}
	 * action returns the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final EarlyAlertResponseTO obj = controller.get(PERSON_ID,
				UUID.randomUUID());

		assertNull(
				"Returned EarlyAlertResponseTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link PersonEarlyAlertResponseController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAll() throws Exception {
		final Collection<EarlyAlertResponseTO> list = controller.getAll(
				PERSON_ID,
				ObjectStatus.ACTIVE,
				null, null, null, null);

		assertNotNull("List should not have been null.", list);
	}

	/**
	 * Test the
	 * {@link PersonEarlyAlertResponseController#create(UUID, EarlyAlertResponseTO)}
	 * and {@link PersonEarlyAlertResponseController#delete(UUID, UUID)}
	 * actions.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test()
	public void testControllerCreateAndDelete() throws Exception {
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

		final EarlyAlertResponseTO saved = controller.create(PERSON_ID, obj);
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
		} catch (ObjectNotFoundException exc) { // NOPMD by jon.adams on 5/9/12
			// expected
		}
	}
}
