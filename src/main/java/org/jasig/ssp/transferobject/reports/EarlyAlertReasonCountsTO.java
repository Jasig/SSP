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

package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;


public class EarlyAlertReasonCountsTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private String schoolId;
    private String lastName;
    private String firstName;
    private String courseName;
    private String courseTitle;
    private String facultyFirstName;
    private String facultyLastName;
    private String facultyName;
    private Long totalReasonsReported;
    private String termCode;


	public EarlyAlertReasonCountsTO () {

    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public String getTermCode() {
		return termCode;
	}

    public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

    public String getCourseName () {
        return courseName;
    }

    public void setCourseName (final String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTitle () {
        return courseTitle;
    }

    public void setCourseTitle (final String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getSchoolId () {
        return schoolId;
    }

    public void setSchoolId (final String schoolId) {
        this.schoolId = schoolId;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName (final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (final String firstName) {
        this.firstName = firstName;
    }

    public Long getTotalReasonsReported () {
        return totalReasonsReported;
    }

    public void setTotalReasonsReported (final Long totalReasonsReported) {
        this.totalReasonsReported = totalReasonsReported;
    }

    public String getFacultyFirstName () {
        return facultyFirstName;
    }

    public void setFacultyFirstName (final String facultyFirstName) {
        this.facultyFirstName = facultyFirstName;
    }

    public String getFacultyLastName () {
        return facultyLastName;
    }

    public void setFacultyLastName (final String facultyLastName) {
        this.facultyLastName = facultyLastName;
    }

    public String getFacultyName () {
        if(facultyName == null || facultyName.length() <= 0) {
            facultyName = getFacultyFirstName() + " " + getFacultyLastName();
        }
        return facultyName;
    }

    public void setFacultyName (final String facultyFullName) {
        this.facultyName = facultyFullName;
    }

    public void setFacultyName (final String facultyFirstName, final String facultyLastName) {
        this.facultyName = facultyFirstName + " " + facultyLastName;
    }
}
