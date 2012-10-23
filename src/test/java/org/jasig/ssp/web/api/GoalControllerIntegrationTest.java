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
package org.jasig.ssp.web.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;
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
 * {@link GoalController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class GoalControllerIntegrationTest {

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final UUID CONFIDENTIALITY_LEVEL_ID = UUID
			.fromString("afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c");

	@Autowired
	private transient GoalController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				confidentialityLevelService
						.confidentialityLevelsAsGrantedAuthorities(),
				"ROLE_PERSON_GOAL_READ", "ROLE_PERSON_GOAL_WRITE",
				"ROLE_PERSON_GOAL_DELETE");

	}

	/**
	 * Test that the {@link GoalController#get(UUID, UUID)} action returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 * 
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final GoalTO obj = controller.get(PERSON_ID,
				UUID.randomUUID());

		assertNull(
				"Returned GoalTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link GoalController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerAll() throws ObjectNotFoundException {
		final Collection<GoalTO> list = controller.getAll(PERSON_ID,
				ObjectStatus.ACTIVE, null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
	}

	/**
	 * Test the {@link GoalController#create(UUID, GoalTO)} and
	 * {@link GoalController#delete(UUID, UUID)} actions.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test()
	public void testControllerCreateAndDelete() throws ValidationException,
			ObjectNotFoundException {
		// Now create Goal for testing
		final GoalTO obj = new GoalTO();
		obj.setName("NAME");
		obj.setPersonId(PERSON_ID);
		obj.setConfidentialityLevel(new ConfidentialityLevelLiteTO(
				CONFIDENTIALITY_LEVEL_ID, ""));
		obj.setObjectStatus(ObjectStatus.ACTIVE);

		final GoalTO saved = controller.create(PERSON_ID, obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);
		assertEquals("PersonIds did not match.", PERSON_ID, saved.getPersonId());

		session.clear();

		final ServiceResponse response = controller.delete(savedId, PERSON_ID);

		assertNotNull("Deletion response should not have been null.",
				response);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());

		try {
			// ObjectNotFoundException expected at this point
			final GoalTO afterDeletion = controller.get(savedId,
					PERSON_ID);
			assertNull(
					"Instance should not be able to get loaded after it has been deleted.",
					afterDeletion);
		} catch (final ObjectNotFoundException exc) { // NOPMD by jon.adams
			// expected
		}
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