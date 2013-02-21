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
package org.jasig.ssp.service.tool; // NOPMD because it's an integration test

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDisability;
import org.jasig.ssp.model.PersonDisabilityAgency;
import org.jasig.ssp.model.reference.DisabilityAgency;
import org.jasig.ssp.model.reference.DisabilityStatus;
import org.jasig.ssp.model.tool.AccommodationForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.DisabilityStatusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * AccommodationService integration tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class AccommodationServiceIntegrationTest {

	@Autowired
	private transient AccommodationService service;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient PersonDao personDao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient DisabilityStatusService disabilityStatusService;

	private static final String LASTNAME = "last";

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString2";

	private static final String TEST_STRING3 = "testString3";

	private static final UUID DISABILITY_STATUS_ID = UUID
			.fromString("e0208429-aeb2-4854-ab7c-3c9281c96002"); // Eligible

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testAccommodationServiceFormObjectNotFoundException()
			throws ObjectNotFoundException {
		service.loadForPerson(UUID.randomUUID());
	}

	/**
	 * Integration test that loads and asserts an
	 * {@link org.jasig.ssp.model.tool.AccommodationForm} for the
	 * administrator user.
	 * 
	 * @throws ObjectNotFoundException
	 *             Thrown if the special administrator user is not found.
	 */
	@Test
	public void testAccommodationServiceFromLoadForPersonFromDatabaseForAdminUser()
			throws ObjectNotFoundException {
		final AccommodationForm form = service
				.loadForPerson(Person.SYSTEM_ADMINISTRATOR_ID);
		assertNotNull("Admin user could not be loaded.", form.getPerson());
		assertNotNull("Admin user loaded but was missing the Person instance.",
				form.getPerson());
		assertEquals("Admin user loaded but identifiers did not match.",
				Person.SYSTEM_ADMINISTRATOR_ID, form.getPerson().getId());

		assertNull(
				"Admin user loaded but included a PersonDisability instance even though it should not have.",
				form.getPerson().getDisability());
	}

	/**
	 * Massive integration test that creates, fills, and asserts the correct
	 * insertion of a {@link AccommodationService#save(org.jasig.ssp.model.tool.AccommodationForm)} all the way
	 * through the service, DAO, and model layers.
	 * 
	 * @throws ObjectNotFoundException
	 *             Thrown if any of the expected test data identifiers are not
	 *             found in the database.
	 */
	@Test
	public void testAccommodationServiceForNewUser() throws ObjectNotFoundException { // NOPMD
		// From test database, see the test liquibase XML
		final UUID testDisabilityAgencyId = UUID
				.fromString("7f92b5bb-8e9c-44c7-88fd-2ffdce68ef98");
		
		// Setup - create a new blank Person
		Person person = new Person();
		person.setFirstName("first");
		person.setLastName(LASTNAME);
		person.setUsername("username");
		person.setSchoolId("school id");
		person.setPrimaryEmailAddress("email");
		person.setAddressLine1("address line 1");
		person.setCellPhone("867-5309");

		// Save new person

		person = personService.create(person);

		final UUID id = person.getId();

		// reload person from database to make sure create() worked
		person = personService.get(id);

		assertNotNull("New person did not save and reload correctly", person);

		// initialize the AccommodationForm with the recently created Person
		// instance
		AccommodationForm form = service.loadForPerson(id);

		assertNotNull("AccommodationForm could not be initialized correctly.", form);
		assertNotNull("Recently created user could not be loaded.",
				form.getPerson());

		person = form.getPerson(); // refresh

		// Setup - fill the AccommodationForm with test data

		final PersonDisability pd1 = new PersonDisability();
		final DisabilityStatus ds1 = disabilityStatusService.get(DISABILITY_STATUS_ID);
		pd1.setContactName(TEST_STRING1);
		pd1.setDisabilityStatus(ds1);
		person.setDisability(pd1);

		final PersonDisabilityAgency pda1 = new PersonDisabilityAgency();
		pda1.setDisabilityAgency(new DisabilityAgency(testDisabilityAgencyId));
		pda1.setDescription(TEST_STRING3);
		person.getDisabilityAgencies().add(pda1);

		// Run
		assertTrue("AccommodationService did not return success (true).",
				service.save(form));

		// Re-load form
		form = service.loadForPerson(id);

		assertNotNull("AccommodationForm could not be initialized correctly.", form);
		assertNotNull("Recently created user could not be loaded.",
				form.getPerson());

		person = form.getPerson();

		// Check that all the persisted values match
		assertNotNull("Disability data did not exist.",
				person.getDisability());
		assertEquals("Disability.contactName did not match.", TEST_STRING1, form
				.getPerson().getDisability().getContactName());
		assertEquals("Disability.disabilityStatus.id did not match.",
				DISABILITY_STATUS_ID, person.getDisability().getDisabilityStatus().getId());

		// Assert DisabilityAgency data
		final Set<PersonDisabilityAgency> testAgencies = person.getDisabilityAgencies();
		assertNotNull("DisabilityAgency data did not exist.", testAgencies);
		assertEquals(
				"DisabilityAgency data did not contain the expected 1 element.",
				1, testAgencies.size());
		final PersonDisabilityAgency pda3 = testAgencies.iterator().next();
		assertNotNull("DisabilityAgency (1) data did not exist.", pda3);
		assertEquals("DisabilityAgency (1) Description did not match.",
				TEST_STRING3, pda3.getDescription());

		// Remove Person completely (not just mark deleted) which should
		// delete all child objects created by the AccommodationService
		personDao.delete(person);

		try {
			personService.get(id);
			fail("Person was not deleted correctly."); // NOPMD because we don't
			// want a possible earlier ObjectNotFoundException to be assumed
			// as test success
		} catch (final ObjectNotFoundException exc) { // NOPMD
			/* expected */
		}
	}
}