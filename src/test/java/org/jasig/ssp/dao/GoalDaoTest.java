/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.TestUtils;
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

import com.google.common.collect.Lists;

/**
 * Tests for the {@link GoalDao} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class GoalDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GoalDaoTest.class);

	@Autowired
	private transient GoalDao dao;

	private static final UUID TEST_GOAL_ID1 = UUID
			.fromString("1B18BF52-BFC7-11E1-9CB8-0026B9E7FF4C");

	private static final UUID KEN = UUID
			.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea");

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient SessionFactory sessionFactory;

	private transient ConfidentialityLevel testConfidentialityLevel;

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
		TestUtils.assertListDoesNotContainNullItems(all);

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
		TestUtils.assertListDoesNotContainNullItems(dao.getAllForPersonId(
				UUID.randomUUID(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows());
	}

	/**
	 * A user with all confidentiality levels accessing the goal
	 */
	@Test
	public void getAllForPersonIdAllLevels() {
		final PagingWrapper<Goal> goals = dao.getAllForPersonId(
				KEN,
				securityService.currentUser(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE));
		TestUtils.assertListDoesNotContainNullItems(goals.getRows());
		assertTrue("Goals should not be empty.", goals.getResults() > 0);
	}

	/**
	 * A user with only the create attribute (no confidentiality levels)
	 * accessing the goal
	 */
	@Test
	public void getAllForPersonIdNoLevels() {
		securityService.setCurrent(new Person(KEN));
		final PagingWrapper<Goal> goals = dao.getAllForPersonId(
				KEN,
				securityService.currentUser(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE));
		TestUtils.assertListDoesNotContainNullItems(goals.getRows());
		assertTrue("Results should not have been empty.",
				goals.getResults() > 0);
	}

	@Test
	public void getGoalsInList() {
		final List<UUID> goalIds = Lists.newArrayList();
		goalIds.add(UUID.fromString("1B18BF52-BFC7-11E1-9CB8-0026B9E7FF4C"));
		goalIds.add(UUID.randomUUID());
		final List<Goal> goals = dao.get(goalIds,
				securityService.currentlyAuthenticatedUser(),
				new SortingAndPaging(ObjectStatus.ACTIVE));
		TestUtils.assertListDoesNotContainNullItems(goals);
		assertFalse("Goal list should not have been empty.", goals.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWithEmptyList() {
		dao.get(new ArrayList<UUID>(), securityService.currentUser(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWithNullList() {
		dao.get(null, securityService.currentUser(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWithNullUser() {
		// arrange
		final List<UUID> list = Lists.newArrayList();
		list.add(TEST_GOAL_ID1);

		// act
		dao.get(list, null, new SortingAndPaging(ObjectStatus.ALL));
	}

	@Test
	public void testGetList() {
		// arrange
		final List<UUID> list = Lists.newArrayList();
		list.add(TEST_GOAL_ID1);

		// act
		final List<Goal> result = dao.get(list, securityService.currentUser(),
				new SortingAndPaging(ObjectStatus.ALL));

		// assert
		assertEquals(
				"Result list did not contain the expected number of items.", 1,
				result.size());
	}
}