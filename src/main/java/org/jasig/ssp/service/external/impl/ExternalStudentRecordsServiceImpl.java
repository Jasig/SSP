package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.model.external.ExternalStudentRecords;
import org.jasig.ssp.model.external.ExternalStudentRecordsLite;
import org.jasig.ssp.service.external.ExternalStudentRecordsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalStudentRecordsServiceImpl implements
		ExternalStudentRecordsService {

	
	
	@Override
	public ExternalStudentRecords getExternalStudentRecordsByStudentId(
			String studentId) {
		
		return null;
	}

	@Override
	public ExternalStudentRecordsLite getExternalStudentRecordsLiteByStudentId(
			String studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
