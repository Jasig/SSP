package org.jasig.ssp.service.reference.impl;

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

import org.jasig.ssp.dao.reference.ChallengeReferralDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the ChallengeReferral service implementation class
 */
public class ChallengeReferralServiceTest {

	private transient ChallengeReferralServiceImpl service;

	private transient ChallengeReferralDao dao;

	/**
	 * Test setup
	 */
	@Before
	public void setUp() {
		service = new ChallengeReferralServiceImpl();
		dao = createMock(ChallengeReferralDao.class);

		service.setDao(dao);
	}

	/**
	 * Test the {@link ChallengeReferralServiceImpl#getAll(SortingAndPaging)}
	 * method.
	 */
	@Test
	public void testGetAll() {
		final List<ChallengeReferral> daoAll = new ArrayList<ChallengeReferral>();
		daoAll.add(new ChallengeReferral());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<ChallengeReferral>(daoAll));

		replay(dao);

		final Collection<ChallengeReferral> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("Result should not have been empty.", all.isEmpty());
		verify(dao);
	}

	/**
	 * Test the {@link ChallengeReferralServiceImpl#get(UUID)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ChallengeReferral daoOne = new ChallengeReferral(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Service.get() method should not have returned null.",
				service.get(id));
		verify(dao);
	}

	/**
	 * Test the {@link ChallengeReferralServiceImpl#save(ChallengeReferral)}
	 * method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testSave() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ChallengeReferral daoOne = new ChallengeReferral(id);
		daoOne.setShowInSelfHelpGuide(true);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		final ChallengeReferral saved = service.save(daoOne);
		assertNotNull("Saved instance should not have returned null.", saved);
		assertTrue("Saved values did not match.", saved.isShowInSelfHelpGuide());
		verify(dao);
	}

	/**
	 * Test the {@link ChallengeReferralServiceImpl#delete(UUID)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ChallengeReferral daoOne = new ChallengeReferral(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(new ObjectNotFoundException(""));

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
