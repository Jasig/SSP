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
package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Campus;
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

/**
 * Tests for {@link CampusDao}.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration
@Transactional
public class CampusDaoTest {

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	private transient CampusDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service for the tests
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	/**
	 * Test {@link CampusDao#save(Campus)}
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown for this test
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID savedId;

		Campus obj = new Campus();
		obj.setName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setEarlyAlertCoordinatorId(UUID.randomUUID());
		obj = dao.save(obj);

		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		savedId = obj.getId();

		session.clear();

		final Campus saved = dao.get(savedId);
		assertNotNull("Reloading did not return the correct saved data.", saved);
		assertNotNull("Reloaded data did not have a set Name property.",
				saved.getName());
		assertEquals("Reloaded data did not have matching data.",
				obj.getName(), saved.getName());

		final Collection<Campus> all = dao.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull("GetAll() should not have returned a null collection.",
				all);
		assertFalse(
				"GetAll() should not have returned an empty list. (This test assumes some sample reference data exists in the testing database.)",
				all.isEmpty());
		assertList(all);

		dao.delete(saved);
	}

	/**
	 * Test that {@link CampusDao#get(UUID)} returns null if identifier is not
	 * found.
	 * 
	 * @throws ObjectNotFoundException
	 *             expected
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testNotFoundIdReturnsNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Campus campus = dao.get(id);

		assertNull(
				"Get() did not return null when a missing identifier was used.",
				campus);
	}

	/**
	 * Asserts that list contains objects with non-null identifiers.
	 * 
	 * @param objects
	 *            Collection of objects to assert have non-null identifiers.
	 */
	private void assertList(final Collection<Campus> objects) {
		assertFalse("List should not have been empty.", objects.isEmpty());

		for (final Campus object : objects) {
			assertNotNull("List item should not have a null id.",
					object.getId());
		}
	}

	/**
	 * Test UUID generation, {@link CampusDao#save(Campus)}.
	 */
	@Test
	public void uuidGeneration() {
		final Campus obj = new Campus();
		obj.setName("A name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setEarlyAlertCoordinatorId(UUID.randomUUID());
		dao.save(obj);
		sessionFactory.getCurrentSession().flush();

		assertNotNull("Transient instance was not assigned a new identifier.",
				obj.getId());
	}
}
