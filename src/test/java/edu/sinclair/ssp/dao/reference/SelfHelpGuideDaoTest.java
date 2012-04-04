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
import edu.sinclair.ssp.model.reference.SelfHelpGuide;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class SelfHelpGuideDaoTest {

	private static final Logger logger = LoggerFactory
			.getLogger(SelfHelpGuideDaoTest.class);

	@Autowired
	private SelfHelpGuideDao dao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() {
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

		assertNotNull(obj.getId());
		saved = obj.getId();

		logger.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getName());

		List<SelfHelpGuide> all = dao.getAll(ObjectStatus.ACTIVE);
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test
	public void testNull() {
		UUID id = UUID.randomUUID();
		SelfHelpGuide selfHelpGuide = dao.get(id);

		assertNull(selfHelpGuide);
	}

	private void assertList(List<SelfHelpGuide> objects) {
		for (SelfHelpGuide object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
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

		logger.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		dao.delete(obj);
		dao.delete(obj2);
	}

}
