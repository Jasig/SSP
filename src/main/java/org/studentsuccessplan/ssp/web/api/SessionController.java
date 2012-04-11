package org.studentsuccessplan.ssp.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.security.SspUser;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.transferobject.PersonTO;
import org.studentsuccessplan.ssp.transferobject.ServiceResponse;

/**
 * Allows the logged in user to get their profile.
 * <p>
 * Mapped to the URI path <code>/api/session/...</code>
 */
@Controller
@RequestMapping("/session")
public class SessionController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SessionController.class);

	@Autowired
	private SecurityService service;

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

		SspUser user = service.currentlyLoggedInSspUser();

		if (user == null) {
			// User not authenticated, so return an empty result, but still with
			// an HTTP 200 response.
			return null;
		}

		// Convert model to a transfer object
		PersonTO pTo = new PersonTO();
		pTo.pullAttributesFromModel(user.getPerson());

		// Return authenticated person transfer object
		return pTo;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ServiceResponse handle(Exception e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
