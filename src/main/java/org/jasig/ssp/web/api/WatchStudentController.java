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

import java.util.Calendar;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.factory.PersonSearchResult2TOFactory;
import org.jasig.ssp.factory.WatchStudentTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSearchResult2;
import org.jasig.ssp.model.WatchStudent;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.WatchStudentService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PersonSearchResult2TO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.WatchStudentTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
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

/**
 * WatchStudent controller
 */
@Controller
@RequestMapping("/1/person/{personId}/watchstudent")
public class WatchStudentController
		extends AbstractBaseController{

	/**
	 * Construct an instance with specific classes for use by the super class
	 * methods.
	 */
	protected WatchStudentController() {
		super();
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WatchStudentController.class);

	@Autowired
	protected transient WatchStudentService service;
	
	@Autowired
	protected transient PersonService personService;

	@Autowired
	protected transient WatchStudentTOFactory factory;
	
	@Autowired
	private transient ProgramStatusService programStatusService;

	protected WatchStudentService getService() {
		return service;
	}
	@Autowired
	private transient PersonSearchResult2TOFactory searchTOFactory;
	
	protected WatchStudentTOFactory getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	  
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_PERSON_WATCHLIST_READ')")
	public @ResponseBody
	PagedResponse<PersonSearchResult2TO> myWatchlist(
			@PathVariable @NotNull final UUID personId,
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
		Person person = personService.get(personId);
		
		PagingWrapper<PersonSearchResult2> watchList = service.watchListFor(programStatus, person, buildSortAndPage( limit,  start,  sort,  sortDirection));
		
		return new PagedResponse<PersonSearchResult2TO>(true, new Long(watchList.getRows().size()),
				searchTOFactory.asTOList(watchList.getRows()));
	}
	  
	@PreAuthorize("hasRole('ROLE_PERSON_WATCHLIST_READ')")
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public @ResponseBody
	Long myWatchlistCount(
			@PathVariable @NotNull final UUID personId,
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
		Person person = personService.get(personId);
		
		return  service.watchListCountFor(programStatus, person, buildSortAndPage( limit,  start,  sort,  sortDirection));
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_PERSON_WATCHLIST_DELETE')")
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException {
		getService().delete(id);
		return new ServiceResponse(true);
	}
		
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_PERSON_WATCHLIST_WRITE')")
	public @ResponseBody
	WatchStudentTO create(@PathVariable @NotNull final UUID personId,
			@Valid @RequestBody @NotNull final WatchStudentTO obj)
			throws ValidationException, ObjectNotFoundException {


		if (obj == null) {
			throw new ValidationException("Missing data.");
		}

		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send with an ID to the create method. Did you mean to use the save method instead?");
		}

		if (personId == null) {
			throw new IllegalArgumentException("Person identifier is required.");
		}

		final WatchStudent model = getFactory().from(obj);
		model.setWatchDate(Calendar.getInstance().getTime());

		if (null != model) {
			final WatchStudent createdModel = getService().create(model);
			if (null != createdModel) {
				return new WatchStudentTO(model);
			}
		}

		return null;
	}
	
	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_PERSON_WATCHLIST_READ')")
	public @ResponseBody
	WatchStudentTO get(final @PathVariable UUID personId,
			@PathVariable final UUID studentId) throws ObjectNotFoundException,
			ValidationException {


		final WatchStudent model = getService().get(personId, studentId);
		if (model == null) {
			return null;
		}
		return new WatchStudentTO(model);
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
	
}