package org.jasig.ssp.transferobject.external;

import java.util.Date;

import org.jasig.ssp.model.external.ExternalDepartment;
import org.jasig.ssp.model.external.ExternalPersonNote;

public class ExternalPersonNoteTO implements ExternalDataTO<ExternalPersonNote> {

	
	public ExternalPersonNoteTO() {
		super();
	}

	public ExternalPersonNoteTO(final ExternalPersonNote model) {
		super();
		from(model);
	}
	
	@Override
	public void from(ExternalPersonNote model) {
		setAuthor(model.getAuthor());
		setDate(model.getDate());
		setDepartment(model.getDepartment());
		setNoteType(model.getNoteType());
		setNote(model.getNote());
		setCode(model.getCode());
	}
	
	private String code;
	
	private String schoolId;
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the noteType
	 */
	public String getNoteType() {
		return noteType;
	}

	/**
	 * @param noteType the noteType to set
	 */
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	private String noteType;
	
	private String author;
	
	private String department;
	
	private Date date;
	
	private String note;

}
