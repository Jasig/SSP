package org.studentsuccessplan.ssp.service.reference.impl;

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

import org.junit.Before;
import org.junit.Test;
import org.studentsuccessplan.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public class SelfHelpGuideQuestionServiceTest {

	private SelfHelpGuideQuestionServiceImpl service;
	private SelfHelpGuideQuestionDao dao;

	@Before
	public void setup() {
		service = new SelfHelpGuideQuestionServiceImpl();
		dao = createMock(SelfHelpGuideQuestionDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		List<SelfHelpGuideQuestion> daoAll = new ArrayList<SelfHelpGuideQuestion>();
		daoAll.add(new SelfHelpGuideQuestion());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<SelfHelpGuideQuestion>(daoAll));

		replay(dao);

		Collection<SelfHelpGuideQuestion> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertTrue(all.size() > 0);
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		SelfHelpGuideQuestion daoOne = new SelfHelpGuideQuestion(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		SelfHelpGuideQuestion daoOne = new SelfHelpGuideQuestion(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		SelfHelpGuideQuestion daoOne = new SelfHelpGuideQuestion(id);

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
