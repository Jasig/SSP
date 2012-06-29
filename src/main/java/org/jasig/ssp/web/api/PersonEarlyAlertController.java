package org.jasig.ssp.web.api;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Mapped to URI path <code>/1/person/{personId}/earlyAlert</code>
 */
@Controller
public class PersonEarlyAlertController extends
		AbstractPersonAssocController<EarlyAlert, EarlyAlertTO> {

	protected PersonEarlyAlertController() {
		super(EarlyAlert.class, EarlyAlertTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonEarlyAlertController.class);

	@Autowired
	private transient EarlyAlertService service;

	@Autowired
	private transient EarlyAlertTOFactory factory;

	@Override
	protected EarlyAlertTOFactory getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected EarlyAlertService getService() {
		return service;
	}

	// Overriding to specify full request path since we needed a custom create
	// method
	@Override
	@RequestMapping(value = "/1/person/{personId}/earlyAlert/{id}", method = RequestMethod.GET)
	public @ResponseBody
	EarlyAlertTO get(final @PathVariable UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException,
			ValidationException {
		return super.get(id, personId);
	}

	@Override
	@RequestMapping(value = "/1/person/{personId}/earlyAlert/", method = RequestMethod.POST)
	public @ResponseBody
	EarlyAlertTO create(@PathVariable final UUID personId,
			@Valid @RequestBody final EarlyAlertTO obj)
			throws ValidationException, ObjectNotFoundException {
		return super.create(personId, obj);
	}

	@Override
	@RequestMapping(value = "/1/person/{personId}/earlyAlert/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	EarlyAlertTO save(@PathVariable final UUID id,
			@PathVariable final UUID personId,
			@Valid @RequestBody final EarlyAlertTO obj)
			throws ObjectNotFoundException, ValidationException {
		return super.save(id, personId, obj);
	}

	@Override
	@RequestMapping(value = "/1/person/{personId}/earlyAlert/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException {
		return super.delete(id, personId);
	}

	// Overriding because the default sort column needs to be unique
	@Override
	@RequestMapping(value = "/1/person/{personId}/earlyAlert/", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<EarlyAlertTO> getAll(
			@PathVariable final UUID personId,
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
		final PagingWrapper<EarlyAlert> data = getService().getAllForPerson(
				person,
				SortingAndPaging.createForSingleSort(status, start,
						limit, sort, sortDirection, "createdDate"));

		return new PagedResponse<EarlyAlertTO>(true, data.getResults(),
				getFactory().asTOList(data.getRows()));
	}

	@RequestMapping(value = "/1/person/earlyAlert/", method = RequestMethod.POST)
	public @ResponseBody
	EarlyAlertTO create(@RequestParam final String studentId,
			@Valid @RequestBody final EarlyAlertTO obj)
			throws ObjectNotFoundException, ValidationException {

		checkPermissionForOp("wRITE");

		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send with an ID to the create method. Did you mean to use the save method instead?");
		}

		if (StringUtils.isEmpty(studentId)) {
			throw new IllegalArgumentException(
					"Person identifier is required.");
		}

		UUID personId = null; // NOPMD by jon.adams on 5/14/12 1:40 PM

		// Figure out which type of PersonID was sent
		try {
			personId = UUID.fromString(studentId); // NOPMD by jon.adams
		} catch (final IllegalArgumentException exc) {
			final Person person = personService.getByStudentId(studentId);

			if (person == null) {
				throw new ObjectNotFoundException(
						null, "Person", exc);
			}

			personId = person.getId();
		}

		if (personId == null) {
			throw new ObjectNotFoundException(
					"Specified person or student identifier could not be found.",
					"Person");
		}

		obj.setPersonId(personId);

		return super.create(personId, obj);
	}

	@Override
	public String permissionBaseName() {
		return "EARLY_ALERT";
	}
}