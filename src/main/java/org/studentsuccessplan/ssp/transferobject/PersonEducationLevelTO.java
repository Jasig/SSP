package org.studentsuccessplan.ssp.transferobject;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonEducationLevel;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;

import com.google.common.collect.Lists;

public class PersonEducationLevelTO
		extends AuditableTO<PersonEducationLevel>
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
		fromModel(model);
	}

	@Override
	public final void fromModel(final PersonEducationLevel model) {
		super.fromModel(model);

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

	@Override
	public PersonEducationLevel addToModel(final PersonEducationLevel model) {
		super.addToModel(model);

		model.setDescription(getDescription());

		if (getEducationLevelId() != null) {
			model.setEducationLevel(new EducationLevel(getEducationLevelId()));
		}

		model.setGraduatedYear(getGraduatedYear());
		model.setHighestGradeCompleted(getHighestGradeCompleted());
		model.setLastYearAttended(getLastYearAttended());

		if (getPersonId() != null) {
			model.setPerson(new Person(getPersonId()));
		}

		model.setSchoolName(getSchoolName());

		return model;
	}

	@Override
	public PersonEducationLevel asModel() {
		return addToModel(new PersonEducationLevel());
	}

	public static List<PersonEducationLevelTO> listToTOList(
			final List<PersonEducationLevel> models) {
		final List<PersonEducationLevelTO> tos = Lists.newArrayList();
		for (PersonEducationLevel model : models) {
			tos.add(new PersonEducationLevelTO(model));
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
