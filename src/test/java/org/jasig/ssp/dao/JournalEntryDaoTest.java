package org.jasig.ssp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.jasig.ssp.dao.reference.JournalSourceDao;
import org.jasig.ssp.dao.reference.JournalTrackDao;
import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
	private transient JournalSourceDao journalSourceDao;

	@Autowired
	private transient JournalTrackDao journalTrackDao;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	protected transient SecurityServiceInTestEnvironment securityService;

	protected transient Person ken;

	@Before
	public void setUp() throws ObjectNotFoundException {
		try {
			ken = personService.personFromUsername("ken");
		} catch (final ObjectNotFoundException e) {
			LOGGER.error("can't find one of either sysadmin or ken");
		}
		securityService.setCurrent(ken,
				confidentialityLevelService
						.confidentialityLevelsAsGrantedAuthorities());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void getAllForPersonIdWithoutRequestor() {
		assertList(dao.getAllForPersonId(UUID.randomUUID(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows());
	}

	/**
	 * A user with all confidentiality levels accessing the goal
	 * 
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void getAllForPersonIdAllLevels() throws ObjectNotFoundException {
		final PagingWrapper<JournalEntry> entries = dao.getAllForPersonId(
				Person.SYSTEM_ADMINISTRATOR_ID,
				securityService.currentUser(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE));
		assertList(entries.getRows());
		assertTrue("Entries should not have been empty.",
				entries.getResults() > 0);
	}

	protected void assertList(final Collection<JournalEntry> objects) {
		for (final JournalEntry object : objects) {
			assertNotNull("List item should not have been null.",
					object.getId());
		}
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		JournalEntry obj = new JournalEntry();
		obj.setComment("new comment");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setPerson(securityService.currentUser().getPerson());
		obj.setConfidentialityLevel(confidentialityLevelService
				.get(CONFIDENTIALITYLEVEL_ID));
		obj.setEntryDate(new Date());
		obj.setJournalSource(journalSourceDao.get(UUID
				.fromString("b2d07973-5056-a51a-8073-1d3641ce507f")));
		obj.setJournalTrack(journalTrackDao.get(UUID
				.fromString("b2d07a7d-5056-a51a-80a8-96ae5188a188")));
		dao.save(obj);

		assertNotNull("obj.id should not have been null.", obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull("Saved object should not have been null.", obj);
		assertNotNull("Saved ID should not have been null.", obj.getId());
		assertNotNull("Saved comment should not have been null.",
				obj.getComment());
		assertEquals("Confidentiality level name did not match.",
				CONFIDENTIALITYLEVEL_NAME, obj.getConfidentialityLevel()
						.getName());

		final Collection<JournalEntry> all = dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull("GetAll() result should not have been null.", all);
		assertFalse("GetAll() result should not have been empty.",
				all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}
}