package org.jasig.ssp.dao; // NOPMD by jon.adams on 5/16/12 9:59 PM

import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class MessageDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MessageDaoTest.class);

	@Autowired
	private transient MessageDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient PersonDao personDao;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test the {@link MessageDao#save(Message)} and
	 * {@link MessageDao#delete(Message)} actions.
	 */
	@Test
	public void testSaveNew() {
		final Message saved = dao.save(createTestMessage());

		assertNotNull("Id should have been automatically generated.",
				saved.getId());
		final UUID savedId = saved.getId();

		LOGGER.debug(saved.toString());

		try {
			final Message obj = dao.get(savedId);

			assertNotNull("Saved message could not reloaded.", obj);
			assertEquals("Saved and reloaded IDs do not match.", savedId,
					obj.getId());

			final Collection<Message> all = dao.getAll(ObjectStatus.ACTIVE)
					.getRows();
			assertNotNull("GetAll list should not have been null.", all);
			assertFalse("GetAll list should not have been empty.",
					all.isEmpty());
			assertList(all);

			dao.delete(obj);
		} catch (final ObjectNotFoundException e) {
			fail("Saved message could not be found to reload.");
		}
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Message obj = dao.get(id);

		assertNull("Random ID should not have loaded any object.", obj);
	}

	private void assertList(final Collection<Message> objects) {
		for (final Message object : objects) {
			assertNotNull("List should not have contained any null objects.",
					object.getId());
		}
	}

	@Test
	public void uuidGeneration() {
		final Message obj = dao.save(createTestMessage());
		final Message obj2 = dao.save(createTestMessage());

		assertNotNull("Generated ID should not have been null.", obj.getId());
		assertNotNull("Generated ID should not have been null.", obj2.getId());

		dao.delete(obj);
		dao.delete(obj2);
	}

	/**
	 * Test that the queued list does not return any null objects. (But can
	 * return an empty set.)
	 */
	@Test
	public void queued() {
		assertList(dao.queued(25));
	}

	@Test
	@Rollback
	public void queuedWithSortingAndPaging() throws InterruptedException {
		for ( int i = 0; i < 15; i++ ) {
			final Message msg = new Message("Subject " + i, "Body " + i,
					personDao.fromUsername("ken"),
					personDao.fromUsername("dmr"),
					"a@b.com");
			if ( i == 5 ) {
				// should skip this one in the 1st batch
				msg.setSentDate(new Date());
			} else if ( i == 12 ) {
				// should skip this one in the second batch
				msg.setObjectStatus(ObjectStatus.INACTIVE);
			}
			dao.save(msg);
			Thread.sleep(100); // make sure date sorting works predictably
		}

		// assert on first batch
		final SortingAndPaging sAndP1 = new SortingAndPaging(ObjectStatus.ACTIVE, 0,
				10, null, null, null);
		final PagingWrapper<Message> batch1 = dao.queued(sAndP1);
		assertEquals("Unexpected total unpaged result set size", 13,
				batch1.getResults());
		assertEquals("Unexpected initial batch size", 10, batch1.getRows().size());
		int i = 0;
		for ( final Message msg : batch1 ) {
			if ( i == 0 ) {
				assertEquals("Unexpected message sorted to head of first batch",
						"Subject 0", msg.getSubject());
			}
			if ( i == 9 ) {
				// should have skipped one previously created message
				assertEquals("Unexpected message sorted to end of first batch",
						"Subject 10", msg.getSubject());
			}
			i++;
		}

		// assert on second batch
		final SortingAndPaging sAndP2 = new SortingAndPaging(ObjectStatus.ACTIVE, 10,
				10, null, null, null);
		final PagingWrapper<Message> batch2 = dao.queued(sAndP2);
		assertEquals("Unexpected total unpaged result set size", 13,
				batch1.getResults());
		assertEquals("Unexpected second batch size", 3, batch2.getRows().size());
		int j = 0;
		for ( final Message msg : batch2 ) {
			if ( j == 0 ) {
				assertEquals("Unexpected message sorted to head of second batch",
						"Subject 11", msg.getSubject());
			}
			if ( j == 2 ) {
				// should have skipped one previously created message
				assertEquals("Unexpected message sorted to end of second batch",
						"Subject 14", msg.getSubject());
			}
			j++;
		}
	}

	/**
	 * Create a new sample message to use for testing.
	 * 
	 * @return a new sample message to use for testing
	 */
	private Message createTestMessage() {
		return new Message("test subject", "test body",
				personDao.fromUsername("ken"), personDao.fromUsername("dmr"),
				"a@b.com");
	}
}
