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
package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.TaskDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.TaskMessageEnqueueService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.TaskTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Task transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class TaskTOFactoryImpl extends
		AbstractAuditableTOFactory<TaskTO, Task>
		implements TaskTOFactory {

	public TaskTOFactoryImpl() {
		super(TaskTO.class, Task.class);
	}

	@Autowired
	private transient TaskDao dao;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient ChallengeService challengeService;

	@Autowired
	private transient ChallengeReferralService challengeReferralService;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;
	
	@Autowired
	private transient TaskMessageEnqueueService taskMessageSentService;

	@Override
	protected TaskDao getDao() {
		return dao;
	}

	@Override
	public Task from(final TaskTO tObject)
			throws ObjectNotFoundException {
		final Task model = super.from(tObject);

		model.setName(tObject.getName());
		model.setDescription(tObject.getDescription());
		model.setLink(tObject.getLink());
		model.setDeletable(tObject.isDeletable());
		model.setDueDate(tObject.getDueDate());
		model.setCompletedDate(tObject.getCompletedDate());
		// reminder sent date should only be set by the system

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		if (tObject.getChallengeId() != null) {
			model.setChallenge(challengeService.get(tObject.getChallengeId()));
		}

		if (tObject.getChallengeReferralId() != null) {
			model.setChallengeReferral(challengeReferralService.get(tObject
					.getChallengeReferralId()));
		}

		if ((tObject.getConfidentialityLevel() == null)
				|| (tObject.getConfidentialityLevel().getId() == null)) {
			model.setConfidentialityLevel(null);
		} else {
			model.setConfidentialityLevel(confidentialityLevelService
					.get(tObject.getConfidentialityLevel().getId()));
		}
		
		if(tObject.getMessageSentIds() != null && !tObject.getMessageSentIds().isEmpty()){
			model.setMessagesSent(taskMessageSentService.getAllFromIds(tObject.getMessageSentIds()));
		}

		return model;
	}
}