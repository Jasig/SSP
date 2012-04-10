package org.studentsuccessplan.mygps.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.studentsuccessplan.mygps.model.transferobject.MessageTO;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.MessageService;
import org.studentsuccessplan.ssp.service.SecurityService;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private SecurityService securityService;

	private Logger logger = LoggerFactory.getLogger(MessageController.class);

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Boolean contactCoach(@RequestBody MessageTO messageTO, HttpServletResponse response) throws Exception {

		try {
			if ((securityService.currentlyLoggedInSspUser().getPerson() == null)
					|| (securityService.currentlyLoggedInSspUser().getPerson()
					.getDemographics().getCoach() == null)) {
				return false;
			}

			Person coach = securityService.currentlyLoggedInSspUser()
					.getPerson().getDemographics().getCoach();

			messageService.createMessage(coach, messageTO.getSubject(), messageTO.getMessage());

			return true;
		} catch (Exception e) {
			logger.error("ERROR : contactCoach() : {}", e.getMessage(), e);
			throw e;
		}

	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody String handleException(Exception e, HttpServletResponse response) {
		logger.error("ERROR : handleException()", e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return e.getMessage();
	}

}
