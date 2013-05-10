package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.TermNote;

public class TermNoteTO extends AbstractAuditableTO<TermNote> {

	private String studentNotes;
	
	private String contactNotes;
	
	private String termCode;
	
	private Boolean isImportant = false;

	public TermNoteTO(TermNote model) {
		super();
		from(model);
	}	

	@Override
	public void from(TermNote model) {
		super.from(model);
		this.setContactNotes(model.getContactNotes());
		this.setIsImportant(model.getIsImportant());
		this.setStudentNotes(model.getStudentNotes());
		this.setTermCode(model.getTermCode());
	}
	
	public String getStudentNotes() {
		return studentNotes;
	}

	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}

	public String getContactNotes() {
		return contactNotes;
	}

	public void setContactNotes(String contactNotes) {
		this.contactNotes = contactNotes;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public Boolean getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}
}
