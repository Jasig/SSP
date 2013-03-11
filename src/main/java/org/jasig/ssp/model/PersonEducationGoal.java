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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.EducationGoal;

/**
 * Students may have an Education Goal stored for use in notifications to
 * appropriate users, and for reporting purposes.
 * 
 * Students may have one associated Education Goal instance (one-to-one
 * mapping). Non-student users should never have any education goals associated
 * to them.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonEducationGoal
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = -5687416606848336981L;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "education_goal_id", nullable = true)
	private EducationGoal educationGoal;

	@Column(length = 50)
	@Size(max = 50)
	private String description;

	@Column(length = 50)
	@Size(max = 50)
	private String plannedOccupation;

	@Column(length = 128)
	@Size(max = 128)
	private String militaryBranchDescription;

	@Column
	private Integer howSureAboutMajor;

	@Column(length = 50)
	@Size(max = 50)
	private String plannedMajor;

	@Column
	private Boolean careerDecided;

	@Column
	private Integer howSureAboutOccupation;

	@Column
	private Boolean confidentInAbilities;

	@Column
	private Boolean additionalAcademicProgramInformationNeeded;
	
	@Column(length = 50)
	@Size(max = 50)
	private String courseWorkWeeklyHoursName;
	
	@Column(length = 50)
	@Size(max = 50)
	private String registrationLoadName;
	
	@Column(length = 50)
	@Size(max = 50)
	private String anticipatedGraduationDateTermCode;
	

	public PersonEducationGoal() {
		super();
	}

	public PersonEducationGoal(final EducationGoal educationGoal) {
		super();
		this.educationGoal = educationGoal;
	}

	public EducationGoal getEducationGoal() {
		return educationGoal;
	}

	public void setEducationGoal(final EducationGoal educationGoal) {
		this.educationGoal = educationGoal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getHowSureAboutMajor() {
		return howSureAboutMajor;
	}

	public void setHowSureAboutMajor(final Integer howSureAboutMajor) {
		this.howSureAboutMajor = howSureAboutMajor;
	}

	public String getPlannedOccupation() {
		return plannedOccupation;
	}

	public void setPlannedOccupation(final String plannedOccupation) {
		this.plannedOccupation = plannedOccupation;
	}

	public String getMilitaryBranchDescription() {
		return militaryBranchDescription;
	}

	public void setMilitaryBranchDescription(
			final String militaryBranchDescription) {
		this.militaryBranchDescription = militaryBranchDescription;
	}

	public String getPlannedMajor() {
		return plannedMajor;
	}

	public void setPlannedMajor(final String plannedMajor) {
		this.plannedMajor = plannedMajor;
	}

	public Boolean getCareerDecided() {
		return careerDecided;
	}

	public void setCareerDecided(final Boolean careerDecided) {
		this.careerDecided = careerDecided;
	}

	public Integer getHowSureAboutOccupation() {
		return howSureAboutOccupation;
	}

	public void setHowSureAboutOccupation(final Integer howSureAboutOccupation) {
		this.howSureAboutOccupation = howSureAboutOccupation;
	}

	public Boolean getConfidentInAbilities() {
		return confidentInAbilities;
	}

	public void setConfidentInAbilities(final Boolean confidentInAbilities) {
		this.confidentInAbilities = confidentInAbilities;
	}

	public Boolean getAdditionalAcademicProgramInformationNeeded() {
		return additionalAcademicProgramInformationNeeded;
	}

	public void setAdditionalAcademicProgramInformationNeeded(
			final boolean additionalAcademicProgramInformationNeeded) {
		this.additionalAcademicProgramInformationNeeded = additionalAcademicProgramInformationNeeded;
	}

	/**
	 * @return the courseWorkWeeklyHoursName
	 */
	public String getCourseWorkWeeklyHoursName() {
		return courseWorkWeeklyHoursName;
	}

	/**
	 * @param courseWorkWeeklyHoursName the courseWorkWeeklyHoursName to set
	 */
	public void setCourseWorkWeeklyHoursName(String courseWorkWeeklyHoursName) {
		this.courseWorkWeeklyHoursName = courseWorkWeeklyHoursName;
	}

	/**
	 * @return the registrationLoadName
	 */
	public String getRegistrationLoadName() {
		return registrationLoadName;
	}

	/**
	 * @param registrationLoadName the registrationLoadName to set
	 */
	public void setRegistrationLoadName(String registrationLoadName) {
		this.registrationLoadName = registrationLoadName;
	}

	/**
	 * @return the anticipatedGraduationDateTermCode
	 */
	public String getAnticipatedGraduationDateTermCode() {
		return anticipatedGraduationDateTermCode;
	}

	/**
	 * @param anticipatedGraduationDateTermCode the anticipatedGraduationDateTermCode to set
	 */
	public void setAnticipatedGraduationDateTermCode(
			String anticipatedGraduationDateTermCode) {
		this.anticipatedGraduationDateTermCode = anticipatedGraduationDateTermCode;
	}

	@Override
	protected int hashPrime() {
		return 13;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:17 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonEducationGoal
		result *= hashField("educationGoal", educationGoal);
		result *= hashField("description", description);
		result *= hashField("plannedOccupation", plannedOccupation);
		result *= hashField("militaryBranchDescription",
				militaryBranchDescription);
		result *= hashField("howSureAboutMajor", howSureAboutMajor);
		result *= hashField("plannedMajor", plannedMajor);
		result *= ((careerDecided == null) || !careerDecided) ? 3 : 5;
		result *= hashField("howSureAboutOccupation", howSureAboutOccupation);
		result *= ((confidentInAbilities == null) || !confidentInAbilities) ? 7
				: 11;
		result *= ((additionalAcademicProgramInformationNeeded == null) || !additionalAcademicProgramInformationNeeded) ? 13
				: 17;

		return result;
	}
}