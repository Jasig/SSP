package org.jasig.mygps.web;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.service.reference.SelfHelpGuideGroupService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideDetailTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/1/mygps/selfhelpguide")
public class MyGpsSelfHelpGuideController extends AbstractBaseController {

	@Autowired
	private transient SelfHelpGuideService selfHelpGuideService;

	@Autowired
	private transient SelfHelpGuideGroupService selfHelpGuideGroupService;

	@Autowired
	private transient SelfHelpGuideTOFactory selfHelpGuideTOFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuideController.class);

	public MyGpsSelfHelpGuideController() {
		super();
	}

	public MyGpsSelfHelpGuideController(
			final SelfHelpGuideService selfHelpGuideService,
			final SelfHelpGuideGroupService selfHelpGuideGroupService,
			final SelfHelpGuideTOFactory selfHelpGuideTOFactory) {
		super();
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
		return selfHelpGuideTOFactory.asTOList(selfHelpGuideService
				.getAll(new SortingAndPaging(ObjectStatus.ACTIVE))
				.getRows());
	}

	@RequestMapping(value = "/getContentById", method = RequestMethod.GET)
	public @ResponseBody
	SelfHelpGuideDetailTO getContentById(
			final @RequestParam("selfHelpGuideId") UUID selfHelpGuideId)
			throws Exception {
		final SelfHelpGuide guide = selfHelpGuideService
				.get(selfHelpGuideId);
		return new SelfHelpGuideDetailTO(guide);
	}

	@RequestMapping(value = "/getBySelfHelpGuideGroup", method = RequestMethod.GET)
	public @ResponseBody
	List<SelfHelpGuideTO> getBySelfHelpGuideGroup(
			final @RequestParam("selfHelpGuideGroupId") UUID selfHelpGuideGroupId)
			throws Exception {
		final SelfHelpGuideGroup selfHelpGuideGroup = selfHelpGuideGroupService
				.get(selfHelpGuideGroupId);
		return selfHelpGuideTOFactory.asTOList(selfHelpGuideService
				.getBySelfHelpGuideGroup(selfHelpGuideGroup));
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
