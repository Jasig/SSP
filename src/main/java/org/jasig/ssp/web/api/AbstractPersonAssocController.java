/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAssocAuditableService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Basic REST command implementation to respond with standard transfer objects
 * in JSON format for entities associated with a Person. <br>
 * Largely based on AbstractAuditableReferenceController
 * 
 * @param <T>
 *            Model Type
 * @param <TO>
 *            Transfer object type that handles the model type T.
 */
public abstract class AbstractPersonAssocController<T extends PersonAssocAuditable, TO extends AbstractAuditableTO<T>>
		extends AbstractBaseController {

	/**
	 * Logger
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractPersonAssocController.class);

	/**
	 * Service that handles the business logic for the implementing type for T.
	 */
	protected abstract PersonAssocAuditableService<T> getService();

	/**
	 * Transfer object factory to create new instances of the specific TO for
	 * extending classes.
	 */
	protected abstract TOFactory<TO, T> getFactory();

	/**
	 * Model class type
	 */
	protected transient Class<T> persistentClass;

	/**
	 * Transfer object class type
	 */
	protected transient Class<TO> transferObjectClass;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	protected transient SecurityService securityService;

	public abstract String permissionBaseName();

	/**
	 * Check authorization for the specified operation.
	 * 
	 * @param operation
	 *            check authorization to this operation
	 */
	public void checkPermissionForOp(final String operation) {
		final String permission = "ROLE_PERSON_" + permissionBaseName() + "_"
				+ operation;
		if (!securityService.hasAuthority(permission)) {
			LOGGER.warn("Access is denied for Operation. Role required: "
					+ permission);
			throw new AccessDeniedException(
					"Access is denied for Operation. Role required: "
							+ permission);
		}
	}

	/**
	 * Construct a controller with the specified specific service and types.
	 * 
	 * @param persistentClass
	 *            Model class type
	 * @param transferObjectClass
	 *            Transfer object class type
	 */
	protected AbstractPersonAssocController(
			final Class<T> persistentClass, final Class<TO> transferObjectClass) {
		super();
		this.persistentClass = persistentClass;
		this.transferObjectClass = transferObjectClass;
	}

	/**
	 * Get all instances for the specified criteria.
	 * 
	 * @param personId
	 *            Person identifier
	 * @param status
	 *            Object status
	 * @param start
	 *            First result
	 * @param limit
	 *            Total results for a single page
	 * @param sort
	 *            Sort field (property)
	 * @param sortDirection
	 *            Sort direction (asc/desc)
	 * @return All instances for the specified criteria, possibly paged based on
	 *         start/limit filters.
	 * @throws ObjectNotFoundException
	 *             If specified person could not be found.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	PagedResponse<TO> getAll(@PathVariable final UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {
		// Check permissions
		checkPermissionForOp("READ");

		// Run getAll for the specified person
		final Person person = personService.get(personId);
		final PagingWrapper<T> data = getService().getAllForPerson(person,
				SortingAndPaging.createForSingleSortWithPaging(status, start,
						limit, sort, sortDirection, null));

		return new PagedResponse<TO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	TO get(final @PathVariable UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException,
			ValidationException {

		checkPermissionForOp("READ");

		final T model = getService().get(id);
		if (model == null) {
			return null;
		}

		return instantiateTO(model);
	}

	@RequestMapping(method = RequestMethod.POST)
	@DynamicPermissionChecking
	public @ResponseBody
	TO create(@PathVariable @NotNull final UUID personId,
			@Valid @RequestBody @NotNull final TO obj)
			throws ValidationException, ObjectNotFoundException {

		checkPermissionForOp("WRITE");

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

		final T model = getFactory().from(obj);

		if (null != model) {

			// associate with person here.
			if (model.getPerson() == null) {
				model.setPerson(personService.get(personId));
			}

			final T createdModel = getService().create(model);
			if (null != createdModel) {
				return instantiateTO(createdModel);
			}
		}

		return null;
	}

	/**
	 * Save changes to the specified ID and object, for the specified person.
	 * 
	 * @param id
	 *            the instance to update
	 * @param personId
	 *            the person
	 * @param obj
	 *            the full instance data to update
	 * @return the updated instance
	 * @throws ObjectNotFoundException
	 *             If the specified ID could not be found.
	 * @throws ValidationException
	 *             If the updated data was not valid.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@DynamicPermissionChecking
	public @ResponseBody
	TO save(@PathVariable final UUID id,
			@PathVariable final UUID personId,
			@Valid @RequestBody final TO obj)
			throws ObjectNotFoundException, ValidationException {

		checkPermissionForOp("WRITE");

		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		final T model = getFactory().from(obj);
		model.setId(id);

		if (model.getPerson() == null) {
			model.setPerson(personService.get(personId));
		}

		final T savedT = getService().save(model);
		if (null != savedT) {
			return instantiateTO(savedT);
		}
		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@DynamicPermissionChecking
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException {
		checkPermissionForOp("DELETE");
		getService().delete(id);
		return new ServiceResponse(true);
	}

	protected TO instantiateTO(final T model) throws ValidationException {
		TO out;
		try {
			out = this.transferObjectClass.newInstance();
			out.from(model);
			return out;
		} catch (final InstantiationException e) {
			LOGGER.error("Could not instantiate new instance of "
					+ this.transferObjectClass.getName(), e);
			throw new ValidationException("Unexpected error.", e);
		} catch (final IllegalAccessException e) {
			LOGGER.error(
					"Illegal access instantiating new instance of "
							+ this.transferObjectClass.getName(), e);
			throw new ValidationException("Unexpected error.", e);
		}
	}
}