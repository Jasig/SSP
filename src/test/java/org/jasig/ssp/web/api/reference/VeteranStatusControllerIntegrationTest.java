/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api.reference; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.reference.VeteranStatusTO;
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
 * {@link VeteranStatusController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class VeteranStatusControllerIntegrationTest {

	@Autowired
	private transient VeteranStatusController controller;

	private static final UUID VETERANSTATUS_ID = UUID
			.fromString("5c584fdb-dcc8-44ff-a30d-8c3e0a2d8206");

	private static final String VETERANSTATUS_NAME = "Not applicable";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString2";

	/**
	 * Setup the security service with the administrator user for use by
	 * {@link #testControllerCreateAndDelete()} that checks that the Auditable
	 * auto-fill properties are correctly filled.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the {@link VeteranStatusController#get(UUID)} action.
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

		final VeteranStatusTO obj = controller.get(VETERANSTATUS_ID);

		assertNotNull(
				"Returned VeteranStatusTO from the controller should not have been null.",
				obj);

		assertEquals("Returned VeteranStatus.Name did not match.",
				VETERANSTATUS_NAME, obj.getName());
	}

	/**
	 * Test that the {@link VeteranStatusController#get(UUID)} action returns
	 * the correct validation errors when an invalid ID is sent.
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

		final VeteranStatusTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned VeteranStatusTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link VeteranStatusController#create(VeteranStatusTO)} and
	 * {@link VeteranStatusController#delete(UUID)} actions.
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
		try {
			final VeteranStatusTO obj = controller.create(new VeteranStatusTO(
					UUID
							.randomUUID(),
					TEST_STRING1, TEST_STRING2, (short) 1)); // NOPMD
			assertNull(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					obj);
		} catch (final ValidationException exc) {
			assertNotNull("ValidatedException was expected to be thrown.", exc);
		}

		// Now create a valid VeteranStatus
		final VeteranStatusTO valid = controller.create(new VeteranStatusTO(
				null,
				TEST_STRING1,
				TEST_STRING2, (short) 1)); // NOPMD

		assertNotNull(
				"Returned VeteranStatusTO from the controller should not have been null.",
				valid);
		assertNotNull(
				"Returned VeteranStatusTO.ID from the controller should not have been null.",
				valid.getId());
		assertEquals(
				"Returned VeteranStatusTO.Name from the controller did not match.",
				TEST_STRING1, valid.getName());
		assertEquals(
				"Returned VeteranStatusTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, valid.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(valid.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link VeteranStatusController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<VeteranStatusTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
	}

	/**
	 * Test the
	 * {@link VeteranStatusController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllResults() {
		final PagedResponse<VeteranStatusTO> results = controller
				.getAll(ObjectStatus.ACTIVE, 0, 4, null, null);

		final Collection<VeteranStatusTO> list = results.getRows();

		assertNotNull("The list should not have been null.", list);
		assertFalse("There should have been some results.", list.isEmpty());
		assertTrue("Returned results (" + list.size()
				+ ") should have been fewer than the maximum available ("
				+ results.getResults() + ") in the database.",
				results.getResults() > list.size());

		final Iterator<VeteranStatusTO> iter = list.iterator();

		VeteranStatusTO veteranStatus = iter.next();
		assertEquals("Name should have been " + VETERANSTATUS_NAME,
				VETERANSTATUS_NAME, veteranStatus.getName());
		assertFalse("ModifiedBy id should not have been empty.", veteranStatus
				.getModifiedBy().getId().equals(UUID.randomUUID()));

		veteranStatus = iter.next();
		assertTrue("Description should have been longer than 0 characters.",
				veteranStatus.getDescription().length() > 0);
		assertFalse("CreatedBy id should not have been empty.", veteranStatus
				.getCreatedBy().getId().equals(UUID.randomUUID()));
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