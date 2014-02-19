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
package org.jasig.ssp.dao; // NOPMD by jon.adams

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDaoTest { // NOPMD Test suites love lots of methods!

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonDaoTest.class);

	private static final UUID PERSON_ID = UUID
			.fromString("F549ECAB-5110-4CC1-B2BB-369CAC854DEA");
	
	private static final String PERSON_USER_ID = "ken";

	@Autowired
	private transient PersonDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user for use by
	 * {@link #testSaveNew()} that checks that the Auditable auto-fill
	 * properties are correctly filled.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAll() {
		final Collection<Person> list = dao.getAll(ObjectStatus.ALL).getRows();
		assertTrue("List should have included multiple entities.",
				list.size() > 1);
	}

	@Test
	public void testGetAllWithRowFilter() {
		final Collection<Person> listAll = dao.getAll(ObjectStatus.ALL)
				.getRows();
		final Collection<Person> listFiltered = dao.getAll(
				new SortingAndPaging(ObjectStatus.ALL, 1, 2, null, "lastName",
						SortDirection.ASC)).getRows();

		assertTrue("List should have included multiple entities.",
				listAll.size() > 2);

		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());

		assertNotEquals(
				"The filtered list should have included a different number of entities then the unfiltered list.",
				listFiltered.size(), listAll.size());
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		assertEquals("Ids did not match.", Person.SYSTEM_ADMINISTRATOR_ID, dao
				.get(Person.SYSTEM_ADMINISTRATOR_ID).getId());
	}

	@Test
	public void testGetFromUserId() {
		// act
		final Person person = dao.fromUsername(PERSON_USER_ID);

		// assert
		assertNotNull("Person should not have been null.", person);
		assertEquals("IDs did not match.", PERSON_ID, person.getId());
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		// arrange
		final Person obj = new Person();
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setFirstName("System");
		obj.setLastName("User");
		obj.setUsername("username");
		obj.setSchoolId("school id");
		obj.setPrimaryEmailAddress("user@sinclair.edu");

		// act
		final Person saved = dao.save(obj);

		// assert
		assertNotNull("", obj.getId());

		LOGGER.debug(obj.toString());

		final Person loaded = dao.get(saved.getId());
		assertNotNull("Loaded ID should not have been null.", loaded.getId());


		final Collection<Person> all = dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertFalse("GetAll() result should not have been empty.",
				all.isEmpty());
		assertList(all);

		dao.delete(loaded);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Person person = dao.get(id);

		assertNull("Person should not have been null.", person);
	}

	@Test
	public void testFromUsername() {
		assertEquals("Ids did not match.", Person.SYSTEM_ADMINISTRATOR_ID, dao
				.fromUsername("system").getId());
	}

	@Test
	public void getPeopleInList() throws ValidationException {
		final List<UUID> personIds = Lists.newArrayList();
		personIds.add(UUID.randomUUID());
		personIds.add(UUID.randomUUID());
		personIds.add(UUID.randomUUID());
		assertList(dao.getPeopleInList(personIds, new SortingAndPaging(
				ObjectStatus.ACTIVE)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWithEmptyList() {
		dao.get(new ArrayList<UUID>(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWithNullList() {
		dao.get(null, null);
	}

	@Test
	public void testGetList() {
		// arrange
		final List<UUID> list = Lists.newArrayList();
		list.add(Person.SYSTEM_ADMINISTRATOR_ID);

		// act
		final PagingWrapper<Person> result = dao.get(list,
				new SortingAndPaging(ObjectStatus.ALL));

		// assert
		assertEquals(
				"Result list did not contain the expected number of items.", 1,
				result.getResults());
	}

	private void assertList(final Collection<Person> objects) {
		for (final Person object : objects) {
			assertNotNull("List item should not have been null.",
					object.getId());
		}
	}

	@Test(expected = ObjectNotFoundException.class)
	public void getBySchoolIdException() throws ObjectNotFoundException {
		dao.getBySchoolId("borkborkbork");
	}

	@Test
	public void getBySchoolId() throws ObjectNotFoundException {
		final Person person = dao.getBySchoolId("ken.1");
		assertNotNull("ken should have been found", person);
		assertEquals("first name should be set", "Kenneth",
				person.getFirstName());
	}
	

	@Test
	public void testGetAllAssignedCoaches() throws ObjectNotFoundException {
		Person advisor0 = dao.get(Stubs.PersonFixture.ADVISOR_0.id());
		PagingWrapper<Person> results = dao.getAllAssignedCoaches(null);
		assertEquals(advisor0, results.getRows().iterator().next());
		assertEquals(0, results.getResults()); // 0 b/c no paging
	}

	@Test
	public void testGetAllAssignedCoachesWithSimplePaging() throws ObjectNotFoundException {
		Person advisor0 = dao.get(Stubs.PersonFixture.ADVISOR_0.id());
		PagingWrapper<Person> results = dao.getAllAssignedCoaches(
				new SortingAndPaging(ObjectStatus.ALL, 0, 10, null, null, null));
		assertEquals(advisor0, results.getRows().iterator().next());
		assertEquals(2, results.getResults()); //Changed because data has been added 2/8/2013 J. Stanley
	}
}
