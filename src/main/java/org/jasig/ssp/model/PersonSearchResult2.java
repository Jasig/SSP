/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.model;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;


/**
 * Caseload record
 * 
 * <p>
 * There isn't a 'Caseload' table/model, but this psuedo-model is used by the
 * DAO layer to store aggregate data for use by the Caseload controller and
 * service layers.
 *
 * NOTE: When adding things here make sure it makes it to PersonSearchResultFull.java,
 *   and CaseloadCsvWriterHelper.java and CustomizableCaseloadCsvWriterHelper.java
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
	
	private String primaryEmailAddress;

    private Date birthDate;

	private String studentTypeName;

	private Date currentAppointmentStartTime;

	private Date studentIntakeCompleteDate;
	
	private int activeAlerts = 0;
	
	private int closedAlerts = 0;
	
	private int numberEarlyAlertResponsesRequired;
	
	private UUID id;
	
	private UUID coachId;

	private String coachFirstName;
	
	private String coachLastName;
		
	private String photoUrl;

	private String currentProgramStatusName;

    private String actualStartTerm;

	private String campusName;

	private Integer configuredSuccessIndicatorsLow = 0;

	private Integer configuredSuccessIndicatorsMedium = 0;



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

	/**
	 * Alias for {@link #getActiveAlerts()} for legacy reasons.
	 *
	 * @return
	 */
	public int getNumberOfEarlyAlerts() {
		return activeAlerts;
	}

	public int getClosedAlerts() {
		return closedAlerts;
	}

	public void setClosedAlerts(Integer closedAlerts) {
		if(closedAlerts != null){
			this.closedAlerts = closedAlerts;
		}
	}

	public int getActiveAlerts() {
		return activeAlerts;
	}

	public void setActiveAlerts(Integer activeAlerts) {
		if(activeAlerts != null){
			this.activeAlerts = activeAlerts;
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
	
	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(@NotNull final String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
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

	public void setNumberEarlyAlertResponsesRequired(final Number numberEarlyAlertResponsesRequired) {
		if ( numberEarlyAlertResponsesRequired != null ) {
			this.numberEarlyAlertResponsesRequired = numberEarlyAlertResponsesRequired.intValue();
		}
	}


	public int getNumberEarlyAlertResponsesRequired() {
		return numberEarlyAlertResponsesRequired;
	}

    public String getActualStartTerm () {
        return actualStartTerm;
    }

    public void setActualStartTerm (final String actualStartTerm) {
        this.actualStartTerm = actualStartTerm;
    }

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	public Integer getConfiguredSuccessIndicatorsLow() {
		return configuredSuccessIndicatorsLow;
	}

	public void setConfiguredSuccessIndicatorsLow(Integer configuredSuccessIndicatorsLow) {
		if (configuredSuccessIndicatorsLow != null) {
			this.configuredSuccessIndicatorsLow = configuredSuccessIndicatorsLow;
		}
	}

	public Integer getConfiguredSuccessIndicatorsMedium() {
		return configuredSuccessIndicatorsMedium;
	}

	public void setConfiguredSuccessIndicatorsMedium(Integer configuredSuccessIndicatorsMedium) {
		if (configuredSuccessIndicatorsMedium != null) {
			this.configuredSuccessIndicatorsMedium = configuredSuccessIndicatorsMedium;
		}
	}
}