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

import org.jasig.ssp.dao.reference.JournalSourceDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

public class JournalSourceServiceTest {

	private transient JournalSourceServiceImpl service;

	private transient JournalSourceDao dao;

	@Before
	public void setUp() {
		service = new JournalSourceServiceImpl();
		dao = createMock(JournalSourceDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<JournalSource> daoAll = new ArrayList<JournalSource>();
		daoAll.add(new JournalSource());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<JournalSource>(daoAll));

		replay(dao);

		final Collection<JournalSource> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse(all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final JournalSource daoOne = new JournalSource(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final JournalSource daoOne = new JournalSource(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final JournalSource daoOne = new JournalSource(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "JournalSource"));

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

}
