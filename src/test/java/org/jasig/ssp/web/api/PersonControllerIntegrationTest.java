package org.jasig.ssp.web.api; // NOPMD

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
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

	private static final String PERSON_SORTEDBY_FIRSTNAME_7 = "James";

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
		iter.next(); // skip checking 5th
		iter.next(); // skip checking 6th
		final PersonTO person7 = iter.next(); // check 7th
		assertEquals("7th",
				PERSON_SORTEDBY_FIRSTNAME_7, person7.getFirstName());
	}

	@Test
	public void testUniqueSchoolId() throws ObjectNotFoundException,
			ValidationException {
		final Person person1 = createPerson();
		person1.setUsername(TEST_SCHOOLID);
		final PersonTO person1Saved = controller.create(new PersonTO(person1));

		final Person person2 = createPerson();
		person2.setUsername(TEST_SCHOOLID);
		try {
			controller.create(new PersonTO(person2));
			fail("Exception should have been thrown.");
		} catch ( ObjectExistsException e ) {
			assertEquals("Conflict on unexpected entity type",
					Person.class.getName(), e.getName());
			final Map<String,UUID> expectedTopLevelLookupFields =
					new HashMap<String,UUID>();
			expectedTopLevelLookupFields.put("id", person1Saved.getId());
			assertEquals("Conflict on unexpected person record",
					expectedTopLevelLookupFields, e.getLookupFields());
			final Map<String,String> expectedConflictingLookupFields =
					new HashMap<String,String>();
			expectedConflictingLookupFields.put("username", TEST_SCHOOLID);
			assertEquals("Conflict on unexpected person key",
					expectedConflictingLookupFields,
					((ObjectExistsException)e.getCause()).getLookupFields());
			assertEquals("Conflict on unexpected entity type",
					Person.class.getName(), ((ObjectExistsException)e.getCause()).getName());
		}
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

	@Test
	public void testUpdateReplacesServiceReasons()
			throws ObjectNotFoundException, ValidationException {
		final PersonTO person1 = new PersonTO(createPerson());
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons = Lists
				.newArrayList();
		serviceReasons
				.add(new ReferenceLiteTO<ServiceReason>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person1.setServiceReasons(serviceReasons);

		final PersonTO person = controller.create(person1);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final PersonTO person1Edit = new PersonTO(createPerson());
		person1Edit.setId(person.getId());
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons2 = Lists
				.newArrayList();
		// ServiceReasons collection edits have replace to semantics, so here
		// we reference a new Reason and don't reference the old Reason, which
		// should cause the old Reason association to be deactivated
		serviceReasons2
				.add(new ReferenceLiteTO<ServiceReason>(UUID
						.fromString("205df6c0-fea0-11e1-9678-406c8f22c3ce"),
						"IGNORED"));
		person1Edit.setServiceReasons(serviceReasons2);

		final PersonTO person1SavedEdit =
				controller.save(person1Edit.getId(), person1Edit);
		List<ReferenceLiteTO<ServiceReason>> serviceReasons3 =
				person1SavedEdit.getServiceReasons();
		assertEquals("All deletions are soft operations, so the ServiceReason"
				+ " collection should actually grow when \"replacing\" an"
				+ " existing ServiceReason.", 2, serviceReasons3.size());
		sortById(serviceReasons3); // UUIDs don't sort lexicographically!
		assertEquals("Unexpected ServiceReason",
				"f6201a04-bb31-4ca5-b606-609f3ad09f87",
				serviceReasons3.get(0).getId().toString());
		assertEquals("Replaced ServiceReason should be inactive",
				ObjectStatus.INACTIVE,
				serviceReasons3.get(0).getObjectStatus());
		assertEquals("Unexpected ServiceReason",
				"205df6c0-fea0-11e1-9678-406c8f22c3ce",
				serviceReasons3.get(1).getId().toString());
		assertEquals("Newly added ServiceReason should be active",
				ObjectStatus.ACTIVE,
				serviceReasons3.get(1).getObjectStatus());

		session.flush();
		session.clear();

		// paranoia
		final PersonTO reloaded = controller.get(person.getId());
		List<ReferenceLiteTO<ServiceReason>> serviceReasons4 =
				reloaded.getServiceReasons();
		sortById(serviceReasons4); // UUIDs don't sort lexicographically!
		assertEquals("ServiceReason replacement not actually written through"
				+ " to database", 2, serviceReasons4.size());
		assertEquals("Unexpected ServiceReason in persistent Person",
				"f6201a04-bb31-4ca5-b606-609f3ad09f87",
				serviceReasons4.get(0).getId().toString());
		assertEquals("Replaced ServiceReason should be inactive in persistent Person",
				ObjectStatus.INACTIVE,
				serviceReasons4.get(0).getObjectStatus());
		assertEquals("Unexpected ServiceReason in persistent Person",
				"205df6c0-fea0-11e1-9678-406c8f22c3ce",
				serviceReasons4.get(1).getId().toString());
		assertEquals("Newly added ServiceReason should be active in persistent Person",
				ObjectStatus.ACTIVE,
				serviceReasons4.get(1).getObjectStatus());

	}

	// Similar to testUpdateReplacesServiceReasons() but makes sure empty
	// and null ServiceReason collections aren't ignored
	@Test
	public void testUpdateCanDeleteAllServiceReasons()
			throws ObjectNotFoundException, ValidationException {
		final PersonTO person1 = new PersonTO(createPerson());
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons1 = Lists
				.newArrayList();
		serviceReasons1
				.add(new ReferenceLiteTO<ServiceReason>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person1.setServiceReasons(serviceReasons1);

		final PersonTO person1Created = controller.create(person1);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		// sanity check
		final PersonTO person1Reloaded = controller.get(person1Created.getId());
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons1Reloaded =
				person1Reloaded.getServiceReasons();
		assertEquals("Unexpected persistent ServiceReason count", 1,
				serviceReasons1Reloaded.size());
		assertEquals("Unexpected persistent ServiceReason ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceReasons1Reloaded.get(0).getId());

		// first test handling of null collections
		final PersonTO person2 = new PersonTO(createPerson());
		person2.setId(person1Created.getId());
		person2.setServiceReasons(null);
		final PersonTO person2Saved =
				controller.save(person2.getId(), person2);
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons2Saved =
				person2Saved.getServiceReasons();
		assertEquals("Should have soft-deleted the ServiceReason so the"
				+ " ServiceReason collection size should not have changed",
				1,
				serviceReasons2Saved.size());
		assertEquals("Should have soft-deleted the ServiceReason so the one"
				+ " ServiceReason in the collection should not have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceReasons2Saved.get(0).getId());
		assertEquals("Should have soft-deleted the ServiceReason",
				ObjectStatus.INACTIVE,
				serviceReasons2Saved.get(0).getObjectStatus());

		session.flush();

		// add an active service reason back so we can test empty collection
		// handling
		final PersonTO person3 = new PersonTO(createPerson());
		person3.setId(person1Created.getId());
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons3 = Lists
				.newArrayList();
		serviceReasons3
				.add(new ReferenceLiteTO<ServiceReason>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person3.setServiceReasons(serviceReasons3);
		final PersonTO person3Saved =
				controller.save(person3.getId(), person3);

		session.flush();

		// sanity check again
		final PersonTO person3Reloaded = controller.get(person1Created.getId());
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons3Reloaded =
				person3Reloaded.getServiceReasons();
		assertEquals("Unexpected persistent ServiceReason count", 2,
				serviceReasons3Reloaded.size());
		// same ID should occupy both slots in the collection now
		assertEquals("Unexpected persistent ServiceReason ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceReasons3Reloaded.get(0).getId());
		assertEquals("Unexpected persistent ServiceReason ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceReasons3Reloaded.get(1).getId());
		int activeCnt3 = 0;
		if ( serviceReasons3Reloaded.get(0).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt3++;
		}
		if ( serviceReasons3Reloaded.get(1).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt3++;
		}
		assertEquals("Only one ServiceReason should be active", 1, activeCnt3);

		// now test that empty collections act like null collections
		final PersonTO person4 = new PersonTO(createPerson());
		person4.setId(person1Created.getId());
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons4 = Lists
				.newArrayList();
		person4.setServiceReasons(serviceReasons4);
		final PersonTO person4Saved =
				controller.save(person4.getId(), person4);
		final List<ReferenceLiteTO<ServiceReason>> serviceReasons4Saved =
				person4Saved.getServiceReasons();
		assertEquals("Should have soft-deleted the ServiceReason so the"
				+ " ServiceReason collection size should not have changed",
				2,
				serviceReasons4Saved.size());
		assertEquals("Should have soft-deleted the ServiceReason so neither"
				+ " ServiceReason in the collection should have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceReasons4Saved.get(0).getId());
		assertEquals("Should have soft-deleted the ServiceReason so neither"
				+ " ServiceReason in the collection should have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceReasons4Saved.get(1).getId());
		int activeCnt4 = 0;
		if ( serviceReasons4Saved.get(0).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt4++;
		}
		if ( serviceReasons4Saved.get(1).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt4++;
		}
		assertEquals("Should be no active ServiceReasons.", 0, activeCnt4);

	}

	@Test
	public void testUpdateReplacesSpecialServiceGroups()
			throws ObjectNotFoundException, ValidationException {
		final PersonTO person1 = new PersonTO(createPerson());
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups = Lists
				.newArrayList();
		serviceGroups
				.add(new ReferenceLiteTO<SpecialServiceGroup>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person1.setSpecialServiceGroups(serviceGroups);

		final PersonTO person = controller.create(person1);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final PersonTO person1Edit = new PersonTO(createPerson());
		person1Edit.setId(person.getId());
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups2 = Lists
				.newArrayList();
		// SpecialServiceGroups collection edits have replace to semantics, so here
		// we reference a new Group and don't reference the old Group, which
		// should cause the old Group association to be deactivated
		serviceGroups2
				.add(new ReferenceLiteTO<SpecialServiceGroup>(UUID
						.fromString("40b6b8aa-bca1-11e1-9344-037cb4088c72"),
						"IGNORED"));
		person1Edit.setSpecialServiceGroups(serviceGroups2);

		final PersonTO person1SavedEdit =
				controller.save(person1Edit.getId(), person1Edit);
		List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups3 =
				person1SavedEdit.getSpecialServiceGroups();
		assertEquals("All deletions are soft operations, so the SpecialServiceGroup"
				+ " collection should actually grow when \"replacing\" an"
				+ " existing SpecialServiceGroup.", 2, serviceGroups3.size());
		sortById(serviceGroups3); // UUIDs don't sort lexicographically!
		assertEquals("Unexpected SpecialServiceGroup",
				"f6201a04-bb31-4ca5-b606-609f3ad09f87",
				serviceGroups3.get(0).getId().toString());
		assertEquals("Replaced SpecialServiceGroup should be inactive",
				ObjectStatus.INACTIVE,
				serviceGroups3.get(0).getObjectStatus());
		assertEquals("Unexpected SpecialServiceGroup",
				"40b6b8aa-bca1-11e1-9344-037cb4088c72",
				serviceGroups3.get(1).getId().toString());
		assertEquals("Newly added SpecialServiceGroup should be active",
				ObjectStatus.ACTIVE,
				serviceGroups3.get(1).getObjectStatus());

		session.flush();
		session.clear();

		// paranoia
		final PersonTO reloaded = controller.get(person.getId());
		List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups4 =
				reloaded.getSpecialServiceGroups();
		sortById(serviceGroups4); // UUIDs don't sort lexicographically!
		assertEquals("SpecialServiceGroup replacement not actually written through"
				+ " to database", 2, serviceGroups4.size());
		assertEquals("Unexpected SpecialServiceGroup in persistent Person",
				"f6201a04-bb31-4ca5-b606-609f3ad09f87",
				serviceGroups4.get(0).getId().toString());
		assertEquals("Replaced SpecialServiceGroup should be inactive in persistent Person",
				ObjectStatus.INACTIVE,
				serviceGroups4.get(0).getObjectStatus());
		assertEquals("Unexpected SpecialServiceGroup in persistent Person",
				"40b6b8aa-bca1-11e1-9344-037cb4088c72",
				serviceGroups4.get(1).getId().toString());
		assertEquals("Newly added SpecialServiceGroup should be active in persistent Person",
				ObjectStatus.ACTIVE,
				serviceGroups4.get(1).getObjectStatus());

	}

	@Test
	public void testUpdateCanDeleteAllSpecialServiceGroups()
			throws ObjectNotFoundException, ValidationException {
		final PersonTO person1 = new PersonTO(createPerson());
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups1 = Lists
				.newArrayList();
		serviceGroups1
				.add(new ReferenceLiteTO<SpecialServiceGroup>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person1.setSpecialServiceGroups(serviceGroups1);

		final PersonTO person1Created = controller.create(person1);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		// sanity check
		final PersonTO person1Reloaded = controller.get(person1Created.getId());
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups1Reloaded =
				person1Reloaded.getSpecialServiceGroups();
		assertEquals("Unexpected persistent SpecialServiceGroup count", 1,
				serviceGroups1Reloaded.size());
		assertEquals("Unexpected persistent SpecialServiceGroup ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceGroups1Reloaded.get(0).getId());

		// first test handling of null collections
		final PersonTO person2 = new PersonTO(createPerson());
		person2.setId(person1Created.getId());
		person2.setSpecialServiceGroups(null);
		final PersonTO person2Saved =
				controller.save(person2.getId(), person2);
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups2Saved =
				person2Saved.getSpecialServiceGroups();
		assertEquals("Should have soft-deleted the SpecialServiceGroup so the"
				+ " SpecialServiceGroup collection size should not have changed",
				1,
				serviceGroups2Saved.size());
		assertEquals("Should have soft-deleted the SpecialServiceGroup so the one"
				+ " SpecialServiceGroup in the collection should not have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceGroups2Saved.get(0).getId());
		assertEquals("Should have soft-deleted the SpecialServiceGroup",
				ObjectStatus.INACTIVE,
				serviceGroups2Saved.get(0).getObjectStatus());

		session.flush();

		// add an active service group back so we can test empty collection
		// handling
		final PersonTO person3 = new PersonTO(createPerson());
		person3.setId(person1Created.getId());
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups3 = Lists
				.newArrayList();
		serviceGroups3
				.add(new ReferenceLiteTO<SpecialServiceGroup>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person3.setSpecialServiceGroups(serviceGroups3);
		final PersonTO person3Saved =
				controller.save(person3.getId(), person3);

		session.flush();

		// sanity check again
		final PersonTO person3Reloaded = controller.get(person1Created.getId());
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups3Reloaded =
				person3Reloaded.getSpecialServiceGroups();
		assertEquals("Unexpected persistent SpecialServiceGroup count", 2,
				serviceGroups3Reloaded.size());
		// same ID should occupy both slots in the collection now
		assertEquals("Unexpected persistent SpecialServiceGroup ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceGroups3Reloaded.get(0).getId());
		assertEquals("Unexpected persistent SpecialServiceGroup ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceGroups3Reloaded.get(1).getId());
		int activeCnt3 = 0;
		if ( serviceGroups3Reloaded.get(0).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt3++;
		}
		if ( serviceGroups3Reloaded.get(1).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt3++;
		}
		assertEquals("Only one SpecialServiceGroup should be active", 1, activeCnt3);

		// now test that empty collections act like null collections
		final PersonTO person4 = new PersonTO(createPerson());
		person4.setId(person1Created.getId());
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups4 = Lists
				.newArrayList();
		person4.setSpecialServiceGroups(serviceGroups4);
		final PersonTO person4Saved =
				controller.save(person4.getId(), person4);
		final List<ReferenceLiteTO<SpecialServiceGroup>> serviceGroups4Saved =
				person4Saved.getSpecialServiceGroups();
		assertEquals("Should have soft-deleted the SpecialServiceGroup so the"
				+ " SpecialServiceGroup collection size should not have changed",
				2,
				serviceGroups4Saved.size());
		assertEquals("Should have soft-deleted the SpecialServiceGroup so neither"
				+ " SpecialServiceGroup in the collection should have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceGroups4Saved.get(0).getId());
		assertEquals("Should have soft-deleted the SpecialServiceGroup so neither"
				+ " SpecialServiceGroup in the collection should have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				serviceGroups4Saved.get(1).getId());
		int activeCnt4 = 0;
		if ( serviceGroups4Saved.get(0).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt4++;
		}
		if ( serviceGroups4Saved.get(1).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt4++;
		}
		assertEquals("Should be no active SpecialServiceGroup.", 0, activeCnt4);

	}

	@Test
	public void testUpdateReplacesReferralSources()
			throws ObjectNotFoundException, ValidationException {
		final PersonTO person1 = new PersonTO(createPerson());
		final List<ReferenceLiteTO<ReferralSource>> referralSources = Lists
				.newArrayList();
		referralSources
				.add(new ReferenceLiteTO<ReferralSource>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person1.setReferralSources(referralSources);

		final PersonTO person = controller.create(person1);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		final PersonTO person1Edit = new PersonTO(createPerson());
		person1Edit.setId(person.getId());
		final List<ReferenceLiteTO<ReferralSource>> referralSources2 = Lists
				.newArrayList();
		// ReferralSources collection edits have replace to semantics, so here
		// we reference a new Source and don't reference the old Source, which
		// should cause the old Source association to be deactivated
		referralSources2
				.add(new ReferenceLiteTO<ReferralSource>(UUID
						.fromString("ccadd634-bd7a-11e1-8d28-3368721922dc"),
						"IGNORED"));
		person1Edit.setReferralSources(referralSources2);

		final PersonTO person1SavedEdit =
				controller.save(person1Edit.getId(), person1Edit);
		List<ReferenceLiteTO<ReferralSource>> referralSources3 =
				person1SavedEdit.getReferralSources();
		assertEquals("All deletions are soft operations, so the ReferralSource"
				+ " collection should actually grow when \"replacing\" an"
				+ " existing ReferralSource.", 2, referralSources3.size());
		sortById(referralSources3); // UUIDs don't sort lexicographically!
		assertEquals("Unexpected ReferralSource",
				"ccadd634-bd7a-11e1-8d28-3368721922dc",
				referralSources3.get(0).getId().toString());
		assertEquals("Newly added ReferralSource should be active",
				ObjectStatus.ACTIVE,
				referralSources3.get(0).getObjectStatus());
		assertEquals("Unexpected ReferralSource",
				"f6201a04-bb31-4ca5-b606-609f3ad09f87",
				referralSources3.get(1).getId().toString());
		assertEquals("Replaced ReferralSource should be inactive",
				ObjectStatus.INACTIVE,
				referralSources3.get(1).getObjectStatus());

		session.flush();
		session.clear();

		// paranoia
		final PersonTO reloaded = controller.get(person.getId());
		List<ReferenceLiteTO<ReferralSource>> referralSources4 =
				reloaded.getReferralSources();
		sortById(referralSources4); // UUIDs don't sort lexicographically!
		assertEquals("ReferralSource replacement not actually written through"
				+ " to database", 2, referralSources4.size());
		assertEquals("Unexpected ReferralSource in persistent Person",
				"ccadd634-bd7a-11e1-8d28-3368721922dc",
				referralSources4.get(0).getId().toString());
		assertEquals("Newly added ReferralSource should be active in persistent Person",
				ObjectStatus.ACTIVE,
				referralSources4.get(0).getObjectStatus());
		assertEquals("Unexpected ReferralSource in persistent Person",
				"f6201a04-bb31-4ca5-b606-609f3ad09f87",
				referralSources4.get(1).getId().toString());
		assertEquals("Replaced ReferralSource should be inactive in persistent Person",
				ObjectStatus.INACTIVE,
				referralSources4.get(1).getObjectStatus());

	}

	@Test
	public void testUpdateCanDeleteAllReferralSources()
			throws ObjectNotFoundException, ValidationException {
		final PersonTO person1 = new PersonTO(createPerson());
		final List<ReferenceLiteTO<ReferralSource>> referralSources1 = Lists
				.newArrayList();
		referralSources1
				.add(new ReferenceLiteTO<ReferralSource>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person1.setReferralSources(referralSources1);

		final PersonTO person1Created = controller.create(person1);
		final Session session = sessionFactory.getCurrentSession();
		session.flush();

		// sanity check
		final PersonTO person1Reloaded = controller.get(person1Created.getId());
		final List<ReferenceLiteTO<ReferralSource>> referralSources1Reloaded =
				person1Reloaded.getReferralSources();
		assertEquals("Unexpected persistent ReferralSource count", 1,
				referralSources1Reloaded.size());
		assertEquals("Unexpected persistent ReferralSource ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				referralSources1Reloaded.get(0).getId());

		// first test handling of null collections
		final PersonTO person2 = new PersonTO(createPerson());
		person2.setId(person1Created.getId());
		person2.setReferralSources(null);
		final PersonTO person2Saved =
				controller.save(person2.getId(), person2);
		final List<ReferenceLiteTO<ReferralSource>> referralSources2Saved =
				person2Saved.getReferralSources();
		assertEquals("Should have soft-deleted the ReferralSource so the"
				+ " ReferralSource collection size should not have changed",
				1,
				referralSources2Saved.size());
		assertEquals("Should have soft-deleted the ReferralSource so the one"
				+ " ReferralSource in the collection should not have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				referralSources2Saved.get(0).getId());
		assertEquals("Should have soft-deleted the ReferralSource",
				ObjectStatus.INACTIVE,
				referralSources2Saved.get(0).getObjectStatus());

		session.flush();

		// add an active service group back so we can test empty collection
		// handling
		final PersonTO person3 = new PersonTO(createPerson());
		person3.setId(person1Created.getId());
		final List<ReferenceLiteTO<ReferralSource>> referralSources3 = Lists
				.newArrayList();
		referralSources3
				.add(new ReferenceLiteTO<ReferralSource>(UUID
						.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
						"IGNORED"));
		person3.setReferralSources(referralSources3);
		final PersonTO person3Saved =
				controller.save(person3.getId(), person3);

		session.flush();

		// sanity check again
		final PersonTO person3Reloaded = controller.get(person1Created.getId());
		final List<ReferenceLiteTO<ReferralSource>> referralSources3Reloaded =
				person3Reloaded.getReferralSources();
		assertEquals("Unexpected persistent ReferralSource count", 2,
				referralSources3Reloaded.size());
		// same ID should occupy both slots in the collection now
		assertEquals("Unexpected persistent ReferralSource ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				referralSources3Reloaded.get(0).getId());
		assertEquals("Unexpected persistent ReferralSource ID",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				referralSources3Reloaded.get(1).getId());
		int activeCnt3 = 0;
		if ( referralSources3Reloaded.get(0).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt3++;
		}
		if ( referralSources3Reloaded.get(1).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt3++;
		}
		assertEquals("Only one ReferralSource should be active", 1, activeCnt3);

		// now test that empty collections act like null collections
		final PersonTO person4 = new PersonTO(createPerson());
		person4.setId(person1Created.getId());
		final List<ReferenceLiteTO<ReferralSource>> referralSources4 = Lists
				.newArrayList();
		person4.setReferralSources(referralSources4);
		final PersonTO person4Saved =
				controller.save(person4.getId(), person4);
		final List<ReferenceLiteTO<ReferralSource>> referralSources4Saved =
				person4Saved.getReferralSources();
		assertEquals("Should have soft-deleted the ReferralSource so the"
				+ " ReferralSource collection size should not have changed",
				2,
				referralSources4Saved.size());
		assertEquals("Should have soft-deleted the ReferralSource so neither"
				+ " ReferralSource in the collection should have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				referralSources4Saved.get(0).getId());
		assertEquals("Should have soft-deleted the ReferralSource so neither"
				+ " ReferralSource in the collection should have changed",
				UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"),
				referralSources4Saved.get(1).getId());
		int activeCnt4 = 0;
		if ( referralSources4Saved.get(0).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt4++;
		}
		if ( referralSources4Saved.get(1).getObjectStatus() == ObjectStatus.ACTIVE ) {
			activeCnt4++;
		}
		assertEquals("Should be no active ReferralSource.", 0, activeCnt4);

	}

	private <T extends AbstractReference> void sortById(List<ReferenceLiteTO<T>> list) {
		Collections.sort(list, new Comparator<ReferenceLiteTO<T>>() {
			@Override
			public int compare(ReferenceLiteTO<T> o1, ReferenceLiteTO<T> o2) {
				if ( o1.getId() == o2.getId() ) {
					return 0;
				}
				if ( o1.getId() == null ) {
					return -1;
				}
				if ( o2.getId() == null ) {
					return 1;
				}
				return o1.getId().compareTo(o2.getId());
			}
		});
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