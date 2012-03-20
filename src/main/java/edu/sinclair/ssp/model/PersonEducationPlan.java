package edu.sinclair.ssp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import edu.sinclair.ssp.model.reference.StudentStatus;

@Entity
@Table(name = "person_education_plan", schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonEducationPlan extends Auditable{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false, updatable = false)
	private Person person;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_status_id", nullable = true, updatable = true)
	private StudentStatus studentStatus;
	
	@Column
	private boolean newOrientationComplete;

	@Column
	private boolean registeredForClasses;

	@Column
	private boolean collegeDegreeForParents;

	@Column
	private boolean specialNeeds;

	@Column(length=2)
	@Size(max = 2)
	private String gradeTypicallyEarned;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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
