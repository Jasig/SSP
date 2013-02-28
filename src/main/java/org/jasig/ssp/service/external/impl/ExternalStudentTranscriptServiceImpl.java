package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.dao.external.ExternalStudentTranscriptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ExternalStudentTranscriptServiceImpl extends
		AbstractExternalDataService< ExternalStudentTranscript> implements ExternalStudentTranscriptService {


	@Autowired
	private transient ExternalStudentTranscriptDao dao;
	
	public ExternalStudentTranscript getRecordsBySchoolId(String schoolId){
		return dao.getRecordsBySchoolId(schoolId);
	}
	
	@Override
	protected ExternalDataDao<ExternalStudentTranscript> getDao() {
		return dao;
	}

}
