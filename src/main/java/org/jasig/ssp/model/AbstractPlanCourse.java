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
package org.jasig.ssp.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.reference.Elective;

@MappedSuperclass
public abstract class AbstractPlanCourse<T extends AbstractPlan> extends AbstractAuditable implements Cloneable {

	private static final long serialVersionUID = 4387422660830382586L;

	@Column(length = 50 , nullable = false)
	@Size(max = 50)
	private String termCode;
	
	@Column(length = 50 , nullable = false)
	@Size(max = 50)	
	private String courseCode;
	
	@Column(length = 35 , nullable = false)
	@Size(max = 35)
	private String formattedCourse;

	@Column(length = 200 , nullable = false)
	@Size(max = 200)	
	private String courseTitle;
	
	@Column(length = 2000, nullable = false)
	@Size(max = 2000)	
	private String courseDescription;
	
	@Column(nullable = false)
	private BigDecimal creditHours;
	
	@Column(nullable = false)
	private Boolean isDev = false;

	@Column(nullable = false)
	private Integer orderInTerm;
	
	@Column(length = 2000)
	@Size(max = 2000)
	private String studentNotes;
	
	@Column(length = 2000)
	@Size(max = 2000)
	private String contactNotes;
	
	
	@Column(nullable = false)
	private Boolean isImportant = false;

	@ManyToOne()
	@JoinColumn(name = "elective_id", updatable = false, nullable = true)
	private Elective elective;
	
	@Transient
	private transient boolean isValidInTerm = true;
	
	@Transient
	private transient boolean hasPrerequisites = true;
	
	@Transient
	private transient boolean hasCorequisites = true;
	
	@Transient
	private transient String invalidReasons;
	
	public abstract T getParent();
	
	
	@Override
	protected int hashPrime() {
		return 0;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	protected void cloneCommonFields(AbstractPlanCourse<T> clone) {
		clone.setCourseCode(this.getCourseCode());
		clone.setCourseDescription(this.getCourseDescription());
		clone.setCourseTitle(this.getCourseTitle());
		clone.setCourseDescription(this.getCourseDescription());
		clone.setCreditHours(this.getCreditHours());
		clone.setFormattedCourse(this.getFormattedCourse());
		clone.setIsDev(this.isDev());
		clone.setOrderInTerm(this.getOrderInTerm());
		clone.setTermCode(this.getTermCode());
		clone.setContactNotes(this.getContactNotes());
		clone.setElective(this.getElective());
		clone.setIsImportant(this.getIsImportant());
		clone.setStudentNotes(this.getStudentNotes());
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


	public Boolean getIsImportant() {
		return isImportant;
	}


	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}

	public Elective getElective() {
		return elective;
	}


	public void setElective(Elective elective) {
		this.elective = elective;
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
		this.invalidReasons = invalidReasons;
	}

	
}
