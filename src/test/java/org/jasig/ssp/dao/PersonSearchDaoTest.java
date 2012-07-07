package org.jasig.ssp.dao;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonSearchDaoTest {

	@Autowired
	private transient PersonSearchDao dao;

	@Autowired
	private transient PersonService personService;

	@Test
	public void testGetAllTTfirstName() {
		final Collection<Person> list = dao.searchBy(
				null, true,
				"ennis", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertTrue("List should have one entity.",
				!list.isEmpty());
	}

	@Test
	public void testGetAlTTlastName() {
		final Collection<Person> list = dao.searchBy(
				null, true,
				"ritch", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertTrue("List should have one entity.",
				!list.isEmpty());
	}

	@Test
	public void testGetAllTTschoolId() {
		final Collection<Person> list = dao.searchBy(
				null, true,
				"dmr.1", null, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertTrue("List should have one entity.",
				!list.isEmpty());
	}

	@Test
	public void testGetAllTTfullName() {
		final Collection<Person> list = dao.searchBy(
				null, true,
				"dennis ritchie", null,
				new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertTrue("List should have one entity.",
				!list.isEmpty());
	}

	@Test
	public void testGetAllTTprogramStatusAndAdvisor()
			throws ObjectNotFoundException {

		final Person turing = personService.get(UUID
				.fromString("252de4a0-7c06-4254-b7d8-4ffc02fe81ff"));

		final Collection<Person> list = dao.searchBy(
				new ProgramStatus(ProgramStatus.ACTIVE_ID), true,
				"james", turing, new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows();
		assertTrue("List should have one entity.",
				!list.isEmpty());
	}
}
