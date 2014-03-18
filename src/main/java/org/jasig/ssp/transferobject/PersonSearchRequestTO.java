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
package org.jasig.ssp.transferobject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.jasig.ssp.model.PersonSearchRequest;

/**
 * PersonSearchResult transfer object
 */
public class PersonSearchRequestTO  implements	TransferObject<PersonSearchRequest> {

	private String studentId;

	private UUID programStatus;

	private UUID coachId;

	private String declaredMajor;

	private BigDecimal hoursEarnedMin;
	
	private BigDecimal hoursEarnedMax;

	private BigDecimal gpaEarnedMin;
	
	private BigDecimal gpaEarnedMax;
	
	private Boolean currentlyRegistered;
	
	private String sapStatusCode;
	
	private String mapStatus;
	
	private String planStatus;
	
	private Boolean myCaseload;
	
	private Boolean myPlans;
	
	private Date birthDate;
	
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public UUID getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(UUID programStatus) {
		this.programStatus = programStatus;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}

	public String getDeclaredMajor() {
		return declaredMajor;
	}

	public void setDeclaredMajor(String declaredMajor) {
		this.declaredMajor = declaredMajor;
	}

	public BigDecimal getHoursEarnedMin() {
		return hoursEarnedMin;
	}

	public void setHoursEarnedMin(BigDecimal hoursEarnedMin) {
		this.hoursEarnedMin = hoursEarnedMin;
	}

	public BigDecimal getHoursEarnedMax() {
		return hoursEarnedMax;
	}

	public void setHoursEarnedMax(BigDecimal hoursEarnedMax) {
		this.hoursEarnedMax = hoursEarnedMax;
	}

	public BigDecimal getGpaEarnedMin() {
		return gpaEarnedMin;
	}

	public void setGpaEarnedMin(BigDecimal gpaEarnedMin) {
		this.gpaEarnedMin = gpaEarnedMin;
	}

	public BigDecimal getGpaEarnedMax() {
		return gpaEarnedMax;
	}

	public void setGpaEarnedMax(BigDecimal gpaEarnedMax) {
		this.gpaEarnedMax = gpaEarnedMax;
	}

	public Boolean getCurrentlyRegistered() {
		return currentlyRegistered;
	}

	public void setCurrentlyRegistered(Boolean currentlyRegistered) {
		this.currentlyRegistered = currentlyRegistered;
	}

	public String getSapStatusCode() {
		return sapStatusCode;
	}

	public void setSapStatusCode(String sapStatusCode) {
		this.sapStatusCode = sapStatusCode;
	}

	public String getMapStatus() {
		return mapStatus;
	}

	public void setMapStatus(String mapStatus) {
		this.mapStatus = mapStatus;
	}

	@Override
	public void from(PersonSearchRequest model) {
		//NO-OP because this TO should only be used for requests.
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public Boolean getMyCaseload() {
		return myCaseload;
	}

	public void setMyCaseload(Boolean myCaseload) {
		this.myCaseload = myCaseload;
	}

	public Boolean getMyPlans() {
		return myPlans;
	}

	public void setMyPlans(Boolean myPlans) {
		this.myPlans = myPlans;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}