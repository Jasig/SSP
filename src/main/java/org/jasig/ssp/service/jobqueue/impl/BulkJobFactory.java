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
package org.jasig.ssp.service.jobqueue.impl;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.model.jobqueue.WorkflowStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.jobqueue.BulkEmailStudentRequestForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class BulkJobFactory  {

	@Autowired
	private transient PersonService personService;
	
	@Autowired
	private transient SecurityService securityService;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BulkJobFactory.class);

	public Job getNewJob(BulkEmailStudentRequestForm form,
			String serializedJobRequest) throws ObjectNotFoundException {
		
		Person authenticatedPerson = personService.get(securityService.currentlyAuthenticatedUser().getPerson().getId());
		Job job = new Job();
		job.setExecutionComponentName("bulk-email-executor");
		job.setExecutionSpec(serializedJobRequest);
		job.setScheduledByProcess("TEST");
		job.setRunAs(authenticatedPerson);
		job.setOwner(authenticatedPerson);
		job.setWorkflowStatus(WorkflowStatus.QUEUED);
		job.setExecutionState(serializedJobRequest);
		return job;
	}
	
}
