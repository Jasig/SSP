package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalStudentTranscriptTermDao;
import org.jasig.ssp.model.external.ExternalStudentTranscriptTerm;
import org.jasig.ssp.service.external.ExternalStudentTranscriptTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalStudentTranscriptTermServiceImpl extends
		AbstractExternalDataService<ExternalStudentTranscriptTerm> implements
		ExternalStudentTranscriptTermService {

	@Autowired
	transient private ExternalStudentTranscriptTermDao dao;


	@Override
	protected ExternalStudentTranscriptTermDao getDao() {
		return dao;
	}
	

	@Override
	public List<ExternalStudentTranscriptTerm> getExternalStudentTranscriptTermsBySchoolId(
			String schoolId) {
		return dao.getExternalStudentTranscriptTermsBySchoolId(schoolId);
	}
	
	public 	ExternalStudentTranscriptTerm getExternalStudentTranscriptTermBySchoolIdTermCode(String schoolId, 
			String termCode){
		return dao.getExternalStudentTranscriptTermBySchoolIdTermCode(schoolId, termCode);
	}


}
