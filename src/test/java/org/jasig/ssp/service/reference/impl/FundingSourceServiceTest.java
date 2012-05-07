package org.jasig.ssp.service.reference.impl;

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

import org.jasig.ssp.dao.reference.FundingSourceDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.FundingSource;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;

public class FundingSourceServiceTest {

	private transient FundingSourceServiceImpl service;

	private transient FundingSourceDao dao;

	@Before
	public void setUp() {
		service = new FundingSourceServiceImpl();
		dao = createMock(FundingSourceDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<FundingSource> daoAll = new ArrayList<FundingSource>();
		daoAll.add(new FundingSource());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<FundingSource>(daoAll));

		replay(dao);

		final Collection<FundingSource> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertTrue(all.size() > 0);
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		FundingSource obj = new FundingSource(id);
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		FundingSource daoOne = obj;

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		FundingSource daoOne = new FundingSource(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		FundingSource daoOne = new FundingSource(id);
		daoOne.setObjectStatus(ObjectStatus.ACTIVE);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andReturn(null);

		replay(dao);

		service.delete(id);

		boolean found = true;
		try {
			service.get(id);
		} catch (ObjectNotFoundException e) {
			found = false;
		}

		assertFalse(found);
		verify(dao);
	}

}
