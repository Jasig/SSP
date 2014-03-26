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

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyDeserializer;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlySerializer;

/**
 * Caseload record transfer object
 */
public class PersonSearchResult2TO implements TransferObject<PersonSearchResult2> {

	@NotNull
	private UUID personId;

	@NotNull
	private String schoolId;

	@NotNull
	private String firstName;

	private String middleName;

	@NotNull
	private String lastName;

    @JsonSerialize(using = DateOnlySerializer.class)
    @JsonDeserialize(using = DateOnlyDeserializer.class)
    private Date birthDate;

	private String studentTypeName;

	private Date currentAppointmentStartTime;

	private boolean studentIntakeComplete;

	private int numberOfEarlyAlerts;
	
	private int numberEarlyAlertResponsesRequired;
	
	private UUID id;
	
	private UUID coachId;

	private String coachFirstName;
	
	private String coachLastName;
		
	private String photoUrl;

	private String currentProgramStatusName;
					

	public PersonSearchResult2TO(final PersonSearchResult2 record) {
		super();
		from(record);
	}

	/* (non-Javadoc)
	 * @see org.jasig.ssp.transferobject.TransferObject#from(java.lang.Object)
	 */
	@Override
	public final void from(final PersonSearchResult2 model) {
		setFirstName(model.getFirstName());
		setLastName(model.getLastName());
		setMiddleName(model.getMiddleName());
		setPersonId(model.getPersonId());
		setSchoolId(model.getSchoolId());
		setStudentTypeName(model.getStudentTypeName());
        setBirthDate(model.getBirthDate());
		setNumberOfEarlyAlerts(model.getNumberOfEarlyAlerts());
		setStudentIntakeComplete(model.isStudentIntakeComplete());
		setCurrentAppointmentStartTime(model.getCurrentAppointmentStartTime());
		
		setId(model.getId());
		setCoachId(model.getCoachId());
		setCoachFirstName(model.getCoachFirstName());
		setCoachLastName(model.getCoachLastName());
		setPhotoUrl(model.getPhotoUrl());
		setCurrentProgramStatusName(model.getCurrentProgramStatusName());
		setNumberEarlyAlertResponsesRequired(model.getNumberEarlyAlertResponsesRequired());
	}

	public UUID getPersonId() {
		return personId;
	}

	public final void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public final void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public String getFirstName() {
		return firstName;
	}

	public final void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public final void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public final void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getStudentTypeName() {
		return studentTypeName;
	}

	public final void setStudentTypeName(final String studentTypeName) {
		this.studentTypeName = studentTypeName;
	}

    public Date getBirthDate() {
        return birthDate == null ? null : new Date(birthDate.getTime());
    }

    public final void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate == null ? null : new Date(
                birthDate.getTime());
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

	public final void setNumberOfEarlyAlerts(final int numberOfEarlyAlerts) {
		this.numberOfEarlyAlerts = numberOfEarlyAlerts;
	}

	public boolean isStudentIntakeComplete() {
		return studentIntakeComplete;
	}

	public final void setStudentIntakeComplete(
			final boolean studentIntakeComplete) {
		this.studentIntakeComplete = studentIntakeComplete;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public int getNumberEarlyAlertResponsesRequired() {
		return numberEarlyAlertResponsesRequired;
	}

	public void setNumberEarlyAlertResponsesRequired(
			int numberEarlyAlertResponsesRequired) {
		this.numberEarlyAlertResponsesRequired = numberEarlyAlertResponsesRequired;
	}
}