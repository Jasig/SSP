package edu.sinclair.ssp.model;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.EducationLevel;

public class StudentEducationLevel {

	private UUID id;
	
	private String description;
	
	private Student student;
	
	private EducationLevel educationLevel;

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

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}
	
	
}
