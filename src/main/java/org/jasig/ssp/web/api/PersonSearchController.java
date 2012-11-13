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

import org.jasig.ssp.factory.PersonSearchResultTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
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
@PreAuthorize("hasRole('ROLE_PERSON_SEARCH_READ')")
@RequestMapping("/1/person/search")
public class PersonSearchController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonSearchController.class);

	@Autowired
	private transient PersonSearchService service;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient PersonSearchResultTOFactory factory;

	@Autowired
	private transient SecurityService securityService;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<PersonSearchResultTO> search(
			final @RequestParam String searchTerm,
			final @RequestParam(required = false) UUID programStatusId,
			// ignored if programStatusId is non-null
			final @RequestParam(required = false) Boolean requireProgramStatus,
			final @RequestParam(required = false) Boolean outsideCaseload,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException, ValidationException {

		ProgramStatus programStatus = null;
		if (null != programStatusId) {
			programStatus = programStatusService.get(programStatusId);
		}

		final PagingWrapper<PersonSearchResult> results = service.searchBy(
				programStatus, requireProgramStatus, outsideCaseload, searchTerm,
				securityService.currentUser().getPerson(),
				SortingAndPaging.createForSingleSort(status, start, limit,
						sort, sortDirection, null));

		return new PagedResponse<PersonSearchResultTO>(true,
				results.getResults(), factory.asTOList(results.getRows()));
	}
}