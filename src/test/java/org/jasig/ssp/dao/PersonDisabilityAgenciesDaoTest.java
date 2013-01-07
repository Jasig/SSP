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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDisabilityAgency;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonDisabilityAgenciesDaoTest {

	@Autowired
	private transient PersonDao daoPerson;

	@Autowired
	private transient PersonDisabilityAgencyDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_DESCRIPTION = "some description";

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		Person person = daoPerson.get(UUID
				.fromString("f549ecab-5110-4cc1-b2bb-369cac854dea"));

		assertNotNull(
				"Sample data should have allowed the user Ken Thompson to be loaded for UUID f549ecab-5110-4cc1-b2bb-369cac854dea",
				person);

		Set<PersonDisabilityAgency> pdas = person.getDisabilityAgencies();
		PersonDisabilityAgency pda = new PersonDisabilityAgency();

		if (pdas.isEmpty()) {
			pda = new PersonDisabilityAgency();
			pda.setDescription(TEST_DESCRIPTION + "2");
			pda.setPerson(person);
			dao.save(pda);

			pda = new PersonDisabilityAgency();
			pda.setDescription(TEST_DESCRIPTION);
			pda.setPerson(person);
			dao.save(pda);

			// reload to check that save worked
			person = daoPerson.get(person.getId());
			pdas = person.getDisabilityAgencies();
			for (final PersonDisabilityAgency tmp : pdas) {
				pda = tmp;
			}
		} else {
			for (final PersonDisabilityAgency tmp : pdas) {
				pda = tmp;
				pda.setDescription(TEST_DESCRIPTION);
			}
			daoPerson.save(person);
		}

		assertNotNull("A PersonDisabilityAgency should have been found.", pda);
		assertEquals("Description values did not match.", TEST_DESCRIPTION,
				pda.getDescription());

		final PersonDisabilityAgency byId = dao.get(pda.getId());
		assertEquals("Ids did not match.", byId.getId(), pda.getId());

		final UUID oldId = pda.getId();
		pdas.clear();
		daoPerson.save(person);
		dao.delete(pda);
		try {
			assertNull(
					"Person-Disability Agency information was not correctly deleted.",
					dao.get(oldId));
			fail("ObjectNotFoundException should have been thrown."); // NOPMD
		} catch (final ObjectNotFoundException e) { // NOPMD
			// expected
		}
	}

	@Test
	public void testGetAll() {
		final PagingWrapper<PersonDisabilityAgency> list = dao
				.getAll(ObjectStatus.ALL);
		assertNotNull("List should not have been null.", list);
	}
}