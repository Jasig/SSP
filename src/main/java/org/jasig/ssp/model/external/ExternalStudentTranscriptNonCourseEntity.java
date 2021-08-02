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
package org.jasig.ssp.model.external;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This model is for entities that are considered non-course but
 *  exist as informational items to display and/or as substitutes for real
 *   external_student_transcript_courses in MAP and
 *    MAP Plan Status Calculation
 */
@Entity
@Immutable
@Table(name = "v_external_student_transcript_non_course")
public class ExternalStudentTranscriptNonCourseEntity extends AbstractExternalData
		implements ExternalData, Serializable {

	private static final long serialVersionUID = 5886930169590147558L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;

	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 35)
	private String targetFormattedCourse;

    @Column(nullable = false, length = 25)
    @NotNull
    @NotEmpty
    @Size(max = 25)
    private String termCode;

    @Column(nullable = false, length = 50)
    @Size(max = 50)
    private String nonCourseCode;
	
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
	private String creditType;
	
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String statusCode;
	



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
	 * @return the targetFormattedCourse
	 */
	public String getTargetFormattedCourse() {
		return targetFormattedCourse;
	}

	/**
	 * @param targetFormattedCourse
	 *            the targetFormattedCourse to set that can replace a student's
     *             existing MAP Plan Status Calculated course and mark as passing
	 */
	public void setTargetFormattedCourse(final String targetFormattedCourse) {
		this.targetFormattedCourse = targetFormattedCourse;
	}

    /**
     * @return the non-course code
     *    the code for the non-course entity
     */
    public String getNonCourseCode() {
        return nonCourseCode;
    }

    /**
     * @param nonCourseCode
     *     the non-course code to set
     */
    public void setNonCourseCode(final String nonCourseCode) {
        this.nonCourseCode = nonCourseCode;
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
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the status_code to set
	 */
	public void setStatusCode(final String statusCode) {
		this.statusCode = statusCode;
	}
}
