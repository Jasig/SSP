package org.jasig.ssp.portlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class SSPIndexPortletController extends AbstractSSPController {

	@RenderMapping
	public String show() {
		return "ssp-main";
	}

}
