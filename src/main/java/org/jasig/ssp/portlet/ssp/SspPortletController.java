package org.jasig.ssp.portlet.ssp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public final class SspPortletController {

	@RenderMapping
	public String show() {
		return "ssp-main";
	}

}
