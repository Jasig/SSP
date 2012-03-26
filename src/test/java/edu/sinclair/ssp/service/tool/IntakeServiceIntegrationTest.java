package edu.sinclair.ssp.service.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.PersonDao;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
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
	@Transactional
	public void testIntakeServiceFormObjectNotFoundException()
			throws ObjectNotFoundException {
		service.loadForPerson(UUID.randomUUID());
	}

	@Test
	@Transactional
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

	@Test
	@Transactional
	public void testIntakeServiceForSimpleNewUser()
			throws ObjectNotFoundException {
		// create a new blank Person
		UUID id = null;
		Person person = new Person();
		person.setFirstName("first");
		person.setLastName("last");
		person.setPrimaryEmailAddress("email");

		// Save and commit person

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

		// Fill the IntakeForm with test data
		// TODO: Fill form

		assertTrue("IntakeFormService did not return success (true).",
				service.save(form));

		// Re-load form
		service.loadForPerson(id);

		assertNotNull("IntakeForm could not be initialized correctly.", form);
		assertNotNull("Recently created user could not be loaded.",
				form.getPerson());

		// Check that all the persisted values match
		// TODO: Assert matching values

		// Remove Person completely (not just mark deleted) which should
		// delete
		// all child objects created by the IntakeFormService
		personDao.delete(form.getPerson());

		try {
			person = personService.get(id);
			assertNull("Person was not deleted correctly.", person);
		} catch (ObjectNotFoundException exc) {
			assertNotNull("Expected exception was not thrown.", exc);
		}

	}
}
