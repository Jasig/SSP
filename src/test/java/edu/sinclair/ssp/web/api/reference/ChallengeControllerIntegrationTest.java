package edu.sinclair.ssp.web.api.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;
import edu.sinclair.ssp.web.api.validation.ValidationException;

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

	@Autowired
	private ChallengeController controller;

	private static final UUID CHALLENGE_ID = UUID
			.fromString("f5bb0a62-1756-4ea2-857d-5821ee44a1d0");

	private static final String CHALLENGE_NAME = "Test Challenge";

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
	 * Test the {@link ChallengeController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		ChallengeTO obj = controller.get(CHALLENGE_ID);

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
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		ChallengeTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned ChallengeTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link ChallengeController#create(ChallengeTO)} and
	 * {@link ChallengeController#delete(UUID)} actions.
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
		ChallengeTO obj = new ChallengeTO(UUID.randomUUID(), testString1,
				testString2);
		try {
			obj = controller.create(obj);
			assertTrue(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					false);
		} catch (ValidationException exc) {
			/* expected */
		}

		// Now create a valid Challenge
		obj = new ChallengeTO(null, testString1, testString2);
		obj = controller.create(obj);

		assertNotNull(
				"Returned ChallengeTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned ChallengeTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned ChallengeTO.Name from the controller did not match.",
				testString1, obj.getName());
		assertEquals(
				"Returned ChallengeTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedById());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}
}
