package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentTestDao;
import org.jasig.ssp.model.external.ExternalStudentTest;
import org.jasig.ssp.service.external.ExternalStudentTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalStudentTestServiceImpl extends
		AbstractExternalDataService<ExternalStudentTest> implements ExternalStudentTestService {

	@Autowired
	private transient ExternalStudentTestDao dao;

	@Override
	protected ExternalDataDao<ExternalStudentTest> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public List<ExternalStudentTest> getStudentTestResults(String schoolId) {
		// TODO Auto-generated method stub
		return dao.getStudentTestResults(schoolId);
	}


}
