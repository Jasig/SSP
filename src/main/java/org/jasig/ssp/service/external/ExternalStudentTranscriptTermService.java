package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.ExternalStudentTranscriptTerm;

public interface ExternalStudentTranscriptTermService extends
		ExternalDataService<ExternalStudentTranscriptTerm> {

	List<ExternalStudentTranscriptTerm> getExternalStudentTranscriptTermsBySchoolId(
			String schoolId);
	
	ExternalStudentTranscriptTerm getExternalStudentTranscriptTermBySchoolIdTermCode(String schoolId, 
			String termCode);
}
