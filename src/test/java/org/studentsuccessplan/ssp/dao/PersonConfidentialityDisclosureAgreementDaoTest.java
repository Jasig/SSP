package edu.sinclair.ssp.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonConfidentialityDisclosureAgreement;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonConfidentialityDisclosureAgreementDaoTest {

	@Autowired
	private PersonConfidentialityDisclosureAgreementDao dao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Autowired
	private ConfidentialityDisclosureAgreementDao confidentialityDao;

	@Autowired
	private PersonService personService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void forStudent() {
		List<PersonConfidentialityDisclosureAgreement> agreements = null;
		try {
			agreements = dao
					.forStudent(personService.personFromUsername("ken"));
		} catch (ObjectNotFoundException e) {
			// this is really not likely
			fail("threw an Object not found exception");
		}

		assertList(agreements);
	}

	@Test
	public void forStudentAndAgreement() {
		PersonConfidentialityDisclosureAgreement agreement = null;
		try {
			agreement = dao
					.forStudentAndAgreement(
							personService.personFromUsername("ken"),
							confidentialityDao.get(UUID
									.fromString("06919242-824c-11e1-af98-0026b9e7ff4c")));
		} catch (ObjectNotFoundException e) {
			// this is really not likely
			fail("threw an Object not found exception");
		}

		assertNull(agreement);
	}

	private void assertList(
			List<PersonConfidentialityDisclosureAgreement> objects) {
		for (PersonConfidentialityDisclosureAgreement object : objects) {
			assertNotNull(object.getId());
		}
	}

}
