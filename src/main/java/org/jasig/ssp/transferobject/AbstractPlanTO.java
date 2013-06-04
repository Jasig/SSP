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
package org.jasig.ssp.transferobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.AbstractPlanCourse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.TermNote;

public abstract class AbstractPlanTO<T extends AbstractPlan> extends
		AbstractAuditableTO<T> implements TransferObject<T> {

	private String name;

	private String ownerId;
	
	private String ownerName;

	private String contactTitle;
	
	private String contactName;
	
	private String contactPhone;

	private String contactEmail;
	
	private String contactNotes;
	
	private String studentNotes;
	
	private Boolean isFinancialAid = false;

	private Boolean isImportant = false;
	
	private Boolean isF1Visa = false;	
	
	private String academicGoals;
	
	private String academicLink;
	
	private String careerLink;	
	
	private List<TermNoteTO> termNotes = new ArrayList<TermNoteTO>();
	/**
	 * Empty constructor.
	 */
	public AbstractPlanTO() {
		super();
	}

	@Override
	public void from(T model) {
		super.from(model);
		this.setName(model.getName());
		this.setOwnerId(model.getOwner().getId().toString());
		this.setOwnerName(model.getOwner().getFirstName()+" "+model.getOwner().getLastName());
		this.setAcademicGoals(model.getAcademicGoals());
		this.setAcademicLink(model.getAcademicLink());
		this.setCareerLink(model.getCareerLink());
		this.setContactEmail(model.getContactEmail());
		this.setContactName(model.getContactName());
		this.setContactNotes(model.getContactNotes());
		this.setContactPhone(model.getContactPhone());
		this.setContactTitle(model.getContactTitle());
		this.setIsF1Visa(model.getIsF1Visa());
		this.setIsFinancialAid(model.getIsFinancialAid());
		this.setIsImportant(model.getIsImportant());
		this.setStudentNotes(model.getStudentNotes());
		setTrueModifiedDateAndBy(model);
		
	}
	
	private void setTrueModifiedDateAndBy(T model) {
		List<? extends AbstractPlanCourse<?>> courses = model.getCourses();
		Date latestModifiedDate = model.getModifiedDate();
		Person latestModifiedBy = model.getModifiedBy();
		for (AbstractPlanCourse<?> abstractPlanCourse : courses) {
			if(abstractPlanCourse.getModifiedDate().after(latestModifiedDate))
			{
				latestModifiedDate = abstractPlanCourse.getModifiedDate();
				latestModifiedBy = abstractPlanCourse.getModifiedBy();
			}
		}
		List<? extends TermNote> notes = model.getNotes();
		for (TermNote termNote : notes) {
			if(termNote.getModifiedDate().after(latestModifiedDate))
			{
				latestModifiedDate = termNote.getModifiedDate();
				latestModifiedBy = termNote.getModifiedBy();			}
		}
		this.setModifiedBy(new PersonLiteTO(latestModifiedBy));
		this.setModifiedDate(latestModifiedDate);
	}

	public abstract List<? extends AbstractPlanCourseTO<T,? extends AbstractPlanCourse<T>>> getCourses();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactNotes() {
		return contactNotes;
	}

	public void setContactNotes(String contactNotes) {
		this.contactNotes = contactNotes;
	}

	public String getStudentNotes() {
		return studentNotes;
	}

	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}

	public Boolean getIsFinancialAid() {
		return isFinancialAid;
	}

	public void setIsFinancialAid(Boolean isFinancialAid) {
		this.isFinancialAid = isFinancialAid;
	}

	public Boolean getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}

	public Boolean getIsF1Visa() {
		return isF1Visa;
	}

	public void setIsF1Visa(Boolean isF1Visa) {
		this.isF1Visa = isF1Visa;
	}

	public String getAcademicGoals() {
		return academicGoals;
	}

	public void setAcademicGoals(String academicGoals) {
		this.academicGoals = academicGoals;
	}

	public String getAcademicLink() {
		return academicLink;
	}

	public void setAcademicLink(String academicLink) {
		this.academicLink = academicLink;
	}

	public String getCareerLink() {
		return careerLink;
	}

	public void setCareerLink(String careerLink) {
		this.careerLink = careerLink;
	}

	public List<TermNoteTO> getTermNotes() {
		return termNotes;
	}

	public void setTermNotes(List<TermNoteTO> termNotes) {
		this.termNotes = termNotes;
	}

}