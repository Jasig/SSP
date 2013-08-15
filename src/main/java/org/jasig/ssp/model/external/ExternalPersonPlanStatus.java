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
package org.jasig.ssp.model.external;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Immutable
@Table(name = "v_external_person_planning_status")
public class ExternalPersonPlanStatus extends AbstractExternalData implements
ExternalData, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1933700524986906504L;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private PlanStatus status;
	
	@Column(length = 255)
	@Size(max = 255)
	private String statusReason;

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
