package edu.sinclair.ssp.service.reference.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.sinclair.ssp.dao.reference.ReferralDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Referral;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public class ReferralServiceTest {

	transient private ReferralServiceImpl service;

	transient private ReferralDao dao;

	@Before
	public void setUp() {
		service = new ReferralServiceImpl();
		dao = createMock(ReferralDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		List<Referral> daoAll = new ArrayList<Referral>();
		daoAll.add(new Referral());

		expect(dao.getAll(ObjectStatus.ACTIVE, null, null, null, null))
				.andReturn(daoAll);

		replay(dao);

		final List<Referral> all = service.getAll(ObjectStatus.ACTIVE, null,
				null, null, null);
		assertTrue(!all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		Referral daoOne = new Referral(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		Referral daoOne = new Referral(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		String testString1 = "testString1";

		Referral daoOne = new Referral(id);
		daoOne.setPublicDescription(testString1);

		assertEquals("Public description did not match.", testString1,
				daoOne.getPublicDescription());

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
