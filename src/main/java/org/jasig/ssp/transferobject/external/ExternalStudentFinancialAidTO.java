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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jasig.ssp.model.external.ExternalStudentFinancialAid;
import org.jasig.ssp.model.external.FinancialFileStatus;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyDeserializer;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlySerializer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


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

	@JsonSerialize(using = DateOnlySerializer.class)
	@JsonDeserialize(using = DateOnlyDeserializer.class)
	Date fafsaDate;
	
	BigDecimal financialAidRemaining;
	
	BigDecimal originalLoanAmount;
	
	BigDecimal remainingLoanAmount;
	
	BigDecimal balanceOwed;
	
	FinancialFileStatus financialAidFileStatus;
	
	Integer termsLeft;
	
	BigDecimal institutionalLoanAmount;
	
	String sapStatusCode;
	
	String eligibleFederalAid;
	
	public ExternalStudentFinancialAidTO(){
		super();
	}
	
	/**
	 * @param schoolId the school id
	 * @param financialAidGpa the financial aid GPA
	 * @param gpa20bHrsNeeded the GPA 20b hours needed
	 * @param gpa20aHrsNeeded the GPA 20a hours needed
	 * @param neededFor67PtcCompletion the amount needed for 67% completion
	 * @param currentYearFinancialAidAward the current year financial aid award
	 * @param sapStatus the SAP status
	 * @param fafsaDate the FASFA date
	 * @param financialAidRemaining the amount of financial aid remaining
	 * @param originalLoanAmount the original loan amount
	 * @param remainingLoanAmount the remaining load amount
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
	
	public String getSapStatusCode() {
		return sapStatusCode;
	}

	public void setSapStatusCode(String sapStatusCode) {
		this.sapStatusCode = sapStatusCode;
	}

	public String getEligibleFederalAid() {
		return eligibleFederalAid;
	}

	public void setEligibleFederalAid(String eligibleFederalAid) {
		this.eligibleFederalAid = eligibleFederalAid;
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
		sapStatusCode = model.getSapStatusCode();
		fafsaDate = model.getFafsaDate();
		financialAidRemaining = model.getFinancialAidRemaining();
		originalLoanAmount = model.getOriginalLoanAmount();
		remainingLoanAmount = model.getRemainingLoanAmount();	
		financialAidFileStatus = model.getFinancialAidFileStatus();
		termsLeft = model.getTermsLeft();
		institutionalLoanAmount = model.getInstitutionalLoanAmount();
		eligibleFederalAid = model.getEligibleFederalAid();
	}

	
}
