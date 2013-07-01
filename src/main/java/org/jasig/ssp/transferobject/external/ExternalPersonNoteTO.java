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
		setDateNoteTaken(model.getDateNoteTaken());
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
	 * @return the dateNoteTaken
	 */
	public Date getDateNoteTaken() {
		return dateNoteTaken;
	}

	/**
	 * @param date the dateNoteTaken to set
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

	private String noteType;
	
	private String author;
	
	private String department;
	
	private Date dateNoteTaken;
	
	private String note;

}
