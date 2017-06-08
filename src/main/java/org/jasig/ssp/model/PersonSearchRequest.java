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

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.PersonSearchController;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


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

	public static final String CURRENTLY_REGISTERED_CURRENT_TERM = "REGISTERED_CURRENT";

	public static final String CURRENTLY_REGISTERED_NOT_CURRENT_TERM = "NOT_REGISTERED_CURRENT";

	public static final String CURRENTLY_REGISTERED_NEXT_TERM = "REGISTERED_NEXT";

	public static final String CURRENTLY_REGISTERED_NOT_NEXT_TERM = "NOT_REGISTERED_NEXT";

    public static final String CONFIGURED_SUCCESS_INDICATOR_EVALUATION_POOR = "POOR";

    public static final String CONFIGURED_SUCCESS_INDICATOR_EVALUATION_OKAY = "OKAY";

	public static final String CONFIGURED_SUCCESS_INDICATOR_EVALUATION_GOOD = "GOOD";

    // id of the person
	private String schoolId;

	private List<ProgramStatus> programStatus;
	
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
	
	private List<String> currentlyRegistered;
	
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

	private List<Campus> homeCampus;

	private List<String> configuredSuccessIndicator;

    public PersonSearchRequest() {
		super();
	}




    public String getSchoolId() {
		return schoolId;
	}


	public void setSchoolId(String studentId) {
		this.schoolId = studentId;
	}


	public List<ProgramStatus> getProgramStatus() {
		return programStatus;
	}
	
	public List<String> getProgramStatusNames() {
		final List<String> retVal = Lists.newArrayList();
		for(ProgramStatus ps: programStatus) {
			retVal.add(ps.getName());
		}

		return retVal;
	}


	public void setProgramStatus(List<ProgramStatus> programStatus) {
		if (CollectionUtils.isNotEmpty(programStatus)) {
            this.programStatus = programStatus;
        }
	}
	
	public void setProgramStatus(ProgramStatus programStatus) {
		if (programStatus != null) {
            final List<ProgramStatus> psList = Lists.newArrayList();
            psList.add(programStatus);

            this.programStatus = psList;
        }
	}

	public List<Campus> getHomeCampus() {
		return homeCampus;
	}

	public List<String> getHomeCampusNames() {
		final ArrayList<String> retVal = Lists.newArrayList();
		for(Campus ps: homeCampus) {
			retVal.add(ps.getName());
		}

		return retVal;
	}


	public void setHomeCampus(List<Campus> homeCampus) {
		if (CollectionUtils.isNotEmpty(homeCampus)) {
			this.homeCampus = homeCampus;
		}
	}

	public void setHomeCampus(Campus homeCampus) {
		if (homeCampus != null) {
			final List<Campus> psList = Lists.newArrayList();
			psList.add(homeCampus);

			this.homeCampus = psList;
		}
	}

	public List<SpecialServiceGroup> getSpecialServiceGroup() {
		return specialServiceGroup;
	}


	public void setSpecialServiceGroup(List<SpecialServiceGroup> specialServiceGroup) {
		if (CollectionUtils.isNotEmpty(specialServiceGroup)) {
            this.specialServiceGroup = specialServiceGroup;
        }
	}
	
	public void setSpecialServiceGroup(SpecialServiceGroup specialServiceGroup) {
		if (specialServiceGroup != null) {
            final List<SpecialServiceGroup> specialServiceGroupList = Lists.newArrayList();
            specialServiceGroupList.add(specialServiceGroup);

            this.specialServiceGroup = specialServiceGroupList;
        }
	}


	public List<Person> getCoach() {
		return coach;
	}


	public void setCoach(List<Person> coach) {
		if (CollectionUtils.isNotEmpty(coach)) {
            this.coach = coach;
        }
	}

    public void setCoach(Person coach) {
        if (coach != null) {
            final List<Person> coachList = Lists.newArrayList();
            coachList.add(coach) ;

            this.coach = coachList;
        }
    }


	public List<String> getDeclaredMajor() {
		return declaredMajor;
	}


	public void setDeclaredMajor(List<String> declaredMajor) {
		if (CollectionUtils.isNotEmpty(declaredMajor)) {
            this.declaredMajor = declaredMajor;
        }
	}
	
	public void setDeclaredMajor(String declaredMajor) {
		if (StringUtils.isNotEmpty(declaredMajor)) {
			final List<String> items = Arrays.asList(declaredMajor.split("\\s*,\\s*"));
			final List<String> declaredMajorList = Lists.newArrayList();

			for (String item: items) {
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


	public List<String> getCurrentlyRegistered() {
		return currentlyRegistered;
	}


	public void setCurrentlyRegistered(List<String> currentlyRegistered) {
		if (CollectionUtils.isNotEmpty(currentlyRegistered)) {
			this.currentlyRegistered = currentlyRegistered;
		}
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
		if (CollectionUtils.isNotEmpty(sapStatusCode)) {
            this.sapStatusCode = sapStatusCode;
        }
	}
	
	
	//handle a list of comma separated values
	public void setSapStatusCode(String sapStatusCode) {
		if (StringUtils.isNotBlank(sapStatusCode)) {
			List<String> items = Arrays.asList(sapStatusCode.split("\\s*,\\s*"));
			ArrayList<String> sapStatusCodes = new ArrayList<String>();
			for (String item: items) {
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
        if (StringUtils.isNotEmpty(personTableType)) {
            final List<String> items = Arrays.asList(personTableType.split("\\s*,\\s*"));
            boolean externalOnly = false;
            boolean internalOnly = false;
            boolean anywhere = false;

            for (String item: items) {
                if (item.trim().toUpperCase().equals(PERSON_TABLE_TYPE_ANYWHERE)) {
                    anywhere = true;
                    break; //trying to determine if a combo equals anywhere, so break if anywhere found first
                } else if (item.trim().toUpperCase().equals(PERSON_TABLE_TYPE_SSP_ONLY)) {
                    if (externalOnly) {
                        anywhere = true;
                        break;
                    } else {
                        internalOnly = true;
                    }
                } else if (item.trim().toUpperCase().equals(PERSON_TABLE_TYPE_EXTERNAL_DATA_ONLY)) {
                    if (internalOnly) {
                        anywhere = true;
                        break;
                    } else {
                        externalOnly = true;
                    }
                } else {
                    anywhere = true; //default if string doesn't match
                }
            }

            if (internalOnly && !anywhere && !externalOnly) {
                this.personTableType = PERSON_TABLE_TYPE_SSP_ONLY;
            } else if (externalOnly && !anywhere && !internalOnly) {
                this.personTableType = PERSON_TABLE_TYPE_EXTERNAL_DATA_ONLY;
            } else {
                this.personTableType = PERSON_TABLE_TYPE_ANYWHERE; //currently the default
            }
        } else {
            this.personTableType = PERSON_TABLE_TYPE_ANYWHERE; //if null or empty set to anywhere the default
        }
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
        if (CollectionUtils.isNotEmpty(actualStartTerm)) {
            this.actualStartTerm = actualStartTerm;
        }
    }
    
    public void setActualStartTerm (String actualStartTerm) {
    	if (StringUtils.isNotEmpty(actualStartTerm)) {
			List<String> items = Arrays.asList(actualStartTerm.split("\\s*,\\s*"));
			ArrayList<String> actualStartTermList = new ArrayList<String>();
			for (String item: items) {
				actualStartTermList.add(item);
			}
			this.actualStartTerm = actualStartTermList;
		}
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

    public List<String> getConfiguredSuccessIndicator() {
        return configuredSuccessIndicator;
    }

    public void setConfiguredSuccessIndicator(List<String> configuredSuccessIndicator) {
        this.configuredSuccessIndicator = configuredSuccessIndicator;
    }
}
