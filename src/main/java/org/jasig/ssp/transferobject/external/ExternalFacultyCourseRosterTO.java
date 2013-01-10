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

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;

public class ExternalFacultyCourseRosterTO implements Serializable,
		ExternalDataTO<ExternalFacultyCourseRoster> {

	private String schoolId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String facultySchoolId;
	private String formattedCourse;
	private String primaryEmailAddress;
	private String statusCode;
	private String termCode;

	/**
	 * Empty constructor. Should only ever be used for unit tests or ORMs.
	 */
	public ExternalFacultyCourseRosterTO() {
		super();
	}

	/**
	 * Construct a simple Person from the specified model
	 *
	 * @param externalFacultyCourseRoster
	 *            The ExternalFacultyCourseRoster model to copy
	 */
	public ExternalFacultyCourseRosterTO(
			@NotNull final ExternalFacultyCourseRoster externalFacultyCourseRoster) {
		super();
		from(externalFacultyCourseRoster);
	}

	@Override
	public void from(@NotNull final ExternalFacultyCourseRoster model) {
		if (model == null) {
			throw new IllegalArgumentException(
					"ExternalFacultyCourseRoster required when construcing a new simple ExternalFacultyCourseRosterTO.");
		}

		schoolId = model.getSchoolId();
		firstName = model.getFirstName();
		middleName = model.getMiddleName();
		lastName = model.getLastName();
		facultySchoolId = model.getFacultySchoolId();
		formattedCourse = model.getFormattedCourse();
		primaryEmailAddress = model.getPrimaryEmailAddress();
		statusCode = model.getStatusCode();
		termCode = model.getTermCode();
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(@NotNull final String schoolId) {
		this.schoolId = schoolId;
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

	public String getFacultySchoolId() {
		return facultySchoolId;
	}

	public void setFacultySchoolId(@NotNull final String facultySchoolId) {
		this.facultySchoolId = facultySchoolId;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(@NotNull final String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(final String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(final String statusCode) {
		this.statusCode = statusCode;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(final String termCode) {
		this.termCode = termCode;
	}
}
