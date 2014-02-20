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
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Immutable
@Table(name = "v_external_student_financial_aid")
public class ExternalStudentFinancialAid extends AbstractExternalData implements
		Serializable, ExternalData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2228829757687092678L;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	String schoolId;
	
	@Column(nullable = false)
	BigDecimal financialAidGpa;
	
	@Column(nullable = true, name = "gpa_20_b_hrs_Needed")
	BigDecimal gpa20BHrsNeeded;
	
	@Column(nullable = true,  name = "gpa_20_a_hrs_Needed")
	BigDecimal gpa20AHrsNeeded;
	
	@Column(nullable = true,  name = "needed_for_67ptc_completion")
	BigDecimal neededFor67PtcCompletion;
	
	@Column(nullable = true)
	String currentYearFinancialAidAward;
	
	
	/* retained for legacy reasons */
	@Column(nullable = true)
	String sapStatus;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	Date fafsaDate;
	
	@Column(nullable = true)
	BigDecimal financialAidRemaining;
	
	@Column(nullable = true)
	BigDecimal originalLoanAmount;
	
	@Column(nullable = true)
	BigDecimal remainingLoanAmount;
	
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	FinancialFileStatus financialAidFileStatus;
	
	@Column(nullable = true)
	Integer termsLeft;
	
	@Column(nullable = true)
	BigDecimal institutionalLoanAmount;
	
	@Column(nullable = true, length = 10)
	@Size(max = 10)
	String sapStatusCode;
	
	@Column(nullable = true, length = 1)
	@Size(max = 1)
	String eligibleFederalAid;
	
	public String getSapStatusCode() {
		return sapStatusCode;
	}

	public void setSapStatusCode(String sapStatusCode) {
		this.sapStatusCode = sapStatusCode;
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
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return the financialAidGpa
	 */
	public BigDecimal getFinancialAidGpa() {
		return financialAidGpa;
	}

	/**
	 * @param financialAidGpa the financialAidGpa to set
	 */
	public void setFinancialAidGpa(BigDecimal financialAidGpa) {
		this.financialAidGpa = financialAidGpa;
	}

	/**
	 * @return the gpa20BHrsNeeded
	 */
	public BigDecimal getGpa20BHrsNeeded() {
		return gpa20BHrsNeeded;
	}

	/**
	 * @param gpa20bHrsNeeded the gpa20BHrsNeeded to set
	 */
	public void setGpa20BHrsNeeded(BigDecimal gpa20bHrsNeeded) {
		gpa20BHrsNeeded = gpa20bHrsNeeded;
	}

	/**
	 * @return the gpa20AHrsNeeded
	 */
	public BigDecimal getGpa20AHrsNeeded() {
		return gpa20AHrsNeeded;
	}

	/**
	 * @param gpa20aHrsNeeded the gpa20AHrsNeeded to set
	 */
	public void setGpa20AHrsNeeded(BigDecimal gpa20aHrsNeeded) {
		gpa20AHrsNeeded = gpa20aHrsNeeded;
	}

	/**
	 * @return the neededFor67PtcCompletion
	 */
	public BigDecimal getNeededFor67PtcCompletion() {
		return neededFor67PtcCompletion;
	}

	/**
	 * @param neededFor67PtcCompletion the neededFor67PtcCompletion to set
	 */
	public void setNeededFor67PtcCompletion(BigDecimal neededFor67PtcCompletion) {
		this.neededFor67PtcCompletion = neededFor67PtcCompletion;
	}

	/**
	 * @return the currentYearFinancialAidAward
	 */
	public String getCurrentYearFinancialAidAward() {
		return currentYearFinancialAidAward;
	}

	/**
	 * @param currentYearFinancialAidAward the currentYearFinancialAidAward to set
	 */
	public void setCurrentYearFinancialAidAward(String currentYearFinancialAidAward) {
		this.currentYearFinancialAidAward = currentYearFinancialAidAward;
	}

	/**
	 * @return the sapStatus
	 */
	public String getSapStatus() {
		return sapStatus;
	}

	/**
	 * @param sapStatus the sapStatus to set
	 */
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}

	/**
	 * @return the fasaDate
	 */
	public Date getFafsaDate() {
		return fafsaDate;
	}

	/**
	 * @param fafsaDate the fafsaDate to set
	 */
	public void setFafsaDate(Date fafsaDate) {
		this.fafsaDate = fafsaDate;
	}

	/**
	 * @return the financialAidRemaining
	 */
	public BigDecimal getFinancialAidRemaining() {
		return financialAidRemaining;
	}

	/**
	 * @param financialAidRemaining the financialAidRemaining to set
	 */
	public void setFinancialAidRemaining(BigDecimal financialAidRemaining) {
		this.financialAidRemaining = financialAidRemaining;
	}

	/**
	 * @return the originalLoanAmount
	 */
	public BigDecimal getOriginalLoanAmount() {
		return originalLoanAmount;
	}

	/**
	 * @param originalLoanAmount the originalLoanAmount to set
	 */
	public void setOriginalLoanAmount(BigDecimal originalLoanAmount) {
		this.originalLoanAmount = originalLoanAmount;
	}

	/**
	 * @return the remainingLoanAmount
	 */
	public BigDecimal getRemainingLoanAmount() {
		return remainingLoanAmount;
	}

	/**
	 * @param remainingLoanAmount the remainingLoanAmount to set
	 */
	public void setRemainingLoanAmount(BigDecimal remainingLoanAmount) {
		this.remainingLoanAmount = remainingLoanAmount;
	}

	public FinancialFileStatus getFinancialAidFileStatus() {
		return financialAidFileStatus;
	}

	public void setFinancialAidFileStatus(FinancialFileStatus financialAidFileStatus) {
		this.financialAidFileStatus = financialAidFileStatus;
	}

	public Integer getTermsLeft() {
		return termsLeft;
	}

	public void setTermsLeft(Integer termsLeft) {
		this.termsLeft = termsLeft;
	}

	public BigDecimal getInstitutionalLoanAmount() {
		return institutionalLoanAmount;
	}

	public void setInstitutionalLoanAmount(BigDecimal institutionalLoanAmount) {
		this.institutionalLoanAmount = institutionalLoanAmount;
	}

	public String getEligibleFederalAid() {
		return eligibleFederalAid;
	}

	public void setEligibleFederalAid(String eligibleFederalAid) {
		this.eligibleFederalAid = eligibleFederalAid;
	}

}
