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
package org.jasig.mygps.web;

import java.util.UUID;

import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.PlanCourseTO;
import org.jasig.ssp.transferobject.PlanOutputTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.TermNoteTO;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/mygps/plan")
public class MyGpsPlanController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsPlanController.class);

	@Autowired
	private transient PlanService service;

	@Autowired
	private transient SecurityService securityService;

	@PreAuthorize("hasRole('ROLE_MY_GPS_TOOL')")
	@RequestMapping(value="/current", method = RequestMethod.GET)
	public @ResponseBody
	PlanTO current() throws ObjectNotFoundException, ValidationException {
		final UUID currentPersonId = securityService.currentUser().getPerson().getId();
		final Plan model = service.getCurrentForStudent(currentPersonId);
		if (model == null) {
			return null;
		}
		return stripAdvisorNotes(service.validate(new PlanTO(model)));
	}

	// This was previously an overload in PlanController but relocated here
	// to match conventions for dealing with APIs where a GPS permission grant
	// gives users access to ones own data. Most of the non-GPS APIs give
	// you access to *anyone's* data if you hold the permission for any given
	// API
	@PreAuthorize("hasRole('ROLE_MY_GPS_TOOL')")
	@RequestMapping(value="/print", method = RequestMethod.GET)
	public @ResponseBody
	String print() throws ObjectNotFoundException, ValidationException {
		PlanOutputTO planOutputDataTO = new PlanOutputTO();

		final UUID currentPersonId = securityService.currentUser().getPerson().getId();
		final Plan model = service.getCurrentForStudent(currentPersonId);
		if (model == null) {
			return null;
		}
		planOutputDataTO.setPlan(new PlanTO(model));
		planOutputDataTO.setOutputFormat(PlanService.OUTPUT_FORMAT_MATRIX);
		planOutputDataTO = stripAdvisorNotes(planOutputDataTO);

		SubjectAndBody message = service.createOutput(planOutputDataTO);
		if(message != null)
			return message.getBody();

		return null;
	}

	// Put the advisor note-stripping functions here rather than in a service
	// method b/c we need this for both inputs and outputs, but there's no good
	// way right now to clone the TO so there's no polite way for a service
	// operation to filter fields on a TO before passing it on to another
	// service, as PlanService.createOutput() would need to do. This is
	// acceptable so long as this is the only place that needs to perfom this
	// sort of "note stripping", but will need to be refactored if other call
	// sites need it.

	private PlanOutputTO stripAdvisorNotes(PlanOutputTO planOutputDataTO) {
		planOutputDataTO.setPlan(stripAdvisorNotes(planOutputDataTO.getPlan()));
		return planOutputDataTO;
	}

	private PlanTO stripAdvisorNotes(PlanTO planTO) {
		planTO.setContactNotes(null);
		for ( PlanCourseTO courseTO : planTO.getPlanCourses() ) {
			courseTO.setContactNotes(null);
		}
		for ( TermNoteTO termNoteTO : planTO.getTermNotes() ) {
			termNoteTO.setContactNotes(null);
		}
		return planTO;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
