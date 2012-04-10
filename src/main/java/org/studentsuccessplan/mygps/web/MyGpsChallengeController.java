package org.studentsuccessplan.mygps.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.mygps.business.SelfHelpGuideManager;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

@Controller
@RequestMapping("/mygps/challenge")
public class MyGpsChallengeController {

	@Autowired
	private SelfHelpGuideManager manager;

	private Logger logger = LoggerFactory
			.getLogger(MyGpsChallengeController.class);

	// Needed for tests, will be removed in the future.
	public void setManager(SelfHelpGuideManager manager) {
		this.manager = manager;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody
	List<ChallengeTO> search(@RequestParam("query") String query)
			throws Exception {

		try {
			return manager.challengeSearch(query);

		} catch (Exception e) {

			logger.error("ERROR : search() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	String handleException(Exception e, HttpServletResponse response) {
		logger.error("ERROR : handleException()", e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return e.getMessage();
	}

}
