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
 * Copy/paste of {@link FacultyCourse} but with JPA/Hibernate configured to
 * allow writes to the default underlying table. ({@link FacultyCourse} is
 * mapped to a view and its ID field doesn't actually exist in the underlying
 * default physical table).
 */
@Entity
@Table(name = "external_faculty_course")
@IdClass(ExternalFacultyCoursePk.class)
public class WriteableExternalFacultyCourse {

	@Id
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String facultySchoolId;

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

	@Column(nullable = false, length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String title;

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

	/**
	 * @param formattedCourse
	 *            the formattedCourse
	 */
	public void setFormattedCourse(final String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title
	 */
	public void setTitle(final String title) {
		this.title = title;
	}
}
