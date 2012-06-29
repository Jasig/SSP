package org.jasig.ssp.service.tool; // NOPMD because it's an integration test

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.PersonEducationGoal;
import org.jasig.ssp.model.PersonEducationLevel;
import org.jasig.ssp.model.PersonEducationPlan;
import org.jasig.ssp.model.PersonFundingSource;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.EducationLevel;
import org.jasig.ssp.model.reference.FundingSource;
import org.jasig.ssp.model.reference.StudentStatus;
import org.jasig.ssp.model.tool.IntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * IntakeService integration tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class IntakeServiceIntegrationTest {

	@Autowired
	private transient IntakeService service;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient PersonDao personDao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String LASTNAME = "last";

	private static final String TEST_STRING1 = "testString1";

	private static final String TEST_STRING2 = "testString2";

	private static final String TEST_STRING3 = "testString3";

	private static final String EDUCATIONLEVEL_NAME = "Test Education Level";

	private static final String CHALLENGE_NAME = "Test Challenge";

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testIntakeServiceFormObjectNotFoundException()
			throws ObjectNotFoundException {
		service.loadForPerson(UUID.randomUUID());
	}

	/**
	 * Integration test that loads and asserts an {@link IntakeForm} for the
	 * administrator user.
	 * 
	 * @throws ObjectNotFoundException
	 *             Thrown if the special administrator user is not found.
	 */
	@Test
	public void testIntakeServiceFromLoadForPersonFromDatabaseForAdminUser()
			throws ObjectNotFoundException {
		final IntakeForm form = service
				.loadForPerson(Person.SYSTEM_ADMINISTRATOR_ID);
		assertNotNull("Admin user could not be loaded.", form.getPerson());
		assertNotNull("Admin user loaded but was missing the Person instance.",
				form.getPerson());
		assertEquals("Admin user loaded but identifiers did not match.",
				Person.SYSTEM_ADMINISTRATOR_ID, form.getPerson().getId());

		assertNull(
				"Admin user loaded but included a PersonDemographics instance even though it should not have.",
				form.getPerson().getDemographics());
	}

	/**
	 * Massive integration test that creates, fills, and asserts the correct
	 * insertion of an {@link IntakeService#save(IntakeForm)} all the way
	 * through the service, DAO, and model layers.
	 * 
	 * @throws ObjectNotFoundException
	 *             Thrown if any of the expected test data identifiers are not
	 *             found in the database.
	 */
	@Test
	public void testIntakeServiceForNewUser() throws ObjectNotFoundException { // NOPMD
		// From test database, see the test liquibase XML
		final UUID testEducationLevelId = UUID
				.fromString("841652e8-7b80-41e7-9ef2-ce456d2606ca");
		final UUID testChallengeId = UUID
				.fromString("f5bb0a62-1756-4ea2-857d-5821ee44a1d0");
		// Setup - create a new blank Person
		Person person = new Person();
		person.setFirstName("first");
		person.setLastName(LASTNAME);
		person.setUsername("username");
		person.setSchoolId("school id");
		person.setPrimaryEmailAddress("email");
		person.setAddressLine1("address line 1");
		person.setCellPhone("867-5309");

		// Save new person

		person = personService.create(person);

		final UUID id = person.getId();

		// reload person from database to make sure create() worked
		person = personService.get(id);

		assertNotNull("New person did not save and reload correctly", person);

		// initialize the IntakeForm with the recently created Person
		// instance
		IntakeForm form = service.loadForPerson(id);

		assertNotNull("IntakeForm could not be initialized correctly.", form);
		assertNotNull("Recently created user could not be loaded.",
				form.getPerson());

		person = form.getPerson(); // refresh

		// Setup - fill the IntakeForm with test data

		final PersonDemographics pd1 = new PersonDemographics();
		pd1.setChildAges(TEST_STRING1);
		person.setCoach(personService.get(Person.SYSTEM_ADMINISTRATOR_ID));
		person.setDemographics(pd1);

		final PersonEducationGoal peg1 = new PersonEducationGoal();
		peg1.setDescription(TEST_STRING1);
		person.setEducationGoal(peg1);

		final PersonEducationPlan pep1 = new PersonEducationPlan();
		pep1.setObjectStatus(ObjectStatus.INACTIVE);
		final StudentStatus ss1 = new StudentStatus();
		ss1.setName(TEST_STRING1);
		ss1.setDescription(TEST_STRING2);
		pep1.setStudentStatus(ss1);
		person.setEducationPlan(pep1);

		final PersonEducationLevel pel1 = new PersonEducationLevel();
		pel1.setEducationLevel(new EducationLevel(testEducationLevelId));
		pel1.setDescription(TEST_STRING1);
		person.getEducationLevels().add(pel1);

		final PersonFundingSource pfs1 = new PersonFundingSource();
		pfs1.setFundingSource(new FundingSource());
		pfs1.setDescription(TEST_STRING3);
		person.getFundingSources().add(pfs1);

		final PersonChallenge pc1 = new PersonChallenge();
		pc1.setChallenge(new Challenge(testChallengeId));
		pc1.setDescription(TEST_STRING1);
		person.getChallenges().add(pc1);

		// Run
		assertTrue("IntakeFormService did not return success (true).",
				service.save(form));

		// Re-load form
		form = service.loadForPerson(id);

		assertNotNull("IntakeForm could not be initialized correctly.", form);
		assertNotNull("Recently created user could not be loaded.",
				form.getPerson());

		person = form.getPerson();

		// Check that all the persisted values match
		assertNotNull("Demographic data did not exist.",
				person.getDemographics());
		assertEquals("Demographic.ChildAges did not match.", TEST_STRING1, form
				.getPerson().getDemographics().getChildAges());
		assertNotNull("Demographic.Coach data did not exist.", person
				.getCoach());
		assertEquals("Demographic.Coach.id did not match.",
				Person.SYSTEM_ADMINISTRATOR_ID, person.getCoach().getId());

		// Assert Education Goal data
		assertNotNull("Education Goal data did not exist.",
				person.getEducationGoal());
		assertEquals("EducationGoal.Description did not match.", TEST_STRING1,
				person.getEducationGoal().getDescription());

		// Assert Education Plan
		assertNotNull("Education Plan data did not exist.",
				person.getEducationPlan());
		assertEquals("EducationPlan.ObjectStatus did not match.",
				ObjectStatus.INACTIVE, person.getEducationPlan()
						.getObjectStatus());
		assertNotNull("Education Plan.StudentStatus data did not exist.",
				person.getEducationPlan().getStudentStatus());
		/*
		 * TODO Won't work until we can load an existing reference data row
		 * first. Need to add some to the liquibase test XML.
		 * 
		 * assertEquals("EducationPlan.StudentStatus did not match.",
		 * testString2,
		 * person.getEducationPlan().getStudentStatus().getDescription());
		 */

		// Assert Education Level data
		final Set<PersonEducationLevel> testLevels = person
				.getEducationLevels();
		assertNotNull("Education Level data did not exist.", testLevels);
		assertEquals(
				"Education Level data did not contain the expected 1 element.",
				1, testLevels.size());
		final PersonEducationLevel pel3 = testLevels.iterator().next();
		assertNotNull("Education Level (1) data did not exist.", pel3);
		assertNotNull("Education Level (1) EducationLevel did not exist.",
				pel3.getEducationLevel());
		assertEquals("Education level (1) ID that was loaded did not match.",
				testEducationLevelId, pel3.getEducationLevel().getId());
		assertEquals("Education level (1) name that was loaded did not match.",
				EDUCATIONLEVEL_NAME, pel3.getEducationLevel().getName());

		// Assert FundingSource data
		final Set<PersonFundingSource> testSources = person.getFundingSources();
		assertNotNull("FundingSource data did not exist.", testSources);
		assertEquals(
				"FundingSource data did not contain the expected 1 element.",
				1, testSources.size());
		final PersonFundingSource pfs3 = testSources.iterator().next();
		assertNotNull("FundingSource (1) data did not exist.", pfs3);
		assertEquals("FundingSource (1) Description did not match.",
				TEST_STRING3, pfs3.getDescription());

		// Assert Challenge data
		final Set<PersonChallenge> testChallenges = person.getChallenges();
		assertNotNull("Challenge data did not exist.", testChallenges);
		assertEquals("Challenge data did not contain the expected 1 element.",
				1, testChallenges.size());
		final PersonChallenge pc3 = testChallenges.iterator().next();
		assertNotNull("Challenge (1) data did not exist.", pc3);
		assertEquals("Challenge (1) Description did not match.", TEST_STRING1,
				pc3.getDescription());
		assertEquals("Challenge (1) ID that was loaded did not match.",
				testChallengeId, pc3.getChallenge().getId());
		assertEquals("Challenge (1) name that was loaded did not match.",
				CHALLENGE_NAME, pc3.getChallenge().getName());

		// Remove Person completely (not just mark deleted) which should
		// delete all child objects created by the IntakeFormService
		personDao.delete(person);

		try {
			personService.get(id);
			fail("Person was not deleted correctly."); // NOPMD because we don't
			// want a possible earlier ObjectNotFoundException to be assumed
			// as test success
		} catch (final ObjectNotFoundException exc) { // NOPMD
			/* expected */
		}
	}
}