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
import org.jasig.ssp.model.external.ExternalStudentTranscript;

public class ExternalStudentTranscriptTO implements ExternalDataTO<ExternalStudentTranscript>,
		Serializable {
	

	private String schoolId;
	private BigDecimal creditHoursForGpa;
	private BigDecimal creditHoursAttempted;
	private BigDecimal totalQualityPoints;
	private BigDecimal gradePointAverage;
	
	private BigDecimal creditHoursEarned;
	private BigDecimal creditCompletionRate;
	private BigDecimal creditHoursNotCompleted;
	private String currentRestrictions;
	private String academicStanding;
	private String gpaTrendIndicator;

	@Override
	public void from(ExternalStudentTranscript model) {
		
		schoolId = model.getSchoolId();
		creditHoursForGpa = model.getCreditHoursForGpa();
		creditHoursAttempted = model.getCreditHoursAttempted();
		totalQualityPoints = model.getTotalQualityPoints();
		gradePointAverage = model.getGradePointAverage();
		
		creditHoursEarned = model.getCreditHoursEarned();
		creditCompletionRate = model.getCreditCompletionRate();
		creditHoursNotCompleted = model.getCreditHoursNotCompleted();
		currentRestrictions = model.getCurrentRestrictions();
		academicStanding = model.getAcademicStanding();
		gpaTrendIndicator = model.getGpaTrendIndicator();
	}

	/**
	 * @param schoolId
	 * @param creditHoursForGpa
	 * @param creditHoursAttempted
	 * @param totalQualityPoints
	 * @param gradePointAverrage
	 */
	public ExternalStudentTranscriptTO(final String schoolId,
			final BigDecimal creditHoursForGpa, final BigDecimal creditHoursAttempted,
			final BigDecimal totalQualityPoints, final BigDecimal gradePointAverrage) {
		super();
		this.schoolId = schoolId;
		this.creditHoursForGpa = creditHoursForGpa;
		this.creditHoursAttempted = creditHoursAttempted;
		this.totalQualityPoints = totalQualityPoints;
		this.gradePointAverage = gradePointAverrage;
	}
	
	public ExternalStudentTranscriptTO(ExternalStudentTranscript model)
	{
		super();
		from(model);
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
	 * @return the creditHoursForGpa
	 */
	public BigDecimal getCreditHoursForGpa() {
		return creditHoursForGpa;
	}

	/**
	 * @param creditHoursForGpa the creditHoursForGpa to set
	 */
	public void setCreditHoursForGpa(final BigDecimal creditHoursForGpa) {
		this.creditHoursForGpa = creditHoursForGpa;
	}

	/**
	 * @return the creditHoursAttempted
	 */
	public BigDecimal getCreditHoursAttempted() {
		return creditHoursAttempted;
	}

	/**
	 * @param creditHoursAttempted the creditHoursAttempted to set
	 */
	public void setCreditHoursAttempted(final BigDecimal creditHoursAttempted) {
		this.creditHoursAttempted = creditHoursAttempted;
	}

	/**
	 * @return the totalQualityPoints
	 */
	public BigDecimal getTotalQualityPoints() {
		return totalQualityPoints;
	}

	/**
	 * @param totalQualityPoints the totalQualityPoints to set
	 */
	public void setTotalQualityPoints(final BigDecimal totalQualityPoints) {
		this.totalQualityPoints = totalQualityPoints;
	}

	/**
	 * @return the gradePointAverrage
	 */
	public BigDecimal getGradePointAverage() {
		return gradePointAverage;
	}

	/**
	 * @param gradePointAverrage the gradePointAverrage to set
	 */
	public void setGradePointAverage(final BigDecimal gradePointAverage) {
		this.gradePointAverage = gradePointAverage;
	}

	/**
	 * @return the creditHoursEarned
	 */
	public BigDecimal getCreditHoursEarned() {
		return creditHoursEarned;
	}

	/**
	 * @param creditHoursEarned the creditHoursEarned to set
	 */
	public void setCreditHoursEarned(BigDecimal creditHoursEarned) {
		this.creditHoursEarned = creditHoursEarned;
	}

	/**
	 * @return the creditCompletionRate
	 */
	public BigDecimal getCreditCompletionRate() {
		return creditCompletionRate;
	}

	/**
	 * @param creditCompletionRate the creditCompletionRate to set
	 */
	public void setCreditCompletionRate(BigDecimal creditCompletionRate) {
		this.creditCompletionRate = creditCompletionRate;
	}

	/**
	 * @return the creditHoursNotCompleted
	 */
	public BigDecimal getCreditHoursNotCompleted() {
		return creditHoursNotCompleted;
	}

	/**
	 * @param creditHoursNotCompleted the creditHoursNotCompleted to set
	 */
	public void setCreditHoursNotCompleted(BigDecimal creditHoursNotCompleted) {
		this.creditHoursNotCompleted = creditHoursNotCompleted;
	}

	/**
	 * @return the CurrentRestrictions
	 */
	public String getCurrentRestrictions() {
		return currentRestrictions;
	}

	/**
	 * @param CurrentRestrictions the CurrentRestrictions to set
	 */
	public void setCurrentRestrictions(String currentRestrictions) {
		this.currentRestrictions = currentRestrictions;
	}

	/**
	 * @return the academicStanding
	 */
	public String getAcademicStanding() {
		return academicStanding;
	}

	/**
	 * @param academicStanding the academicStanding to set
	 */
	public void setAcademicStanding(String academicStanding) {
		this.academicStanding = academicStanding;
	}

	/**
	 * @return the gpaTrendIndicator
	 */
	public String getGpaTrendIndicator() {
		return gpaTrendIndicator;
	}

	/**
	 * @param gpaTrendIndicator the gpaTrendIndicator to set
	 */
	public void setGpaTrendIndicator(String gpaTrendIndicator) {
		this.gpaTrendIndicator = gpaTrendIndicator;
	}

}
