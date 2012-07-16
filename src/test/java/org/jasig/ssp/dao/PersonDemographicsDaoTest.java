package org.jasig.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDemographicsDaoTest {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonDemographicsDaoTest.class);

	@Autowired
	private transient PersonDao daoPerson;

	@Autowired
	private transient PersonDemographicsDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "test string 1";

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		Person person = daoPerson.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));

		assertNotNull(
				"Sample data should have allowed the user Ken Thompson to be loaded for UUID f549ecab-5110-4cc1-b2bb-369cac854dea",
				person);

		PersonDemographics pd = person.getDemographics();

		if (null == pd) {
			pd = new PersonDemographics();
			pd.setWage(TEST_STRING1);
			person.setDemographics(pd);
			daoPerson.save(person);

			// reload to check that save worked
			person = daoPerson.get(person.getId());
			pd = person.getDemographics();
		} else {
			pd.setWage(TEST_STRING1);
			daoPerson.save(person);
		}

		assertEquals("Demographics wage values did not match.", TEST_STRING1,
				pd.getWage());

		final PersonDemographics byId = dao.get(pd.getId());
		assertEquals("IDs did not match.", byId.getId(), pd.getId());

		final UUID oldId = pd.getId();
		person.setDemographics(null);
		daoPerson.save(person);
		dao.delete(pd);

		try {
			assertNull("Demographic information was not correctly deleted.",
					dao.get(oldId));
		} catch (final ObjectNotFoundException e) { // NOPMD
			// expected
		}
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final PersonDemographics pd = dao.get(id);
		assertNull("PersonDemographics should not have been null.", pd);
		assertNull("Person FK to demographics should not have been null.",
				new Person(id).getDemographics());
	}

	@Test
	public void testGetAll() {
		assertNotNull("GetAll should not have returned null.",
				dao.getAll(ObjectStatus.ALL));
	}
}