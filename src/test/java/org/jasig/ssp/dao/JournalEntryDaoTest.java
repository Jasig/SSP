package org.jasig.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.jasig.ssp.dao.reference.ConfidentialityLevelDao;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Ignore;
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
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class JournalEntryDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalEntryDaoTest.class);
	private static final UUID CONFIDENTIALITYLEVEL_ID = UUID
			.fromString("afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c");

	private static final String CONFIDENTIALITYLEVEL_NAME = "Test Confidentiality Level";

	@Autowired
	private transient JournalEntryDao dao;

	@Autowired
	private transient ConfidentialityLevelDao confidentialityLevelDao;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	protected transient SecurityServiceInTestEnvironment securityService;

	protected transient Person ken;

	@Before
	public void setUp() throws ObjectNotFoundException {
		try {
			ken = personService.personFromUsername("ken");
		} catch (ObjectNotFoundException e) {
			LOGGER.error("can't find one of either sysadmin or ken");
		}
		securityService.setCurrent(ken);
	}

	@Test
	public void getAllForPersonId() {
		assertList(dao.getAllForPersonId(ken.getId(), new SortingAndPaging(
				ObjectStatus.ACTIVE)).getRows());
	}

	protected void assertList(final Collection<JournalEntry> objects) {
		for (JournalEntry object : objects) {
			assertNotNull(object.getId());
		}
	}

	// :TODO need test data before reenabling
	@Ignore
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		JournalEntry obj = new JournalEntry();
		obj.setComment("new comment");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setPerson(securityService.currentUser().getPerson());
		obj.setConfidentialityLevel(confidentialityLevelDao
				.load(CONFIDENTIALITYLEVEL_ID));
		obj.setEntryDate(new Date());
		dao.save(obj);

		assertNotNull("obj.id should not have been null.", obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());
		assertNotNull(obj.getComment());
		assertEquals("Confidentiality level name did not match.",
				CONFIDENTIALITYLEVEL_NAME, obj.getConfidentialityLevel()
						.getName());

		final Collection<JournalEntry> all = dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull(all);
		assertFalse(all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}
}
