package org.studentsuccessplan.ssp.transferobject;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.PersonEducationPlan;

import com.google.common.collect.Lists;

public class PersonEducationPlanTO
		extends AuditableTO<PersonEducationPlan>
		implements TransferObject<PersonEducationPlan> {

	private UUID personId, studentStatusId;
	private boolean newOrientationComplete, registeredForClasses,
			collegeDegreeForParents, specialNeeds;
	private String gradeTypicallyEarned;

	public PersonEducationPlanTO() {
		super();
	}

	public PersonEducationPlanTO(final PersonEducationPlan model) {
		super();
		fromModel(model);
	}

	@Override
	public final void fromModel(final PersonEducationPlan model) {
		super.fromModel(model);

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

	@Override
	public PersonEducationPlan addToModel(final PersonEducationPlan model) {
		super.addToModel(model);

		model.setNewOrientationComplete(isNewOrientationComplete());
		model.setRegisteredForClasses(isRegisteredForClasses());
		model.setCollegeDegreeForParents(isCollegeDegreeForParents());
		model.setSpecialNeeds(isSpecialNeeds());
		model.setGradeTypicallyEarned(getGradeTypicallyEarned());

		return model;
	}

	@Override
	public PersonEducationPlan asModel() {
		return addToModel(new PersonEducationPlan());
	}

	public static List<PersonEducationPlanTO> listToTOList(
			final List<PersonEducationPlan> models) {
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
