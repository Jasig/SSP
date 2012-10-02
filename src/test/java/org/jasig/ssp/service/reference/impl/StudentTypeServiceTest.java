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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.reference.StudentTypeDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

/**
 * StudentType service test
 * 
 * @author jon.adams
 * 
 */
public class StudentTypeServiceTest {

	private transient StudentTypeServiceImpl service;

	private transient StudentTypeDao dao;

	@Before
	public void setUp() {
		service = new StudentTypeServiceImpl();
		dao = createMock(StudentTypeDao.class);

		service.setDao(dao);
	}

	/**
	 * Test {@link StudentTypeService#getAll(SortingAndPaging)}
	 */
	@Test
	public void testGetAll() {
		final List<StudentType> daoAll = new ArrayList<StudentType>();
		daoAll.add(new StudentType());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<StudentType>(daoAll));

		replay(dao);

		final Collection<StudentType> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("GetAll() result should not have been empty.",
				all.isEmpty());
		verify(dao);
	}

	/**
	 * Test {@link StudentTypeService#get(UUID)}
	 * 
	 * @throws ObjectNotFoundException
	 *             If the object could not be found.
	 */
	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final StudentType daoOne = new StudentType(
				id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Service get() response should not have been null.",
				service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final StudentType daoOne = new StudentType(
				id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull("save() response should not have been null.",
				service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final StudentType daoOne = new StudentType(
				id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "StudentType"));

		replay(dao);

		service.delete(id);

		boolean found = true; // NOPMD
		try {
			service.get(id);
		} catch (final ObjectNotFoundException e) {
			found = false;
		}

		assertFalse("Deleted object should not have returned an object.", found);
		verify(dao);
	}
}