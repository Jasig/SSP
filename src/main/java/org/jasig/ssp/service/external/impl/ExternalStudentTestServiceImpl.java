package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.model.external.ExternalStudentTest;
import org.jasig.ssp.service.external.ExternalStudentTestService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public class ExternalStudentTestServiceImpl extends
		AbstractExternalDataService<ExternalStudentTest> implements ExternalStudentTestService {

	@Override
	protected ExternalDataDao<ExternalStudentTest> getDao() {
		// TODO Auto-generated method stub
		return null;
	}


}
