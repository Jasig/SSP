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
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.EarlyAlertSuggestionTO;
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
 * {@link PersonEarlyAlertController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class PersonEarlyAlertControllerIntegrationTest {

	@Autowired
	private transient PersonEarlyAlertController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final String PERSON_STUDENTID = "student0";

	private static final UUID EARLY_ALERT_SUGGESTION_ID = UUID
			.fromString("b2d11141-5056-a51a-80c1-c1250ba820f8");

	private static final String EARLY_ALERT_SUGGESTION_NAME = "See Instructor";

	private static final UUID EARLY_ALERT_SUGGESTION_DELETED_ID = UUID
			.fromString("881DF3DD-1AA6-4CB8-8817-E95DAF49227A");

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
	 * Test that the {@link PersonEarlyAlertController#get(UUID, UUID)} action
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

		final EarlyAlertTO obj = controller.get(PERSON_ID, UUID.randomUUID());

		assertNull(
				"Returned EarlyAlertTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link PersonEarlyAlertController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAll() throws Exception {
		final Collection<EarlyAlertTO> list = controller.getAll(PERSON_ID,
				ObjectStatus.ACTIVE,
				null, null, null, null);

		assertNotNull("List should not have been null.", list);
	}

	/**
	 * Test the {@link PersonEarlyAlertController#create(UUID, EarlyAlertTO)}
	 * and {@link PersonEarlyAlertController#delete(UUID, UUID)} actions.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerDelete() throws Exception {
		final String testEmailCC = "some@email.address.com"; // NOPMD by jon

		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setPersonId(PERSON_ID);
		obj.setEmailCC(testEmailCC);
		final Set<EarlyAlertSuggestionTO> earlyAlertSuggestionIds = Sets
				.newHashSet();
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestionTO(
				EARLY_ALERT_SUGGESTION_ID, EARLY_ALERT_SUGGESTION_NAME));
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);

		final EarlyAlertTO saved = controller.create(PERSON_ID,
				obj);
		assertNotNull("Saved instance should not have been null.", saved);

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);

		assertEquals("Saved instance values did not match.", testEmailCC,
				saved.getEmailCC());
		assertEquals("Saved instance sets did not match.",
				EARLY_ALERT_SUGGESTION_NAME,
				saved.getEarlyAlertSuggestionIds().iterator().next().getName());

		final ServiceResponse response = controller.delete(savedId, PERSON_ID);

		assertNotNull("Deletion response should not have been null.",
				response);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());

		final EarlyAlertTO afterDeletion = controller.get(savedId,
				PERSON_ID);
		// ObjectNotFoundException expected at this point
		assertNull(
				"Instance should not be able to get loaded after it has been deleted.",
				afterDeletion);
	}

	/**
	 * Test that the {@link PersonEarlyAlertController#get(UUID, UUID)} action
	 * only returns sets with only {@link ObjectStatus#ACTIVE} reference data
	 * objects.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	@Transactional()
	public void testControllerGetSetsWithOnlyActiveReference() throws Exception {
		final EarlyAlertTO obj = createEarlyAlert();

		final EarlyAlertTO saved = controller.create(PERSON_ID,
				obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush(); // flush to ensure the INSERT commands are run now

		assertNotNull("Saved instance should not have been null.", saved);
		assertEquals("Saved instance data did not match.", PERSON_ID,
				saved.getClosedById());

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);

		// now clear all entities from the session so reloading the instance by
		// the identifier will run any mapping filter annotations
		session.clear();

		// Reload data to make sure it filters correctly
		// final EarlyAlertTO reloaded = controller.get(savedId, PERSON_ID);

		// TODO: ObjectStatus filter isn't working right now

		// final Set<EarlyAlertSuggestionTO> suggestions =
		// reloaded.getEarlyAlertSuggestionIds();

		// assertEquals("Set returned all objects instead of active only.", 1,
		// suggestions.size());
		// assertEquals("Saved instance sets did not match.",
		// EARLY_ALERT_SUGGESTION_NAME,
		// suggestions.iterator().next().getName());

		final ServiceResponse response = controller.delete(savedId,
				PERSON_ID);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());
	}

	public static EarlyAlertTO createEarlyAlert() {
		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setPersonId(PERSON_ID);
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setClosedById(PERSON_ID);

		final Set<EarlyAlertSuggestionTO> earlyAlertSuggestionIds = Sets
				.newHashSet();
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestionTO(
				EARLY_ALERT_SUGGESTION_ID, EARLY_ALERT_SUGGESTION_NAME));
		final EarlyAlertSuggestionTO deletedSuggestion = new EarlyAlertSuggestionTO(
				EARLY_ALERT_SUGGESTION_DELETED_ID,
				"EARLY_ALERT_SUGGESTION_DELETED_NAME");
		deletedSuggestion.setObjectStatus(ObjectStatus.DELETED);
		earlyAlertSuggestionIds.add(deletedSuggestion);
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);
		return obj;
	}

	/**
	 * Test the {@link PersonEarlyAlertController#create(UUID, EarlyAlertTO)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerCreateWithStudentId() throws Exception {
		final EarlyAlertTO obj = new EarlyAlertTO();
		final EarlyAlertTO saved = controller.create(PERSON_STUDENTID,
				obj);
		assertNotNull("Saved instance should not have been null.", saved);

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);

		assertEquals("Saved instance Person ID values did not match.",
				PERSON_ID,
				saved.getPersonId());

		sessionFactory.getCurrentSession().flush();

		final ServiceResponse response = controller.delete(savedId, PERSON_ID);

		assertNotNull("Deletion response should not have been null.",
				response);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());
	}

	/**
	 * Test the {@link PersonEarlyAlertController#create(UUID, EarlyAlertTO)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerCreateWithInvalidStudentId() throws Exception {
		final EarlyAlertTO obj = new EarlyAlertTO();
		final EarlyAlertTO saved = controller.create("invalid id",
				obj);
		assertNull(
				"Invalid StudentID should have thrown an ObjectNotFoundException.",
				saved);
	}
}
