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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.PersonSearchController;


/**
 * PersonSearchResult model for use by {@link PersonSearchResultTO} and then
 * {@link PersonSearchController}.
 */
public class PersonSearchRequest {
	
	
	public static final String PLAN_EXISTS_ACTIVE = "ACTIVE";
	
	public static final String PLAN_EXISTS_INACTIVE = "INACTIVE";

	public static final String PLAN_EXISTS_NONE = "NONE";

	public static final String PLAN_STATUS_ON_PLAN = "ON_PLAN";
	
	public static final String PLAN_STATUS_OFF_PLAN = "OFF_PLAN";

	public static final String PLAN_STATUS_ON_TRACK_SUBSTITUTION = "ON_TRACK_SUBSTITUTIO";
	
	public static final String PLAN_STATUS_ON_TRACK_SEQUENCE = "ON_TRACK_SEQUENCE";
	
	public static final String EARLY_ALERT_RESPONSE_RESPONSE_CURRENT = "RESPONSE_CURRENT";
	
	public static final String EARLY_ALERT_RESPONSE_RESPONSE_OVERDUE = "RESPONSE_OVERDUE";
	
	public static final String EARLY_ALERT_RESPONSE_ALL_OPEN_ALERTS = "ALL_OPEN_ALERTS";
	
	public static final String PERSON_TABLE_TYPE_ANYWHERE = "ANYWHERE";
	
	public static final String PERSON_TABLE_TYPE_SSP_ONLY = "SSP_ONLY";
	
	public static final String PERSON_TABLE_TYPE_EXTERNAL_DATA_ONLY = "EXTERNAL_DATA_ONLY";
	
	// id of the person
	private String schoolId;

	private ArrayList<ProgramStatus> programStatus;
	
	private List<SpecialServiceGroup> specialServiceGroup;

	private List<Person> coach;
	
	private Person watcher;

	private List<String> declaredMajor;

	private BigDecimal hoursEarnedMin;
	
	private BigDecimal hoursEarnedMax;

	private BigDecimal gpaEarnedMin;
	
	private BigDecimal gpaEarnedMax;

    private BigDecimal localGpaMin;

    private BigDecimal localGpaMax;

    private BigDecimal programGpaMin;

    private BigDecimal programGpaMax;
	
	private Boolean currentlyRegistered;
	
	private String earlyAlertResponseLate;
	
	private String planExists;
	
	private List<String> sapStatusCode;
	
	private String planStatus;
	
	private Boolean myCaseload;
	
	private Boolean myPlans;
	
	private Boolean myWatchList;

	private Date birthDate;
	
	private String firstName;
	
	private String lastName;

    private List<String> actualStartTerm;
	
	private String personTableType;
	
	private SortingAndPaging sortAndPage;

	public PersonSearchRequest() {
		super();
	}


	public String getSchoolId() {
		return schoolId;
	}


	public void setSchoolId(String studentId) {
		this.schoolId = studentId;
	}


	public ArrayList<ProgramStatus> getProgramStatus() {
		return programStatus;
	}
	
	public ArrayList<String> getProgramStatusNames() {
		ArrayList<String> retVal = new ArrayList<String>();
		for(ProgramStatus ps: programStatus) {
			retVal.add(ps.getName());
		}
			
		return retVal;
	}


	public void setProgramStatus(ArrayList<ProgramStatus> programStatus) {
		this.programStatus = programStatus;
	}
	
	public void setProgramStatus(ProgramStatus programStatus) {
		ArrayList<ProgramStatus> psList = new ArrayList<ProgramStatus>();
		psList.add(programStatus);
		this.programStatus = psList;
	}


	public List<SpecialServiceGroup> getSpecialServiceGroup() {
		return specialServiceGroup;
	}


	public void setSpecialServiceGroup(List<SpecialServiceGroup> specialServiceGroup) {
		this.specialServiceGroup = specialServiceGroup;
	}
	
	public void setSpecialServiceGroup(SpecialServiceGroup specialServiceGroup) {
		List<SpecialServiceGroup> specialServiceGroupList = new ArrayList<SpecialServiceGroup>();
		specialServiceGroupList.add(specialServiceGroup);
		this.specialServiceGroup = specialServiceGroupList;
	}


	public List<Person> getCoach() {
		return coach;
	}


	public void setCoach(List<Person> coach) {
		this.coach = coach;
	}


	public List<String> getDeclaredMajor() {
		return declaredMajor;
	}


	public void setDeclaredMajor(List<String> declaredMajor) {
		this.declaredMajor = declaredMajor;
	}
	
	public void setDeclaredMajor(String declaredMajor) {
		
		if(StringUtils.isNotEmpty(declaredMajor)) {
			List<String> items = Arrays.asList(declaredMajor.split("\\s*,\\s*"));
			ArrayList<String> declaredMajorList = new ArrayList<String>();
			for(String item: items)
			{
				declaredMajorList.add(item);
			}
			this.declaredMajor = declaredMajorList;
		}
	}	


	public BigDecimal getHoursEarnedMin() {
		return hoursEarnedMin;
	}


	public void setHoursEarnedMin(BigDecimal hoursEarnedMin) {
		this.hoursEarnedMin = hoursEarnedMin;
	}


	public BigDecimal getHoursEarnedMax() {
		return hoursEarnedMax;
	}


	public void setHoursEarnedMax(BigDecimal hoursEarnedMax) {
		this.hoursEarnedMax = hoursEarnedMax;
	}


	public BigDecimal getGpaEarnedMin() {
		return gpaEarnedMin;
	}


	public void setGpaEarnedMin(BigDecimal gpaEarnedMin) {
		this.gpaEarnedMin = gpaEarnedMin;
	}


	public BigDecimal getGpaEarnedMax() {
		return gpaEarnedMax;
	}


	public void setGpaEarnedMax(BigDecimal gpaEarnedMax) {
		this.gpaEarnedMax = gpaEarnedMax;
	}


	public Boolean getCurrentlyRegistered() {
		return currentlyRegistered;
	}


	public void setCurrentlyRegistered(Boolean currentlyRegistered) {
		this.currentlyRegistered = currentlyRegistered;
	}


	public String getEarlyAlertResponseLate() {
		return earlyAlertResponseLate;
	}


	public void setEarlyAlertResponseLate(String earlyAlertResponseLate) {
		this.earlyAlertResponseLate = earlyAlertResponseLate;
	}

	
	public List<String> getSapStatusCode() {
		return sapStatusCode;
	}

	public void setSapStatusCode(List<String> sapStatusCode) {
		this.sapStatusCode = sapStatusCode;
	}
	
	
	//handle a list of comma separated values
	public void setSapStatusCode(String sapStatusCode) {
		if(sapStatusCode != null) {
			List<String> items = Arrays.asList(sapStatusCode.split("\\s*,\\s*"));
			ArrayList<String> sapStatusCodes = new ArrayList<String>();
			for(String item: items)
			{
				sapStatusCodes.add(item);
			}
			this.sapStatusCode = sapStatusCodes;
		}
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	
	
	/*
	//handle a list of comma separated values
	public void setPlanStatus(String planStatus) {
		if(planStatus != null) {
			List<String> items = Arrays.asList(planStatus.split("\\s*,\\s*"));
			ArrayList<String> planStatuses = new ArrayList<String>();
			for(String item: items)
			{
				planStatuses.add(item);
			}
			this.planStatus = planStatuses;
		}
	}
	*/



	public String getPlanExists() {
		return planExists;
	}


	public void setPlanExists(String planExists) {
		this.planExists = planExists;
	}

	
	/*
	public List<String> getPlanExists() {
		return planExists;
	}


	public void setPlanExists(List<String> planExists) {
		this.planExists = planExists;
	}
	
	//handle a list of comma separated values
	public void setPlanExists(String planExists) {
		if(planExists != null) {
			List<String> items = Arrays.asList(planExists.split("\\s*,\\s*"));
			ArrayList<String> planExistses = new ArrayList<String>();
			for(String item: items)
			{
				planExistses.add(item);
			}
			this.planExists = planExistses;
		}
	}
	*/


	public Boolean getMyCaseload() {
		return myCaseload;
	}


	public void setMyCaseload(Boolean myCaseload) {
		this.myCaseload = myCaseload;
	}


	public Boolean getMyPlans() {
		return myPlans;
	}


	public void setMyPlans(Boolean myPlans) {
		this.myPlans = myPlans;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


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


	public String getPersonTableType() {
		return personTableType;
	}


	public void setPersonTableType(String personTableType) {
		this.personTableType = personTableType;
	}


	public SortingAndPaging getSortAndPage() {
		return sortAndPage;
	}


	public void setSortAndPage(SortingAndPaging sortAndPage) {
		this.sortAndPage = sortAndPage;
	}


	public Boolean getMyWatchList() {
		return myWatchList;
	}


	public void setMyWatchList(Boolean myWatchList) {
		this.myWatchList = myWatchList;
	}


	public Person getWatcher() {
		return watcher;
	}


	public void setWatcher(Person watcher) {
		this.watcher = watcher;
	}


    public List<String> getActualStartTerm () {
                return actualStartTerm;
        }


    public void setActualStartTerm (final List<String> actualStartTerm) {
                this.actualStartTerm = actualStartTerm;
        }
    
    public void setActualStartTerm (String actualStartTerm) {
    	
    	if(StringUtils.isNotEmpty(actualStartTerm)) {
			List<String> items = Arrays.asList(actualStartTerm.split("\\s*,\\s*"));
			ArrayList<String> actualStartTermList = new ArrayList<String>();
			for(String item: items)
			{
				actualStartTermList.add(item);
			}
			this.actualStartTerm = actualStartTermList;
		}
    	
    	//List<String> startTermList = new ArrayList<String>();
    	//startTermList.add(actualStartTerm);
        //this.actualStartTerm = startTermList;
}


    public BigDecimal getLocalGpaMin () {
        return localGpaMin;
    }


    public void setLocalGpaMin (final BigDecimal localGpaMin) {
        this.localGpaMin = localGpaMin;
    }


    public BigDecimal getLocalGpaMax () {
        return localGpaMax;
    }


    public void setLocalGpaMax (final BigDecimal localGpaMax) {
        this.localGpaMax = localGpaMax;
    }


    public BigDecimal getProgramGpaMin () {
        return programGpaMin;
    }


    public void setProgramGpaMin (final BigDecimal programGpaMin) {
        this.programGpaMin = programGpaMin;
    }


    public BigDecimal getProgramGpaMax () {
        return programGpaMax;
    }


    public void setProgramGpaMax (final BigDecimal programGpaMax) {
        this.programGpaMax = programGpaMax;
    }
}
