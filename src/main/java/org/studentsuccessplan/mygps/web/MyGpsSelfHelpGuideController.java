package org.studentsuccessplan.mygps.web;

import java.util.List;
import java.util.UUID;

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
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideContentTO;
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideTO;

@Controller
@RequestMapping("/mygps/selfhelpguide")
public class MyGpsSelfHelpGuideController {

	@Autowired
	private SelfHelpGuideManager selfHelpGuideManager;

	private Logger logger = LoggerFactory
			.getLogger(MyGpsSelfHelpGuideController.class);

	// Needed for tests
	public void setSelfHelpGuideManager(
			SelfHelpGuideManager selfHelpGuideManager) {
		this.selfHelpGuideManager = selfHelpGuideManager;
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody
	List<SelfHelpGuideTO> getAll() throws Exception {

		try {
			return selfHelpGuideManager.getAll();
		} catch (Exception e) {
			logger.error("ERROR : getAll() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "/getContentById", method = RequestMethod.GET)
	public @ResponseBody
	SelfHelpGuideContentTO getContentById(
			@RequestParam("selfHelpGuideId") UUID selfHelpGuideId)
			throws Exception {

		try {
			return selfHelpGuideManager.getContentById(selfHelpGuideId);
		} catch (Exception e) {
			logger.error("ERROR : getContentById() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "/getBySelfHelpGuideGroup", method = RequestMethod.GET)
	public @ResponseBody
	List<SelfHelpGuideTO> getBySelfHelpGuideGroup(
			@RequestParam("selfHelpGuideGroupId") UUID selfHelpGuideGroupId)
			throws Exception {

		try {
			return selfHelpGuideManager
					.getBySelfHelpGuideGroup(selfHelpGuideGroupId);
		} catch (Exception e) {
			logger.error("ERROR : getBySelfHelpGuideGroup() : {}",
					e.getMessage(), e);
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
