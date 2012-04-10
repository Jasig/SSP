package edu.sinclair.ssp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonChallenge;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.reference.ChallengeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
public class PersonServiceIntegrationTest {

	@Autowired
	private PersonService service;

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAll() {
		List<Person> list = service.getAll(ObjectStatus.ALL, null, null, null,
				null);
		assertNotNull(list);
		assertTrue("List should have included multiple entities.",
				list.size() > 1);
	}

	@Test
	public void testGetAllWithRowFilter() {
		List<Person> listAll = service.getAll(ObjectStatus.ALL, null, null,
				null, null);
		List<Person> listFiltered = service.getAll(ObjectStatus.ALL, 1, 2,
				null, null);

		assertNotNull(listAll);
		assertTrue("List should have included multiple entities.",
				listAll.size() > 2);

		assertNotNull(listFiltered);
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());

		assertTrue(
				"The filtered list should have included a different number of entities then the unfiltered list.",
				listFiltered.size() != listAll.size());
	}

	@Test
	public void testOverwriteWithCollections() throws ObjectNotFoundException {
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
		challenge1.setChallenge(challengeService.get(UUID
				.fromString("f5bb0a62-1756-4ea2-857d-5821ee44a1d0")));
		challenge1.setPerson(pPersistent);
		challenges.add(challenge1);
		pFromTO.setChallenges(challenges);

		service.overwriteWithCollections(pPersistent, pFromTO);

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
