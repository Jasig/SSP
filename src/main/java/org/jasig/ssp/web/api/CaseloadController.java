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

import org.jasig.ssp.factory.CaseloadRecordTOFactory;
import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.CaseloadService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.CaseloadReassignmentRequestTO;
import org.jasig.ssp.transferobject.CaseloadRecordTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthorize("hasRole('ROLE_PERSON_CASELOAD_READ')")
@RequestMapping("/1/person")
public class CaseloadController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CaseloadController.class);

	@Autowired
	private transient PersonService personService;

	@Autowired
	private transient CaseloadService service;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient CaseloadRecordTOFactory factory;

	@Autowired
	private transient SecurityService securityService;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
 
	@RequestMapping(value = "/caseload", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<CaseloadRecordTO> myCaseload(
			final @RequestParam(required = false) UUID programStatusId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		ProgramStatus programStatus = null;
		if (null != programStatusId) {
			programStatus = programStatusService.get(programStatusId);
		}

		final PagingWrapper<CaseloadRecord> caseload = service.caseLoadFor(
				programStatus, securityService.currentUser().getPerson(),
				SortingAndPaging.createForSingleSortWithPaging(status, start, limit,
						sort, sortDirection, "lastName"));

		return new PagedResponse<CaseloadRecordTO>(true, caseload.getResults(),
				factory.asTOList(caseload.getRows()));
	}

	@RequestMapping(value = "/{personId}/caseload", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<CaseloadRecordTO> caseloadFor(
			@PathVariable final UUID personId,
			final @RequestParam(required = false) UUID programStatusId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		ProgramStatus programStatus = null;
		if (null != programStatusId) {
			programStatus = programStatusService.get(programStatusId);
		}

		final PagingWrapper<CaseloadRecord> caseload = service.caseLoadFor(
				programStatus, personService.get(personId),
				SortingAndPaging.createForSingleSortWithPaging(status, start, limit,
						sort, sortDirection, "lastName"));

		return new PagedResponse<CaseloadRecordTO>(true, caseload.getResults(),
				factory.asTOList(caseload.getRows()));
	}
	
	@RequestMapping(value = "/caseload", method = RequestMethod.POST)
	@PreAuthorize(Permission.SECURITY_PERSON_BULK_REASSIGN)
	public @ResponseBody
	CaseloadReassignmentRequestTO reassignStudents(
			final @RequestBody CaseloadReassignmentRequestTO obj)
			throws IllegalArgumentException, ObjectNotFoundException {
		service.reassignStudents(obj);
		return obj;
	}
}
