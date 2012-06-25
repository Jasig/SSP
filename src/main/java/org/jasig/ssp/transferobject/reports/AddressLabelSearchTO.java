package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * AddressLabelSearch transfer object
 */
public class AddressLabelSearchTO
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date intakeDateTo;
	private UUID homeDepartment;
	private UUID coachId;
	private String programStatus;
	private List<UUID> specialServiceGroupId;
	private List<UUID> referralSourcesId;
	private String anticipatedStartTerm;
	private Integer anticipatedStartYear;
	private List<UUID> studentTypeIds;

	public List<UUID> getStudentTypeIds() {
		return studentTypeIds;
	}

	public void setStudentTypeIds(final List<UUID> studentTypeIds) {
		this.studentTypeIds = studentTypeIds;
	}

	private String registrationTerm;

	private Integer registrationYear;

	/**
	 * Empty constructor
	 */
	public AddressLabelSearchTO() {
		super();
	}

	public AddressLabelSearchTO(final Date intakeDateTo,
			final UUID homeDepartment,
			final UUID coachId, final String programStatus,
			final List<UUID> specialServiceGroupId,
			final List<UUID> referralSourcesId,
			final String anticipatedStartTerm,
			final Integer anticipatedStartYear,
			final List<UUID> studentTypeIds,
			final String registrationTerm, final Integer registrationYear) {
		super();
		this.intakeDateTo = intakeDateTo == null ? null : new Date(
				intakeDateTo.getTime());
		this.homeDepartment = homeDepartment;
		this.coachId = coachId;
		this.programStatus = programStatus;
		this.specialServiceGroupId = specialServiceGroupId;
		this.referralSourcesId = referralSourcesId;
		this.anticipatedStartTerm = anticipatedStartTerm;
		this.anticipatedStartYear = anticipatedStartYear;
		this.studentTypeIds = studentTypeIds;
		this.registrationTerm = registrationTerm;
		this.registrationYear = registrationYear;
	}

	public Date getIntakeDateTo() {
		return intakeDateTo == null ? null : new Date(intakeDateTo.getTime());
	}

	public void setIntakeDateTo(final Date intakeDateTo) {
		this.intakeDateTo = intakeDateTo == null ? null : new Date(
				intakeDateTo.getTime());
	}

	public UUID getHomeDepartment() {
		return homeDepartment;
	}

	public void setHomeDepartment(final UUID homeDepartment) {
		this.homeDepartment = homeDepartment;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(final UUID coachId) {
		this.coachId = coachId;
	}

	public String getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(final String programStatus) {
		this.programStatus = programStatus;
	}

	public List<UUID> getSpecialServiceGroupIds() {
		return specialServiceGroupId;
	}

	public void setSpecialServiceGroupIds(final List<UUID> specialServiceGroupId) {
		this.specialServiceGroupId = specialServiceGroupId;
	}

	public List<UUID> getReferralSourcesIds() {
		return referralSourcesId;
	}

	public void setReferralSourcesIds(final List<UUID> referralSourcesId) {
		this.referralSourcesId = referralSourcesId;
	}

	public String getAnticipatedStartTerm() {
		return anticipatedStartTerm;
	}

	public void setAnticipatedStartTerm(final String anticipatedStartTerm) {
		this.anticipatedStartTerm = anticipatedStartTerm;
	}

	public Integer getAnticipatedStartYear() {
		return anticipatedStartYear;
	}

	public void setAnticipatedStartYear(final Integer anticipatedStartYear) {
		this.anticipatedStartYear = anticipatedStartYear;
	}

	public String getRegistrationTerm() {
		return registrationTerm;
	}

	public void setRegistrationTerm(final String registrationTerm) {
		this.registrationTerm = registrationTerm;
	}

	public Integer getRegistrationYear() {
		return registrationYear;
	}

	public void setRegistrationYear(final Integer registrationYear) {
		this.registrationYear = registrationYear;
	}
}