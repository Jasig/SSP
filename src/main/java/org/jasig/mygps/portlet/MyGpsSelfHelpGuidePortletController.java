/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.mygps.portlet;

import java.util.HashMap;
import java.util.Map;

import org.jasig.ssp.service.ServerService;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class MyGpsSelfHelpGuidePortletController extends
		AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuidePortletController.class);

	@Autowired
	private ServerService serverService;
	
	@RenderMapping
	public ModelAndView show(){
		Map<String,Object> model = new HashMap<String,Object>();		
		try{
			String build = "/versioned/" + serverService.getVersionProfile().get("buildDate").toString();
			model.put("cachebust", build);
		}catch(Exception exp){
			
		}
		return new ModelAndView("self-help-guide", "model", model);
	}

/**	
	@RequestMapping(params = "action=showGuides")
	public String showGuides() {
		return "guides";
	}

	@RequestMapping(params = "action=showAGuide")
	public String showAGuide() {
		return "guide";
	}

	@RequestMapping(params = "action=search")
	public String showSearch() {
		return "search";
	}

	@RequestMapping(params = "action=intake")
	public String showIntake() {
		return "intake";
	}

	@RequestMapping(params = "action=showContacts")
	public String accessDenied() {
		return "accessDenied";
	}
 **/

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
