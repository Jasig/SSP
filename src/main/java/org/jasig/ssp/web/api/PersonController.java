package org.jasig.ssp.web.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.PersonTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
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
 * Service methods for manipulating data about people in the system.
 * <p>
 * Mapped to URI path <code>/1/person</code>
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/person")
public class PersonController extends RestController<PersonTO, Person> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonController.class);

	@Autowired
	private transient PersonService service;

	@Autowired
	private transient PersonTOFactory factory;

	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	PagingTO<PersonTO, Person> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {

		final PagingWrapper<Person> people = service.getAll(SortingAndPaging
				.createForSingleSort(status, start, limit, sort, sortDirection,
						null));

		return new PagingTO<PersonTO, Person>(true, people.getResults(),
				factory.asTOList(people.getRows()));
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	PersonTO get(final @PathVariable UUID id) throws ObjectNotFoundException {
		final Person model = service.get(id);
		if (model == null) {
			return null;
		} else {
			return new PersonTO(model);
		}
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody
	PersonTO create(final @Valid @RequestBody PersonTO obj)
			throws ObjectNotFoundException, ValidationException {
		if (obj.getId() != null) {
			throw new ValidationException(
					"You submitted a person with an id to the create method.  Did you mean to save?");
		}

		final Person model = factory.from(obj);

		if (null != model) {
			final Person createdModel = service.create(model);
			if (null != createdModel) {
				return new PersonTO(createdModel);
			}
		}
		return null;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
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

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		service.delete(id);
		return new ServiceResponse(true);
	}

	@RequestMapping(value = "/{id}/history/print", method = RequestMethod.GET)
	public @ResponseBody
	PersonTO historyPrint(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		// final Person model = service.get(id);
		// :TODO historyPrint on PersonController
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
}