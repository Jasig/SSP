package org.studentsuccessplan.mygps.web;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.mygps.business.SelfHelpGuideManager;
import org.studentsuccessplan.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideGroupService;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideService;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideDetailTO;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideTO;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Controller
@RequestMapping("/1/mygps/selfhelpguide")
public class MyGpsSelfHelpGuideController extends AbstractMyGpsController {

	@Autowired
	private SelfHelpGuideManager selfHelpGuideManager;

	@Autowired
	private SelfHelpGuideService selfHelpGuideService;

	@Autowired
	private SelfHelpGuideGroupService selfHelpGuideGroupService;

	@Autowired
	private SelfHelpGuideTOFactory selfHelpGuideTOFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuideController.class);

	public MyGpsSelfHelpGuideController() {
	}

	public MyGpsSelfHelpGuideController(
			SelfHelpGuideManager selfHelpGuideManager,
			SelfHelpGuideService selfHelpGuideService,
			SelfHelpGuideGroupService selfHelpGuideGroupService,
			SelfHelpGuideTOFactory selfHelpGuideTOFactory) {
		this.selfHelpGuideManager = selfHelpGuideManager;
		this.selfHelpGuideService = selfHelpGuideService;
		this.selfHelpGuideGroupService = selfHelpGuideGroupService;
		this.selfHelpGuideTOFactory = selfHelpGuideTOFactory;
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody
	List<SelfHelpGuideTO> getAll() throws Exception {
		// TODO: MyGPSSelfGuideController.getAll() needs filtered based on
		// security. Guides with authenticationRequired == true should not show
		// for the anonymous user.
		try {
			return selfHelpGuideTOFactory.asTOList((List<SelfHelpGuide>) selfHelpGuideService
							.getAll(new SortingAndPaging(ObjectStatus.ACTIVE))
							.getRows());
		} catch (Exception e) {
			LOGGER.error("ERROR : getAll() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "/getContentById", method = RequestMethod.GET)
	public @ResponseBody
	SelfHelpGuideDetailTO getContentById(
			@RequestParam("selfHelpGuideId") UUID selfHelpGuideId)
			throws Exception {
		try {
			return selfHelpGuideManager.getContentById(selfHelpGuideId);
		} catch (Exception e) {
			LOGGER.error("ERROR : getContentById() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "/getBySelfHelpGuideGroup", method = RequestMethod.GET)
	public @ResponseBody
	List<SelfHelpGuideTO> getBySelfHelpGuideGroup(
			@RequestParam("selfHelpGuideGroupId") UUID selfHelpGuideGroupId)
			throws Exception {

		try {
			SelfHelpGuideGroup selfHelpGuideGroup = selfHelpGuideGroupService
					.get(selfHelpGuideGroupId);
			return selfHelpGuideTOFactory.asTOList(selfHelpGuideService
					.getBySelfHelpGuideGroup(selfHelpGuideGroup));
		} catch (Exception e) {
			LOGGER.error("ERROR : getBySelfHelpGuideGroup() : {}",
					e.getMessage(), e);
			throw e;
		}
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
