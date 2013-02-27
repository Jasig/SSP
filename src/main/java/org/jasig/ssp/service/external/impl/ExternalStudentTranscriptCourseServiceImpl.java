package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public class ExternalStudentTranscriptCourseServiceImpl extends
		AbstractExternalDataService<ExternalStudentTranscriptCourse> implements
		ExternalStudentTranscriptCourseService {

	@Override
	protected ExternalDataDao<ExternalStudentTranscriptCourse> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
