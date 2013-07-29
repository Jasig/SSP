/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
