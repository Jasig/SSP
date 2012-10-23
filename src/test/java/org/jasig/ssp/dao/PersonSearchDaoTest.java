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
package org.jasig.ssp.dao;

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ProgramStatusService;
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
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonSearchDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonSearchDaoTest.class);

	@Autowired
	private transient PersonSearchDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient ProgramStatusService programStatusService;

	private ProgramStatus activeProgramStatus;

	@Before
	public void setUp() {
		try {
			activeProgramStatus = programStatusService.get(UUID
					.fromString("b2d12527-5056-a51a-8054-113116baab88"));
		} catch (ObjectNotFoundException e) {
			LOGGER.error("Active Program Status not found in db");
		}
	}

	@Test
	public void testNumberOfEntries() {
		final Collection<Person> list = dao.searchBy(
				null, true,
				"Gosling", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should not have been empty.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAllTTfirstName() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, true,
				"enneth", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should not have been empty.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAlTTlastName() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, true,
				"hompso", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should not have been empty.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAllTTschoolId() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, true,
				"ken.1", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should have had at least one entity.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAllTTfullName() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, true,
				"kenneth thompson", null,
				new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should have one entity.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAllTTfullNameWithOutsideCaseLoad() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, false,
				"kenneth thompson", null,
				new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertTrue("List should have been empty.", list.isEmpty());
	}

	@Test
	public void testGetAllTTprogramStatusAndAdvisor()
			throws ObjectNotFoundException {

		final Person turing = personService.get(UUID
				.fromString("252de4a0-7c06-4254-b7d8-4ffc02fe81ff"));

		final Collection<Person> list = dao.searchBy(
				null, true,
				"james", turing, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should have one entity.", list);
	}
}