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
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * PersonSearchResult transfer object
 */
public class PersonSearchRequestTO  implements	TransferObject<PersonSearchRequest> {

	private String schoolId;
	
	private String firstName;
	
	private String lastName;

	private UUID programStatus;
	
	private UUID specialServiceGroup;

	private UUID coachId;

	private String declaredMajor;

	private BigDecimal hoursEarnedMin;
	
	private BigDecimal hoursEarnedMax;

	private BigDecimal gpaEarnedMin;
	
	private BigDecimal gpaEarnedMax;
	
	private Boolean currentlyRegistered;
	
	private String earlyAlertResponseLate;
	
	private String sapStatusCode;
	
	private String mapStatus;
	
	private String planStatus;
	
	private Boolean myCaseload;
	
	private Boolean myPlans;
	
	private Boolean myWatchList;
	
	private Date birthDate;

    private String actualStartTerm;
	
	private String personTableType;
	
	private SortingAndPaging sortAndPage;
	
	
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
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

	public UUID getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(UUID programStatus) {
		this.programStatus = programStatus;
	}

	public UUID getSpecialServiceGroup() {
		return specialServiceGroup;
	}

	public void setSpecialServiceGroup(UUID specialServiceGroup) {
		this.specialServiceGroup = specialServiceGroup;
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

	public Boolean getMyWatchList() {
		return myWatchList;
	}

	public void setMyWatchList(Boolean myWatchList) {
		this.myWatchList = myWatchList;
	}

        public String getActualStartTerm () {
                return actualStartTerm;
        }

        public void setActualStartTerm (final String actualStartTerm) {
                this.actualStartTerm = actualStartTerm;
        }
}
