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

import java.util.UUID;

public class PersonSearchResult2 {

	private UUID id;
	
	private UUID coachId;


	private String schoolId;

	private String firstName;

	private String middleName;

	private String lastName;

	private String coachFirstName;
	
	private String coachLastName;
		
	private String photoUrl;

	private String currentProgramStatusName;

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}
	

	/**
	 * @return the coachId
	 */
	public UUID getCoachId() {
		return coachId;
	}

	/**
	 * @param coachId the coachId to set
	 */
	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
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
	 * @return the programStatusName
	 */
	public String getCurrentProgramStatusName() {
		return currentProgramStatusName;
	}

	/**
	 * @param programStatusName the programStatusName to set
	 */
	public void setCurrentProgramStatusName(String programStatusName) {
		this.currentProgramStatusName = programStatusName;
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
