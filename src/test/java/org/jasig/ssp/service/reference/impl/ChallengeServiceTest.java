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
package org.jasig.ssp.service.reference.impl; // NOPMD

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.reference.ChallengeDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

/**
 * Challenge service test
 */
public class ChallengeServiceTest {

	private transient ChallengeServiceImpl service;

	private transient ChallengeDao dao;

	@Before
	public void setUp() {
		service = new ChallengeServiceImpl();
		dao = createMock(ChallengeDao.class);

		service.setDao(dao);
	}

	/**
	 * Test the {@link ChallengeServiceImpl#getAll(SortingAndPaging)} action.
	 * Assumes some test data items exist.
	 */
	@Test
	public void testGetAll() {
		final List<Challenge> daoAll = new ArrayList<Challenge>();
		daoAll.add(new Challenge());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<Challenge>(daoAll));

		replay(dao);

		final Collection<Challenge> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("GetAll list should not have been empty.", all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Challenge daoOne = new Challenge(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Get should not have returned null.", service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final Challenge daoOne = new Challenge(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Save result should not have been null.",
				service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Challenge daoOne = new Challenge(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "ChallengeService"));

		replay(dao);

		service.delete(id);

		try {
			service.get(id);
			fail("Exception should have been thrown."); // NOPMD
		} catch (final ObjectNotFoundException e) { // NOPMD
			/* expected */
		}

		verify(dao);
	}

	@Test
	public void testSearch() {
		// arrange, act
		final List<Challenge> challenges = service.challengeSearch("bad query", false);

		// assert
		assertTrue("List with a bad query should have returned an empty list.",
				challenges.isEmpty());
	}
}