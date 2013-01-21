package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;

public class EarlyAlertTermCaseCountsTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String termCode;
	String termName;
	Long totalStudents;
	Long totalCases;
	Long totalRespondedTo;
	Long totalClosed;
	
	public EarlyAlertTermCaseCountsTO(String termCode, String termName,
			Long totalStudents, Long totalCases, Long totalRespondedTo,
			Long totalClosed) {
		super();
		this.termCode = termCode;
		this.termName = termName;
		this.totalStudents = totalStudents;
		this.totalCases = totalCases;
		this.totalRespondedTo = totalRespondedTo;
		this.totalClosed = totalClosed;
	}
	
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public Long getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(Long totalStudents) {
		this.totalStudents = totalStudents;
	}
	public Long getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(Long totalCases) {
		this.totalCases = totalCases;
	}
	public Long getTotalRespondedTo() {
		return totalRespondedTo;
	}
	public void setTotalRespondedTo(Long totalRespondedTo) {
		this.totalRespondedTo = totalRespondedTo;
	}
	public Long getTotalClosed() {
		return totalClosed;
	}
	public void setTotalClosed(Long totalClosed) {
		this.totalClosed = totalClosed;
	}


}
