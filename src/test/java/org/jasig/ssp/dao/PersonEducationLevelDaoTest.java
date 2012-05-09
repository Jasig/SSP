package org.jasig.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonEducationLevel;
import org.jasig.ssp.model.reference.EducationLevel;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.EducationLevelService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonEducationLevelDaoTest {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonEducationLevelDaoTest.class);

	@Autowired
	private PersonEducationLevelDao dao;

	@Autowired
	private EducationLevelService educationLevelService;

	@Autowired
	private PersonService personService;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	private EducationLevel testEducationLevel;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
		testEducationLevel = educationLevelService
				.getAll(new SortingAndPaging(ObjectStatus.ACTIVE)).getRows()
				.iterator().next();
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		// test student = ken thompson
		Person person = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));

		List<PersonEducationLevel> modelsBefore = dao.forPerson(person);

		// save a new challenge for a person
		PersonEducationLevel model = new PersonEducationLevel(person,
				testEducationLevel);
		dao.save(model);

		List<PersonEducationLevel> modelsAfter = dao.forPerson(person);

		// we should see more than before
		assertTrue(modelsBefore.size() < modelsAfter.size());

		// makes sure the person is the same
		assertEquals(model.getPerson().getId(), person.getId());

		// fetch the saved one from the db
		PersonEducationLevel byId = dao.get(model.getId());
		assertEquals(byId.getId(), model.getId());

		PersonEducationLevel found = null;
		for (PersonEducationLevel pc : person.getEducationLevels()) {
			if (pc.getId().equals(model.getId())) {
				found = pc;
				break;
			}
		}
		assertNotNull(found);

		// delete it
		person.getEducationLevels().remove(found);
		dao.delete(model);

		try {
			assertNull(dao.get(model.getId()));
		} catch (ObjectNotFoundException e) {
			// expected
		}
	}

	@Test
	public void testNull() {
		UUID id = UUID.randomUUID();
		PersonEducationLevel model = null;
		try {
			model = dao.get(id);
		} catch (ObjectNotFoundException e) {
			// expected
		}
		assertNull(model);

		List<PersonEducationLevel> modelsAfter = dao.forPerson(new Person(id));
		assertEquals(0, modelsAfter.size());
	}

	@Test
	public void testGetAll() {
		dao.getAll(ObjectStatus.ALL);
	}

}
