package org.jasig.ssp.service.reference.impl; // NOPMD

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.reference.MaritalStatusDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.MaritalStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.MaritalStatusService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests on the {@link MaritalStatusService} class
 */
public class MaritalStatusServiceTest {

	private transient MaritalStatusServiceImpl service;

	private transient MaritalStatusDao dao;

	@Before
	public void setUp() {
		service = new MaritalStatusServiceImpl();
		dao = createMock(MaritalStatusDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<MaritalStatus> daoAll = new ArrayList<MaritalStatus>();
		daoAll.add(new MaritalStatus());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<MaritalStatus>(daoAll));

		replay(dao);

		final Collection<MaritalStatus> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertNotEmpty("List should not have been empty.", all);
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final MaritalStatus daoOne = new MaritalStatus(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Get result should not have been null.", service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final MaritalStatus daoOne = new MaritalStatus(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Save result should not have been null.",
				service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final MaritalStatus daoOne = new MaritalStatus(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "MaritalStatus"));

		replay(dao);

		service.delete(id);

		boolean found = true;
		try {
			service.get(id);
		} catch (final ObjectNotFoundException e) {
			found = false;
		}

		assertFalse("Should have found the entity.", found);
		verify(dao);
	}
}