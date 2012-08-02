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
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.JournalStepTO;
import org.jasig.ssp.transferobject.reference.JournalTrackTO;
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
 * JournalTrack controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class JournalTrackControllerIntegrationTest {

	@Autowired
	private transient JournalTrackController controller;

	private static final UUID JOURNALTRACK_ID = UUID
			.fromString("b2d07a7d-5056-a51a-80a8-96ae5188a188");

	private static final String JOURNALTRACK_NAME = "ILP";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString1";

	/**
	 * Setup the security service with the admin user for use by
	 * {@link #testControllerCreateAndDelete()} that checks that the Auditable
	 * auto-fill properties are correctly filled.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the {@link JournalTrackController#get(UUID)} action.
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

		final JournalTrackTO obj = controller.byId(JOURNALTRACK_ID);

		assertNotNull(
				"Returned JournalTrackTO from the controller should not have been null.",
				obj);

		assertEquals("Returned JournalTrack.Name did not match.",
				JOURNALTRACK_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link JournalTrackController#get(UUID)} action returns the
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

		final JournalTrackTO obj = controller.byId(UUID.randomUUID());

		assertNull(
				"Returned JournalTrackTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link JournalTrackController#create(JournalTrackTO)} and
	 * {@link JournalTrackController#delete(UUID)} actions.
	 * 
	 * @throws ValidationException
	 *             Expected exception.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test(expected = ValidationException.class)
	public void testControllerCreateInvalid() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		// Check validation of 'no ID for create()'
		final JournalTrackTO journalTrackInvalid = new JournalTrackTO(
				UUID.randomUUID(),
				TEST_STRING1,
				TEST_STRING2);
		controller.create(journalTrackInvalid);
		fail("Calling create with an object with an ID should have thrown a validation excpetion.");
	}

	/**
	 * Test the {@link JournalTrackController#create(JournalTrackTO)} and
	 * {@link JournalTrackController#delete(UUID)} actions.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCreateAndDelete() throws ObjectNotFoundException,
			ValidationException {
		// arrange
		final JournalTrackTO journalTrack = new JournalTrackTO(null,
				TEST_STRING1,
				TEST_STRING2);

		// act
		final JournalTrackTO saved = controller.create(journalTrack);

		// assert
		assertNotNull(
				"Returned JournalTrackTO from the controller should not have been null.",
				saved);
		assertNotNull(
				"Returned JournalTrackTO.ID from the controller should not have been null.",
				saved.getId());
		assertEquals(
				"Returned JournalTrackTO.Name from the controller did not match.",
				TEST_STRING1, saved.getName());
		assertEquals(
				"Returned JournalTrackTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, saved.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(saved.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link JournalTrackController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<JournalTrackTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test the
	 * {@link JournalTrackController#getAllForJournalTrack(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test.
	 */
	@Test
	public void testControllerAllForJournalTrack()
			throws ObjectNotFoundException {
		final PagedResponse<JournalStepTO> list = controller
				.getAllForJournalTrack(JOURNALTRACK_ID, ObjectStatus.ALL, 0,
						10, null, null);

		assertNotNull("List should not have been null.", list);
		assertEquals("List action should have returned 1 item in the list.", 1,
				list.getResults());
	}

	/**
	 * Test that getLogger() returns the matching log class name for the current
	 * class under test.
	 */
	@Test
	public void testLogger() {
		// arrange, act
		final Logger logger = controller.getLogger();

		// assert
		assertEquals("Log class name did not match.", controller.getClass()
				.getName(), logger.getName());
	}
}