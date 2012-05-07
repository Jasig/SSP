package org.jasig.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.service.ObjectNotFoundException;
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
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDemographicsDaoTest {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonDemographicsDaoTest.class);

	@Autowired
	private PersonDao daoPerson;

	@Autowired
	private PersonDemographicsDao dao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		// test student = ken thompson; test wage = "some wage"
		String testWage = "some wage";
		Person person = daoPerson.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));

		assertTrue(
				"Sample data should have allowed the user Ken Thompson to be loaded for UUID f549ecab-5110-4cc1-b2bb-369cac854dea",
				person != null);

		PersonDemographics pd = person.getDemographics();

		if (null == pd) {
			pd = new PersonDemographics();
			pd.setWage(testWage);
			person.setDemographics(pd);
			daoPerson.save(person);

			// reload to check that save worked
			person = daoPerson.get(person.getId());
			pd = person.getDemographics();
		} else {
			pd.setWage(testWage);
			daoPerson.save(person);
		}

		assertEquals("Demographics wage values did not match.", testWage,
				pd.getWage());

		PersonDemographics byId = dao.get(pd.getId());
		assertEquals(byId.getId(), pd.getId());

		UUID oldId = pd.getId();
		person.setDemographics(null);
		daoPerson.save(person);
		dao.delete(pd);

		try {
			assertNull("Demographic information was not correctly deleted.",
					dao.get(oldId));
		} catch (ObjectNotFoundException e) {
			// expected
			e.printStackTrace();
		}
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		PersonDemographics pd = dao.get(id);
		assertNull(pd);

		assertNull(new Person(id).getDemographics());
	}

	@Test
	public void testGetAll() {
		dao.getAll(ObjectStatus.ALL);
		assertTrue(true);
	}

}
