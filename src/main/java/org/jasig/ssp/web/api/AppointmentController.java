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

import java.util.UUID;

import javax.validation.Valid;

import org.jasig.mygps.business.StudentIntakeRequestManager;
import org.jasig.ssp.factory.AppointmentTOFactory;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.Appointment;
import org.jasig.ssp.service.AppointmentService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.AppointmentTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Appointment controller
 */
@Controller
@RequestMapping("/1/person/{personId}/appointment")
public class AppointmentController
		extends AbstractPersonAssocController<Appointment, AppointmentTO> {

	protected AppointmentController() {
		super(Appointment.class, AppointmentTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppointmentController.class);

	@Autowired
	protected transient AppointmentService service;

	@Autowired
	protected transient AppointmentTOFactory factory;

	@Autowired
	protected transient StudentIntakeRequestManager intakeRequestManager;
	
	@Override
	protected AppointmentService getService() {
		return service;
	}

	@Override
	protected TOFactory<AppointmentTO, Appointment> getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	public String permissionBaseName() {
		return "APPOINTMENT";
	}

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	AppointmentTO getCurrentAppointmentForPerson(
			final @PathVariable UUID personId) throws ObjectNotFoundException {

		checkPermissionForOp("READ");

		final Appointment appt = service.getCurrentAppointmentForPerson(
				personService.get(personId));
		if (appt == null) {
			return null;
		}

		return factory.from(appt);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@DynamicPermissionChecking
	@Override
	public @ResponseBody
	AppointmentTO save(@PathVariable final UUID id,
			@PathVariable final UUID personId,
			@Valid @RequestBody final AppointmentTO obj)
			throws ObjectNotFoundException, ValidationException {

		AppointmentTO appointment = super.save(id, personId, obj);
		if(obj.isStudentIntakeRequested() && appointment != null)
		{
			intakeRequestManager.processStudentIntakeRequest(appointment);
		}
		return appointment;
		
	}
}
