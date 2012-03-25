package edu.sinclair.ssp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;

import edu.sinclair.ssp.model.reference.Challenge;

public class PersonTest {

	@Test
	public void testOverwrite() {
		// Simple fields
		String testString1 = "ts1";
		String testString2 = "ts2";
		String testString3 = "ts3";
		Date testDate = new Date();
		Date testDate2 = new Date();
		testDate2.setTime(testDate2.getTime() + 24);

		// Set<PersonChallenge>
		Set<PersonChallenge> challenges = new HashSet<PersonChallenge>();
		challenges.add(new PersonChallenge());

		Person pPersistent = new Person(UUID.randomUUID());
		pPersistent.setAddressLine1(testString1);
		pPersistent.setBirthDate(testDate);
		pPersistent.setSchoolId(testString2);
		pPersistent.setWorkPhone(null);
		pPersistent.setZipCode(null);

		Person pFromTO = new Person(UUID.randomUUID());
		pFromTO.setAddressLine1(testString3);
		pFromTO.setBirthDate(testDate2);
		pFromTO.setSchoolId(testString3);
		pFromTO.setZipCode(testString3);
		pFromTO.setChallenges(challenges);

		pPersistent.overwrite(pFromTO);

		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getAddressLine1());
		assertEquals("Field not copied correctly.", testDate2,
				pPersistent.getBirthDate());
		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getSchoolId());
		assertEquals("Field not copied correctly.", null,
				pPersistent.getWorkPhone());
		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getZipCode());
		assertNotNull("Set not copied correctly.", pPersistent.getChallenges());
		assertEquals("PersonChallenge Set copied when it shouldn't have been.",
				0, pPersistent.getChallenges().size());
	}

	@Test
	public void testOverwriteWithCollections() {
		// Simple fields
		String testString1 = "ts1";
		String testString2 = "ts2";
		String testString3 = "ts3";
		Date testDate = new Date();
		Date testDate2 = new Date();
		testDate2.setTime(testDate2.getTime() + 24);

		Person pPersistent = new Person(UUID.randomUUID());
		pPersistent.setAddressLine1(testString1);
		pPersistent.setBirthDate(testDate);
		pPersistent.setSchoolId(testString2);
		pPersistent.setWorkPhone(null);
		pPersistent.setZipCode(null);

		Person pFromTO = new Person(UUID.randomUUID());
		pFromTO.setAddressLine1(testString3);
		pFromTO.setBirthDate(testDate2);
		pFromTO.setSchoolId(testString3);
		pFromTO.setZipCode(testString3);

		// Set<PersonChallenge>
		Set<PersonChallenge> challenges = new HashSet<PersonChallenge>();
		PersonChallenge challenge1 = new PersonChallenge();
		challenge1.setChallenge(new Challenge());
		challenge1.setPerson(pPersistent);
		challenges.add(challenge1);
		pFromTO.setChallenges(challenges);

		pPersistent.overwriteWithCollections(pFromTO);

		// test simple fields
		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getAddressLine1());
		assertEquals("Field not copied correctly.", testDate2,
				pPersistent.getBirthDate());
		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getSchoolId());
		assertEquals("Field not copied correctly.", null,
				pPersistent.getWorkPhone());
		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getZipCode());

		// test PersonChallenge data
		assertNotNull("Set not copied correctly.", pPersistent.getChallenges());
		assertEquals("PersonChallenge Set not copied correctly.", 1,
				pPersistent.getChallenges().size());
		PersonChallenge assertChallenge = pPersistent.getChallenges()
				.iterator().next();
		assertNotNull("PersonChallenge Set not copied correctly.",
				assertChallenge);
		assertNotNull("PersonChallenge.Person not copied correctly.",
				assertChallenge.getPerson());
		assertEquals("PersonChallenge.Person.SchoolId not copied correctly.",
				testString3, assertChallenge.getPerson().getSchoolId());
	}
}
