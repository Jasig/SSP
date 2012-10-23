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
import org.jasig.ssp.transferobject.reference.EducationGoalTO;
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
 * EducationGoal controller tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class EducationGoalControllerIntegrationTest {

	@Autowired
	private transient EducationGoalController controller;

	private static final UUID EDUCATIONGOAL_ID = UUID
			.fromString("78b54da7-fb19-4092-bb44-f60485678d6b");

	private static final String EDUCATIONGOAL_NAME = "Other";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString2";

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
	 * Test the {@link EducationGoalController#get(UUID)} action.
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

		final EducationGoalTO obj = controller.get(EDUCATIONGOAL_ID);

		assertNotNull(
				"Returned EducationGoalTO from the controller should not have been null.",
				obj);

		assertEquals("Returned EducationGoal.Name did not match.",
				EDUCATIONGOAL_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link EducationGoalController#get(UUID)} action returns
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

		final EducationGoalTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned EducationGoalTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link EducationGoalController#create(EducationGoalTO)} and
	 * {@link EducationGoalController#delete(UUID)} actions.
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
		final EducationGoalTO educationGoal = new EducationGoalTO(
				UUID.randomUUID(),
				TEST_STRING1, TEST_STRING2);
		try {
			controller.create(educationGoal);
			fail("Calling create with an object with an ID should have thrown a validation excpetion."); // NOPMD
		} catch (final ValidationException exc) { // NOPMD
			/* expected */
		}

		// Now create a valid EducationGoal
		final EducationGoalTO toSave = new EducationGoalTO(null, TEST_STRING1,
				TEST_STRING2);
		final EducationGoalTO saved = controller.create(toSave);

		assertNotNull(
				"Returned EducationGoalTO from the controller should not have been null.",
				saved);
		assertNotNull(
				"Returned EducationGoalTO.ID from the controller should not have been null.",
				saved.getId());
		assertEquals(
				"Returned EducationGoalTO.Name from the controller did not match.",
				TEST_STRING1, saved.getName());
		assertEquals(
				"Returned EducationGoalTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, saved.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(saved.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link EducationGoalController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<EducationGoalTO> list = controller.getAll(
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