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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDisabilityAgency;
import org.jasig.ssp.model.PersonDisabilityType;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.jasig.ssp.model.reference.DisabilityType;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.PersonTO;

public class DisabilityServicesReportTO extends BaseStudentReportTO {
	
	final private static String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

	public DisabilityServicesReportTO(Person model, Boolean ilp,
			String disabilityCode, String agencyContact, String sspStatus,
			String odsReason, Date odsRegistrationDate, String interpreter,
			String assignmentDate, String major,
			String veteranStatus, String ethnicity, String race) {
		super(model);
		this.disabilityTypesName = disabilityCode;
		this.agencyContacts = agencyContact;
		this.odsReason = odsReason;
		this.odsRegistrationDate = odsRegistrationDate;
		this.assignmentDates = assignmentDate;
		this.major = major;
		this.veteranStatus = veteranStatus;
		this.ethnicity = ethnicity;
		this.race = race;
	}
	
	
	
	public DisabilityServicesReportTO()
	{
		
	}
	
	private String disabilityType;
	private List<String> disabilityTypes = new ArrayList<String>();
	private String disabilityTypesName;
	private String agencyContacts;
	private String odsStatus;
	private String odsReason;
	private Date odsRegistrationDate;

	private String assignmentDates;
	private String major;
	private String veteranStatus;
	private String ethnicity;
	private String race;
	private List<String> disabilityAgenciesName = new ArrayList<String>();
	private List<Date> disabilityAgenciesCreatedDate = new ArrayList<Date>();
	private String disabilityAgencyName = null;
	private Date disabilityAgencyCreatedDate = null;
	

	private Boolean noDocumentation = false;
	private Boolean inadequateDocumentation = false;
	private Boolean noDisability = false;
	private Boolean noSpecialEd = false;
	
	
	public String getDisabilityAgencyName() {
		return disabilityAgencyName;
	}

	public void setDisabilityAgencyName(String disabilityAgencyName) {
		this.disabilityAgencyName = disabilityAgencyName;
		addDisabilityAgenciesName(disabilityAgencyName);
	}

	public List<String> getDisabilityAgenciesName() {
		return disabilityAgenciesName;
	}

	public void addDisabilityAgenciesName(List<String> disabilityAgenciesName) {
		for(String disabilityAgencyName:disabilityAgenciesName)
			addDisabilityAgenciesName(disabilityAgencyName);
	}

	public void addDisabilityAgenciesName(String disabilityAgenciesName) {
		if(disabilityAgenciesName == null || disabilityAgenciesName.isEmpty())
			return;
		if(!this.disabilityAgenciesName.contains(disabilityAgenciesName))
			this.disabilityAgenciesName.add(disabilityAgenciesName);
	}

	public String getAgencyContacts() {
		if(agencyContacts == null || agencyContacts.length() == 0){
			agencyContacts = "";
			if(disabilityAgenciesName == null || disabilityAgenciesName.size() == 0)
				return agencyContacts;
			for(String disabilityAgencyName:disabilityAgenciesName){
				agencyContacts = addValueToStringList(agencyContacts, disabilityAgencyName);
			}
		}
		return agencyContacts;
	}

	public void setAgencyContacts(String agencyContacts) {
		this.agencyContacts = agencyContacts;
	}
	
	public Date getDisabilityAgencyCreatedDate() {
		return disabilityAgencyCreatedDate;
	}

	public void setDisabilityAgencyCreatedDate(Date disabilityAgencyCreatedDate) {
		addDisabilityAgenciesCreatedDate(disabilityAgencyCreatedDate);
		this.disabilityAgencyCreatedDate = disabilityAgencyCreatedDate;
	}
	
	public List<Date> getDisabilityAgenciesCreatedDate() {
		return disabilityAgenciesCreatedDate;
	}
	
	public void addDisabilityAgenciesCreatedDate(List<Date> disabilityAgenciesCreatedDate) {
		for(Date disabilityAgencyCreatedDate:disabilityAgenciesCreatedDate)
			addDisabilityAgenciesCreatedDate(disabilityAgencyCreatedDate);
	}

	public void addDisabilityAgenciesCreatedDate(Date disabilityAgenciesCreatedDate) {	
		if(disabilityAgenciesCreatedDate == null)
			return;
		if(!this.disabilityAgenciesCreatedDate.contains(disabilityAgenciesCreatedDate))
			this.disabilityAgenciesCreatedDate.add(disabilityAgenciesCreatedDate);
	}
	
	public void setAssignmentDates(String assignmentDate) {
		this.assignmentDates = assignmentDate;
	}

	public String getAssignmentDates() {
		if(assignmentDates == null || assignmentDates.length() == 0){
				assignmentDates = "";
			if(disabilityAgenciesCreatedDate == null || disabilityAgenciesCreatedDate.size() == 0)
				return assignmentDates;
			for(Date disabilityAgencyCreatedDate:disabilityAgenciesCreatedDate)
				assignmentDates = addValueToStringList(assignmentDates, dateFormatter().format(disabilityAgencyCreatedDate));
			
		}
		return assignmentDates;
	}


	public String getOdsStatus() {
		return odsStatus;
	}

	public void setOdsStatus(String odsStatus) {
		this.odsStatus = odsStatus;
	}

	public String getOdsReason() {
		odsReason = "";
		if(noDocumentation)
			odsReason = "None ";
		if(inadequateDocumentation) 
			odsReason = odsReason + "I ";
		if(noDisability) 
			odsReason = odsReason + "NA ";
		if(noSpecialEd) 
			odsReason = odsReason + "NSE";
		return odsReason.trim();
	}

	public void setOdsReason(String odsReason) {
		this.odsReason = odsReason;
	}
	
	private String odsRegistrationDateString;
	
	public void setOdsRegistrationDateString(String odsRegistrationDateString) {
		this.odsRegistrationDateString = odsRegistrationDateString;
	}
	
	public String getOdsRegistrationDateString() {
		
		Date date = getOdsRegistrationDate();
		if(date == null)
			odsRegistrationDateString = "";
		else
			odsRegistrationDateString = dateFormatter().format(date);
		return odsRegistrationDateString;
	}


	public Date getOdsRegistrationDate() {
		return odsRegistrationDate;
	}

	public void setOdsRegistrationDate(Date odsRegistrationDate) {
		this.odsRegistrationDate = odsRegistrationDate;
	}

	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getVeteranStatus() {
		return veteranStatus;
	}

	public void setVeteranStatus(String verteranStatuss) {
		this.veteranStatus = verteranStatuss;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	
	public String getRace() {
		return race;
	}
	
	public void setRace(String race) {
		this.race = race;
	}
	
	public List<String> getDisabilityTypes() {
		return disabilityTypes;
	}

	public void setDisabilityTypes(List<String> disabilityTypes) {
		this.disabilityTypes = disabilityTypes;
	}
	
	public String getDisabilityType() {
		return disabilityType;
	}

	public void setDisabilityType(String disabilityType) {
		this.disabilityType = disabilityType;
		addDisabilityTypes(disabilityType);
	}

	public void addDisabilityTypes(List<String> disabilityTypes) {
		for(String disabilityType:disabilityTypes)
			addDisabilityTypes(disabilityType);
	}

	public void addDisabilityTypes(String disabilityType) {
		if(disabilityType == null)
			return;
		if(!this.disabilityTypes.contains(disabilityType))
			this.disabilityTypes.add(disabilityType);
	}
	
	public void setdisabilityTypesName(String disabilityTypesName) {
		this.disabilityTypesName = disabilityTypesName;
	}

	public String getdisabilityTypesName() {
		
		if(disabilityTypesName == null || disabilityTypesName.length() == 0){
			disabilityTypesName = "";
			if(disabilityTypes == null || disabilityTypes.size() == 0)
				return disabilityTypesName;
			for(String disabilityType:disabilityTypes){
				disabilityTypesName = addValueToStringList(disabilityTypesName, disabilityType);
			}
		}
		return disabilityTypesName;
	}
	

	/**
	 * @return the noDocumentation
	 */
	public Boolean getNoDocumentation() {
		return noDocumentation;
	}



	/**
	 * @param noDocumentation the noDocumentation to set
	 */
	public void setNoDocumentation(Boolean noDocumentation) {
		this.noDocumentation = noDocumentation;
	}



	/**
	 * @return the inadequateDocumentation
	 */
	public Boolean getInadequateDocumentation() {
		return inadequateDocumentation;
	}



	/**
	 * @param inadequateDocumentation the inadequateDocumentation to set
	 */
	public void setInadequateDocumentation(Boolean inadequateDocumentation) {
		this.inadequateDocumentation = inadequateDocumentation;
	}



	/**
	 * @return the noDisability
	 */
	public Boolean getNoDisability() {
		return noDisability;
	}



	/**
	 * @param noDisability the noDisability to set
	 */
	public void setNoDisability(Boolean noDisability) {
		this.noDisability = noDisability;
	}



	/**
	 * @return the noSpecialEd
	 */
	public Boolean getNoSpecialEd() {
		return noSpecialEd;
	}



	/**
	 * @param noSpecialEd the noSpecialEd to set
	 */
	public void setNoSpecialEd(Boolean noSpecialEd) {
		this.noSpecialEd = noSpecialEd;
	}
	
	public void setPerson(Person person) {
		super.setPerson(person);
		if(person.getDemographics() != null){
			if(person.getDemographics().getEthnicity() != null)
				setEthnicity(person.getDemographics().getEthnicity().getName());
			if(person.getDemographics().getVeteranStatus() != null)
				setVeteranStatus(person.getDemographics().getVeteranStatus().getName());
			if(person.getDemographics().getRace() != null)
				setRace(person.getDemographics().getRace().getName());
		}
		
		StringBuffer disabilityAgents = new StringBuffer("");
		StringBuffer disabilityAssignmentDates = new StringBuffer("");

		for(PersonDisabilityAgency disabilityAgency:person.getDisabilityAgencies())
		{
			disabilityAgents.append(disabilityAgency.getDisabilityAgency().getName() + " ");
			disabilityAssignmentDates.append(dateFormatter().format(disabilityAgency.getCreatedDate()) + " ");
		}
		setAgencyContacts(disabilityAgents.toString());
		setAssignmentDates(disabilityAssignmentDates.toString());
		
		StringBuffer disabilityTypes = new StringBuffer("");
		for(PersonDisabilityType disabilityType:person.getDisabilityTypes())
		{
			disabilityTypes.append(disabilityType.getDisabilityType().getName() + " ");
		}
		setdisabilityTypesName(disabilityTypes.toString());

		if(person.getCurrentRegistrationStatus() != null)
			setRegistrationStatus(person.getCurrentRegistrationStatus().getRegisteredCourseCount());
		
		if(person.getEducationGoal() != null && person.getEducationGoal().getPlannedMajor() != null)
			setMajor(person.getEducationGoal().getPlannedMajor());
		
		if(person.getDisability() != null && person.getDisability().getDisabilityStatus() != null)
			setOdsStatus(person.getDisability().getDisabilityStatus().getName());
	}
	
	public void updateMajorFromAcademicPrograms(List<ExternalStudentAcademicProgram> academicPrograms){
		if(academicPrograms != null && !academicPrograms.isEmpty()){
			ArrayList<String> majors = new ArrayList<String>();
			for(ExternalStudentAcademicProgram academicProgram:academicPrograms)
				if(academicProgram.getProgramName() != null && !academicProgram.getProgramName().isEmpty())
					majors.add(academicProgram.getProgramName());
			if(!majors.isEmpty()){
				String majorsStr = "";
				for(String maj:majors)
					majorsStr = this.addValueToStringList(majorsStr, maj);
				setMajor(majorsStr);
				return;
			}
		}
		
		String plannedMajor = getMajor();
		if(plannedMajor == null || plannedMajor.trim().isEmpty())
			return;
		if(!plannedMajor.trim().contains("Planned Major:"))
			setMajor("Planned Major: " + plannedMajor);
	}
	
	public void processDuplicate(BaseStudentReportTO reportTO){
		super.processDuplicate(reportTO);
		this.addDisabilityAgenciesCreatedDate(((DisabilityServicesReportTO)reportTO).getDisabilityAgenciesCreatedDate());
		this.addDisabilityAgenciesName(((DisabilityServicesReportTO)reportTO).getDisabilityAgenciesName());
	}

	private SimpleDateFormat dateFormatter() {
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT,Locale.US);
	}
}
