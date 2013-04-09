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

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.PlanDao;
import org.jasig.ssp.dao.reference.SelfHelpGuideDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.PlanCourseTOFactory;
import org.jasig.ssp.factory.reference.PlanTOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanCourse;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.PlanCourseTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;

@Service
@Transactional(readOnly = true)
public class PlanTOFactoryImpl extends AbstractAuditableTOFactory<PlanTO, Plan>
		implements PlanTOFactory {

	public PlanTOFactoryImpl() {
		super(PlanTO.class, Plan.class);
	}

	@Autowired
	private transient PlanDao dao;

	@Autowired
	private PersonService personService;
	
	@Autowired
	private PlanCourseTOFactory planCourseTOFactory;
	
	@Override
	protected PlanDao getDao() {
		return dao;
	}
	
	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@Override
	public Plan from(PlanTO tObject) throws ObjectNotFoundException {
		Plan model = super.from(tObject);
		model.setOwner(getPersonService().get(UUID.fromString(tObject.getOwnerId())));
		model.setPerson(getPersonService().get(UUID.fromString(tObject.getPersonId())));
		model.setName(tObject.getName());
		List<PlanCourseTO> planCourses = tObject.getPlanCourses();
		for (PlanCourseTO planCourseTO : planCourses) {
			PlanCourse planCourse = getPlanCourseTOFactory().from(planCourseTO);
			planCourse.setPlan(model);
			model.getPlanCourses().add(planCourse);
		}
		return model;
	}

	public PlanCourseTOFactory getPlanCourseTOFactory() {
		return planCourseTOFactory;
	}

	public void setPlanCourseTOFactory(PlanCourseTOFactory planCourseTOFactory) {
		this.planCourseTOFactory = planCourseTOFactory;
	}
		
		
		

}
