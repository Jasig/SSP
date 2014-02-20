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

import org.jasig.ssp.dao.StudentDocumentDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.reference.StudentDocumentTOFactory;
import org.jasig.ssp.model.StudentDocument;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.StudentDocumentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StudentDocumentTOFactoryImpl extends AbstractAuditableTOFactory<StudentDocumentTO, StudentDocument>
		implements StudentDocumentTOFactory {

	public StudentDocumentTOFactoryImpl() {
		super(StudentDocumentTO.class, StudentDocument.class);
	}

	@Autowired
	private transient StudentDocumentDao dao;

	@Autowired
	private PersonService personService;
	
	
	@Override
	protected StudentDocumentDao getDao() {
		return dao;
	}
	
	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	@Override
	public StudentDocumentTO from(StudentDocument model) {
		StudentDocumentTO to = super.from(model);
		to.setComment(model.getComments());
		to.setConfidentialityLevelId(model.getConfidentialityLevel().getId());
		to.setConfidentialityLevelName(model.getConfidentialityLevel().getName());
		to.setName(model.getName());
		to.setFileName(model.getFileName());
		to.setAuthor(model.getAuthor().getFullName());
		return to;
	}
		

}
