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


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.mygps.model.transferobject.TaskReportTO;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;

import com.google.common.collect.Lists;

public class BaseStudentReportTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4549055165582379400L;
	
	private static String ILP = "ILP";
	/**
	 * Construct a transfer object from a related model instance
	 * 
	 * @param model
	 *            Initialize instance from the data in this model
	 */
	public BaseStudentReportTO(final Person model) {	
		this.setPerson(model);

	}
	
	public BaseStudentReportTO()
	{
		
	}
	
	public BaseStudentReportTO(BaseStudentReportTO person)
	{
		setPerson(person);
	}
	
	private String firstName;
	private String lastName;
	private String middleName;
	private String schoolId;
	private String primaryEmailAddress;
	private String secondaryEmailAddress;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode;
    private String cellPhone;
    private String homePhone;
	private String studentType;
	private List<String> studentTypes = new ArrayList<String>();
	private String studentTypeNames;
	private Integer registrationStatus;
	private CoachPersonLiteTO coach;
	private String coachFirstName;
	private String coachLastName;
	private String coachMiddleName;
	private String coachSchoolId;
	private String coachUsername;
	
	private String currentProgramStatusName;
	private Date programStatusExpirationDate;
	private UUID programStatusId;
	private String programStatusName;
	private List<ProgramStatusReportTO> programStatuses = null;
	
	private List<String> specialServiceGroups = new ArrayList<String>();
	private String specialServiceGroup;
	private String specialServiceGroupsName;
	private Boolean isIlp;
	
	private String actualStartTerm;
	private Integer actualStartYear;
	private BigDecimal gradePointAverage;
	
	private Person person;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	private UUID id;



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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}

	public void setSecondaryEmailAddress(String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getCurrentProgramStatusName() {
		currentProgramStatusName = "";
		for(ProgramStatusReportTO programStatus:this.getProgramStatuses()){
			if(programStatus.getExpirationDate() == null && programStatus.getName() != null)
				currentProgramStatusName = addValueToStringList(currentProgramStatusName, 
						programStatus.getName());		
		}
		return currentProgramStatusName;
	}

	public void setCurrentProgramStatusName(String currentProgramStatusName) {
		this.currentProgramStatusName = currentProgramStatusName;
	}
	
	public void setProgramStatusExpirationDate(Date programStatusExpirationDate) {
		this.programStatusExpirationDate = programStatusExpirationDate;
	}
		
	public Date getProgramStatusExpirationDate(){
		return programStatusExpirationDate;
	}
	
	public void setProgramStatusId(UUID programStatusId) {
			this.programStatusId = programStatusId;
	}
		
	public UUID getProgramStatusId(){
		return programStatusId;
	}
	
	public void setProgramStatusName(String programStatusName) {
			this.programStatusName = programStatusName;
	}
		
	public String getProgramStatusName(){
			return programStatusName;
	}
	
	
	public void addProgramStatuses(ProgramStatusReportTO programStatus) {
			if(!this.getProgramStatuses().contains(programStatus))
				this.programStatuses.add(programStatus);
	}
	
	public void addProgramStatuses(List<ProgramStatusReportTO> programStatuses) {
		for(ProgramStatusReportTO programStatus:programStatuses)
			addProgramStatuses(programStatus);
	}
	
	
	public List<ProgramStatusReportTO> getProgramStatuses(){
		if(programStatuses == null)
			programStatuses = Lists.newArrayList(new ProgramStatusReportTO( 
					programStatusName, 
					programStatusId,
					programStatusExpirationDate));
		return programStatuses;
	}

	public Integer getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(Integer registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public CoachPersonLiteTO getCoach() {
		return coach;
	}

	public void setCoach(CoachPersonLiteTO coach) {
		this.coach = coach;
	}
	
	public String getCoachName(){
		if(coach != null)
			return coach.getFirstName() + " " + coach.getLastName();
		return getCoachFirstName() + " " + getCoachLastName();
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

	public String getCoachMiddleName() {
		return coachMiddleName;
	}

	public void setCoachMiddleName(String coachMiddleName) {
		this.coachMiddleName = coachMiddleName;
	}

	public String getCoachSchoolId() {
		return coachSchoolId;
	}

	public void setCoachSchoolId(String coachSchoolId) {
		this.coachSchoolId = coachSchoolId;
	}

	public String getCoachUsername() {
		return coachUsername;
	}

	public void setCoachUsername(String coachUsername) {
		this.coachUsername = coachUsername;
	}

	public Person getPerson() {
		return person;
	}

	
	public void setPerson(Person person) {
		this.person = person;
		setFirstName(person.getFirstName());
		setLastName(person.getLastName());
		setMiddleName(person.getMiddleName());
		setPrimaryEmailAddress(person.getPrimaryEmailAddress());
		setSecondaryEmailAddress(person.getSecondaryEmailAddress());
		setAddressLine1(person.getAddressLine1());
		setAddressLine2(person.getAddressLine2());
		setCity(person.getCity());
		setState(person.getState());
		setZipCode(person.getZipCode());
		setHomePhone(person.getHomePhone());
		setCellPhone(person.getCellPhone());
		setSchoolId(person.getSchoolId());
		setCoach(new CoachPersonLiteTO(person.getCoach()));
		setStudentType(person.getStudentType().getName());
		
		
		if(getStudentType().equals(ILP))
			setIsIlp(true);
		else
			setIsIlp(false);
	}
	
	public void setPerson(BaseStudentReportTO person) {
		setId(person.getId());
		setFirstName(person.getFirstName());
		setLastName(person.getLastName());
		setMiddleName(person.getMiddleName());
		setPrimaryEmailAddress(person.getPrimaryEmailAddress());
		setSecondaryEmailAddress(person.getSecondaryEmailAddress());
		setAddressLine1(person.getAddressLine1());
		setAddressLine2(person.getAddressLine2());
		setCity(person.getCity());
		setState(person.getState());
		setZipCode(person.getZipCode());
		setHomePhone(person.getHomePhone());
		setCellPhone(person.getCellPhone());
		setSchoolId(person.getSchoolId());
		setCoachFirstName(person.getCoachFirstName());
		setCoachMiddleName(person.getCoachMiddleName());
		setCoachLastName(person.getCoachLastName());
		setCoachSchoolId(person.getCoachSchoolId());
		setCoachUsername(person.getCoachUsername());
		setCoach(person.getCoach());
		setStudentType(person.getStudentType());
		setCurrentProgramStatusName(person.getCurrentProgramStatusName());
		setRegistrationStatus(person.getRegistrationStatus());
		setSpecialServiceGroupsName(person.getSpecialServiceGroupsName());
		
		if(getStudentType().equals(ILP))
			setIsIlp(true);
		else
			setIsIlp(false);
	}

		
	public void setSpecialServiceGroup(String specialServiceGroup) {
		this.specialServiceGroup = specialServiceGroup;
		addSpecialServiceGroups(specialServiceGroup);
	}
	
	public String getSpecialServiceGroup(){
		return specialServiceGroup;
	}

	public void addSpecialServiceGroups(List<String> specialServiceGroups) {
		for(String specialServiceGroup:specialServiceGroups)
			if(!this.specialServiceGroups.contains(specialServiceGroup))
				this.specialServiceGroups.add(specialServiceGroup);
	}

	public void addSpecialServiceGroups(String specialServiceGroup) {
		if(!this.specialServiceGroups.contains(specialServiceGroup))
			this.specialServiceGroups.add(specialServiceGroup);
	}
	
	public List<String> getSpecialServiceGroups() {
			return this.specialServiceGroups;
	}
	
	public void setSpecialServiceGroups(List<String> specialServiceGroups) {
		this.specialServiceGroups = specialServiceGroups;
}
	

	public void setSpecialServiceGroupsName(String specialServiceGroupsName) {
		this.specialServiceGroupsName = specialServiceGroupsName;
	}

	public String getSpecialServiceGroupsName() {
		if(specialServiceGroupsName == null || specialServiceGroupsName.length() == 0){
			specialServiceGroupsName = "";
			for(String specialServiceGroup:specialServiceGroups){
				if(specialServiceGroup == null)
					continue;
				specialServiceGroupsName = addValueToStringList(specialServiceGroupsName, specialServiceGroup);
			}
		}
		return specialServiceGroupsName;
	}
	
	public void setStudentType(String studentType) {
		this.studentType = studentType;
		addStudentTypes(studentType);
	}
	
	public String getStudentType(){
		return studentType;
	}

	public void addStudentTypes(List<String> studentTypes) {
		for(String studentType:studentTypes)
			addStudentTypes(studentType);
	}

	public void addStudentTypes(String studentType) {
		if(!this.studentTypes.contains(studentType))
			this.studentTypes.add(studentType);
	}
	
	public List<String> getStudentTypes() {
			return this.studentTypes;
	}
	
	public void setStudentTypes(List<String> studentTypes) {
		this.studentTypes = studentTypes;
	}

	public void setStudentTypeNames(String studentTypeNames) {
		this.studentTypeNames = studentTypeNames;
	}

	public String getStudentTypeNames() {
		if(studentTypeNames == null || studentTypeNames.length() == 0){
			studentTypeNames = "";
			for(String studentType:studentTypes){
				studentTypeNames = addValueToStringList(studentTypeNames, studentType);
			}
		}
		return studentTypeNames;
	}

	public Boolean getIsIlp() {
		return isIlp;
	}

	public void setIsIlp(Boolean isIlp) {
		this.isIlp = isIlp;
	}

	
	/**
	 * @return the actualStartTerm
	 */
	public String getActualStartTerm() {
		return actualStartTerm;
	}

	/**
	 * @param actualStartTerm the actualStartTerm to set
	 */
	public void setActualStartTerm(String actualStartTerm) {
		this.actualStartTerm = actualStartTerm;
	}

	/**
	 * @return the actualStartYear
	 */
	public Integer getActualStartYear() {
		return actualStartYear;
	}

	/**
	 * @param actualStartYear the actualStartYear to set
	 */
	public void setActualStartYear(Integer actualStartYear) {
		this.actualStartYear = actualStartYear;
	}

	/**
	 * @return the gradePointAverage
	 */
	public BigDecimal getGradePointAverage() {
		return gradePointAverage;
	}

	/**
	 * @param gradePointAverage the gradePointAverage to set
	 */
	public void setGradePointAverage(BigDecimal gradePointAverage) {
		this.gradePointAverage = gradePointAverage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((addressLine1 == null) ? 0 : addressLine1.hashCode());
		result = prime * result
				+ ((addressLine2 == null) ? 0 : addressLine2.hashCode());
		result = prime * result
				+ ((cellPhone == null) ? 0 : cellPhone.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((middleName == null) ? 0 : middleName.hashCode());
		result = prime
				* result
				+ ((primaryEmailAddress == null) ? 0 : primaryEmailAddress
						.hashCode());
		result = prime * result
				+ ((schoolId == null) ? 0 : schoolId.hashCode());
		result = prime * result
				+ ((studentType == null) ? 0 : studentType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if (!(BaseStudentReportTO.class.isInstance(obj))
				|| !(getClass().equals(obj.getClass()))) {
			return false;
		}
		return ((BaseStudentReportTO)obj).getId().equals(getId());
	}
	
	

	protected String addValueToStringList(String str, String value){
		return str + ((str.length() != 0) ? " - ":"") + value;
	}
	
	public void processDuplicate(BaseStudentReportTO reportTO){
		addSpecialServiceGroups(reportTO.getSpecialServiceGroups());
		addStudentTypes(reportTO.getStudentTypes());
		addProgramStatuses(reportTO.getProgramStatuses());
	}
	
	
public void setCurrentRegistrationStatus(RegistrationStatusByTermService registrationStatusByTermService )
			throws ObjectNotFoundException {
	
	   if(getSchoolId() != null && !getSchoolId().isEmpty()){
		   RegistrationStatusByTerm termStatus = registrationStatusByTermService
				.getForCurrentTerm(getSchoolId());
		   if(termStatus != null)
			   setRegistrationStatus(termStatus.getRegisteredCourseCount());
	   }
}

public void setStudentTranscript(ExternalStudentTranscriptService externalStudentTranscriptService )
		throws ObjectNotFoundException {
	if(getSchoolId() != null && !getSchoolId().isEmpty()){
		ExternalStudentTranscript transcript = externalStudentTranscriptService
			.getRecordsBySchoolId(getSchoolId());
		if(transcript != null)
			this.setGradePointAverage(transcript.getGradePointAverage());
	}
}

}
