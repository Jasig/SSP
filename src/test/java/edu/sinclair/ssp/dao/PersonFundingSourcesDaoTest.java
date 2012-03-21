package edu.sinclair.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonFundingSource;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonFundingSourcesDaoTest {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonDemographicsDaoTest.class);

	@Autowired
	private PersonDao daoPerson;

	@Autowired
	private PersonFundingSourceDao dao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGet() {
		// test student = ken thompson; test description = "some description"
		String testDescription = "some description";
		Person person = daoPerson.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));

		assertTrue(
				"Sample data should have allowed the user Ken Thompson to be loaded for UUID f549ecab-5110-4cc1-b2bb-369cac854dea",
				person != null);

		Set<PersonFundingSource> pfss = person.getFundingSources();
		PersonFundingSource pfs = null;

		if (pfss.size() == 0) {
			pfs = new PersonFundingSource();
			pfs.setDescription(testDescription + "2");
			pfs.setPerson(person);
			dao.save(pfs);

			pfs = new PersonFundingSource();
			pfs.setDescription(testDescription);
			pfs.setPerson(person);
			dao.save(pfs);

			// reload to check that save worked
			person = daoPerson.get(person.getId());
			pfss = person.getFundingSources();
			for (PersonFundingSource tmp : pfss) {
				pfs = tmp;
			}
		} else {
			for (PersonFundingSource tmp : pfss) {
				pfs = tmp;
				pfs.setDescription(testDescription);
			}
			daoPerson.save(person);
		}

		assertEquals("Description values did not match.", testDescription,
				pfs.getDescription());

		PersonFundingSource byId = dao.get(pfs.getId());
		assertEquals(byId.getId(), pfs.getId());

		UUID oldId = pfs.getId();
		pfss.clear();
		daoPerson.save(person);
		dao.delete(pfs);
		assertNull(
				"Person-funding source information was not correctly deleted.",
				dao.get(oldId));
	}

	@Test
	public void testGetAll() {
		dao.getAll(ObjectStatus.ALL);
		assertTrue(true);
	}

}
