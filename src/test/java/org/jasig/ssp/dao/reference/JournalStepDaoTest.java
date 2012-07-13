package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.TestUtils;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.JournalStep;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test suites on the {@link JournalStepDao} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class JournalStepDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalStepDaoTest.class);

	@Autowired
	private transient JournalStepDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		JournalStep obj = new JournalStep();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setUsedForTransition(true);
		dao.save(obj);

		assertNotNull("Step should not have been null.", obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull("Saved instance should not have been null.", obj);
		assertNotNull("ID should not have been null.", obj.getId());
		assertNotNull("Name should not have been null.", obj.getName());
		assertTrue("UsedForTransition should have been true.",
				obj.isUsedForTransition());

		final Collection<JournalStep> all = dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull("List should not have been null.", all);
		assertFalse("List should not have been empty.", all.isEmpty());
		TestUtils.assertListDoesNotContainNullItems(all);

		dao.delete(obj);
	}

	@Test
	public void testGetAllForJournalTrack() {
		// arrange, act
		final PagingWrapper<JournalStep> all = dao.getAllForJournalTrack(
				UUID.randomUUID(), new SortingAndPaging(ObjectStatus.ACTIVE));

		// assert
		assertNotNull("List should not have been null.", all);
		TestUtils.assertListDoesNotContainNullItems(all.getRows());
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final JournalStep journalStep = dao.get(id);

		assertNull("JournalStep should not have been null.", journalStep);
	}

	@Test
	public void uuidGeneration() {
		final JournalStep obj = new JournalStep();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		final JournalStep obj2 = new JournalStep();
		obj2.setName("new name");
		obj2.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj2);

		LOGGER.debug("obj1 id: " + obj.getId().toString() + ", obj2 id: "
				+ obj2.getId().toString());

		assertNotNull("Obj should not have been null.", obj);
		assertNotNull("Obj2 should not have been null.", obj2);
	}
}