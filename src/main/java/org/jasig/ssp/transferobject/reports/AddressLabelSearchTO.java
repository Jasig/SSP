package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.transferobject.PersonTO;

/**
 * AddressLabelSearch transfer object
 */
public class AddressLabelSearchTO
		implements Serializable {

	private static final long serialVersionUID = 3118831549819428989L;

	private UUID programStatus;

	private List<UUID> specialServiceGroupIds;

	private List<UUID> referralSourcesIds;

	private String anticipatedStartTerm;

	private Integer anticipatedStartYear;

	private List<UUID> studentTypeIds;

	public PersonTO getCoach() {
		return coach;
	}

	public void setCoach(PersonTO coach) {
		this.coach = coach;
	}

	private Date createDateFrom;

	private Date createDateTo;
	
	private PersonTO coach;
	

	public UUID getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(UUID programStatus) {
		this.programStatus = programStatus;
	}

	public Date getCreateDateFrom() {
		return createDateFrom == null ? null : new Date(
				createDateFrom.getTime());
	}

	public void setCreateDateFrom(final Date createDateFrom) {
		this.createDateFrom = createDateFrom == null ? null : new Date(
				createDateFrom.getTime());
	}

	public Date getCreateDateTo() {
		return createDateTo == null ? null : new Date(createDateTo.getTime());
	}

	public void setCreateDateTo(final Date createDateTo) {
		this.createDateTo = createDateTo == null ? null : new Date(
				createDateTo.getTime());
	}

	public List<UUID> getStudentTypeIds() {
		return studentTypeIds;
	}

	public void setStudentTypeIds(final List<UUID> studentTypeIds) {
		this.studentTypeIds = studentTypeIds;
	}


	public List<UUID> getSpecialServiceGroupIds() {
		return specialServiceGroupIds;
	}

	public void setSpecialServiceGroupIds(
			final List<UUID> specialServiceGroupIds) {
		this.specialServiceGroupIds = specialServiceGroupIds;
	}

	public List<UUID> getReferralSourcesIds() {
		return referralSourcesIds;
	}

	public void setReferralSourcesIds(final List<UUID> referralSourcesIds) {
		this.referralSourcesIds = referralSourcesIds;
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

	public AddressLabelSearchTO(final PersonTO coach, 
			final UUID programStatus,
			final List<UUID> specialServiceGroupId,
			final List<UUID> referralSourcesId,
			final String anticipatedStartTerm,
			final Integer anticipatedStartYear,
			final List<UUID> studentTypeIds,
			final Date createDateFrom, final Date createDateTo) {
		super();
		this.programStatus = programStatus;
		specialServiceGroupIds = specialServiceGroupId;
		referralSourcesIds = referralSourcesId;
		this.anticipatedStartTerm = anticipatedStartTerm;
		this.anticipatedStartYear = anticipatedStartYear;
		this.studentTypeIds = studentTypeIds;
		this.createDateFrom = createDateFrom == null ? null : new Date(
				createDateFrom.getTime());
		this.createDateTo = createDateTo == null ? null : new Date(
				createDateTo.getTime());
	}
}