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
package org.jasig.ssp.web.api.external;

import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.factory.external.ExternalCourseTOFactory;
import org.jasig.ssp.factory.external.ExternalDivisionTOFactory;
import org.jasig.ssp.factory.external.ExternalProgramTOFactory;
import org.jasig.ssp.model.external.ExternalCourse;
import org.jasig.ssp.model.external.ExternalDivision;
import org.jasig.ssp.model.external.ExternalProgram;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalCourseService;
import org.jasig.ssp.service.external.ExternalDivisionService;
import org.jasig.ssp.service.external.ExternalProgramService;
import org.jasig.ssp.transferobject.external.ExternalCourseTO;
import org.jasig.ssp.transferobject.external.ExternalCourseTermResponseTO;
import org.jasig.ssp.transferobject.external.ExternalDivisionTO;
import org.jasig.ssp.transferobject.external.ExternalProgramTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/reference/division")
public class ExternalDivisionController extends AbstractExternalController<ExternalDivisionTO, ExternalDivision> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalDivisionController.class);

	@Autowired
	protected transient ExternalDivisionService service;

	@Override
	protected ExternalDivisionService getService() {
		return service;
	}

	@Autowired
	protected transient ExternalDivisionTOFactory factory;

	@Override
	protected ExternalDivisionTOFactory getFactory() {
		return factory;
	}

	protected ExternalDivisionController() {
		super(ExternalDivisionTO.class, ExternalDivision.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	List<ExternalDivisionTO> getAllPrograms() {
		List<ExternalDivisionTO> response = new ArrayList<ExternalDivisionTO>();
		List<ExternalDivision> allDivisions = getService().getAll();
		for (ExternalDivision externalDivision : allDivisions) {
			response.add(getFactory().from(externalDivision));
		}
		return response;
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	ExternalDivisionTO get(final @PathVariable String code) throws ObjectNotFoundException,
			ValidationException {
		final ExternalDivision model = getService().getByCode(code);
		if (model == null) {
			return null;
		}
		return new ExternalDivisionTO(model);
	}
	
	
}