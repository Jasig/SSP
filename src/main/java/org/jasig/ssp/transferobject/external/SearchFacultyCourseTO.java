package org.jasig.ssp.transferobject.external;

import org.apache.commons.lang.StringUtils;


public class SearchFacultyCourseTO {
	private final String facultySchoolId;
	private final String termCode;
	private final String sectionCode;
	private final String formattedCourse;
	
	public SearchFacultyCourseTO(String facultyId, String termCode,
			String sectionCode, String formattedCourse) {
		super();
		this.facultySchoolId = facultyId;
		this.termCode = termCode;
		this.sectionCode = sectionCode;
		this.formattedCourse = formattedCourse;
	}

	public String getFacultySchoolId() {
		return facultySchoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}
	
	public Boolean hasFacultySchoolId(){
		return StringUtils.isNotBlank(facultySchoolId);
	}
	
	public Boolean hasTermCode(){
		return StringUtils.isNotBlank(termCode);
	}

	
	public Boolean hasSectionCode(){
		return StringUtils.isNotBlank(sectionCode);
	}

	
	public Boolean hasFormattedCourse(){
		return StringUtils.isNotBlank(formattedCourse);
	}
}
