package org.jasig.ssp.service.reference.impl;

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

import org.jasig.ssp.dao.reference.SelfHelpGuideQuestionDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;

public class SelfHelpGuideQuestionServiceTest {

	private transient SelfHelpGuideQuestionServiceImpl service;

	private transient SelfHelpGuideQuestionDao dao;

	@Before
	public void setUp() {
		service = new SelfHelpGuideQuestionServiceImpl();
		dao = createMock(SelfHelpGuideQuestionDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<SelfHelpGuideQuestion> daoAll = new ArrayList<SelfHelpGuideQuestion>();
		daoAll.add(new SelfHelpGuideQuestion());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<SelfHelpGuideQuestion>(daoAll));

		replay(dao);

		final Collection<SelfHelpGuideQuestion> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse(all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final SelfHelpGuideQuestion daoOne = new SelfHelpGuideQuestion(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final SelfHelpGuideQuestion daoOne = new SelfHelpGuideQuestion(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final SelfHelpGuideQuestion daoOne = new SelfHelpGuideQuestion(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "SelfHelpGuideQuestion"));

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
