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
package org.jasig.ssp.web.api.external; // NOPMD

import java.util.Collection;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.jasig.ssp.model.external.WriteableExternalFacultyCourse;
import org.jasig.ssp.model.external.WriteableExternalFacultyCourseRoster;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.external.ExternalFacultyCourseRosterTO;
import org.jasig.ssp.transferobject.external.FacultyCourseTO;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * {@link FacultyCourseController} tests
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class FacultyCourseControllerIntegrationTest {

	@Autowired
	private transient FacultyCourseController controller;

	@Autowired
	private transient SessionFactory sessionFactory;

	private static final String FACULTY_SCHOOL_ID = "uf928711";

	private static final String TERM_CODE = "FA12";

	private static final String FORMATTED_COURSE = "MTH101";

	private static final String TITLE = "College Algebra";

	private static final String LAST_NAME = "Doe";

	/**
	 * Test the {@link FacultyCourseController#getAllCoursesForFaculty(String)}
	 * action.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGetAllCoursesForFaculty()
			throws ObjectNotFoundException, ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final PagedResponse<FacultyCourseTO> list = controller
				.getAllCoursesForFaculty(FACULTY_SCHOOL_ID);
		final FacultyCourseTO obj = list.getRows().iterator().next();

		assertNotNull(
				"Returned FacultyCourseTO from the controller should not have been null.",
				obj);

		assertEquals("Returned FacultyCourse.TermCode did not match.",
				TERM_CODE, obj.getTermCode());
		assertEquals("Returned FacultyCourse.FormattedCourse did not match.",
				FORMATTED_COURSE, obj.getFormattedCourse());
		assertEquals("Returned FacultyCourse.Title did not match.", TITLE,
				obj.getTitle());
	}

	/**
	 * Test that the {@link FacultyCourseController#getRoster(String, String, String)}
	 * action returns the correct validation errors when an invalid ID is sent.
	 * 
	 * @throws ValidationException
	 *             If validation error occurred.
	 * @throws ObjectNotFoundException
	 *             If object could not be found.
	 */
	@Test
	public void testControllerGetOfInvalidId() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final PagedResponse<ExternalFacultyCourseRosterTO> obj = controller.getRoster(
				"invalid id", "invalid id", null);

		assertEquals(
				"Returned FacultyCourseTO from the controller should have been null.",
				0, obj.getResults());
	}

	/**
	 * Test the
	 * {@link FacultyCourseController#getAll(Integer, Integer, String, String)}
	 * action.
	 */
	@Test
	public void testControllerAll() {
		final Collection<FacultyCourseTO> list = controller.getAll(null, null,
				null,
				null).getRows();

		assertNotNull("List should not have been null.", list);
		assertNotEmpty("List action should have returned some objects.", list);
	}

	/**
	 * Test the
	 * {@link FacultyCourseController#getAll(Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllResults() {
		final Collection<FacultyCourseTO> list = controller.getAll(null, null,
				null, null).getRows();

		final Iterator<FacultyCourseTO> iter = list.iterator();

		FacultyCourseTO facultyCourse = iter.next();
		assertTrue("Title should have been longer than 0 characters.",
				facultyCourse.getTitle().length() > 0);

		facultyCourse = iter.next();
		assertNotNull("TermCode should not have been null.",
				facultyCourse.getTermCode());
	}

	/**
	 * Test the
	 * {@link FacultyCourseController#getAll(Integer, Integer, String, String)}
	 * action results.
	 */
	@Test
	public void testControllerGetAllLimits() {
		// arrange, act
		final Collection<FacultyCourseTO> list = controller.getAll(null, null,
				null,
				null).getRows();
		final Collection<FacultyCourseTO> filtered = controller.getAll(0, 1,
				null,
				null).getRows();

		// assert
		assertTrue("List should have returned several data rows.",
				list.size() > 1);

		final Iterator<FacultyCourseTO> iter = list.iterator();

		FacultyCourseTO facultyCourse = iter.next();
		assertTrue("Title should have been longer than 0 characters.",
				facultyCourse.getTitle().length() > 0);

		facultyCourse = iter.next();
		assertNotNull("TermCode() should not have been null.",
				facultyCourse.getTermCode());

		assertEquals("Limit didn't limit results correctly.", 1,
				filtered.size());
		assertNotEquals(
				"GetAll and filtered lists should have been different sizes.",
				list.size(), filtered.size());
	}

	@Test
	public void testGetAllCoursesForFaculty() throws ObjectNotFoundException,
			ValidationException {
		// arrange, act
		final PagedResponse<FacultyCourseTO> list = controller
				.getAllCoursesForFaculty(FACULTY_SCHOOL_ID);

		// assert
		assertEquals("Course list should have returned 2 courses.", 2,
				list.getResults());
		assertEquals("Course name did not match.", FORMATTED_COURSE, list
				.getRows().iterator().next().getFormattedCourse());
	}

	@Test
	public void testGetRoster() throws ObjectNotFoundException,
			ValidationException {
		// arrange, act
		final PagedResponse<ExternalFacultyCourseRosterTO> list = controller
				.getRoster(FACULTY_SCHOOL_ID, FORMATTED_COURSE, null);

		// assert
		assertEquals("List should have returned 2 students.", 2,
				list.getResults());
		assertEquals("Last name did not match.", LAST_NAME, list
				.getRows().iterator().next().getLastName());
	}

	@Test
	public void testGetRostersForFormattedCourseTaughtBySameInstructorInMultipleTerms()
			throws ObjectNotFoundException, ValidationException {

		// assert on roster preconditions b/c the TOs that come back from the
		// controller method don't have fields describing the course/section
		// for each enrollment. so we couldn't otherwise know that our new
		// enrollment wasn't somehow already in the getRoster() result set.
		final PagedResponse<ExternalFacultyCourseRosterTO> initialEnrollments =
				controller.getRoster(Stubs.PersonFixture.KEN.schoolId(),
						"MTH101", null);

		assertEquals(1, initialEnrollments.getRows().size());
		assertEquals(Stubs.PersonFixture.STUDENT_0.schoolId(),
				initialEnrollments.getRows().iterator().next().getSchoolId());

		final WriteableExternalFacultyCourse course = new WriteableExternalFacultyCourse();
		course.setFacultySchoolId(Stubs.PersonFixture.KEN.schoolId());
		course.setTermCode(Stubs.TermFixture.FALL_2012.code());
		// prob. not worth the trouble to model a "stub" type for courses since
		// we don't actually *have* a 1st class course entity in our ext. model
		// we just happen to know that KEN already instructs MTH101 in term FA12
		// and PYF101 in term SP13
		course.setFormattedCourse("MTH101");
		course.setTermCode(Stubs.TermFixture.SPRING_2013.code());
		course.setTitle("College Algebra");

		final WriteableExternalFacultyCourseRoster roster = new WriteableExternalFacultyCourseRoster();
		roster.setFacultySchoolId(Stubs.PersonFixture.KEN.schoolId());
		roster.setFirstName(Stubs.PersonFixture.KEVIN_SMITH.firstName());
		roster.setFormattedCourse("MTH101");
		roster.setLastName(Stubs.PersonFixture.KEVIN_SMITH.lastName());
		roster.setMiddleName(Stubs.PersonFixture.KEVIN_SMITH.middleName());
		roster.setPrimaryEmailAddress(Stubs.PersonFixture.KEVIN_SMITH.primaryEmailAddress());
		roster.setSchoolId(Stubs.PersonFixture.KEVIN_SMITH.schoolId());
		roster.setTermCode(Stubs.TermFixture.SPRING_2013.code());

		sessionFactory.getCurrentSession().save(course);
		sessionFactory.getCurrentSession().save(roster);
		sessionFactory.getCurrentSession().flush();

		final PagedResponse<ExternalFacultyCourseRosterTO> modifiedEnrollments =
				controller.getRoster(Stubs.PersonFixture.KEN.schoolId(),
						"MTH101", null);

		assertEquals(2, modifiedEnrollments.getRows().size());
		final Iterator<ExternalFacultyCourseRosterTO> modifiedEnrollmentsIterator =
				modifiedEnrollments.getRows().iterator();
		assertEquals(Stubs.PersonFixture.STUDENT_0.schoolId(),
				modifiedEnrollmentsIterator.next().getSchoolId());
		assertEquals(Stubs.PersonFixture.KEVIN_SMITH.schoolId(),
				modifiedEnrollmentsIterator.next().getSchoolId());

	}

	@Test
	public void testGetTermCodeFilteredRostersForFormattedCourseTaughtBySameInstructorInMultipleTerms()
			throws ObjectNotFoundException, ValidationException {
		// same fixture as testGetRostersForFormattedCourseTaughtBySameInstructorInMultipleTerms(),
		// this time we add a filter to the results

		// assert on roster preconditions b/c the TOs that come back from the
		// controller method don't have fields describing the course/section
		// for each enrollment. so we couldn't otherwise know that our new
		// enrollment wasn't somehow already in the getRoster() result set.
		final PagedResponse<ExternalFacultyCourseRosterTO> initialEnrollments =
				controller.getRoster(Stubs.PersonFixture.KEN.schoolId(),
						"MTH101", null);

		assertEquals(1, initialEnrollments.getRows().size());
		assertEquals(Stubs.PersonFixture.STUDENT_0.schoolId(),
				initialEnrollments.getRows().iterator().next().getSchoolId());

		WriteableExternalFacultyCourse course = new WriteableExternalFacultyCourse();
		course.setFacultySchoolId(Stubs.PersonFixture.KEN.schoolId());
		course.setTermCode(Stubs.TermFixture.FALL_2012.code());
		// prob. not worth the trouble to model a "stub" type for courses since
		// we don't actually *have* a 1st class course entity in our ext. model
		// we just happen to know that KEN already instructs MTH101 in term FA12
		// and PYF101 in term SP13
		course.setFormattedCourse("MTH101");
		course.setTermCode("SP13");
		course.setTitle("College Algebra");

		WriteableExternalFacultyCourseRoster roster = new WriteableExternalFacultyCourseRoster();
		roster.setFacultySchoolId(Stubs.PersonFixture.KEN.schoolId());
		roster.setFirstName(Stubs.PersonFixture.KEVIN_SMITH.firstName());
		roster.setFormattedCourse("MTH101");
		roster.setLastName(Stubs.PersonFixture.KEVIN_SMITH.lastName());
		roster.setMiddleName(Stubs.PersonFixture.KEVIN_SMITH.middleName());
		roster.setPrimaryEmailAddress(Stubs.PersonFixture.KEVIN_SMITH.primaryEmailAddress());
		roster.setSchoolId(Stubs.PersonFixture.KEVIN_SMITH.schoolId());
		roster.setTermCode("SP13");

		sessionFactory.getCurrentSession().save(course);
		sessionFactory.getCurrentSession().save(roster);
		sessionFactory.getCurrentSession().flush();

		final PagedResponse<ExternalFacultyCourseRosterTO> modifiedEnrollments =
				controller.getRoster(Stubs.PersonFixture.KEN.schoolId(),
						"MTH101", Stubs.TermFixture.SPRING_2013.code());

		assertEquals(1, modifiedEnrollments.getRows().size());
		assertEquals(Stubs.PersonFixture.KEVIN_SMITH.schoolId(),
				modifiedEnrollments.getRows().iterator().next().getSchoolId());
	}

	/**
	 * Test that getLogger() returns the matching log class name for the current
	 * class under test.
	 */
	@Test
	public void testLogger() {
		// arrange, act
		final Logger logger = controller.getLogger();

		// assert
		assertEquals("Log class name did not match.", controller.getClass()
				.getName(), logger.getName());
	}
}