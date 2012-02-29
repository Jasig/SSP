package edu.sinclair.ssp.service.reference.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.sinclair.ssp.dao.reference.ChallengeDao;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.reference.impl.ChallengeServiceImpl;

public class ChallengeServiceTest {

	private ChallengeServiceImpl service;
	private ChallengeDao dao;
	
	@Before
	public void setup(){
		service = new ChallengeServiceImpl();
		dao = createMock(ChallengeDao.class);
		
		service.setDao(dao);
	}
	
	@Test
	public void testGetAll() {
		List<Challenge> daoAll = new ArrayList<Challenge>();
		daoAll.add(new Challenge());
		
		expect(dao.getAll()).andReturn(daoAll);
		
		replay(dao);
		
		List<Challenge> all = service.getAll();
		assertTrue(all.size()>0);
		verify(dao);
	}

	@Test
	public void testGet() {
		UUID id = UUID.randomUUID();
		Challenge daoOne = new Challenge(id);
		
		expect(dao.get(id)).andReturn(daoOne);
		
		replay(dao);
		
		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() {
		Challenge daoOne = new Challenge();
		
		expect(dao.save(daoOne)).andReturn(daoOne);
		
		replay(dao);
		
		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() {
		UUID id = UUID.randomUUID();
		Challenge daoOne = new Challenge(id);
		
		expect(dao.get(id)).andReturn(daoOne);
		dao.delete(daoOne);
		expect(dao.get(id)).andReturn(null);
		
		replay(dao);
		
		service.delete(id);
		Challenge ghost = service.get(id);
		
		assertNull(ghost);
		verify(dao);
	}

}
