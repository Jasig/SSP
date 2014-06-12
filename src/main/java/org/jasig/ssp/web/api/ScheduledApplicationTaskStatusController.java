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
package org.jasig.ssp.web.api;

import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ScheduledApplicationTaskStatusService;
import org.jasig.ssp.transferobject.ScheduledApplicationTaskStatusTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/scheuledapplicationtask/")
public class ScheduledApplicationTaskStatusController
		extends AbstractBaseController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ScheduledApplicationTaskStatusController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@Autowired
	private ScheduledApplicationTaskStatusService service;
	

	@RequestMapping(method = RequestMethod.GET, params = "name")
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	ScheduledApplicationTaskStatusTO getByName(@RequestParam String name)
			throws ObjectNotFoundException {
		final ScheduledApplicationTaskStatus status = service.getByName(name);
		if(status == null)
			return null;
		return new ScheduledApplicationTaskStatusTO(status);
	}
	
	@RequestMapping(value="/reset", method = RequestMethod.PUT, params = "name")
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	ScheduledApplicationTaskStatusTO reset(@RequestParam String name)
			throws ObjectNotFoundException {
		final ScheduledApplicationTaskStatus status = service.getByName(name);
		if(status == null)
			return null;
		return new ScheduledApplicationTaskStatusTO(service.resetTask(name));
	}
}
