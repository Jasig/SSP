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
package org.jasig.ssp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.JournalEntryService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class PersonJournalEntryServiceIntegrationTest {

	@Autowired
	private transient JournalEntryService service;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final UUID JOURNAL_ID = UUID
			.fromString("86FFCD52-AF44-11E1-98F9-0026B9E7FF4C");

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAll() {
		final Collection<JournalEntry> list = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertNotNull("GetAll list should not have been null.", list);
		assertFalse("List should not have been empty.", list.isEmpty());
	}

	@Test
	public void testGetAllWithRowFilter() {
		final Collection<JournalEntry> listAll = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		final Collection<JournalEntry> listFiltered = service.getAll(
				SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ACTIVE, 1, 2,
						null, null, null)).getRows();

		assertNotNull("List should not have been null.", listAll);
		assertFalse("List should not have been empty.", listAll.isEmpty());

		assertNotNull("Filtered list should not have been null.", listFiltered);
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());

		assertNotSame(
				"The filtered list should have included a different number of entities then the unfiltered list.",
				listFiltered.size(), listAll.size());
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		// act
		final JournalEntry journalEntry = service.get(JOURNAL_ID);

		// assert
		assertNotNull("JournalEntry is not null.", journalEntry);
		assertEquals("Detail list count did not match.", 2, journalEntry
				.getJournalEntryDetails().size());
	}
}