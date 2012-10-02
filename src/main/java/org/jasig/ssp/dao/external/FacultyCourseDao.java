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
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the FacultyCourse reference entity.
 */
@Repository
public class FacultyCourseDao extends AbstractExternalDataDao<FacultyCourse> {

	public FacultyCourseDao() {
		super(FacultyCourse.class);
	}

	/**
	 * Gets all courses for the specified faculty.
	 * 
	 * @param facultySchoolId
	 *            The faculty school id to use to lookup the associated data.
	 * @return The specified courses if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@SuppressWarnings("unchecked")
	public List<FacultyCourse> getAllCoursesForFaculty(
			final String facultySchoolId)
			throws ObjectNotFoundException {
		if (!StringUtils.isNotBlank(facultySchoolId)) {
			throw new ObjectNotFoundException(facultySchoolId,
					FacultyCourse.class.getName());
		}

		return createCriteria().add(
				Restrictions.eq("facultySchoolId", facultySchoolId)).list();
	}
	
	public FacultyCourse getCourseByFacultySchoolIdAndFormattedCourse(
			final String facultySchoolId, final String formattedCourse)
			throws ObjectNotFoundException {
		if (!StringUtils.isNotBlank(formattedCourse)) {
			throw new ObjectNotFoundException(formattedCourse,
					FacultyCourse.class.getName());
		}
		
		return (FacultyCourse) createCriteria().add(
				Restrictions.eq("facultySchoolId", facultySchoolId)).add(
				Restrictions.eq("formattedCourse", formattedCourse))
				.uniqueResult();
	}
}