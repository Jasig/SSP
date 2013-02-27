package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.math.BigDecimal;

import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;

public class ExternalStudentTranscriptCourseTO implements ExternalDataTO<ExternalStudentTranscriptCourse>,
		Serializable {


	@Override
	public void from(ExternalStudentTranscriptCourse model) {
		// TODO Auto-generated method stub
		
	}
	private String schoolId;
	private String subjectAbbreviation;
	private String number;
	private String formattedCourse;
	private String sectionNumber;
	private String title;
	private String description;
	private String grade;
	private BigDecimal creditEarned;
	private String termCode;
	private String creditType;
	/**
	 * @param schoolId
	 * @param subjectAbbreviation
	 * @param number
	 * @param formattedCourse
	 * @param sectionNumber
	 * @param title
	 * @param description
	 * @param grade
	 * @param creditEarned
	 * @param termCode
	 * @param creditType
	 */
	public ExternalStudentTranscriptCourseTO(final String schoolId,
			final String subjectAbbreviation, final String number, final String formattedCourse,
			final String sectionNumber, final String title, final String description,
			final String grade, final BigDecimal creditEarned, final String termCode,
			final String creditType) {
		
		super();
		this.schoolId = schoolId;
		this.subjectAbbreviation = subjectAbbreviation;
		this.number = number;
		this.formattedCourse = formattedCourse;
		this.sectionNumber = sectionNumber;
		this.title = title;
		this.description = description;
		this.grade = grade;
		this.creditEarned = creditEarned;
		this.termCode = termCode;
		this.creditType = creditType;
	}
	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}
	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
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
	public void setSubjectAbbreviation(final String subjectAbbreviation) {
		this.subjectAbbreviation = subjectAbbreviation;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(final String number) {
		this.number = number;
	}
	/**
	 * @return the formattedCourse
	 */
	public String getFormattedCourse() {
		return formattedCourse;
	}
	/**
	 * @param formattedCourse the formattedCourse to set
	 */
	public void setFormattedCourse(final String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}
	/**
	 * @return the sectionNumber
	 */
	public String getSectionNumber() {
		return sectionNumber;
	}
	/**
	 * @param sectionNumber the sectionNumber to set
	 */
	public void setSectionNumber(final String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(final String grade) {
		this.grade = grade;
	}
	/**
	 * @return the creditEarned
	 */
	public BigDecimal getCreditEarned() {
		return creditEarned;
	}
	/**
	 * @param creditEarned the creditEarned to set
	 */
	public void setCreditEarned(final BigDecimal creditEarned) {
		this.creditEarned = creditEarned;
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
	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}
	/**
	 * @return the creditType
	 */
	public String getCreditType() {
		return creditType;
	}
	/**
	 * @param creditType the creditType to set
	 */
	public void setCreditType(final String creditType) {
		this.creditType = creditType;
	}
}
