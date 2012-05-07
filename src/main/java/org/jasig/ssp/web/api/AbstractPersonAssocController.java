package org.jasig.ssp.web.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.PersonAssocService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.AuditableTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Basic REST command implementation to respond with standard transfer objects
 * in JSON format for entities associated with a Person. <br />
 * Largely based on AbstractAuditableReferenceController
 * 
 * @param <T>
 *            Model Type
 * @param <TO>
 *            Transfer object type that handles the model type T.
 */
public abstract class AbstractPersonAssocController<T extends Auditable, TO extends AuditableTO<T>>
		extends BaseController {

	/**
	 * Logger
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractPersonAssocController.class);

	/**
	 * Service that handles the business logic for the implementing type for T.
	 */
	protected abstract PersonAssocService<T> getService();

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
	 * @throws Exception
	 *             If any exceptions were thrown.
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	List<TO> getAll(@PathVariable final UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws Exception {

		final Person person = personService.get(personId);

		return getFactory().asTOList(
				getService().getAllForPerson(person,
						SortingAndPaging.createForSingleSort(status, start,
								limit, sort, sortDirection, "name")));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	TO get(final @PathVariable UUID id,
			@PathVariable final UUID personId) throws Exception {
		final T model = getService().get(id);
		if (model == null) {
			return null;
		}

		final TO out = this.transferObjectClass.newInstance();
		out.from(model);
		return out;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody
	TO create(@PathVariable final UUID personId,
			@Valid @RequestBody final TO obj) throws Exception {
		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send with an ID to the create method. Did you mean to use the save method instead?");
		}

		if (personId == null) {
			throw new IllegalArgumentException(
					"Person identifier is required.");
		}

		final T model = getFactory().from(obj);

		if (null != model) {
			final T createdModel = getService().create(model);
			if (null != createdModel) {
				final TO out = this.transferObjectClass.newInstance();
				out.from(createdModel);
				return out;
			}
		}

		return null;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	TO save(@PathVariable final UUID id,
			@PathVariable final UUID personId,
			@Valid @RequestBody final TO obj)
			throws Exception {
		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		final T model = getFactory().from(obj);
		model.setId(id);

		final T savedT = getService().save(model);
		if (null != savedT) {
			final TO out = this.transferObjectClass.newInstance();
			out.from(savedT);
			return out;
		}

		return null;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id,
			@PathVariable final UUID personId) throws Exception {
		getService().delete(id);
		return new ServiceResponse(true);
	}
}
