package org.jasig.ssp.web.api; // NOPMD

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.EarlyAlertReasonTO;
import org.jasig.ssp.transferobject.reference.EarlyAlertSuggestionTO;
import org.jasig.ssp.util.sort.PagingWrapper;
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
 * {@link PersonEarlyAlertController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class PersonEarlyAlertControllerIntegrationTest { // NOPMD by jon.adams

	@Autowired
	private transient PersonEarlyAlertController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	protected transient CampusService campusService;

	// Can't use service because it doesn't offer GetAll or similar methods
	@Autowired
	protected transient MessageDao messageDao;

	@Autowired
	protected transient PersonService personService;

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final UUID EARLY_ALERT_ID = UUID
			.fromString("74891747-36aa-409c-8b1f-76d3eaf9028e");

	private static final String PERSON_STUDENTID = "student0";

	private static final UUID EARLY_ALERT_SUGGESTION_ID = UUID
			.fromString("b2d11141-5056-a51a-80c1-c1250ba820f8");

	private static final String EARLY_ALERT_SUGGESTION_NAME = "See Instructor";

	private static final UUID EARLY_ALERT_SUGGESTION_INACTIVE_ID = UUID
			.fromString("881DF3DD-1AA6-4CB8-8817-E95DAF49227A");

	private static final UUID EARLY_ALERT_REASON_ID = UUID
			.fromString("B2D11335-5056-A51A-80EA-074F8FEF94EA");

	private static final UUID EARLY_ALERT_SUGGESTION_ID1 = UUID
			.fromString("B2D11170-5056-A51A-8002-B5CE9F25E2BC");

	private static final UUID EARLY_ALERT_SUGGESTION_ID2 = UUID
			.fromString("B2D111ED-5056-A51A-8046-5291453E8720");

	private static final UUID CAMPUS_ID = UUID
			.fromString("901E104B-4DC7-43F5-A38E-581015E204E1");

	private static final String COURSE_NAME = "Some Really Fancy Course Name";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				"ROLE_PERSON_EARLY_ALERT_READ",
				"ROLE_PERSON_EARLY_ALERT_WRITE",
				"ROLE_PERSON_EARLY_ALERT_DELETE");
	}

	/**
	 * Test that the {@link PersonEarlyAlertController#get(UUID, UUID)} action
	 * returns the correct validation errors when an invalid ID is sent.
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
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerAll() throws ObjectNotFoundException {
		final Collection<EarlyAlertTO> list = controller.getAll(PERSON_ID,
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
	}

	/**
	 * Test the {@link PersonEarlyAlertController#create(UUID, EarlyAlertTO)}
	 * and {@link PersonEarlyAlertController#delete(UUID, UUID)} actions.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerDelete() throws ValidationException,
			ObjectNotFoundException {
		final String testEmailCC = "some@email.address.com"; // NOPMD by jon

		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setPersonId(PERSON_ID);
		obj.setEmailCC(testEmailCC);
		obj.setCampusId(CAMPUS_ID);
		final Set<EarlyAlertSuggestionTO> earlyAlertSuggestionIds = Sets
				.newHashSet();
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestionTO(
				EARLY_ALERT_SUGGESTION_ID, EARLY_ALERT_SUGGESTION_NAME));
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);

		final EarlyAlertTO saved = controller.create(PERSON_ID,
				obj);
		assertNotNull("Saved data should not have been null.", saved);

		final UUID savedId = saved.getId();
		assertNotNull(
				"New identifier should not have been null.",
				savedId);

		assertEquals("Saved instance values did not match.", testEmailCC,
				saved.getEmailCC());
		assertEquals("Saved instance sets did not match.",
				EARLY_ALERT_SUGGESTION_NAME,
				saved.getEarlyAlertSuggestionIds().iterator().next()
						.getName());

		final ServiceResponse response = controller.delete(savedId,
				PERSON_ID);

		assertNotNull("Deletion response should not have been null.",
				response);
		assertTrue("Response should have returned success.",
				response.isSuccess());

		final EarlyAlertTO afterDeletion = controller.get(savedId,
				PERSON_ID);
		// ObjectNotFoundException expected at this point
		assertNull(
				"Instance should not be able to get loaded after it has been deleted.",
				afterDeletion);
	}

	/**
	 * Test the {@link PersonEarlyAlertController#get(UUID, UUID)} action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		final EarlyAlertTO ea = controller.get(EARLY_ALERT_ID, PERSON_ID);
		assertEquals("Person instance does not match expected.", PERSON_ID,
				ea.getPersonId());
	}

	/**
	 * Test the {@link PersonEarlyAlertController#get(UUID, UUID)} action throws
	 * correct exception for missing ID.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetNotFound() throws ObjectNotFoundException,
			ValidationException {
		controller.get(UUID.randomUUID(), PERSON_ID);
		fail("Exception should have been thrown.");
	}

	/**
	 * Test that the {@link PersonEarlyAlertController#get(UUID, UUID)} action
	 * returns sets with {@link ObjectStatus#ACTIVE} and
	 * {@link ObjectStatus#INACTIVE} reference data objects.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGetSetsWithOnlyActiveReference()
			throws ValidationException, ObjectNotFoundException {
		final EarlyAlertTO obj = createEarlyAlert();
		final EarlyAlertTO saved = controller.create(PERSON_ID, obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush(); // flush to ensure the INSERT commands are run now

		assertEquals("Saved instance data did not match.", PERSON_ID,
				saved.getClosedById());

		final UUID savedId = saved.getId();

		// now clear all entities from the session so reloading the instance by
		// the identifier will run any mapping filter annotations
		session.clear();

		// Reload data to make sure it filters correctly
		final EarlyAlertTO reloaded = controller.get(savedId, PERSON_ID);

		final Set<EarlyAlertSuggestionTO> suggestions = reloaded
				.getEarlyAlertSuggestionIds();

		assertEquals("Set returned all objects instead of active only.", 2,
				suggestions.size());

		int count = 0;

		for (final EarlyAlertSuggestionTO eas : suggestions) {
			if (eas.getId().equals(EARLY_ALERT_SUGGESTION_ID)) {
				count++;
				assertEquals("Status should be active.", ObjectStatus.ACTIVE,
						eas.getObjectStatus());
				assertEquals("Saved instance sets did not match.",
						EARLY_ALERT_SUGGESTION_NAME, eas.getName());
			} else if (eas.getId().equals(EARLY_ALERT_SUGGESTION_INACTIVE_ID)) {
				count++;
				assertEquals("Status should be inactive.",
						ObjectStatus.INACTIVE,
						eas.getObjectStatus());
			} else {
				fail("Unexpected Suggestion found.");
			}
		}

		assertEquals("Found suggestion identifiers did not match expected.", 2,
				count);

		final ServiceResponse response = controller.delete(savedId,
				PERSON_ID);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());
	}

	@Test(expected = ValidationException.class)
	public void testControllerCreateWithInvalidDataGetId()
			throws ValidationException, ObjectNotFoundException {
		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setId(UUID.randomUUID());
		controller.create(UUID.randomUUID(), obj);
		fail("Create with invalid Person UUID should have thrown exception.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testControllerCreateWithInvalidDataEmptyId()
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlertTO obj = new EarlyAlertTO();
		controller.create("", obj);
		fail("Create with empty student ID should have thrown exception.");
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testControllerCreateWithInvalidData()
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlertTO obj = new EarlyAlertTO();
		controller.create("bad id", obj);
		fail("Create with invalid person UUID should have thrown exception.");
	}

	@Test(expected = ValidationException.class)
	public void testControllerCreateWithMismatchedPersonIds()
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setPersonId(UUID.randomUUID());
		controller.create(UUID.randomUUID(), obj);
		fail("Create with mismatched path and TO person IDs should have"
				+ " thrown exception.");
	}

	@Test
	public void testControllerCreateWithNoPersonIdInTO()
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlertTO obj = createEarlyAlert();
		obj.setPersonId(null);
		final EarlyAlertTO saved = controller.create(PERSON_ID, obj);
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
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCreateWithStudentId()
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setCampusId(CAMPUS_ID);
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
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerCreateWithInvalidStudentId()
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlertTO obj = new EarlyAlertTO();
		final EarlyAlertTO saved = controller.create("invalid id",
				obj);
		assertNull(
				"Invalid StudentID should have thrown an ObjectNotFoundException.",
				saved);
	}

	/**
	 * Test that student->coach is set during EarlyAlert creation.
	 * 
	 * <p>
	 * This test assumes that the default campus EA coordinator is the system
	 * administrator account.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCreateAndSetCoach()
			throws ObjectNotFoundException, ValidationException {
		final Session session = sessionFactory.getCurrentSession();
		final EarlyAlertTO obj = createEarlyAlert();
		final UUID studentId = obj.getPersonId();
		final Person student = personService.get(studentId);
		student.setCoach(null);
		assertNull("Test data coach should have been null.", student.getCoach());
		session.flush();
		assertNull("Student coach should have been null.", student.getCoach());

		// No that coach has been cleared, save EarlyAlert to ensure it is reset
		final EarlyAlertTO saved = controller.create(studentId, obj);
		assertNotNull("Saved Early Alert should not have been null.", saved);

		session.flush();
		assertNotNull("Student coach should not have been null.",
				student.getCoach());
		session.evict(student);

		final Person reloadedPerson = personService.get(studentId);

		assertEquals("Coach IDs did not match.",
				Person.SYSTEM_ADMINISTRATOR_ID,
				reloadedPerson.getCoach().getId());
	}

	public static EarlyAlertTO createEarlyAlert() {
		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setPersonId(PERSON_ID);
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setClosedById(PERSON_ID);
		obj.setCampusId(UUID.fromString("901E104B-4DC7-43F5-A38E-581015E204E1"));
		obj.setEarlyAlertReasonOtherDescription("some string");
		obj.setEarlyAlertSuggestionOtherDescription("some other description");

		final Set<EarlyAlertSuggestionTO> earlyAlertSuggestionIds = Sets
				.newHashSet();
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestionTO(
				EARLY_ALERT_SUGGESTION_ID, EARLY_ALERT_SUGGESTION_NAME));
		final EarlyAlertSuggestionTO inactiveSuggestion = new EarlyAlertSuggestionTO(
				EARLY_ALERT_SUGGESTION_INACTIVE_ID,
				"Some EARLY_ALERT_SUGGESTION_INACTIVE_NAME");
		inactiveSuggestion.setObjectStatus(ObjectStatus.INACTIVE);
		earlyAlertSuggestionIds.add(inactiveSuggestion);
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);
		return obj;
	}

	/**
	 * Test the {@link PersonEarlyAlertController#create(UUID, EarlyAlertTO)}
	 * action and check that the appropriate {@link Message}s are created.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCreateAndMessageResults()
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setCampusId(CAMPUS_ID);
		obj.setCourseName(COURSE_NAME);
		final EarlyAlertTO saved = controller.create(PERSON_STUDENTID,
				obj);

		final UUID savedId = saved.getId();

		sessionFactory.getCurrentSession().flush();

		// Get messages
		final PagingWrapper<Message> data = messageDao
				.getAll(ObjectStatus.ACTIVE);
		final Collection<Message> msgs = data.getRows();
		assertFalse("Some messages should have been entered.", msgs.isEmpty());

		boolean found = false; // NOPMD by jon.adams on 5/20/12 10:06 PM
		for (final Message msg : msgs) {
			final String body = msg.getBody();
			if (body.contains("<tr><th>Instructor</th><td>System Administrator</td></tr>")) {
				controller.getLogger().debug(
						"Applicable message found. Body: {}", body);
				found = true;
				break;
			}
		}

		assertTrue("Some message for this early alert should have been found.",
				found);

		final ServiceResponse response = controller.delete(savedId, PERSON_ID);
		assertTrue("Deletion did not return success.",
				response.isSuccess());
	}

	/**
	 * Test the {@link PersonEarlyAlertController#create(UUID, EarlyAlertTO)}
	 * and
	 * {@link PersonEarlyAlertController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * actions.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCreateWithStudentIdAndMultipleSuggestions()
			throws ObjectNotFoundException, ValidationException {
		final EarlyAlertTO obj = new EarlyAlertTO();
		obj.setCampusId(CAMPUS_ID);

		final Set<EarlyAlertReasonTO> earlyAlertReasonIds = Sets.newHashSet();
		earlyAlertReasonIds.add(new EarlyAlertReasonTO(EARLY_ALERT_REASON_ID,
				""));
		obj.setEarlyAlertReasonIds(earlyAlertReasonIds);

		final Set<EarlyAlertSuggestionTO> earlyAlertSuggestionIds = Sets
				.newHashSet();
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestionTO(
				EARLY_ALERT_SUGGESTION_ID1,
				""));
		earlyAlertSuggestionIds.add(new EarlyAlertSuggestionTO(
				EARLY_ALERT_SUGGESTION_ID2,
				""));
		obj.setEarlyAlertSuggestionIds(earlyAlertSuggestionIds);

		final EarlyAlertTO saved = controller.create(PERSON_STUDENTID,
				obj);
		assertNotNull("Saved instance should not have been null.", saved);

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);

		assertEquals("Saved instance Person ID values did not match.",
				PERSON_ID,
				saved.getPersonId());

		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		session.clear();

		final PagedResponse<EarlyAlertTO> earlyAlerts = controller.getAll(
				PERSON_ID, ObjectStatus.ALL, null, null, null, null);

		assertNotEmpty("Response should not have been empty.",
				earlyAlerts.getRows());
		assertEquals(
				"Should have included 2 Early Alerts. (One existing, one new)",
				2, earlyAlerts.getResults());
		assertEquals(
				"Should have included 2 Early Alerts. (One existing, one new)",
				2, earlyAlerts.getRows().size());
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