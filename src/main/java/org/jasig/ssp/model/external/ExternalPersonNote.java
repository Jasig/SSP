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
	private Date dateNoteTaken;
	
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
	 * @return the dateNoteTaken
	 */
	public Date getDateNoteTaken() {
		return dateNoteTaken;
	}

	/**
	 * @param date the date to set
	 */
	public void setDateNoteTaken(Date dateNoteTaken) {
		this.dateNoteTaken = dateNoteTaken;
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
