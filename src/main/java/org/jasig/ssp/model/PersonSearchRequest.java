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
package org.jasig.ssp.model;

import java.math.BigDecimal;
import java.util.Date;

import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.PersonSearchController;

/**
 * PersonSearchResult model for use by {@link PersonSearchResultTO} and then
 * {@link PersonSearchController}.
 */
public class PersonSearchRequest {
	
	
	public static final String PLAN_STATUS_ACTIVE = "ACTIVE";
	
	public static final String PLAN_STATUS_INACTIVE = "INACTIVE";

	public static final String MAP_STATUS_ON_PLAN = "ON_PLAN";
	
	public static final String MAP_STATUS_OFF_PLAN = "OFF_PLAN";

	public static final String MAP_STATUS_ON_TRACK_SUBSTITUTION = "ON_TRACK_SUBSTITUTION";
	
	public static final String MAP_STATUS_ON_TRACK_SEQUENCE = "ON_TRACK_SEQUENCE";
	
	public static final String EARLY_ALERT_RESPONSE_RESPONSE_CURRENT = "RESPONSE_CURRENT";
	
	public static final String EARLY_ALERT_RESPONSE_RESPONSE_OVERDUE = "RESPONSE_OVERDUE";
	
	public static final String EARLY_ALERT_RESPONSE_ALL_OPEN_ALERTS = "ALL_OPEN_ALERTS";
	
	public static final String PERSON_TABLE_TYPE_ANYWHERE = "ANYWHERE";
	
	public static final String PERSON_TABLE_TYPE_SSP_ONLY = "SSP_ONLY";
	
	public static final String PERSON_TABLE_TYPE_EXTERNAL_DATA_ONLY = "EXTERNAL_DATA_ONLY";
	
	// id of the person
	private String schoolId;

	private ProgramStatus programStatus;
	
	private SpecialServiceGroup specialServiceGroup;

	private Person coach;

	private String declaredMajor;

	private BigDecimal hoursEarnedMin;
	
	private BigDecimal hoursEarnedMax;

	private BigDecimal gpaEarnedMin;
	
	private BigDecimal gpaEarnedMax;
	
	private Boolean currentlyRegistered;
	
	private String earlyAlertResponseLate;
	
	private String planStatus;
	
	private String sapStatusCode;
	
	private String mapStatus;
	
	private Boolean myCaseload;
	
	private Boolean myPlans;

	private Date birthDate;
	
	private String firstName;
	
	private String lastName;
	
	private String personTableType;
	
	private SortingAndPaging sortAndPage;

	public PersonSearchRequest() {
		super();
	}


	public String getSchoolId() {
		return schoolId;
	}


	public void setSchoolId(String studentId) {
		this.schoolId = studentId;
	}


	public ProgramStatus getProgramStatus() {
		return programStatus;
	}


	public void setProgramStatus(ProgramStatus programStatus) {
		this.programStatus = programStatus;
	}


	public SpecialServiceGroup getSpecialServiceGroup() {
		return specialServiceGroup;
	}


	public void setSpecialServiceGroup(SpecialServiceGroup specialServiceGroup) {
		this.specialServiceGroup = specialServiceGroup;
	}


	public Person getCoach() {
		return coach;
	}


	public void setCoach(Person coach) {
		this.coach = coach;
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


	public String getEarlyAlertResponseLate() {
		return earlyAlertResponseLate;
	}


	public void setEarlyAlertResponseLate(String earlyAlertResponseLate) {
		this.earlyAlertResponseLate = earlyAlertResponseLate;
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


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPersonTableType() {
		return personTableType;
	}


	public void setPersonTableType(String personTableType) {
		this.personTableType = personTableType;
	}


	public SortingAndPaging getSortAndPage() {
		return sortAndPage;
	}


	public void setSortAndPage(SortingAndPaging sortAndPage) {
		this.sortAndPage = sortAndPage;
	}


}