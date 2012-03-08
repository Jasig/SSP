package edu.sinclair.ssp.dao.reference;

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

import edu.sinclair.ssp.dao.PersonDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.Ethnicity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class EthnicityDaoTest {

	private static final Logger logger = LoggerFactory.getLogger(EthnicityDaoTest.class);

	@Autowired
	private EthnicityDao dao;
	
	@Autowired
	private PersonDao personDao;

	@Test
	public void testSaveNew(){
		UUID saved;
		
		Ethnicity obj = new Ethnicity();
		obj.setCreatedDate(new Date());
		obj.setCreatedBy(personDao.get(Person.SYSTEM_ADMINISTRATOR_ID));
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);
		
		assertNotNull(obj.getId());
		saved = obj.getId();
		
		logger.debug(obj.toString());
		
		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		List<Ethnicity> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(all.size()>0);
		assertList(all);
		
		dao.delete(obj);
	}

	@Test
	public void testNull(){
		UUID id = UUID.randomUUID();
		Ethnicity ethnicity = dao.get(id);
		
		assertNull(ethnicity);
	}

	private void assertList(List<Ethnicity> objects){
		for(Ethnicity object : objects){
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}

}
