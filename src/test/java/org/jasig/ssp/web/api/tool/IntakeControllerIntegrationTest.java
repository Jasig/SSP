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

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.PersonEducationGoal;
import org.jasig.ssp.model.PersonEducationLevel;
import org.jasig.ssp.model.reference.ChildCareArrangement;
import org.jasig.ssp.model.reference.Citizenship;
import org.jasig.ssp.model.reference.EducationGoal;
import org.jasig.ssp.model.reference.EducationLevel;
import org.jasig.ssp.model.reference.EmploymentShifts;
import org.jasig.ssp.model.reference.Ethnicity;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.model.reference.MaritalStatus;
import org.jasig.ssp.model.reference.MilitaryAffiliation;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.tool.IntakeService;
import org.jasig.ssp.transferobject.PersonDemographicsTO;
import org.jasig.ssp.transferobject.PersonEducationGoalTO;
import org.jasig.ssp.transferobject.PersonEducationLevelTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.transferobject.tool.IntakeFormTO;
import org.jasig.ssp.util.service.stub.Stubs;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * {@link IntakeController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class IntakeControllerIntegrationTest {

	@Autowired
	private transient IntakeController controller;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient PersonTOFactory personTOFactory;

	@Autowired
	private transient IntakeService intakeService;

	private static final UUID STUDENT_ID = UUID
			.fromString("7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194");

	private static final String STUDENT_FIRSTNAME = "Dennis";

	private static final UUID EDUCATION_LEVEL_ID = UUID
			.fromString("459F4CB3-2274-4F47-B757-68B987F8707E");

	private static final UUID EDUCATION_GOAL_ID = UUID
			.fromString("5CCCDCA1-9A73-47E8-814F-134663A2AE67");

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
	 * Test the {@link IntakeController#load(UUID)} action.
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

		final IntakeFormTO obj = controller.load(STUDENT_ID);

		assertNotNull(
				"Returned IntakeFormTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Person.FirstName did not match.",
				STUDENT_FIRSTNAME, obj.getPerson().getFirstName());
	}

	/**
	 * Test the {@link IntakeController#save(UUID, IntakeFormTO)} action.
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
		final IntakeFormTO obj = new IntakeFormTO();
		final Person person = personService.get(STUDENT_ID);
		obj.setPerson(personTOFactory.from(person));

		person.setAnticipatedStartTerm("spring");
		person.setAnticipatedStartYear(2012);
		person.setActualStartTerm("spring");
		person.setActualStartYear(2012);
		person.setAbilityToBenefit(true);

		final PersonDemographics personDemographics = new PersonDemographics();
		personDemographics.setChildAges("age 1");
		personDemographics.setChildCareArrangement(new ChildCareArrangement());
		personDemographics.setChildCareNeeded(true);
		personDemographics.setCitizenship(new Citizenship());
		personDemographics.setCountryOfCitizenship("Brazil");
		personDemographics.setCountryOfResidence("San Diego");
		personDemographics.setEmployed(true);
		personDemographics.setEthnicity(new Ethnicity());
		personDemographics.setGender(Genders.F);
		personDemographics.setLocal(true);
		personDemographics.setMaritalStatus(new MaritalStatus());
		personDemographics.setMilitaryAffiliation(new MilitaryAffiliation());
		personDemographics.setNumberOfChildren(Integer.MAX_VALUE);
		personDemographics.setPaymentStatus("paymentStatus");
		personDemographics.setPlaceOfEmployment("Chili's");
		personDemographics.setPrimaryCaregiver(true);
		personDemographics.setShift(EmploymentShifts.SECOND);
		personDemographics.setTotalHoursWorkedPerWeek("40");
		personDemographics.setVeteranStatus(new VeteranStatus());
		personDemographics.setWage("435246246");

		obj.setPersonDemographics(new PersonDemographicsTO(
				personDemographics));

		final List<PersonEducationLevelTO> personEducationLevels = Lists
				.newArrayList();
		final PersonEducationLevel personEducationLevel = new PersonEducationLevel();
		personEducationLevel.setEducationLevel(new EducationLevel(
				EDUCATION_LEVEL_ID));
		personEducationLevel.setPerson(person);
		personEducationLevel.setGraduatedYear("2008");
		personEducationLevel.setHighestGradeCompleted("4");
		personEducationLevel.setLastYearAttended("2011");
		personEducationLevel.setDescription("description");
		personEducationLevel.setSchoolName("School name");
		personEducationLevels.add(new PersonEducationLevelTO(
				personEducationLevel));

		obj.setPersonEducationLevels(personEducationLevels);

		final PersonEducationGoal personEducationGoal = new PersonEducationGoal();
		personEducationGoal.setEducationGoal(new EducationGoal(
				EDUCATION_GOAL_ID));
		personEducationGoal.setHowSureAboutMajor(1);
		personEducationGoal.setMilitaryBranchDescription("mb");
		personEducationGoal.setDescription("description");
		personEducationGoal.setPlannedOccupation("fd");
		personEducationGoal.setPlannedMajor("Computer Science");
		personEducationGoal.setCareerDecided(false);
		personEducationGoal.setHowSureAboutOccupation(2);
		personEducationGoal.setConfidentInAbilities(true);
		personEducationGoal
				.setAdditionalAcademicProgramInformationNeeded(false);

		obj.setPersonEducationGoal(new PersonEducationGoalTO(
				personEducationGoal));

		// act
		final ServiceResponse response = controller.save(STUDENT_ID, obj);

		// assert
		assertNotNull(
				"Returned IntakeFormTO from the controller should not have been null.",
				response);

		assertTrue("Result should have returned success.",
				response.isSuccess());
	}

	/**
	 * Test the {@link IntakeController#referenceData()} action.
	 * 
	 * This test assumes that there is at least 1 valid, active
	 * ChallengeReferral in the test database.
	 * @throws ObjectNotFoundException 
	 */
	@Test
	public void testControllerRefData() throws ObjectNotFoundException {

		final IntakeFormTO intakeFormTO = new IntakeFormTO(
				intakeService.loadForPerson(Stubs.PersonFixture.STUDENT_0.id()));

		final Map<String, Object> data = controller.referenceData(intakeFormTO);

		assertNotNull("The map should not have been null.", data);

		@SuppressWarnings("unchecked")
		final List<ChallengeTO> challenges = (List<ChallengeTO>) data
				.get("challenges");
		assertNotNull("The Challenges should not have been null.", challenges);

		for (final ChallengeTO challenge : challenges) {
			for (final ChallengeReferralTO referral : challenge
					.getChallengeChallengeReferrals()) {
				assertTrue(
						"All Referrals with !ShowInStudentIntake should not have been returned.",
						referral.getShowInStudentIntake());
			}
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