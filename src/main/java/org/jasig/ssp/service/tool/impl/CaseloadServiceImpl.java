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

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.CaseloadBulkAddReassignmentDao;
import org.jasig.ssp.dao.CaseloadDao;
import org.jasig.ssp.model.*;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.*;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.service.tool.CaseloadService;
import org.jasig.ssp.transferobject.BulkAddCaseloadReassignmentTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
	private CaseloadBulkAddReassignmentDao bulkReassignDao;

    @Autowired
    private transient CaseloadDao caseloadDao;
	
	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient PersonService personService;

    @Autowired
    private transient PersonProgramStatusService personProgramStatusService;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient MessageTemplateService messageTemplateService;


	protected CaseloadBulkAddReassignmentDao getBulkDao() {
		return bulkReassignDao;
	}


	@Override
	public void processCaseloadBulkAddReassignment() {
		final Map<String, AuditPerson> auditPersonMap = new HashMap<String, AuditPerson>();
		final List<String> errors = new ArrayList<String>();
		final List<CaseloadBulkAddReassignment> list = bulkReassignDao.getAll();
		String notificationEmailAddress = null;

        if (list.size() > 0) {
			int successCount = 0;
			for (CaseloadBulkAddReassignment model : list) {
				if (notificationEmailAddress==null) {
					notificationEmailAddress = model.getNotificationEmailAddress();
				}

				if (StringUtils.isNotBlank(model.getSchoolId())) {
					try {
						Person student = personService.getInternalOrExternalPersonBySchoolId(model.getSchoolId(), true, true); //slow, but need to add external only students

                        if (StringUtils.isNotBlank(model.getCoachSchoolId()) || student.getCoach() != null) {
                            if (StringUtils.isBlank(student.getCurrentProgramStatusName())) {
                                try {
                                    personProgramStatusService.setActiveForStudent(student);
                                } catch (ObjectNotFoundException | ValidationException onfve) {
                                    createError(errors, "Active Program Status not found", model);
                                }
                            }

                            Person coach = null;
                            if (StringUtils.isNotBlank(model.getCoachSchoolId())) {
                                try {
                                    coach = personService.getInternalOrExternalPersonBySchoolId(model.getCoachSchoolId(), true, false); //slow, but very rare need to add external only coach

                                    if (coach!= null && student.getCoach() == null) {
                                        student.setCoach(coach);
                                    }
                                } catch (ObjectNotFoundException e) {
                                    createError(errors, "Coach School Id not found for record", model);
                                }
                            } else {
                                coach = student.getCoach();
                            }

                            AuditPerson auditPerson = null;
                            if (StringUtils.isNotBlank(model.getModifiedBySchoolId())) {
                                auditPerson = getAuditPerson(auditPersonMap, model.getModifiedBySchoolId());
                            } else {
                                final SspUser sspUser = (SspUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                                auditPerson = new AuditPerson(sspUser.getPerson().getId());
                                createError(errors, "Modified By School Id not found for record, using current or system user", model);
                            }

                            caseloadDao.reassignStudentWithSpecifiedModifier(student, coach, auditPerson);
                            successCount++;

						} else {
							createError(errors, "Student not added because student does not have a coach assigned for record nor was a coach assigned in the csv file", model);
						}
					} catch (ObjectNotFoundException e) {
						createError(errors, "Student School Id not found for record", model);
					} catch (Exception e) {
						createError(errors, "Error saving the student for bulk add/reassign: " + e.getMessage(), model);
					}
				} else {
					createError(errors, "Student School Id was not set for record", model);
				}
			}
            bulkReassignDao.truncate();

			if (notificationEmailAddress==null) {
				notificationEmailAddress = securityService.currentUser().getPerson().getPrimaryEmailAddress();
			}

			final SubjectAndBody subjectAndBody = messageTemplateService.createBulkAddCaseloadReassignmentMessage(successCount, errors);
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
		final String message = error + " --> " + model.toString();
		errors.add(message);
		LOGGER.warn(message);
	}

	private AuditPerson getAuditPerson(Map<String, AuditPerson> map, String schoolId) {
		AuditPerson auditPerson = map.get(schoolId);
		if (auditPerson==null) {
			try {
				final Person modifiedBy = personService.getInternalOrExternalPersonBySchoolId(schoolId, true, false); //slow, but external lookup ideally won't occur. Plus need to add external only in order to set person.id
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

		final CommonsMultipartFile file = uploadItem.getFile();
		
		validateFile(file);

		//Remove all rows from the table
        bulkReassignDao.truncate();

		final BufferedReader bReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

		//read file line by line
		String line;
		Integer count = new Integer(1);
		while( (line = bReader.readLine()) != null){
			final CaseloadBulkAddReassignment model = createCaseloadBulkAddReassignmentFromCSVString(line, count, securityService.currentUser().getPerson().getPrimaryEmailAddress());
			save(model);
			//TODO Need to handle duplicate id exception
		}

		//set extjs return - sucsess
		extjsFormResult.setSuccess(true);
	}

	private CaseloadBulkAddReassignment createCaseloadBulkAddReassignmentFromCSVString(String line, Integer count, String notificationEmailAddress) throws ObjectNotFoundException {

		final String[] values = line.replaceAll("^\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?");

		if (values.length < 3 || values.length > 4) {
			throw new IllegalArgumentException("CVS file not formatted properly.  Each row must contain three or four values.  Row " + count + " is invalid.");
		}

		final CaseloadBulkAddReassignment caseloadBulkAddReassignment = new CaseloadBulkAddReassignment();
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
		return bulkReassignDao.create(obj);
	}

	private synchronized void validateFile(CommonsMultipartFile file) {
		//validate file extension 
		final String fname = file.getOriginalFilename().trim();

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