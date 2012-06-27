package org.jasig.ssp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.google.common.collect.Sets;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
public class PersonServiceIntegrationTest {

	@Autowired
	private transient PersonService service;

	@Autowired
	private transient ChallengeService challengeService;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final UUID CHALLENGE_ID = UUID
			.fromString("f5bb0a62-1756-4ea2-857d-5821ee44a1d0");

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAll() {
		final Collection<Person> list = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertNotNull("GetAll list should not have been null.", list);
		assertTrue("List should have included multiple entities.",
				list.size() > 1);
	}

	@Test
	public void testGetAllWithRowFilter() {
		final Collection<Person> listAll = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		final Collection<Person> listFiltered = service.getAll(
				SortingAndPaging.createForSingleSort(ObjectStatus.ACTIVE, 1, 2,
						null, null, null)).getRows();

		assertNotNull("List should not have been null.", listAll);
		assertTrue("List should have included multiple entities.",
				listAll.size() > 2);

		assertNotNull("Filtered list should not have been null.", listFiltered);
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());

		assertNotSame(
				"The filtered list should have included a different number of entities then the unfiltered list.",
				listFiltered.size(), listAll.size());
	}

	@Test
	public void testGetDoesntIncludeDeletedChildren()
			throws ObjectNotFoundException, ValidationException {
		// arrange
		final Session session = sessionFactory.openSession();

		final Person person = new Person();
		person.setFirstName("first name");
		person.setLastName("last name");
		person.setPrimaryEmailAddress("email");
		person.setUsername("username");

		final Challenge challengeDeleted = new Challenge(UUID.randomUUID(),
				"deletedChallenge");
		challengeDeleted.setObjectStatus(ObjectStatus.DELETED);
		final Challenge savedChallenge = challengeService
				.create(challengeDeleted);

		session.flush();

		final Set<PersonChallenge> challenges = Sets.newHashSet();
		challenges.add(new PersonChallenge(person, challengeService
				.get(CHALLENGE_ID)));
		challenges.add(new PersonChallenge(person, savedChallenge));
		person.setChallenges(challenges);

		final PersonDemographics demographics = new PersonDemographics();
		demographics.setObjectStatus(ObjectStatus.DELETED);
		person.setDemographics(demographics);

		final Person saved = service.create(person);

		// act
		final Person loaded = service.get(saved.getId());

		// assert
		final Set<PersonChallenge> loadedChallenges = loaded.getChallenges();
		assertEquals("Active challenge count did not match expected.", 1,
				loadedChallenges);
	}
}