package org.jasig.ssp.web.api;

import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.RestrictedPersonAssocAuditableService;
import org.jasig.ssp.service.RestrictedPersonAssocPermissionService;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Base controller for models that have an association to the Person model and
 * data restrictions via ConfidentialityLevels.
 * 
 * @param <T>
 *            Restricted, person-associated, auditable model
 * @param <TO>
 *            Transfer object for the model
 */
public abstract class AbstractRestrictedPersonAssocController<T extends RestrictedPersonAssocAuditable, TO extends AbstractAuditableTO<T>>
		extends AbstractPersonAssocController<T, TO> {

	/**
	 * Logger
	 */
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractRestrictedPersonAssocController.class);

	protected AbstractRestrictedPersonAssocController(
			final Class<T> persistentClass, final Class<TO> transferObjectClass) {
		super(persistentClass, transferObjectClass);
	}

	@Autowired
	private transient RestrictedPersonAssocPermissionService restrictedPersonAssocPermissionService;

	/**
	 * Service that handles the business logic for the implementing type for T.
	 */
	@Override
	protected abstract RestrictedPersonAssocAuditableService<T> getService();

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<TO> getAll(@PathVariable final UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		checkPermissionForOp("READ");

		final SspUser requestor = securityService.currentUser();

		final Person person = personService.get(personId);
		final PagingWrapper<T> data = getService().getAllForPerson(
				person, requestor,
				SortingAndPaging.createForSingleSort(status, start,
						limit, sort, sortDirection, null));

		return new PagedResponse<TO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	TO get(final @PathVariable UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException,
			ValidationException {

		checkPermissionForOp("READ");

		final T model = getService().get(id);
		if (model == null) {
			return null;
		}

		restrictedPersonAssocPermissionService.checkPermissionForModel(model,
				securityService.currentUser());

		return instantiateTO(model);
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
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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

		if (personId == null) {
			throw new IllegalArgumentException("Person identifier is required.");
		}

		final T model = getFactory().from(obj);
		model.setId(id);

		if (model.getPerson() == null) {
			model.setPerson(personService.get(personId));
		}

		restrictedPersonAssocPermissionService.checkPermissionForModel(model,
				securityService.currentUser());

		final T savedT = getService().save(model);
		if (null != savedT) {
			return instantiateTO(savedT);
		}

		return null;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException {

		checkPermissionForOp("DELETE");

		try {
			final T model = getService().get(id);
			restrictedPersonAssocPermissionService
					.checkPermissionForModel(model,
							securityService.currentUser());
			getService().delete(id);
		} catch (final ObjectNotFoundException e) {
			LOGGER.debug("Item was not found: {}", id.toString());
		}

		return new ServiceResponse(true);
	}
}