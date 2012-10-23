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

import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
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
public class PersonStaffDetailsDaoTest {

	@Autowired
	private transient PersonDao daoPerson;

	@Autowired
	private transient PersonStaffDetailsDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final String TEST_STRING1 = "test string 1";

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

		PersonStaffDetails psd = person.getStaffDetails();

		if (null == psd) {
			psd = new PersonStaffDetails();
			psd.setOfficeLocation(TEST_STRING1);
			person.setStaffDetails(psd);
			daoPerson.save(person);

			// reload to check that save worked
			person = daoPerson.get(person.getId());
			psd = person.getStaffDetails();
		} else {
			psd.setOfficeLocation(TEST_STRING1);
			daoPerson.save(person);
		}

		assertEquals("Details location values did not match.", TEST_STRING1,
				psd.getOfficeLocation());

		final PersonStaffDetails byId = dao.get(psd.getId());
		assertEquals("IDs did not match.", byId.getId(), psd.getId());

		final UUID oldId = psd.getId();
		person.setStaffDetails(null);
		daoPerson.save(person);
		dao.delete(psd);

		try {
			assertNull("Detail information was not correctly deleted.",
					dao.get(oldId));
		} catch (final ObjectNotFoundException e) { // NOPMD
			// expected
		}
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final PersonStaffDetails pd = dao.get(id);
		assertNull("PersonStaffDetails should not have been null.", pd);
		assertNull("Person FK to staff details should not have been null.",
				new Person(id).getStaffDetails());
	}

	@Test
	public void testGetAll() {
		assertNotNull("GetAll should not have returned null.",
				dao.getAll(ObjectStatus.ALL));
	}
}