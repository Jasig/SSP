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
 * JournalStepDetail controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class JournalStepDetailControllerIntegrationTest {

	@Autowired
	private transient JournalStepDetailController controller;

	private static final UUID JOURNALSTEPDETAIL_ID = UUID
			.fromString("471afc02-ab5c-11e1-a997-0026b9e7ff4c");

	private static final String JOURNALSTEPDETAIL_NAME = "Test Journal Step Detail";

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
	 * Test the {@link JournalStepDetailController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final JournalStepDetailTO obj = controller.get(JOURNALSTEPDETAIL_ID);

		assertNotNull(
				"Returned JournalStepDetailTO from the controller should not have been null.",
				obj);

		assertEquals("Returned JournalStepDetail.Name did not match.",
				JOURNALSTEPDETAIL_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link JournalStepDetailController#get(UUID)} action
	 * returns the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final JournalStepDetailTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned JournalStepDetailTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link JournalStepDetailController#create(JournalStepDetailTO)}
	 * and {@link JournalStepDetailController#delete(UUID)} actions.
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
		JournalStepDetailTO obj = new JournalStepDetailTO(UUID.randomUUID(),
				testString1,
				testString2); // NOPMD
		try {
			obj = controller.create(obj);
			fail("Calling create with an object with an ID should have thrown a validation excpetion.");
		} catch (final ValidationException exc) {
			/* expected */
		}

		// Now create a valid JournalStepDetail
		obj = new JournalStepDetailTO(null, testString1, testString2); // NOPMD
		obj = controller.create(obj);

		assertNotNull(
				"Returned JournalStepDetailTO from the controller should not have been null.",
				obj);
		assertNotNull(
				"Returned JournalStepDetailTO.ID from the controller should not have been null.",
				obj.getId());
		assertEquals(
				"Returned JournalStepDetailTO.Name from the controller did not match.",
				testString1, obj.getName());
		assertEquals(
				"Returned JournalStepDetailTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, obj.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(obj.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link JournalStepDetailController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<JournalStepDetailTO> list = controller.getAll(
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