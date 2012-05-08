package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class SelfHelpGuideDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SelfHelpGuideDaoTest.class);

	@Autowired
	private transient SelfHelpGuideDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		SelfHelpGuide obj = new SelfHelpGuide();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setIntroductoryText("text");
		obj.setSummaryText("summary text");
		obj.setSummaryTextEarlyAlert("early alert summary");
		obj.setSummaryTextThreshold("threshold");
		obj.setThreshold(5);
		obj.setAuthenticationRequired(false);
		dao.save(obj);

		assertNotNull("Identifier should not have been null.", obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		Collection<SelfHelpGuide> all = dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		SelfHelpGuide selfHelpGuide = dao.get(id);

		assertNull(selfHelpGuide);
	}

	private void assertList(Collection<SelfHelpGuide> objects) {
		for (SelfHelpGuide object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		SelfHelpGuide obj = new SelfHelpGuide();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setIntroductoryText("text");
		obj.setSummaryText("summary text");
		obj.setSummaryTextEarlyAlert("early alert summary");
		obj.setSummaryTextThreshold("threshold");
		obj.setThreshold(5);
		obj.setAuthenticationRequired(false);
		dao.save(obj);

		SelfHelpGuide obj2 = new SelfHelpGuide();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		obj2.setIntroductoryText("text");
		obj2.setSummaryText("summary text");
		obj2.setSummaryTextEarlyAlert("early alert summary");
		obj2.setSummaryTextThreshold("threshold");
		obj2.setThreshold(5);
		obj2.setAuthenticationRequired(false);
		dao.save(obj2);

		LOGGER.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

	@Test
	public void findAllActiveForUnauthenticated() {
		assertList(dao.findAllActiveForUnauthenticated());
	}

	@Test
	public void findAllActiveBySelfHelpGuideGroup() {
		assertList(dao.findAllActiveBySelfHelpGuideGroup(UUID.randomUUID()));

	}

}
