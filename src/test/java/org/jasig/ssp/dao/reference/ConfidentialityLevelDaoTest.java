package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
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
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ConfidentialityLevelDaoTest {

	@Autowired
	private transient ConfidentialityLevelDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ConfidentialityLevel confidentialityLevel = dao.get(id);

		assertNull(confidentialityLevel);
	}
}
