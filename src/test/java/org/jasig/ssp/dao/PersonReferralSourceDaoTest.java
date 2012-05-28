package org.jasig.ssp.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.dao.reference.ReferralSourceDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonReferralSource;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
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

/**
 * Tests for the {@link PersonReferralSourceDao} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonReferralSourceDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonReferralSourceDaoTest.class);

	@Autowired
	private transient PersonReferralSourceDao dao;

	@Autowired
	private transient ReferralSourceDao referralSourceDao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient SessionFactory sessionFactory;

	/**
	 * Initialize security and test data.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test {@link PersonReferralSourceDao#save(PersonReferralSource)} ,
	 * {@link PersonReferralSourceDao#get(UUID)},
	 * {@link PersonReferralSourceDao#getAll(ObjectStatus)}, and
	 * {@link PersonReferralSourceDao#delete(PersonReferralSource)}.
	 * 
	 * @throws ObjectNotFoundException
	 *             If saved instance could not be reloaded.
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		PersonReferralSource obj = new PersonReferralSource();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setPerson(securityService.currentUser().getPerson());
		obj.setReferralSource(referralSourceDao.get(UUID
				.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87")));
		obj = dao.save(obj);

		assertNotNull("Saved object should not have been null.", obj.getId());
		saved = obj.getId();

		// flush to storage, then clear out in-memory version
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		session.evict(obj);

		obj = dao.get(saved);
		LOGGER.debug("testSaveNew(): Saved " + obj.toString());
		assertNotNull("Reloaded object should not have been null.", obj);
		assertNotNull("Reloaded ID should not have been null.", obj.getId());

		final List<PersonReferralSource> all = (List<PersonReferralSource>) dao
				.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull("GetAll list should not have been null.", all);
		assertFalse("GetAll list should not have been empty.", all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	/**
	 * Test that invalid identifiers to
	 * {@link PersonReferralSourceDao#get(UUID)} correctly
	 * throw ObjectNotFound exception.
	 * 
	 * @throws ObjectNotFoundException
	 *             Expected to be thrown
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		dao.get(UUID.randomUUID());
		fail("Result of invalid get() should have thrown an exception.");
	}

	/**
	 * Test that all results from getAll are not null
	 */
	@Test
	public void getAllForPersonId() {
		assertList(dao.getAllForPersonId(UUID.randomUUID(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows());
	}

	private void assertList(final Collection<PersonReferralSource> objects) {
		for (final PersonReferralSource object : objects) {
			assertNotNull("Object in the list should not have been null.",
					object.getId());
		}
	}
}
