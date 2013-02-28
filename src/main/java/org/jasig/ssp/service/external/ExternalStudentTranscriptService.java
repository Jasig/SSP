package org.jasig.ssp.service.external;

import org.jasig.ssp.model.external.ExternalStudentTranscript;

public interface ExternalStudentTranscriptService extends
		ExternalDataService<ExternalStudentTranscript> {
	
	ExternalStudentTranscript getRecordsBySchoolId(String schoolId);

}
