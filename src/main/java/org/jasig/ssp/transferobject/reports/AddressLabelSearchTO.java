package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

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
	
	
	public List<UUID> getStudentTypeIds() {
		return studentTypeIds;
	}

	public void setStudentTypeIds(List<UUID> studentTypeIds) {
		this.studentTypeIds = studentTypeIds;
	}

	public UUID getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(UUID programStatus) {
		this.programStatus = programStatus;
	}

	public List<UUID> getSpecialServiceGroupIds() {
		return specialServiceGroupIds;
	}

	public void setSpecialServiceGroupIds(List<UUID> specialServiceGroupIds) {
		this.specialServiceGroupIds = specialServiceGroupIds;
	}

	public List<UUID> getReferralSourcesIds() {
		return referralSourcesIds;
	}

	public void setReferralSourcesIds(List<UUID> referralSourcesIds) {
		this.referralSourcesIds = referralSourcesIds;
	}

	public String getAnticipatedStartTerm() {
		return anticipatedStartTerm;
	}

	public void setAnticipatedStartTerm(String anticipatedStartTerm) {
		this.anticipatedStartTerm = anticipatedStartTerm;
	}

	public Integer getAnticipatedStartYear() {
		return anticipatedStartYear;
	}

	public void setAnticipatedStartYear(Integer anticipatedStartYear) {
		this.anticipatedStartYear = anticipatedStartYear;
	}



	public AddressLabelSearchTO(UUID programStatus, List<UUID> specialServiceGroupId,
			List<UUID> referralSourcesId, String anticipatedStartTerm,
			Integer anticipatedStartYear, List<UUID> studentTypeIds) {
		super();
		this.programStatus = programStatus;
		this.specialServiceGroupIds = specialServiceGroupId;
		this.referralSourcesIds = referralSourcesId;
		this.anticipatedStartTerm = anticipatedStartTerm;
		this.anticipatedStartYear = anticipatedStartYear;
		this.studentTypeIds = studentTypeIds;
	}

}
