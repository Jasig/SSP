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
package org.jasig.ssp.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.array;
import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.easymock.internal.matchers.Matches;
import org.hamcrest.BaseMatcher;
import org.hamcrest.CustomMatcher;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.service.reference.StudentTypeService;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spockframework.util.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * Tests for the {@link CaseloadDao} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")

// many (most?) other DAO tests commit by default... here we're just testing
// reads, so we know that any fixture setup needs to be rolled back
@TransactionConfiguration(defaultRollback = true)

@Transactional
public class CaseloadDaoTest {

	@Autowired
	private transient CaseloadDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient PersonProgramStatusService personProgramStatusService;

	@Autowired
	private transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	private transient StudentTypeService studentTypeService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test
	public void testCaseLoadFor() throws ObjectNotFoundException {
		final Person turing = personService.get(Stubs.PersonFixture.ADVISOR_0.id());

		final PagingWrapper<CaseloadRecord> caseload = dao.caseLoadFor(null,
				turing, new SortingAndPaging(ObjectStatus.ACTIVE));

		assertNotEmpty("Unable to find any students in caseload",
				caseload.getRows());
	}

	@Test
	public void testHistoricalCaseLoadCountsByStatusForDefaultDataSetAndNoFilters() {
		// this counts *everything*... students counted several times, once for
		// each status they've ever had (or will have)
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results =
				dao.caseLoadCountsByStatus(null, null, null, null);
		expectHistoricalUnfilteredResultsAgainstDefaultDataSet(results);
	}

	@Test
	public void testCurrentCaseLoadCountsByStatusForDefaultDataSetAndNoFilters() {
		// this counts *everything*... students counted several times, once for
		// each status they've ever had (or will have)
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results =
				dao.currentCaseLoadCountsByStatus(null, null);
		expectCurrentUnfilteredResultsAgainstDefaultDataSet(results);
	}

	@Test
	public void testHistoricalCaseloadCountForMultipleCoachesAndMultipleStatusesAndNoFilters()
			throws ObjectNotFoundException, ValidationException {

		// same fixture as
		// testCurrentCaseloadCountForMultipleCoachesAndMultipleStatusesAndNoFilters()
		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person markGalafion = person(Stubs.PersonFixture.MARK_GALAFRION);
		final Person bobReynolds = person(Stubs.PersonFixture.BOB_REYNOLDS);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		final Person faculty0 = person(Stubs.PersonFixture.FACULTY_0);

		jamesDoe.setCoach(kevinSmith);
		markGalafion.setCoach(kevinSmith);
		bobReynolds.setCoach(faculty0);
		activate(jamesDoe, markGalafion, bobReynolds);
		deactivate(jamesDoe);
		saveAndFlush(jamesDoe, markGalafion, bobReynolds);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> counts =
				dao.caseLoadCountsByStatus(null, null, null, null);

		final List<CoachCaseloadRecordCountForProgramStatus> expectedRecords =
				Lists.newArrayListWithCapacity(4);

		// order is important b/c we should be sorting by coach name and all
		// a coach's records should be grouped together.

		// these expectations are slightly different than for
		// testCurrentCaseloadCountForMultipleCoachesAndMultipleStatusesAndNoFilters(),
		// which uses the same fixture. Here we'll count all the newly
		// activated cases plus a few extra expired cases.
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.ACTIVE, 2, expectedRecords); // 2 b/c james doe and mark g both counted
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedRecords); // james doe has both his active and inactive statuses counted
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0, // "Douglas Toya"
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedRecords);
		collectUnfilteredCoachStatusCountsForDefaultDataSet(expectedRecords);

		assertCaseloadCountCollectionsEqual(expectedRecords, counts.getRows());
		assertEquals("Should have been one record for each unique status associated with each coach",
				expectedRecords.size(),
				counts.getResults());

	}

	@Test
	public void testCurrentCaseloadCountForMultipleCoachesAndMultipleStatusesAndNoFilters()
			throws ObjectNotFoundException, ValidationException {

		// same fixture as
		// testHistoricalCaseloadCountForMultipleCoachesAndMultipleStatusesAndNoFilters()
		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person markGalafion = person(Stubs.PersonFixture.MARK_GALAFRION);
		final Person bobReynolds = person(Stubs.PersonFixture.BOB_REYNOLDS);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		final Person faculty0 = person(Stubs.PersonFixture.FACULTY_0);

		jamesDoe.setCoach(kevinSmith);
		markGalafion.setCoach(kevinSmith);
		bobReynolds.setCoach(faculty0);
		activate(jamesDoe, markGalafion, bobReynolds);
		deactivate(jamesDoe);
		saveAndFlush(jamesDoe, markGalafion, bobReynolds);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> counts =
				dao.currentCaseLoadCountsByStatus(null, null);

		final List<CoachCaseloadRecordCountForProgramStatus> expectedRecords =
				Lists.newArrayListWithCapacity(4);

		// order is important b/c we should be sorting by coach name and all
		// a coach's records should be grouped together.

		// these expectations are slightly different than for
		// testHistoricalCaseloadCountForMultipleCoachesAndMultipleStatusesAndNoFilters(),
		// which uses the same fixture. Here we'll *only* count all the newly
		// activated cases plus those cases that already existed but leaving
		// out any expired cases.
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0, // "Douglas Toya"
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedRecords);
		collectCurrentCoachStatusCountsForDefaultDataSet(expectedRecords);

		assertCaseloadCountCollectionsEqual(expectedRecords, counts.getRows());
		assertEquals("Should have been one record for each unique status associated with each coach",
				expectedRecords.size(),
				counts.getResults());

	}

	@Test
	public void testHistoricalCaseloadCountIncludesExpiredProgramStatusesOverlappingFromFilter()
			throws ObjectNotFoundException, ValidationException {
		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		jamesDoe.setCoach(kevinSmith);
		// We happen to know jamesDoe has no program statuses set in the
		// default data, otherwise this would potentially corrupt his record...
		// you can't have overlapping statuses, but nothing in the code really
		//prevents that.
		setStatus(Stubs.ProgramStatusFixture.ACTIVE, threeDaysAgo(), oneDayAgo(), true, jamesDoe);
		saveAndFlush(jamesDoe);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> resultsOverlappingNow =
				dao.caseLoadCountsByStatus(null, new Date(), null, null);

		// should be the same as the default report plus the expired record
		// we added above
		expectCurrentUnfilteredResultsAgainstDefaultDataSet(resultsOverlappingNow);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> resultsOverlappingTwoDaysAgo =
				dao.caseLoadCountsByStatus(null, twoDaysAgo(), null, null);
		final List<CoachCaseloadRecordCountForProgramStatus> expectedRecords =
				Lists.newArrayListWithCapacity(2);
		// We just happen to know the correct ordering of expected results here
		// fragile, yes, but there's a lot of static expectations re the
		// default data fixtures scattered throughout the tests
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedRecords);
		collectCurrentCoachStatusCountsForDefaultDataSet(expectedRecords);
		assertCaseloadCountCollectionsEqual(expectedRecords,
				resultsOverlappingTwoDaysAgo.getRows());
		assertEquals("Unexpected total result count", expectedRecords.size(),
				resultsOverlappingTwoDaysAgo.getResults());
	}

	@Test
	public void testCurrentCaseloadCountExcludesExpiredProgramStatuses()
			throws ObjectNotFoundException, ValidationException {

		// doesn't really add much... basically just a pair with
		// testHistoricalCaseloadCountIncludesExpiredProgramStatusesOverlappingFromOrToFilters(),
		// with which it shares a fixture, but this test really just re-verifies
		// what testCurrentCaseLoadCountsByStatusForDefaultDataSetAndNoFilters()
		// had already verified, but with a slightly different db fixture

		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		jamesDoe.setCoach(kevinSmith);
		// We happen to know jamesDoe has no program statuses set in the
		// default data, otherwise this would potentially corrupt his record...
		// you can't have overlapping statuses, but nothing in the code really
		//prevents that.
		setStatus(Stubs.ProgramStatusFixture.ACTIVE, threeDaysAgo(), oneDayAgo(), true, jamesDoe);
		saveAndFlush(jamesDoe);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results =
				dao.currentCaseLoadCountsByStatus(null, null);

		// should be the same as the default report, which should only count
		// statuses active at this moment (despite the "unfiltered" name
		// here)
		expectCurrentUnfilteredResultsAgainstDefaultDataSet(results);

	}

	@Test
	public void testHistoricalCaseloadCountIncludesFutureProgramStatusesAndPreviouslyExpiredStatuseEffectivePriorToToFilter()
			throws ObjectNotFoundException, ValidationException {

		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		jamesDoe.setCoach(kevinSmith);
		// See testHistoricalCaseloadCountIncludesExpiredProgramStatusesOverlappingFromFilter() for
		// more notes on issues w/ the underlying data model.
		//
		// Also, we use a program status effective date range in the future even
		// though there's no real use case for that b/c it lets us prove we're
		// actually using the "to" filter. If we put the range in the past we
		// couldn't be sure whether the "to" filter was really being used as
		// such or if it was inadvertently implemented identically to the "from"
		// filter.
		setStatus(Stubs.ProgramStatusFixture.ACTIVE, oneDayFromNow(), threeDaysFromNow(), true, jamesDoe);
		saveAndFlush(jamesDoe);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> resultsOverlappingNow =
				dao.caseLoadCountsByStatus(null, null, new Date(), null);

		// to filter should exclude the new status we created above
		expectHistoricalUnfilteredResultsAgainstDefaultDataSet(resultsOverlappingNow);

		// change the to filter to include the new status
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus>
				resultsPriorToAndOverlappingTwoDaysFromNow =
				dao.caseLoadCountsByStatus(null, null, twoDaysFromNow(), null);

		final List<CoachCaseloadRecordCountForProgramStatus> expectedRecords =
				Lists.newArrayListWithCapacity(3);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedRecords);
		collectUnfilteredCoachStatusCountsForDefaultDataSet(expectedRecords);

		assertCaseloadCountCollectionsEqual(expectedRecords,
				resultsPriorToAndOverlappingTwoDaysFromNow.getRows());
		assertEquals("Unexpected total result count", expectedRecords.size(),
				resultsPriorToAndOverlappingTwoDaysFromNow.getResults());
	}

	@Test
	public void testCurrentCaseloadCountIncludesFutureProgramStatuses()
			throws ObjectNotFoundException, ValidationException {

		// same fixture as
		// testHistoricalCaseloadCountIncludesFutureProgramStatusesAndPreviouslyExpiredStatuseEffectivePriorToToFilter()
		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		jamesDoe.setCoach(kevinSmith);
		setStatus(Stubs.ProgramStatusFixture.ACTIVE, oneDayFromNow(), threeDaysFromNow(), true, jamesDoe);
		saveAndFlush(jamesDoe);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results =
				dao.currentCaseLoadCountsByStatus(null, null);

		final List<CoachCaseloadRecordCountForProgramStatus> expectedRecords =
				Lists.newArrayListWithCapacity(2);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedRecords);
		collectCurrentCoachStatusCountsForDefaultDataSet(expectedRecords);

		assertCaseloadCountCollectionsEqual(expectedRecords, results.getRows());
		assertEquals("Unexpected total result count", expectedRecords.size(),
				results.getResults());
	}

	@Test
	public void testHistoricalCaseloadCountsRespectCombinedFromAndToFilters()
			throws ObjectNotFoundException, ValidationException {

		// want to make sure both filters work at the same time and can handle
		// all modes of overlap, i.e.:
		//   1) only overlap from,
		//   2) only overlap to,
		//   3) overlap both,
		//   4) sit between both

		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person markGalafion = person(Stubs.PersonFixture.MARK_GALAFRION);
		final Person bobReynolds = person(Stubs.PersonFixture.BOB_REYNOLDS);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		final Person faculty0 = person(Stubs.PersonFixture.FACULTY_0); // "Douglas Toya"
		final Person faculty1 = person(Stubs.PersonFixture.FACULTY_1); // "Mary Webber"


		jamesDoe.setCoach(faculty0);
		markGalafion.setCoach(faculty0);
		bobReynolds.setCoach(faculty0);
		kevinSmith.setCoach(faculty0);
		faculty1.setCoach(faculty0);

		// from will be 2 days ago, to will be 2 days from now
		// only overlap from
		setStatus(Stubs.ProgramStatusFixture.ACTIVE, threeDaysAgo(), oneDayAgo(), true, jamesDoe);
		// only overlap to
		setStatus(Stubs.ProgramStatusFixture.INACTIVE, oneDayFromNow(), threeDaysFromNow(), true, markGalafion);
		// overlap from and to
		setStatus(Stubs.ProgramStatusFixture.NO_SHOW, threeDaysAgo(), threeDaysFromNow(), true, bobReynolds);
		// sits in between from and to
		setStatus(Stubs.ProgramStatusFixture.NON_PARTICIPATING, twoDaysAgo(), twoDaysFromNow(), true, kevinSmith);
		// doesn't intersect with the from-to range at all
		setStatus(Stubs.ProgramStatusFixture.ACTIVE, fiveDaysAgo(), fourDaysAgo(), true, faculty1);
		saveAndFlush(jamesDoe, markGalafion, bobReynolds, kevinSmith, faculty1);

		// plus remember in the default data advisor0 has two statuses that
		// expired prior to 'from' and two statuses that were effective before
		// from and have no expiration.

		// lots of sanity checks to make sure we're testing what we think we're
		// testing. yes, this means this test does have some overlap with
		// other tests of these same date bound filters.

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> unfilteredResults =
				dao.caseLoadCountsByStatus(null, null, null, null);

		// should get everything
		final List<CoachCaseloadRecordCountForProgramStatus> expectedUnfilteredRecords =
				Lists.newArrayListWithCapacity(6);
		// james doe and mary webber
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.ACTIVE, 2, expectedUnfilteredRecords);
		// mark g
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedUnfilteredRecords);
		// kevin smith
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedUnfilteredRecords);
		// bob reynolds
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedUnfilteredRecords);
		collectUnfilteredCoachStatusCountsForDefaultDataSet(expectedUnfilteredRecords);
		assertCaseloadCountCollectionsEqual(expectedUnfilteredRecords,
				unfilteredResults.getRows());
		assertEquals("Unexpected total result count", expectedUnfilteredRecords.size(),
				unfilteredResults.getResults());

		// yes, we do have other tests to verify that the 'from' filter works as
		// it should by itself.
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> fromFilteredResults =
				dao.caseLoadCountsByStatus(null, twoDaysAgo(), null, null);
		final List<CoachCaseloadRecordCountForProgramStatus> expectedFromFilteredRecords =
				Lists.newArrayListWithCapacity(5);
		// james doe only (excludes mary webber)
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedFromFilteredRecords);
		// mark g
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedFromFilteredRecords);
		// kevin smith
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedFromFilteredRecords);
		// bob reynolds
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedFromFilteredRecords);
		collectCurrentCoachStatusCountsForDefaultDataSet(expectedFromFilteredRecords);
		assertCaseloadCountCollectionsEqual(expectedFromFilteredRecords,
				fromFilteredResults.getRows());
		assertEquals("Unexpected total result count", expectedFromFilteredRecords.size(),
				fromFilteredResults.getResults());

		// yes, we do have other tests to verify that the 'to' filter works as
		// it should by itself.
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> toFilteredResults =
				dao.caseLoadCountsByStatus(null, null, twoDaysFromNow(), null);

		// should get you everything, including the two expired records from
		// the default data
		final List<CoachCaseloadRecordCountForProgramStatus> expectedToFilteredRecords =
				Lists.newArrayListWithCapacity(6);
		// james doe and mary webber
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.ACTIVE, 2, expectedToFilteredRecords);
		// mark g.
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedToFilteredRecords);
		// kevin smith
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedToFilteredRecords);
		// bob reynolds
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedToFilteredRecords);
		collectUnfilteredCoachStatusCountsForDefaultDataSet(expectedToFilteredRecords);
		assertCaseloadCountCollectionsEqual(expectedToFilteredRecords,
				toFilteredResults.getRows());
		assertEquals("Unexpected total result count", expectedToFilteredRecords.size(),
				toFilteredResults.getResults());

		// now what we're *really* after
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> toAndFromFilteredResults =
				dao.caseLoadCountsByStatus(null, twoDaysAgo(), twoDaysFromNow(), null);
		// should get you everything, except the two expired records from
		// the default data and the expired mary webber record created above

		final List<CoachCaseloadRecordCountForProgramStatus> expectedToAndFromFilteredRecords =
				Lists.newArrayListWithCapacity(5);
		// james doe
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedToAndFromFilteredRecords);
		// mark g.
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedToAndFromFilteredRecords);
		// kevin smith
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedToAndFromFilteredRecords);
		// bob reynolds
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedToAndFromFilteredRecords);
		collectCurrentCoachStatusCountsForDefaultDataSet(expectedToAndFromFilteredRecords);
		assertCaseloadCountCollectionsEqual(expectedToAndFromFilteredRecords,
				toAndFromFilteredResults.getRows());
		assertEquals("Unexpected total result count",
				expectedToAndFromFilteredRecords.size(),
				toAndFromFilteredResults.getResults());
	}


	@Test
	public void testCurentCaseloadCountsWithComplexStatusFixture()
			throws ObjectNotFoundException, ValidationException {

		// same fixture as
		// testHistoricalCaseloadCountsRespectCombinedFromAndToFilters()... this
		// is pretty much just a sanity check that there's not some subtle
		// problem that would cause the "current" query to blow up with a
		// more complicated dataset

		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person markGalafion = person(Stubs.PersonFixture.MARK_GALAFRION);
		final Person bobReynolds = person(Stubs.PersonFixture.BOB_REYNOLDS);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		final Person faculty0 = person(Stubs.PersonFixture.FACULTY_0); // "Douglas Toya"
		final Person faculty1 = person(Stubs.PersonFixture.FACULTY_1); // "Mary Webber"

		jamesDoe.setCoach(faculty0);
		markGalafion.setCoach(faculty0);
		bobReynolds.setCoach(faculty0);
		kevinSmith.setCoach(faculty0);
		faculty1.setCoach(faculty0);

		setStatus(Stubs.ProgramStatusFixture.ACTIVE, threeDaysAgo(), oneDayAgo(), true, jamesDoe);
		setStatus(Stubs.ProgramStatusFixture.INACTIVE, oneDayFromNow(), threeDaysFromNow(), true, markGalafion);
		setStatus(Stubs.ProgramStatusFixture.NO_SHOW, threeDaysAgo(), threeDaysFromNow(), true, bobReynolds);
		setStatus(Stubs.ProgramStatusFixture.NON_PARTICIPATING, twoDaysAgo(), twoDaysFromNow(), true, kevinSmith);
		setStatus(Stubs.ProgramStatusFixture.ACTIVE, fiveDaysAgo(), fourDaysAgo(), true, faculty1);
		saveAndFlush(jamesDoe, markGalafion, bobReynolds, kevinSmith, faculty1);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results =
				dao.currentCaseLoadCountsByStatus(null, null);

		final List<CoachCaseloadRecordCountForProgramStatus> expectedRecords =
				Lists.newArrayListWithCapacity(2);

		// mark g.
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedRecords);
		// kevin smith
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedRecords);
		// bob reynolds
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.FACULTY_0,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedRecords);
		collectCurrentCoachStatusCountsForDefaultDataSet(expectedRecords);

		assertCaseloadCountCollectionsEqual(expectedRecords, results.getRows());
		assertEquals("Unexpected total result count", expectedRecords.size(),
				results.getResults());

	}

	@Test
	public void testHistoricalCaseloadCountWithFromFilterInTheFuture() {
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results =
				dao.caseLoadCountsByStatus(null, daysPlusMinus(100), null, null);
		// some statuses in default data set have no expiration.
		expectCurrentUnfilteredResultsAgainstDefaultDataSet(results);
	}

	@Test
	public void testHistoricalCaseloadCountWithNoResults() {
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results =
				dao.caseLoadCountsByStatus(null, daysPlusMinus(-1000), daysPlusMinus(-1000), null);
		assertEquals("Should have been no results",
				Lists.newArrayListWithExpectedSize(0), results.getRows());
		assertEquals("Unexpected zero total results", 0, results.getResults());
	}

	@Test
	public void testHistoricalCaseloadCountWithSingleStudentTypeIdFilterWithAtLeastOneMatch() {
		// are two distinct student types on students with any sort of
		// status at all in the default data set: ILP and CAP

		// first a sanity check... only use a 'to' filter so we get the complete
		// set of statuses, included expired
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> toFilteredResults =
				dao.caseLoadCountsByStatus(null, null, new Date(), null);

		List<CoachCaseloadRecordCountForProgramStatus> expectedToFilteredRecords =
				Lists.newArrayListWithCapacity(2);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 3, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.TRANSITIONED, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedToFilteredRecords);
		assertCaseloadCountCollectionsEqual(expectedToFilteredRecords,
				toFilteredResults.getRows());
		assertEquals("Unexpected total result count", 7, toFilteredResults.getResults());

		// now one of the actual tests
		final List<UUID> capFilter = Lists.newArrayList(Stubs.StudentTypeFixture.CAP.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> capAndToFilteredResults =
				dao.caseLoadCountsByStatus(capFilter, null, new Date(), null);
		List<CoachCaseloadRecordCountForProgramStatus> expectedCapFilteredRecords =
				Lists.newArrayListWithCapacity(1);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedCapFilteredRecords);
		assertCaseloadCountCollectionsEqual(expectedCapFilteredRecords,
				capAndToFilteredResults.getRows(), 1);
		assertEquals("Unexpected total result count", 3, capAndToFilteredResults.getResults());

		// another one
		final List<UUID> ilpFilter = Lists.newArrayList(Stubs.StudentTypeFixture.ILP.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> ilpAndToFilteredResults =
				dao.caseLoadCountsByStatus(ilpFilter, null, new Date(), null);
		List<CoachCaseloadRecordCountForProgramStatus> expectedIlpFilteredRecords =
				Lists.newArrayListWithCapacity(5);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 2, expectedIlpFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedIlpFilteredRecords);
		
		assertCaseloadCountCollectionsEqual(expectedIlpFilteredRecords,
				ilpAndToFilteredResults.getRows(), 2);
		assertEquals("Unexpected total result count", 4, ilpAndToFilteredResults.getResults());
	}

	@Test
	public void testCurrentCaseloadCountWithSingleStudentTypeIdFilterWithAtLeastOneMatch()
			throws ObjectNotFoundException, ValidationException {
		// are two distinct student types on students with any sort of
		// status at all in the default data set: ILP and CAP, and both are
		// currently non-expired but have the same status, so we have to
		// change the status on one of them to make sure our test is meaningful
		Person student0 = person(Stubs.PersonFixture.STUDENT_0);
		deactivate(student0);
		saveAndFlush(student0);

		// first a sanity check...
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> unfilteredRecords =
				dao.currentCaseLoadCountsByStatus(null, null);

		final List<CoachCaseloadRecordCountForProgramStatus> expectedUnfilteredRecords =
				Lists.newArrayListWithCapacity(2);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedUnfilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedUnfilteredRecords);
		
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedUnfilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedUnfilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedUnfilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.TRANSITIONED, 1, expectedUnfilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedUnfilteredRecords);
		
		assertCaseloadCountCollectionsEqual(expectedUnfilteredRecords,
				unfilteredRecords.getRows());
		assertEquals(expectedUnfilteredRecords.size(), unfilteredRecords.getResults());

		// now one of the actual tests
		final List<UUID> capFilter = Lists.newArrayList(Stubs.StudentTypeFixture.CAP.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> capAndToFilteredResults =
				dao.currentCaseLoadCountsByStatus(capFilter, null);
		List<CoachCaseloadRecordCountForProgramStatus> expectedCapFilteredRecords =
				Lists.newArrayListWithCapacity(1);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedCapFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedCapFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedCapFilteredRecords);
		
		assertCaseloadCountCollectionsEqual(expectedCapFilteredRecords,
				capAndToFilteredResults.getRows());
		assertEquals("Unexpected total result count", 3, capAndToFilteredResults.getResults());

		// another one
		final List<UUID> ilpFilter = Lists.newArrayList(Stubs.StudentTypeFixture.ILP.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> ilpAndToFilteredResults =
				dao.currentCaseLoadCountsByStatus(ilpFilter, null);
		List<CoachCaseloadRecordCountForProgramStatus> expectedIlpFilteredRecords =
				Lists.newArrayListWithCapacity(1);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedIlpFilteredRecords);
		assertCaseloadCountCollectionsEqual(expectedIlpFilteredRecords,
				ilpAndToFilteredResults.getRows(), 1);
		assertEquals("Unexpected total result count", 3, ilpAndToFilteredResults.getResults());
	}

	@Test
	public void testHistoricalCaseloadCountWithStudentTypeIdFilterWithNoMatch() {
		final List<UUID> ealFilter = Lists.newArrayList(Stubs.StudentTypeFixture.EAL.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> ealAndToFilteredResults =
				dao.caseLoadCountsByStatus(ealFilter, null, new Date(), null);
		
		List<CoachCaseloadRecordCountForProgramStatus> expectedFilterWithNoMatch =
				Lists.newArrayListWithCapacity(1);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedFilterWithNoMatch);
		
		assertCaseloadCountCollectionsEqual(expectedFilterWithNoMatch,
				ealAndToFilteredResults.getRows());
		
		assertEquals("Unexpected zero total results", 1, ealAndToFilteredResults.getResults());
	}

	@Test
	public void testCurrentCaseloadCountWithStudentTypeIdFilterWithNoMatch() {
		final List<UUID> ealFilter = Lists.newArrayList(Stubs.StudentTypeFixture.EAL.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> ealAndToFilteredResults =
				dao.currentCaseLoadCountsByStatus(ealFilter, null);
		
		List<CoachCaseloadRecordCountForProgramStatus> expectedFilterWithNoMatch =
				Lists.newArrayListWithCapacity(1);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedFilterWithNoMatch);
		
		assertCaseloadCountCollectionsEqual(expectedFilterWithNoMatch,
				ealAndToFilteredResults.getRows());
		
		assertEquals("Unexpected zero total results", 1, ealAndToFilteredResults.getResults());
	}

	@Test
	public void  testHistoricalCaseloadCountWithMultipleStudentTypeIdFilter() throws ObjectNotFoundException, ValidationException {

		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		jamesDoe.setCoach(kevinSmith);
		jamesDoe.setStudentType(studentType(Stubs.StudentTypeFixture.EAL));
		activate(jamesDoe);
		saveAndFlush(jamesDoe);

		// first the sanity check - get a baseline before applying filters
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> toFilteredResults =
				dao.caseLoadCountsByStatus(null, null, new Date(), null);

		List<CoachCaseloadRecordCountForProgramStatus> expectedToFilteredRecords =
				Lists.newArrayListWithCapacity(3);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 3, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedToFilteredRecords);
		
		
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.TRANSITIONED, 1, expectedToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedToFilteredRecords);
		
		assertCaseloadCountCollectionsEqual(expectedToFilteredRecords,
				toFilteredResults.getRows());
		assertEquals("Unexpected total result count", 8, toFilteredResults.getResults());

		// now the real test
		final List<UUID> typeFilters = Lists.newArrayList(
				Stubs.StudentTypeFixture.CAP.id(),
				Stubs.StudentTypeFixture.EAL.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> typeAndToFilteredResults =
				dao.caseLoadCountsByStatus(typeFilters, null, new Date(), null);

		List<CoachCaseloadRecordCountForProgramStatus> expectedTypeAndToFilteredRecords =
				Lists.newArrayListWithCapacity(2);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedTypeAndToFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedTypeAndToFilteredRecords);
		
		assertCaseloadCountCollectionsEqual(expectedTypeAndToFilteredRecords,
				typeAndToFilteredResults.getRows(), 2);
		assertEquals("Unexpected total result count", 5, typeAndToFilteredResults.getResults());
	}

	@Test
	public void testHistoricalCaseloadCountWithWithAllFilters() throws ObjectNotFoundException, ValidationException {
		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		jamesDoe.setCoach(kevinSmith);
		jamesDoe.setStudentType(studentType(Stubs.StudentTypeFixture.EAL));
		setStatus(Stubs.ProgramStatusFixture.ACTIVE, threeDaysAgo(), oneDayAgo(), true, jamesDoe);
		saveAndFlush(jamesDoe);

		// we have other tests that verify the two date filters work together, so
		// let's just focus on adding type filters to that mix
		final List<UUID> capFilter = Lists.newArrayList(Stubs.StudentTypeFixture.CAP.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> capAndDateFilteredResults =
				dao.caseLoadCountsByStatus(capFilter, twoDaysAgo(), new Date(), null);

		List<CoachCaseloadRecordCountForProgramStatus> expectedCapAndDateFilteredRecords =
				Lists.newArrayListWithCapacity(2);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedCapAndDateFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.INACTIVE, 1, expectedCapAndDateFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, expectedCapAndDateFilteredRecords);
		
		assertCaseloadCountCollectionsEqual(expectedCapAndDateFilteredRecords,
				capAndDateFilteredResults.getRows());
		assertEquals("Unexpected total result count", 3, capAndDateFilteredResults.getResults());

		final List<UUID> typeFilters = Lists.newArrayList(
				Stubs.StudentTypeFixture.CAP.id(),
				Stubs.StudentTypeFixture.EAL.id());
		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> typeAndDateFilteredResults =
				dao.caseLoadCountsByStatus(typeFilters, twoDaysAgo(), new Date(), null);

		List<CoachCaseloadRecordCountForProgramStatus> expectedTypeAndDateFilteredRecords =
				Lists.newArrayListWithCapacity(2);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.KEVIN_SMITH,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedTypeAndDateFilteredRecords);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 1, expectedTypeAndDateFilteredRecords);
		
		assertCaseloadCountCollectionsEqual(expectedTypeAndDateFilteredRecords,
				typeAndDateFilteredResults.getRows(), 2);
		assertEquals("Unexpected total result count", 5, typeAndDateFilteredResults.getResults());
	}

	@Test
	public void testHistoricalCaseloadCountWithPaging() throws ObjectNotFoundException, ValidationException {
		// set up the same fixtures as in testMultipleCoachesAndMultipleStatusesButNoFilters()
		// so we have several coaches to deal with
		final Person jamesDoe = person(Stubs.PersonFixture.JAMES_DOE);
		final Person markGalafion = person(Stubs.PersonFixture.MARK_GALAFRION);
		final Person bobReynolds = person(Stubs.PersonFixture.BOB_REYNOLDS);
		final Person kevinSmith = person(Stubs.PersonFixture.KEVIN_SMITH);
		final Person faculty0 = person(Stubs.PersonFixture.FACULTY_0);

		jamesDoe.setCoach(kevinSmith);
		markGalafion.setCoach(kevinSmith);
		bobReynolds.setCoach(faculty0);
		activate(jamesDoe, markGalafion, bobReynolds);
		deactivate(jamesDoe);
		saveAndFlush(jamesDoe, markGalafion, bobReynolds);

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> firstResult =
				dao.caseLoadCountsByStatus(null, null, null,
						new SortingAndPaging(ObjectStatus.ALL, 0, 1, null, null, null));

		final List<CoachCaseloadRecordCountForProgramStatus> firstExpectedRecord =
				Lists.newArrayList(personStubToCoachStatusCount(
						Stubs.PersonFixture.KEVIN_SMITH,
						Stubs.ProgramStatusFixture.ACTIVE, 2)); // james doe expired active status included in this

		assertCaseloadCountCollectionsEqual(firstExpectedRecord, firstResult.getRows());
		assertEquals(10, firstResult.getResults());

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> secondResult =
				dao.caseLoadCountsByStatus(null, null, null,
						new SortingAndPaging(ObjectStatus.ALL, 1, 1, null, null, null));

		final List<CoachCaseloadRecordCountForProgramStatus> secondExpectedRecord =
				Lists.newArrayList(personStubToCoachStatusCount(
						Stubs.PersonFixture.KEVIN_SMITH,
						Stubs.ProgramStatusFixture.INACTIVE, 1));

		assertCaseloadCountCollectionsEqual(secondExpectedRecord, secondResult.getRows(), 1);
		assertEquals(10, secondResult.getResults());

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> thirdResult =
				dao.caseLoadCountsByStatus(null, null, null,
						new SortingAndPaging(ObjectStatus.ALL, 2, 1, null, null, null));

		final List<CoachCaseloadRecordCountForProgramStatus> thirdExpectedRecord =
				Lists.newArrayList(personStubToCoachStatusCount(
						Stubs.PersonFixture.FACULTY_0, // "Douglas Toya"
						Stubs.ProgramStatusFixture.ACTIVE, 1));

		assertCaseloadCountCollectionsEqual(thirdExpectedRecord, thirdResult.getRows());
		assertEquals(10, thirdResult.getResults());

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> fourthResult =
				dao.caseLoadCountsByStatus(null, null, null,
						new SortingAndPaging(ObjectStatus.ALL, 3, 1, null, null, null));

		final List<CoachCaseloadRecordCountForProgramStatus> fourthExpectedRecord =
				Lists.newArrayList(personStubToCoachStatusCount(
						Stubs.PersonFixture.ADVISOR_0, // "Alan Turing"
						Stubs.ProgramStatusFixture.ACTIVE, 3));

		assertCaseloadCountCollectionsEqual(fourthExpectedRecord, fourthResult.getRows());
		assertEquals(10, fourthResult.getResults());

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> fifthResult =
				dao.caseLoadCountsByStatus(null, null, null,
						new SortingAndPaging(ObjectStatus.ALL, 4, 1, null, null, null));

		final List<CoachCaseloadRecordCountForProgramStatus> fifthExpectedRecord =
				Lists.newArrayList(personStubToCoachStatusCount(
						Stubs.PersonFixture.ADVISOR_0, // "Alan Turing"
						Stubs.ProgramStatusFixture.INACTIVE, 1));

		assertCaseloadCountCollectionsEqual(fifthExpectedRecord, fifthResult.getRows());
		assertEquals(10, fifthResult.getResults());

		final PagingWrapper<CoachCaseloadRecordCountForProgramStatus> sixthResult =
				dao.caseLoadCountsByStatus(null, null, null,
						new SortingAndPaging(ObjectStatus.ALL, 5, 1, null, null, null));

		final List<CoachCaseloadRecordCountForProgramStatus> sixthExpectedRecord =
				Lists.newArrayListWithExpectedSize(0);

		assertCaseloadCountCollectionsEqual(sixthExpectedRecord, sixthResult.getRows(), 0);
		assertEquals(10, sixthResult.getResults());

	}

	private void expectHistoricalUnfilteredResultsAgainstDefaultDataSet(
			PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results) {
		final List<CoachCaseloadRecordCountForProgramStatus> expectedRecords =
				Lists.newArrayListWithCapacity(2);
		collectUnfilteredCoachStatusCountsForDefaultDataSet(expectedRecords);
		//assertEquals("Unexpected record(s)", expectedRecords, results.getRows());
		assertCaseloadCountCollectionsEqual(expectedRecords, results.getRows());
		assertEquals("Unexpected total result count", expectedRecords.size(), results.getResults());
	}

	private void collectUnfilteredCoachStatusCountsForDefaultDataSet(
			List<CoachCaseloadRecordCountForProgramStatus> collectInto) {
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 3, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.INACTIVE, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.ACTIVE, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.INACTIVE, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.TRANSITIONED, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, collectInto);

		
	}

	private void expectCurrentUnfilteredResultsAgainstDefaultDataSet(
			PagingWrapper<CoachCaseloadRecordCountForProgramStatus> results) {
		final List<CoachCaseloadRecordCountForProgramStatus> expectedRecords =
				Lists.newArrayListWithCapacity(1);
		collectCurrentCoachStatusCountsForDefaultDataSet(expectedRecords);
		assertCaseloadCountCollectionsEqual(expectedRecords, results.getRows());
		assertEquals("Unexpected total result count", expectedRecords.size(), results.getResults());
	}

	private void collectCurrentCoachStatusCountsForDefaultDataSet(
			List<CoachCaseloadRecordCountForProgramStatus> collectInto) {
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.ADVISOR_0,
				Stubs.ProgramStatusFixture.ACTIVE, 2, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.ACTIVE, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.INACTIVE, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NON_PARTICIPATING, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.TRANSITIONED, 1, collectInto);
		collectPersonStubToCoachStatusCount(Stubs.PersonFixture.COACH_1,
				Stubs.ProgramStatusFixture.NO_SHOW, 1, collectInto);
	}

	private void collectPersonStubToCoachStatusCount(
			Stubs.PersonFixture personFixture,
			Stubs.ProgramStatusFixture programStatusFixture,
			int count,
			List<CoachCaseloadRecordCountForProgramStatus> collectInto) {
		collectInto.add(personStubToCoachStatusCount(personFixture, programStatusFixture, count));
	}

	private CoachCaseloadRecordCountForProgramStatus personStubToCoachStatusCount(
			Stubs.PersonFixture personFixture,
			Stubs.ProgramStatusFixture statusFixture,
			int count) {
		return new CoachCaseloadRecordCountForProgramStatus(
				personFixture.id(),
				statusFixture.id(),
				count,
				personFixture.username(),
				personFixture.schoolId(),
				personFixture.firstName(),
				personFixture.middleName(),
				personFixture.lastName(),
				personFixture.departmentName());
	}

	private void saveAndFlush(Person... persons) throws ObjectNotFoundException {
		for ( Person person : persons ) {
			personService.save(person);
		}
		sessionFactory.getCurrentSession().flush();
	}



	private Date oneDayAgo() {
		return daysPlusMinus(-1);
	}

	private Date twoDaysAgo() {
		return daysPlusMinus(-2);
	}

	private Date threeDaysAgo() {
		return daysPlusMinus(-3);
	}

	private Date fourDaysAgo() {
		return daysPlusMinus(-4);
	}

	private Date fiveDaysAgo() {
		return daysPlusMinus(-5);
	}

	private Date oneDayFromNow() {
		return daysPlusMinus(1);
	}

	private Date twoDaysFromNow() {
		return daysPlusMinus(2);
	}

	private Date threeDaysFromNow() {
		return daysPlusMinus(3);
	}

	private Date fourDaysFromNow() {
		return daysPlusMinus(4);
	}

	private Date fiveDaysFromNow() {
		return daysPlusMinus(5);
	}

	private Date daysPlusMinus(int days) {
		return DateUtils.addDays(new Date(), days);
	}

	private Person person(Stubs.PersonFixture personFixture)
			throws ObjectNotFoundException {
		return Stubs.person(personFixture, personService);
	}

	private StudentType studentType(Stubs.StudentTypeFixture typeFixture)
			throws ObjectNotFoundException {
		return Stubs.studentType(typeFixture, studentTypeService);
	}

	private void activate(Person... persons)
			throws ObjectNotFoundException, ValidationException {
		Stubs.activateInProgram(personService, personProgramStatusService,
				programStatusService, persons);
	}

	private void deactivate(Person... persons)
			throws ObjectNotFoundException, ValidationException {
		Stubs.deactivateInProgram(personService, personProgramStatusService,
				programStatusService, persons);
	}

	private void setStatus(Stubs.ProgramStatusFixture statusFixture,
						   Date effectiveOn,
						   Date expiredOn,
						   boolean makeCurrent,
						   Person... persons)
			throws ObjectNotFoundException, ValidationException {
		Stubs.setProgramStatus(personService, personProgramStatusService,
				programStatusService, statusFixture, effectiveOn, expiredOn,
				makeCurrent, persons);
	}

	private void assertCaseloadCountCollectionsEqual(Collection<CoachCaseloadRecordCountForProgramStatus> expected,
													 Collection<CoachCaseloadRecordCountForProgramStatus> actual) {
		CoachCaseloadRecordCountForProgramStatus[] actualArray =
				actual.toArray(new CoachCaseloadRecordCountForProgramStatus[actual.size()]);
		assertThat(actualArray, array(matchersFor(expected)));
	}
	
	private void assertCaseloadCountCollectionsEqual(Collection<CoachCaseloadRecordCountForProgramStatus> expected,
			 Collection<CoachCaseloadRecordCountForProgramStatus> actual, Integer testCount) {
		Iterator<CoachCaseloadRecordCountForProgramStatus> iter = actual.iterator();
		ArrayList<CoachCaseloadRecordCountForProgramStatus> resizedActual = new ArrayList<CoachCaseloadRecordCountForProgramStatus>();
		Integer i = 0;
		while(iter.hasNext()){
			if(i < testCount)
				resizedActual.add(iter.next());
			else
				break;
			i++;
		}
		CoachCaseloadRecordCountForProgramStatus[] actualArray =
				resizedActual.toArray(new CoachCaseloadRecordCountForProgramStatus[testCount]);
		assertThat(actualArray, array(matchersFor(expected)));
	}

	private Matcher<CoachCaseloadRecordCountForProgramStatus>[]
		matchersFor(Collection<CoachCaseloadRecordCountForProgramStatus> caseloadCounts) {
		Matcher<CoachCaseloadRecordCountForProgramStatus>[] matchers =
				new Matcher[caseloadCounts.size()];
		int i = 0;
		for ( CoachCaseloadRecordCountForProgramStatus caseloadCount : caseloadCounts ) {
			matchers[i++] = matcherFor(caseloadCount);
		}
		return matchers;
	}

	private Matcher<CoachCaseloadRecordCountForProgramStatus>
		matcherFor(final CoachCaseloadRecordCountForProgramStatus caseloadCount) {
		return new CustomTypeSafeMatcher<CoachCaseloadRecordCountForProgramStatus>(caseloadCount.toString()) {
			@Override
			protected boolean matchesSafely(CoachCaseloadRecordCountForProgramStatus item) {
				return item.equalsAllFields(caseloadCount);
			}
		};
	}

}