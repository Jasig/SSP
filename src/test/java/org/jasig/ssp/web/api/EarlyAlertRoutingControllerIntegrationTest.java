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
package org.jasig.ssp.web.api; // NOPMD by jon.adams because integration test can have many imports

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.EarlyAlertRoutingTO;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.jasig.ssp.transferobject.ServiceResponse;
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
 * {@link EarlyAlertRoutingController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class EarlyAlertRoutingControllerIntegrationTest {

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final UUID CAMPUS_ID = UUID
			.fromString("901e104b-4dc7-43f5-a38e-581015e204e1");

	private static final UUID EARLY_ALERT_REASON_ID = UUID
			.fromString("b2d11335-5056-a51a-80ea-074f8fef94ea");

	@Autowired
	private transient EarlyAlertRoutingController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test that the {@link EarlyAlertRoutingController#get(UUID, UUID)} action
	 * returns the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ObjectNotFoundException
	 *             If data could not be loaded; expected for this test.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final EarlyAlertRoutingTO obj = controller.get(CAMPUS_ID,
				UUID.randomUUID());

		assertNull(
				"Returned EarlyAlertRoutingTO from the controller should have been null.",
				obj);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testControllerMismatchingCampusIds()
			throws ObjectNotFoundException, ValidationException {
		// arrange
		final EarlyAlertRouting model = new EarlyAlertRouting();
		model.setCampus(new Campus(CAMPUS_ID));

		// act
		controller.save(CAMPUS_ID, UUID.randomUUID(), new EarlyAlertRoutingTO(
				model));

		// assert
		fail("IllegalArgumentException should have been thrown with campus identifiers do not match.");
	}

	/**
	 * Test the
	 * {@link EarlyAlertRoutingController#getAll(UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If referenced Person could not be found. Should not be thrown
	 *             for this test.
	 */
	@Test
	public void testControllerAll() throws ObjectNotFoundException {
		final Collection<EarlyAlertRoutingTO> list = controller.getAll(
				CAMPUS_ID, ObjectStatus.ACTIVE, null, null, null, null)
				.getRows();

		assertNotNull("List should not have been null.", list);
	}

	/**
	 * Test the
	 * {@link EarlyAlertRoutingController#create(UUID, EarlyAlertRoutingTO)} and
	 * {@link EarlyAlertRoutingController#delete(UUID, UUID)} actions.
	 * 
	 * @throws ValidationException
	 *             Thrown if data is not valid. Should not be thrown for this
	 *             test.
	 * @throws ObjectNotFoundException
	 *             Thrown if lookup data could not be found. Should not be
	 *             thrown for this test.
	 */
	@Test()
	public void testControllerCreateAndDelete() throws ObjectNotFoundException,
			ValidationException {
		// Now create EarlyAlertRouting for testing
		final EarlyAlertRoutingTO obj = new EarlyAlertRoutingTO();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setCampusId(CAMPUS_ID);
		obj.setEarlyAlertReasonId(EARLY_ALERT_REASON_ID);
		obj.setPerson(new PersonLiteTO(PERSON_ID, "", ""));
		obj.setGroupName("NAME");

		final EarlyAlertRoutingTO saved = controller.create(CAMPUS_ID, obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);
		assertEquals("PersonIds did not match.", PERSON_ID, saved.getPerson()
				.getId());

		session.clear();

		final ServiceResponse response = controller.delete(CAMPUS_ID, savedId);

		assertNotNull("Deletion response should not have been null.",
				response);
		assertTrue("Deletion response did not return success.",
				response.isSuccess());

		try {
			// ObjectNotFoundException expected at this point
			final EarlyAlertRoutingTO afterDeletion = controller.get(savedId,
					PERSON_ID);
			assertNull(
					"Instance should not be able to get loaded after it has been deleted.",
					afterDeletion);
		} catch (final ObjectNotFoundException exc) { // NOPMD by jon.adams
			// expected
		}
	}

	/**
	 * Test the
	 * {@link EarlyAlertRoutingController#create(UUID, EarlyAlertRoutingTO)}
	 * action auto-fills the reference IDs from the path.
	 * 
	 * @throws ValidationException
	 *             Thrown if data is not valid. Should not be thrown for this
	 *             test.
	 * @throws ObjectNotFoundException
	 *             Thrown if lookup data could not be found. Should not be
	 *             thrown for this test.
	 */
	@Test()
	public void testControllerCreateWithMissingCampus()
			throws ObjectNotFoundException,
			ValidationException {
		// Now create EarlyAlertRouting for testing
		final EarlyAlertRoutingTO obj = new EarlyAlertRoutingTO();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setEarlyAlertReasonId(EARLY_ALERT_REASON_ID);
		obj.setPerson(new PersonLiteTO(PERSON_ID, "", ""));
		obj.setGroupName("NAME");

		final EarlyAlertRoutingTO saved = controller.create(CAMPUS_ID, obj);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final UUID savedId = saved.getId();
		assertNotNull("Saved instance identifier should not have been null.",
				savedId);

		session.clear();

		final EarlyAlertRoutingTO reloaded = controller.get(CAMPUS_ID, savedId);

		assertEquals("Campus was not filled automatically.", CAMPUS_ID,
				reloaded.getCampusId());
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