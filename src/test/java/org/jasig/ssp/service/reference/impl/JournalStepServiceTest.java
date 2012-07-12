package org.jasig.ssp.service.reference.impl; // NOPMD

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.reference.JournalStepDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

public class JournalStepServiceTest {

	private transient JournalStepServiceImpl service;

	private transient JournalStepDao dao;

	public final static String TEST_STRING1 = "Test String 1";

	@Before
	public void setUp() {
		service = new JournalStepServiceImpl();
		dao = createMock(JournalStepDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<JournalStep> daoAll = new ArrayList<JournalStep>();
		daoAll.add(new JournalStep());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<JournalStep>(daoAll));

		replay(dao);

		final Collection<JournalStep> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("Collection should not have been empty.", all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final JournalStep daoOne = new JournalStep(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		final JournalStep journalStep = service.get(id);
		assertNotNull("Object should not have been null.", journalStep);
		assertFalse("Default bool value did match expected.",
				journalStep.isUsedForTransition());
		verify(dao);
	}

	@Test
	public void testGetWithName() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final JournalStep daoOne = new JournalStep(id, TEST_STRING1);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		final JournalStep journalStep = service.get(id);
		assertNotNull("Object should not have been null.", journalStep);
		assertEquals("Names did not match.", TEST_STRING1,
				journalStep.getName());
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final JournalStep daoOne = new JournalStep(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Save result should not have been null.",
				service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final JournalStep daoOne = new JournalStep(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "JournalStep"));

		replay(dao);

		service.delete(id);

		try {
			service.get(id);
			fail("Deleted item should have thrown an exception upon reloading."); // NOPMD
		} catch (final ObjectNotFoundException e) { // NOPMD
			/* expected */
		}

		verify(dao);
	}
}