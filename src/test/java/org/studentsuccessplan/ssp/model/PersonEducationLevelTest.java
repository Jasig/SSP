package org.studentsuccessplan.ssp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.UUID;

import org.junit.Test;

import org.studentsuccessplan.ssp.model.reference.EducationLevel;

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
		String testString1 = "teststring1";
		String testString2 = "teststring2";
		String testString3 = "teststring3";

		Person person = new Person(UUID.randomUUID());
		Person person2 = new Person(UUID.randomUUID());

		EducationLevel educationLevel = new EducationLevel(UUID.randomUUID());
		EducationLevel educationLevel2 = new EducationLevel(UUID.randomUUID());

		educationLevel.setName(testString1);
		educationLevel2.setName(testString2);

		PersonEducationLevel pPersistent = new PersonEducationLevel();
		pPersistent.setPerson(person);
		pPersistent.setEducationLevel(educationLevel);

		PersonEducationLevel pFromTO = new PersonEducationLevel();
		pFromTO.setPerson(person2);
		pFromTO.setEducationLevel(educationLevel2);
		pFromTO.setDescription(testString3);

		pPersistent.overwrite(pFromTO);

		assertEquals(
				"PersonChallenge.Person property copied when it shouldn't have been.",
				person, pPersistent.getPerson());
		assertNotSame(
				"Person.educationLevel property copied exactly, instead of deep copying.",
				educationLevel2, pPersistent.getEducationLevel());
		assertEquals(
				"PersonEducationLevel.Challenge property copied when it shouldn't have been.",
				testString1, pPersistent.getEducationLevel().getName());
		assertEquals(
				"PersonEducationLevel.Description property not copied correctly.",
				testString3, pPersistent.getDescription());
	}
}
