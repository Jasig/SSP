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
import static org.junit.Assert.fail;

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

import com.google.common.collect.Lists;

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
	private ProgramStatus inactiveProgramStatus;

	@Before
	public void setUp() throws ObjectNotFoundException {
		activeProgramStatus = programStatusService.get(UUID
					.fromString("b2d12527-5056-a51a-8054-113116baab88"));
		inactiveProgramStatus = programStatusService.get(UUID
				.fromString("b2d125a4-5056-a51a-8042-d50b8eff0df1"));
	}

	@Test
	public void testNumberOfEntries() {
		final Collection<Person> list = dao.searchBy(
				null, null, true,
				"Gosling", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should not have been empty.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAllTTfirstName() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, null, true,
				"enneth", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should not have been empty.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAlTTlastName() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, null, true,
				"hompso", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should not have been empty.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAllTTschoolId() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, null, true,
				"ken.1", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should have had at least one entity.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAllTTfullName() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, null, true,
				"kenneth thompson", null,
				new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should have one entity.", list);
		assertEquals("Should only be one entry", 1, list.size());
	}

	@Test
	public void testGetAllTTfullNameWithOutsideCaseLoad() {
		final Collection<Person> list = dao.searchBy(
				activeProgramStatus, null, false,
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
				null, null, true,
				"james", turing, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertNotEmpty("List should have one entity.", list);
	}

	@Test
	public void testGetAllTTschoolIdNotRequiringProgramStatus() throws ObjectNotFoundException {
		// first a sanity check... should not be able to find this user with
		// the default requireProgramStatus flag
		final Collection<Person> bySchoolId = dao.searchBy(
				null, null, true,
				"turing.1", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertEquals("Default behavior should be to require a program status",
				Lists.newArrayListWithExpectedSize(0), bySchoolId);

		// now expectations for the real test
		final Person turing = personService.get(UUID
				.fromString("252de4a0-7c06-4254-b7d8-4ffc02fe81ff"));
		Collection<Person> expectedList = Lists.newArrayList(turing);

		// the real test
		final Collection<Person> bySchoolIdAndOptionalProgramStatus = dao.searchBy(
				null, false, true,
				"turing.1", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertEquals(expectedList, bySchoolIdAndOptionalProgramStatus);
	}

	@Test
	public void testGetAllTTschoolIdAndProgramStatus()
			throws ObjectNotFoundException {

		final Person ken1 = personService.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));
		Collection<Person> ken1List = Lists.newArrayList(ken1);

		// first two sanity checks... verify that this user has a program status,
		// then that filtering on program status works without specifying
		// the 'programStatusRequired' flag. Then we can know for sure that
		// the flag is genuinely ignored when set to false in combination with
		// a program status.
		final Collection<Person> bySchoolIdAndActiveProgramStaus= dao.searchBy(
				activeProgramStatus, null, true,
				"ken.1", null, new SortingAndPaging(ObjectStatus.ALL))
				.getRows();
		assertEquals(ken1List, bySchoolIdAndActiveProgramStaus);

		final Collection<Person> bySchoolIdAndInactiveProgramStaus = dao.searchBy(
				inactiveProgramStatus, null, true,
				"ken.1", null, new SortingAndPaging(ObjectStatus.ALL))
				.getRows();
		assertEquals("Unexpected results when specifying a program status"
				+ " but no program status required flag",
				Lists.newArrayListWithExpectedSize(0),
				bySchoolIdAndInactiveProgramStaus);

		final Collection<Person> bySchoolIdAndInactiveProgramStatusAndOptionalProgramStatus =
				dao.searchBy(inactiveProgramStatus, false, true,
					"ken.1", null, new SortingAndPaging(ObjectStatus.ALL))
					.getRows();
		assertEquals("Program status flag should be ignored if a program"
				+ " status is requested",
				Lists.newArrayListWithExpectedSize(0),
				bySchoolIdAndInactiveProgramStatusAndOptionalProgramStatus);
	}

}