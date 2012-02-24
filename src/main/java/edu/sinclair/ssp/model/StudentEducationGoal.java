package edu.sinclair.ssp.model;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.EducationGoal;

public class StudentEducationGoal {

	private UUID id;
	
	private String description;
	
	private Student student;
	
	private EducationGoal educationGoal;
	
	private int howSureAboutMajor;
	
	private String plannedOccupation;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public EducationGoal getEducationGoal() {
		return educationGoal;
	}

	public void setEducationGoal(EducationGoal educationGoal) {
		this.educationGoal = educationGoal;
	}

	public int getHowSureAboutMajor() {
		return howSureAboutMajor;
	}

	public void setHowSureAboutMajor(int howSureAboutMajor) {
		this.howSureAboutMajor = howSureAboutMajor;
	}

	public String getPlannedOccupation() {
		return plannedOccupation;
	}

	public void setPlannedOccupation(String plannedOccupation) {
		this.plannedOccupation = plannedOccupation;
	}
	
}
