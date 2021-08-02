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
import org.jasig.ssp.model.external.ExternalStudentTest;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyDeserializer;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlySerializer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ExternalStudentTestTO implements Serializable, ExternalDataTO<ExternalStudentTest> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6430469007527451476L;
	
	private String schoolId;
	private String name;
	private String testCode;
	private String subTestCode;
	private String subTestName;

	@JsonSerialize(using = DateOnlySerializer.class)
	@JsonDeserialize(using = DateOnlyDeserializer.class)
	private Date takenDate;

	private BigDecimal score;
	private String status;
	private String outcome;
	
	private String testProviderLink;
	
	private Boolean hasDetails = false;
	
	public ExternalStudentTestTO(){
		super();
	}

	/**
	 * @param schoolId the school id
	 * @param testCode the test code
	 * @param testName the test name
	 * @param subTestCode the sub test code
	 * @param subTestName the sub test name
	 * @param testDate the test date
	 * @param score the score
	 * @param status the status
	 */
	public ExternalStudentTestTO(final String schoolId,  final String testCode, 
			final String testName,
			final String subTestCode, final String subTestName, final Date testDate,
			final BigDecimal score, final String status) {
		super();
		this.schoolId = schoolId;
		this.testCode = testCode;
		this.name = testName;
		this.subTestCode = subTestCode;
		this.subTestName = subTestName;
		this.takenDate = testDate;
		this.score = score;
		this.status = status;
	}
	
	/**
	 * @param schoolId the school id
	 * @param testCode the test code
	 * @param testName the test name
	 * @param subTestCode the sub test code
	 * @param subTestName the sub test name
	 * @param testDate the test date
	 * @param score the score
	 * @param status the status
	 * @param outcome the outcome
	 */
	public ExternalStudentTestTO(final String schoolId,  final String testCode, 
			final String testName,
			final String subTestCode, final String subTestName, final Date testDate,
			final BigDecimal score, final String status, final String outcome) {
		super();
		this.schoolId = schoolId;
		this.testCode = testCode;
		this.name = testName;
		this.subTestCode = subTestCode;
		this.subTestName = subTestName;
		this.takenDate = testDate;
		this.score = score;
		this.status = status;
		this.outcome = outcome;
	}
	
	
	@Override
	public void from(ExternalStudentTest model) {
		
		schoolId = model.getSchoolId();
		name = model.getTestName();
		testCode = model.getTestCode();
		subTestCode = model.getSubTestCode();
		subTestName = model.getSubTestName();
		takenDate = model.getTestDate();
		score = model.getScore();
		status = model.getStatus();
		outcome = model.getOutcome();
		hasDetails = model.getHasDetails();
		testProviderLink = model.getTestProviderLink();
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
	 * @return the testName
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param testName the testName to set
	 */
	public void setName(final String testName) {
		this.name = testName;
	}


	/**
	 * @return the subTestCode
	 */
	public String getSubTestCode() {
		return subTestCode;
	}


	/**
	 * @param subTestCode the subTestCode to set
	 */
	public void setSubTestCode(final String subTestCode) {
		this.subTestCode = subTestCode;
	}


	/**
	 * @return the subTestName
	 */
	public String getSubTestName() {
		return subTestName;
	}


	/**
	 * @param subTestName the subTestName to set
	 */
	public void setSubTestName(final String subTestName) {
		this.subTestName = subTestName;
	}


	/**
	 * @return the testDate
	 */
	public Date getTakenDate() {
		return takenDate;
	}


	/**
	 * @param testDate the testDate to set
	 */
	public void setTakenDate(final Date testDate) {
		this.takenDate = testDate;
	}


	/**
	 * @return the score
	 */
	public BigDecimal getScore() {
		return score;
	}


	/**
	 * @param score the score to set
	 */
	public void setScore(final BigDecimal score) {
		this.score = score;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(final String status) {
		this.status = status;
	}


	/**
	 * @return the testCode
	 */
	public String getTestCode() {
		return testCode;
	}


	/**
	 * @param testCode the testCode to set
	 */
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	
	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return outcome;
	}

	/**
	 * @param outcome the outcome to set
	 */
	public void setOutcome(final String outcome) {
		this.outcome = outcome;
	}

	public String getTestProviderLink() {
		return testProviderLink;
	}

	public void setTestProviderLink(String testProviderLink) {
		this.testProviderLink = testProviderLink;
	}

	public Boolean getHasDetails() {
		return hasDetails;
	}

	public void setHasDetails(Boolean hasDetails) {
		this.hasDetails = hasDetails;
	}
}
