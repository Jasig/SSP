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
import org.jasig.ssp.model.external.ExternalStudentTranscriptTerm;

public class ExternalStudentTranscriptTermTO implements Serializable,
		ExternalDataTO<ExternalStudentTranscriptTerm> {

	/**
	 * @param schoolId
	 * @param creditHoursForGpa
	 * @param creditHoursEarned
	 * @param creditHoursAttempted
	 * @param creditHoursNotCompleted
	 * @param creditCompletionRate
	 * @param totalQualityPoints
	 * @param gradePointAverage
	 * @param termCode
	 */
	public ExternalStudentTranscriptTermTO(String schoolId,
			BigDecimal creditHoursForGpa, BigDecimal creditHoursEarned,
			BigDecimal creditHoursAttempted,
			BigDecimal creditHoursNotCompleted,
			BigDecimal creditCompletionRate, BigDecimal totalQualityPoints,
			BigDecimal gradePointAverage, String termCode) {
		super();
		this.schoolId = schoolId;
		this.creditHoursForGpa = creditHoursForGpa;
		this.creditHoursEarned = creditHoursEarned;
		this.creditHoursAttempted = creditHoursAttempted;
		this.creditHoursNotCompleted = creditHoursNotCompleted;
		this.creditCompletionRate = creditCompletionRate;
		this.totalQualityPoints = totalQualityPoints;
		this.gradePointAverage = gradePointAverage;
		this.termCode = termCode;
	}
	
	public ExternalStudentTranscriptTermTO()
	{
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8234383994143195272L;

	private String schoolId;
	
	private BigDecimal creditHoursForGpa;
	
	private BigDecimal creditHoursEarned;
	
	private BigDecimal creditHoursAttempted;
	
	private BigDecimal creditHoursNotCompleted;
	
	private BigDecimal creditCompletionRate;
	
	private BigDecimal totalQualityPoints;
	
	private BigDecimal gradePointAverage;
	
	private String termCode;

	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(String schoolId) {
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
	public void setCreditHoursForGpa(BigDecimal creditHoursForGpa) {
		this.creditHoursForGpa = creditHoursForGpa;
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
	 * @return the creditHoursAttempted
	 */
	public BigDecimal getCreditHoursAttempted() {
		return creditHoursAttempted;
	}

	/**
	 * @param creditHoursAttempted the creditHoursAttempted to set
	 */
	public void setCreditHoursAttempted(BigDecimal creditHoursAttempted) {
		this.creditHoursAttempted = creditHoursAttempted;
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
	 * @return the totalQualityPoints
	 */
	public BigDecimal getTotalQualityPoints() {
		return totalQualityPoints;
	}

	/**
	 * @param totalQualityPoints the totalQualityPoints to set
	 */
	public void setTotalQualityPoints(BigDecimal totalQualityPoints) {
		this.totalQualityPoints = totalQualityPoints;
	}

	/**
	 * @return the gradePointAverage
	 */
	public BigDecimal getGradePointAverage() {
		return gradePointAverage;
	}

	/**
	 * @param gradePointAverage the gradePointAverage to set
	 */
	public void setGradePointAverage(BigDecimal gradePointAverage) {
		this.gradePointAverage = gradePointAverage;
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

	@Override
	public void from(ExternalStudentTranscriptTerm model) {
		
		schoolId = model.getSchoolId();
		
		creditHoursForGpa = model.getCreditHoursForGpa();
		
		creditHoursEarned = model.getCreditHoursEarned();
		
		creditHoursAttempted = model.getCreditHoursAttempted();
		
		creditHoursNotCompleted = model.getCreditHoursNotCompleted();
		
		creditCompletionRate = model.getCreditCompletionRate();
		
		totalQualityPoints = model.getTotalQualityPoints();
		
		gradePointAverage = model.getGradePointAverage();
		
		termCode = model.getTermCode();
	}

}
