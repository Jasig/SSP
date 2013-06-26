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

import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.ScheduledTaskWrapperService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskWrapperServiceImpl implements ScheduledTaskWrapperService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ScheduledTaskWrapperServiceImpl.class);

	@Autowired
	private transient ExternalPersonService externalPersonService;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient TaskService taskService;

	@Value("#{configProperties.scheduled_coach_sync_enabled}")
	private boolean scheduledCoachSyncEnabled;

	// For all of the scheduled methods below... don't need to call
	// securityService.currentFallingBackToAdmin()... it doesn't actually do any
	// good... it won't be cached as the "current" user. Do need to clean up
	// tho, b/c when securityService.currentFallingBackToAdmin() *is* called in
	// the depths of syncCoaches(), threadlocals will be set

	@Override
	@Scheduled(fixedDelay = 150000)
	// run 2.5 minutes after the end of the last invocation
	public void sendMessages() {
		try {
			messageService.sendQueuedMessages();
		} finally {
			securityService.afterRequest();
		}
	}

	@Override
	@Scheduled(fixedDelay = 300000)
	// run every 5 minutes
	public void syncCoaches() {
		if ( !(scheduledCoachSyncEnabled) ) {
			LOGGER.debug("Scheduled coach sync disabled. Abandoning sync job");
			return;
		}
		try {
			LOGGER.info("Scheduled coach sync starting.");
			PagingWrapper<Person> localCoaches = personService.syncCoaches();
			LOGGER.info("Scheduled coach sync complete. Local coach count {}",
					localCoaches.getResults());
		} finally {
			securityService.afterRequest();
		}
	}

	@Override
	//@Scheduled(fixedDelay = 300000)
	// run every 5 minutes
	public void syncExternalPersons() {
		try {
			externalPersonService.syncWithPerson();
		} finally {
			securityService.afterRequest();
		}
	}

	@Override
	@Scheduled(cron = "0 0 1 * * *")
	// run at 1 am every day
	public void sendTaskReminders() {
		try {
		taskService.sendAllTaskReminderNotifications();
		} finally {
			securityService.afterRequest();
		}
	}
}
