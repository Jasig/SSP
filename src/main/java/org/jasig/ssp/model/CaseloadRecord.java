package org.jasig.ssp.model;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * Caseload record
 * 
 * <p>
 * There isn't a 'Caseload' table/model, but this psuedo-model is used by the
 * DAO layer to store aggregate data for use by the Caseload controller and
 * service layers.
 */
public class CaseloadRecord {

	@NotNull
	private UUID personId;

	@NotNull
	private String schoolId;

	@NotNull
	private String firstName;

	private String middleName;

	@NotNull
	private String lastName;

	private String studentTypeName;

	private Date currentAppointmentStartTime;

	private Date studentIntakeCompleteDate;

	private int numberOfEarlyAlerts;

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public Date getCurrentAppointmentStartTime() {
		return currentAppointmentStartTime == null ? null : new Date(
				currentAppointmentStartTime.getTime());
	}

	public final void setCurrentAppointmentStartTime(
			final Date currentAppointmentStartTime) {
		this.currentAppointmentStartTime = currentAppointmentStartTime == null ? null
				: new Date(currentAppointmentStartTime.getTime());
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
		return studentIntakeCompleteDate == null ? null : new Date(
				studentIntakeCompleteDate.getTime());
	}

	public void setStudentIntakeCompleteDate(
			final Date studentIntakeCompleteDate) {
		this.studentIntakeCompleteDate = (studentIntakeCompleteDate == null ? null
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
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