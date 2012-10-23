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
package org.jasig.ssp.web.api.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.reference.StudentStatusTO;
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
 * {@link StudentStatusController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class StudentStatusControllerIntegrationTest {

	@Autowired
	private transient StudentStatusController controller;

	private static final UUID STUDENTSTATUS_ID = UUID
			.fromString("0b150cea-c3de-40ef-8564-fc2f53847a43");

	private static final String STUDENTSTATUS_NAME = "New";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

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
	 * Test the {@link StudentStatusController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final StudentStatusTO obj = controller.get(STUDENTSTATUS_ID);

		assertNotNull(
				"Returned StudentStatusTO from the controller should not have been null.",
				obj);

		assertEquals("Returned StudentStatus.Name did not match.",
				STUDENTSTATUS_NAME, obj.getName());
	}

	/**
	 * Test that the {@link StudentStatusController#get(UUID)} action returns
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

		final StudentStatusTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned StudentStatusTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link StudentStatusController#create(StudentStatusTO)} and
	 * {@link StudentStatusController#delete(UUID)} actions.
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
		try {
			final StudentStatusTO obj = controller.create(new StudentStatusTO(
					UUID
							.randomUUID(),
					testString1, testString2)); // NOPMD by jon.adams
			assertNull(
					"Calling create with an object with an ID should have thrown a validation excpetion.",
					obj);
		} catch (final ValidationException exc) {
			assertNotNull("ValidatedException was expected to be thrown.", exc);
		}

		// Now create a valid StudentStatus
		final StudentStatusTO valid = controller.create(new StudentStatusTO(
				null,
				testString1,
				testString2)); // NOPMD

		assertNotNull(
				"Returned StudentStatusTO from the controller should not have been null.",
				valid);
		assertNotNull(
				"Returned StudentStatusTO.ID from the controller should not have been null.",
				valid.getId());
		assertEquals(
				"Returned StudentStatusTO.Name from the controller did not match.",
				testString1, valid.getName());
		assertEquals(
				"Returned StudentStatusTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, valid.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(valid.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link StudentStatusController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<StudentStatusTO> list = controller.getAll(
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

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
		final Logger logger = controller.getLogger();
		assertEquals("Log class name did not match.", controller.getClass()
				.getName(), logger.getName());
	}
}