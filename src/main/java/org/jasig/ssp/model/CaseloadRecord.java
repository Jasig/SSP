package org.jasig.ssp.model;

import java.util.Date;
import java.util.UUID;

public class CaseloadRecord {

	private UUID personId;
	private String schoolId, firstName, middleInitial, lastName,
			studentTypeName;
	private Date currentAppointmentStartDate, studentIntakeCompleteDate;
	private int numberOfEarlyAlerts;

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public Date getCurrentAppointmentStartDate() {
		return currentAppointmentStartDate == null ? null : new Date(
				currentAppointmentStartDate.getTime());
	}

	public final void setCurrentAppointmentStartDate(
			final Date currentAppointmentStartDate) {
		this.currentAppointmentStartDate = currentAppointmentStartDate == null ? null
				: new Date(currentAppointmentStartDate.getTime());
	}

	public int getNumberOfEarlyAlerts() {
		return numberOfEarlyAlerts;
	}

	public void setNumberOfEarlyAlerts(final Number numberOfEarlyAlerts) {
		this.numberOfEarlyAlerts = numberOfEarlyAlerts.intValue();
	}

	public boolean isStudentIntakeComplete() {
		return (studentIntakeCompleteDate != null);
	}

	public Date getStudentIntakeCompleteDate() {
		return (studentIntakeCompleteDate == null) ? null : new Date(
				studentIntakeCompleteDate.getTime());
	}

	public void setStudentIntakeCompleteDate(
			final Date studentIntakeCompleteDate) {
		this.studentIntakeCompleteDate = ((studentIntakeCompleteDate == null) ? null
				: new Date(studentIntakeCompleteDate.getTime()));
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
