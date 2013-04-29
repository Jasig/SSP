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
import java.util.Date;

import org.jasig.ssp.model.external.ExternalStudentFinancialAid;

public class ExternalStudentFinancialAidTO implements Serializable,
		ExternalDataTO<ExternalStudentFinancialAid> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9181364579964163471L;

	String schoolId;
	
	BigDecimal financialAidGpa;
	
	BigDecimal gpa20BHrsNeeded;
	
	BigDecimal gpa20AHrsNeeded;
	
	BigDecimal neededFor67PtcCompletion;
	
	String currentYearFinancialAidAward;
	
	String sapStatus;
	
	Date fafsaDate;
	
	BigDecimal financialAidRemaining;
	
	BigDecimal originalLoanAmount;
	
	BigDecimal remainingLoanAmount;
	
	BigDecimal balanceOwed;
	
	public ExternalStudentFinancialAidTO(){
		super();
	}
	
	/**
	 * @param schoolId
	 * @param financialAidGpa
	 * @param gpa20bHrsNeeded
	 * @param gpa20aHrsNeeded
	 * @param neededFor67PtcCompletion
	 * @param currentYearFinancialAidAward
	 * @param sapStatus
	 * @param fafsaDate
	 * @param financialAidRemaining
	 * @param originalLoanAmount
	 * @param remainingLoanAmount
	 */
	public ExternalStudentFinancialAidTO(String schoolId,
			BigDecimal financialAidGpa, BigDecimal gpa20bHrsNeeded,
			BigDecimal gpa20aHrsNeeded, BigDecimal neededFor67PtcCompletion,
			String currentYearFinancialAidAward, String sapStatus,
			Date fafsaDate, BigDecimal financialAidRemaining,
			BigDecimal originalLoanAmount, BigDecimal remainingLoanAmount) {
		super();
		this.schoolId = schoolId;
		this.financialAidGpa = financialAidGpa;
		gpa20BHrsNeeded = gpa20bHrsNeeded;
		gpa20AHrsNeeded = gpa20aHrsNeeded;
		this.neededFor67PtcCompletion = neededFor67PtcCompletion;
		this.currentYearFinancialAidAward = currentYearFinancialAidAward;
		this.sapStatus = sapStatus;
		this.fafsaDate = fafsaDate;
		this.financialAidRemaining = financialAidRemaining;
		this.originalLoanAmount = originalLoanAmount;
		this.remainingLoanAmount = remainingLoanAmount;
	}
	
	public ExternalStudentFinancialAidTO(ExternalStudentFinancialAid model){
		super();
		from(model);
	}

	
	public ExternalStudentFinancialAidTO(ExternalStudentFinancialAid model, BigDecimal balanceOwed){
		super();
		from(model);
		this.balanceOwed = balanceOwed;
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
	 * @return the fafsaDate
	 */
	public Date getFafsaDate() {
		return fafsaDate;
	}

	/**
	 * @param fafsaDate the fafsaDate to set
	 */
	public void setFasaDate(Date fafsaDate) {
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

	/**
	 * @return the balanceOwed
	 */
	public BigDecimal getBalanceOwed() {
		return balanceOwed;
	}

	/**
	 * @param balanceOwed the balanceOwed to set
	 */
	public void setBalanceOwed(BigDecimal balanceOwed) {
		this.balanceOwed = balanceOwed;
	}

	@Override
	public void from(ExternalStudentFinancialAid model) {
		schoolId = model.getSchoolId();
		financialAidGpa = model.getFinancialAidGpa();
		gpa20BHrsNeeded = model.getGpa20BHrsNeeded();
		gpa20AHrsNeeded = model.getGpa20AHrsNeeded();
		neededFor67PtcCompletion = model.getNeededFor67PtcCompletion();
		currentYearFinancialAidAward = model.getCurrentYearFinancialAidAward();
		sapStatus = model.getSapStatus();
		fafsaDate = model.getFafsaDate();
		financialAidRemaining = model.getFinancialAidRemaining();
		originalLoanAmount = model.getOriginalLoanAmount();
		remainingLoanAmount = model.getRemainingLoanAmount();		
	}

	
}
