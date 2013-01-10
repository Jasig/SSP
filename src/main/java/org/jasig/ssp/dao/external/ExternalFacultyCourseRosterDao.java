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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the FacultyCourse reference entity.
 */
@Repository
public class ExternalFacultyCourseRosterDao extends
		AbstractExternalDataDao<ExternalFacultyCourseRoster> {

	public ExternalFacultyCourseRosterDao() {
		super(ExternalFacultyCourseRoster.class);
	}

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
	@SuppressWarnings("unchecked")
	public List<ExternalFacultyCourseRoster> getRosterByFacultySchoolIdAndCourse(
			final String facultySchoolId, final String formattedCourse)
			throws ObjectNotFoundException {

		// Some inconsistency throughout services and dao w/r/t null/blank
		// param handling, both w/r/t whether it's an error or a wildcard and,
		// if an error, how that error is represented. In this case we're trying
		// to be consistent w/ what we see in FacultyCourseDao, which in most
		// cases requires all params be non-blank and throws
		// ObjectNotFoundException if that's not the case. Note that in no
		// case does that implementation use ObjectNotFoundException to represent
		// an actual empty result set.
		//
		// Not allowing wildcards makes some sense here since the result set
		// is completely unbounded and we know that this operation is not used
		// for reporting purposes.

		// TODO consider if IllegalArgumentException is a better fit for bad params
		// and if so fix it across the board.

		if ( StringUtils.isBlank(facultySchoolId) ) {
			throw new ObjectNotFoundException("Must specify a faculty school ID",
					ExternalFacultyCourseRoster.class.getName());
		}
		if ( StringUtils.isBlank(formattedCourse) ) {
			throw new ObjectNotFoundException("Must specify a formatted course ID",
					ExternalFacultyCourseRoster.class.getName());
		}
		return createCriteria()
				.add(Restrictions.eq("facultySchoolId", facultySchoolId))
				.add(Restrictions.eq("formattedCourse", formattedCourse))
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<ExternalFacultyCourseRoster> getRosterByFacultySchoolIdAndCourseAndTermCode(
			final String facultySchoolId, final String formattedCourse,
			final String termCode) throws ObjectNotFoundException {

		// See comments on getRosterByFacultySchoolIdAndCourse() re params
		// processing

		if ( StringUtils.isBlank(facultySchoolId) ) {
			throw new ObjectNotFoundException("Must specify a faculty school ID",
					ExternalFacultyCourseRoster.class.getName());
		}
		if ( StringUtils.isBlank(formattedCourse) ) {
			throw new ObjectNotFoundException("Must specify a formatted course ID",
					ExternalFacultyCourseRoster.class.getName());
		}
		if ( StringUtils.isBlank(termCode) ) {
			throw new ObjectNotFoundException("Must specify a term code",
					ExternalFacultyCourseRoster.class.getName());
		}
		return createCriteria()
				.add(Restrictions.eq("facultySchoolId", facultySchoolId))
				.add(Restrictions.eq("formattedCourse", formattedCourse))
				.add(Restrictions.eq("termCode", termCode))
				.list();

	}

	public ExternalFacultyCourseRoster getEnrollment(String facultySchoolId,
			String formattedCourse, String termCode, String studentSchoolId)
			throws ObjectNotFoundException {
		if ( StringUtils.isBlank(facultySchoolId) ) {
			throw new ObjectNotFoundException("Must specify a faculty school ID",
					ExternalFacultyCourseRoster.class.getName());
		}
		if ( StringUtils.isBlank(formattedCourse) ) {
			throw new ObjectNotFoundException("Must specify a formatted course ID",
					ExternalFacultyCourseRoster.class.getName());
		}
		if ( StringUtils.isBlank(termCode) ) {
			throw new ObjectNotFoundException("Must specify a term code",
					ExternalFacultyCourseRoster.class.getName());
		}
		if ( StringUtils.isBlank(termCode) ) {
			throw new ObjectNotFoundException("Must specify a student school ID",
					ExternalFacultyCourseRoster.class.getName());
		}
		return (ExternalFacultyCourseRoster) createCriteria()
				.add(Restrictions.eq("facultySchoolId", facultySchoolId))
				.add(Restrictions.eq("formattedCourse", formattedCourse))
				.add(Restrictions.eq("termCode", termCode))
				.add(Restrictions.eq("schoolId", studentSchoolId))
				.uniqueResult();
	}
}