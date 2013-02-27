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
package org.jasig.ssp.dao.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.hibernate.NonUniqueResultException;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.model.external.WriteableExternalFacultyCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.service.stub.Stubs;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * FacultyCourse DAO
 * 
 * @author jon.adams
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class FacultyCourseDaoTest {

	@Autowired
	private transient FacultyCourseDao dao;

	@Autowired
	private transient SessionFactory sessionFactory;

	@Test
	public void getAll() throws ObjectNotFoundException {
		final Collection<FacultyCourse> all = dao.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("GetAll should have returned some data.", all.isEmpty());
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testGetWithNull() throws ObjectNotFoundException {
		final List<FacultyCourse> facultyCourse = dao
				.getAllCoursesForFaculty(null);
		assertNull(
				"Invalid identifier passed to get() should have returned null.",
				facultyCourse);
	}

	@Test
	public void testGetByFacultySchoolIdAndFormattedCourse() throws ObjectNotFoundException {
		final FacultyCourse course =
				dao.getCourseByFacultySchoolIdAndFormattedCourse(
						Stubs.PersonFixture.KEN.schoolId(), "MTH101");
		assertEquals(course.getFacultySchoolId(), Stubs.PersonFixture.KEN.schoolId());
		assertEquals(course.getFormattedCourse(), "MTH101");
		assertEquals(course.getTermCode(), Stubs.TermFixture.FALL_2012.code());
		assertEquals(course.getTitle(), "College Algebra");
	}

	@Test(expected = NonUniqueResultException.class)
	public void testGetByFacultySchoolIdAndFormattedCourseWithMultipleMatches()
			throws ObjectNotFoundException {
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
		course.setSectionCode("CA100FA");
		course.setSectionNumber("CA100FA");

		sessionFactory.getCurrentSession().save(course);
		sessionFactory.getCurrentSession().flush();

		final FacultyCourse foundCourse =
				dao.getCourseByFacultySchoolIdAndFormattedCourse(
						Stubs.PersonFixture.KEN.schoolId(), "MTH101");
	}

	@Test
	public void testGetByFacultySchoolIdAndFormattedCourseAndTermCode() throws ObjectNotFoundException {
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
		course.setSectionCode("CA100SP");
		course.setSectionNumber("CA100SP2");
		sessionFactory.getCurrentSession().save(course);
		sessionFactory.getCurrentSession().flush();

		final FacultyCourse foundCourse1 =
				dao.getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(
						Stubs.PersonFixture.KEN.schoolId(), "MTH101",
						Stubs.TermFixture.FALL_2012.code());

		assertEquals(foundCourse1.getFacultySchoolId(), Stubs.PersonFixture.KEN.schoolId());
		assertEquals(foundCourse1.getFormattedCourse(), "MTH101");
		assertEquals(foundCourse1.getTermCode(), Stubs.TermFixture.FALL_2012.code());
		assertEquals(foundCourse1.getTitle(), "College Algebra");

		final FacultyCourse foundCourse2 =
				dao.getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(
						Stubs.PersonFixture.KEN.schoolId(), "MTH101",
						Stubs.TermFixture.SPRING_2013.code());

		assertEquals(foundCourse2.getFacultySchoolId(), Stubs.PersonFixture.KEN.schoolId());
		assertEquals(foundCourse2.getFormattedCourse(), "MTH101");
		assertEquals(foundCourse2.getTermCode(), Stubs.TermFixture.SPRING_2013.code());
		assertEquals(foundCourse2.getTitle(), "College Algebra");
	}
}