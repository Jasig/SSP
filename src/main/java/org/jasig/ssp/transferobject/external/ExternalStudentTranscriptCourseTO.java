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
package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;

public class ExternalStudentTranscriptCourseTO implements ExternalDataTO<ExternalStudentTranscriptCourse>,
		Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6238162986848364288L;
	@Override
	public void from(ExternalStudentTranscriptCourse model) {
		schoolId = model.getSchoolId();
		subjectAbbreviation = model.getSubjectAbbreviation();
		number = model.getNumber();
		formattedCourse = model.getFormattedCourse();
		sectionNumber = model.getSectionNumber();
		title = model.getTitle();
		description = model.getDescription();
		grade = model.getGrade();
		creditEarned = model.getCreditEarned();
		termCode = model.getTermCode();
		creditType = model.getCreditType();
		
		sectionCode = model.getSectionCode();
		audited = model.getAudited();
		statusCode = model.getStatusCode();
		
		firstName = model.getFirstName();
		lastName = model.getLastName();
		middleName = model.getMiddleName();
		facultySchoolId = model.getFacultySchoolId();		
	}
	
	public ExternalStudentTranscriptCourseTO(ExternalStudentTranscriptCourse model)
	{
		super();
		from(model);
	}
	
	public ExternalStudentTranscriptCourseTO()
	{
		super();
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

	private String sectionCode;
	private String audited;
	private String statusCode;
	
	private String firstName;
	private String lastName;
	private String middleName;
	
	private String facultySchoolId;
	private String facultyName;
	
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
			final String firstName,
			final String middleName,
			final String lastName,
			final String subjectAbbreviation, 
			final String number, 
			final String formattedCourse,
			final String sectionNumber, 
			final String title, 
			final String description,
			final String grade, 
			final BigDecimal creditEarned, 
			final String termCode,
			final String creditType, 
			final String sectionCode,
			final String facultySchoolId,
	final String audited,
	final String statusCode) {
		
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
		this.sectionCode = sectionCode;
		this.audited = audited;
		this.statusCode = statusCode;
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.facultySchoolId = facultySchoolId;
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

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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

	/**
	 * @return the facultyName
	 */
	public String getFacultyName() {
		return facultyName;
	}

	/**
	 * @param facultyName the facultyName to set
	 */
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
}
