/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.reports;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import java.io.Serializable;
import java.util.List;


public class SpecialServiceStudentCoursesTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private String schoolId;
    private String firstName;
    private String lastName;
    private String studentName;
    private String campusName;
    private String courseTitle;
    private String formattedCourse;
    private String facultySchoolId;
    private String facultyFirstName;
    private String facultyLastName;
    private String facultyName;
    private String termCode;
    private String statusCode;
    private String grade;
    private List<String> specialServiceGroupNames;
    private String specialServiceGroupNamesForDisplay;

	public SpecialServiceStudentCoursesTO() {
    }

    public SpecialServiceStudentCoursesTO(String schoolId, String formattedCourse, String termCode) {
        this.schoolId = schoolId;
        this.formattedCourse = formattedCourse;
        this.termCode = termCode;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentName() {
        if(StringUtils.isBlank(studentName)) {
            studentName = getFirstName() + " " + getLastName();
        }
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getFormattedCourse() {
        return formattedCourse;
    }

    public void setFormattedCourse(String formattedCourse) {
        this.formattedCourse = formattedCourse;
    }

    public String getFacultySchoolId() {
        return facultySchoolId;
    }

    public void setFacultySchoolId(String facultySchoolId) {
        this.facultySchoolId = facultySchoolId;
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
        if(StringUtils.isBlank(facultyName)) {
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

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setSpecialServiceGroupNamesForDisplay(String specialServiceGroupNamesForDisplay) {
        this.specialServiceGroupNamesForDisplay = specialServiceGroupNamesForDisplay;
    }

    public String getSpecialServiceGroupNamesForDisplay() {
        return specialServiceGroupNamesForDisplay;
    }

    public List<String> getSpecialServiceGroupNames() {
        return specialServiceGroupNames;
    }

    public void setSpecialServiceGroupNames(List<String> specialServiceGroupNames) {
        this.specialServiceGroupNames = specialServiceGroupNames;

        if (CollectionUtils.isNotEmpty(specialServiceGroupNames)) {
            final StringBuilder ssgNamesForDisplay = new StringBuilder();
            for (String ssg : specialServiceGroupNames) {
                ssgNamesForDisplay.append(ssg + " - ");
            }

            this.specialServiceGroupNamesForDisplay = ssgNamesForDisplay.substring(0, ssgNamesForDisplay.length() - 3);
        }
    }
}