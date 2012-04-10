package edu.sinclair.ssp.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.Message;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class MessageDaoTest {

	private static final Logger logger = LoggerFactory
			.getLogger(MessageDaoTest.class);

	@Autowired
	private MessageDao dao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Autowired
	private PersonDao personDao;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		Message obj = new Message();
		obj.setSubject("test subject");
		obj.setBody("test body");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setSender(personDao.fromUsername("ken"));
		obj.setRecipient(personDao.fromUsername("dmr"));
		obj.setRecipientEmailAddress("a@b.com");
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		logger.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());

		List<Message> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		UUID id = UUID.randomUUID();
		Message fundingSource = dao.get(id);

		assertNull(fundingSource);
	}

	private void assertList(List<Message> objects) {
		for (Message object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}

	@Test
	public void uuidGeneration() {
		Message obj = new Message();
		obj.setSubject("test subject");
		obj.setBody("test body");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setSender(personDao.fromUsername("ken"));
		obj.setRecipient(personDao.fromUsername("dmr"));
		obj.setRecipientEmailAddress("a@b.com");
		dao.save(obj);

		Message obj2 = new Message();
		obj2.setSubject("test subject");
		obj2.setBody("test body");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		obj2.setSender(personDao.fromUsername("ken"));
		obj2.setRecipient(personDao.fromUsername("dmr"));
		obj2.setRecipientEmailAddress("a@b.com");
		dao.save(obj2);

		logger.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

	@Test
	public void queued() {
		assertList(dao.queued());
	}

}
