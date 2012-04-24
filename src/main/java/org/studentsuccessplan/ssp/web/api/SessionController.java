package org.studentsuccessplan.ssp.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.security.SspUser;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.transferobject.PersonTO;

/**
 * Allows the logged in user to get their profile.
 * <p>
 * Mapped to the URI path <code>/1/session/...</code>
 */
@Controller
@RequestMapping("/1/session")
public class SessionController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SessionController.class);

	@Autowired
	private transient SecurityService service;

	/**
	 * Gets the currently authenticated user.
	 * <p>
	 * Mapped to the URI path <code>.../getAuthenticatedPerson</code>
	 * 
	 * @return The currently authenticated {@link Person} info, or null (empty)
	 *         if not authenticated.
	 */
	@RequestMapping(value = "/getAuthenticatedPerson", method = RequestMethod.GET)
	public @ResponseBody
	PersonTO getAuthenticatedPerson() {
		if (service == null) {
			LOGGER.error("The security service was not wired by the Spring container correctly for the SessionController.");
			return null;
		}

		final SspUser user = service.currentlyAuthenticatedUser();

		if (user == null) {
			// User not authenticated, so return an empty result, but still with
			// an HTTP 200 response.
			return null;
		}

		// Convert model to a transfer object
		final PersonTO pTo = new PersonTO();
		pTo.fromModel(user.getPerson());

		// Return authenticated person transfer object
		return pTo;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
