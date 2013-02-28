package org.jasig.ssp.service.external;

import org.jasig.ssp.model.external.ExternalStudentRecords;
import org.jasig.ssp.model.external.ExternalStudentRecordsLite;

public interface ExternalStudentRecordsService {

	
	ExternalStudentRecords getExternalStudentRecordsByStudentId(String studentId);
	ExternalStudentRecordsLite getExternalStudentRecordsLiteByStudentId(String studentId);
}
