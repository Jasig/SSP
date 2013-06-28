package org.jasig.ssp.model.external;

import java.io.Serializable;
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
@Table(name = "v_external_person_note")
public class ExternalPersonNote extends AbstractExternalReferenceData implements
		ExternalData, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6439855236652060878L;
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;
	
	@Column(nullable = false, length = 35)
	@NotNull
	@NotEmpty
	@Size(max = 35)
	private String noteType;
	
	@Column(nullable = false, length = 80)
	@NotNull
	@NotEmpty
	@Size(max = 80)
	private String author;
	
	@Column(nullable = false, length = 80)
	@NotNull
	@NotEmpty
	@Size(max = 80)
	private String department;
	
	@NotNull
	@NotEmpty
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date date;
	
	@NotNull
	@NotEmpty
	private String note;

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
	 * @return the note_type
	 */
	public String getNoteType() {
		return noteType;
	}

	/**
	 * @param note_type the note_type to set
	 */
	public void setNote_type(String noteType) {
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

}
