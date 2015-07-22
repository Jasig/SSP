package org.jasig.ssp.transferobject.reports;

import java.util.Date;

public class JournalCaseNotesStudentReportTO extends BaseStudentReportTO {

	private static final long serialVersionUID = 7431422387894606843L;
	
	Boolean hasCaseNotes;
	Long caseNoteEntries;
	Date createdDate;
	public Boolean getHasCaseNotes() {
		return hasCaseNotes;
	}
	public void setHasCaseNotes(Boolean hasCaseNotes) {
		this.hasCaseNotes = hasCaseNotes;
	}
	public Long getCaseNoteEntries() {
		return caseNoteEntries;
	}
	public void setCaseNoteEntries(Long caseNoteEntries) {
		this.caseNoteEntries = caseNoteEntries;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
