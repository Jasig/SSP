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
package org.jasig.ssp.factory.reference.impl;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.PlanCourseTOFactory;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.reference.Elective;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ElectiveService;
import org.jasig.ssp.transferobject.PlanCourseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlanCourseTOFactoryImpl extends AbstractAuditableTOFactory<PlanCourseTO, PlanCourse>
		implements PlanCourseTOFactory {

	public PlanCourseTOFactoryImpl() {
		super(PlanCourseTO.class, PlanCourse.class);
	}


	@Autowired
	private PersonService personService;
	
	@Autowired
	private ElectiveService electiveService;
	

	
	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@Override
	public PlanCourse from(PlanCourseTO tObject) throws ObjectNotFoundException {
		PlanCourse model = super.from(tObject);
		model.setCourseCode(tObject.getCourseCode());
		model.setCourseDescription(tObject.getCourseDescription());
		model.setCourseTitle(tObject.getCourseTitle());
		model.setContactNotes(tObject.getContactNotes());
		model.setStudentNotes(tObject.getStudentNotes());
		model.setIsImportant(tObject.getIsImportant());
		model.setIsTranscript(tObject.getIsTranscript());
		model.setCreditHours(tObject.getCreditHours());
		model.setTermCode(tObject.getTermCode());
		model.setFormattedCourse(tObject.getFormattedCourse());
		model.setIsDev(tObject.isDev());
		model.setOrderInTerm(tObject.getOrderInTerm());
		if(tObject.getElectiveId() != null)
		{
			Elective elective = electiveService.get(tObject.getElectiveId());
			model.setElective(elective);
		}
		return model;
	}

	@Override
	protected AuditableCrudDao<PlanCourse> getDao() {
		return null;
	}

	public ElectiveService getElectiveService() {
		return electiveService;
	}

	public void setElectiveService(ElectiveService electiveService) {
		this.electiveService = electiveService;
	}

		
		

}
