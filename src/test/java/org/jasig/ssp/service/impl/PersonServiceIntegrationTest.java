package org.jasig.ssp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
public class PersonServiceIntegrationTest {

	@Autowired
	private transient PersonService service;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testGetAll() {
		final Collection<Person> list = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertNotNull("GetAll list should not have been null.", list);
		assertTrue("List should have included multiple entities.",
				list.size() > 1);
	}

	@Test
	public void testGetAllWithRowFilter() {
		final Collection<Person> listAll = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		final Collection<Person> listFiltered = service.getAll(
				SortingAndPaging.createForSingleSort(ObjectStatus.ACTIVE, 1, 2,
						null, null, null)).getRows();

		assertNotNull("List should not have been null.", listAll);
		assertTrue("List should have included multiple entities.",
				listAll.size() > 2);

		assertNotNull("Filtered list should not have been null.", listFiltered);
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());
		assertEquals("List should have included exactly 2 entities.", 2,
				listFiltered.size());

		assertNotSame(
				"The filtered list should have included a different number of entities then the unfiltered list.",
				listFiltered.size(), listAll.size());
	}
}