package org.jasig.ssp.transferobject.external;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.external.ExternalPersonPlanStatus;
import org.jasig.ssp.model.external.PlanStatus;

public class ExternalPersonPlanStatusTO implements
		ExternalDataTO<ExternalPersonPlanStatus> {

	private String schoolId;

	private PlanStatus status;

	private String statusReason;
	
	@Override
	public void from(ExternalPersonPlanStatus model) {
		this.setSchoolId(model.getSchoolId());
		this.setStatus(model.getStatus());
		this.setStatusReason(model.getStatusReason());		
	}

	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @return the status
	 */
	public PlanStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(PlanStatus status) {
		this.status = status;
	}

	/**
	 * @return the statusReason
	 */
	public String getStatusReason() {
		return statusReason;
	}

	/**
	 * @param statusReason the statusReason to set
	 */
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	
	

}
