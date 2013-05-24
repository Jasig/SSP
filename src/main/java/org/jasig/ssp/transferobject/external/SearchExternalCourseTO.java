package org.jasig.ssp.transferobject.external;

import org.apache.commons.lang.StringUtils;

public class SearchExternalCourseTO {
	
	private String programCode;
	private String termCode;
	private String subjectAbbreviation;
	private String courseNumber;
	private String tag;
	/**
	 * @param programCode
	 * @param termCode
	 * @param subjectAbbreviation
	 * @param courseNumber
	 * @param tag
	 */
	public SearchExternalCourseTO(String programCode, String termCode,
			String subjectAbbreviation, String courseNumber, String tag) {
		super();
		this.programCode = programCode;
		this.termCode = termCode;
		this.subjectAbbreviation = subjectAbbreviation;
		this.courseNumber = courseNumber;
		this.tag = tag;
	}
	/**
	 * @return the programCode
	 */
	public String getProgramCode() {
		return programCode;
	}
	/**
	 * @param programCode the programCode to set
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	/**
	 * @return the termCode
	 */
	public String getTermCode() {
		return termCode;
	}
	/**
	 * @param termCode the termCode to set
	 */
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	/**
	 * @return the subjectAbbreviation
	 */
	public String getSubjectAbbreviation() {
		return subjectAbbreviation;
	}
	/**
	 * @param subjectAbbreviation the subjectAbbreviation to set
	 */
	public void setSubjectAbbreviation(String subjectAbbreviation) {
		this.subjectAbbreviation = subjectAbbreviation;
	}
	/**
	 * @return the courseNumber
	 */
	public String getCourseNumber() {
		return courseNumber;
	}
	/**
	 * @param courseNumber the courseNumber to set
	 */
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public boolean isUnbounded() {
	return StringUtils.isEmpty(this.tag) && StringUtils.isEmpty(this.courseNumber)  
		&& StringUtils.isEmpty(this.programCode) && StringUtils.isEmpty(this.subjectAbbreviation)  
		&& StringUtils.isEmpty(this.termCode);	
	}
}
