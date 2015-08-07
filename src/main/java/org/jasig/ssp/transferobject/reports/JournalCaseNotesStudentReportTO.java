package org.jasig.ssp.transferobject.reports;

import org.jasig.ssp.model.Person;

public class JournalCaseNotesStudentReportTO extends BaseStudentReportTO {
	
	public JournalCaseNotesStudentReportTO(){
		super();
	}
	
	public JournalCaseNotesStudentReportTO(BaseStudentReportTO person)
	{
		super(person);
		caseNoteEntries = 0L;
	}
	
	public JournalCaseNotesStudentReportTO(final Person model) {
		super(model);
		setCreatedDate(model.getCreatedDate());
		caseNoteEntries = 0L;
	}

	private static final long serialVersionUID = 7431422387894606843L;
	
	Boolean hasCaseNotes;
	Long caseNoteEntries;
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

}
