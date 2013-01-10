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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Copy/paste of {@link ExternalFacultyCourseRoster} but with JPA/Hibernate
 * configured to allow writes to the default underlying table.
 * ({@link ExternalFacultyCourseRoster} is mapped to a view and its ID field
 * doesn't actually exist in the underlying default physical table).
 */
@Entity
@Table(name = "external_faculty_course_roster")
@IdClass(ExternalFacultyCoursePk.class)
public class WriteableExternalFacultyCourseRoster  {

	@Id
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String facultySchoolId;

	@Id
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;

	@Id
	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String termCode;

	@Id
	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 35)
	private String formattedCourse;

	@Column(nullable = true, length = 2)
	@Size(max = 2)
	private String statusCode;

	/**
	 * First name; required.
	 *
	 * Maximum length of 50.
	 */
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String firstName;

	/**
	 * Middle name; optional.
	 *
	 * Optional; maximum length of 50.
	 */
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String middleName;

	/**
	 * Last name; required.
	 *
	 * Maximum length of 50.
	 */
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String lastName;

	@Column(nullable = false, length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String primaryEmailAddress;

	public String getFacultySchoolId() {
		return facultySchoolId;
	}

	/**
	 * @param facultySchoolId
	 *            the facultySchoolId
	 */
	public void setFacultySchoolId(final String facultySchoolId) {
		this.facultySchoolId = facultySchoolId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId
	 *            the schoolId
	 */
	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	/**
	 * @param termCode
	 *            the termCode
	 */
	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @param formattedCourse
	 *            the formattedCourse
	 */
	public void setFormattedCourse(final String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(@NotNull final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(@NotNull final String lastName) {
		this.lastName = lastName;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(@NotNull final String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}
}