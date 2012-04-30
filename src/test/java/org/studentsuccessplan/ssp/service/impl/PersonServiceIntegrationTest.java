package org.studentsuccessplan.ssp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
public class PersonServiceIntegrationTest {

	@Autowired
	private PersonService service;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAll() {
		List<Person> list = service.getAll(new SortingAndPaging(
				ObjectStatus.ACTIVE));
		assertNotNull(list);
		assertTrue("List should have included multiple entities.",
				list.size() > 1);
	}

	@Test
	public void testGetAllWithRowFilter() {
		List<Person> listAll = service.getAll(new SortingAndPaging(
				ObjectStatus.ACTIVE));
		List<Person> listFiltered = service.getAll(SortingAndPaging
				.createForSingleSort(
						ObjectStatus.ACTIVE, 1, 2, null, null, null));

		assertNotNull(listAll);
		assertTrue("List should have included multiple entities.",
				listAll.size() > 2);

		assertNotNull(listFiltered);
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());

		assertTrue(
				"The filtered list should have included a different number of entities then the unfiltered list.",
				listFiltered.size() != listAll.size());
	}
}
