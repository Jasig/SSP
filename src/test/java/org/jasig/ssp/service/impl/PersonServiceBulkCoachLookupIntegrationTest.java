/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.util.service.stub.StubPersonAttributesService;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.service.stub.Stubs.PersonFixture;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import static org.hamcrest.Matchers.array;
import static org.jasig.ssp.util.service.stub.Stubs.PersonFixture.ADVISOR_0;
import static org.jasig.ssp.util.service.stub.Stubs.PersonFixture.JAMES_DOE;
import static org.jasig.ssp.util.service.stub.Stubs.PersonFixture.COACH_1;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"../service-testConfig.xml", "../stubPersonAttributesService-testConfig.xml"})
@TransactionConfiguration
@Transactional
public class PersonServiceBulkCoachLookupIntegrationTest {

	@Autowired
	protected transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	protected transient StubPersonAttributesService personAttributesService;

	@Autowired
	protected  transient PersonService personService;

	@Autowired
	protected transient SessionFactory sessionFactory;

	protected transient List<String> origCoachUsernames;

	/**
	 * Setup the security service with the administrator.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
		Collection<String> rawCoachUsernames = personAttributesService.getCoachUsernames();
		if ( rawCoachUsernames != null ) {
			origCoachUsernames = new ArrayList<String>(rawCoachUsernames);
		}
	}

	@After
	public void tearDown() {
		personAttributesService.setCoachUsernames(origCoachUsernames);
		securityService.setCurrent(new Person());
	}

	@Test
	public void testGetAllAssignedCoachesLite() {

		final Collection<CoachPersonLiteTO> expected =
				Lists.newArrayList( coachPersonLiteTOFor(ADVISOR_0), coachPersonLiteTOFor(COACH_1));

		final PagingWrapper<CoachPersonLiteTO> result1 =
				personService.getAllAssignedCoachesLite(null);

		assertCoachPersonLiteTOCollectionsEqual(expected, result1.getRows());
		// zero b/c the request specified no pagination, so impl skips total
		// result size calculation
		assertEquals(0, result1.getResults());

		// now prove that getAllAssignedCoachesLite() doesn't lazily
		// create/return new coaches by creating a fixture where it could do so,
		// run the same method again, then checking that we get the exact
		// same results as before
		final Set<String> newExternalCoachUsernames =
				addCoachesToExternalDataAndAttributeService(5);

		final PagingWrapper<CoachPersonLiteTO> result2 =
				personService.getAllAssignedCoachesLite(null);

		assertCoachPersonLiteTOCollectionsEqual(expected, result2.getRows());
		// zero b/c the request specified no pagination, so impl skips total
		// result size calculation
		assertEquals(0, result2.getResults());
	}

	private CoachPersonLiteTO coachPersonLiteTOFor(Stubs.PersonFixture personFixture) {
		return new CoachPersonLiteTO(personFixture.id(),
				personFixture.firstName(), personFixture.lastName(),
				personFixture.primaryEmailAddress(),
				null, personFixture.departmentName(),
				personFixture.workPhone());
	}

	@Test
	public void testGetAllAssignedCoaches() throws ObjectNotFoundException {

		// basically the same as testGetAllAssignedCoachesLite() except
		// we expect Persons instead
		final Collection<UUID> expected =
				Lists.newArrayList(ADVISOR_0.id(), COACH_1.id());

		final PagingWrapper<Person> result1 =
				personService.getAllAssignedCoaches(null);

		assertPersonCollectionsHaveSameIds(expected,
				result1.getRows()) ;
		// zero b/c the request specified no pagination, so impl skips total
		// result size calculation
		assertEquals(0, result1.getResults());

		// now prove that getAllAssignedCoaches() doesn't lazily
		// create/return new coaches by creating a fixture where it could do so,
		// run the same method again, then checking that we get the exact
		// same results as before
		final Set<String> newExternalCoachUsernames =
				addCoachesToExternalDataAndAttributeService(5);

		final PagingWrapper<Person> result2 =
				personService.getAllAssignedCoaches(null);

		assertPersonCollectionsHaveSameIds(expected, result2.getRows());
		// zero b/c the request specified no pagination, so impl skips total
		// result size calculation
		assertEquals(0, result2.getResults());

	}

	@Test
	public void testGetAllCurrentCoachesLite() throws ObjectNotFoundException {

		final Collection<CoachPersonLiteTO> expected =
				Lists.newArrayList(coachPersonLiteTOFor(ADVISOR_0), coachPersonLiteTOFor(COACH_1));

		final SortedSet<CoachPersonLiteTO> result1 =
				personService.getAllCurrentCoachesLite(null);

		assertCoachPersonLiteTOCollectionsEqual(expected, result1);

		final Set<String> newExternalCoachUsernames =
				addCoachesToExternalDataAndAttributeService(2);

		final SortedSet<CoachPersonLiteTO> result2 =
				personService.getAllCurrentCoachesLite(null);

		assertCoachPersonLiteTOCollectionsEqual(expected, result2);

	}

	@Test
	public void testGetAllCurrentCoaches() throws ObjectNotFoundException {

		// unlike the getAllAssignedCoaches()/getAllAssignedCoachesLite()
		// pair, getAllCurrentCoaches()/getAllCurrentCoachesLite() have
		// significantly different behavior. Specifically the non-lite version,
		// tested here, *does* lazily create and return new Persons. The lite
		// version does not.
		final SortedSet<Person> result1 =
				personService.getAllCurrentCoaches(null);

		assertPersonCollectionsHaveSameIds(
				Lists.newArrayList(ADVISOR_0.id(),COACH_1.id()),
				result1);

		final Set<String> newExternalCoachUsernames =
				addCoachesToExternalDataAndAttributeService(2);

		final SortedSet<Person> result2 =
				personService.getAllCurrentCoaches(null);

		assertPersonCollectionsHaveSameIds(
				Lists.newArrayList(
						personService.personFromUsername("bulk_coach_001").getId(),
						personService.personFromUsername("bulk_coach_002").getId(),
						ADVISOR_0.id(),
						COACH_1.id()),
				result2);
	}

	@Test
	public void testGetAllCurrentCoachesLiteFiltersDuplicates()
			throws ObjectNotFoundException {
		final Person jamesDoe = person(JAMES_DOE);
		final Person advisor0 = person(ADVISOR_0);
		jamesDoe.setCoach(advisor0);
		personService.save(jamesDoe);
		sessionFactory.getCurrentSession().flush();

		final SortedSet<CoachPersonLiteTO> result =
				personService.getAllCurrentCoachesLite(null);
		assertEquals(2, result.size());
	}

	@Test
	public void testGetAllCurrentCoachesFiltersDuplicates()
			throws ObjectNotFoundException {
		final Person jamesDoe = person(JAMES_DOE);
		final Person advisor0 = person(ADVISOR_0);
		jamesDoe.setCoach(advisor0);
		personService.save(jamesDoe);
		sessionFactory.getCurrentSession().flush();

		final SortedSet<Person> result =
				personService.getAllCurrentCoaches(null);
		assertEquals(2, result.size());
	}

	@Test
	public void testGetAllCurrentCoachesLiteFiltersDuplicatesByIdNotName()
			throws ObjectNotFoundException {
		final String duplicatePersonSchoolId = ADVISOR_0.schoolId() + "_foo";
		this.createExternalPerson(duplicatePersonSchoolId,
				ADVISOR_0.username() + "_foo",
				ADVISOR_0.firstName(), // everything else the same
				ADVISOR_0.lastName(),
				ADVISOR_0.middleName(),
				ADVISOR_0.primaryEmailAddress());

		// this should create the person record
		Person duplicatePerson =
				personService.getBySchoolId(duplicatePersonSchoolId);
		assertNotNull(duplicatePerson); // sanity check
		final Person jamesDoe = person(JAMES_DOE);
		jamesDoe.setCoach(duplicatePerson);
		personService.save(jamesDoe);
		sessionFactory.getCurrentSession().flush();

		final SortedSet<CoachPersonLiteTO> result =
				personService.getAllCurrentCoachesLite(null);
		assertEquals(3, result.size());
	}

	@Test
	public void testGetAllCurrentCoachesFiltersDuplicatesByIdNotName()
			throws ObjectNotFoundException {
		final String duplicatePersonSchoolId = ADVISOR_0.schoolId() + "_foo";
		this.createExternalPerson(duplicatePersonSchoolId,
				ADVISOR_0.username() + "_foo",
				ADVISOR_0.firstName(), // everything else the same
				ADVISOR_0.lastName(),
				ADVISOR_0.middleName(),
				ADVISOR_0.primaryEmailAddress());

		// this should create the person record
		Person duplicatePerson =
				personService.getBySchoolId(duplicatePersonSchoolId);
		assertNotNull(duplicatePerson); // sanity check
		final Person jamesDoe = person(JAMES_DOE);
		jamesDoe.setCoach(duplicatePerson);
		personService.save(jamesDoe);
		sessionFactory.getCurrentSession().flush();

		final SortedSet<Person> result =
				personService.getAllCurrentCoaches(null);
		assertEquals(3, result.size());
	}

	/**
	 * Ignored b/c it doesn't assert anything, it just demonstrates the
	 * performance (and behavioral) differences between
	 * {@link PersonService#getAllCurrentCoaches(java.util.Comparator)}
	 * and {@link PersonService#getAllCurrentCoachesLite(java.util.Comparator)}.
	 * There are (at least) three problems with the former...
	 *
	 * <ol>
	 *     <li>It lazily creates person records it hasn't encountered before,
	 *     and</li>
	 *     <li>It looks up {@link Person}s one-by-one, and</li>
	 *     <li>Person lookups are just very expensive</li>
	 * </ol>
	 *
	 * <p>So if the total number of coaches returned from
	 * {@link org.jasig.ssp.service.PersonAttributesService#getCoaches()} is
	 * large (anywhere into the 100s),
	 * {@link PersonService#getAllCurrentCoaches(java.util.Comparator)} is
	 * unsuitable for invocation in the request cycle, <em>even if all
	 * the referenced coaches have already been created as {@link Person}
	 * records</em>.</p>
	 *
	 * <p>{@link PersonService#getAllCurrentCoachesLite(java.util.Comparator)}
	 * is faster, but partly b/c it doesn't make any attempt to lazily create
	 * new {@link Person}s. So it doesn't run the risk of exceptionally long
	 * runtimes when first invoked. But it does so at the cost of potentially
	 * not returning a completely up-to-date view of all coaches.
	 * <a href="https://issues.jasig.org/browse/SSP-470">SSP-470</a> combats
	 * this by moving the {@link Person} creation into a background job.</p>
	 *
	 * <p>This test demonstrates the performance gradient by causing
	 * {@link org.jasig.ssp.service.PersonAttributesService#getCoaches()} to
	 * return 500 coach usernames the {@link PersonService} hasn't seen before,
	 * then making a series of calls to the methods of interest. At this
	 * writing (Nov 20, 2012), in an all-local development env, the numbers
	 * looked like this (execution time in
	 * {@link org.jasig.ssp.service.PersonAttributesService#getCoaches()} is
	 * effecively negiligible b/c this test stubs that service):</p>
	 *
	 * <ol>
	 *     <li>{@link PersonService#getAllCurrentCoachesLite(java.util.Comparator)} (returns 1 record): 55ms</li>
	 *     <li>{@link PersonService#getAllCurrentCoaches(java.util.Comparator)} (returns 501 records): 29504ms</li>
	 *     <li>{@link PersonService#getAllCurrentCoaches(java.util.Comparator)} (returns 501 records): 15428ms</li>
	 *     <li>{@link PersonService#getAllCurrentCoachesLite(java.util.Comparator)} (returns 501 records): 59ms</li>
	 * </ol>
	 *
	 * <p>Keep in mind again that
	 * {@link PersonService#getAllCurrentCoachesLite(java.util.Comparator)}
	 * doesn't make any of the lazy-creation promises of
	 * {@link PersonService#getAllCurrentCoaches(java.util.Comparator)}, so
	 * the comparison isn't completely fair. But the calls to the latter
	 * are sufficiently slow that it would be nice to find a way to
	 * drop them both down... maybe through a combination of bulk db reads
	 * and writes and by simplifying the object graph returned with all
	 * {@link Person} lookups.</p>
	 *
	 */
	@Test
	@Ignore
	public void testLiteCoachLookupMuchFasterButPotentiallyIncomplete() {
		int externalCoachQuota = 500;
		Set<String> addedCoachUsernames = addCoachesToExternalDataAndAttributeService(externalCoachQuota);

		long started = new Date().getTime();
		final PagingWrapper<CoachPersonLiteTO> allCoachesLite1 = personService.getAllCoachesLite(null);
		long ended = new Date().getTime();
		System.out.println("Lite Person lookups, no external Persons created yet: "
				+ (ended - started) + "ms (" + allCoachesLite1.getResults()
				+ " total records returned)");

		started = new Date().getTime();
		final SortedSet<Person> lazyCreatedCoaches1 = personService.getAllCurrentCoaches(null);
		ended = new Date().getTime();
		System.out.println("Full Person lookups, lazy Person record creation: "
				+ (ended - started) + "ms (" + externalCoachQuota
				+ " lazy records, " + lazyCreatedCoaches1.size()
				+ " total records returned)");

		started = new Date().getTime();
		final SortedSet<Person> lazyCreatedCoaches2 = personService.getAllCurrentCoaches(null);
		ended = new Date().getTime();
		System.out.println("Full Person lookups, all Persons already created: "
				+ (ended - started) + "ms (" + lazyCreatedCoaches2.size()
				+ " total records returned)");

		started = new Date().getTime();
		final PagingWrapper<CoachPersonLiteTO> allCoachesLite2 = personService.getAllCoachesLite(null);
		ended = new Date().getTime();
		System.out.println("Lite Person lookups, all Persons already created: "
				+ (ended - started) + "ms (" + allCoachesLite2.getResults()
				+ " total records returned)");
	}

	private Set<String> addCoachesToExternalDataAndAttributeService(int quota) {
		Set<String> usernames = Sets.newHashSet();
		if ( quota <= 0 ) {
			return usernames;
		}
		for ( int i = 1 ; i <= quota ; i++ ) {
			String paddedIdx = StringUtils.leftPad("" + i, 3, "0");
			String username = "bulk_coach_" + paddedIdx;
			createExternalPerson("bulk_coach_school_id_" + paddedIdx,
					username,
					"BulkCoach",
					"BulkCoach" + paddedIdx,
					"" + i,
					"bulkcoach" + paddedIdx + "@school.edu");
			usernames.add(username);
		}
		personAttributesService.getCoachUsernames().addAll(usernames);
		sessionFactory.getCurrentSession().flush();
		return usernames;
	}

	public void createExternalPerson(final String schoolId,
											   final String username,
											   final String firstName,
											   final String lastName,
											   final String middleName,
											   final String primaryEmailAddress) {
		final Session session = sessionFactory.getCurrentSession();
		final SQLQuery sqlQuery = session.createSQLQuery("insert into external_person (school_id," +
				"username, first_name, last_name, middle_name," +
				"primary_email_address) values (?,?,?,?,?,?)");
		sqlQuery.setString(0, schoolId)
				.setString(1, username)
				.setString(2, firstName)
				.setString(3, lastName)
				.setString(4, middleName)
				.setString(5, primaryEmailAddress);
		sqlQuery.executeUpdate();
	}

	private void assertPersonCollectionsHaveSameIds(Collection<UUID> expected,
													Collection<Person> actual){
		assertArrayEquals(uuidCollectionToUuidAray(expected),
				personCollectionToUuidAray(actual));
	}

	private UUID[] uuidCollectionToUuidAray(Collection<UUID> uuidCollection) {
		return uuidCollection.toArray(new UUID[uuidCollection.size()]);
	}

	private UUID[] personCollectionToUuidAray(Collection<Person> personCollection) {
		UUID[] uuidArray = new UUID[personCollection.size()];
		int i = 0;
		for ( Person person : personCollection ) {
			uuidArray[i] = person.getId();
			i++;
		}
		return uuidArray;
	}

	private void assertCoachPersonLiteTOCollectionsEqual(
			Collection<CoachPersonLiteTO> expected,
			Collection<CoachPersonLiteTO> actual) {
		CoachPersonLiteTO[] actualArray =
				actual.toArray(new CoachPersonLiteTO[actual.size()]);
		assertThat(actualArray, array(matchersFor(expected)));
	}

	private Matcher<CoachPersonLiteTO>[] matchersFor(
			Collection<CoachPersonLiteTO> coachPersonLiteTOs) {
		Matcher<CoachPersonLiteTO>[] matchers =
				new Matcher[coachPersonLiteTOs.size()];
		int i = 0;
		for ( CoachPersonLiteTO coachPersonLiteTO : coachPersonLiteTOs ) {
			matchers[i++] = matcherFor(coachPersonLiteTO);
		}
		return matchers;
	}

	private Matcher<CoachPersonLiteTO> matcherFor(
			final CoachPersonLiteTO coachPersonLiteTO) {
		return new CustomTypeSafeMatcher<CoachPersonLiteTO>(
				coachPersonLiteTO.toString()) {
			@Override
			protected boolean matchesSafely(CoachPersonLiteTO item) {
				return item.equalsAllFields(coachPersonLiteTO);
			}
		};
	}

	private Person person(Stubs.PersonFixture personFixture)
			throws ObjectNotFoundException {
		return Stubs.person(personFixture, personService);
	}

}
