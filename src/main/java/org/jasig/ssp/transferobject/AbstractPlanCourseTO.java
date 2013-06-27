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

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Transient;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.AbstractPlanCourse;

public class AbstractPlanCourseTO<P extends AbstractPlan ,T extends AbstractPlanCourse<P>> extends
		AbstractAuditableTO<T> implements TransferObject<T> {

	private String termCode;

	private String courseCode;

	private String formattedCourse;

	private String courseTitle;

	private String courseDescription;
	
	private String studentNotes;
	
	private String contactNotes;
	
	private Boolean isImportant;
	
	private Boolean isTranscript;
	
	private UUID electiveId;

	private BigDecimal creditHours;

	private Boolean isDev;

	private Integer orderInTerm;
	
	private String planToOffer;
	
	private transient boolean isValidInTerm = true;
	
	private transient boolean duplicateOfTranscript = true;
	
	private transient boolean hasPrerequisites = true;
	
	private transient boolean hasCorequisites = true;
	
	private transient String invalidReasons = new String();

	/**
	 * Empty constructor.
	 */
	public AbstractPlanCourseTO() {
		super();
	}

	@Override
	public void from(T model) {
		super.from(model);
		this.setCourseCode(model.getCourseCode());
		this.setCourseDescription(model.getCourseDescription());
		this.setCourseTitle(model.getCourseTitle());
		this.setCreditHours(model.getCreditHours());
		this.setFormattedCourse(model.getFormattedCourse());
		this.setIsDev(model.isDev());
		this.setOrderInTerm(model.getOrderInTerm());
		this.setTermCode(model.getTermCode());
		this.setContactNotes(model.getContactNotes());
		this.setStudentNotes(model.getStudentNotes());
		this.setIsImportant(model.getIsImportant());
		if(model.getElective() != null)
			this.setElectiveId(model.getElective().getId());
		
		// validation transients 
		this.setIsValidInTerm(model.isValidInTerm());
		this.setHasPrerequisites(model.getHasPrerequisites());
		this.setHasCorequisites(model.getHasCorequisites());
		this.setInvalidReasons(model.getInvalidReasons());
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getFormattedCourse() {
		return formattedCourse;
	}

	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public BigDecimal getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(BigDecimal creditHours) {
		this.creditHours = creditHours;
	}

	public Boolean isDev() {
		return isDev;
	}

	public void setIsDev(Boolean isDev) {
		this.isDev = isDev;
	}

	public Integer getOrderInTerm() {
		return orderInTerm;
	}

	public void setOrderInTerm(Integer orderInTerm) {
		this.orderInTerm = orderInTerm;
	}

	/**
	 * @return the studentNotes
	 */
	public String getStudentNotes() {
		return studentNotes;
	}

	/**
	 * @param studentNotes the studentNotes to set
	 */
	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}

	/**
	 * @return the contactNotes
	 */
	public String getContactNotes() {
		return contactNotes;
	}

	/**
	 * @param contactNotes the contactNotes to set
	 */
	public void setContactNotes(String contactNotes) {
		this.contactNotes = contactNotes;
	}

	/**
	 * @return the isImportant
	 */
	public Boolean getIsImportant() {
		return isImportant;
	}

	/**
	 * @param isImportant the isImportant to set
	 */
	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}

	/**
	 * @return the isTranscript
	 */
	public Boolean getIsTranscript() {
		return isTranscript;
	}

	/**
	 * @param isTranscript the isTranscript to set
	 */
	public void setIsTranscript(Boolean isTranscript) {
		this.isTranscript = isTranscript;
	}

	/**
	 * @return the electiveId
	 */
	public UUID getElectiveId() {
		return electiveId;
	}

	/**
	 * @param electiveId the electiveId to set
	 */
	public void setElectiveId(UUID electiveId) {
		this.electiveId = electiveId;
	}

	/**
	 * @return the isDev
	 */
	public Boolean getIsDev() {
		return isDev;
	}

	public String getPlanToOffer() {
		return planToOffer;
	}

	public void setPlanToOffer(String planToOffer) {
		this.planToOffer = planToOffer;
	}

	/**
	 * @return the isValidInTerm
	 */
	public boolean isValidInTerm() {
		return isValidInTerm;
	}

	/**
	 * @param isValidInTerm the isValidInTerm to set
	 */
	public void setIsValidInTerm(boolean isValidInTerm) {
		this.isValidInTerm = isValidInTerm;
	}

	
	/**
	 * @return the hasPrerequisites
	 */
	public boolean getHasPrerequisites() {
		return hasPrerequisites;
	}

	/**
	 * @param hasPrerequisites the hasPrerequisites to set
	 */
	public void setHasPrerequisites(boolean hasPrerequisites) {
		this.hasPrerequisites = hasPrerequisites;
	}


	/**
	 * @return the hasCorequisites
	 */
	public boolean getHasCorequisites() {
		return hasCorequisites;
	}

	/**
	 * @param hasCorequisites the hasCorequisites to set
	 */
	public void setHasCorequisites(boolean hasCorequisites) {
		this.hasCorequisites = hasCorequisites;
	}

	/**
	 * @return the invalidReasons
	 */
	public String getInvalidReasons() {
		return invalidReasons;
	}

	/**
	 * @param invalidReasons the invalidReasons to set
	 */
	public void setInvalidReasons(String invalidReasons) {
		if(invalidReasons == null)
			invalidReasons = new String();
		else
			this.invalidReasons = this.invalidReasons + " ";
		this.invalidReasons = this.invalidReasons + invalidReasons;
	}

	/**
	 * @return the duplicateOfTranscript
	 */
	public boolean getDuplicateOfTranscript() {
		return duplicateOfTranscript;
	}

	/**
	 * @param duplicateOfTranscript the duplicateOfTranscript to set
	 */
	public void setDuplicateOfTranscript(boolean duplicateOfTranscript) {
		this.duplicateOfTranscript = duplicateOfTranscript;
	}

}
