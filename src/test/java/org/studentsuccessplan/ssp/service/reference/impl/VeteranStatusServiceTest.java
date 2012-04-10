package org.studentsuccessplan.ssp.service.reference.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import org.studentsuccessplan.ssp.dao.reference.VeteranStatusDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public class VeteranStatusServiceTest {

	private VeteranStatusServiceImpl service;
	private VeteranStatusDao dao;

	@Before
	public void setup() {
		service = new VeteranStatusServiceImpl();
		dao = createMock(VeteranStatusDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		List<VeteranStatus> daoAll = new ArrayList<VeteranStatus>();
		daoAll.add(new VeteranStatus());

		expect(dao.getAll(ObjectStatus.ACTIVE, null, null, null, null))
				.andReturn(daoAll);

		replay(dao);

		List<VeteranStatus> all = service.getAll(ObjectStatus.ACTIVE, null, null,
				null, null);
		assertTrue(all.size() > 0);
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		VeteranStatus daoOne = new VeteranStatus(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		VeteranStatus daoOne = new VeteranStatus(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		VeteranStatus daoOne = new VeteranStatus(id);

		expect(dao.get(id)).andReturn(daoOne).times(2);
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
