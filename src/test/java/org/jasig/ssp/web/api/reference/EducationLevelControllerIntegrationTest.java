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
import org.jasig.ssp.transferobject.reference.EducationLevelTO;
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
 * EducationLevel controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class EducationLevelControllerIntegrationTest {

	@Autowired
	private transient EducationLevelController controller;

	private static final UUID EDUCATIONLEVEL_ID = UUID
			.fromString("841652e8-7b80-41e7-9ef2-ce456d2606ca");

	private static final String EDUCATIONLEVEL_NAME = "Test Education Level";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

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
	 * Test the {@link EducationLevelController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final EducationLevelTO obj = controller.get(EDUCATIONLEVEL_ID);

		assertNotNull(
				"Returned EducationLevelTO from the controller should not have been null.",
				obj);

		assertEquals("Returned EducationLevel.Name did not match.",
				EDUCATIONLEVEL_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link EducationLevelController#get(UUID)} action returns
	 * the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final EducationLevelTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned EducationLevelTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link EducationLevelController#create(EducationLevelTO)} and
	 * {@link EducationLevelController#delete(UUID)} actions.
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
		EducationLevelTO obj = new EducationLevelTO(UUID.randomUUID(),
				testString1,
				testString2);
		try {
			obj = controller.create(obj);
			fail("Calling create with an object with an ID should have thrown a validation excpetion.");
		} catch (final ValidationException exc) {
			/* expected */
		}

		// Now create a valid EducationLevel
		obj = new EducationLevelTO(null, testString1, testString2);
		obj = controller.create(obj);

		assertNotNull(
				"Returned EducationLevelTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned EducationLevelTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned EducationLevelTO.Name from the controller did not match.",
				testString1, obj.getName());
		assertEquals(
				"Returned EducationLevelTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link EducationLevelController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<EducationLevelTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test that the getAll action rejects a filter of
	 * {@link ObjectStatus#DELETED}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testControllerGetAllRejectsDeletedFilter() {
		controller.getAll(ObjectStatus.DELETED, null, null, null, null);
	}
}