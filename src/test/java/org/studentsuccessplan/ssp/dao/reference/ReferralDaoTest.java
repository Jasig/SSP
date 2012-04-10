package edu.sinclair.ssp.dao.reference;

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

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.Referral;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ReferralDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReferralDaoTest.class);

	@Autowired
	transient private ReferralDao dao;

	@Autowired
	transient private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		Referral obj = new Referral();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull("obj.id should not have been null.", obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		List<Referral> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(!all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		final UUID id = UUID.randomUUID();
		Referral referral = dao.get(id);

		assertNull(referral);
	}

	private void assertList(List<Referral> objects) {
		for (Referral object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		Referral obj = new Referral();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		Referral obj2 = new Referral();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj2);

		LOGGER.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

}
