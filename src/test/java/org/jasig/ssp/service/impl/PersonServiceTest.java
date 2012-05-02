package org.jasig.ssp.service.impl;

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

import org.junit.Before;
import org.junit.Test;
import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Lists;

public class PersonServiceTest {

	private PersonServiceImpl service;

	private PersonDao dao;

	@Before
	public void setup() {
		service = new PersonServiceImpl();
		dao = createMock(PersonDao.class);
		service.setDao(dao);
	}

	@Test
	public void testGetAll() {
		List<Person> daoAll = new ArrayList<Person>();
		daoAll.add(new Person());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<Person>(daoAll));

		replay(dao);

		Collection<Person> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse(all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		Person daoOne = new Person(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		Person daoOne = new Person(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		Person daoOne = new Person(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		service.delete(id);

		verify(dao);
	}

	@Test
	public void personFromUserId() throws ObjectNotFoundException {
		String userId = "12345";
		Person person = new Person();
		expect(dao.fromUserId(userId)).andReturn(person);

		replay(dao);

		Person result = service.personFromUserId(userId);

		verify(dao);
		assertEquals(person, result);
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

		List<Person> result = service.peopleFromListOfIds(personIds, sAndP);

		verify(dao);
		assertEquals(people, result);
	}
}
