package org.jasig.ssp.web.api.reference;

import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.RestController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public abstract class AbstractAuditableReferenceController<T extends AbstractReference, TO extends AbstractReferenceTO<T>>
		extends RestController<TO, T> {

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

	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	PagingTO<TO, T> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws Exception {

		final PagingWrapper<T> data = getService().getAll(
				SortingAndPaging.createForSingleSort(status, start,
						limit, sort, sortDirection, "name"));

		return new PagingTO<TO, T>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	TO get(final @PathVariable UUID id) throws Exception {
		final T model = getService().get(id);
		if (model == null) {
			return null;
		}

		final TO out = this.transferObjectClass.newInstance();
		out.from(model);
		return out;
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody
	TO create(@Valid @RequestBody final TO obj) throws Exception {
		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send a reference entity with an ID to the create method. Did you mean to use the save method instead?");
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

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	TO save(@PathVariable final UUID id, @Valid @RequestBody final TO obj)
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

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id) throws Exception {
		getService().delete(id);
		return new ServiceResponse(true);
	}
}
