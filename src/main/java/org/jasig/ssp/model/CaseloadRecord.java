package org.jasig.ssp.model;

import java.util.Date;
import java.util.UUID;

public class CaseloadRecord {

	private UUID personId;
	private String schoolId, firstName, middleInitial, lastName,
			studentTypeName;
	private Date currentAppointmentDate;
	private int numberOfEarlyAlerts;
	private boolean studentIntakeComplete;

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public Date getCurrentAppointmentDate() {
		return currentAppointmentDate == null ? null : new Date(
				currentAppointmentDate.getTime());
	}

	public final void setCurrentAppointmentDate(
			final Date currentAppointmentDate) {
		this.currentAppointmentDate = currentAppointmentDate == null ? null
				: new Date(currentAppointmentDate.getTime());
	}

	public int getNumberOfEarlyAlerts() {
		return numberOfEarlyAlerts;
	}

	public void setNumberOfEarlyAlerts(final int numberOfEarlyAlerts) {
		this.numberOfEarlyAlerts = numberOfEarlyAlerts;
	}

	public boolean isStudentIntakeComplete() {
		return studentIntakeComplete;
	}

	public void setStudentIntakeComplete(final boolean studentIntakeComplete) {
		this.studentIntakeComplete = studentIntakeComplete;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(final String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getStudentTypeName() {
		return studentTypeName;
	}

	public void setStudentTypeName(final String studentTypeName) {
		this.studentTypeName = studentTypeName;
	}

}
