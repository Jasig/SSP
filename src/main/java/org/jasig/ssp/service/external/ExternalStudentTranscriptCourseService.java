package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;

public interface ExternalStudentTranscriptCourseService extends
		ExternalDataService<ExternalStudentTranscriptCourse> {
	
	List<ExternalStudentTranscriptCourse> getTranscriptsBySchoolId(String schoolId);

}
