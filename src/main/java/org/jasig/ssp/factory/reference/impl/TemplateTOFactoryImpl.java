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

import org.jasig.ssp.dao.TemplateDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.TemplateCourseTOFactory;
import org.jasig.ssp.factory.reference.TemplateTOFactory;
import org.jasig.ssp.factory.reference.TermNoteTOFactory;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.model.TermNote;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.TemplateCourseTO;
import org.jasig.ssp.transferobject.TemplateTO;
import org.jasig.ssp.transferobject.TermNoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class TemplateTOFactoryImpl extends AbstractAuditableTOFactory<TemplateTO, Template>
		implements TemplateTOFactory {

	public TemplateTOFactoryImpl() {
		super(TemplateTO.class, Template.class);
	}

	@Autowired
	private transient TemplateDao dao;

	@Autowired
	private PersonService personService;
	
	@Autowired
	private TemplateCourseTOFactory templateCourseTOFactory;
	
	@Autowired
	private TermNoteTOFactory termNoteTOFactory;	
	
	
	@Override
	protected TemplateDao getDao() {
		return dao;
	}
	
	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@Override
	public Template from(TemplateTO tObject) throws ObjectNotFoundException {
		Template model = super.from(tObject);
		model.setOwner(getPersonService().get(UUID.fromString(tObject.getOwnerId())));
		model.setName(tObject.getName());
		model.setDepartmentCode(tObject.getDepartmentCode());
		model.setDivisionCode(tObject.getDivisionCode());
		model.setIsPrivate(tObject.getIsImportant());
		model.setProgramCode(tObject.getProgramCode());
		model.getTemplateCourses().clear();
		List<TemplateCourseTO> planCourses = tObject.getTemplateCourses();
		for (TemplateCourseTO planCourseTO : planCourses) {
			TemplateCourse planCourse = getTemplateCourseTOFactory().from(planCourseTO);
			planCourse.setTemplate(model);
			model.getTemplateCourses().add(planCourse);
		}
		List<TermNoteTO> termNotes = tObject.getTermNotes();
		for (TermNoteTO termNoteTO : termNotes) {
			TermNote noteModel = getTermNoteTOFactory().from(termNoteTO);
			noteModel.setTemplate(model);
		}
		return model;
	}

	public TemplateCourseTOFactory getTemplateCourseTOFactory() {
		return templateCourseTOFactory;
	}

	public void setPlanCourseTOFactory(TemplateCourseTOFactory planCourseTOFactory) {
		this.templateCourseTOFactory = planCourseTOFactory;
	}

	public TermNoteTOFactory getTermNoteTOFactory() {
		return termNoteTOFactory;
	}

	public void setTermNoteTOFactory(TermNoteTOFactory termNoteTOFactory) {
		this.termNoteTOFactory = termNoteTOFactory;
	}
}
