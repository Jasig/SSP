package org.jasig.ssp.transferobject.reports;

import java.util.UUID;

public class StudentChallengesTO {

	UUID id;
	String schoolId;
	String lastName;
	String firstName;
	String studentType;
	String coachName;
	String challengeName;
	String coachLastName;
	String coachFirstName;
	
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getStudentType() {
		return studentType;
	}
	public void setStudentType(String studentType) {
		this.studentType = studentType;
	}
	public String getCoachName(){
		if(coachName == null || coachName.length() <= 0)
			coachName = getCoachFirstName() + " " + getCoachLastName();
		return coachName;
	}
	public String getCoachLastName() {
		return coachLastName;
	}
	public void setCoachLastName(String coachLastName) {
		this.coachLastName = coachLastName;
	}
	public String getCoachFirstName() {
		return coachFirstName;
	}
	public void setCoachFirstName(String coachFirstName) {
		this.coachFirstName = coachFirstName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public String getChallengeName() {
		return challengeName;
	}
	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}
}
