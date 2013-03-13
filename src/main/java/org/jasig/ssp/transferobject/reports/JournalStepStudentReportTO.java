package org.jasig.ssp.transferobject.reports;

import java.util.UUID;

public class JournalStepStudentReportTO extends BaseStudentReportTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6054716071286423025L;
	String journalStepDetailName;
	UUID journalEntryDetailId;

	/**
	 * 
	 */
	public JournalStepStudentReportTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param person
	 */
	public JournalStepStudentReportTO(BaseStudentReportTO person) {
		super(person);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the journalStepName
	 */
	public String getJournalStepDetailName() {
		return journalStepDetailName;
	}

	/**
	 * @param journalStepName the journalStepName to set
	 */
	public void setJournalStepDetailName(String journalStepDetailName) {
		this.journalStepDetailName = journalStepDetailName;
	}
}
