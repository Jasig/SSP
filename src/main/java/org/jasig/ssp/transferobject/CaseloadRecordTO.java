package org.jasig.ssp.transferobject;

import java.util.Date;
import java.util.UUID;

import org.jasig.ssp.model.CaseloadRecord;

public class CaseloadRecordTO implements TransferObject<CaseloadRecord> {

	private UUID personId;
	private String schoolId, firstName, middleInitial, lastName,
			studentTypeName;
	private Date currentAppointmentDate;
	private int numberOfEarlyAlerts;
	private boolean studentIntakeComplete;

	public CaseloadRecordTO(final CaseloadRecord record) {
		super();
		from(record);
	}

	@Override
	public final void from(final CaseloadRecord model) {
		this.setFirstName(model.getFirstName());
		this.setLastName(model.getLastName());
		this.setMiddleInitial(model.getMiddleInitial());
		this.setPersonId(model.getPersonId());
		this.setSchoolId(model.getSchoolId());
		this.setStudentTypeName(model.getStudentTypeName());

		this.setNumberOfEarlyAlerts(model.getNumberOfEarlyAlerts());
		this.setStudentIntakeComplete(model.isStudentIntakeComplete());
		this.setCurrentAppointmentDate(model.getCurrentAppointmentStartDate());
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public final void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getFirstName() {
		return firstName;
	}

	public final void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public final void setMiddleInitial(final String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public final void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getStudentTypeName() {
		return studentTypeName;
	}

	public final void setStudentTypeName(final String studentTypeName) {
		this.studentTypeName = studentTypeName;
	}

	public Date getCurrentAppointmentDate() {
		return currentAppointmentDate == null ? null : new Date(
				currentAppointmentDate.getTime());
	}

	public final void setCurrentAppointmentDate(final Date currentAppointmentDate) {
		this.currentAppointmentDate = currentAppointmentDate == null ? null
				: new Date(currentAppointmentDate.getTime());
	}

	public int getNumberOfEarlyAlerts() {
		return numberOfEarlyAlerts;
	}

	public final void setNumberOfEarlyAlerts(final int numberOfEarlyAlerts) {
		this.numberOfEarlyAlerts = numberOfEarlyAlerts;
	}

	public boolean isStudentIntakeComplete() {
		return studentIntakeComplete;
	}

	public final void setStudentIntakeComplete(
			final boolean studentIntakeComplete) {
		this.studentIntakeComplete = studentIntakeComplete;
	}
}
