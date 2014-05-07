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

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * Caseload record
 * 
 * <p>
 * There isn't a 'Caseload' table/model, but this psuedo-model is used by the
 * DAO layer to store aggregate data for use by the Caseload controller and
 * service layers.
 */
public class PersonSearchResult2 {

	private UUID personId;

	@NotNull
	private String schoolId;

	@NotNull
	private String firstName;

	private String middleName;

	@NotNull
	private String lastName;

    private Date birthDate;

	private String studentTypeName;

	private Date currentAppointmentStartTime;

	private Date studentIntakeCompleteDate;

	private int numberOfEarlyAlerts = 0;
	
	private int activeAlerts = 0;
	
	private int closedAlerts = 0;
	
	private int numberEarlyAlertResponsesRequired;
	
	private UUID id;
	
	private UUID coachId;

	private String coachFirstName;
	
	private String coachLastName;
		
	private String photoUrl;

	private String currentProgramStatusName;

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		setIds(personId);
	}

	public Date getCurrentAppointmentStartTime() {
		return currentAppointmentStartTime == null ? null : new Date(
				currentAppointmentStartTime.getTime());
	}

	public final void setCurrentAppointmentStartTime(
			final Date currentAppointmentStartTime) {
		this.currentAppointmentStartTime = currentAppointmentStartTime == null ? null
				: new Date(currentAppointmentStartTime.getTime());
	}

	public int getNumberOfEarlyAlerts() {
		return numberOfEarlyAlerts;
	}

	public void setNumberOfEarlyAlerts(final Number numberOfEarlyAlerts) {
		this.numberOfEarlyAlerts = numberOfEarlyAlerts.intValue();
	}

	public int getClosedAlerts() {
		return closedAlerts;
	}

	public void setClosedAlerts(Integer closedAlerts) {
		if(closedAlerts != null){
			this.closedAlerts = closedAlerts;
			this.numberOfEarlyAlerts = closedAlerts + activeAlerts;
		}
	}

	public int getActiveAlerts() {
		return activeAlerts;
	}

	public void setActiveAlerts(Integer activeAlerts) {
		if(activeAlerts != null){
			this.activeAlerts = activeAlerts;
			this.numberOfEarlyAlerts = closedAlerts + activeAlerts;
		}
	}

	public boolean isStudentIntakeComplete() {
		return (studentIntakeCompleteDate != null);
	}

	public Date getStudentIntakeCompleteDate() {
		return studentIntakeCompleteDate == null ? null : new Date(
				studentIntakeCompleteDate.getTime());
	}

	public void setStudentIntakeCompleteDate(
			final Date studentIntakeCompleteDate) {
		this.studentIntakeCompleteDate = (studentIntakeCompleteDate == null ? null
				: new Date(studentIntakeCompleteDate.getTime()));
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
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

	public String getStudentTypeName() {
		return studentTypeName;
	}

	public void setStudentTypeName(final String studentTypeName) {
		this.studentTypeName = studentTypeName;
	}

    public Date getBirthDate() {
        return birthDate == null ? null : new Date(birthDate.getTime());
    }

    public final void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate == null ? null : new Date(
                birthDate.getTime());
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		setIds(id);
	}
	
	private void setIds(UUID id){
		this.personId = id;
		this.id = id;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}

	public String getCoachFirstName() {
		return coachFirstName;
	}

	public void setCoachFirstName(String coachFirstName) {
		this.coachFirstName = coachFirstName;
	}

	public String getCoachLastName() {
		return coachLastName;
	}

	public void setCoachLastName(String coachLastName) {
		this.coachLastName = coachLastName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getCurrentProgramStatusName() {
		return currentProgramStatusName;
	}

	public void setCurrentProgramStatusName(String currentProgramStatusName) {
		this.currentProgramStatusName = currentProgramStatusName;
	}

	public void setNumberOfEarlyAlerts(int numberOfEarlyAlerts) {
		this.numberOfEarlyAlerts = numberOfEarlyAlerts;
	}
	
	public void setNumberEarlyAlertResponsesRequired(final Number numberEarlyAlertResponsesRequired) {
		this.numberEarlyAlertResponsesRequired = numberEarlyAlertResponsesRequired.intValue();
	}


	public int getNumberEarlyAlertResponsesRequired() {
		return numberEarlyAlertResponsesRequired;
	}

	public void setNumberEarlyAlertResponsesRequired(int numberEarlyAlertResponsesRequired) {
		this.numberEarlyAlertResponsesRequired = numberEarlyAlertResponsesRequired;
	}
}