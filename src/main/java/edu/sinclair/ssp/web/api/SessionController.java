package edu.sinclair.ssp.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.service.SecurityService;
import edu.sinclair.ssp.transferobject.ServiceResponse;


@Controller
@RequestMapping("/session")
public class SessionController {

	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

	@Autowired
	private SecurityService service;
	
	@RequestMapping(value="getAuthenticatedPerson", method = RequestMethod.GET)
	public @ResponseBody Person getAuthenticatedPerson() throws Exception {
		return service.currentlyLoggedInSspUser().getPerson();
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody ServiceResponse handle(Exception e){
		logger.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
