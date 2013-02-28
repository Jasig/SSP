package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.jasig.ssp.model.external.ExternalStudentTest;

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
	private Date takenDate;
	private BigDecimal score;
	private String status;
	
	public ExternalStudentTestTO(){
		super();
	}

	/**
	 * @param schoolId
	 * @param testName
	 * @param subTestCode
	 * @param subTestName
	 * @param testDate
	 * @param score
	 * @param status
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
	

}
