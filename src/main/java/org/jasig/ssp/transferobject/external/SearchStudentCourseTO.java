package org.jasig.ssp.transferobject.external;

import org.apache.commons.lang.StringUtils;

public class SearchStudentCourseTO extends SearchFacultyCourseTO {
	
	private final String studentSchoolId;

	public SearchStudentCourseTO(String facultyId, String termCode,
			String sectionCode, String formattedCourse, String studentSchoolId) {
		super(facultyId, termCode, sectionCode, formattedCourse);
		this.studentSchoolId = studentSchoolId;
	}

	public String getStudentSchoolId() {
		return studentSchoolId;
	}
	
	public Boolean hasStudentSchoolId() {
		return StringUtils.isNotBlank(studentSchoolId);
	}

}
