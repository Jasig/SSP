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
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Person external data object.
 */
@Entity
@Immutable
@Table(name = "v_external_person")
public class ExternalPerson extends AbstractExternalData implements
		Serializable, ExternalData {

	private static final long serialVersionUID = 7972452789881520560L;

	public static final String BOOLEAN_YES = "Y";
	public static final String BOOLEAN_NO = "N";
	
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String schoolId, firstName, lastName;

	@NotNull
	@NotEmpty
	@Column(length = 50)
	@Size(max = 50)
	private String username;

	@Column(length = 50)
	@Size(max = 50)
	private String middleName;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@Column(length = 100)
	@Size(max = 100)
	private String primaryEmailAddress;

	@Column(length = 50, name = "address_line_1")
	@Size(max = 50)
	private String addressLine1;

	@Column(length = 50, name = "address_line_2")
	@Size(max = 50)
	private String addressLine2;

	@Column(length = 50)
	@Size(max = 50)
	private String city;

	@Column(length = 2)
	@Size(max = 2)
	private String state;

	@Column(length = 10)
	@Size(max = 10)
	private String zipCode;

	@Column(length = 25)
	@Size(max = 25)
	private String homePhone, workPhone, cellPhone;

	@Column(length = 50)
	@Size(max = 50)
	private String officeLocation, officeHours;

    @Column(length = 250)
    @Size(max = 250)
    private String photoUrl;

	@Column(length = 100)
	@Size(max = 100)
	private String departmentName;

	@Column(length = 20)
	@Size(max = 20)
	private String actualStartTerm;

	private Integer actualStartYear;

	@Column(length = 80)
	@Size(max = 80)
	private String maritalStatus, ethnicity;

	@Column(length = 1)
	@Size(max = 1)
	private String gender, isLocal;

	private BigDecimal balanceOwed;

	@Column(length = 50)
	@Size(max = 50)
	private String coachSchoolId;
		
	@Column(nullable = false, length = 1)
	@Size(max = 1)
	private String nonLocalAddress;
	
	@Column(length = 50)
	@Size(max = 50)
	@Nullable
	private String residencyCounty;
	
	@Column(length = 1, name = "f1_status")
	@Size(max = 1)
	@Nullable
	private String f1Status;
	
	@Column(length = 10, name = "student_type_code")
	@Size(max = 10)
	private String studentType;

	@Column(length = 10, name = "race_code")
	@Size(max = 10)
	private String race_code;
	
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return (birthDate == null) ? null : (new Date(birthDate.getTime()));
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = (birthDate == null) ? null : (new Date(
				birthDate.getTime()));
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(final String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(final String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(final String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(final String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(final String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(final String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(final String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(final String officeHours) {
		this.officeHours = officeHours;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(final String departmentName) {
		this.departmentName = departmentName;
	}

	public String getActualStartTerm() {
		return actualStartTerm;
	}

	public void setActualStartTerm(final String actualStartTerm) {
		this.actualStartTerm = actualStartTerm;
	}

	public Integer getActualStartYear() {
		return actualStartYear;
	}

	public void setActualStartYear(final Integer actualStartYear) {
		this.actualStartYear = actualStartYear;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(final String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(final String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getRace() {
		return race_code;
	}
	
	public void setRace(final String race_code) {
		this.race_code = race_code;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(final String isLocal) {
		this.isLocal = isLocal;
	}

	public BigDecimal getBalanceOwed() {
		return balanceOwed;
	}

	public void setBalanceOwed(final BigDecimal balanceOwed) {
		this.balanceOwed = balanceOwed;
	}

	public String getCoachSchoolId() {
		return coachSchoolId;
	}

	public void setCoachSchoolId(final String coachSchoolId) {
		this.coachSchoolId = coachSchoolId;
	}
	
	public String getStudentType() { 
		return studentType;
	}
	
	public void setStudentType(final String studentTypeCode) {
		this.studentType = studentTypeCode;
	}

	/**
	 * @return the residencyCounty
	 */
	public String getResidencyCounty() {
		return residencyCounty;
	}

	/**
	 * @param residencyCounty the residencyCounty to set
	 */
	public void setResidencyCounty(String residencyCounty) {
		this.residencyCounty = residencyCounty;
	}

	/**
	 * @return the f1Status
	 */
	public String getF1Status() {
		return f1Status;
	}

	/**
	 * @param f1Status the f1Status to set
	 */
	public void setF1Status(String f1Status) {
		this.f1Status = f1Status;
	}
	public Boolean getNonLocalAddress() {
		String nonLocalAddressTrimmed = StringUtils.trimToNull(nonLocalAddress);
		if ( nonLocalAddressTrimmed == null ) {
			// non-nullable field so no point in returning null here
			return false;
		}
		return BOOLEAN_YES.equalsIgnoreCase(nonLocalAddressTrimmed);
	}

	public void setNonLocalAddress(Boolean nonLocalAddress) {
		if ( nonLocalAddress == null ) {
			this.nonLocalAddress = BOOLEAN_NO;
		}
		this.nonLocalAddress = nonLocalAddress ? BOOLEAN_YES : BOOLEAN_NO;
	}

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(final String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
