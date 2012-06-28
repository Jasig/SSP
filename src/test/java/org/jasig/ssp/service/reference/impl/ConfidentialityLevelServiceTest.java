package org.jasig.ssp.service.reference.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.reference.ConfidentialityLevelDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ConfidentialityLevelServiceTest {

	private ConfidentialityLevelServiceImpl service;
	private ConfidentialityLevelDao dao;

	@Before
	public void setup() {
		service = new ConfidentialityLevelServiceImpl();
		dao = createMock(ConfidentialityLevelDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<ConfidentialityLevel> daoAll = new ArrayList<ConfidentialityLevel>();
		daoAll.add(new ConfidentialityLevel());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<ConfidentialityLevel>(daoAll));

		replay(dao);

		final Collection<ConfidentialityLevel> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertTrue(all.size() > 0);
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ConfidentialityLevel daoOne = new ConfidentialityLevel(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final ConfidentialityLevel daoOne = new ConfidentialityLevel(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ConfidentialityLevel daoOne = new ConfidentialityLevel(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "ConfidentialityLevel"));

		replay(dao);

		service.delete(id);

		boolean found = true;
		try {
			service.get(id);
		} catch (final ObjectNotFoundException e) {
			found = false;
		}

		assertFalse(found);
		verify(dao);
	}

	@Test
	public void filterConfidentialityLevelsFromAuthorities() {
		final List<ConfidentialityLevel> daoAll = new ArrayList<ConfidentialityLevel>();
		daoAll.add(new ConfidentialityLevel(UUID.randomUUID(), "TEST 1", "T1",
				DataPermissions.DATA_DISABILITY));
		daoAll.add(new ConfidentialityLevel(UUID.randomUUID(), "TEST 2", "T2",
				DataPermissions.DATA_COUNSELING_SERVICES));

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<ConfidentialityLevel>(daoAll));

		replay(dao);

		final List<String> authorities = Lists.newArrayList();
		authorities.add("ROLE_DATA_DISABILITY");

		final Collection<ConfidentialityLevel> all = service
				.filterConfidentialityLevelsFromPermissions(authorities);
		assertEquals(1, all.size());
		verify(dao);
	}

}
