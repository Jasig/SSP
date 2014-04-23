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
package org.jasig.ssp.transferobject.messagetemplate;

import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;

public class CoachPersonLiteMessageTemplateTO extends CoachPersonLiteTO {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1631470044556030291L;
	
	private String secondaryEmailAddress;
	private String cellPhone;
	private String homePhone;
	private String state;
	private String city;
	private String zipCode;
	private String addressLine1;
	private String addressLine2;
	private String schoolId;
	

	public CoachPersonLiteMessageTemplateTO() {
		// TODO Auto-generated constructor stub
	}

	public CoachPersonLiteMessageTemplateTO(UUID id, String firstName,
			String lastName, String primaryEmailAddress, String officeLocation,
			String departmentName, String workPhone, String photoUrl) {
		super(id, firstName, lastName, primaryEmailAddress, officeLocation,
				departmentName, workPhone, photoUrl);
		
	}

	public CoachPersonLiteMessageTemplateTO(Person person) {
		super(person);
		secondaryEmailAddress = person.getSecondaryEmailAddress();
		cellPhone = person.getCellPhone();
		city = person.getCity();
		zipCode = person.getZipCode();
		addressLine1 = person.getAddressLine1();
		addressLine2 = person.getAddressLine2();
		schoolId = person.getSchoolId();
		homePhone = person.getHomePhone();
		state = person.getState();
		
	}

	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}

	public void setSecondaryEmailAddress(String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	@Override
	public boolean equalsAllFields(Object o) {
		if(!super.equalsAllFields(o))
			return false;

		CoachPersonLiteMessageTemplateTO that = (CoachPersonLiteMessageTemplateTO) o;

		if (schoolId != null ? !schoolId.equals(that.schoolId) : that.schoolId != null)
			return false;
		if (addressLine1 != null ? !addressLine1.equals(that.addressLine1) : that.addressLine1 != null)
			return false;
		if (addressLine2 != null ? !addressLine2.equals(that.addressLine2) : that.addressLine2 != null) return false;
		if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null)
			return false;
		if (city != null ? !city.equals(that.city) : that.city != null)
			return false;
		if (cellPhone != null ? !cellPhone.equals(that.cellPhone) : that.cellPhone != null)
			return false;
		if (secondaryEmailAddress != null ? !secondaryEmailAddress.equals(that.secondaryEmailAddress) : that.secondaryEmailAddress != null)
			return false;
		
		if (homePhone != null ? !homePhone.equals(that.homePhone) : that.homePhone != null)
			return false;
		
		if (state != null ? !state.equals(that.state) : that.state != null)
			return false;

		return true;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getFullName(){
		return getFirstName() + " " + getLastName();
	}

}
