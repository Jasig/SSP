package org.jasig.ssp.web.api; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.transferobject.PersonProgramStatusTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

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
	private transient PersonController controller;

	@Autowired
	private transient PersonProgramStatusController personProgramStatusController;

	@Autowired
	private transient SessionFactory sessionFactory;

	private static final UUID PERSON_ID = UUID
			.fromString("1010e4a0-1001-0110-1011-4ffc02fe81ff");

	private static final String PERSON_FIRSTNAME = "James";

	private static final String PERSON_LASTNAME = "Gosling";

	private static final String PERSON_SORTEDBY_FIRSTNAME_0 = "Alan";

	private static final String PERSON_SORTEDBY_FIRSTNAME_5 = "James";

	private static final String TEST_SCHOOLID = "legacy school id";

	private static final UUID PROGRAM_STATUS_ID = UUID
			.fromString("b2d12527-5056-a51a-8054-113116baab88");

	private static final String PROGRAM_STATUS_NAME = "Active";

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	/**
	 * Setup the security service with the administrator user.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				Permission.PERSON_PROGRAM_STATUS_WRITE);
	}

	/**
	 * Test the {@link PersonController#get(UUID)} action.
	 * 
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGet() throws ObjectNotFoundException {
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
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException {
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
	 */
	@Test
	public void testControllerAll() {
		final Collection<PersonTO> list = controller.getAll(
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();

		assertNotNull("List should not have been null.", list);
		assertFalse("List action should have returned some objects.",
				list.isEmpty());
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
	public void testControllerAllWithPaging() {
		final Collection<PersonTO> listAll = controller.getAll(
				ObjectStatus.ACTIVE,
				null, null, null, null).getRows();
		final Collection<PersonTO> listFiltered = controller.getAll(
				ObjectStatus.ACTIVE, 1, 2, null, null).getRows();

		assertNotNull("ListAll should not have been null.", listAll);
		assertNotNull("ListFiltered should not have been null.", listFiltered);
		assertFalse("ListAll action should have returned some objects.",
				listAll.isEmpty());
		assertFalse("ListFiltered action should have returned some objects.",
				listFiltered.isEmpty());
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

		final PersonTO person = iter.next(); // 1st
		assertNotNull("List[0] should not have been null.", person);
		assertEquals("First name properties do not match.",
				PERSON_SORTEDBY_FIRSTNAME_0, person.getFirstName());
		iter.next(); // skip checking 2nd
		iter.next(); // skip checking 3rd
		iter.next(); // skip checking 4th
		final PersonTO person5 = iter.next(); // check 5th
		assertEquals("5th",
				PERSON_SORTEDBY_FIRSTNAME_5, person5.getFirstName());
	}

	@Test(expected = ObjectExistsException.class)
	public void testUniqueSchoolId() throws ObjectNotFoundException,
			ValidationException {
		final Person person1 = createPerson();
		person1.setUsername(TEST_SCHOOLID);
		controller.create(new PersonTO(person1));

		final Person person2 = createPerson();
		person2.setUsername(TEST_SCHOOLID);
		controller.create(new PersonTO(person2));
		fail("Exception should have been thrown.");
	}

	@Test
	public void testProgramStatus() throws ObjectNotFoundException,
			ValidationException {
		// arrange
		final PersonTO person1 = new PersonTO(createPerson());
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons = Lists
				.newArrayList();
		serviceReasons
				.add(new ReferenceLiteTO<ServiceReason>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person1.setServiceReasons(serviceReasons);

		// act
		final PersonTO person = controller.create(person1);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		assertNull("Active program status should be empty at this point.",
				person.getCurrentProgramStatusName());

		// now add statuses
		final PersonProgramStatusTO ps1 = new PersonProgramStatusTO();
		ps1.setEffectiveDate(new Date());
		ps1.setPersonId(person.getId());
		ps1.setProgramStatusId(PROGRAM_STATUS_ID);
		final PersonProgramStatusTO ps2 = new PersonProgramStatusTO();
		ps2.setEffectiveDate(new Date());
		ps2.setPersonId(person.getId());
		ps2.setProgramStatusId(UUID
				.fromString("b2d125a4-5056-a51a-8042-d50b8eff0df1"));

		// insert one we want to expire
		personProgramStatusController.create(person.getId(), ps2);
		// now insert current (which expires the other)
		personProgramStatusController.create(person.getId(), ps1);

		session.flush();
		session.clear();

		final PersonTO reloaded = controller.get(person.getId());

		// assert
		assertEquals("Active program status name did not match.",
				PROGRAM_STATUS_NAME, reloaded.getCurrentProgramStatusName());
	}

	private Person createPerson() {
		final Person person = new Person();
		person.setFirstName(PERSON_FIRSTNAME);
		person.setLastName(PERSON_LASTNAME);
		person.setPrimaryEmailAddress("email");
		person.setUsername("username");
		person.setSchoolId("legacy id");
		return person;
	}

	/**
	 * Test that getLogger() returns the matching log class name for the current
	 * class under test.
	 */
	@Test
	public void testLogger() {
		final Logger logger = controller.getLogger();
		assertEquals("Log class name did not match.", controller.getClass()
				.getName(), logger.getName());
	}
}