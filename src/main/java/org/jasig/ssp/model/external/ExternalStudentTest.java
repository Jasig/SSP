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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Immutable
@Table(name = "v_external_student_test")
public class ExternalStudentTest extends AbstractExternalData implements
		Serializable, ExternalData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -872213439723690003L;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;
	
	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String testCode;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String testName;
	
	
	@Column(nullable = true, length = 25)
	@Size(max = 25)
	private String subTestCode;
	
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String subTestName;
	
	/**
	 * Entity creation time stamp.
	 */
	@Temporal(TemporalType.DATE)
	@Column(nullable = false, updatable = false)
	private Date testDate;
	
	@Column(nullable = false)
	private BigDecimal  score;
	
	@Column(nullable = false, length = 25)
	@NotNull
	@NotEmpty
	@Size(max = 25)
	private String status;

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
	 * @return the testCode
	 */
	public String getTestCode() {
		return testCode;
	}


	/**
	 * @param testCode the testCode to set
	 */
	public void setTestCode(final String testCode) {
		this.testCode = testCode;
	}


	/**
	 * @return the testName
	 */
	public String getTestName() {
		return testName;
	}


	/**
	 * @param testName the testName to set
	 */
	public void setTestName(final String testName) {
		this.testName = testName;
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
	public Date getTestDate() {
		return testDate;
	}


	/**
	 * @param testDate the testDate to set
	 */
	public void setTestDate(final Date testDate) {
		this.testDate = testDate;
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

}
