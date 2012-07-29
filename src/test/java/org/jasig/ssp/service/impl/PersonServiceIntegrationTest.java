package org.jasig.ssp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.model.PersonStaffDetails;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class PersonServiceIntegrationTest {

	@Autowired
	private transient PersonService service;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	private static final UUID PERSON_ID = UUID
			.fromString("1010E4A0-1001-0110-1011-4FFC02FE81FF");

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

	/**
	 * Test relies on test data inserted in 000007-test.xml
	 * 
	 * @throws ObjectNotFoundException
	 *             If student could not be found. Should not be thrown in this
	 *             test.
	 * @throws ValidationException
	 *             If data is not valid. Should not be thrown in this test.
	 */
	@Test
	public void testGetIncludesInactiveChildren()
			throws ObjectNotFoundException, ValidationException {
		// arrange

		// act
		final Person person = service.get(PERSON_ID);

		// assert
		final Set<PersonChallenge> loadedChallenges = person.getChallenges();
		assertEquals("Challenge associations count did not match expected.", 3,
				loadedChallenges.size());
	}

	@Test
	public void testCreateWithStaffDetails() throws ObjectNotFoundException {
		// arrange
		final Person person = createPerson();
		final PersonStaffDetails staffDetails = new PersonStaffDetails();
		staffDetails.setOfficeLocation("officeLocation");
		staffDetails.setOfficeHours("officeHours");
		staffDetails.setDepartmentName("departmentName");
		person.setStaffDetails(staffDetails);

		// act
		final Person created = service.create(person);

		// assert
		assertNotNull("Created ID should not have been null.", created.getId());

		final Session session = sessionFactory.getCurrentSession();
		session.flush();
		session.clear();

		final Person reloaded = service.get(created.getId());
		assertNotNull("reloaded ID should not have been null.",
				reloaded.getId());
		final PersonStaffDetails reloadedDetails = reloaded.getStaffDetails();
		assertEquals("Office location values did not match.", "officeLocation",
				reloadedDetails.getOfficeLocation());
		assertEquals("Office hours values did not match.", "officeHours",
				reloadedDetails.getOfficeHours());
		assertEquals("Department name values did not match.", "departmentName",
				reloadedDetails.getDepartmentName());
	}

	@Test
	public void getBySchoolIdKen() throws ObjectNotFoundException {
		final Person person = service.getBySchoolId("ken.1");
		assertNotNull("should have found ken", person);
		assertEquals("schoolid should be ken", "ken.1", person.getSchoolId());
	}

	@Test(expected = ObjectNotFoundException.class)
	public void getBySchoolIdNotInSystem() throws ObjectNotFoundException {
		service.getBySchoolId("borkborkbork");
	}

	@Test
	public void getBySchoolIdNewUser() throws ObjectNotFoundException {
		final PagingWrapper<Person> before = service
				.getAll(new SortingAndPaging(
						ObjectStatus.ACTIVE));

		final Person person = service.getBySchoolId("notInSsp");

		final PagingWrapper<Person> after = service
				.getAll(new SortingAndPaging(
						ObjectStatus.ACTIVE));

		assertTrue("Should have added 1 person record",
				(before.getResults() + 1) == after.getResults());

		assertNotNull("should have created new user notInSsp", person);
		assertEquals("schoolid should be notInSsp", "notInSsp",
				person.getSchoolId());
		assertEquals("Firstname should match", "Not", person.getFirstName());
		assertEquals("middlename should match", "In", person.getMiddleName());
		assertEquals("lastname should match", "Ssp", person.getLastName());
		assertEquals("username should match", "notInSsp", person.getUsername());
		assertEquals("primaryemailaddress should match", "test@sinclair.edu",
				person.getPrimaryEmailAddress());
		assertEquals("coach should be set to advisor0", "advisor0", person
				.getCoach().getUsername());
		assertEquals("Gender should be set to male", Genders.M, person
				.getDemographics().getGender());
		assertFalse("Should not be local", person.getDemographics().getLocal());
	}

	private Person createPerson() {
		final Person person = new Person();
		person.setFirstName("first");
		person.setLastName("last");
		person.setPrimaryEmailAddress("email");
		person.setUsername("username");
		person.setSchoolId("legacy id");
		return person;
	}
}