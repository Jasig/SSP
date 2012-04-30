package org.studentsuccessplan.ssp.web.api.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.studentsuccessplan.ssp.transferobject.reference.FundingSourceTO;
import org.studentsuccessplan.ssp.web.api.validation.ValidationException;

/**
 * FundingSource controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class FundingSourceControllerIntegrationTest {

	private static final UUID FUNDINGSOURCE_ID = UUID
			.fromString("a6521a04-b531-4c25-b6a6-609f3a509f85");

	private static final String FUNDINGSOURCE_NAME = "Test Funding Source";

	@Autowired
	private transient FundingSourceController controller;

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
	 * Test the {@link FundingSourceController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final FundingSourceTO obj = controller.get(FUNDINGSOURCE_ID);

		assertNotNull(
				"Returned FundingSourceTO from the controller should not have been null.",
				obj);

		assertEquals("Returned FundingSource.Name did not match.",
				FUNDINGSOURCE_NAME, obj.getName());
	}

	/**
	 * Test that the {@link FundingSourceController#get(UUID)} action returns
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

		final FundingSourceTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned FundingSourceTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link FundingSourceController#create(FundingSourceTO)} and
	 * {@link FundingSourceController#delete(UUID)} actions.
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
		FundingSourceTO obj = new FundingSourceTO(UUID.randomUUID(),
				testString1, testString2);
		try {
			obj = controller.create(obj);
			assertTrue(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					false);
		} catch (ValidationException exc) {
			/* expected */
		}

		// Now create a valid FundingSource
		obj = new FundingSourceTO(null, testString1, testString2);
		obj = controller.create(obj);

		assertNotNull(
				"Returned FundingSourceTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned FundingSourceTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned FundingSourceTO.Name from the controller did not match.",
				testString1, obj.getName());
		assertEquals(
				"Returned FundingSourceTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedById());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link FundingSourceController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAll() throws Exception {
		final Collection<FundingSourceTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}
}
