package org.jasig.mygps.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jasig.mygps.model.transferobject.MessageTO;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.web.api.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/mygps/message")
public class MyGpsMessageController extends BaseController {

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient MessageService messageService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsMessageController.class);

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Boolean contactCoach(@RequestBody final MessageTO messageTO,
			final HttpServletResponse response) throws Exception {

		try {
			if ((securityService.currentUser().getPerson() == null)
					|| (securityService.currentUser().getPerson().getCoach() == null)) {
				return false;
			}

			final Person coach = securityService.currentUser().getPerson()
					.getCoach();

			final Map<String, Object> messageParams = new HashMap<String, Object>();
			messageParams.put("subj", messageTO.getSubject());
			messageParams.put("mesg", messageTO.getMessage());

			messageService.createMessage(coach,
					MessageTemplate.EMPTY_TEMPLATE_EMAIL_ID, messageParams);

			return true;
		} catch (Exception e) {
			LOGGER.error("ERROR : contactCoach() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
