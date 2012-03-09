package edu.sinclair.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDaoTest {

	private static final Logger logger = LoggerFactory.getLogger(PersonDaoTest.class);

	@Autowired
	private PersonDao dao;

	@Test
	public void testGet(){
		assertEquals(Person.SYSTEM_ADMINISTRATOR_ID, dao.get(Person.SYSTEM_ADMINISTRATOR_ID).getId());
	}

	@Test
	public void testSaveNew(){
		UUID saved;
		
		Person obj = new Person();
		obj.setCreatedDate(new Date());
		obj.setCreatedBy(dao.get(Person.SYSTEM_ADMINISTRATOR_ID));
		obj.setModifiedDate(new Date());
		obj.setModifiedBy(dao.get(Person.SYSTEM_ADMINISTRATOR_ID));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setFirstName("System");
		obj.setLastName("User");
		obj.setPrimaryEmailAddress("user@sinclair.edu");
		dao.save(obj);
		
		assertNotNull(obj.getId());
		saved = obj.getId();
		
		logger.debug(obj.toString());
		
		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());

		List<Person> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(all.size()>0);
		assertList(all);
		
		dao.delete(obj);
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

	private void assertList(List<Person> objects){
		for(Person object : objects){
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}
}
