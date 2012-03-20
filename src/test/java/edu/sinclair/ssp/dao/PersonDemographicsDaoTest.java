package edu.sinclair.ssp.dao;

import static org.junit.Assert.assertEquals;
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

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.PersonDemographics;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDemographicsDaoTest {

	//private static final Logger logger = LoggerFactory.getLogger(PersonDemographicsDaoTest.class);

	@Autowired
	private PersonDemographicsDao dao;
	
	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup(){
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGet() {
		// test student = ken thompson
		Person person = new Person(UUID.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		
		PersonDemographics pd = dao.forPerson(person);
		if(null==pd){
			pd = new PersonDemographics();
			pd.setPerson(person);
			dao.save(pd);
			pd = dao.forPerson(person);
		}
		
		assertEquals(pd.getPerson().getId(), person.getId());
		
		PersonDemographics byId = dao.get(pd.getId());
		assertEquals(byId.getId(), pd.getId());
		
		dao.delete(pd);
		assertNull(dao.forPerson(person));
	}
	
	@Test
	public void testNull(){
		UUID id = UUID.randomUUID();
		PersonDemographics pd = dao.get(id);
		assertNull(pd);
		
		pd = dao.forPerson(new Person(id));
		assertNull(pd);
	}
	
	@Test
	public void testGetAll(){
		dao.getAll(ObjectStatus.ALL);
		assertTrue(true);
	}


}
