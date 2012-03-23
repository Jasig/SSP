package edu.sinclair.ssp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import edu.sinclair.ssp.model.reference.EducationLevel;

public class PersonEducationLevelTest {

	@Test
	public void testOverwrite() {
		String testString1 = "ts1";
		String testString2 = "ts2";

		PersonEducationLevel pPersistent = new PersonEducationLevel();
		pPersistent.setDescription(testString1);

		PersonEducationLevel pFromTO = new PersonEducationLevel();
		pFromTO.setDescription(testString2);

		pPersistent.overwrite(pFromTO);

		assertEquals("Field not copied correctly.", testString2,
				pPersistent.getDescription());
	}

	@Test
	public void testOverwriteWithPersonAndEducationLevel() {
		String testString1 = "ts1";
		String testString2 = "ts2";
		Date testDate = new Date();
		Date testDate2 = new Date();
		testDate2.setTime(testDate2.getTime() + 24);

		Person person = new Person(UUID.randomUUID());
		Person person2 = new Person(UUID.randomUUID());

		EducationLevel educationLevel = new EducationLevel(UUID.randomUUID());
		EducationLevel educationLevel2 = new EducationLevel(UUID.randomUUID());

		person.setBirthDate(testDate);
		person2.setBirthDate(testDate2);

		educationLevel.setName(testString1);
		educationLevel2.setName(testString2);

		PersonEducationLevel pPersistent = new PersonEducationLevel();
		pPersistent.setPerson(person);
		pPersistent.setEducationLevel(educationLevel);

		PersonEducationLevel pFromTO = new PersonEducationLevel();
		pFromTO.setPerson(person2);
		pFromTO.setEducationLevel(educationLevel2);

		pPersistent.overwriteWithPersonAndEducationLevel(pFromTO, person2);

		assertEquals("Person property not copied correctly.", person2,
				pPersistent.getPerson());
		assertEquals("Person.birthDate property not copied correctly.",
				testDate2, pPersistent.getPerson().getBirthDate());
		assertNotSame(
				"Person.educationLevel property copied exactly, instead of deep copying.",
				educationLevel2, pPersistent.getEducationLevel());
		assertEquals("Person.educationLevel property not copied correctly.",
				testString2, pPersistent.getEducationLevel().getName());
	}
}
