package org.jasig.ssp.web.api; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.TestUtils;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.JournalEntryDetailTO;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;

/**
 * {@link PersonJournalEntryController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class PersonJournalEntryControllerIntegrationTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonJournalEntryControllerIntegrationTest.class);

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final UUID CONFIDENTIALITY_LEVEL_ID = UUID
			.fromString("B3D077A7-4055-0510-7967-4A09F93A0357");

	private static final DataPermissions CONFIDENTIALITY_LEVEL_PERMISSION = DataPermissions.DATA_EVERYONE;

	private static final UUID JOURNAL_ENTRY_ID = UUID
			.fromString("86FFCD52-AF44-11E1-98F9-0026B9E7FF4C");

	private static final UUID JOURNAL_STEP_ID = UUID
			.fromString("ABA1440C-AB5B-11E1-BA73-0026B9E7FF4C");

	private static final UUID JOURNAL_STEP_DETAIL_ID = UUID
			.fromString("471afc02-ab5c-11e1-a997-0026b9e7ff4c");

	private static final UUID JOURNAL_STEP_DETAIL_ID2 = UUID
			.fromString("471afc02-ab5c-11f1-a997-0026b9e7ff5d");

	private static final UUID JOURNAL_TRACK_ID = UUID
			.fromString("B2D07B38-5056-A51A-809D-81EA2F3B27BF");

	private static final String JSON_CREATE_JOURNAL_ENTRY = "journal_entry_create.json";

	@Autowired
	private transient PersonJournalEntryController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				confidentialityLevelService
						.confidentialityLevelsAsGrantedAuthorities(),
				"ROLE_PERSON_JOURNAL_ENTRY_READ",
				"ROLE_PERSON_JOURNAL_ENTRY_WRITE",
				"ROLE_PERSON_JOURNAL_ENTRY_DELETE",
				CONFIDENTIALITY_LEVEL_PERMISSION.asPermissionString());
	}

	/**
	 * Test that the {@link PersonJournalEntryController#get(UUID, UUID)} action
	 * returns the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 * @throws ValidationException
	 *             Should not be thrown for this test.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final JournalEntryTO obj = controller.byId(PERSON_ID,
				UUID.randomUUID());

		assertNull(
				"Returned JournalEntryTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test that the {@link PersonJournalEntryController#get(UUID, UUID)} action
	 * returns the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 * @throws ValidationException
	 *             Should not be thrown for this test.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		final JournalEntryTO journalEntry = controller.byId(PERSON_ID,
				JOURNAL_ENTRY_ID);

		assertNull(
				"Returned JournalEntryTO from the controller should have been null.",
				journalEntry);
		assertEquals("Detail list count did not match.", 2, journalEntry
				.getJournalEntryDetails().size());
	}

	/**
	 * Test the
	 * {@link PersonJournalEntryController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 */
	@Test
	public void testControllerAll() throws ObjectNotFoundException {
		final Collection<JournalEntryTO> list = controller.getAll(
				PERSON_ID,
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
	}

	/**
	 * Test the
	 * {@link PersonJournalEntryController#create(UUID, JournalEntryTO)} and
	 * {@link PersonJournalEntryController#delete(UUID, UUID)} actions.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 * @throws ValidationException
	 *             Should not be thrown for this test.
	 */
	@Test()
	public void testControllerCreateAndDelete() throws ObjectNotFoundException,
			ValidationException {
		// Now create JournalEntry for testing
		final JournalEntryTO obj = new JournalEntryTO();
		obj.setPersonId(PERSON_ID);
		obj.setEntryDate(new Date());
		obj.setJournalSource(new ReferenceLiteTO<JournalSource>(UUID
				.fromString("b2d07973-5056-a51a-8073-1d3641ce507f"), ""));
		obj.setJournalTrack(new ReferenceLiteTO<JournalTrack>(UUID
				.fromString("b2d07a7d-5056-a51a-80a8-96ae5188a188"), ""));
		obj.setConfidentialityLevel(new ConfidentialityLevelLiteTO(
				CONFIDENTIALITY_LEVEL_ID, ""));
		obj.setObjectStatus(ObjectStatus.ACTIVE);

		final Set<JournalEntryDetailTO> journalEntryDetails = Sets.newHashSet();
		final JournalEntryDetailTO journalEntryDetail = new JournalEntryDetailTO();
		journalEntryDetail.setJournalStep(new ReferenceLiteTO<JournalStep>(
				JOURNAL_STEP_ID, ""));
		final Set<ReferenceLiteTO<JournalStepDetail>> details = Sets
				.newHashSet();
		details.add(new ReferenceLiteTO<JournalStepDetail>(
				JOURNAL_STEP_DETAIL_ID, ""));
		journalEntryDetail.setJournalStepDetails(details);
		journalEntryDetails.add(journalEntryDetail);
		obj.setJournalEntryDetails(journalEntryDetails);

		final JournalEntryTO saved = controller.create(PERSON_ID, obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);
		assertEquals("Person identifiers don't match.", PERSON_ID,
				saved.getPersonId());
		assertFalse("JournalEntryDetails should not have been empty.", saved
				.getJournalEntryDetails().isEmpty());

		session.flush();
		session.clear();

		final JournalEntryTO reloaded = controller.byId(savedId, PERSON_ID);
		assertFalse("JournalEntryDetails should not have been empty.", reloaded
				.getJournalEntryDetails().isEmpty());

		// Try save (update)
		final JournalEntryDetailTO journalEntryDetail2 = new JournalEntryDetailTO();
		journalEntryDetail2.setJournalStep(new ReferenceLiteTO<JournalStep>(
				JOURNAL_STEP_ID, ""));
		final Set<ReferenceLiteTO<JournalStepDetail>> details2 = Sets
				.newHashSet();
		details2.add(new ReferenceLiteTO<JournalStepDetail>(
				JOURNAL_STEP_DETAIL_ID, ""));
		journalEntryDetail2.setJournalStepDetails(details2);
		journalEntryDetails.add(journalEntryDetail2);
		reloaded.getJournalEntryDetails().add(journalEntryDetail2);

		final JournalEntryTO updated = controller.save(savedId, PERSON_ID,
				reloaded);
		session.flush();

		assertEquals("Saved instance identifiers do not match.", savedId,
				updated.getId());
		assertEquals("PersonIds did not match.", PERSON_ID,
				updated.getPersonId());
		assertFalse("JournalEntryDetails should have had entries.", updated
				.getJournalEntryDetails().isEmpty());

		session.flush();
		session.clear();

		final JournalEntryTO reloadedAgain = controller
				.byId(savedId, PERSON_ID);
		assertEquals(
				"JournalEntryDetails did not have the expected number of entries.",
				2, reloadedAgain.getJournalEntryDetails().size());

		// finally delete
		final ServiceResponse response = controller.delete(savedId, PERSON_ID);

		assertNotNull("Deletion response should not have been null.",
				response);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());

		try {
			// ObjectNotFoundException expected at this point
			final JournalEntryTO afterDeletion = controller.byId(savedId,
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
	 * {@link PersonJournalEntryController#create(UUID, JournalEntryTO)} action
	 * using a null JournalTrack.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 * @throws ValidationException
	 *             Should not be thrown for this test.
	 */
	@Test()
	public void testControllerCreateWithoutTrack()
			throws ObjectNotFoundException,
			ValidationException {
		// Now create JournalEntry for testing
		final JournalEntryTO obj = new JournalEntryTO();
		obj.setPersonId(PERSON_ID);
		obj.setEntryDate(new Date());
		obj.setJournalSource(new ReferenceLiteTO<JournalSource>(UUID
				.fromString("b2d07973-5056-a51a-8073-1d3641ce507f"), ""));
		obj.setConfidentialityLevel(new ConfidentialityLevelLiteTO(
				CONFIDENTIALITY_LEVEL_ID, ""));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		/*
		 * final Set<JournalEntryDetailTO> journalEntryDetails =
		 * Sets.newHashSet(); final JournalEntryDetailTO journalEntryDetail =
		 * new JournalEntryDetailTO(); journalEntryDetail.setJournalStep(new
		 * ReferenceLiteTO<JournalStep>( JOURNAL_STEP_ID, "")); final
		 * Set<ReferenceLiteTO<JournalStepDetail>> details = Sets .newHashSet();
		 * details.add(new ReferenceLiteTO<JournalStepDetail>(
		 * JOURNAL_STEP_DETAIL_ID, ""));
		 * journalEntryDetail.setJournalStepDetails(details);
		 * journalEntryDetails.add(journalEntryDetail);
		 * obj.setJournalEntryDetails(journalEntryDetails);
		 */
		final JournalEntryTO saved = controller.create(PERSON_ID, obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);
		assertEquals("PersonIds did not match.", PERSON_ID, saved.getPersonId());

		session.flush();
		session.clear();

		final JournalEntryTO reloaded = controller.byId(savedId, PERSON_ID);
		assertNotNull("Reloaded entry should not have been null.", reloaded);
		assertNull("Track should have been null.", reloaded.getJournalTrack());
	}

	/**
	 * Test the
	 * {@link PersonJournalEntryController#create(UUID, JournalEntryTO)} action.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test.
	 * @throws ValidationException
	 *             Should not be thrown for this test.
	 */
	@Test()
	public void testControllerCreateMultiple() throws ValidationException,
			ObjectNotFoundException {
		// Now create JournalEntry for testing
		final JournalEntryTO obj = new JournalEntryTO();
		obj.setPersonId(PERSON_ID);
		obj.setEntryDate(new Date());
		obj.setJournalSource(new ReferenceLiteTO<JournalSource>(UUID
				.fromString("b2d07973-5056-a51a-8073-1d3641ce507f"), ""));
		obj.setJournalTrack(new ReferenceLiteTO<JournalTrack>(UUID
				.fromString("b2d07a7d-5056-a51a-80a8-96ae5188a188"), ""));
		obj.setConfidentialityLevel(new ConfidentialityLevelLiteTO(
				CONFIDENTIALITY_LEVEL_ID, ""));
		obj.setObjectStatus(ObjectStatus.ACTIVE);

		final ReferenceLiteTO<JournalStep> journalStep = new ReferenceLiteTO<JournalStep>(
				JOURNAL_STEP_ID, "");
		final Set<JournalEntryDetailTO> journalEntryDetails = Sets.newHashSet();
		final Set<ReferenceLiteTO<JournalStepDetail>> details = Sets
				.newHashSet();

		final JournalEntryDetailTO journalEntryDetail = new JournalEntryDetailTO();
		journalEntryDetail.setJournalStep(journalStep);
		details.add(new ReferenceLiteTO<JournalStepDetail>(
				JOURNAL_STEP_DETAIL_ID, ""));

		final JournalEntryDetailTO journalEntryDetail2 = new JournalEntryDetailTO();
		journalEntryDetail2.setJournalStep(journalStep);
		details.add(new ReferenceLiteTO<JournalStepDetail>(
				JOURNAL_STEP_DETAIL_ID2, ""));

		journalEntryDetail.setJournalStepDetails(details);
		journalEntryDetails.add(journalEntryDetail);
		obj.setJournalEntryDetails(journalEntryDetails);

		final JournalEntryTO saved = controller.create(PERSON_ID, obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);
		assertEquals("PersonIds did not match.", PERSON_ID, saved.getPersonId());
		assertFalse("JournalEntryDetails should have had entries.", saved
				.getJournalEntryDetails().isEmpty());

		session.clear();

		final JournalEntryTO reloaded = controller.byId(savedId, PERSON_ID);
		assertFalse("JournalEntryDetails should have had entries.", reloaded
				.getJournalEntryDetails().isEmpty());
		assertEquals("Details list did not have the expected item count.", 2,
				reloaded.getJournalEntryDetails().size());
	}

	/**
	 * Test the
	 * {@link PersonJournalEntryController#create(UUID, JournalEntryTO)} method
	 * with pre-created JSON data.
	 * 
	 * @exception ObjectNotFoundException
	 *                if security user could not be found.
	 * @exception JsonParseException
	 *                if test file could not be parsed
	 * @exception JsonMappingException
	 *                if test file could not be mapped
	 * @exception IOException
	 *                if test file could not be loaded
	 * @exception ValidationException
	 *                if data was not valid
	 */
	@Test
	public void testCreateFromJson() throws JsonParseException,
			JsonMappingException, IOException, ObjectNotFoundException,
			ValidationException {
		// arrange
		final JournalEntryTO journalEntry = TestUtils
				.loadJson(getClass(), JSON_CREATE_JOURNAL_ENTRY,
						JournalEntryTO.class);

		assertNotNull("JournalEntryTO should not have been null.", journalEntry);

		// act
		final JournalEntryTO saved = controller.create(PERSON_ID, journalEntry);

		// assert
		assertNotNull("Create should have returned a valid instance.", saved);
		assertEquals("Track IDs did not match.", JOURNAL_TRACK_ID, saved
				.getJournalTrack().getId());
		assertFalse("Journal Details list should not have been empty.", saved
				.getJournalEntryDetails().isEmpty());

		boolean found = false;

		for (final JournalEntryDetailTO jedTO : saved.getJournalEntryDetails()) {

			assertFalse(
					"Journal Step Details list should not have been empty.",
					jedTO.getJournalStepDetails().isEmpty());

			for (final ReferenceLiteTO<JournalStepDetail> jsdTO : jedTO
					.getJournalStepDetails()) {
				if (JOURNAL_STEP_DETAIL_ID2.equals(jsdTO.getId())) {
					found = true;
				} else {
					LOGGER.debug(
							"JournalEntryDetail ID found but not matching: {}",
							jsdTO.getId());
				}
			}
		}

		assertTrue(
				"The 2nd Journal Detail ID was not found in the returned list.",
				found);
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