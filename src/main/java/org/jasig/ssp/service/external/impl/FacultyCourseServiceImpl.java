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
package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalFacultyCourseRosterDao;
import org.jasig.ssp.dao.external.FacultyCourseDao;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.FacultyCourseService;
import org.jasig.ssp.transferobject.external.SearchFacultyCourseTO;
import org.jasig.ssp.transferobject.external.SearchStudentCourseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FacultyCourse service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class FacultyCourseServiceImpl extends
		AbstractExternalDataService<FacultyCourse> implements
		FacultyCourseService {

	@Autowired
	transient private FacultyCourseDao dao;

	@Autowired
	transient private ExternalFacultyCourseRosterDao externalFacultyCourseRosterDao;

	@Override
	protected FacultyCourseDao getDao() {
		return dao;
	}

	protected void setDao(final FacultyCourseDao dao) {
		this.dao = dao;
	}

	@Override
	public List<FacultyCourse> getAllCoursesForFaculty(
			final String facultySchoolId)
			throws ObjectNotFoundException {
		return dao.getAllCoursesForFaculty(facultySchoolId);
	}

	@Override
	public List<ExternalFacultyCourseRoster> getRosterByFacultySchoolIdAndCourse(
			final String facultySchoolId, final String formattedCourse)
			throws ObjectNotFoundException {
		return externalFacultyCourseRosterDao
				.getRosterByFacultySchoolIdAndCourse(facultySchoolId,
						formattedCourse);
	}

	@Override
	public List<ExternalFacultyCourseRoster> getRosterByFacultySchoolIdAndCourseAndTermCode(
			String facultySchoolId, String formattedCourse, String termCode)
			throws ObjectNotFoundException {
		return externalFacultyCourseRosterDao.getRosterByFacultySchoolIdAndCourseAndTermCode(
				facultySchoolId, formattedCourse, termCode);
	}
	
	@Override
	public ExternalFacultyCourseRoster getEnrollment(SearchStudentCourseTO searchStudentCourse)
			throws ObjectNotFoundException {
		return externalFacultyCourseRosterDao.getEnrollment(searchStudentCourse);
	}

	@Override
	public ExternalFacultyCourseRoster getEnrollment(String facultySchoolId,
			String formattedCourse, String termCode, String studentSchoolId)
			throws ObjectNotFoundException {
		return externalFacultyCourseRosterDao.getEnrollment(facultySchoolId,
				formattedCourse, termCode, studentSchoolId);
	}

	@Override
	public FacultyCourse getCourseByFacultySchoolIdAndFormattedCourse(
			String facultySchoolId, String formattedCourse) 
			throws ObjectNotFoundException {
		return dao.getCourseByFacultySchoolIdAndFormattedCourse(facultySchoolId, 
				formattedCourse);
	}

	@Override
	public FacultyCourse getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(
			String facultySchoolId, String formattedCourse, String termCode)
			throws ObjectNotFoundException {
		return dao.getCourseByFacultySchoolIdAndFormattedCourseAndTermCode(
				facultySchoolId, formattedCourse, termCode);
	}
	
	@Override
	public FacultyCourse getCourseBySearchFacultyCourseTO(SearchFacultyCourseTO searchFacultyCourse)
			throws ObjectNotFoundException {
		return dao.getCourseBySearchFacultyCourseTO(searchFacultyCourse);
	}

}