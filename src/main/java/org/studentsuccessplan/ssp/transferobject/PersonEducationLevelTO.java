package org.studentsuccessplan.ssp.transferobject;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonEducationLevel;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;

public class PersonEducationLevelTO extends AuditableTO<PersonEducationLevel> 
		implements TransferObject<PersonEducationLevel> {

	private UUID educationLevelId, personId;
	private Integer graduatedYear, highestGradeCompleted, lastYearAttended;
	private String description, schoolName;

	
	public PersonEducationLevelTO() {
		super();
	}

	public PersonEducationLevelTO(PersonEducationLevel model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(PersonEducationLevel model) {
		super.fromModel(model);

		setDescription(model.getDescription());
		
		if (model.getEducationLevel() != null 
				&& model.getEducationLevel().getId() != null) {
			setEducationLevelId(model.getEducationLevel().getId());
		}
		
		setGraduatedYear(model.getGraduatedYear());
		setHighestGradeCompleted(model.getHighestGradeCompleted());
		setLastYearAttended(model.getLastYearAttended());

		if (model.getPerson() != null
				&& model.getPerson().getId() != null) {
			setPersonId(model.getPerson().getId());
		}
		
		setSchoolName(model.getSchoolName());
	}

	@Override
	public PersonEducationLevel pushAttributesToModel(PersonEducationLevel model) {
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
		return pushAttributesToModel(new PersonEducationLevel());
	}

	public UUID getEducationLevelId() {
		return educationLevelId;
	}

	public void setEducationLevelId(UUID educationLevelId) {
		this.educationLevelId = educationLevelId;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public Integer getGraduatedYear() {
		return graduatedYear;
	}

	public void setGraduatedYear(Integer graduatedYear) {
		this.graduatedYear = graduatedYear;
	}

	public Integer getHighestGradeCompleted() {
		return highestGradeCompleted;
	}

	public void setHighestGradeCompleted(Integer highestGradeCompleted) {
		this.highestGradeCompleted = highestGradeCompleted;
	}

	public Integer getLastYearAttended() {
		return lastYearAttended;
	}

	public void setLastYearAttended(Integer lastYearAttended) {
		this.lastYearAttended = lastYearAttended;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
}
