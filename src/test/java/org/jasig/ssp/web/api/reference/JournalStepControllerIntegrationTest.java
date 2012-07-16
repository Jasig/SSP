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
import org.jasig.ssp.transferobject.reference.JournalStepDetailTO;
import org.jasig.ssp.transferobject.reference.JournalStepTO;
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
 * JournalStep controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class JournalStepControllerIntegrationTest {

	@Autowired
	private transient JournalStepController controller;

	private static final UUID JOURNALSTEP_ID = UUID
			.fromString("aba1440c-ab5b-11e1-ba73-0026b9e7ff4c");

	private static final String JOURNALSTEP_NAME = "Test Journal Step";

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
	 * Test the {@link JournalStepController#get(UUID)} action.
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

		final JournalStepTO obj = controller.get(JOURNALSTEP_ID);

		assertNotNull(
				"Returned JournalStepTO from the controller should not have been null.",
				obj);

		assertEquals("Returned JournalStep.Name did not match.",
				JOURNALSTEP_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link JournalStepController#get(UUID)} action returns the
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

		final JournalStepTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned JournalStepTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link JournalStepController#create(JournalStepTO)} and
	 * {@link JournalStepController#delete(UUID)} actions.
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
		final JournalStepTO journalStep = new JournalStepTO(UUID.randomUUID(),
				TEST_STRING1,
				TEST_STRING2); // NOPMD
		try {
			controller.create(journalStep);
			fail("Calling create with an object with an ID should have thrown a validation excpetion."); // NOPMD
		} catch (final ValidationException exc) { // NOPMD
			/* expected */
		}

		// Now create a valid JournalStep
		final JournalStepTO journalStep2 = new JournalStepTO(null,
				TEST_STRING1,
				TEST_STRING2); // NOPMD
		journalStep2.setUsedForTransition(true);
		final JournalStepTO saved = controller.create(journalStep2);

		assertNotNull(
				"Returned JournalStepTO from the controller should not have been null.",
				saved);
		assertNotNull(
				"Returned JournalStepTO.ID from the controller should not have been null.",
				saved.getId());
		assertEquals(
				"Returned JournalStepTO.Name from the controller did not match.",
				TEST_STRING1, saved.getName());
		assertEquals(
				"Returned JournalStepTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, saved.getCreatedBy().getId());
		assertTrue("UsedForTransition value did not match.",
				saved.isUsedForTransition());

		assertTrue("Delete action did not return success.",
				controller.delete(saved.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link JournalStepController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<JournalStepTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test the
	 * {@link JournalStepController#getAllForJournalStep(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test.
	 */
	@Test
	public void testControllerAllForJournalTrack()
			throws ObjectNotFoundException {
		final Collection<JournalStepDetailTO> list = controller
				.getAllForJournalStep(JOURNALSTEP_ID, ObjectStatus.ALL, 0, 10,
						null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
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