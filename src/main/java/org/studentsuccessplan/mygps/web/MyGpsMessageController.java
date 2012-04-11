package org.studentsuccessplan.mygps.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.mygps.model.transferobject.MessageTO;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.MessageService;

@Controller
@RequestMapping("/mygps/message")
public class MyGpsMessageController extends AbstractMyGpsController {

	@Autowired
	private MessageService messageService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsMessageController.class);

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Boolean contactCoach(@RequestBody MessageTO messageTO,
			HttpServletResponse response) throws Exception {

		try {
			if ((securityService.currentlyLoggedInSspUser().getPerson() == null)
					|| (securityService.currentlyLoggedInSspUser().getPerson()
							.getDemographics().getCoach() == null)) {
				return false;
			}

			Person coach = securityService.currentlyLoggedInSspUser()
					.getPerson().getDemographics().getCoach();

			messageService.createMessage(coach, messageTO.getSubject(),
					messageTO.getMessage());

			return true;
		} catch (Exception e) {
			LOGGER.error("ERROR : contactCoach() : {}", e.getMessage(), e);
			throw e;
		}
	}

}
