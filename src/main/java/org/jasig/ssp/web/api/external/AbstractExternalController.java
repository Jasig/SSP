package org.jasig.ssp.web.api.external;

import java.io.Serializable;

import org.jasig.ssp.factory.external.ExternalTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.ExternalDataService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.external.ExternalDataTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.BaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * All the Methods a Reference Controller needs to be useful.
 * 
 * @param <TO>
 *            The TO type this controller works with.
 * @param <T>
 *            The model that the TO type T works with.
 */
public abstract class AbstractExternalController<TO extends ExternalDataTO<T>, T>
		extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractExternalController.class);

	/**
	 * Model class type
	 */
	protected transient Class<T> persistentClass;

	/**
	 * Transfer object class type
	 */
	protected transient Class<TO> transferObjectClass;

	/**
	 * Service that handles the business logic for the implementing type for T.
	 */
	protected abstract ExternalDataService<T> getService();

	/**
	 * Transfer object factory to create new instances of the specific TO for
	 * extending classes.
	 */
	protected abstract ExternalTOFactory<TO, T> getFactory();

	/**
	 * Construct a controller with the specified specific service and types.
	 * 
	 * @param persistentClass
	 *            Model class type
	 * @param transferObjectClass
	 *            Transfer object class type
	 */
	protected AbstractExternalController(final Class<TO> transferObjectClass,
			final Class<T> persistentClass) {
		super();
		this.transferObjectClass = transferObjectClass;
		this.persistentClass = persistentClass;
	}

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
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
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {

		// Run getAll
		final PagingWrapper<T> data = getService().getAll(
				SortingAndPaging.createForSingleSort(ObjectStatus.ALL, start,
						limit, sort, sortDirection, null));

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
	TO get(final @PathVariable Serializable id) throws ObjectNotFoundException,
			ValidationException {
		final T model = getService().get(id);
		if (model == null) {
			return null;
		}

		return this.instantiateTO(model);
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