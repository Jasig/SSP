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

import org.jasig.ssp.dao.reference.EducationGoalDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.EducationGoal;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

public class EducationGoalServiceTest {

	private transient EducationGoalServiceImpl service;

	private transient EducationGoalDao dao;

	@Before
	public void setUp() {
		service = new EducationGoalServiceImpl();
		dao = createMock(EducationGoalDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<EducationGoal> daoAll = new ArrayList<EducationGoal>();
		daoAll.add(new EducationGoal());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<EducationGoal>(daoAll));

		replay(dao);

		final Collection<EducationGoal> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertNotEmpty("GetAll result should not have been null.", all);
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EducationGoal daoOne = new EducationGoal(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Get result should not have been null.", service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final EducationGoal daoOne = new EducationGoal(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Save should not have been null.", service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EducationGoal daoOne = new EducationGoal(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "EducationGoal"));

		replay(dao);

		service.delete(id);

		boolean found = true;
		try {
			service.get(id);
		} catch (final ObjectNotFoundException e) {
			found = false;
		}

		assertFalse("Deleted instance should not have been found.", found);
		verify(dao);
	}
}