package org.jasig.ssp.web.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link PersonController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class PersonControllerIntegrationTest {

	@Autowired
	private PersonController controller;

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final String PERSON_FIRSTNAME = "James";

	private static final String PERSON_LASTNAME = "Gosling";

	private static final String PERSON_SORTEDBY_FIRSTNAME_0 = "Alan";

	private static final String PERSON_SORTEDBY_FIRSTNAME_3 = "James";

	/**
	 * Test the {@link PersonController#get(UUID)} action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerGet() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final PersonTO obj = controller.get(PERSON_ID);

		assertNotNull(
				"Returned PersonTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Person.FirstName did not match.",
				PERSON_FIRSTNAME, obj.getFirstName());
		assertEquals("Returned Person.LastName did not match.",
				PERSON_LASTNAME, obj.getLastName());
	}

	/**
	 * Test that the {@link PersonController#get(UUID)} action returns the
	 * correct validation errors when an invalid ID is sent.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws Exception {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final PersonTO obj = controller.get(UUID.randomUUID());

		assertNull(
				"Returned PersonTO from the controller should have been null.",
				obj);
	}

	/**
	 * Test the
	 * {@link PersonController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAll() throws Exception {
		final Collection<PersonTO> list = controller.getAll(
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertTrue("List action should have returned some objects.",
				list.size() > 0);
	}

	/**
	 * Test the
	 * {@link PersonController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * This test assumes that there are at least 3 valid, active Persons in the
	 * test database.
	 * 
	 * @throws Exception
	 *             Thrown if the controller throws any exceptions.
	 */
	@Test
	public void testControllerAllWithPaging() throws Exception {
		final Collection<PersonTO> listAll = controller.getAll(
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();
		final Collection<PersonTO> listFiltered = controller.getAll(
				ObjectStatus.ACTIVE, 1, 2, null, null).getRows();

		assertNotNull("ListAll should not have been null.", listAll);
		assertNotNull("ListFiltered should not have been null.", listFiltered);
		assertTrue("ListAll action should have returned some objects.",
				listAll.size() > 0);
		assertTrue("ListFiltered action should have returned some objects.",
				listFiltered.size() > 0);
		assertTrue("ListFiltered should have had fewer items than ListAll.",
				listFiltered.size() < listAll.size());
		assertEquals("ListFiltered should have had exactly 2 items.", 2,
				listFiltered.size());
	}

	/**
	 * Test the
	 * {@link PersonController#getAll(ObjectStatus, Integer, Integer, String, String)}
	 * action.
	 * 
	 * This test assumes that there are at least 3 valid, active Persons in the
	 * test database.
	 */
	@Test
	public void testControllerAllWithSorting() {
		final Collection<PersonTO> list = controller.getAll(
				ObjectStatus.ACTIVE, 0,
				200, "firstName", "ASC").getRows();

		assertNotNull("The list should not have been null.", list);

		final Iterator<PersonTO> iter = list.iterator();

		PersonTO person = iter.next();
		assertNotNull("List[0] should not have been null.", person);
		assertEquals(PERSON_SORTEDBY_FIRSTNAME_0, person.getFirstName());
		person = iter.next();
		person = iter.next();
		person = iter.next();
		assertEquals(PERSON_SORTEDBY_FIRSTNAME_3, person.getFirstName());
	}

	/**
	 * Test that the getAll action rejects a filter of
	 * {@link ObjectStatus#DELETED}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testControllerGetAllRejectsDeletedFilter() {
		controller.getAll(ObjectStatus.DELETED, null, null, null, null);
	}
}