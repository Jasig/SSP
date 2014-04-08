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

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.dao.ObjectExistsException;
import org.jasig.ssp.dao.PersonExistsException;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.ExternalPersonService;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.jasig.ssp.transferobject.PersonSearchResultTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.EmailStudentRequestTO;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.util.transaction.WithTransaction;
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

import javax.validation.Valid;

import java.io.Serializable;
import java.util.Map;
import java.util.SortedSet;
import java.util.UUID;

/**
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>/1/person</code>
 */
@Controller
@RequestMapping("/1/person")
public class PersonController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonController.class);

	@Autowired
	private transient PersonService service;

	@Autowired
	private transient PersonTOFactory factory;

	@Autowired
	protected transient SecurityService securityService;
	
	@Autowired
	private WithTransaction withTransaction;

	@Autowired
	private ExternalPersonService externalPersonService;

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param status
	 *            Filter by this status.
	 * @param start
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer. Often comes from client as a
	 *            parameter labeled <code>start</code>. A null value indicates
	 *            to return rows starting from index 0.
	 * @param limit
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer. Often comes from client as a
	 *            parameter labeled <code>limit</code>. A null value indicates
	 *            return all rows from the start parameter to the end of the
	 *            data.
	 * @param sort
	 *            Property name. If null or empty string, the default sort will
	 *            be used. If non-empty, must be a case-sensitive model property
	 *            name. Often comes from client as a parameter labeled
	 *            <code>sort</code>. Example sort expression:
	 *            <code>propertyName</code>
	 * @param sortDirection
	 *            Ascending/descending keyword. If null or empty string, the
	 *            default sort will be used. Must be <code>ASC</code> or
	 *            <code>DESC</code>.
	 * @return All entities in the database filtered by the supplied status.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	PagedResponse<PersonTO> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {

		// Performance is so terrible against a production-sized dataset that
		// we've got to turn this op off.
		throw new UnsupportedOperationException("The 'list all persons' API"
				+ " is disabled. Consider /1/person/<id> or /1/person/search"
				+ " and /1/person/students/search instead.");

		// Leaving code commented out b/c we'll want to turn this back on once
		// performance problems are sorted out.
//		final PagingWrapper<Person> people = service.getAll(SortingAndPaging
//				.createForSingleSortWithPaging(status, start, limit, sort, sortDirection,
//						null));
//
//		return new PagedResponse<PersonTO>(true, people.getResults(),
//				factory.asTOList(people.getRows()));
	}

	@RequestMapping(value = "/coach", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	PagedResponse<PersonLiteTO> getAllCoaches(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {
		final PagingWrapper<CoachPersonLiteTO> coaches = service
				.getAllCoachesLite(SortingAndPaging.createForSingleSortWithPaging(status,
						start, limit, sort, sortDirection, null));

		return new PagedResponse<PersonLiteTO>(true, coaches.getResults(),
				PersonLiteTO.toTOListFromCoachTOs(coaches.getRows()));
	}

	@RequestMapping(value = "/email", method = RequestMethod.POST)
	@PreAuthorize(Permission.SECURITY_PERSON_WRITE)
	public @ResponseBody
	boolean emailStudent(
			final @RequestBody EmailStudentRequestTO emailRequest) throws ObjectNotFoundException, ValidationException {
		service.emailStudent(emailRequest);
		return true;
	}
	
	@RequestMapping(value = "/currentCoachesLite", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	PagedResponse<PersonLiteTO> getAllCurrentCoaches() {
		final SortedSet<CoachPersonLiteTO> coaches = service
				.getAllCurrentCoachesLite(CoachPersonLiteTO.COACH_PERSON_LITE_TO_NAME_AND_ID_COMPARATOR);

		return new PagedResponse<PersonLiteTO>(true, new Long(coaches.size()),
				PersonLiteTO.toTOListFromCoachTOs(coaches));
	}
	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	PersonTO get(final @PathVariable UUID id) throws ObjectNotFoundException {
		final Person model = service.get(id);
		if (model == null) {
			return null;
		}

		return new PersonTO(model);
	}

	@RequestMapping(value= "/lite/{id}", method=RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	@ResponseBody
	PersonLiteTO getLite(final @PathVariable UUID id) throws ObjectNotFoundException {
		final Person model = service.get(id);
		if (model == null) {
			return null;
		}
		return new PersonLiteTO(model);
	}

	@RequestMapping(value = "/searchlite/{id}", method=RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	@ResponseBody
	PersonSearchResultTO getSearchLite(final @PathVariable UUID id) throws ObjectNotFoundException {
		final Person model = service.get(id);
		if (model == null) {
			return null;
		}
		return new PersonSearchResultTO(model);
	}

	@RequestMapping(value = "/bySchoolId/{id}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	PersonTO bySchoolId(final @PathVariable String id)
			throws ObjectNotFoundException {

		final Person model = service.getBySchoolId(id,false);
		if (model == null) {
			return null;
		}

		PersonTO personTO = new PersonTO(model);
		service.evict(model);
		return personTO;
	}

	/**
	 * Persist a new instance of the specified object.
	 * <p>
	 * Must not include an id.
	 * 
	 * @param obj
	 *            New instance to persist.
	 * @return Original instance plus the generated id.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If the specified data contains an id (since it shouldn't).
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize(Permission.SECURITY_PERSON_WRITE)
	public @ResponseBody
	PersonTO create(final @Valid @RequestBody PersonTO obj)
			throws  ValidationException, ObjectNotFoundException {
		if (obj.getId() != null) {
			throw new ValidationException(
					"You submitted a person with an id to the create method.  Did you mean to save?");
		}

		final Person model = factory.from(obj);

		Person createdModel = null;
		PersonExistsException conflict = null;
		if (null != model) {
			int retryLimit = 1;
			do {
				try {
					conflict = null;
					createdModel = service.create(model);
					if (null != createdModel) {
						// syncing newly created person to external person table
						externalPersonService.updatePersonFromExternalPerson(createdModel);
						return new PersonTO(createdModel);
					}
				} catch ( PersonExistsException e ) {
					LOGGER.info("Person creation conflicted with an existing"
							+ " record. Will be retried {} times before"
							+ " raising an error to the caller.", retryLimit, e);
					conflict = e; 
					// try to tell the caller which record conflicted, by PK
					// else something deleted the person from under us?
				}
			} while ( retryLimit-- > 0 );
			if ( null == createdModel ) {
				if ( conflict != null ) {
					throw conflict;
				}
				return null;
			}
		}
		return null;
	}
  

	/**
	 * Persist any changes to the specified instance.
	 * 
	 * @param id
	 *            Explicit id to the instance to persist.
	 * @param obj
	 *            Full instance to persist.
	 * @return The update data object instance.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If the specified id is null.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize(Permission.SECURITY_PERSON_WRITE)
	public @ResponseBody
	PersonTO save(final @PathVariable UUID id,
			final @Valid @RequestBody PersonTO obj)
			throws ObjectNotFoundException, ValidationException {
		if (id == null) {
			throw new ValidationException(
					"You submitted a person without an id to the save method.  Did you mean to create?");
		}

		final Person model = factory.from(obj);
		model.setId(id);

		final Person savedPerson = service.save(model);
		if (null != savedPerson) {
			return new PersonTO(savedPerson);
		}
		return null;
	}

	/**
	 * Marks the specified data instance with a status of
	 * {@link ObjectStatus#INACTIVE}.
	 * 
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize(Permission.SECURITY_PERSON_DELETE)
	public @ResponseBody
	ServiceResponse delete(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		service.delete(id);
		return new ServiceResponse(true);
	}

	//@RequestMapping(value = "/{id}/history/print", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	public @ResponseBody
	PersonTO historyPrint(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		// final Person model = service.get(id);
		// :TODO historyPrint on PersonController
		throw new NotImplementedException();
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}