package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;

public interface ExternalStudentAcademicProgramService extends
		ExternalDataService<ExternalStudentAcademicProgram> {

	List<ExternalStudentAcademicProgram> getAcademicProgramsBySchoolId(String schoolId);
}
