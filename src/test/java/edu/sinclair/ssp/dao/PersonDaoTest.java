package edu.sinclair.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDaoTest {

	//private static final Logger logger = LoggerFactory.getLogger(PersonDaoTest.class);

	@Autowired
	private PersonDao dao;

	@Test
	public void testGet(){
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID, dao.get(Person.SYSTEM_ADMINISTRATOR_ID).getId());
	}
	
	@Test
	public void testNull(){
		UUID id = UUID.randomUUID();
		Person person = dao.get(id);
		
		assertNull(person);
	}
	
	@Test
	public void testFromUsername(){
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID, dao.fromUsername("system").getId());
	}

}
