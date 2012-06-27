package org.jasig.ssp.portlet.reports;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class ReportsPortletController {

	@RenderMapping
	public String show() {
		return "reports";
	}
}
