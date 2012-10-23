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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class JournalTrackJournalStepDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalTrackJournalStepDaoTest.class);

	@Autowired
	private transient JournalTrackJournalStepDao dao;

	@Autowired
	private transient JournalStepDao journalStepDao;

	@Autowired
	private transient JournalTrackDao journalTrackDao;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		JournalTrackJournalStep obj = new JournalTrackJournalStep();
		obj.setJournalStep(journalStepDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next());
		obj.setJournalTrack(journalTrackDao.get(UUID
				.fromString("b2d07b38-5056-a51a-809d-81ea2f3b27bf")));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());

		final Collection<JournalTrackJournalStep> all = dao.getAll(
				ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull(all);
		assertTrue(all.size() > 0);
		assertList(all);

		dao.delete(obj);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final JournalTrackJournalStep journalStepJournalTrack = dao.get(id);

		assertNull(journalStepJournalTrack);
	}

	private void assertList(final Collection<JournalTrackJournalStep> objects) {
		for (final JournalTrackJournalStep object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() throws ObjectNotFoundException {
		final JournalTrackJournalStep obj = new JournalTrackJournalStep();
		obj.setJournalStep(journalStepDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next());
		obj.setJournalTrack(journalTrackDao.get(UUID
				.fromString("b2d07b38-5056-a51a-809d-81ea2f3b27bf")));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		dao.delete(obj);
	}

	@Test
	public void getAllforJournalTrack() {
		final PagingWrapper<JournalTrackJournalStep> wrapper = dao
				.getAllForJournalTrack(
						UUID.randomUUID(), new SortingAndPaging(
								ObjectStatus.ACTIVE));
		assertList(wrapper.getRows());
	}

	@Test
	public void getAllforJournalStepAndJournalTrack() {
		final PagingWrapper<JournalTrackJournalStep> wrapper = dao
				.getAllForJournalTrackAndJournalStep(
						UUID.randomUUID(), UUID.randomUUID(),
						new SortingAndPaging(ObjectStatus.ACTIVE));
		assertList(wrapper.getRows());
	}

}
