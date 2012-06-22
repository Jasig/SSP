package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.UUID;


import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.TransferObject;


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
	private UUID programStatus;
	private List<UUID> specialServiceGroupId;
	private UUID referralSourcesId;
	private String anticipatedStartTerm;
	private Integer anticipatedStartYear;
	private UUID studentTypeId;
	private String registrationTerm;
	private Integer registrationYear;		

	/**
	 * Empty constructor
	 */
	public AddressLabelSearchTO() {
		super();
	}

	public AddressLabelSearchTO(Date intakeDateTo, UUID homeDepartment,
			UUID coachId, UUID programStatus, List<UUID> specialServiceGroupId,
			UUID referralSourcesId, String anticipatedStartTerm,
			Integer anticipatedStartYear, UUID studentTypeId,
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
		this.studentTypeId = studentTypeId;
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

	public UUID getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(UUID programStatus) {
		this.programStatus = programStatus;
	}

	public List<UUID> getSpecialServiceGroupId() {
		return specialServiceGroupId;
	}

	public void setSpecialServiceGroupId(List<UUID> specialServiceGroupId) {
		this.specialServiceGroupId = specialServiceGroupId;
	}

	public UUID getReferralSourcesId() {
		return referralSourcesId;
	}

	public void setReferralSourcesId(UUID referralSourcesId) {
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

	public UUID getStudentTypeId() {
		return studentTypeId;
	}

	public void setStudentTypeId(UUID studentTypeId) {
		this.studentTypeId = studentTypeId;
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
