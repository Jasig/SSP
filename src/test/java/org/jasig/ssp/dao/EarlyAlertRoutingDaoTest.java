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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
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
 * Tests for the {@link EarlyAlertRoutingDao} class.
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class EarlyAlertRoutingDaoTest {

	public static final UUID CAMPUS_ID = UUID
			.fromString("901e104b-4dc7-43f5-a38e-581015e204e1");

	public static final UUID EARLY_ALERT_REASON_ID = UUID
			.fromString("b2d11335-5056-a51a-80ea-074f8fef94ea");

	public static final String EARLY_ALERT_REASON_NAME = "Other";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertRoutingDaoTest.class);

	@Autowired
	private transient EarlyAlertRoutingDao dao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient CampusService campusService;

	@Autowired
	private transient EarlyAlertReasonService earlyAlertReasonService;

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
	 * Test {@link EarlyAlertRoutingDao#save(EarlyAlertRouting)},
	 * {@link EarlyAlertRoutingDao#get(UUID)},
	 * {@link EarlyAlertRoutingDao#getAll(ObjectStatus)}, and
	 * {@link EarlyAlertRoutingDao#delete(EarlyAlertRouting)}.
	 * 
	 * @throws ObjectNotFoundException
	 *             If saved instance could not be reloaded.
	 */
	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		EarlyAlertRouting obj = new EarlyAlertRouting();
		obj.setGroupName("new name");
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		obj.setCampus(campusService.get(CAMPUS_ID));
		obj.setEarlyAlertReason(earlyAlertReasonService
				.get(EARLY_ALERT_REASON_ID));
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
		assertNotNull("Reloaded name should not have been null.",
				obj.getGroupName());
		assertEquals("EarlyAlertReason names do not match.",
				EARLY_ALERT_REASON_NAME, obj.getEarlyAlertReason().getName());

		final List<EarlyAlertRouting> all = (List<EarlyAlertRouting>) dao
				.getAll(ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull("GetAll list should not have been null.", all);
		assertFalse("GetAll list should not have been empty.", all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	/**
	 * Test that invalid identifiers to {@link EarlyAlertRoutingDao#get(UUID)}
	 * correctly throw ObjectNotFound exception.
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
	public void getAllForCampusId() {
		assertList(dao.getAllForCampusId(UUID.randomUUID(),
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows());
	}

	private void assertList(final Collection<EarlyAlertRouting> objects) {
		for (final EarlyAlertRouting object : objects) {
			assertNotNull("Object in the list should not have been null.",
					object.getId());
		}
	}
}