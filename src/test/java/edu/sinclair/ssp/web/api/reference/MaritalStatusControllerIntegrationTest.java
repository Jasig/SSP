package edu.sinclair.ssp.web.api.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;
import edu.sinclair.ssp.transferobject.reference.MaritalStatusTO;
import edu.sinclair.ssp.web.api.validation.ValidationException;

/**
 * MaritalStatus controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class MaritalStatusControllerIntegrationTest {

	@Autowired
	private MaritalStatusController controller;

	private static final UUID MaritalStatus_ID = UUID
			.fromString("76521a04-b531-4c25-b6a6-609f3a123456");

	private static final String MaritalStatus_NAME = "I have a fiancé";

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the admin user for use by
	 * {@link #testControllerCreateAndDelete()} that checks that the Auditable
	 * auto-fill properties are correctly filled.
	 */
	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the {@link MaritalStatusController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		MaritalStatusTO obj = controller.get(MaritalStatus_ID);

		assertNotNull(
				"Returned MaritalStatusTO from the controller should not have been null.",
				obj);

		assertEquals("Returned MaritalStatus.Name did not match.",
				MaritalStatus_NAME, obj.getName());
	}

	/**
	 * Test that the {@link MaritalStatusController#get(UUID)} action returns
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

		MaritalStatusTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned MaritalStatusTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link MaritalStatusController#create(MaritalStatusTO)} and
	 * {@link MaritalStatusController#delete(UUID)} actions.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerCreateAndDelete() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		String testString1 = "testString1";
		String testString2 = "testString1";

		// Check validation of 'no ID for create()'
		MaritalStatusTO obj = new MaritalStatusTO(UUID.randomUUID(),
				testString1, testString2);
		try {
			obj = controller.create(obj);
			assertTrue(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					false);
		} catch (ValidationException exc) {
			/* expected */
		}

		// Now create a valid MaritalStatus
		obj = new MaritalStatusTO(null, testString1, testString2);
		obj = controller.create(obj);

		assertNotNull(
				"Returned MaritalStatusTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned MaritalStatusTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned MaritalStatusTO.Name from the controller did not match.",
				testString1, obj.getName());
		assertEquals(
				"Returned MaritalStatusTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedById());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}

	/**
	 * Test the {@link MaritalStatusController#getAll(ObjectStatus)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAll() throws Exception {
		List<MaritalStatusTO> list = controller.getAll(ObjectStatus.ACTIVE);

		assertNotNull("List should not have been null.", list);
		assertTrue("List action should have returned some objects.",
				list.size() > 0);
	}
}
