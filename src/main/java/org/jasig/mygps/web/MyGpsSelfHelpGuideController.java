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
package org.jasig.mygps.web;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.reference.SelfHelpGuideGroupService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideDetailsTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
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
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "/getContentById", method = RequestMethod.GET)
	public @ResponseBody
	SelfHelpGuideDetailsTO getContentById(
			final @RequestParam("selfHelpGuideId") UUID selfHelpGuideId)
			throws Exception {
		final SelfHelpGuide guide = selfHelpGuideService
				.get(selfHelpGuideId);
		return new SelfHelpGuideDetailsTO(guide);
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
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
