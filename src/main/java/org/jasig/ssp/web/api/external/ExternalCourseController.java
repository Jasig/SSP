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
import java.util.UUID;

import org.jasig.ssp.factory.external.ExternalCourseTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalCourse;
import org.jasig.ssp.model.reference.Tag;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalCourseService;
import org.jasig.ssp.transferobject.external.ExternalCourseTO;
import org.jasig.ssp.transferobject.external.ExternalCourseTermResponseTO;
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
@RequestMapping("/1/reference/course")
public class ExternalCourseController extends AbstractExternalController<ExternalCourseTO, ExternalCourse> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalCourseController.class);

	@Autowired
	protected transient ExternalCourseService service;

	@Override
	protected ExternalCourseService getService() {
		return service;
	}

	@Autowired
	protected transient ExternalCourseTOFactory factory;

	@Override
	protected ExternalCourseTOFactory getFactory() {
		return factory;
	}

	protected ExternalCourseController() {
		super(ExternalCourseTO.class, ExternalCourse.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	List<ExternalCourseTO> getAllCourses() {
		List<ExternalCourseTO> response = new ArrayList<ExternalCourseTO>();
		List<ExternalCourse> allCourses = getService().getAll();
		for (ExternalCourse externalCourse : allCourses) {
			response.add(getFactory().from(externalCourse));
		}
		return response;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	List<ExternalCourseTO> searchCourses(final @RequestParam(required = false) String programCode,
			final @RequestParam(required = false) String tag,
			final @RequestParam(required = false) String termCode) {
		List<ExternalCourseTO> response = new ArrayList<ExternalCourseTO>();
		List<ExternalCourse> allCourses = getService().search(programCode,tag,termCode);
		for (ExternalCourse externalCourse : allCourses) {
			response.add(getFactory().from(externalCourse));
		}
		return response;
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	ExternalCourseTO get(final @PathVariable String code) throws ObjectNotFoundException,
			ValidationException {
		final ExternalCourse model = getService().getByCode(code);
		if (model == null) {
			return null;
		}
		ExternalCourseTO externalCourseTO = new ExternalCourseTO(model);
		
		//Kludge:  for the sake of implementation time we are pivoting these tags on the client side.  
		List<Tag> tags = service.getAllTagsForCourse(code);
		StringBuilder tagBuilder = new StringBuilder();
		for (Tag tag : tags) {
			tagBuilder.append(tag.getCode()+" ");
		}
		//end Kludge.
		externalCourseTO.setTags(tagBuilder.toString().trim());
		return externalCourseTO;
	}
	
	@RequestMapping(value = "/validateTerm", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	ExternalCourseTermResponseTO validateTerm(final @RequestParam String courseCode, final @RequestParam String termCode) throws ObjectNotFoundException,
			ValidationException {
		final Boolean model = getService().validateCourseForTerm(courseCode,termCode);
		if (model == null) {
			return new ExternalCourseTermResponseTO(false);
		}
		return new ExternalCourseTermResponseTO(model);
	}
	
}