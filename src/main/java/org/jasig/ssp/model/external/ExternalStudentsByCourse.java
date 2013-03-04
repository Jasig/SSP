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
/**
 * 
 */
package org.jasig.ssp.model.external;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author jamesstanley
 *
 */
@Entity
@Immutable
@Table(name = "v_external_students_by_course")
public class ExternalStudentsByCourse extends AbstractExternalData implements
		Serializable, ExternalData {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5948712680153764526L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;
	
	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 35)
	private String formattedCourse;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String firstName;
	
	@Column(nullable = true, length = 50)
	@NotEmpty
	@Size(max = 50)
	private String middleName;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String lastName;
	
	@Column(nullable = true, length = 1)
	@Size(max = 1)
	private String audited;
	
	@Column(nullable = true, length = 10)
	@Size(max = 10)
	private String academicGrade;
	
	@Column(nullable = true, length = 25)
	@Size(max = 25)
	private String termCode;
	
	/**
	 * 
	 */
	public ExternalStudentsByCourse() {
		// TODO Auto-generated constructor stub
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(final String firstName) {
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
	public void setMiddleName(final String middleName) {
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
	public void setLastName(final String lastName) {
		this.lastName = lastName;
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
	public void setAudited(final String audited) {
		this.audited = audited;
	}

	/**
	 * @return the academicGrade
	 */
	public String getAcademicGrade() {
		return academicGrade;
	}

	/**
	 * @param academicGrade the academicGrade to set
	 */
	public void setAcademicGrade(final String academicGrade) {
		this.academicGrade = academicGrade;
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

}
