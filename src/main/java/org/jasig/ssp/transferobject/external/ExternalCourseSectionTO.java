package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.math.BigDecimal;

import org.jasig.ssp.model.external.ExternalCourseSection;

public class ExternalCourseSectionTO implements Serializable, ExternalDataTO<ExternalCourseSection> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8465736452376227906L;
	private String formattedCourse;
	private String subjectAbbreviation;
	private String number;
	private String sectionNumber;
	private BigDecimal creditValue;
	private String termCode;
	private String description;

	@Override
	public void from(ExternalCourseSection model) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param formattedCourse
	 * @param subjectAbbreviation
	 * @param number
	 * @param sectionNumber
	 * @param creditValue
	 * @param termCode
	 * @param description
	 */
	public ExternalCourseSectionTO(final String formattedCourse,
			String subjectAbbreviation, String number, String sectionNumber,
			BigDecimal creditValue, String termCode, String description) {
		super();
		this.formattedCourse = formattedCourse;
		this.subjectAbbreviation = subjectAbbreviation;
		this.number = number;
		this.sectionNumber = sectionNumber;
		this.creditValue = creditValue;
		this.termCode = termCode;
		this.description = description;
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
	 * @return the creditValue
	 */
	public BigDecimal getCreditValue() {
		return creditValue;
	}

	/**
	 * @param creditValue the creditValue to set
	 */
	public void setCreditValue(final BigDecimal creditValue) {
		this.creditValue = creditValue;
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

}
