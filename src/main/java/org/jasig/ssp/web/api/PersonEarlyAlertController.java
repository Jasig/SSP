package org.jasig.ssp.web.api;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Services to manipulate EarlyAlerts.
 * <p>
 * Mapped to URI path <code>/1/person/{personId}/earlyAlert</code>
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/person/{personId}/earlyAlert")
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

	// Overriding because the default sort column needs to be unique
	@Override
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	List<EarlyAlertTO> getAll(@PathVariable final UUID personId,
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
								limit, sort, sortDirection, "createdDate")));
	}
}
