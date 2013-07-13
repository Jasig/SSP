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
package org.jasig.ssp.service.impl

import org.hibernate.SessionFactory
import org.jasig.ssp.model.*
import org.jasig.ssp.model.reference.*
import org.jasig.ssp.service.PersonChallengeService
import org.jasig.ssp.service.PersonDemographicsService
import org.jasig.ssp.service.PersonService
import org.jasig.ssp.service.reference.*
import org.jasig.ssp.util.sort.SortingAndPaging
import org.jasig.ssp.web.api.tool.IntakeController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * The "G" is in the name b/c there's already a Java
 * {@code IntakeControllerIntegrationTest}
 */
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
@Transactional
class IntakeControllerGIntegrationTest extends Specification {

	@Autowired
	IntakeController intakeController

	@Autowired
	PersonService personService

	@Autowired
	PersonChallengeService personChallengeService

	@Autowired
	ChallengeService challengeService

	@Autowired
	ChildCareArrangementService childCareArrangementService

	@Autowired
	CitizenshipService citizenshipService

	@Autowired
	PersonDemographicsService personDemographicsService

	@Autowired
	EducationGoalService educationGoalService

	@Autowired
	EducationLevelService educationLevelService

	@Autowired
	EthnicityService ethnicityService

	@Autowired
	FundingSourceService fundingSourceService

	@Autowired
	MaritalStatusService maritalStatusService

	@Autowired
	MilitaryAffiliationService militaryAffiliationService

	@Autowired
	StudentStatusService studentStatusService

	@Autowired
	VeteranStatusService veteranStatusService

	@Autowired
	SecurityServiceInTestEnvironment securityService;

	@Autowired
	SessionFactory sessionFactory;

	def setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID))
	}

	def cleanup() {
		securityService.setCurrent(new Person())
	}

	def "inactive but already associated challenges still selectable"() {
		given: "a person with an inactive challenge"
		def person = createPerson()
		def inactiveChallenge1 = challengeService.create(new Challenge(name: "inactive challenge 1",
				description: "inactive challenge description 1",
				showInStudentIntake: true,
				objectStatus: ObjectStatus.INACTIVE))
		person.challenges.add(new PersonChallenge(person: person,
				challenge: inactiveChallenge1))
		person = personService.save(person)

		and: "several other challenges in the db that this test is completely in control of"
		def inactiveChallenge2 = challengeService.create(new Challenge(name: "inactive challenge 2",
				description: "inactive challenge description 2",
				showInStudentIntake: true,
				objectStatus: ObjectStatus.INACTIVE))
		def activeChallenge = challengeService.create(new Challenge(name: "active challenge",
				description: "active challenge description",
				showInStudentIntake: true,
				objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable challenges include the inactive challenge"
		idsOf(intake.referenceData["challenges"]).contains(inactiveChallenge1.id)
		!(idsOf(intake.referenceData["challenges"]).contains(inactiveChallenge2.id))
		idsOf(intake.referenceData["challenges"]).contains(activeChallenge.id)
	}

	def "inactive but already associated child care arrangements still selectable"() {
		given: "a person with an inactive child care arrangement"
		def person = createPerson()
		def inactiveArrangement1 = childCareArrangementService.create(
				new ChildCareArrangement(name: "inactive arrangement 1",
						description: "inactive arrangement description 1",
						objectStatus: ObjectStatus.INACTIVE))
		person.demographics =
			personDemographicsService.create(new PersonDemographics(
					childCareArrangement: inactiveArrangement1))

		and: "several other child care arrangements in the db that this test is completely in control of"
		def inactiveArrangement2 = childCareArrangementService.create(
				new ChildCareArrangement(name: "inactive arrangement 2",
						description: "inactive arrangement description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeArrangement = childCareArrangementService.create(
				new ChildCareArrangement(name: "active arrangement",
						description: "active arrangement description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable child care arrangements include the inactive child care arrangement"
		idsOf(intake.referenceData["childCareArrangements"]).contains(inactiveArrangement1.id)
		!(idsOf(intake.referenceData["childCareArrangements"]).contains(inactiveArrangement2.id))
		idsOf(intake.referenceData["childCareArrangements"]).contains(activeArrangement.id)
	}

	def "inactive but already associated citizenship still selectable"() {
		given: "a person with an inactive citizenship"
		def person = createPerson()
		def inactiveCitizenship1 = citizenshipService.create(
				new Citizenship(name: "inactive citizenship 1",
						description: "inactive citizenship description 1",
						objectStatus: ObjectStatus.INACTIVE))
		person.demographics =
			personDemographicsService.create(new PersonDemographics(
					citizenship: inactiveCitizenship1))

		and: "several other citizenships in the db that this test is completely in control of"
		def inactiveCitizenship2 = citizenshipService.create(
				new Citizenship(name: "inactive citizenship 2",
						description: "inactive citizenship description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeCitizenship = citizenshipService.create(
				new Citizenship(name: "active citizenship ",
						description: "active citizenship description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable citizenships include the inactive citizenship"
		idsOf(intake.referenceData["citizenships"]).contains(inactiveCitizenship1.id)
		!(idsOf(intake.referenceData["citizenships"]).contains(inactiveCitizenship2.id))
		idsOf(intake.referenceData["citizenships"]).contains(activeCitizenship.id)
	}

	def "inactive but already associated edu goals still selectable"() {
		given: "a person with an inactive edu goal"
		def person = createPerson()
		def inactiveEduGoal1 = educationGoalService.create(
				new EducationGoal(name: "inactive edu goal 1",
					description: "inactive edu goal description 1",
					objectStatus: ObjectStatus.INACTIVE))
		person.educationGoal = new PersonEducationGoal(educationGoal: inactiveEduGoal1)
		person = personService.save(person)

		and: "several other edu goals in the db that this test is completely in control of"
		def inactiveEduGoal2 = educationGoalService.create(
				new EducationGoal(name: "inactive edu goal 2",
						description: "inactive edu goal description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeEduGoal = educationGoalService.create(
				new EducationGoal(name: "active edu goal",
						description: "active edu goal description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable edu goals include the inactive edu goal"
		idsOf(intake.referenceData["educationGoals"]).contains(inactiveEduGoal1.id)
		!(idsOf(intake.referenceData["educationGoals"]).contains(inactiveEduGoal2.id))
		idsOf(intake.referenceData["educationGoals"]).contains(activeEduGoal.id)
	}

	def "inactive but already associated edu levels still selectable"() {
		given: "a person with an inactive edu level"
		def person = createPerson()
		def inactiveEduLevel1 = educationLevelService.create(
				new EducationLevel(name: "inactive edu level 1",
						description: "inactive edu level description 1",
						objectStatus: ObjectStatus.INACTIVE))
		person.educationLevels.add(new PersonEducationLevel(
				educationLevel: inactiveEduLevel1,
				person: person))
		person = personService.save(person)

		and: "several other edu levels in the db that this test is completely in control of"
		def inactiveEduLevel2 = educationLevelService.create(
				new EducationLevel(name: "inactive edu level 2",
						description: "inactive edu level description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeEduLevel = educationLevelService.create(
				new EducationLevel(name: "active edu level",
						description: "active edu level description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable edu levels include the inactive edu level"
		idsOf(intake.referenceData["educationLevels"]).contains(inactiveEduLevel1.id)
		!(idsOf(intake.referenceData["educationLevels"]).contains(inactiveEduLevel2.id))
		idsOf(intake.referenceData["educationLevels"]).contains(activeEduLevel.id)
	}

	def "inactive but already associated ethnicity still selectable"() {
		given: "a person with an inactive ethnicity"
		def person = createPerson()
		def inactiveEthnicity1 = ethnicityService.create(
				new Ethnicity(name: "inactive ethnicity 1",
						description: "inactive ethnicity description 1",
						objectStatus: ObjectStatus.INACTIVE))
		person.demographics =
			personDemographicsService.create(new PersonDemographics(
					ethnicity: inactiveEthnicity1))

		and: "several other ethnicities in the db that this test is completely in control of"
		def inactiveEthnicity2 = ethnicityService.create(
				new Ethnicity(name: "inactive ethnicity 2",
						description: "inactive ethnicity description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeEthnicity = ethnicityService.create(
				new Ethnicity(name: "active ethnicity",
						description: "active ethnicity description ",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable ethnicities include the inactive ethnicity"
		idsOf(intake.referenceData["ethnicities"]).contains(inactiveEthnicity1.id)
		!(idsOf(intake.referenceData["ethnicities"]).contains(inactiveEthnicity2.id))
		idsOf(intake.referenceData["ethnicities"]).contains(activeEthnicity.id)
	}

	def "inactive but already associated funding sources still selectable"() {
		given: "a person with an inactive funding source"
		def person = createPerson()
		def inactiveFundingSource1 = fundingSourceService.create(
				new FundingSource(name: "inactive funding source 1",
					description: "inactive funding source description 1",
					objectStatus: ObjectStatus.INACTIVE))
		person.fundingSources.add(new PersonFundingSource(person: person,
				fundingSource: inactiveFundingSource1))
		person = personService.save(person)

		and: "several other funding sourcs in the db that this test is completely in control of"
		def inactiveFundingSource2 = fundingSourceService.create(
				new FundingSource(name: "inactive funding source 2",
						description: "inactive funding source description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeFundingSource = fundingSourceService.create(
				new FundingSource(name: "active funding source",
						description: "active funding source description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable funding sources include the inactive funding source"
		idsOf(intake.referenceData["fundingSources"]).contains(inactiveFundingSource1.id)
		!(idsOf(intake.referenceData["fundingSources"]).contains(inactiveFundingSource2.id))
		idsOf(intake.referenceData["fundingSources"]).contains(activeFundingSource.id)
	}

	def "inactive but already associated marital statuses still selectable"() {
		given: "a person with an inactive marital status"
		def person = createPerson()
		def inactiveMaritalStatus1 = maritalStatusService.create(
				new MaritalStatus(name: "inactive marital status 1",
						description: "inactive marital status description 1",
						objectStatus: ObjectStatus.INACTIVE))
		person.demographics =
			personDemographicsService.create(new PersonDemographics(
					maritalStatus: inactiveMaritalStatus1))

		and: "several other marital statuses in the db that this test is completely in control of"
		def inactiveMaritalStatus2 = maritalStatusService.create(
				new MaritalStatus(name: "inactive marital status 2",
						description: "inactive marital status description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeMaritalStatus = maritalStatusService.create(
				new MaritalStatus(name: "active marital status",
						description: "active marital status description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable marital statuses include the inactive marital status"
		idsOf(intake.referenceData["maritalStatuses"]).contains(inactiveMaritalStatus1.id)
		!(idsOf(intake.referenceData["maritalStatuses"]).contains(inactiveMaritalStatus2.id))
		idsOf(intake.referenceData["maritalStatuses"]).contains(activeMaritalStatus.id)
	}

	def "inactive but already associated military affiliation still selectable"() {
		given: "a person with an inactive military affiliation"
		def person = createPerson()
		def inactiveMilitaryAffiliation1 = militaryAffiliationService.create(
				new MilitaryAffiliation(name: "inactive military affiliation 1",
						description: "inactive military affiliation description 1",
						objectStatus: ObjectStatus.INACTIVE))
		person.demographics =
			personDemographicsService.create(new PersonDemographics(
					militaryAffiliation: inactiveMilitaryAffiliation1))

		and: "several other military affiliations in the db that this test is completely in control of"
		def inactiveMilitaryAffiliation2 = militaryAffiliationService.create(
				new MilitaryAffiliation(name: "inactive military affiliation 2",
						description: "inactive military affiliation description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeMilitaryAffiliation = militaryAffiliationService.create(
				new MilitaryAffiliation(name: "active military affiliation",
						description: "active military affiliation description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable military affiliations include the inactive military affiliation"
		idsOf(intake.referenceData["militaryAffiliations"]).contains(inactiveMilitaryAffiliation1.id)
		!(idsOf(intake.referenceData["militaryAffiliations"]).contains(inactiveMilitaryAffiliation2.id))
		idsOf(intake.referenceData["militaryAffiliations"]).contains(activeMilitaryAffiliation.id)
	}

	def "inactive but already associated student status still selectable"() {
		given: "a person with an inactive student status"
		def person = createPerson()
		def inactiveStudentStatus1 = studentStatusService.create(
				new StudentStatus(name: "inactive student status 1",
						description: "inactive student status description 1",
						objectStatus: ObjectStatus.INACTIVE))
		person.educationPlan = new PersonEducationPlan(studentStatus: inactiveStudentStatus1)
		person = personService.save(person)

		and: "several other student statuses in the db that this test is completely in control of"
		def inactiveStudentStatus2 = studentStatusService.create(
				new StudentStatus(name: "inactive student status 2",
						description: "inactive student status description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeStudentStatus = studentStatusService.create(
				new StudentStatus(name: "active student status",
						description: "active student status description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable student statuses include the inactive student status"
		idsOf(intake.referenceData["studentStatuses"]).contains(inactiveStudentStatus1.id)
		!(idsOf(intake.referenceData["studentStatuses"]).contains(inactiveStudentStatus2.id))
		idsOf(intake.referenceData["studentStatuses"]).contains(activeStudentStatus.id)
	}

	def "inactive but already associated veteran status still selectable"() {
		given: "a person with an inactive veteran status"
		def person = createPerson()
		def inactiveVeteranStatus1 = veteranStatusService.create(
				new VeteranStatus(name: "inactive veteran status 1",
						description: "inactive veteran status description 1",
						objectStatus: ObjectStatus.INACTIVE))
		person.demographics =
			personDemographicsService.create(new PersonDemographics(
					veteranStatus: inactiveVeteranStatus1))

		and: "several other veteran statuses in the db that this test is completely in control of"
		def inactiveVeteranStatus2 = veteranStatusService.create(
				new VeteranStatus(name: "inactive veteran status 2",
						description: "inactive veteran status description 2",
						objectStatus: ObjectStatus.INACTIVE))
		def activeVeteranStatus = veteranStatusService.create(
				new VeteranStatus(name: "active veteran status",
						description: "active veteran status description",
						objectStatus: ObjectStatus.ACTIVE))

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable veteran statuses include the inactive veteran status"
		idsOf(intake.referenceData["veteranStatuses"]).contains(inactiveVeteranStatus1.id)
		!(idsOf(intake.referenceData["veteranStatuses"]).contains(inactiveVeteranStatus2.id))
		idsOf(intake.referenceData["veteranStatuses"]).contains(activeVeteranStatus.id)
	}

	// We know the SortingAndPaging construction logic is shared by all
	// reference data lookups, as is the DAO code that handles the resulting
	// SortingAndPaging, so a per-type test for non-paginated results is
	// overkill. (Compare to the type-specific ID unpacking logic that
	// warrants the type-specific tests above.)
	def "all active challenges are selectable"() {
		given: "more challenges than will fit on the largest default page size"
		def existingChallengeCnt =
			challengeService.getAllForIntake(SortingAndPaging.createForSingleSortAll(ObjectStatus.ACTIVE, null, null)).results
		def additionalChallengesQuota = SortingAndPaging.MAXIMUM_ALLOWABLE_RESULTS + 10
		def totalActiveChallengesForIntake = existingChallengeCnt + additionalChallengesQuota
		for ( i in (1..additionalChallengesQuota) ) {
			challengeService.create(new Challenge(name: "additional challenge ${i}",
					description: "inactive challenge description \${i}",
					showInStudentIntake: true,
					objectStatus: ObjectStatus.ACTIVE))
		}

		and: "a person to load the intake form for"
		def person = createPerson()

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()
		person = personService.load(person.id) // paranoia

		when: "the intake form is asked for"
		def intake = intakeController.load(person.id)

		then: "the selectable challenges include all active challenges"
		idsOf(intake.referenceData["challenges"]).size() == totalActiveChallengesForIntake
	}

	def idsOf(objs) {
		objs.collect([], { obj -> obj.id })
	}

	def createPerson() {
		personService.create(
				new Person(firstName: "first", lastName: "last",
						username: "username", schoolId: "schoolId",
						primaryEmailAddress: "email"))
	}

}
