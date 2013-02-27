package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.model.external.ExternalStudentsByCourse;
import org.jasig.ssp.service.external.ExternalStudentsByCourseService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public class ExternalStudentsByCourseServiceImpl extends
		AbstractExternalDataService<ExternalStudentsByCourse> implements
		ExternalStudentsByCourseService {

	@Override
	protected ExternalDataDao<ExternalStudentsByCourse> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
