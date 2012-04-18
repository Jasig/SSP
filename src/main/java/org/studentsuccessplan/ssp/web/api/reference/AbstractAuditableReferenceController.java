package org.studentsuccessplan.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.studentsuccessplan.ssp.factory.TransferObjectListFactory;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.AbstractReference;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.transferobject.ServiceResponse;
import org.studentsuccessplan.ssp.transferobject.reference.AbstractReferenceTO;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;
import org.studentsuccessplan.ssp.web.api.RestController;
import org.studentsuccessplan.ssp.web.api.validation.ValidationException;

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
		extends RestController<TO> {

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
	private final TransferObjectListFactory<TO, T> listFactory;

	/**
	 * Model class type
	 */
	protected Class<T> persistentClass;

	/**
	 * Transfer object class type
	 */
	protected Class<TO> transferObjectClass;

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
		this.listFactory = TransferObjectListFactory
				.newFactory(transferObjectClass);
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	List<TO> getAll(final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws Exception {

		return listFactory.toTOList(
				getService().getAll(
						SortingAndPaging.createForSingleSort(status, start,
								limit, sort, sortDirection, "name")));
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
		out.fromModel(model);
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

		final T model = obj.asModel();

		if (null != model) {
			final T createdModel = getService().create(model);
			if (null != createdModel) {
				final TO out = this.transferObjectClass.newInstance();
				out.fromModel(createdModel);
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
					"You submitted a citizenship without an id to the save method.  Did you mean to create?");
		}

		final T model = obj.asModel();
		model.setId(id);

		final T savedT = getService().save(model);
		if (null != savedT) {
			final TO out = this.transferObjectClass.newInstance();
			out.fromModel(savedT);
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

	/**
	 * Log and return an appropriate message for a page not found (HTTP 404,
	 * {@link HttpStatus#NOT_FOUND}).
	 * 
	 * @param e
	 *            Original exception
	 * @return An appropriate service response message to send to the client.
	 */
	@PreAuthorize("permitAll")
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody
	ServiceResponse handleNotFound(final ObjectNotFoundException e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}

	/**
	 * Log and return an appropriate message for a bad request error (
	 * {@link HttpStatus#BAD_REQUEST}).
	 * 
	 * @param e
	 *            Original exception
	 * @return An appropriate service response message to send to the client.
	 */
	@PreAuthorize("permitAll")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody
	ServiceResponse handleValidationError(
			final MethodArgumentNotValidException e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e);
	}

	/**
	 * Log and return an appropriate message for an access denied error (
	 * {@link HttpStatus#FORBIDDEN}).
	 * 
	 * @param e
	 *            Original exception
	 * @return An appropriate service response message to send to the client.
	 */
	@PreAuthorize("permitAll")
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody
	ServiceResponse handleAccessDenied(final AccessDeniedException e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}

	/**
	 * Log and return an appropriate message for an internal server error (HTTP
	 * 500, {@link HttpStatus#INTERNAL_SERVER_ERROR}).
	 * 
	 * @param e
	 *            Original exception
	 * @return An appropriate service response message to send to the client.
	 */
	@Override
	@PreAuthorize("permitAll")
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody
	ServiceResponse handle(final Exception e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
