package org.studentsuccessplan.ssp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.UUID;

import org.junit.Test;

import org.studentsuccessplan.ssp.model.reference.Challenge;

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
		String testString1 = "teststring1";
		String testString2 = "teststring2";
		String testString3 = "teststring3";

		Person person = new Person(UUID.randomUUID());
		Person person2 = new Person(UUID.randomUUID());

		Challenge challenge = new Challenge(UUID.randomUUID());
		Challenge challenge2 = new Challenge(UUID.randomUUID());

		challenge.setName(testString1);
		challenge2.setName(testString2);

		PersonChallenge pPersistent = new PersonChallenge();
		pPersistent.setPerson(person);
		pPersistent.setChallenge(challenge);

		PersonChallenge pFromTO = new PersonChallenge();
		pFromTO.setPerson(person2);
		pFromTO.setChallenge(challenge2);
		pFromTO.setDescription(testString3);

		pPersistent.overwrite(pFromTO);

		assertEquals(
				"PersonChallenge.Person property copied when it shouldn't have been.",
				person, pPersistent.getPerson());
		assertNotSame(
				"Person.challenge property copied exactly, instead of deep copying.",
				challenge2, pPersistent.getChallenge());
		assertEquals(
				"PersonChallenge.Challenge property copied when it shouldn't have been.",
				testString1, pPersistent.getChallenge().getName());
		assertEquals(
				"PersonChallenge.Description property not copied correctly.",
				testString3, pPersistent.getDescription());
	}
}
