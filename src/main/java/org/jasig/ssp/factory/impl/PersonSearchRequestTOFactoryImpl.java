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
package org.jasig.ssp.factory.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.dao.reference.ProgramStatusDao;
import org.jasig.ssp.factory.AbstractTOFactory;
import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchRequest;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonSearchRequestTO;
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
		if(to.getCoachId() != null)
		{
			Person coach = getPersonDao().get(to.getCoachId());
			model.setCoach(coach);
		}
		model.setCurrentlyRegistered(to.getCurrentlyRegistered());
		model.setDeclaredMajor(to.getDeclaredMajor());
		model.setGpaEarnedMax(to.getGpaEarnedMax());
		model.setGpaEarnedMin(to.getGpaEarnedMin());
		model.setHoursEarnedMax(to.getHoursEarnedMax());
		model.setHoursEarnedMin(to.getHoursEarnedMin());
		model.setMapStatus(to.getMapStatus());
		if(to.getProgramStatus() != null)
		{
			ProgramStatus programStatus = getProgramStatusDao().get(to.getProgramStatus());
			model.setProgramStatus(programStatus);
		}
		model.setSapStatus(to.getSapStatus());
		model.setStudentId(to.getStudentId());
		model.setPlanStatus(to.getPlanStatus());
		model.setMyCaseload(to.getMyCaseload());
		model.setMyPlans(to.getMyPlans());
		return model;
	}


	@Override
	public PersonSearchRequest from(UUID id) throws ObjectNotFoundException {
		return null;
	}


	public ProgramStatusDao getProgramStatusDao() {
		return programStatusDao;
	}


	public void setProgramStatusDao(ProgramStatusDao programStatusDao) {
		this.programStatusDao = programStatusDao;
	}


	@Override
	public PersonSearchRequest from(String studentId, String programStatus,
			String coachId, String declaredMajor, BigDecimal hoursEarnedMin,
			BigDecimal hoursEarnedMax, BigDecimal gpaEarnedMin,
			BigDecimal gpaEarnedMax, Boolean currentlyRegistered,
			String sapStatus, String mapStatus, String planStatus, Boolean myCaseload, Boolean myPlans) throws ObjectNotFoundException {
		PersonSearchRequestTO to = new PersonSearchRequestTO();
		to.setStudentId(studentId);
		to.setProgramStatus(programStatus == null ? null : UUID.fromString(programStatus));
		to.setCoachId(coachId == null ? null : UUID.fromString(coachId));
		to.setDeclaredMajor(declaredMajor);
		to.setHoursEarnedMin(hoursEarnedMin);
		to.setHoursEarnedMax(hoursEarnedMax);
		to.setGpaEarnedMin(gpaEarnedMin);
		to.setGpaEarnedMax(gpaEarnedMax);
		to.setCurrentlyRegistered(currentlyRegistered);
		to.setSapStatus(sapStatus);
		to.setMapStatus(mapStatus);
		to.setPlanStatus(planStatus);
		to.setMyCaseload(myCaseload);
		to.setMyPlans(myPlans);
		return from(to);
	}
}