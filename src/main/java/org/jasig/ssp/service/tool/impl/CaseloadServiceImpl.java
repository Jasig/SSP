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
package org.jasig.ssp.service.tool.impl;

import org.jasig.ssp.dao.CaseloadBulkAddReassignmentDao;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.CaseloadBulkAddReassignment;
import org.jasig.ssp.model.FileUploadResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.service.tool.CaseloadService;
import org.jasig.ssp.transferobject.BulkAddCaseloadReassignmentTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Person service implementation
 * 
 * @author tony.arland
 */
@Service
@Transactional
public class CaseloadServiceImpl implements CaseloadService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseloadServiceImpl.class);

	@Autowired 
	private CaseloadBulkAddReassignmentDao dao;
	
	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient MessageTemplateService messageTemplateService;

	protected CaseloadBulkAddReassignmentDao getDao() {
		return dao;
	}

	@Override
	public void processCaseloadBulkAddReassignment() {
		Map<String, AuditPerson> auditPersonMap = new HashMap<String, AuditPerson>();
		List<String> errors = new ArrayList<String>();
		List<CaseloadBulkAddReassignment> list = dao.getAll();
		String notificationEmailAddress = null;
		if (list.size() > 0) {
			int successCount = 0;
			for (CaseloadBulkAddReassignment model : list) {
				if (notificationEmailAddress==null) {
					notificationEmailAddress = model.getNotificationEmailAddress();
				}
				if (!StringUtils.isEmpty(model.getSchoolId().trim())) {
					try {
						Person student = personService.getBySchoolIdOrGetFromExternalBySchoolId(model.getSchoolId(), new Boolean(false)); //method is slow, but looks like proper use
						if (!StringUtils.isEmpty(model.getCoachSchoolId().trim())) {
							if (!StringUtils.isEmpty(model.getModifiedBySchoolId().trim())) {
								AuditPerson auditPerson = getAuditPerson(auditPersonMap, model.getModifiedBySchoolId());
								if (auditPerson != null) {
									try {
										Person coach = personService.getBySchoolIdOrGetFromExternalBySchoolId(model.getCoachSchoolId(), new Boolean(false)); //method is slow, but looks like proper use
										student.setCoach(coach);
										student.setModifiedBy(auditPerson);
										personService.save(student);
										successCount++;
									} catch (ObjectNotFoundException e) {
										createError(errors, "Coach School Id not found for record", model);
									}
								} else {
									createError(errors, "Modified By School Id not found for record", model);
								}
							} else {
								createError(errors, "Modified By School Id was not set for record", model);
							}
						} else if (null != student.getCoach()) {
							student = personService.getBySchoolIdOrGetFromExternalBySchoolId(model.getSchoolId(), new Boolean(true)); //method is slow, but looks like proper use
							successCount++;
						} else {
							createError(errors, "Student not added because the external student does not have a coach assigned for record", model);
						}
					} catch (ObjectNotFoundException e) {
						createError(errors, "Student School Id not found for record", model);
					}
				} else {
					createError(errors, "Student School Id was not set for record", model);
				}
			}
			dao.truncate();

			if (notificationEmailAddress==null) {
				notificationEmailAddress = securityService.currentUser().getPerson().getPrimaryEmailAddress();
			}

			SubjectAndBody subjectAndBody = messageTemplateService.createBulkAddCaseloadReassignmentMessage(successCount, errors);
			try {
				messageService.createMessage(notificationEmailAddress, null, subjectAndBody);
			} catch (ObjectNotFoundException e) {
				LOGGER.error("Error creating BulkAddCaseloadReassignment email", e);
			} catch (ValidationException e) {
				LOGGER.error("Error creating BulkAddCaseloadReassignment email", e);
			}
		}
	}

	private void createError(List<String> errors, String error, CaseloadBulkAddReassignment model) {
		String message = error + " --> " + model.toString();
		errors.add(message);
		LOGGER.warn(message);
	}

	private AuditPerson getAuditPerson(Map<String, AuditPerson> map, String schoolId) {
		AuditPerson auditPerson = map.get(schoolId);
		if (auditPerson==null) {
			try {
				Person modifiedBy = personService.getBySchoolIdOrGetFromExternalBySchoolId(schoolId, new Boolean(false)); //lookup can be slow, but sync/external lookup ideally won't occur
				auditPerson = new AuditPerson();
				auditPerson.setFirstName(modifiedBy.getFirstName());
				auditPerson.setLastName(modifiedBy.getLastName());
				auditPerson.setId(modifiedBy.getId());
				map.put(schoolId, auditPerson);
			} catch (ObjectNotFoundException e) {
				//ignore error and allow null to be returned
			}
		}
		return auditPerson;
	}

	@Override
	public void loadCaseloadBulkAddReassignment(BulkAddCaseloadReassignmentTO uploadItem,
			FileUploadResponse extjsFormResult) throws IOException,
			ObjectNotFoundException, ValidationException {

		CommonsMultipartFile file = uploadItem.getFile();
		
		validateFile(file);

		//Remove all rows from the table
		dao.truncate();

		BufferedReader bReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

		//read file line by line
		String line = null;
		Integer count = new Integer(1);
		while( (line = bReader.readLine()) != null){
			CaseloadBulkAddReassignment model = createCaseloadBulkAddReassignmentFromCSVString(line, count, securityService.currentUser().getPerson().getPrimaryEmailAddress());
			save(model);
			//TODO Need to handle duplicate id exception
		}

		//set extjs return - sucsess
		extjsFormResult.setSuccess(true);
	}

	private CaseloadBulkAddReassignment createCaseloadBulkAddReassignmentFromCSVString(String line, Integer count, String notificationEmailAddress) throws ObjectNotFoundException {

		String[] values = line.replaceAll("^\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?");

		if (values.length < 3 || values.length > 4) {
			throw new IllegalArgumentException("CVS file not formatted properly.  Each row must contain three or four values.  Row " + count + " is invalid.");
		}

		CaseloadBulkAddReassignment caseloadBulkAddReassignment = new CaseloadBulkAddReassignment();
		caseloadBulkAddReassignment.setSchoolId(values[0]);
		caseloadBulkAddReassignment.setCoachSchoolId(values[1]);
		caseloadBulkAddReassignment.setModifiedBySchoolId(values[2]);
		if (values.length == 4) {
			caseloadBulkAddReassignment.setJournalEntryComment(values[3]);
		}
		caseloadBulkAddReassignment.setNotificationEmailAddress(notificationEmailAddress);
		count++;
		return caseloadBulkAddReassignment;
	}

	public CaseloadBulkAddReassignment save(CaseloadBulkAddReassignment obj)
			throws ObjectNotFoundException, ValidationException {
		return dao.create(obj);
	}

	private synchronized void validateFile(CommonsMultipartFile file) {
		//validate file extension 
		String fname = file.getOriginalFilename().trim();
		int extDelimIdx = fname.lastIndexOf(".");
		if ( extDelimIdx < 0 ) {
			throw new IllegalArgumentException("File type not accepted.  Please upload a file of type 'csv'.");
		}
		if ( extDelimIdx == fname.length() - 1 ) {
			throw new IllegalArgumentException("File type not accepted.  Please upload a file of type 'csv'.");
		}
		if ( !("csv".equals(fname.substring(extDelimIdx + 1))) ) {
			throw new IllegalArgumentException("File type not accepted.  Please upload a file of type 'csv'.");
		}
	}
}