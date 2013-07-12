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
package org.jasig.ssp.service.impl;

import java.util.UUID;

import org.jasig.ssp.dao.StudentDocumentDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.StudentDocument;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.StudentDocumentService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.StudentDocumentTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Person service implementation
 * 
 * @author tony.arland
 */
@Service
@Transactional
public class StudentDocumentServiceImpl extends AbstractAuditableCrudService<StudentDocument>
		implements StudentDocumentService {

	@Autowired 
	private StudentDocumentDao dao;
	
	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private transient SecurityService securityService;
	
	@Override
	public PagingWrapper<StudentDocument> getAllForPerson(Person person,
			SortingAndPaging sAndP) {
		return dao.getAllForPerson(person, sAndP);
	}

	@Override
	protected StudentDocumentDao getDao() {
		return dao;
	}

	@Override
	public StudentDocument save(StudentDocument obj)
			throws ObjectNotFoundException, ValidationException {
		return dao.save(obj);
	}

	@Override
	public StudentDocument createStudentDocFromUploadBean(StudentDocumentTO bean, String fileLocation,UUID studentId) throws ObjectNotFoundException {
		
		StudentDocument studentDocument = new StudentDocument();
		studentDocument.setComments(bean.getComment());
		studentDocument.setFileLocation(fileLocation);
		studentDocument.setName(bean.getName());
		studentDocument.setAuthor(getSecurityService().currentlyAuthenticatedUser().getPerson());
		if(bean.getConfidentialityLevelId() != null)
		{
			studentDocument.setConfidentialityLevel(getConfidentialityLevelService().get(bean.getConfidentialityLevelId()));
		}
		studentDocument.setPerson(getPersonService().get(studentId));
		studentDocument.setFileName(bean.getFile().getOriginalFilename());
		return studentDocument;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public ConfidentialityLevelService getConfidentialityLevelService() {
		return confidentialityLevelService;
	}

	public void setConfidentialityLevelService(
			ConfidentialityLevelService confidentialityLevelService) {
		this.confidentialityLevelService = confidentialityLevelService;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}


}