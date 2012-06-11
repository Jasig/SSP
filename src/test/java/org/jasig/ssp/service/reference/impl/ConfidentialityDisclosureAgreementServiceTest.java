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

import org.jasig.ssp.dao.reference.ConfidentialityDisclosureAgreementDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ConfidentialityDisclosureAgreement;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

public class ConfidentialityDisclosureAgreementServiceTest {

	private ConfidentialityDisclosureAgreementServiceImpl service;
	private ConfidentialityDisclosureAgreementDao dao;

	@Before
	public void setup() {
		service = new ConfidentialityDisclosureAgreementServiceImpl();
		dao = createMock(ConfidentialityDisclosureAgreementDao.class);

		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		final List<ConfidentialityDisclosureAgreement> daoAll = new ArrayList<ConfidentialityDisclosureAgreement>();
		daoAll.add(new ConfidentialityDisclosureAgreement());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<ConfidentialityDisclosureAgreement>(daoAll));

		replay(dao);

		final Collection<ConfidentialityDisclosureAgreement> all = service
				.getAll(
						new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertTrue(all.size() > 0);
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ConfidentialityDisclosureAgreement daoOne = new ConfidentialityDisclosureAgreement(
				id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final ConfidentialityDisclosureAgreement daoOne = new ConfidentialityDisclosureAgreement(
				id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ConfidentialityDisclosureAgreement daoOne = new ConfidentialityDisclosureAgreement(
				id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id,
						"ConfidentialityDisclosureAgreement"));

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
