package org.jasig.ssp.web.api; // NOPMD

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.factory.EarlyAlertResponseTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.EarlyAlertResponseService;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.transferobject.EarlyAlertResponseTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
@Controller
@RequestMapping("/1/person/{personId}/earlyAlert/{earlyAlertId}/response")
public class PersonEarlyAlertResponseController extends
		AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonEarlyAlertResponseController.class);

	@Autowired
	private transient EarlyAlertResponseService service;

	@Autowired
	private transient EarlyAlertResponseTOFactory factory;

	@Autowired
	protected transient EarlyAlertService earlyAlertService;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	protected transient SecurityService securityService;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Get all early alert responses, filtered by the specified parameters.
	 * 
	 * @param personId
	 *            person identifier
	 * @param earlyAlertId
	 *            early alert identifier
	 * @param status
	 *            Object status
	 * @param start
	 *            Start row
	 * @param limit
	 *            Row limit
	 * @param sort
	 *            Sort fields
	 * @param sortDirection
	 *            Sort direction
	 * @return All early alert responses, filtered by the specified parameters.
	 * @throws ObjectNotFoundException
	 *             If any of the specified data was not found.
	 * @throws ValidationException
	 *             If any of the data is not valid.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<EarlyAlertResponseTO> getAll(
			@PathVariable final UUID personId,
			@PathVariable final UUID earlyAlertId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException, ValidationException {
		// Check permissions
		checkPermissionForOp("READ");

		// Run getAll for the specified person
		final Person person = personService.get(personId);
		final EarlyAlert earlyAlert = earlyAlertService.get(earlyAlertId);
		if (earlyAlert == null || earlyAlert.getPerson() == null
				|| !earlyAlert.getPerson().equals(person)) {
			throw new ValidationException(
					"Early Alert and Person ids do not match.");
		}

		final PagingWrapper<EarlyAlertResponse> data = service
				.getAllForEarlyAlert(
						earlyAlert,
						SortingAndPaging.createForSingleSort(status, start,
								limit, sort, sortDirection, "createdDate"));

		return new PagedResponse<EarlyAlertResponseTO>(true,
				data.getResults(), factory.asTOList(data.getRows()));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	EarlyAlertResponseTO get(final @PathVariable UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException,
			ValidationException {

		checkPermissionForOp("READ");

		final EarlyAlertResponse model = service.get(id);
		if (model == null) {
			return null;
		}

		return instantiateTO(model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	EarlyAlertResponseTO create(@PathVariable @NotNull final UUID personId,
			@PathVariable @NotNull final UUID earlyAlertId,
			@Valid @NotNull @RequestBody final EarlyAlertResponseTO obj)
			throws ValidationException, ObjectNotFoundException {

		checkPermissionForOp("WRITE");

		if (obj == null) {
			throw new IllegalArgumentException(
					"EarlyAlertResponse is required.");
		}

		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send with an ID to the create method. Did you mean to use the save method instead?");
		}

		if (personId == null) {
			throw new IllegalArgumentException(
					"Person identifier is required.");
		}

		// ensure earlyAlertId is set to path variable
		final EarlyAlertResponseTO earlyAlertResponseTO = obj;
		earlyAlertResponseTO.setEarlyAlertId(earlyAlertId);
		final EarlyAlertResponse model = factory.from(earlyAlertResponseTO);

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

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	EarlyAlertResponseTO save(@PathVariable final UUID id,
			@PathVariable final UUID personId,
			@PathVariable @NotNull final UUID earlyAlertId,
			@Valid @RequestBody final EarlyAlertResponseTO obj)
			throws ObjectNotFoundException, ValidationException {

		checkPermissionForOp("WRITE");

		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		// ensure earlyAlertId is set to path variable
		final EarlyAlertResponseTO earlyAlertResponseTO = obj;

		if (earlyAlertResponseTO.getEarlyAlertId() == null) {
			earlyAlertResponseTO.setEarlyAlertId(earlyAlertId);
		}

		final EarlyAlertResponse model = factory.from(earlyAlertResponseTO);

		final EarlyAlertResponse savedT = service.save(model);
		if (null != savedT) {
			return instantiateTO(savedT);
		}

		return null;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException {

		checkPermissionForOp("DELETE");

		service.delete(id);
		return new ServiceResponse(true);
	}

	public void checkPermissionForOp(final String op) {
		if (!securityService.hasAuthority("ROLE_PERSON_EARLY_ALERT_RESPONSE_"
				+ op)) {
			throw new AccessDeniedException("Access is denied.");
		}
	}
}