package org.jasig.ssp.service.external.impl

import org.junit.Test
import org.junit.Before
import org.jasig.ssp.service.reference.ConfigService
import org.jasig.ssp.dao.external.ExternalPersonDao
import org.jasig.ssp.model.Person
import org.jasig.ssp.model.external.ExternalPerson

import static org.junit.Assert.assertNull
import static org.junit.Assert.fail
import org.jasig.ssp.service.PersonService

import static org.junit.Assert.assertEquals

class ExternalPersonServiceImplTest {

	@Test
	void skipsExternalCoachAssignmentIfDisabled() {
		def student = new Person(id: UUID.randomUUID(),
			schoolId: "student-school-id")
		def externalPerson = new ExternalPerson(schoolId: "student-school-id",
				coachSchoolId: "coach-school-id")
		def service = new ExternalPersonServiceImpl(
				configService: configService([coachSetFromExternalData: "false",
						coachUnsetFromExternalData: "true"]),
				personService: [save:{}] as PersonService
		)
		service.updatePersonFromExternalPerson(student, externalPerson)
		assertNull(student.coach)
	}

	@Test
	void assignsExternalCoachIfEnabled() {
		def student = new Person(id: UUID.randomUUID(),
				schoolId: "student-school-id")
		def coach = new Person(id:  UUID.randomUUID(),
				schoolId: "coach-school-id")
		def externalPerson = new ExternalPerson(schoolId: "student-school-id",
				coachSchoolId: "coach-school-id")
		def service = new ExternalPersonServiceImpl(
				configService: configService([coachSetFromExternalData: "true",
						coachUnsetFromExternalData: "false"]),
				personService: [
						getBySchoolId: { schoolId ->
							schoolId == "coach-school-id" ? coach : null
						},
						save:{}] as PersonService
		)
		service.updatePersonFromExternalPerson(student, externalPerson)
		assertEquals(coach, student.coach)
	}

	@Test
	void skipsCoachAssignmentDeletionIfDisabled() {
		def coach = new Person(id:  UUID.randomUUID(),
				schoolId: "coach-school-id")
		def student = new Person(id: UUID.randomUUID(),
				schoolId: "student-school-id",
				coach: coach)
		def externalPerson = new ExternalPerson(schoolId: "student-school-id")
		def service = new ExternalPersonServiceImpl(
				configService: configService([coachSetFromExternalData: "true",
						coachUnsetFromExternalData: "false"]),
				personService: [save:{}] as PersonService
		)
		service.updatePersonFromExternalPerson(student, externalPerson)
		assertEquals(coach, student.coach)
	}

	// same as skipsCoachAssignmentDeletionIfDisabled() but with reversed config
	@Test
	void skipsCoachDeletionIfDeletionEnabledButAssignmentDisabled() {
		def coach = new Person(id:  UUID.randomUUID(),
				schoolId: "coach-school-id")
		def student = new Person(id: UUID.randomUUID(),
				schoolId: "student-school-id",
				coach: coach)
		def externalPerson = new ExternalPerson(schoolId: "student-school-id")
		def service = new ExternalPersonServiceImpl(
				configService: configService([coachSetFromExternalData: "false",
						coachUnsetFromExternalData: "true"]),
				personService: [save:{}] as PersonService
		)
		service.updatePersonFromExternalPerson(student, externalPerson)
		assertEquals(coach, student.coach)
	}

	@Test
	void deletesCoachAssignmentsIfEnabled() {
		def coach = new Person(id:  UUID.randomUUID(),
				schoolId: "coach-school-id")
		def student = new Person(id: UUID.randomUUID(),
				schoolId: "student-school-id",
				coach: coach)
		def externalPerson = new ExternalPerson(schoolId: "student-school-id")
		def service = new ExternalPersonServiceImpl(
				configService: configService([coachSetFromExternalData: "true",
						coachUnsetFromExternalData: "true"]),
				personService: [save:{}] as PersonService
		)
		service.updatePersonFromExternalPerson(student, externalPerson)
		assertNull(student.coach)
	}

	@Test
	void preservesExistingCoachAssignmentIfNewAssignmentRefersToUnknownCoach() {
		def coach = new Person(id:  UUID.randomUUID(),
				schoolId: "coach-school-id")
		def student = new Person(id: UUID.randomUUID(),
				schoolId: "student-school-id",
				coach: coach)
		def externalPerson = new ExternalPerson(schoolId: "student-school-id",
				coachSchoolId: "coach-school-id")
		def service = new ExternalPersonServiceImpl(
				configService: configService([coachSetFromExternalData: "true",
						coachUnsetFromExternalData: "true"]),
				personService: [ getBySchoolId: { null }, save:{} ]
						as PersonService
		)
		service.updatePersonFromExternalPerson(student, externalPerson)
		assertEquals(coach, student.coach)
	}

	private configService(config) {
		[ getByNameNullOrDefaultValue: { config[it] } ] as ConfigService
	}
}
