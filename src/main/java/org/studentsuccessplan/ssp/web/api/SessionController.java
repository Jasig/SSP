package org.studentsuccessplan.ssp.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.security.SspUser;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
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

	@PreAuthorize("permitAll")
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody
	ServiceResponse handleNotFound(final ObjectNotFoundException e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}

	@PreAuthorize("permitAll")
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody
	ServiceResponse handleNotFound(final AccessDeniedException e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}

	@PreAuthorize("permitAll")
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody
	ServiceResponse handle(final Exception e) {
		LOGGER.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
