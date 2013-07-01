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

import java.util.List;

import org.jasig.ssp.factory.external.ExternalPersonNoteTOFactory;
import org.jasig.ssp.factory.external.ExternalTOFactory;
import org.jasig.ssp.model.external.ExternalPersonNote;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalPersonNoteService;
import org.jasig.ssp.transferobject.external.ExternalPersonNoteTO;
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
@RequestMapping("/1/person/{schoolId}/note")
public class ExternalPersonNoteController extends
		AbstractExternalController<ExternalPersonNoteTO, ExternalPersonNote> {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FacultyCourseController.class);
	
	@Autowired
	protected transient ExternalPersonNoteTOFactory factory;
	
	@Autowired
	protected transient ExternalPersonNoteService service;

	protected ExternalPersonNoteController() {
		super(ExternalPersonNoteTO.class, ExternalPersonNote.class);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@Override
	protected ExternalTOFactory<ExternalPersonNoteTO, ExternalPersonNote> getFactory() {
		return factory;
	}
	
	@Override
	protected ExternalPersonNoteService getService() {
		return service;
	}
	
	/**
	 * Gets all courses for the specified faculty.
	 * 
	 * @param schoolId
	 *            The student school id to use to lookup the associated data.
	 * @return The specified notes if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	List<ExternalPersonNoteTO> getNotesForStudent(
			final @PathVariable String schoolId)
			throws ObjectNotFoundException {
		final List<ExternalPersonNote> list = getService().getNotesBySchoolId(schoolId);

		return factory.asTOList(list);
	}
}
