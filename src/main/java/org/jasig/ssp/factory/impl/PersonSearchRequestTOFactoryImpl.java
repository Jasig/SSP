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
package org.jasig.ssp.factory.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.dao.reference.ProgramStatusDao;
import org.jasig.ssp.dao.reference.SpecialServiceGroupDao;
import org.jasig.ssp.factory.AbstractTOFactory;
import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonSearchRequestTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Person transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class PersonSearchRequestTOFactoryImpl extends AbstractTOFactory<PersonSearchRequest, PersonSearchRequestTO>
		implements PersonSearchRequestTOFactory {

	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private ProgramStatusDao programStatusDao;
	
	@Autowired
	private SpecialServiceGroupDao specialServiceGroupDao;
	
	public PersonSearchRequestTOFactoryImpl() {
		super(PersonSearchRequestTO.class, PersonSearchRequest.class);
	}


	protected PersonDao getPersonDao() {
		return personDao;
	}

	@Override
	public PersonSearchRequest from(final PersonSearchRequestTO to)
			throws ObjectNotFoundException {
		final PersonSearchRequest model = new PersonSearchRequest();

        if (to.getCoachId() != null) {
        	//cycle through each of the coachids
        	ArrayList<Person> coaches = new ArrayList<Person>();
			for (UUID coachid : to.getCoachId()) { 
				coaches.add(getPersonDao().get(coachid));				
			}
			model.setCoach(coaches);
		}
		model.setCurrentlyRegistered(to.getCurrentlyRegistered());
		model.setDeclaredMajor(to.getDeclaredMajor());
		model.setGpaEarnedMax(to.getGpaEarnedMax());
		model.setGpaEarnedMin(to.getGpaEarnedMin());
		model.setHoursEarnedMax(to.getHoursEarnedMax());
		model.setHoursEarnedMin(to.getHoursEarnedMin());
        model.setLocalGpaMax(to.getLocalGpaMax());
        model.setLocalGpaMin(to.getLocalGpaMin());
        model.setProgramGpaMax(to.getProgramGpaMax());
        model.setProgramGpaMin(to.getProgramGpaMin());
		model.setPlanStatus(to.getPlanStatus());
		
		if (to.getProgramStatus() != null) {			
			//cycle through each programstatus
			ArrayList<ProgramStatus> programStatus = new ArrayList<ProgramStatus>();
			for (UUID programstatusId : to.getProgramStatus()) { 
				programStatus.add(getProgramStatusDao().get(programstatusId));				
			}
			model.setProgramStatus(programStatus);
		}
		
		if (to.getSpecialServiceGroup() != null) {
			
			ArrayList<SpecialServiceGroup> specialServiceGroupList = new ArrayList<SpecialServiceGroup>();
			for (UUID specialServiceGroupId : to.getSpecialServiceGroup()) { 
				specialServiceGroupList.add(getSpecialServiceGroupDao().get(specialServiceGroupId));				
			}
			model.setSpecialServiceGroup(specialServiceGroupList);
		}
		model.setSapStatusCode(to.getSapStatusCode());
		model.setSchoolId(to.getSchoolId());
		model.setFirstName(to.getFirstName());
		model.setLastName(to.getLastName());
		model.setPlanExists(to.getPlanExists());
		model.setMyCaseload(to.getMyCaseload());
		model.setMyPlans(to.getMyPlans());
		model.setMyWatchList(to.getMyWatchList());
		model.setBirthDate(to.getBirthDate());
        model.setActualStartTerm(to.getActualStartTerm());
		model.setEarlyAlertResponseLate(to.getEarlyAlertResponseLate());
		model.setPersonTableType(to.getPersonTableType());
		model.setSortAndPage(to.getSortAndPage());

        return model;
	}


	@Override
	public PersonSearchRequest from(UUID id) throws ObjectNotFoundException {
		return null;
	}


	public ProgramStatusDao getProgramStatusDao() {
		return programStatusDao;
	}
	
	public SpecialServiceGroupDao getSpecialServiceGroupDao() {
		return specialServiceGroupDao;
	}

	public void setProgramStatusDao(ProgramStatusDao programStatusDao) {
		this.programStatusDao = programStatusDao;
	}

	@Override
	public PersonSearchRequest from(String schoolId, String firstName, String lastName,
			String programStatus, String specialServiceGroup,
			String coachId, String declaredMajor, BigDecimal hoursEarnedMin,
			BigDecimal hoursEarnedMax, BigDecimal gpaEarnedMin,
			BigDecimal gpaEarnedMax, BigDecimal localGpaMin, BigDecimal localGpaMax,
            BigDecimal programGpaMin, BigDecimal programGpaMax,
            Boolean currentlyRegistered,String earlyAlertResponseLate,
			String sapStatusCode, String planStatus, String planExists, Boolean myCaseload, Boolean myPlans,
			Boolean myWatchList, Date birthDate, String actualStartTerm, String personTableType , SortingAndPaging sortAndPage) throws ObjectNotFoundException {

		PersonSearchRequestTO to = new PersonSearchRequestTO();
		to.setSchoolId(schoolId);
		to.setFirstName(firstName);
		to.setLastName(lastName);
		
		//comma separated set of UUIDs as a String
		to.setProgramStatuses(programStatus);
		
		//comma separated set of UUIDs as a String
		to.setSpecialServiceGroups(specialServiceGroup);
		
		//comma separated set of UUIDs as a String
		//to.setCoachId(coachId == null ? null : UUID.fromString(coachId));
		to.setCoachIds(coachId);
		
		to.setDeclaredMajors(declaredMajor);
		to.setHoursEarnedMin(hoursEarnedMin);
		to.setHoursEarnedMax(hoursEarnedMax);
		to.setGpaEarnedMin(gpaEarnedMin);
		to.setGpaEarnedMax(gpaEarnedMax);
        to.setLocalGpaMin(localGpaMin);
        to.setLocalGpaMax(localGpaMax);
        to.setProgramGpaMin(programGpaMin);
        to.setProgramGpaMax(programGpaMax);
		to.setCurrentlyRegistered(currentlyRegistered);
		to.setEarlyAlertResponseLate(earlyAlertResponseLate);
		to.setSapStatusCodes(sapStatusCode);
		to.setPlanStatus(planStatus);
		to.setPlanExists(planExists);
		to.setMyCaseload(myCaseload);
		to.setMyPlans(myPlans);
		to.setMyWatchList(myWatchList);
		to.setBirthDate(birthDate);
        to.setActualStartTerms(actualStartTerm);
		to.setPersonTableTypes(personTableType);
		to.setSortAndPage(sortAndPage);
		return from(to);
	}
	
	@Override
	public PersonSearchRequest from(String schoolId, String firstName, String lastName,
			String programStatus, String specialServiceGroup,
			String coachId, String declaredMajor, BigDecimal hoursEarnedMin,
			BigDecimal hoursEarnedMax, BigDecimal gpaEarnedMin,
			BigDecimal gpaEarnedMax, BigDecimal localGpaMin, BigDecimal localGpaMax,
            BigDecimal programGpaMin, BigDecimal programGpaMax, Boolean currentlyRegistered,String earlyAlertResponseLate,
			String sapStatusCode, String planStatus, String planExists, Boolean myCaseload, Boolean myPlans,Boolean myWatchList,
			Date birthDate, String actualStartTerm) throws ObjectNotFoundException {

		PersonSearchRequestTO to = new PersonSearchRequestTO(); 
		to.setSchoolId(schoolId);
		to.setFirstName(firstName);
		to.setLastName(lastName);
		
		//comma delineated set of UUIDs as Strings
		to.setProgramStatuses(programStatus);
		
		//comma delineated set of UUIDs as Strings
		to.setSpecialServiceGroups(specialServiceGroup);
		
		to.setCoachIds(coachId);
		
		//comma delineated set of Strings (there can be a single one)
		to.setDeclaredMajors(declaredMajor);
		
		to.setHoursEarnedMin(hoursEarnedMin);
		to.setHoursEarnedMax(hoursEarnedMax);
		to.setGpaEarnedMin(gpaEarnedMin);
		to.setGpaEarnedMax(gpaEarnedMax);
        to.setLocalGpaMin(localGpaMin);
        to.setLocalGpaMax(localGpaMax);
        to.setProgramGpaMin(programGpaMin);
        to.setProgramGpaMax(programGpaMax);
		to.setCurrentlyRegistered(currentlyRegistered);
		to.setEarlyAlertResponseLate(earlyAlertResponseLate);
		to.setSapStatusCodes(sapStatusCode);
		to.setPlanStatus(planStatus);
		to.setPlanExists(planExists);
		to.setMyCaseload(myCaseload);
		to.setMyPlans(myPlans);
		to.setMyWatchList(myWatchList);
		to.setBirthDate(birthDate);
		
		//comma delineated set of Strings (there can be a single one)
        to.setActualStartTerms(actualStartTerm);
		return from(to);
	}
}
