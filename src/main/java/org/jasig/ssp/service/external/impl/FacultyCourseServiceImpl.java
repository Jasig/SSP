package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalFacultyCourseRosterDao;
import org.jasig.ssp.dao.external.FacultyCourseDao;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.FacultyCourseService;
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
	public FacultyCourse getCourseByFacultySchoolIdAndFormattedCourse(
			String facultySchoolId, String formattedCourse) 
			throws ObjectNotFoundException {
		return dao.getCourseByFacultySchoolIdAndFormattedCourse(facultySchoolId, 
				formattedCourse);
	}
}