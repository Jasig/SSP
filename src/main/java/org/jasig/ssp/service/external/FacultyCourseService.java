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
	 * Gets the course roster for the specified faculty's course.
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
	 * Gets the course with the specified id for the specified faculty.
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
}