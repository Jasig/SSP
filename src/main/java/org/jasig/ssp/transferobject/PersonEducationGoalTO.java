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

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.PersonEducationGoal;

import com.google.common.collect.Lists;

public class PersonEducationGoalTO
		extends AbstractAuditableTO<PersonEducationGoal>
		implements TransferObject<PersonEducationGoal> {

	private UUID educationGoalId;

	private UUID personId;

	private String description, plannedOccupation, plannedMajor;
	private Integer howSureAboutMajor, howSureAboutOccupation;
	private Boolean additionalAcademicProgramInformationNeeded, careerDecided,
			confidentInAbilities;
	
	private String courseWorkWeeklyHoursName;

	private String registrationLoadName;

	private String anticipatedGraduationDateTermCode;

	public PersonEducationGoalTO() {
		super();
	}

	public PersonEducationGoalTO(final PersonEducationGoal model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonEducationGoal model) {
		super.from(model);

		howSureAboutMajor = model.getHowSureAboutMajor();
		description = model.getDescription();
		plannedOccupation = model.getPlannedOccupation();
		if ((model.getEducationGoal() != null)
				&& (model.getEducationGoal().getId() != null)) {
			educationGoalId = model.getEducationGoal().getId();
		}

		plannedMajor = model.getPlannedMajor();
		careerDecided = model.getCareerDecided();
		howSureAboutOccupation = model.getHowSureAboutOccupation();
		confidentInAbilities = model.getConfidentInAbilities();
		courseWorkWeeklyHoursName = model.getCourseWorkWeeklyHoursName();
		registrationLoadName = model.getRegistrationLoadName();
		anticipatedGraduationDateTermCode = model.getAnticipatedGraduationDateTermCode();
		additionalAcademicProgramInformationNeeded = model
				.getAdditionalAcademicProgramInformationNeeded();
	}

	public static List<PersonEducationGoalTO> toTOList(
			final Collection<PersonEducationGoal> models) {
		final List<PersonEducationGoalTO> tos = Lists.newArrayList();
		for (final PersonEducationGoal model : models) {
			tos.add(new PersonEducationGoalTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getEducationGoalId() {
		return educationGoalId;
	}

	public void setEducationGoalId(final UUID educationGoalId) {
		this.educationGoalId = educationGoalId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getPlannedOccupation() {
		return plannedOccupation;
	}

	public void setPlannedOccupation(final String plannedOccupation) {
		this.plannedOccupation = plannedOccupation;
	}

	public Integer getHowSureAboutMajor() {
		return howSureAboutMajor;
	}

	public void setHowSureAboutMajor(final Integer howSureAboutMajor) {
		this.howSureAboutMajor = howSureAboutMajor;
	}

	public String getPlannedMajor() {
		return plannedMajor;
	}

	public void setPlannedMajor(final String plannedMajor) {
		this.plannedMajor = plannedMajor;
	}

	public Integer getHowSureAboutOccupation() {
		return howSureAboutOccupation;
	}

	public void setHowSureAboutOccupation(final Integer howSureAboutOccupation) {
		this.howSureAboutOccupation = howSureAboutOccupation;
	}

	public Boolean getAdditionalAcademicProgramInformationNeeded() {
		return additionalAcademicProgramInformationNeeded;
	}

	public void setAdditionalAcademicProgramInformationNeeded(
			final Boolean additionalAcademicProgramInformationNeeded) {
		this.additionalAcademicProgramInformationNeeded = additionalAcademicProgramInformationNeeded;
	}

	public Boolean getCareerDecided() {
		return careerDecided;
	}

	public void setCareerDecided(final boolean careerDecided) {
		this.careerDecided = careerDecided;
	}

	public Boolean getConfidentInAbilities() {
		return confidentInAbilities;
	}

	public void setConfidentInAbilities(final Boolean confidentInAbilities) {
		this.confidentInAbilities = confidentInAbilities;
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
}