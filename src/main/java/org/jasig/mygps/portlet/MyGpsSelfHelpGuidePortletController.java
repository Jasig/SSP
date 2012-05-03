package org.jasig.mygps.portlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class MyGpsSelfHelpGuidePortletController extends
		AbstractMyGpsController {

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
}
