package org.jasig.ssp.web.api;

import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.EarlyAlertResponseTOFactory;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;
import org.jasig.ssp.transferobject.PagingTO;
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
 * Services to manipulate EarlyAlerts.
 * <p>
 * Mapped to URI path <code>/1/person/{personId}/earlyAlertResponse</code>
 * 
 * @author jon.adams
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/person/{personId}/earlyAlertResponse")
public class PersonEarlyAlertResponseController extends
		BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonEarlyAlertResponseController.class);

	@Autowired
	private transient EarlyAlertResponseService service;

	@Autowired
	private transient EarlyAlertResponseTOFactory factory;

	@Autowired
	protected transient PersonService personService;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	PagingTO<EarlyAlertResponseTO, EarlyAlertResponse> getAll(
			@PathVariable final UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {
		final Person person = personService.get(personId);
		final PagingWrapper<EarlyAlertResponse> data = service
				.getAllForPerson(
						person,
						SortingAndPaging.createForSingleSort(status, start,
								limit, sort, sortDirection, "createdDate"));

		return new PagingTO<EarlyAlertResponseTO, EarlyAlertResponse>(true,
				data.getResults(), factory.asTOList(data.getRows()));
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	EarlyAlertResponseTO get(final @PathVariable UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException,
			ValidationException
	{
		final EarlyAlertResponse model = service.get(id);
		if (model == null) {
			return null;
		}

		return instantiateTO(model);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody
	EarlyAlertResponseTO create(@PathVariable final UUID personId,
			@Valid @RequestBody final EarlyAlertResponseTO obj)
			throws ValidationException,
			ObjectNotFoundException {
		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send with an ID to the create method. Did you mean to use the save method instead?");
		}

		if (personId == null) {
			throw new IllegalArgumentException(
					"Person identifier is required.");
		}

		final EarlyAlertResponse model = factory.from(obj);

		if (null != model) {

			final EarlyAlertResponse createdModel = service.create(model);
			if (null != createdModel) {
				return instantiateTO(createdModel);
			}
		}

		return null;
	}

	private EarlyAlertResponseTO instantiateTO(final EarlyAlertResponse model) {
		return new EarlyAlertResponseTO(model);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	EarlyAlertResponseTO save(@PathVariable final UUID id,
			@PathVariable final UUID personId,
			@Valid @RequestBody final EarlyAlertResponseTO obj)
			throws ObjectNotFoundException, ValidationException {
		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		final EarlyAlertResponse model = factory.from(obj);
		model.setId(id);

		final EarlyAlertResponse savedT = service.save(model);
		if (null != savedT) {
			return instantiateTO(savedT);
		}

		return null;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException {
		service.delete(id);
		return new ServiceResponse(true);
	}

}