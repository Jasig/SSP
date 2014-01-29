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

import org.jasig.ssp.dao.TemplateDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.TemplateLiteTOFactory;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.TemplateLiteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TemplateLiteTOFactoryImpl extends AbstractAuditableTOFactory<TemplateLiteTO, Template>
		implements TemplateLiteTOFactory {

	public TemplateLiteTOFactoryImpl() {
		super(TemplateLiteTO.class, Template.class);
	}

	@Autowired
	private transient TemplateDao dao;

	@Autowired
	private PersonService personService;
	
	@Override
	protected TemplateDao getDao() {
		return dao;
	}

	
	@Override
	public Template from(TemplateLiteTO tObject) throws ObjectNotFoundException {
		Template model = super.from(tObject);
		model.setName(tObject.getName());
		model.setAcademicGoals(tObject.getAcademicGoals());
		model.setAcademicLink(tObject.getAcademicLink());
		model.setCareerLink(tObject.getCareerLink());
		model.setContactEmail(tObject.getContactEmail());
		model.setContactName(tObject.getContactName());
		model.setContactNotes(tObject.getContactNotes());
		model.setContactPhone(tObject.getContactPhone());
		model.setContactTitle(tObject.getContactTitle());
		model.setIsF1Visa(tObject.getIsF1Visa());
		model.setIsFinancialAid(tObject.getIsFinancialAid());
		model.setStudentNotes(tObject.getStudentNotes());		
		model.setDepartmentCode(tObject.getDepartmentCode());
		model.setDivisionCode(tObject.getDivisionCode());
		model.setIsPrivate(tObject.getIsPrivate());
		model.setVisibility(tObject.getVisibility());
		model.setProgramCode(tObject.getProgramCode());		
	return model;
	}
		

}
