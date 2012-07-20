package org.jasig.mygps.portlet;

import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class MyGpsSelfHelpGuidePortletController extends
		AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuidePortletController.class);

	@RenderMapping()
	public String show() {
		return "self-help-guide";
	}

	@RenderMapping(params = "action=showGuides")
	public String showGuides() {
		return "guides";
	}

	@RenderMapping(params = "action=showAGuide")
	public String showAGuide() {
		return "guide";
	}

	@RenderMapping(params = "action=search")
	public String showSearch() {
		return "search";
	}

	@RenderMapping(params = "action=intake")
	public String showIntake() {
		return "intake";
	}

	@RenderMapping(params = "action=showContacts")
	public String accessDenied() {
		return "accessDenied";
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
