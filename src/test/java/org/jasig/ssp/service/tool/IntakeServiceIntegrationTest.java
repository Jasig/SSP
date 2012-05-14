package org.jasig.ssp.service.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class IntakeServiceIntegrationTest {

	// private static final Logger logger =
	// LoggerFactory.getLogger(StudentIntakeServiceTest.class);

	@Autowired
	private IntakeService service;

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
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
		IntakeForm form = service.loadForPerson(Person.SYSTEM_ADMINISTRATOR_ID);
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
	public void testIntakeServiceForNewUser() throws ObjectNotFoundException {
		// test data
		String lastName = "last";
		String testString1 = "testString1";
		String testString2 = "testString2";
		String testString3 = "testString3";
		// From test database, see the test liquibase XML
		UUID testEducationLevelId = UUID
				.fromString("841652e8-7b80-41e7-9ef2-ce456d2606ca");
		String testEducationLevelName = "Test Education Level";
		UUID testChallengeId = UUID
				.fromString("f5bb0a62-1756-4ea2-857d-5821ee44a1d0");
		String testChallengeName = "Test Challenge";

		// Setup - create a new blank Person
		UUID id = null;
		Person person = new Person();
		person.setFirstName("first");
		person.setLastName(lastName);
		person.setPrimaryEmailAddress("email");
		person.setAddressLine1("address line 1");
		person.setCellPhone("867-5309");

		// Save new person

		person = personService.create(person);

		id = person.getId();

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

		PersonDemographics pd1 = new PersonDemographics();
		pd1.setChildAges(testString1);
		person.setCoach(personService.get(Person.SYSTEM_ADMINISTRATOR_ID));
		person.setDemographics(pd1);

		PersonEducationGoal peg1 = new PersonEducationGoal();
		peg1.setDescription(testString1);
		person.setEducationGoal(peg1);

		PersonEducationPlan pep1 = new PersonEducationPlan();
		pep1.setObjectStatus(ObjectStatus.INACTIVE);
		StudentStatus ss1 = new StudentStatus();
		ss1.setName(testString1);
		ss1.setDescription(testString2);
		pep1.setStudentStatus(ss1);
		person.setEducationPlan(pep1);

		PersonEducationLevel pel1 = new PersonEducationLevel();
		pel1.setEducationLevel(new EducationLevel(testEducationLevelId));
		pel1.setDescription(testString1);
		person.getEducationLevels().add(pel1);

		PersonFundingSource pfs1 = new PersonFundingSource();
		pfs1.setFundingSource(new FundingSource());
		pfs1.setDescription(testString3);
		person.getFundingSources().add(pfs1);

		PersonChallenge pc1 = new PersonChallenge();
		pc1.setChallenge(new Challenge(testChallengeId));
		pc1.setDescription(testString1);
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
		assertEquals("Demographic.ChildAges did not match.", testString1, form
				.getPerson().getDemographics().getChildAges());
		assertNotNull("Demographic.Coach data did not exist.", person
				.getCoach());
		assertEquals("Demographic.Coach.id did not match.",
				Person.SYSTEM_ADMINISTRATOR_ID, person.getCoach().getId());

		// Assert Education Goal data
		assertNotNull("Education Goal data did not exist.",
				person.getEducationGoal());
		assertEquals("EducationGoal.Description did not match.", testString1,
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
		Set<PersonEducationLevel> testLevels = person.getEducationLevels();
		assertNotNull("Education Level data did not exist.", testLevels);
		assertEquals(
				"Education Level data did not contain the expected 1 element.",
				1, testLevels.size());
		for (PersonEducationLevel thisOne : testLevels) {
			pel1 = thisOne;
		}
		assertNotNull("Education Level (1) data did not exist.", pel1);
		assertNotNull("Education Level (1) EducationLevel did not exist.",
				pel1.getEducationLevel());
		assertEquals("Education level (1) ID that was loaded did not match.",
				testEducationLevelId, pel1.getEducationLevel().getId());
		assertEquals("Education level (1) name that was loaded did not match.",
				testEducationLevelName, pel1.getEducationLevel().getName());

		// Assert FundingSource data
		Set<PersonFundingSource> testSources = person.getFundingSources();
		assertNotNull("FundingSource data did not exist.", testSources);
		assertEquals(
				"FundingSource data did not contain the expected 1 element.",
				1, testSources.size());
		for (PersonFundingSource thisOne : testSources) {
			pfs1 = thisOne;
		}
		assertNotNull("FundingSource (1) data did not exist.", pfs1);
		assertEquals("FundingSource (1) Description did not match.",
				testString3, pfs1.getDescription());

		// Assert Challenge data
		Set<PersonChallenge> testChallenges = person.getChallenges();
		assertNotNull("Challenge data did not exist.", testChallenges);
		assertEquals("Challenge data did not contain the expected 1 element.",
				1, testChallenges.size());
		for (PersonChallenge thisOne : testChallenges) {
			pc1 = thisOne;
		}
		assertNotNull("Challenge (1) data did not exist.", pc1);
		assertEquals("Challenge (1) Description did not match.", testString1,
				pc1.getDescription());
		assertEquals("Challenge (1) ID that was loaded did not match.",
				testChallengeId, pc1.getChallenge().getId());
		assertEquals("Challenge (1) name that was loaded did not match.",
				testChallengeName, pc1.getChallenge().getName());

		// Remove Person completely (not just mark deleted) which should
		// delete
		// all child objects created by the IntakeFormService
		personDao.delete(person);

		try {
			person = personService.get(id);
			assertNull("Person was not deleted correctly.", person);
		} catch (ObjectNotFoundException exc) {
			/* expected */
		}
	}
}
