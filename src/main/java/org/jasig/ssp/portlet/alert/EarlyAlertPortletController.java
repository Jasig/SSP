package org.jasig.ssp.portlet.alert;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class EarlyAlertPortletController {

	@RenderMapping
	public String showRoster() {
		return "ea-roster";
	}

	@RenderMapping(params = "action=enterAlert")
	public String showForm() {
		return "ea-form";
	}

	@RenderMapping(params = "confirm=true")
	public ModelAndView confirm(@RequestParam String studentName) {
		return new ModelAndView("ea-roster", "studentName", studentName);
	}

}
