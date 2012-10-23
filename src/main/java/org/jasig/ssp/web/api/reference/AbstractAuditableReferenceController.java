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
package org.jasig.ssp.web.api.reference;

import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Basic REST command implementation to responds with standard transfer objects
 * in JSON format.
 * 
 * @author jon.adams
 * 
 * @param <T>
 *            Model type
 * @param <TO>
 *            Transfer object type that handles the model type T.
 */
@PreAuthorize(Permission.SECURITY_REFERENCE_WRITE)
public abstract class AbstractAuditableReferenceController<T extends AbstractReference, TO extends AbstractReferenceTO<T>>
		extends AbstractBaseController {

	/**
	 * Logger
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractAuditableReferenceController.class);

	/**
	 * Service that handles the business logic for the implementing type for T.
	 */
	protected abstract AuditableCrudService<T> getService();

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

	/**
	 * Construct a controller with the specified specific service and types.
	 * 
	 * @param persistentClass
	 *            Model class type
	 * @param transferObjectClass
	 *            Transfer object class type
	 */
	protected AbstractAuditableReferenceController(
			final Class<T> persistentClass, final Class<TO> transferObjectClass) {
		super();
		this.persistentClass = persistentClass;
		this.transferObjectClass = transferObjectClass;
	}

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
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<TO> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {

		// Run getAll
		final PagingWrapper<T> data = getService().getAll(
				SortingAndPaging.createForSingleSort(
						status == null ? ObjectStatus.ACTIVE : status, start,
						limit, sort, sortDirection, "name"));

		return new PagedResponse<TO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));
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
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	TO get(final @PathVariable UUID id) throws ObjectNotFoundException,
			ValidationException {
		final T model = getService().get(id);
		if (model == null) {
			return null;
		}

		return this.instantiateTO(model);
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
	public @ResponseBody
	TO create(@Valid @RequestBody final TO obj) throws ObjectNotFoundException,
			ValidationException {
		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send a reference entity with an ID to the create method. Did you mean to use the save method instead?");
		}

		final T model = getFactory().from(obj);

		if (null != model) {
			final T createdModel = getService().create(model);
			if (null != createdModel) {
				return this.instantiateTO(model);
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
	public @ResponseBody
	TO save(@PathVariable final UUID id, @Valid @RequestBody final TO obj)
			throws ValidationException, ObjectNotFoundException {
		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		if (obj.getId() == null) {
			obj.setId(id);
		}

		final T model = getFactory().from(obj);

		final T savedT = getService().save(model);
		if (null != savedT) {
			return this.instantiateTO(model);
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
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id)
			throws ObjectNotFoundException {
		getService().delete(id);
		return new ServiceResponse(true);
	}

	private TO instantiateTO(final T model) throws ValidationException {
		TO out;
		try {
			out = this.transferObjectClass.newInstance();
			out.from(model);
			return out;
		} catch (final InstantiationException e) {
			LOGGER.error("Unable to instantiate this class", e);
			throw new ValidationException("Unable to instantiate this class", e);
		} catch (final IllegalAccessException e) {
			LOGGER.error(
					"Unable to instantiate this class because the Constructor is not visible",
					e);
			throw new ValidationException(
					"Unable to instantiate this class because the Constructor is not visible",
					e);
		}
	}
}