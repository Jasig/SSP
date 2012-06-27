package org.jasig.ssp.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.service.ObjectNotFoundException;
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

/**
 * Tests for the {@link GoalDao} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class GoalDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GoalDaoTest.class);

	@Autowired
	private transient GoalDao dao;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient SessionFactory sessionFactory;

	private transient ConfidentialityLevel testConfidentialityLevel;

	private static final UUID KEN = UUID
			.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea");

	/**
	 * Initialize security and test data.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				confidentialityLevelService
						.confidentialityLevelsAsGrantedAuthorities());

		testConfidentialityLevel = confidentialityLevelService
				.getAll(new SortingAndPaging(ObjectStatus.ACTIVE)).getRows()
				.iterator().next();
	}

	/**
	 * Test {@link GoalDao#save(Goal)}, {@link GoalDao#get(UUID)},
	 * {@link GoalDao#getAll(ObjectStatus)}, and {@link GoalDao#delete(Goal)}.
	 * 
	 * @throws ObjectNotFoundException
	 *             If saved instance could not be reloaded.
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		Goal obj = new Goal();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setConfidentialityLevel(testConfidentialityLevel);
		obj.setPerson(securityService.currentUser().getPerson());
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
		assertNotNull("Reloaded name should not have been null.", obj.getName());

		final List<Goal> all = (List<Goal>) dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull("GetAll list should not have been null.", all);
		assertFalse("GetAll list should not have been empty.", all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	/**
	 * Test that invalid identifiers to {@link GoalDao#get(UUID)} correctly
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

	@Test(expected = UnsupportedOperationException.class)
	public void getAllForPersonIdWithoutRequestor() {
		assertList(dao.getAllForPersonId(UUID.randomUUID(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows());
	}

	/**
	 * A user with all confidentiality levels accessing the goal
	 */
	@Test
	public void getAllForPersonIdAllLevels() throws ObjectNotFoundException {
		final PagingWrapper<Goal> goals = dao.getAllForPersonId(
				KEN,
				securityService.currentUser(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE));
		assertList(goals.getRows());
		assertTrue(goals.getResults() > 0);
	}

	/**
	 * A user with only the create attribute (no conf levels) accessing the goal
	 */
	@Test
	public void getAllForPersonIdNoLevels() throws ObjectNotFoundException {
		securityService.setCurrent(new Person(KEN));
		final PagingWrapper<Goal> goals = dao.getAllForPersonId(
				KEN,
				securityService.currentUser(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE));
		assertList(goals.getRows());
		assertTrue(goals.getResults() > 0);
	}

	private void assertList(final Collection<Goal> objects) {
		for (final Goal object : objects) {
			assertNotNull("Object in the list should not have been null.",
					object.getId());
		}
	}
}