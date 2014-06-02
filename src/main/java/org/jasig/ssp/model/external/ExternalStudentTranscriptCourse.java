/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
	
	@Column(nullable = false, length = 10)
	@Size(max = 10)
	@NotNull
	@NotEmpty
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
	
	@Column(nullable = true, length = 128)
	@Size(max = 128)
	private String sectionCode;

	@Column(nullable = true, length = 1)
	@Size(max = 1)
	private String audited;
	
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String statusCode;
	
	
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String firstName;
	
	
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String middleName;
	
	
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String lastName;
	
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String facultySchoolId;
	
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String courseCode;

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

	/**
	 * @return the sectionCode
	 */
	public String getSectionCode() {
		return sectionCode;
	}

	/**
	 * @param sectionCode the sectionCode to set
	 */
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}


	/**
	 * @return the audited
	 */
	public String getAudited() {
		return audited;
	}

	/**
	 * @param audited the audited to set
	 */
	public void setAudited(String audited) {
		this.audited = audited;
	}

	/**
	 * @return the status_code
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param status_code the status_code to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the facultySchoolId
	 */
	public String getFacultySchoolId() {
		return facultySchoolId;
	}

	/**
	 * @param facultySchoolId the facultySchoolId to set
	 */
	public void setFacultySchoolId(String facultySchoolId) {
		this.facultySchoolId = facultySchoolId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
}
