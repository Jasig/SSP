package edu.sinclair.ssp.model;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.StudentStatus;

public class StudentEducationPlan {

	private UUID id;
	
	private Student student;
	
	private StudentStatus studentStatus;
	
	private boolean newOrientationComplete;
	
	private boolean registeredForClasses;
	
	private boolean collegeDegreeForParents;
	
	private boolean specialNeeds;
	
	private String gradeTypicallyEarned;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public StudentStatus getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(StudentStatus studentStatus) {
		this.studentStatus = studentStatus;
	}

	public boolean isNewOrientationComplete() {
		return newOrientationComplete;
	}

	public void setNewOrientationComplete(boolean newOrientationComplete) {
		this.newOrientationComplete = newOrientationComplete;
	}

	public boolean isRegisteredForClasses() {
		return registeredForClasses;
	}

	public void setRegisteredForClasses(boolean registeredForClasses) {
		this.registeredForClasses = registeredForClasses;
	}

	public boolean isCollegeDegreeForParents() {
		return collegeDegreeForParents;
	}

	public void setCollegeDegreeForParents(boolean collegeDegreeForParents) {
		this.collegeDegreeForParents = collegeDegreeForParents;
	}

	public boolean isSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(boolean specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public String getGradeTypicallyEarned() {
		return gradeTypicallyEarned;
	}

	public void setGradeTypicallyEarned(String gradeTypicallyEarned) {
		this.gradeTypicallyEarned = gradeTypicallyEarned;
	}

}
