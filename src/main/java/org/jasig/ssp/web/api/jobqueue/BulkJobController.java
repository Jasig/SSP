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
package org.jasig.ssp.web.api.jobqueue;

import java.io.IOException;

import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.jobqueue.JobService;
import org.jasig.ssp.service.jobqueue.impl.AbstractBulkRunnable;
import org.jasig.ssp.service.jobqueue.impl.BulkJobFactory;
import org.jasig.ssp.transferobject.jobqueue.BulkEmailStudentRequestForm;
import org.jasig.ssp.transferobject.jobqueue.BulkTO;
import org.jasig.ssp.transferobject.jobqueue.JobTO;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/bulk")
public class BulkJobController  extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BulkJobController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@Autowired 
	private BulkJobFactory bulkJobFactory;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private transient SecurityService securityService;
	
	@PreAuthorize("hasRole('ROLE_PERSON_WRITE') and hasRole('ROLE_BULK_EMAIL_STUDENT')")
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public @ResponseBody
	JobTO bulkEmail(@RequestBody BulkEmailStudentRequestForm form )
			throws ObjectNotFoundException, ValidationException, IOException {
		
		String serializedJobRequest = AbstractBulkRunnable.serialize(form);
		Job job = bulkJobFactory.getNewJob(form,serializedJobRequest);
		jobService.create(job);
		return new JobTO(job);
	}
	

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}


	}
