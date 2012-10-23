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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.reference.ChallengeReferralDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the {@link ChallengeReferralServiceImpl} implementation class
 */
public class ChallengeReferralServiceTest {

	private transient ChallengeReferralServiceImpl service;

	private transient ChallengeReferralDao dao;

	/**
	 * Test setup
	 */
	@Before
	public void setUp() {
		service = new ChallengeReferralServiceImpl();
		dao = createMock(ChallengeReferralDao.class);

		service.setDao(dao);
	}

	/**
	 * Test the {@link ChallengeReferralServiceImpl#getAll(SortingAndPaging)}
	 * method.
	 */
	@Test
	public void testGetAll() {
		final List<ChallengeReferral> daoAll = new ArrayList<ChallengeReferral>();
		daoAll.add(new ChallengeReferral());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<ChallengeReferral>(daoAll));

		replay(dao);

		final Collection<ChallengeReferral> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("Result should not have been empty.", all.isEmpty());
		verify(dao);
	}

	/**
	 * Test the {@link ChallengeReferralServiceImpl#get(UUID)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ChallengeReferral daoOne = new ChallengeReferral(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Service.get() method should not have returned null.",
				service.get(id));
		verify(dao);
	}

	/**
	 * Test the {@link ChallengeReferralServiceImpl#save(ChallengeReferral)}
	 * method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 * @throws ValidationException
	 *             If there were any validation errors.
	 */
	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final ChallengeReferral daoOne = new ChallengeReferral(id);
		daoOne.setShowInSelfHelpGuide(true);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		final ChallengeReferral saved = service.save(daoOne);
		assertNotNull("Saved instance should not have returned null.", saved);
		assertTrue("Saved values did not match.",
				saved.getShowInSelfHelpGuide());
		verify(dao);
	}

	/**
	 * Test the {@link ChallengeReferralServiceImpl#delete(UUID)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ChallengeReferral daoOne = new ChallengeReferral(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "ChallengeReferral"));

		replay(dao);

		service.delete(id);

		boolean found = true;
		try {
			service.get(id);
		} catch (final ObjectNotFoundException e) {
			found = false;
		}

		assertFalse("Id should not have been found after deletion.", found);
		verify(dao);
	}
}