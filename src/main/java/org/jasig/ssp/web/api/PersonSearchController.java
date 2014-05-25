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

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jasig.ssp.factory.PersonSearchRequestTOFactory;
import org.jasig.ssp.factory.PersonSearchResult2TOFactory;
import org.jasig.ssp.factory.PersonSearchResultTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.PersonSearchResult;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSearchService;
import org.jasig.ssp.service.RequestTrustService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PersonSearchResult2TO;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/person")
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
	private transient PersonSearchResult2TOFactory factory2;

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient RequestTrustService requestTrustService;
	
	@Autowired
	private transient PersonSearchRequestTOFactory personSearchRequestFactory;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@DynamicPermissionChecking
	@RequestMapping(value="/search", method = RequestMethod.GET)
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
			final @RequestParam(required = false) String sortDirection,
			final HttpServletRequest request)
			throws ObjectNotFoundException, ValidationException {

		assertSearchApiAuthorization(request);

		ProgramStatus programStatus = null;
		if (null != programStatusId) {
			programStatus = programStatusService.get(programStatusId);
		}

		final PagingWrapper<PersonSearchResult> results = service.searchBy(
				programStatus, requireProgramStatus, outsideCaseload, searchTerm,
				securityService.currentUser().getPerson(),
				SortingAndPaging.createForSingleSortWithPaging(status, start, limit,
						sort, sortDirection, null));

		return new PagedResponse<PersonSearchResultTO>(true,
				results.getResults(), factory.asTOList(results.getRows()));
	}
	

	@DynamicPermissionChecking
	@ResponseBody
	@RequestMapping(value="/students/search", method = RequestMethod.GET)
	PagedResponse<PersonSearchResult2TO>  search2(	
	 final @RequestParam(required = false) String studentId,
	 final @RequestParam(required = false) String programStatus,
	 final @RequestParam(required = false) String coachId,
	 final @RequestParam(required = false) String declaredMajor,
	 final @RequestParam(required = false) BigDecimal hoursEarnedMin,
	 final @RequestParam(required = false) BigDecimal hoursEarnedMax,
	 final @RequestParam(required = false) BigDecimal gpaEarnedMin,
	 final @RequestParam(required = false) BigDecimal gpaEarnedMax,
	 final @RequestParam(required = false) Boolean currentlyRegistered,
	 final @RequestParam(required = false) String earlyAlertResponseLate,
	 final @RequestParam(required = false) String sapStatusCode,
	 final @RequestParam(required = false) String specialServiceGroup,
	 final @RequestParam(required = false)String mapStatus,
	 final @RequestParam(required = false)String planStatus,
	 final @RequestParam(required = false) Boolean myCaseload,
	 final @RequestParam(required = false) Boolean myPlans,
	 final @RequestParam(required = false) Date birthDate,
	 final HttpServletRequest request) throws ObjectNotFoundException
	 {
		assertSearchApiAuthorization(request);

		final PagingWrapper<PersonSearchResult2> models = service.search2(personSearchRequestFactory.from(studentId,null, null,programStatus,specialServiceGroup, coachId,declaredMajor,
				hoursEarnedMin,hoursEarnedMax,gpaEarnedMin,gpaEarnedMax,currentlyRegistered,earlyAlertResponseLate,sapStatusCode,mapStatus,planStatus,myCaseload,myPlans,birthDate));
		return new PagedResponse<PersonSearchResult2TO>(true,
				models.getResults(), factory2.asTOList(models.getRows()));	
	}
	
	@DynamicPermissionChecking
	@ResponseBody
	@RequestMapping(value="/directoryperson/search", method = RequestMethod.GET)
	PagedResponse<PersonSearchResult2TO>  personDirectorySearch(	
	 final @RequestParam(required = false) String schoolId,
	 final @RequestParam(required = false) String firstName,
	 final @RequestParam(required = false) String lastName,
	 final @RequestParam(required = false) String programStatus,
	 final @RequestParam(required = false) String coachId,
	 final @RequestParam(required = false) String declaredMajor,
	 final @RequestParam(required = false) BigDecimal hoursEarnedMin,
	 final @RequestParam(required = false) BigDecimal hoursEarnedMax,
	 final @RequestParam(required = false) BigDecimal gpaEarnedMin,
	 final @RequestParam(required = false) BigDecimal gpaEarnedMax,
	 final @RequestParam(required = false) Boolean currentlyRegistered,
	 final @RequestParam(required = false) String earlyAlertResponseLate,
	 final @RequestParam(required = false) String sapStatusCode,
	 final @RequestParam(required = false) String specialServiceGroup,
	 final @RequestParam(required = false)String mapStatus,
	 final @RequestParam(required = false)String planStatus,
	 final @RequestParam(required = false) Boolean myCaseload,
	 final @RequestParam(required = false) Boolean myPlans,
	 final @RequestParam(required = false) Date birthDate,
	 final @RequestParam(required = false)String personTableType,
	 final @RequestParam(required = false) Integer start,
	 final @RequestParam(required = false) Integer limit,
	 final @RequestParam(required = false) String sort,
	 final @RequestParam(required = false) String sortDirection,
	 final HttpServletRequest request) throws ObjectNotFoundException
	 {
		assertSearchApiAuthorization(request);
		String sortConfigured = sort == null ? "dp.lastName":"dp."+sort;
		if(sortConfigured.equals("dp.coach")){
			sortConfigured = "dp.coachLastName";
		}else if(sortConfigured.equals("dp.currentProgramStatusName")){
			sortConfigured = "dp.programStatusName";
		}else if(sortConfigured.equals("dp.numberOfEarlyAlerts")){
			sortConfigured = "dp.activeAlertsCount";
		}
		SortingAndPaging sortAndPage = SortingAndPaging
		.createForSingleSortWithPaging(ObjectStatus.ALL, start, limit, sortConfigured,
				sortDirection, null);
		if(sortConfigured.equals("dp.coachLastName")){
			sortAndPage.prependSortField("dp.coachFirstName", SortDirection.getSortDirection(sortDirection));
		}
		final PagingWrapper<PersonSearchResult2> models = service.searchPersonDirectory(personSearchRequestFactory.from(schoolId,
				firstName, lastName, 
				programStatus,specialServiceGroup, 
				coachId,declaredMajor,
				hoursEarnedMin,hoursEarnedMax,
				gpaEarnedMin,gpaEarnedMax,
				currentlyRegistered,earlyAlertResponseLate,
				sapStatusCode,
				mapStatus,planStatus,
				myCaseload,myPlans,birthDate, personTableType, sortAndPage));
		return new PagedResponse<PersonSearchResult2TO>(true,
				models.getResults(), factory2.asTOList(models.getRows()));	
	}
	

	private void assertSearchApiAuthorization(HttpServletRequest request)
			throws AccessDeniedException {
		if ( securityService.hasAuthority(Permission.PERSON_READ) ||
				securityService.hasAuthority("ROLE_PERSON_SEARCH_READ")) {
			return;
		}
		try {
			requestTrustService.assertHighlyTrustedRequest(request);
		} catch ( AccessDeniedException e ) {
			throw new AccessDeniedException("Untrusted request with"
					+ " insufficient permissions.", e);
		}
	}
}