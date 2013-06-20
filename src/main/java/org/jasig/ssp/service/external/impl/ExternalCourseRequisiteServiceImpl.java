package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalCourseRequisiteDao;
import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.model.external.ExternalCourseRequisite;
import org.jasig.ssp.service.external.ExternalCourseRequisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalCourseRequisiteServiceImpl extends
		AbstractExternalDataService<ExternalCourseRequisite> implements
		ExternalCourseRequisiteService {

	@Autowired
	private transient ExternalCourseRequisiteDao dao;

	@Override
	public List<ExternalCourseRequisite> getRequisitesForCourse(
			String requiringCourseCode) {
		return dao.getRequisitesForCourse(requiringCourseCode);
	}

	@Override
	public List<ExternalCourseRequisite> getRequisitesForCourses(
			List<String> requiringCourseCodes) {
		return dao.getRequisitesForCourses(requiringCourseCodes);
	}

	@Override
	protected ExternalDataDao<ExternalCourseRequisite> getDao() {
		return dao;
	}

}
