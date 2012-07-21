package org.jasig.ssp.transferobject;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.CaseloadRecord;

/**
 * Caseload record transfer object
 */
public class CaseloadRecordTO implements TransferObject<CaseloadRecord> {

	@NotNull
	private UUID personId;

	@NotNull
	private String schoolId;

	@NotNull
	private String firstName;

	private String middleInitial;

	@NotNull
	private String lastName;

	private String studentTypeName;

	private Date currentAppointmentStartTime;

	private boolean studentIntakeComplete;

	private int numberOfEarlyAlerts;

	public CaseloadRecordTO(final CaseloadRecord record) {
		super();
		from(record);
	}

	@Override
	public final void from(final CaseloadRecord model) {
		setFirstName(model.getFirstName());
		setLastName(model.getLastName());
		setMiddleInitial(model.getMiddleInitial());
		setPersonId(model.getPersonId());
		setSchoolId(model.getSchoolId());
		setStudentTypeName(model.getStudentTypeName());

		setNumberOfEarlyAlerts(model.getNumberOfEarlyAlerts());
		setStudentIntakeComplete(model.isStudentIntakeComplete());
		setCurrentAppointmentStartTime(model.getCurrentAppointmentStartTime());
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