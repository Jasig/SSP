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
package org.jasig.ssp.transferobject.reports;


/**
 * AddressLabelSearch transfer object
 */
public class EarlyAlertStudentResponseOutcomeReportTO  {
	

	private static final long serialVersionUID = 3118831549819428989L;

	private String firstName;
	private String middleName;
	private String lastName;
	private String coachFirstName;
	private String coachMiddleName;
	private String coachLastName;
	private String outcomeName;
	private String coachName;
	private String primaryEmailAddress;
	private String schoolId;
	
	public EarlyAlertStudentResponseOutcomeReportTO()
	{
		super();
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the outcomeName
	 */
	public String getOutcomeName() {
		return outcomeName;
	}
	/**
	 * @param outcomeName the outcomeName to set
	 */
	public void setOutcomeName(String outcomeName) {
		this.outcomeName = outcomeName;
	}
	/**
	 * @return the coachFirstName
	 */
	public String getCoachFirstName() {
		return coachFirstName;
	}
	/**
	 * @param coachFirstName the coachFirstName to set
	 */
	public void setCoachFirstName(String coachFirstName) {
		this.coachFirstName = coachFirstName;
	}
	/**
	 * @return the coachMiddleName
	 */
	public String getCoachMiddleName() {
		return coachMiddleName;
	}
	/**
	 * @param coachMiddleName the coachMiddleName to set
	 */
	public void setCoachMiddleName(String coachMiddleName) {
		this.coachMiddleName = coachMiddleName;
	}
	/**
	 * @return the coachLastName
	 */
	public String getCoachLastName() {
		return coachLastName;
	}
	/**
	 * @param coachLastName the coachLastName to set
	 */
	public void setCoachLastName(String coachLastName) {
		this.coachLastName = coachLastName;
	}
	/**
	 * @return the coachName
	 */
	public String getCoachName(){
		if(coachName == null || coachName.length() <= 0)
			coachName = getCoachFirstName() + " " + getCoachLastName();
		return coachName;
	}
	
	/**
	 * @param coachName the coachName to set
	 */
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	/**
	 * @return the primaryEmail
	 */
	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}
	/**
	 * @param primaryEmail the primaryEmail to set
	 */
	public void setPrimaryEmailAddress(String primaryEmail) {
		this.primaryEmailAddress = primaryEmail;
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
	
}