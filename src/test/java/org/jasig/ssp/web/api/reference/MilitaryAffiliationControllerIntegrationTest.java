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
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.reference.MilitaryAffiliationTO;
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
 * MilitaryAffiliation controller tests
 * 
 * @author TemplateAuthorName
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class MilitaryAffiliationControllerIntegrationTest {

	@Autowired
	private transient MilitaryAffiliationController controller;

	private static final UUID MILITARY_AFFILIATION_ID = UUID
			.fromString("350b9f90-b78f-430f-ac08-27b56b424bb1");

	private static final String MILITARY_AFFILIATION_NAME = "Test Military Affiliation";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString1";

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
	 * Test the {@link MilitaryAffiliationController#get(UUID)} action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final MilitaryAffiliationTO obj = controller.get(MILITARY_AFFILIATION_ID);

		assertNotNull(
				"Returned MilitaryAffiliationTO from the controller should not have been null.",
				obj);

		assertEquals("Returned MilitaryAffiliation.Name did not match.",
				MILITARY_AFFILIATION_NAME,
				obj.getName());
	}

	/**
	 * Test that the {@link MilitaryAffiliationController#get(UUID)} action returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final MilitaryAffiliationTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned MilitaryAffiliationTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the {@link MilitaryAffiliationController#create(MilitaryAffiliationTO)} and
	 * {@link MilitaryAffiliationController#delete(UUID)} actions.
	 * 
	 * @throws ObjectNotFoundException
	 *             If lookup data can not be found.
	 * @throws ValidationException
	 *             If there are any validation errors.
	 */
	@Test
	public void testControllerCreateAndDelete() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		// Check validation of 'no ID for create()'
		try {
			controller.create(new MilitaryAffiliationTO(UUID
					.randomUUID(), TEST_STRING1,
					TEST_STRING2));
			fail("Calling create with an object with an ID should have thrown a validation excpetion."); // NOPMD
		} catch (final ValidationException exc) { // NOPMD
			/* expected */
		}

		// Now create a valid MilitaryAffiliation
		final MilitaryAffiliationTO saved = controller.create(new MilitaryAffiliationTO(null,
				TEST_STRING1, TEST_STRING2));

		assertNotNull(
				"Returned MilitaryAffiliationTO from the controller should not have been null.",
				saved);
		assertNotNull(
				"Returned MilitaryAffiliationTO.ID from the controller should not have been null.",
				saved.getId());
		assertEquals(
				"Returned MilitaryAffiliationTO.Name from the controller did not match.",
				TEST_STRING1, saved.getName());
		assertEquals(
				"Returned MilitaryAffiliationTO.CreatedBy was not correctly auto-filled for the current user (the administrator in this test suite).",
				Person.SYSTEM_ADMINISTRATOR_ID, saved.getCreatedBy().getId());

		assertTrue("Delete action did not return success.",
				controller.delete(saved.getId()).isSuccess());
	}

	/**
	 * Test the
	 * {@link MilitaryAffiliationController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<MilitaryAffiliationTO> list = controller.getAll(
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
