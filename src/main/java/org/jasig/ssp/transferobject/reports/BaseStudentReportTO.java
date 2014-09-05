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
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.jasig.mygps.model.transferobject.TaskReportTO;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.external.ExternalStudentFinancialAid;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.jasig.ssp.model.external.ExternalStudentTranscriptTerm;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptTermService;
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
	private String studentTypeName;
	private String studentTypeCode;
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
	private List<ProgramStatusReportTO> programStatuses = new ArrayList<ProgramStatusReportTO>();
	
	private Map<UUID, String> activeSpecialServiceGroups = new LinkedHashMap<UUID, String>();
	private UUID specialServiceGroupId;
	private String specialServiceGroupName;
	private ObjectStatus specialServiceGroupAssocObjectStatus;

	private Boolean isIlp;
	
	private String actualStartTerm;
	private String academicStanding;
	private Integer actualStartYear;
	private BigDecimal gradePointAverage;
	private BigDecimal lastTermGradePointAverage;
	private String lastTermRegistered;
	private String financialAidStatus;
	
	private Person person;
	
	private String currentProgramStatusCode;
	private UUID currentProgramStatusId;
	
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
		return currentProgramStatusName;
	}

	public void setCurrentProgramStatusName(String currentProgramStatusName) {
		this.currentProgramStatusName = currentProgramStatusName;
	}
	
	public UUID getCurrentProgramStatusId(){
		 return currentProgramStatusId;
	}
	
	public void setCurrentProgramStatusId(UUID currentProgramStatusId){
		this.currentProgramStatusId = currentProgramStatusId;
	}
	
	
	public String getCurrentProgramStatusCode(){
		 return currentProgramStatusCode;
	}
	
	public void setCurrentProgramStatusCode(String code){
		this.currentProgramStatusCode = code;
	}

	private void normalizeProgramStatuses() {
		final ProgramStatusReportTO normalizeTo = findCurrentProgramStatus();
		if ( normalizeTo == null ) {
			setCurrentProgramStatusCode(unexpiredProgramStatusCodeOrNull());
			if ( getCurrentProgramStatusCode() == null ) {
				clearCurrentProgramStatus();
			}
		} else {
			applyCurrentProgramStatus(normalizeTo);
		}
	}

	private ProgramStatusReportTO findCurrentProgramStatus() {
		final List<ProgramStatusReportTO> programStatuses = getProgramStatuses();
		if ( programStatuses == null || programStatuses.isEmpty() ) {
			return null;
		}
		for ( ProgramStatusReportTO status : programStatuses ) {
			if ( status.getExpirationDate() == null ) {
				return status;
			}
		}
		return null;
	}

	private void applyCurrentProgramStatus(ProgramStatusReportTO toApply) {
		setCurrentProgramStatusId(toApply == null ? null : toApply.getId());
		setCurrentProgramStatusName(toApply == null ? null : toApply.getName());
		setCurrentProgramStatusCode(toApply == null ? null : programStatusCodeOrNull(toApply.getId()));
	}

	private void clearCurrentProgramStatus() {
		applyCurrentProgramStatus(null);
	}

	private String unexpiredProgramStatusCodeOrNull() {
		if ( programStatusExpirationDate != null ) {
			return null;
		}
		return programStatusCodeOrNull(getProgramStatusId());
	}

	private String programStatusCodeOrNull(UUID programStatusId) {
		if ( programStatusId == null ) {
			return null;
		}

		String statusIdStr = programStatusId.toString();
		return ProgramStatus.PROGRAM_STATUS_CODES.get(statusIdStr);
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

	private void mergeProgramStatus(ProgramStatusReportTO programStatus) {
		for ( ProgramStatusReportTO alreadyIn : this.getProgramStatuses() ) {
			// Manual equality check here b/c we can't gauge the impact
			// of changing ProgramStatusReportTO.equals()
			if ( alreadyIn.getId().equals(programStatus.getId()) ) {
				if ( alreadyIn.getExpirationDate() == null ) {
					if ( programStatus.getExpirationDate() == null ) {
						return;
					}
				} else if ( alreadyIn.getExpirationDate().equals(programStatus.getExpirationDate()) ) {
					return;
				}
			}
		}
		this.programStatuses.add(programStatus);
	}
	
	private void mergeProgramStatus(BaseStudentReportTO reportTO) {
		ProgramStatusReportTO otherStatus = reportTO.getProgramStatusAsTO();
		if ( otherStatus == null ) {
			return;
		}
		mergeProgramStatus(otherStatus);
	}

	private List<ProgramStatusReportTO> getProgramStatuses(){
		if(programStatuses.isEmpty() && programStatusId != null) {
			programStatuses.add(getProgramStatusAsTO());
		}
		return programStatuses;
	}

	protected ProgramStatusReportTO getProgramStatusAsTO() {
		if ( programStatusId == null ) {
			return null;
		}
		return new ProgramStatusReportTO(
				programStatusName,
				programStatusId,
				programStatusExpirationDate);
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
		setStudentTypeName(person.getStudentType().getName());
		setStudentTypeCode(person.getStudentType().getCode());

		normalize();
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
		setStudentTypeName(person.getStudentTypeName());
		setStudentTypeCode(person.getStudentTypeCode());
		setCurrentProgramStatusCode(person.getCurrentProgramStatusCode());
		setCurrentProgramStatusName(person.getCurrentProgramStatusName());
		setCurrentProgramStatusId(person.getCurrentProgramStatusId());
		setProgramStatusId(person.getProgramStatusId());
		setProgramStatusName(person.getProgramStatusName());
		setProgramStatusExpirationDate(person.getProgramStatusExpirationDate());
		setSpecialServiceGroupId(person.getSpecialServiceGroupId());
		setSpecialServiceGroupName(person.getSpecialServiceGroupName());
		setSpecialServiceGroupAssocObjectStatus(person.getSpecialServiceGroupAssocObjectStatus());
		setRegistrationStatus(person.getRegistrationStatus());

		normalize();
	}

	public void setSpecialServiceGroupId(UUID specialServiceGroupId) {
		this.specialServiceGroupId = specialServiceGroupId;
	}

	public UUID getSpecialServiceGroupId(){
		return specialServiceGroupId;
	}

	public void setSpecialServiceGroupName(String specialServiceGroupName) {
		this.specialServiceGroupName = specialServiceGroupName;
	}
	
	public String getSpecialServiceGroupName(){
		return specialServiceGroupName;
	}

	public void setSpecialServiceGroupAssocObjectStatus(ObjectStatus specialServiceGroupAssocObjectStatus) {
		this.specialServiceGroupAssocObjectStatus = specialServiceGroupAssocObjectStatus;
	}
	
	public ObjectStatus getSpecialServiceGroupAssocObjectStatus(){
		return specialServiceGroupAssocObjectStatus;
	}

	public String getActiveSpecialServiceGroupNames() {
		final Map<UUID, String> activeSsgs = getActiveSpecialServiceGroups();
		if (activeSsgs == null || activeSsgs.isEmpty()) {
			return ""; // legacy
		}
		StringBuilder sb = new StringBuilder();
		final Collection<String> sortedValues = Sets.newTreeSet(activeSsgs.values());
		for ( String name : sortedValues ) {
			addValueToStringList(sb, name);
		}
		return sb.toString();
	}

	private Map<UUID, String> getActiveSpecialServiceGroups() {
		if(activeSpecialServiceGroups.isEmpty() && specialServiceGroupId != null && specialServiceGroupAssocObjectStatus == ObjectStatus.ACTIVE) {
			activeSpecialServiceGroups.put(specialServiceGroupId, specialServiceGroupName);
		}
		return activeSpecialServiceGroups;
	}

	private void mergeSpecialServiceGroup(BaseStudentReportTO reportTO) {
		if ( reportTO.getSpecialServiceGroupId() != null && reportTO.getSpecialServiceGroupAssocObjectStatus() == ObjectStatus.ACTIVE ) {
			getActiveSpecialServiceGroups().put(reportTO.getSpecialServiceGroupId(), reportTO.getSpecialServiceGroupName());
		}
	}

	private void normalizeSpecialServiceGroups() {
		// not strictly necessary. just initializes a private field that would be auto-initialized anyway
		// by the logical public acccessor (getActiveSpecialServiceGroupNames()). But provides for some
		// symmetry w/r/t normalizeProgramStatuses()
		getActiveSpecialServiceGroups();
	}
	
	public void setStudentTypeName(String studentTypeName) {
		this.studentTypeName = studentTypeName;
	}
	
	public String getStudentTypeName(){
		return studentTypeName;
	}

	public void setStudentTypeCode(String studentTypeCode) {
		this.studentTypeCode = studentTypeCode;
	}
	
	public String getStudentTypeCode(){
		return studentTypeCode;
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
				+ ((studentTypeName == null) ? 0 : studentTypeName.hashCode());
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

	protected StringBuilder addValueToStringList(StringBuilder sb, String value) {
		return (sb.length() == 0 ? sb : sb.append(" - ")).append(value);
	}

	protected String addValueToStringList(String str, String value){
		return str + ((str.length() != 0) ? " - ":"") + value;
	}

	public void processDuplicate(BaseStudentReportTO reportTO){
		mergeSpecialServiceGroup(reportTO);
		mergeProgramStatus(reportTO);
		normalize();
	}

	public void normalize() {
		normalizeProgramStatuses();
		normalizeSpecialServiceGroups();
	}
	
	
public void setCurrentRegistrationStatus(RegistrationStatusByTermService registrationStatusByTermService )
			throws ObjectNotFoundException {
	
	   if(getSchoolId() != null && !getSchoolId().isEmpty()){
		   RegistrationStatusByTerm termStatus = registrationStatusByTermService
				.getForCurrentTerm(getSchoolId());
		   if(termStatus != null){
			   setRegistrationStatus(termStatus.getRegisteredCourseCount());
		   }
	   }
}

public void setLastTermGPAAndLastTermRegistered(ExternalStudentTranscriptTermService externalStudentTranscriptTermService, Term currentTerm)
		throws ObjectNotFoundException {

   if(getSchoolId() != null && !getSchoolId().isEmpty()){
	   List<ExternalStudentTranscriptTerm> termTranscripts = externalStudentTranscriptTermService.
			   getExternalStudentTranscriptTermsBySchoolId(getSchoolId());

	   for(ExternalStudentTranscriptTerm transcript:termTranscripts){
		   if(transcript.getCreditHoursAttempted() != null && transcript.getCreditHoursAttempted().floatValue() > 0.0){
			   if(currentTerm == null || !transcript.getTermCode().equals(currentTerm.getCode())){
				   setLastTermGradePointAverage(transcript.getGradePointAverage());
				   setLastTermRegistered(transcript.getTermCode());
			   	   return;
			   }
		   }
	   }
   }
}

public void setStudentTranscript(ExternalStudentTranscriptService externalStudentTranscriptService, 
		ExternalStudentFinancialAidService externalStudentFinancialAidService )
		throws ObjectNotFoundException {
	if(getSchoolId() != null && !getSchoolId().isEmpty()){
		ExternalStudentTranscript transcript = externalStudentTranscriptService
			.getRecordsBySchoolId(getSchoolId());
		if(transcript != null){
			this.setGradePointAverage(transcript.getGradePointAverage());
			this.setAcademicStanding(transcript.getAcademicStanding());
		}
		ExternalStudentFinancialAid financialAid = externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(getSchoolId());
		if(financialAid != null){
			financialAidStatus = financialAid.getCurrentYearFinancialAidAward();
		}
	}
}

/**
 * @return the academicStanding
 */
public String getAcademicStanding() {
	return academicStanding;
}

/**
 * @param academicStanding the academicStanding to set
 */
public void setAcademicStanding(String academicStanding) {
	this.academicStanding = academicStanding;
}

/**
 * @return the financialAidStatus
 */
public String getFinancialAidStatus() {
	return financialAidStatus;
}

/**
 * @param financialAidStatus the financialAidStatus to set
 */
public void setFinancialAidStatus(String financialAidStatus) {
	this.financialAidStatus = financialAidStatus;
}

/**
 * @return the lastTermGradePointAverage
 */
public BigDecimal getLastTermGradePointAverage() {
	return lastTermGradePointAverage;
}

/**
 * @param lastTermGradePointAverage the lastTermGradePointAverage to set
 */
public void setLastTermGradePointAverage(BigDecimal lastTermGradePointAverage) {
	this.lastTermGradePointAverage = lastTermGradePointAverage;
}

/**
 * @return the lastTermRegistered
 */
public String getLastTermRegistered() {
	return lastTermRegistered;
}

/**
 * @param lastTermRegistered the lastTermRegistered to set
 */
public void setLastTermRegistered(String lastTermRegistered) {
	this.lastTermRegistered = lastTermRegistered;
}

}
