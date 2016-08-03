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


import org.jasig.ssp.model.reference.SpecialServiceGroup;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;


/**
 * Full Search/Caseload record as returned by Directory Person Search
 *  with added fields and methods for CustomExport
 *
 * <p>
 * There isn't a table/model, but this psuedo-model is used by the
 * DAO layer to pull all fields from DirectoryPerson and also
 * by the service layer to aggregate additional external data for the
 * Custom Caseload/Search Export
 */
public class PersonSearchResultFull {

    private UUID personId;

    @NotNull
    private String schoolId;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    @NotNull
    private String username;

    private String primaryEmailAddress;

    private String secondaryEmailAddress;

    private String homePhone;

    private String workPhone;

    private String cellPhone;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String zipCode;

    private String state;

    private String residencyCounty;

    private Date birthDate;

    private String studentTypeName;

    private Date studentIntakeCompleteDate;

    private int activeAlerts = 0;

    private int closedAlerts = 0;

    private int numberEarlyAlertResponsesRequired;

    private UUID id;

    private UUID coachId;

    private String coachSchoolId;

    private String coachFirstName;

    private String coachLastName;

    private String photoUrl;

    private String currentProgramStatusName;

    private String actualStartTerm;

    private Integer actualStartYear;

    private String f1Status;

    private BigDecimal gradePointAverage;

    private BigDecimal localGpa;

    private BigDecimal programGpa;

    private Integer currentRegistrationStatus;

    private BigDecimal creditHoursEarned;

    private String campusName;

    //Below added for CustomExport
    private String sapStatus;

    private BigDecimal financialAidGpa;

    private String academicStanding;

    private String academicProgramCode;

    private String academicProgramName;

    private String intendedProgramAtAdmit;

    private String departmentName;

    private String serviceReasons;

    private String referralSources;

    private String specialServiceGroups;

    private String planTitle;

    private String planProgram;

    private String planCatalogYear;

    private String planOwner; //contact name

    private boolean planRequiredForFinancialAidSap;

    private boolean planRequiredForF1Visa;

    private String lastRevisedBy;

    private Date lastRevisedDate;
    //End added for CustomExport



    //***Begin Directory Person getters and setters ***
    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(final UUID personId) {
        setIds(personId);
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

    public String getHomePhone () {
        return homePhone;
    }

    public void setHomePhone (String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone () {
        return workPhone;
    }

    public void setWorkPhone (String workPhone) {
        this.workPhone = workPhone;
    }

    public String getCellPhone () {
        return cellPhone;
    }

    public void setCellPhone (String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getAddressLine1 () {
        return addressLine1;
    }

    public void setAddressLine1 (String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2 () {
        return addressLine2;
    }

    public void setAddressLine2 (String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getZipCode () {
        return zipCode;
    }

    public void setZipCode (String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState () {
        return state;
    }

    public void setState (String state) {
        this.state = state;
    }

    public String getResidencyCounty () {
        return residencyCounty;
    }

    public void setResidencyCounty (String residencyCounty) {
        this.residencyCounty = residencyCounty;
    }

    public Integer getActualStartYear () {
        return actualStartYear;
    }

    public void setActualStartYear (Integer actualStartYear) {
        this.actualStartYear = actualStartYear;
    }

    public String getF1Status () {
        return f1Status;
    }

    public void setF1Status (String f1Status) {
        this.f1Status = f1Status;
    }

    public BigDecimal getGradePointAverage () {
        return gradePointAverage;
    }

    public void setGradePointAverage (BigDecimal gradePointAverage) {
        this.gradePointAverage = gradePointAverage;
    }

    public BigDecimal getLocalGpa () {
        return localGpa;
    }

    public void setLocalGpa (BigDecimal localGpa) {
        this.localGpa = localGpa;
    }

    public BigDecimal getProgramGpa () {
        return programGpa;
    }

    public void setProgramGpa (BigDecimal programGpa) {
        this.programGpa = programGpa;
    }

    public Integer getCurrentRegistrationStatus () {
        return currentRegistrationStatus;
    }

    public void setCurrentRegistrationStatus (Integer currentRegistrationStatus) {
        this.currentRegistrationStatus = currentRegistrationStatus;
    }

    public BigDecimal getCreditHoursEarned () {
        return creditHoursEarned;
    }

    public void setCreditHoursEarned (BigDecimal creditHoursEarned) {
        this.creditHoursEarned = creditHoursEarned;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getCoachSchoolId () {
        return coachSchoolId;
    }

    public void setCoachSchoolId (String coachSchoolId) {
        this.coachSchoolId = coachSchoolId;
    }

    public String getSecondaryEmailAddress () {
        return secondaryEmailAddress;
    }

    public void setSecondaryEmailAddress (String secondaryEmailAddress) {
        this.secondaryEmailAddress = secondaryEmailAddress;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    //***End Directory Person getters/setters ***



    //***Below added for CustomExportSearch ***

    public String getSpecialServiceGroups () {
        return specialServiceGroups;
    }

    public void setSpecialServiceGroups (String specialServiceGroups) {
        this.specialServiceGroups = specialServiceGroups;
    }

    public String getSapStatus () {
        return sapStatus;
    }

    public void setSapStatus (String sapStatus) {
        this.sapStatus = sapStatus;
    }

    public BigDecimal getFinancialAidGpa () {
        return financialAidGpa;
    }

    public void setFinancialAidGpa (BigDecimal financialAidGpa) {
        this.financialAidGpa = financialAidGpa;
    }

    public String getAcademicStanding () {
        return academicStanding;
    }

    public void setAcademicStanding (String academicStanding) {
        this.academicStanding = academicStanding;
    }

    public String getAcademicProgramCode () {
        return academicProgramCode;
    }

    public void setAcademicProgramCode (String academicProgramCode) {
        this.academicProgramCode = academicProgramCode;
    }

    public String getAcademicProgramName () {
        return academicProgramName;
    }

    public void setAcademicProgramName (String academicProgramName) {
        this.academicProgramName = academicProgramName;
    }

    public String getIntendedProgramAtAdmit () {
        return intendedProgramAtAdmit;
    }

    public void setIntendedProgramAtAdmit (String intendedProgramAtAdmit) {
        this.intendedProgramAtAdmit = intendedProgramAtAdmit;
    }

    public String getDepartmentName () {
        return departmentName;
    }

    public void setDepartmentName (String departmentName) {
        this.departmentName = departmentName;
    }

    public String getServiceReasons () {
        return serviceReasons;
    }

    public void setServiceReasons (String serviceReasons) {
        this.serviceReasons = serviceReasons;
    }

    public String getReferralSources () {
        return referralSources;
    }

    public void setReferralSources (String referralSources) {
        this.referralSources = referralSources;
    }

    public String getPlanTitle () {
        return planTitle;
    }

    public void setPlanTitle (String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPlanProgram () {
        return planProgram;
    }

    public void setPlanProgram (String planProgram) {
        this.planProgram = planProgram;
    }

    public String getPlanCatalogYear () {
        return planCatalogYear;
    }

    public void setPlanCatalogYear (String planCatalogYear) {
        this.planCatalogYear = planCatalogYear;
    }

    public String getPlanOwner () {
        return planOwner;
    }

    public void setPlanOwner (String planOwner) {
        this.planOwner = planOwner;
    }

    public boolean getPlanRequiredForFinancialAidSap () {
        return planRequiredForFinancialAidSap;
    }

    public void setPlanRequiredForFinancialAidSap (boolean planRequiredForFinancialAidSap) {
        this.planRequiredForFinancialAidSap = planRequiredForFinancialAidSap;
    }

    public boolean getPlanRequiredForF1Visa () {
        return planRequiredForF1Visa;
    }

    public void setPlanRequiredForF1Visa (boolean planRequiredForF1Visa) {
        this.planRequiredForF1Visa = planRequiredForF1Visa;
    }

    public String getLastRevisedBy () {
        return lastRevisedBy;
    }

    public void setLastRevisedBy (String lastRevisedBy) {
        this.lastRevisedBy = lastRevisedBy;
    }

    public Date getLastRevisedDate () {
        return lastRevisedDate;
    }

    public void setLastRevisedDate (Date lastRevisedDate) {
        this.lastRevisedDate = lastRevisedDate;
    }

    public void setFinancialAidGpaAndSapStatus (BigDecimal financialAidGpa, String sapStatus) {
        this.financialAidGpa = financialAidGpa;
        this.sapStatus = sapStatus;
    }

    public void setAcademicProgramAndIntended (String academicProgramCode, String academicProgramName, String intendedProgramAtAdmit) {
        this.academicProgramCode = academicProgramCode;
        this.academicProgramName = academicProgramName;
        this.intendedProgramAtAdmit = intendedProgramAtAdmit;
    }

    public void setDepartmentServiceReasonsReferralSourcesAndSpecialServiceGroups (String departmentName,
                                                       Set<PersonServiceReason> serviceReasons,
                                                           Set<PersonReferralSource> referralSources,
                                                               Set<PersonSpecialServiceGroup> specialServiceGroups) {

        final StringBuilder serviceReasonNames = new StringBuilder();
        final StringBuilder referralSourceNames = new StringBuilder();

        for (PersonServiceReason reason : serviceReasons) {
            if (serviceReasonNames.length() > 0) {
                serviceReasonNames.append(", ");
            }
            serviceReasonNames.append(reason.getServiceReason().getName());
        }

        for (PersonReferralSource referral : referralSources) {
            if (referralSourceNames.length() > 0) {
                referralSourceNames.append(", ");
            }
            referralSourceNames.append(referral.getReferralSource().getName());
        }

        this.setSpecialServiceGroupsFromPersonSSGs(specialServiceGroups);

        this.departmentName = departmentName;
        this.serviceReasons = serviceReasonNames.toString();
        this.referralSources = referralSourceNames.toString();
    }

    public void setSpecialServiceGroups(Set<SpecialServiceGroup> specialServiceGroups) {
        final StringBuilder specialServiceGroupNames = new StringBuilder();

        for (SpecialServiceGroup ssg : specialServiceGroups) {
            if (specialServiceGroupNames.length() > 0) {
                specialServiceGroupNames.append(", ");
            }
            specialServiceGroupNames.append(ssg.getName());
        }

        this.specialServiceGroups = specialServiceGroupNames.toString();
    }

    public void setSpecialServiceGroupsFromPersonSSGs(Set<PersonSpecialServiceGroup> personSpecialServiceGroups) {
        final StringBuilder specialServiceGroupNames = new StringBuilder();

        for (PersonSpecialServiceGroup ssg : personSpecialServiceGroups) {
            if (specialServiceGroupNames.length() > 0) {
                specialServiceGroupNames.append(", ");
            }
            specialServiceGroupNames.append(ssg.getSpecialServiceGroup().getName());
        }

        this.specialServiceGroups = specialServiceGroupNames.toString();
    }

    public void setMapData (String planTitle, String planProgram, String planCatalogYear, String planOwner,
                            Boolean planRequiredForFinancialAidSap, Boolean planRequiredForF1Visa,
                                String lastRevisedBy, Date lastRevisedDate) {
        this.planTitle = planTitle;
        this.planProgram = planProgram;
        this.planCatalogYear = planCatalogYear;
        this.planOwner = planOwner;
        this.planRequiredForFinancialAidSap = planRequiredForFinancialAidSap;
        this.planRequiredForF1Visa = planRequiredForF1Visa;
        this.lastRevisedBy = lastRevisedBy;
        this.lastRevisedDate = lastRevisedDate;
    }

    //***End added for CustomExportSearch ***
}
