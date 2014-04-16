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

import com.google.common.collect.Lists;
import org.jasig.ssp.factory.external.ExternalPersonNoteTOFactory;
import org.jasig.ssp.factory.external.ExternalTOFactory;
import org.jasig.ssp.factory.external.RegistrationStatusByTermTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.external.ExternalPersonNote;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.external.ExternalPersonNoteService;
import org.jasig.ssp.service.external.RegistrationStatusByTermService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.external.ExternalPersonNoteTO;
import org.jasig.ssp.transferobject.external.RegistrationStatusByTermTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/person/{id}/registrationStatusByTerm")
public class ExternalPersonRegistrationStatusByTermController extends AbstractBaseController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FacultyCourseController.class);

	@Autowired
	private transient PersonService personService;

	@Autowired
	protected transient RegistrationStatusByTermTOFactory factory;
	
	@Autowired
	protected transient RegistrationStatusByTermService service;

	protected ExternalPersonRegistrationStatusByTermController() {
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	protected ExternalTOFactory<RegistrationStatusByTermTO, RegistrationStatusByTerm> getFactory() {
		return factory;
	}
	
	protected RegistrationStatusByTermService getService() {
		return service;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	List<RegistrationStatusByTermTO> getRegistrationStatusByTermForStudent(
			final @PathVariable UUID id)
			throws ObjectNotFoundException {

		Person person = new Person(id);
		person.setSchoolId(personService.getSchoolIdForPersonId(id));
		try {
			final List<RegistrationStatusByTerm> list = getService().getCurrentAndFutureTerms(person);
			return factory.asTOList(list);
		} catch ( ObjectNotFoundException e ) {
			return Lists.newArrayListWithCapacity(0);
		}
	}
}
