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

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.PersonSearchResult2TOFactory;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.CaseloadReassignmentRequestTO;
import org.jasig.ssp.transferobject.PersonSearchResult2TO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
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
	private transient PersonSearchService service;

	@Autowired
	private transient ProgramStatusService programStatusService;

	@Autowired
	private transient PersonSearchResult2TOFactory factory;

	@Autowired
	private transient SecurityService securityService;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
 
	@RequestMapping(value = "/caseload", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<PersonSearchResult2TO> myCaseload(
			final @RequestParam(required = false) UUID programStatusId,
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
		
		final PagingWrapper<PersonSearchResult2> caseload = service.caseLoadFor(
				programStatus, securityService.currentUser().getPerson(),
				 buildSortAndPage( limit,  start,  sort,  sortDirection));

		return new PagedResponse<PersonSearchResult2TO>(true, caseload.getResults(),
				factory.asTOList(caseload.getRows()));
	}
	
	@RequestMapping(value = "/caseload/count", method = RequestMethod.GET)
	public @ResponseBody
	Long myCaseloadCount(
			final @RequestParam(required = false) UUID programStatusId,
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
		
		return service.caseLoadCountFor(
				programStatus, securityService.currentUser().getPerson(),
				 buildSortAndPage( limit,  start,  sort,  sortDirection));
	}

	@RequestMapping(value = "/{personId}/caseload", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<PersonSearchResult2TO> caseloadFor(
			@PathVariable final UUID personId,
			final @RequestParam(required = false) UUID programStatusId,
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
		

		final PagingWrapper<PersonSearchResult2> caseload = service.caseLoadFor(
				programStatus, personService.get(personId),
				 buildSortAndPage( limit,  start,  sort,  sortDirection));

		return new PagedResponse<PersonSearchResult2TO>(true, caseload.getResults(),
				factory.asTOList(caseload.getRows()));
	}
	
	private SortingAndPaging buildSortAndPage(Integer limit, Integer start, String sort, String sortDirection){
		String sortConfigured = sort == null ? "dp.lastName":"dp."+sort;
		if(sortConfigured.equals("dp.coach")){
			sortConfigured = "dp.coachLastName";
		}else if(sortConfigured.equals("dp.currentProgramStatusName")){
			sortConfigured = "dp.programStatusName";
		}else if(sortConfigured.equals("dp.numberOfEarlyAlerts")){
			sortConfigured = "dp.activeAlertsCount";
		}else if(sortConfigured.equals("dp.studentType")){
			sortConfigured = "dp.studentTypeName";
		}
		SortingAndPaging sortAndPage = SortingAndPaging
		.createForSingleSortWithPaging(ObjectStatus.ALL, start, limit, sortConfigured,
				sortDirection, "dp.lastName");
		if(sortConfigured.equals("dp.coachLastName")){
			sortAndPage.prependSortField("dp.coachFirstName", SortDirection.getSortDirection(sortDirection));
		}
		return sortAndPage;
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
