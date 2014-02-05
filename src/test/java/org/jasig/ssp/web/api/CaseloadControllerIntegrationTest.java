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
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.PersonSearchResult2TO;
import org.jasig.ssp.transferobject.PagedResponse;
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
 * {@link CaseloadController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration()
@Transactional
public class CaseloadControllerIntegrationTest {

	private static final UUID ADVISOR_ID = UUID
			.fromString("252de4a0-7c06-4254-b7d8-4ffc02fe81ff");

	@Autowired
	private transient CaseloadController controller;

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	protected transient ProgramStatusService programStatusService;

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
				"ROLE_PERSON_Caseload_READ", "ROLE_PERSON_Caseload_WRITE",
				"ROLE_PERSON_Caseload_DELETE");

	}

	/**
	 * Test that the
	 * {@link CaseloadController#caseloadFor(UUID, UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action returns the correct validation errors when an invalid ID is sent.
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
		// arrange, act
		controller
				.caseloadFor(UUID.randomUUID(), null, null, 0, 10, null, null);

		// assert
		fail("Exception should have been thrown for missing person identifier.");
	}

	/**
	 * Test the
	 * {@link CaseloadController#caseloadFor(UUID, UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCaseloadFor() throws ObjectNotFoundException {
		final Collection<PersonSearchResult2TO> list = controller.caseloadFor(
				ADVISOR_ID, null, ObjectStatus.ACTIVE, null, null, null, null)
				.getRows();

		assertEquals("List should have had 0 rows.", 2, list.size());
	}

	/**
	 * Test the
	 * {@link CaseloadController#caseloadFor(UUID, UUID, ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerCaseloadForProgramStatusFilter()
			throws ObjectNotFoundException {
		final PagedResponse<PersonSearchResult2TO> result = controller
				.caseloadFor(ADVISOR_ID,
						programStatusService.get(ProgramStatus.TRANSITIONED_ID)
								.getId(), ObjectStatus.ACTIVE, null, null,
						null, null);
		final Collection<PersonSearchResult2TO> list = result.getRows();

		assertEquals("Filtered list should have 2 results.", 0,
				result.getResults());
		assertEquals("Filtered list should have 2 results", 0, list.size());
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