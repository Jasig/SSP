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

/**
 * Represents what amounts to a compound JPA primary key on
 * {@link WriteableExternalFacultyCourseRoster} (even if the underlying db does not
 * actually declare a PK for the corresponding table).
 */
public class ExternalFacultyCourseRosterPk implements Serializable {

	private String facultySchoolId;
	private String schoolId;
	private String termCode;
	private String formattedCourse;
	private String sectionCode;
	private String sectionNumber;

	public String getFacultySchoolId() {
		return facultySchoolId;
	}

	public void setFacultySchoolId(String facultySchoolId) {
		this.facultySchoolId = facultySchoolId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
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
	 * @return the sectionNumber
	 */
	public String getSectionNumber() {
		return sectionNumber;
	}

	/**
	 * @param sectionNumber the sectionNumber to set
	 */
	public void setSectionNumber(String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ExternalFacultyCourseRosterPk)) return false;

		ExternalFacultyCourseRosterPk that = (ExternalFacultyCourseRosterPk) o;

		if (!facultySchoolId.equals(that.facultySchoolId)) return false;
		if (!formattedCourse.equals(that.formattedCourse)) return false;
		if (!schoolId.equals(that.schoolId)) return false;
		if (!termCode.equals(that.termCode)) return false;
		if (!sectionCode.equals(that.sectionCode)) return false;
		if (!sectionNumber.equals(that.sectionNumber)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = facultySchoolId.hashCode();
		result = 31 * result + schoolId.hashCode();
		result = 31 * result + termCode.hashCode();
		result = 31 * result + formattedCourse.hashCode();
		result = 31 * result + sectionCode.hashCode();
		result = 31 * result + sectionNumber.hashCode();
		return result;
	}
}
