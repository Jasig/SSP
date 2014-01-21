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

import org.jasig.ssp.model.PersonEducationPlan;

import com.google.common.collect.Lists;

public class PersonEducationPlanTO
		extends AbstractAuditableTO<PersonEducationPlan>
		implements TransferObject<PersonEducationPlan> {

	private UUID studentStatusId;

	private UUID personId;

	private boolean	collegeDegreeForParents, specialNeeds;
	private String gradeTypicallyEarned;

	public PersonEducationPlanTO() {
		super();
	}

	public PersonEducationPlanTO(final PersonEducationPlan model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonEducationPlan model) {
		super.from(model);

		collegeDegreeForParents = model.isCollegeDegreeForParents();
		specialNeeds = model.isSpecialNeeds();

		if ((model.getStudentStatus() != null)
				&& (model.getStudentStatus().getId() != null)) {
			studentStatusId = model.getStudentStatus().getId();
		}

		gradeTypicallyEarned = model.getGradeTypicallyEarned();
	}

	public static List<PersonEducationPlanTO> toTOList(
			final Collection<PersonEducationPlan> models) {
		final List<PersonEducationPlanTO> tos = Lists.newArrayList();
		for (final PersonEducationPlan model : models) {
			tos.add(new PersonEducationPlanTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getStudentStatusId() {
		return studentStatusId;
	}

	public void setStudentStatusId(final UUID studentStatusId) {
		this.studentStatusId = studentStatusId;
	}

	public boolean isCollegeDegreeForParents() {
		return collegeDegreeForParents;
	}

	public void setCollegeDegreeForParents(final boolean collegeDegreeForParents) {
		this.collegeDegreeForParents = collegeDegreeForParents;
	}

	public boolean isSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(final boolean specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public String getGradeTypicallyEarned() {
		return gradeTypicallyEarned;
	}

	public void setGradeTypicallyEarned(final String gradeTypicallyEarned) {
		this.gradeTypicallyEarned = gradeTypicallyEarned;
	}

}