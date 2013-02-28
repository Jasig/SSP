package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentTranscriptCourseDao;
import org.jasig.ssp.dao.external.ExternalStudentTranscriptDao;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalStudentTranscriptCourseServiceImpl extends
		AbstractExternalDataService<ExternalStudentTranscriptCourse> implements
		ExternalStudentTranscriptCourseService {

	@Autowired
	private transient ExternalStudentTranscriptCourseDao dao;
	
	@Override
	protected ExternalDataDao<ExternalStudentTranscriptCourse> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExternalStudentTranscriptCourse> getTranscriptsBySchoolId(
			String schoolId) {
		return dao.getTranscriptsBySchoolId(schoolId);
	}

}
