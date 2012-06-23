package org.jasig.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonFundingSource;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.PagingWrapper;
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
public class PersonFundingSourcesDaoTest {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PersonDemographicsDaoTest.class);

	@Autowired
	private transient PersonDao daoPerson;

	@Autowired
	private transient PersonFundingSourceDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_DESCRIPTION = "some description";

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

		Set<PersonFundingSource> pfss = person.getFundingSources();
		PersonFundingSource pfs = new PersonFundingSource();

		if (pfss.isEmpty()) {
			pfs = new PersonFundingSource();
			pfs.setDescription(TEST_DESCRIPTION + "2");
			pfs.setPerson(person);
			dao.save(pfs);

			pfs = new PersonFundingSource();
			pfs.setDescription(TEST_DESCRIPTION);
			pfs.setPerson(person);
			dao.save(pfs);

			// reload to check that save worked
			person = daoPerson.get(person.getId());
			pfss = person.getFundingSources();
			for (final PersonFundingSource tmp : pfss) {
				pfs = tmp;
			}
		} else {
			for (final PersonFundingSource tmp : pfss) {
				pfs = tmp;
				pfs.setDescription(TEST_DESCRIPTION);
			}
			daoPerson.save(person);
		}

		assertNotNull("A PersonFundingSource should have been found.", pfs);
		assertEquals("Description values did not match.", TEST_DESCRIPTION,
				pfs.getDescription());

		final PersonFundingSource byId = dao.get(pfs.getId());
		assertEquals("Ids did not match.", byId.getId(), pfs.getId());

		final UUID oldId = pfs.getId();
		pfss.clear();
		daoPerson.save(person);
		dao.delete(pfs);
		try {
			assertNull(
					"Person-funding source information was not correctly deleted.",
					dao.get(oldId));
			fail("ObjectNotFoundException should have been thrown."); // NOPMD
		} catch (final ObjectNotFoundException e) { // NOPMD
			// expected
		}
	}

	@Test
	public void testGetAll() {
		final PagingWrapper<PersonFundingSource> list = dao
				.getAll(ObjectStatus.ALL);
		assertNotNull("List should not have been null.", list);
	}
}