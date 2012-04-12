package org.studentsuccessplan.ssp.transferobject;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.PersonEducationGoal;
import org.studentsuccessplan.ssp.model.reference.EducationGoal;

import com.google.common.collect.Lists;

public class PersonEducationGoalTO
		extends AuditableTO<PersonEducationGoal>
		implements TransferObject<PersonEducationGoal> {

	private UUID personId, educationGoalId;
	private String description, plannedOccupation;
	private int howSureAboutMajor;

	public PersonEducationGoalTO() {
		super();
	}

	public PersonEducationGoalTO(PersonEducationGoal model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(PersonEducationGoal model) {
		super.fromModel(model);

		setHowSureAboutMajor(model.getHowSureAboutMajor());
		setDescription(model.getDescription());
		setPlannedOccupation(model.getPlannedOccupation());
		if ((model.getEducationGoal() != null)
				&& (model.getEducationGoal().getId() != null)) {
			setEducationGoalId(model.getEducationGoal().getId());
		}
	}

	@Override
	public PersonEducationGoal addToModel(PersonEducationGoal model) {
		super.addToModel(model);

		model.setHowSureAboutMajor(getHowSureAboutMajor());
		model.setDescription(model.getDescription());
		model.setPlannedOccupation(getPlannedOccupation());
		if (getEducationGoalId() != null) {
			model.setEducationGoal(new EducationGoal(getEducationGoalId()));
		}

		return model;
	}

	@Override
	public PersonEducationGoal asModel() {
		return addToModel(new PersonEducationGoal());
	}

	public static List<PersonEducationGoalTO> listToTOList(
			List<PersonEducationGoal> models) {
		List<PersonEducationGoalTO> tos = Lists.newArrayList();
		for (PersonEducationGoal model : models) {
			tos.add(new PersonEducationGoalTO(model));
		}
		return tos;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public UUID getEducationGoalId() {
		return educationGoalId;
	}

	public void setEducationGoalId(UUID educationGoalId) {
		this.educationGoalId = educationGoalId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlannedOccupation() {
		return plannedOccupation;
	}

	public void setPlannedOccupation(String plannedOccupation) {
		this.plannedOccupation = plannedOccupation;
	}

	public int getHowSureAboutMajor() {
		return howSureAboutMajor;
	}

	public void setHowSureAboutMajor(int howSureAboutMajor) {
		this.howSureAboutMajor = howSureAboutMajor;
	}
}
