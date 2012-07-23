package org.jasig.ssp.service.impl; // NOPMD

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * {@link PersonServiceImpl} test suite
 * 
 * @author jon.adams
 * 
 */
public class PersonServiceTest {

	private transient PersonServiceImpl service;

	private transient PersonDao dao;

	private transient RegistrationStatusByTermService registrationStatusByTermService;

	private static final String TEST_USER_ID = "12345";

	@Before
	public void setUp() {
		service = new PersonServiceImpl();
		dao = createMock(PersonDao.class);
		service.setDao(dao);
		registrationStatusByTermService = createMock(RegistrationStatusByTermService.class);
		service.setRegistrationStatusByTermService(registrationStatusByTermService);
	}

	@Test
	public void testGetAll() {
		final List<Person> daoAll = new ArrayList<Person>();
		daoAll.add(new Person());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<Person>(daoAll));

		replay(dao);

		final Collection<Person> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("GetAll() result should not have returned an empty list.",
				all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Person daoOne = new Person(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(registrationStatusByTermService
				.applyRegistrationStatusForCurrentTerm(daoOne))
				.andReturn(daoOne).anyTimes();

		replay(dao);
		replay(registrationStatusByTermService);

		assertNotNull("Get() result should not have been null.",
				service.get(id));
		verify(dao);
		verify(registrationStatusByTermService);
	}

	@Test
	public void testSave() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Person daoOne = new Person(id);

		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(registrationStatusByTermService
				.applyRegistrationStatusForCurrentTerm(daoOne))
				.andReturn(daoOne).anyTimes();

		replay(dao);
		replay(registrationStatusByTermService);

		assertNotNull("Save() result should not have been null.",
				service.save(daoOne));
		verify(dao);
		verify(registrationStatusByTermService);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException { // NOPMD
		final UUID id = UUID.randomUUID();
		final Person daoOne = new Person(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(registrationStatusByTermService
				.applyRegistrationStatusForCurrentTerm(daoOne))
				.andReturn(daoOne).anyTimes();

		replay(dao);
		replay(registrationStatusByTermService);

		service.delete(id);

		verify(dao);
		verify(registrationStatusByTermService);
	}

	@Test
	public void personFromUserId() throws ObjectNotFoundException {
		final Person person = new Person();

		expect(dao.fromUsername(TEST_USER_ID)).andReturn(person);
		expect(registrationStatusByTermService
				.applyRegistrationStatusForCurrentTerm(person))
				.andReturn(person).anyTimes();

		replay(dao);
		replay(registrationStatusByTermService);

		final Person result = service.personFromUsername(TEST_USER_ID);

		verify(dao);
		verify(registrationStatusByTermService);
		assertEquals("Lists do not match.", person, result);
	}

	@Test
	public void peopleFromListOfIds() {
		final List<UUID> personIds = Lists.newArrayList();
		personIds.add(UUID.randomUUID());
		personIds.add(UUID.randomUUID());
		personIds.add(UUID.randomUUID());

		final List<Person> people = Lists.newArrayList();

		final SortingAndPaging sAndP = new SortingAndPaging(ObjectStatus.ACTIVE);

		expect(dao.getPeopleInList(personIds, sAndP)).andReturn(people);

		replay(dao);

		final List<Person> result = service.peopleFromListOfIds(personIds,
				sAndP);

		verify(dao);
		assertEquals("Lists do not match.", people, result);
	}
}