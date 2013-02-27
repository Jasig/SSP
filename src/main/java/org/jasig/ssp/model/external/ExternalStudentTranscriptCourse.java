package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Immutable
@Table(name = "v_external_student_transcript_course")
public class ExternalStudentTranscriptCourse extends AbstractExternalData
		implements ExternalData, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5886830169590147558L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;
	
	@Column(nullable = false, length = 10)
	@NotNull
	@NotEmpty
	@Size(max = 10)
	private String subjectAbbreviation;
	
	@Column(nullable = false, length = 15)
	@NotNull
	@NotEmpty
	@Size(max = 15)
	private String number;
	
	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 35)
	private String formattedCourse;
	
	@Column(nullable = false, length = 10)
	@NotNull
	@NotEmpty
	@Size(max = 10)
	private String sectionNumber;
	
	@Column(nullable = true, length = 100)
	@Size(max = 100)
	private String title;
	
	@Column(nullable = true, length = 2500)
	@Size(max = 2500)
	private String description;
	
	@Column(nullable = true, length = 10)
	@Size(max = 10)
	private String grade;
	
	@Column(nullable = true)
	private BigDecimal creditEarned;
	
	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String termCode;
	
	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String creditType;

	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId
	 *            the schoolId to set
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
	 * @param subjectAbbreviation
	 *            the subjectAbbreviation to set
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
	 * @param number
	 *            the number to set
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
	 * @param formattedCourse
	 *            the formattedCourse to set
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
	 * @param sectionNumber
	 *            the sectionNumber to set
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
	 * @param title
	 *            the title to set
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
	 * @param description
	 *            the description to set
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
	 * @param grade
	 *            the grade to set
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
	 * @param creditEarned
	 *            the creditEarned to set
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
	 * @param termCode
	 *            the termCode to set
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
	 * @param creditType
	 *            the creditType to set
	 */
	public void setCreditType(final String creditType) {
		this.creditType = creditType;
	}
}
