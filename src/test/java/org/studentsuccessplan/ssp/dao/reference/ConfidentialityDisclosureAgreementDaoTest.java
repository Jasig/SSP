package org.studentsuccessplan.ssp.dao.reference;

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

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.studentsuccessplan.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ConfidentialityDisclosureAgreementDaoTest {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfidentialityDisclosureAgreementDaoTest.class);

	@Autowired
	private ConfidentialityDisclosureAgreementDao dao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() {
		UUID saved;

		ConfidentialityDisclosureAgreement obj = new ConfidentialityDisclosureAgreement();
		obj.setName("new name");
		obj.setText("text");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		logger.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		List<ConfidentialityDisclosureAgreement> all = dao
				.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		UUID id = UUID.randomUUID();
		ConfidentialityDisclosureAgreement confidentialityDisclosureAgreement = dao
				.get(id);

		assertNull(confidentialityDisclosureAgreement);
	}

	private void assertList(List<ConfidentialityDisclosureAgreement> objects) {
		for (ConfidentialityDisclosureAgreement object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}

	@Test
	public void uuidGeneration() {
		ConfidentialityDisclosureAgreement obj = new ConfidentialityDisclosureAgreement();
		obj.setName("new name");
		obj.setText("text");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		ConfidentialityDisclosureAgreement obj2 = new ConfidentialityDisclosureAgreement();
		obj2.setName("new name");
		obj2.setText("text");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj2);

		logger.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

}
