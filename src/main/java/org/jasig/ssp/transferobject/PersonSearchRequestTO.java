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
package org.jasig.ssp.transferobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.util.sort.SortingAndPaging;


/**
 * PersonSearchResult transfer object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonSearchRequestTO  implements	TransferObject<PersonSearchRequest> {

	private String schoolId;
	
	private String firstName;
	
	private String lastName;

	private List<UUID> programStatus;
	
	private List<UUID> specialServiceGroup;

	private List<UUID> coachId;

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
	
	private List<String> sapStatusCode;
	
	private String planStatus;
	
	private String planExists;
	
	private Boolean myCaseload;
	
	private Boolean myPlans;
	
	private Boolean myWatchList;
	
	private Date birthDate;

    private List<String> actualStartTerm;
	
	private String personTableType;
	
	private SortingAndPaging sortAndPage;

	private List<UUID> homeCampus;

	
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
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

	public List<UUID> getProgramStatus() {
		return programStatus;
	}

	public void setProgramStatus(List<UUID> programStatus) {
		if (CollectionUtils.isNotEmpty(programStatus)) {
            this.programStatus = programStatus;
        }
	}
	
	public void setProgramStatuses(String programStatusin) {
		if(StringUtils.isNotBlank(programStatusin)) {
			List<String> items = Arrays.asList(programStatusin.split("\\s*,\\s*"));
			ArrayList<UUID> programStatus = new ArrayList<UUID>();
			for (String item: items) {
				programStatus.add(UUID.fromString(item));
			}
			this.programStatus = programStatus;
		}
	}

	public List<UUID> getSpecialServiceGroup() {
		return specialServiceGroup;
	}

	public void setSpecialServiceGroup(List<UUID> specialServiceGroup) {
	    if (CollectionUtils.isNotEmpty(specialServiceGroup)) {
            this.specialServiceGroup = specialServiceGroup;
        }
	}
	
	//comma separated string of specialservicegroupids
	public void setSpecialServiceGroups(String specialServiceGroups) {
		if (StringUtils.isNotBlank(specialServiceGroups)) {
			List<String> items = Arrays.asList(specialServiceGroups.split("\\s*,\\s*"));
			ArrayList<UUID> specialServiceGroupIds = new ArrayList<UUID>();
			for (String item: items) {
				specialServiceGroupIds.add(UUID.fromString(item));
			}
			this.specialServiceGroup = specialServiceGroupIds;
		}
	}

	public List<UUID> getHomeCampus() {
		return homeCampus;
	}

	public void setHomeCampus(List<UUID> homeCampus) {
		if (CollectionUtils.isNotEmpty(homeCampus)) {
			this.homeCampus = homeCampus;
		}
	}

	//comma separated string of homeCampusids
	public void setHomeCampuses(String homeCampuses) {
		if (StringUtils.isNotBlank(homeCampuses)) {
			List<String> items = Arrays.asList(homeCampuses.split("\\s*,\\s*"));
			ArrayList<UUID> homeCampusIds = new ArrayList<UUID>();
			for (String item: items) {
				homeCampusIds.add(UUID.fromString(item));
			}
			this.homeCampus = homeCampusIds;
		}
	}
	public List<UUID> getCoachId() {
		return coachId;
	}
	
	public void setCoachId(List<UUID> coachIds) {
        this.coachId = coachIds;
    }

	public void setCoachIds(String coaches) {
		if (StringUtils.isNotBlank(coaches)) {
			List<String> items = Arrays.asList(coaches.split("\\s*,\\s*"));
			ArrayList<UUID> coachIds = new ArrayList<UUID>();
			for (String item: items) {
				coachIds.add(UUID.fromString(item));
			}
			this.coachId = coachIds;
		}
	}

	public List<String> getDeclaredMajor() {
		return declaredMajor;
	}

    public void setDeclaredMajors(String declaredMajor) {
        if (StringUtils.isNotBlank(declaredMajor)) {
            List<String> items = Arrays.asList(declaredMajor.split("\\s*,\\s*"));
            this.declaredMajor = items;
        }
    }

    public void setDeclaredMajor(List<String> declaredMajorIn) {
        if (CollectionUtils.isNotEmpty(declaredMajorIn)) {
            this.declaredMajor = declaredMajorIn;
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
	    if (CollectionUtils.isNotEmpty(sapStatusCode)) {
            this.sapStatusCode = sapStatusCode;
        }
	}
	
	
	//handle a list of comma separated values
	public void setSapStatusCodes(String sapStatusCode) {
		if (StringUtils.isNotBlank(sapStatusCode)) {
			List<String> items = Arrays.asList(sapStatusCode.split("\\s*,\\s*"));
			ArrayList<String> sapStatusCodes = Lists.newArrayList();
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

	@Override
	public void from(PersonSearchRequest model) {
		//NO-OP because this TO should only be used for requests.
	}

	
	

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

	public String getPersonTableType() {
        return personTableType;
    }

    public void setPersonTableTypes(String personTableType) {
        if (StringUtils.isNotBlank(personTableType)) {
            this.personTableType = personTableType;
        }
    }

    public void setPersonTableType(List<String> personTableTypeIn) {
        if (CollectionUtils.isNotEmpty(personTableTypeIn)) {
            StringBuilder personTableType = new StringBuilder();
            for (String item: personTableTypeIn) {
                personTableType.append(item);
            }
            this.personTableType = personTableType.toString();
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

    public List<String> getActualStartTerm () {
                return actualStartTerm;
    }

    public void setActualStartTerm (final List<String> actualStartTerm) {
        if (CollectionUtils.isNotEmpty(actualStartTerm)) {
            this.actualStartTerm = actualStartTerm;
        }
    }
    
    public void setActualStartTerms (String actualStartTerm) {
    	if (StringUtils.isNotEmpty(actualStartTerm)) {
			List<String> items = Arrays.asList(actualStartTerm.split("\\s*,\\s*"));
			ArrayList<String> actualStartTermList = Lists.newArrayList();
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
}
