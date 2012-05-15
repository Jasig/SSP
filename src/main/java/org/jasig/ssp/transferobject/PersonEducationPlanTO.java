package org.jasig.ssp.transferobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonEducationPlan;

import com.google.common.collect.Lists;

public class PersonEducationPlanTO
		extends AbstractAuditableTO<PersonEducationPlan>
		implements TransferObject<PersonEducationPlan> {

	@NotNull
	private UUID studentStatusId;

	@NotNull
	private UUID personId;

	private boolean newOrientationComplete, registeredForClasses,
			collegeDegreeForParents, specialNeeds;
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

		newOrientationComplete = model.isNewOrientationComplete();
		registeredForClasses = model.isRegisteredForClasses();
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
		for (PersonEducationPlan model : models) {
			tos.add(new PersonEducationPlanTO(model));
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

	public boolean isNewOrientationComplete() {
		return newOrientationComplete;
	}

	public void setNewOrientationComplete(final boolean newOrientationComplete) {
		this.newOrientationComplete = newOrientationComplete;
	}

	public boolean isRegisteredForClasses() {
		return registeredForClasses;
	}

	public void setRegisteredForClasses(final boolean registeredForClasses) {
		this.registeredForClasses = registeredForClasses;
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
