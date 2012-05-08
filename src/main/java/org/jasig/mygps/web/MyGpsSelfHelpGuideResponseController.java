package org.jasig.mygps.web;

import java.util.UUID;

import org.jasig.mygps.business.SelfHelpGuideManager;
import org.jasig.mygps.model.transferobject.SelfHelpGuideResponseTO;
import org.jasig.ssp.web.api.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/mygps/selfhelpguideresponse")
public class MyGpsSelfHelpGuideResponseController extends
		BaseController {

	@Autowired
	private SelfHelpGuideManager selfHelpGuideManager;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuideResponseController.class);

	protected void setManager(SelfHelpGuideManager selfHelpGuideManager) {
		this.selfHelpGuideManager = selfHelpGuideManager;
	}

	@RequestMapping(value = "cancel", method = RequestMethod.GET)
	public @ResponseBody
	boolean cancel(
			@RequestParam("selfHelpGuideResponseId") UUID selfHelpGuideResponseId)
			throws Exception {

		try {
			return selfHelpGuideManager
					.cancelSelfHelpGuideResponse(selfHelpGuideResponseId);
		} catch (Exception e) {
			LOGGER.error("ERROR : cancel() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "complete", method = RequestMethod.GET)
	public @ResponseBody
	boolean complete(
			@RequestParam("selfHelpGuideResponseId") UUID selfHelpGuideResponseId)
			throws Exception {

		try {
			return selfHelpGuideManager
					.completeSelfHelpGuideResponse(selfHelpGuideResponseId);
		} catch (Exception e) {
			LOGGER.error("ERROR : complete() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public @ResponseBody
	SelfHelpGuideResponseTO getById(
			@RequestParam("selfHelpGuideResponseId") UUID selfHelpGuideResponseId)
			throws Exception {

		try {
			return selfHelpGuideManager
					.getSelfHelpGuideResponseById(selfHelpGuideResponseId);
		} catch (Exception e) {
			LOGGER.error("ERROR : getById() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "initiate", method = RequestMethod.GET)
	public @ResponseBody
	String initiate(@RequestParam("selfHelpGuideId") UUID selfHelpGuideId)
			throws Exception {

		try {
			return selfHelpGuideManager.initiateSelfHelpGuideResponse(
					selfHelpGuideId)
					.toString();
		} catch (Exception e) {
			LOGGER.error("ERROR : initiate() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "answer", method = RequestMethod.GET)
	public @ResponseBody
	boolean answer(
			@RequestParam("selfHelpGuideResponseId") UUID selfHelpGuideResponseId,
			@RequestParam("selfHelpGuideQuestionId") UUID selfHelpGuideQuestionId,
			@RequestParam("response") boolean response) throws Exception {

		try {
			return selfHelpGuideManager.answerSelfHelpGuideQuestion(
					selfHelpGuideResponseId,
					selfHelpGuideQuestionId, response);
		} catch (Exception e) {
			LOGGER.error("ERROR : answer() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
