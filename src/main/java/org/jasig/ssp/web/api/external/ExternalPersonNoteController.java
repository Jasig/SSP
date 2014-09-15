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
import java.util.UUID;

import org.jasig.ssp.factory.external.ExternalPersonNoteTOFactory;
import org.jasig.ssp.factory.external.ExternalTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalPersonNote;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalPersonNoteService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.external.ExternalPersonNoteTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
@RequestMapping("/1/person/{id}/note")
public class ExternalPersonNoteController extends
		AbstractExternalController<ExternalPersonNoteTO, ExternalPersonNote> {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExternalPersonNoteController.class);

	@Autowired
	private transient PersonService personService;

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
	 * Overridden to raise a {@code UnsupportedOperationException}. This method
	 * makes no sense for this API given the path the controller is currently
	 * rooted in. I.e. this is a person-scoped controller but {@code getAll()}
	 * is system-scoped.
	 *
	 * <p>Took some experimentation to get the mapping overrides to work right.
	 * Settled on this {@code nonsense} path b/c other approaches either
	 * resulted in MVC failing to start b/c of ambigious mappings or this
	 * method ending up as the default handler for all GETs.</p>
	 *
	 * @return
	 */
	@Override
	@RequestMapping(value = "/nonsense", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	PagedResponse<ExternalPersonNoteTO> getAll(
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {
		throw new UnsupportedOperationException("This URI not supported. Try {base-api-url}/1/person/{uuid}/note");
	}
	
	/**
	 * Gets all "legacy notes" for the specified user.
	 * 
	 * @param id
	 *            The user's SSP-internal ID to use to lookup the associated data.
	 * @return The specified notes if any were found.
	 * @throws ObjectNotFoundException
	 *             If specified user
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_PERSON_LEGACY_REMARK_READ')")
	public @ResponseBody
	List<ExternalPersonNoteTO> getNotesForStudent(
			final @PathVariable UUID id)
			throws ObjectNotFoundException {

		final String schoolId = getSchoolId(id);
		final List<ExternalPersonNote> list = getService().getNotesBySchoolId(schoolId);

		return factory.asTOList(list);
	}

	private String getSchoolId(UUID personId) throws ObjectNotFoundException{
		return personService.getSchoolIdForPersonId(personId);
	}
}
