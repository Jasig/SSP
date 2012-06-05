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
import org.jasig.ssp.transferobject.reference.ConfidentialityDisclosureAgreementTO;
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
 * ConfidentialityDisclosureAgreement controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ConfidentialityDisclosureAgreementControllerIntegrationTest {

	@Autowired
	private transient ConfidentialityDisclosureAgreementController controller;

	private static final UUID CONFIDENTIALITYDISCLOSUREAGREEMENT_ID = UUID
			.fromString("06919242-824c-11e1-af98-0026b9e7ff4c");

	private static final String CONFIDENTIALITYDISCLOSUREAGREEMENT_NAME = "Test Confidentiality Disclosure Agreement";

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
	 * Test the {@link ConfidentialityDisclosureAgreementController#get(UUID)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final ConfidentialityDisclosureAgreementTO obj = controller
				.get(CONFIDENTIALITYDISCLOSUREAGREEMENT_ID);

		assertNotNull(
				"Returned ConfidentialityDisclosureAgreementTO from the controller should not have been null.",
				obj);

		assertEquals(
				"Returned ConfidentialityDisclosureAgreement.Name did not match.",
				CONFIDENTIALITYDISCLOSUREAGREEMENT_NAME,
				obj.getName());
	}

	/**
	 * Test that the
	 * {@link ConfidentialityDisclosureAgreementController#get(UUID)} action
	 * returns the
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

		final ConfidentialityDisclosureAgreementTO obj = controller.get(UUID
				.randomUUID());

		assertNull(
				"Returned ConfidentialityDisclosureAgreementTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link ConfidentialityDisclosureAgreementController#create(ConfidentialityDisclosureAgreementTO)}
	 * and {@link ConfidentialityDisclosureAgreementController#delete(UUID)}
	 * actions.
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
		ConfidentialityDisclosureAgreementTO obj = new ConfidentialityDisclosureAgreementTO(
				UUID.randomUUID(), testString1,
				testString2);
		try {
			obj = controller.create(obj);
			fail("Calling create with an object with an ID should have thrown a validation excpetion.");
		} catch (final ValidationException exc) {
			/* expected */
		}

		// Now create a valid ConfidentialityDisclosureAgreement
		obj = new ConfidentialityDisclosureAgreementTO(null, testString1,
				testString2);
		obj = controller.create(obj);

		assertNotNull(
				"Returned ConfidentialityDisclosureAgreementTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned ConfidentialityDisclosureAgreementTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned ConfidentialityDisclosureAgreementTO.Name from the controller did not match.",
				testString1, obj.getName());
		assertEquals(
				"Returned ConfidentialityDisclosureAgreementTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link ConfidentialityDisclosureAgreementController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAll() throws Exception {
		final Collection<ConfidentialityDisclosureAgreementTO> list = controller
				.getAll(
						ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}
}
