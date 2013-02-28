package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.ExternalStudentTest;

public interface ExternalStudentTestService extends ExternalDataService<ExternalStudentTest> {
	
	List<ExternalStudentTest> getStudentTestResults(String schoolId);

}
