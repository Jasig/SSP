package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Early Alert transfer object
 * 
 * @author jon.adams
 * 
 */
public class AddressLabelSearchTO
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String programStatus;
	private List<UUID> specialServiceGroupIds;
	private List<UUID> referralSourcesIds;
	private String anticipatedStartTerm;
	private String anticipatedStartYear;
	private List<UUID> studentTypeIds;
	
	
	public String getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(String programStatus) {
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

	public String getAnticipatedStartYear() {
		return anticipatedStartYear;
	}

	public void setAnticipatedStartYear(String anticipatedStartYear) {
		this.anticipatedStartYear = anticipatedStartYear;
	}

	public List<UUID> getStudentTypeIds() {
		return studentTypeIds;
	}

	public void setStudentTypeIds(List<UUID> studentTypeIds) {
		this.studentTypeIds = studentTypeIds;
	}



	/**
	 * Empty constructor
	 */
	public AddressLabelSearchTO() {
		super();
	}

	public AddressLabelSearchTO(String programStatus, List<UUID> specialServiceGroupId,
			List<UUID> referralSourcesId, String anticipatedStartTerm,
			String anticipatedStartYear, List<UUID> studentTypeIds) {
		super();
		this.programStatus = programStatus;
		this.specialServiceGroupIds = specialServiceGroupId;
		this.referralSourcesIds = referralSourcesId;
		this.anticipatedStartTerm = anticipatedStartTerm;
		this.anticipatedStartYear = anticipatedStartYear;
		this.studentTypeIds = studentTypeIds;
	}

	

}
