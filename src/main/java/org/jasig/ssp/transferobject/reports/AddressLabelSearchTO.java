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

	public void setStudentTypeIds(List<UUID> studentTypeIds) {
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

	public AddressLabelSearchTO(Date intakeDateTo, UUID homeDepartment,
			UUID coachId, String programStatus, List<UUID> specialServiceGroupId,
			List<UUID> referralSourcesId, String anticipatedStartTerm,
			Integer anticipatedStartYear, List<UUID> studentTypeIds,
			String registrationTerm, Integer registrationYear) {
		super();
		this.intakeDateTo = intakeDateTo;
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
		return intakeDateTo;
	}

	public void setIntakeDateTo(Date intakeDateTo) {
		this.intakeDateTo = intakeDateTo;
	}

	public UUID getHomeDepartment() {
		return homeDepartment;
	}

	public void setHomeDepartment(UUID homeDepartment) {
		this.homeDepartment = homeDepartment;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}

	public String getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(String programStatus) {
		this.programStatus = programStatus;
	}

	public List<UUID> getSpecialServiceGroupIds() {
		return specialServiceGroupId;
	}

	public void setSpecialServiceGroupIds(List<UUID> specialServiceGroupId) {
		this.specialServiceGroupId = specialServiceGroupId;
	}

	public List<UUID> getReferralSourcesIds() {
		return referralSourcesId;
	}

	public void setReferralSourcesIds(List<UUID> referralSourcesId) {
		this.referralSourcesId = referralSourcesId;
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

	public String getRegistrationTerm() {
		return registrationTerm;
	}

	public void setRegistrationTerm(String registrationTerm) {
		this.registrationTerm = registrationTerm;
	}

	public Integer getRegistrationYear() {
		return registrationYear;
	}

	public void setRegistrationYear(Integer registrationYear) {
		this.registrationYear = registrationYear;
	}

}
