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
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.transferobject.ServiceResponse;

/**
 * Allows the logged in user to get their profile
 */
@Controller
@RequestMapping("/session")
public class SessionController {

	private static final Logger logger = LoggerFactory
			.getLogger(SessionController.class);

	@Autowired
	private SecurityService service;

	@RequestMapping(value = "getAuthenticatedPerson", method = RequestMethod.GET)
	public @ResponseBody
	Person getAuthenticatedPerson() throws Exception {
		return service.currentlyLoggedInSspUser().getPerson();
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ServiceResponse handle(Exception e) {
		logger.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
