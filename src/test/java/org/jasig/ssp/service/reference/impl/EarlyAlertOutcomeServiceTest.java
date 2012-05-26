package org.jasig.ssp.service.reference.impl; // NOPMD by jon.adams

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.reference.EarlyAlertOutcomeDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the EarlyAlertOutcome service implementation class
 * 
 * @author jon.adams
 */
public class EarlyAlertOutcomeServiceTest {

	private transient EarlyAlertOutcomeServiceImpl service;

	private transient EarlyAlertOutcomeDao dao;

	/**
	 * Test setup
	 */
	@Before
	public void setUp() {
		service = new EarlyAlertOutcomeServiceImpl();
		dao = createMock(EarlyAlertOutcomeDao.class);

		service.setDao(dao);
	}

	/**
	 * Test the {@link EarlyAlertOutcomeServiceImpl#getAll(SortingAndPaging)}
	 * method.
	 */
	@Test
	public void testGetAll() {
		final List<EarlyAlertOutcome> daoAll = new ArrayList<EarlyAlertOutcome>();
		daoAll.add(new EarlyAlertOutcome());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<EarlyAlertOutcome>(daoAll));

		replay(dao);

		final Collection<EarlyAlertOutcome> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("List should not have been empty.", all.isEmpty());
		verify(dao);
	}

	/**
	 * Test the {@link EarlyAlertOutcomeServiceImpl#get(UUID)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EarlyAlertOutcome daoOne = new EarlyAlertOutcome(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Get method should have returned a non-null instance.",
				service.get(id));
		verify(dao);
	}

	/**
	 * Test the {@link EarlyAlertOutcomeServiceImpl#save(EarlyAlertOutcome)}
	 * method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testSave() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EarlyAlertOutcome daoOne = new EarlyAlertOutcome(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Save method return model should not have been null.",
				service.save(daoOne));
		verify(dao);
	}

	/**
	 * Test the {@link EarlyAlertOutcomeServiceImpl#delete(UUID)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final EarlyAlertOutcome daoOne = new EarlyAlertOutcome(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "EarlyAlertOutcome"));

		replay(dao);

		service.delete(id);

		try {
			final EarlyAlertOutcome daoTwo = service.get(id);
			assertNull(
					"Recently deleted object should not have been able to be reloaded.",
					daoTwo);
		} catch (final ObjectNotFoundException e) {
			// expected exception
			assertNotNull(
					"Recently deleted object should not have been found when attempting to reload.",
					e);
		}

		verify(dao);
	}
}
