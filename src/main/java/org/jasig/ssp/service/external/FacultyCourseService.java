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
package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.service.ObjectNotFoundException;

/**
 * FacultyCourse service
 * 
 * @author jon.adams
 */
public interface FacultyCourseService extends
		ExternalDataService<FacultyCourse> {

	/**
	 * Gets all courses for the specified faculty.
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @return The specified courses if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	List<FacultyCourse> getAllCoursesForFaculty(final String facultySchoolId)
			throws ObjectNotFoundException;

	/**
	 * Gets the course roster for the specified faculty's course. Note that
	 * this might return enrollments spanning multiple courses/sections if the
	 * given faculty teaches the same courses/sections across multiple terms.
	 *
	 * <p>Not necessarily the same as
	 * {@link #getRosterByFacultySchoolIdAndCourseAndTermCode(String, String, String)}
	 * and passing a null term code.</p>
	 *
	 * @deprecated Potentially ambiguous result if the given faculty teaches
	 *   the same course/section in multiple terms. There really isn't a
	 *   reasonable use case for invoking this method. Use
	 *   {@link #getRosterByFacultySchoolIdAndCourseAndTermCode(String, String, String)}
	 *   instead.
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @param formattedCourse
	 *            the course
	 * @return The specified courses if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	List<ExternalFacultyCourseRoster> getRosterByFacultySchoolIdAndCourse(
			final String facultySchoolId, final String formattedCourse)
			throws ObjectNotFoundException;

	/**
	 * Gets the course with the specified id for the specified faculty in the
	 * given term.
	 *
	 * <p>Note that under the current implementation the
	 * {@link ObjectNotFoundException} in the signature is used to represent
	 * an invalid argument rather than an empty result set. Do not rely on
	 * it as a guard against null results.</p>
	 *
	 * @param facultySchoolId
	 *             The faculty school id to use to lookup the associated data.
	 * @param formattedCourse
	 *             the course
	 * @param termCode
	 *             the term code
	 * @return
	 * @throws ObjectNotFoundException see method description
	 */
	List<ExternalFacultyCourseRoster>
	getRosterByFacultySchoolIdAndCourseAndTermCode(String facultySchoolId,
			String formattedCourse,
			String termCode)
	throws ObjectNotFoundException;

	/**
	 * Gets the course with the specified id for the specified faculty.
	 *
	 * @deprecated Potentially ambiguous result if the given faculty teaches
	 *   the same course/section in multiple terms. The current implementation
	 *   will throw a <code>org.hibernate.NonUniqueResultException</code> in
	 *   this case. But as currently designed there really isn't a reasonable
	 *   use case for invoking this method. Use
	 *   {@link #getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(String, String, String)}
	 *   instead
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @param formattedCourse
	 *            the course
	 * @return The specified course if present in the system
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	FacultyCourse getCourseByFacultySchoolIdAndFormattedCourse(
			String facultySchoolId, final String formattedCourse) 
			throws ObjectNotFoundException;

	/**
	 * Same as {@link #getCourseByFacultySchoolIdAndFormattedCourse(String, String)}
	 * but with a term code parameter that eliminates the former's ambiguity.
	 *
	 * @param facultySchoolId
	 * @param formattedCourse
	 * @param termCode
	 * @return
	 * @throws ObjectNotFoundException
	 */
	FacultyCourse getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(
			String facultySchoolId, final String formattedCourse,
			final String termCode) throws ObjectNotFoundException;

}