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
package org.jasig.ssp.web.api; // NOPMD

import java.util.UUID;

import org.jasig.ssp.factory.PersonProgramStatusTOFactory;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.transferobject.PersonProgramStatusTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Services to manipulate ProgramStatuss.
 * <p>
 * Mapped to URI path <code>/1/person/{personId}/programStatus</code>
 */
@Controller
@RequestMapping("/1/person/{personId}/programStatus")
public class PersonProgramStatusController
		extends
		AbstractPersonAssocController<PersonProgramStatus, PersonProgramStatusTO> {

	protected PersonProgramStatusController() {
		super(PersonProgramStatus.class, PersonProgramStatusTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonProgramStatusController.class);

	@Autowired
	private transient PersonProgramStatusService service;

	@Autowired
	private transient PersonProgramStatusTOFactory factory;

	@Override
	protected PersonProgramStatusTOFactory getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected PersonProgramStatusService getService() {
		return service;
	}

	@Override
	public String permissionBaseName() {
		return "PROGRAM_STATUS";
	}

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	PersonProgramStatusTO getCurrent(@PathVariable final UUID personId)
			throws ObjectNotFoundException, ValidationException {

		checkPermissionForOp("READ");

		final PersonProgramStatus model = getService().getCurrent(personId);
		if (model == null) {
			return null;
		}

		return instantiateTO(model);
	}
}