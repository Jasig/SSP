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
package org.jasig.ssp.transferobject.external;


import org.jasig.ssp.model.external.ExternalStudentTranscriptNonCourseEntity;
import java.io.Serializable;
import java.math.BigDecimal;


public class ExternalStudentTranscriptNonCourseEntityTO implements ExternalDataTO<ExternalStudentTranscriptNonCourseEntity>,
		Serializable {

	private static final long serialVersionUID = -6438162986848364288L;

    private String schoolId;
    private String targetFormattedCourse;
    private String termCode;
    private String nonCourseCode;
    private String title;
    private String description;
    private String grade;
    private BigDecimal creditEarned;
    private String creditType;
    private String statusCode;


    public ExternalStudentTranscriptNonCourseEntityTO (ExternalStudentTranscriptNonCourseEntity model) {
        super();
        from(model);
    }

    public ExternalStudentTranscriptNonCourseEntityTO () {
        super();
    }


	@Override
	public void from(ExternalStudentTranscriptNonCourseEntity model) {
		schoolId = model.getSchoolId();
        targetFormattedCourse = model.getTargetFormattedCourse();
        termCode = model.getTermCode();
        nonCourseCode = model.getNonCourseCode();
        title = model.getTitle();
		description = model.getDescription();
		grade = model.getGrade();
		creditEarned = model.getCreditEarned();
		creditType = model.getCreditType();
		statusCode = model.getStatusCode();
	}


	/**
	 * @param schoolId
	 * @param targetFormattedCourse
	 * @param termCode
	 * @param title
	 * @param description
	 * @param grade
	 * @param creditEarned
	 * @param creditType
     * @param statusCode
	 */
	public ExternalStudentTranscriptNonCourseEntityTO (final String schoolId, final String targetFormattedCourse,
                                                       final String termCode, final String nonCourseCode,
                                                       final String title, final String description, final String grade,
                                                       final BigDecimal creditEarned, final String creditType,
                                                       final String statusCode) {
		super();
		this.schoolId = schoolId;
		this.targetFormattedCourse = targetFormattedCourse;
        this.termCode = termCode;
        this.nonCourseCode = nonCourseCode;
        this.title = title;
		this.description = description;
		this.grade = grade;
		this.creditEarned = creditEarned;
		this.creditType = creditType;
		this.statusCode = statusCode;
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
	 * @return the targetFormattedCourse
	 */
	public String getTargetFormattedCourse() {
		return targetFormattedCourse;
	}

    /**
	 * @param targetFormattedCourse the targetFormattedCourse to set
	 */
	public void setTargetFormattedCourse(final String targetFormattedCourse) {
		this.targetFormattedCourse = targetFormattedCourse;
	}

    /**
     * @return nonCourseCode
     */
    public String getNonCourseCode () {
        return nonCourseCode;
    }

    /**
     * @param nonCourseCode the nonCourseCode to set
     */
    public void setNonCourseCode (String nonCourseCode) {
        this.nonCourseCode = nonCourseCode;
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
}
