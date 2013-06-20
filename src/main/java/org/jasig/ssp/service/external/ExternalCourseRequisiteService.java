package org.jasig.ssp.service.external;

import java.util.List;

import org.jasig.ssp.model.external.ExternalCourseRequisite;

public interface ExternalCourseRequisiteService extends ExternalDataService<ExternalCourseRequisite> {
	
	public List<ExternalCourseRequisite> getRequisitesForCourse(String requiringCourseCode);
	
	public List<ExternalCourseRequisite> getRequisitesForCourses(List<String> requiringCourseCode);

}
