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

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonEducationLevel;

import com.google.common.collect.Lists;

public class PersonEducationLevelTO
		extends AbstractAuditableTO<PersonEducationLevel>
		implements TransferObject<PersonEducationLevel> {

	@NotNull
	private UUID educationLevelId;

	@NotNull
	private UUID personId;

	private Integer graduatedYear, highestGradeCompleted, lastYearAttended;
	private String description, schoolName;

	public PersonEducationLevelTO() {
		super();
	}

	public PersonEducationLevelTO(final PersonEducationLevel model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonEducationLevel model) {
		super.from(model);

		description = model.getDescription();

		if ((model.getEducationLevel() != null)
				&& (model.getEducationLevel().getId() != null)) {
			educationLevelId = model.getEducationLevel().getId();
		}

		graduatedYear = model.getGraduatedYear();
		highestGradeCompleted = model.getHighestGradeCompleted();
		lastYearAttended = model.getLastYearAttended();

		if ((model.getPerson() != null)
				&& (model.getPerson().getId() != null)) {
			personId = model.getPerson().getId();
		}

		schoolName = model.getSchoolName();
	}

	public static List<PersonEducationLevelTO> toTOList(
			final Collection<PersonEducationLevel> models) {
		final List<PersonEducationLevelTO> tos = Lists.newArrayList();
		for (final PersonEducationLevel model : models) {
			tos.add(new PersonEducationLevelTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getEducationLevelId() {
		return educationLevelId;
	}

	public void setEducationLevelId(final UUID educationLevelId) {
		this.educationLevelId = educationLevelId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public Integer getGraduatedYear() {
		return graduatedYear;
	}

	public void setGraduatedYear(final Integer graduatedYear) {
		this.graduatedYear = graduatedYear;
	}

	public Integer getHighestGradeCompleted() {
		return highestGradeCompleted;
	}

	public void setHighestGradeCompleted(final Integer highestGradeCompleted) {
		this.highestGradeCompleted = highestGradeCompleted;
	}

	public Integer getLastYearAttended() {
		return lastYearAttended;
	}

	public void setLastYearAttended(final Integer lastYearAttended) {
		this.lastYearAttended = lastYearAttended;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(final String schoolName) {
		this.schoolName = schoolName;
	}
}