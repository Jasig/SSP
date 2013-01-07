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
package org.jasig.ssp.web.api.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDisability;
import org.jasig.ssp.model.PersonDisabilityAgency;
import org.jasig.ssp.transferobject.PersonDisabilityAgencyTO;
import org.jasig.ssp.model.reference.DisabilityAgency;
import org.jasig.ssp.model.reference.DisabilityStatus;
import org.jasig.ssp.transferobject.reference.DisabilityAgencyTO;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.PersonDisabilityTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.tool.DisabilityIntakeFormTO;
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

import com.google.common.collect.Lists;

/**
 * {@link DisabilityIntakeController} tests
 * 
 * @author shawn.gormley
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class DisabilityIntakeControllerIntegrationTest {

	@Autowired
	private transient DisabilityIntakeController controller;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient PersonTOFactory personTOFactory;

	private static final UUID STUDENT_ID = UUID
			.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194");

	private static final UUID DISABILITY_AGENCY_ID = UUID
			.fromString("7f92b5bb-8e9c-44c7-88fd-2ffdce68ef98");	
	
	private static final String STUDENT_FIRSTNAME = "Dennis";

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
	 * Test the {@link DisabilityIntakeController#load(UUID)} action.
	 * 
	 * @throws ObjectNotFoundException
	 *             Thrown if any of the expected test data identifiers are not
	 *             found in the database.
	 */
	@Test
	public void testControllerLoad() throws ObjectNotFoundException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final DisabilityIntakeFormTO obj = controller.load(STUDENT_ID);

		assertNotNull(
				"Returned DisabilityIntakeFormTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Person.FirstName did not match.",
				STUDENT_FIRSTNAME, obj.getPerson().getFirstName());
	}

	/**
	 * Test the {@link DisabilityIntakeController#save(UUID, DisabilityIntakeFormTO)} action.
	 * 
	 * @throws ObjectNotFoundException
	 *             Thrown if lookup data could not be found.
	 * @throws ValidationException
	 *             If data was invalid.
	 */
	@Test
	public void testControllerSave() throws ObjectNotFoundException,
			ValidationException {
		// arrange
		final DisabilityIntakeFormTO obj = new DisabilityIntakeFormTO();
		final Person person = personService.get(STUDENT_ID);
		obj.setPerson(personTOFactory.from(person));

		person.setAnticipatedStartTerm("spring");
		person.setAnticipatedStartYear(2012);
		person.setActualStartTerm("spring");
		person.setActualStartYear(2012);

		final PersonDisability personDisability = new PersonDisability();
		personDisability.setIntakeCounselor("John Counselor");
		personDisability.setReferredBy("BVR");
		personDisability.setContactName("Test contact");
		personDisability.setDisabilityStatus(new DisabilityStatus());
		personDisability.setReleaseSigned(true);
		personDisability.setRecordsRequested(true);
		personDisability.setRecordsRequestedFrom("John Doe");
		personDisability.setReferForScreening(false);
		personDisability.setDocumentsRequestedFrom("Jane Doe");
		personDisability.setRightsAndDuties("Test rights and duties");
		personDisability.setEligibleLetterSent(false);
		personDisability.setEligibleLetterDate(new Date());
		personDisability.setIneligibleLetterSent(false);
		personDisability.setIneligibleLetterDate(new Date());
		personDisability.setNoDocumentation(false);
		personDisability.setInadequateDocumentation(false);
		personDisability.setNoDisability(false);
		personDisability.setNoSpecialEd(false);
		personDisability.setTempEligibilityDescription("Test temp eligibility desc");
		personDisability.setOnMedication(false);
		personDisability.setMedicationList("Test medication list");
		personDisability.setFunctionalLimitations("Test functional limitations");

		obj.setPersonDisability(new PersonDisabilityTO(
				personDisability));

		final List<PersonDisabilityAgencyTO> personDisabilityAgencies = Lists
				.newArrayList();
		final PersonDisabilityAgency personDisabilityAgency = new PersonDisabilityAgency();
		personDisabilityAgency.setDisabilityAgency(new DisabilityAgency(
				DISABILITY_AGENCY_ID));
		personDisabilityAgency.setPerson(person);
		personDisabilityAgency.setDescription("description");

		obj.setPersonDisabilityAgencies(personDisabilityAgencies);

		// act
		final ServiceResponse response = controller.save(STUDENT_ID, obj);

		// assert
		assertNotNull(
				"Returned DisabilityIntakeFormTO from the controller should not have been null.",
				response);

		assertTrue("Result should have returned success.",
				response.isSuccess());
	}

	/**
	 * Test the {@link DisabilityIntakeController#referenceData()} action.
	 * 
	 * This test assumes that there is at least 1 valid, active
	 * DisabilityAgency in the test database.
	 */
	@Test
	public void testControllerRefData() {
		final Map<String, Object> data = controller.referenceData();

		assertNotNull("The map should not have been null.", data);

		@SuppressWarnings("unchecked")
		final List<DisabilityAgencyTO> agencies = (List<DisabilityAgencyTO>) data
				.get("disabilityAgencies");
		assertNotNull("The Disability Agencies should not have been null.", agencies);
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