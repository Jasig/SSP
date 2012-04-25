package org.studentsuccessplan.ssp.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.studentsuccessplan.ssp.dao.PersonDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonChallenge;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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

		expect(dao.getAll(isA(SortingAndPaging.class)))
				.andReturn(daoAll);

		replay(dao);

		List<Person> all = service.getAll(new SortingAndPaging(
				ObjectStatus.ACTIVE));
		assertTrue(all.size() > 0);
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

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull(service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		UUID id = UUID.randomUUID();
		Person daoOne = new Person(id);

		expect(dao.get(id)).andReturn(daoOne).times(2);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andReturn(null);

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

	@Test
	public void testOverwrite() {
		// Simple fields
		String testString1 = "ts1";
		String testString2 = "ts2";
		String testString3 = "ts3";
		Date testDate = new Date();
		Date testDate2 = new Date();
		testDate2.setTime(testDate2.getTime() + 24);

		// Set<PersonChallenge>
		Set<PersonChallenge> challenges = new HashSet<PersonChallenge>();
		challenges.add(new PersonChallenge());

		Person pPersistent = new Person(UUID.randomUUID());
		pPersistent.setAddressLine1(testString1);
		pPersistent.setBirthDate(testDate);
		pPersistent.setSchoolId(testString2);
		pPersistent.setWorkPhone(null);
		pPersistent.setZipCode(null);

		Person pFromTO = new Person(UUID.randomUUID());
		pFromTO.setAddressLine1(testString3);
		pFromTO.setBirthDate(testDate2);
		pFromTO.setSchoolId(testString3);
		pFromTO.setZipCode(testString3);
		pFromTO.setChallenges(challenges);

		service.overwrite(pPersistent, pFromTO);

		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getAddressLine1());
		assertEquals("Field not copied correctly.", testDate2,
				pPersistent.getBirthDate());
		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getSchoolId());
		assertEquals("Field not copied correctly.", null,
				pPersistent.getWorkPhone());
		assertEquals("Field not copied correctly.", testString3,
				pPersistent.getZipCode());
		assertNotNull("Set not copied correctly.", pPersistent.getChallenges());
		assertEquals("PersonChallenge Set copied when it shouldn't have been.",
				0, pPersistent.getChallenges().size());
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
