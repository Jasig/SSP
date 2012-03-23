package edu.sinclair.ssp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import edu.sinclair.ssp.model.reference.Challenge;

public class PersonChallengeTest {

	@Test
	public void testOverwrite() {
		String testString1 = "ts1";
		String testString2 = "ts2";

		PersonChallenge pPersistent = new PersonChallenge();
		pPersistent.setDescription(testString1);

		PersonChallenge pFromTO = new PersonChallenge();
		pFromTO.setDescription(testString2);

		pPersistent.overwrite(pFromTO);

		assertEquals("Field not copied correctly.", testString2,
				pPersistent.getDescription());
	}

	@Test
	public void testOverwriteWithPersonAndCollections() {
		String testString1 = "ts1";
		String testString2 = "ts2";
		Date testDate = new Date();
		Date testDate2 = new Date();
		testDate2.setTime(testDate2.getTime() + 24);

		Person person = new Person(UUID.randomUUID());
		Person person2 = new Person(UUID.randomUUID());

		Challenge challenge = new Challenge(UUID.randomUUID());
		Challenge challenge2 = new Challenge(UUID.randomUUID());

		person.setBirthDate(testDate);
		person2.setBirthDate(testDate2);

		challenge.setName(testString1);
		challenge2.setName(testString2);

		PersonChallenge pPersistent = new PersonChallenge();
		pPersistent.setPerson(person);
		pPersistent.setChallenge(challenge);

		PersonChallenge pFromTO = new PersonChallenge();
		pFromTO.setPerson(person2);
		pFromTO.setChallenge(challenge2);

		pPersistent.overwriteWithPersonAndCollections(pFromTO, person2);

		assertEquals("Person property not copied correctly.", person2,
				pPersistent.getPerson());
		assertEquals("Person.birthDate property not copied correctly.",
				testDate2, pPersistent.getPerson().getBirthDate());
		assertNotSame(
				"Person.challenge property copied exactly, instead of deep copying.",
				challenge2, pPersistent.getChallenge());
		assertEquals("Person.challenge property not copied correctly.",
				testString2, pPersistent.getChallenge().getName());
	}
}
