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

import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.PersonSelfHelpGuideResponseService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.SelfHelpGuideQuestionService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.transferobject.SelfHelpGuideResponseTO;
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
@RequestMapping("/1/mygps/selfhelpguideresponse")
public class MyGpsSelfHelpGuideResponseController extends
		AbstractBaseController {

	@Autowired
	private transient SelfHelpGuideService selfHelpGuideService;

	@Autowired
	private transient SelfHelpGuideQuestionService selfHelpGuideQuestionService;

	@Autowired
	private transient PersonSelfHelpGuideResponseService service;

	@Autowired
	private transient SecurityService securityService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuideResponseController.class);

	public MyGpsSelfHelpGuideResponseController() {
		super();
	}

	public MyGpsSelfHelpGuideResponseController(
			final PersonSelfHelpGuideResponseService service,
			final SelfHelpGuideService selfHelpGuideService,
			final SelfHelpGuideQuestionService selfHelpGuideQuestionService,
			final SecurityService securityService) {
		super();
		this.service = service;
		this.selfHelpGuideService = selfHelpGuideService;
		this.selfHelpGuideQuestionService = selfHelpGuideQuestionService;
		this.securityService = securityService;
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "cancel", method = RequestMethod.GET)
	public @ResponseBody
	boolean cancel(
			final @RequestParam("selfHelpGuideResponseId") UUID selfHelpGuideResponseId)
			throws Exception {
		final SelfHelpGuideResponse response = service
				.get(selfHelpGuideResponseId);
		return service.cancelSelfHelpGuideResponse(response);
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "complete", method = RequestMethod.GET)
	public @ResponseBody
	boolean complete(
			final @RequestParam("selfHelpGuideResponseId") UUID selfHelpGuideResponseId)
			throws Exception {

		final SelfHelpGuideResponse response = service
				.get(selfHelpGuideResponseId);
		return service.completeSelfHelpGuideResponse(response);
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "getById", method = RequestMethod.GET)
	public @ResponseBody
	SelfHelpGuideResponseTO getById(
			final @RequestParam("selfHelpGuideResponseId") UUID selfHelpGuideResponseId)
			throws Exception {
		final SelfHelpGuideResponse response = service
				.get(selfHelpGuideResponseId);

		return service.getSelfHelpGuideResponseFor(response,
				new SortingAndPaging(ObjectStatus.ACTIVE));
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "initiate", method = RequestMethod.GET)
	public @ResponseBody
	String initiate(final @RequestParam("selfHelpGuideId") UUID selfHelpGuideId)
			throws Exception {
		final SelfHelpGuide guide = selfHelpGuideService.get(selfHelpGuideId);
		final Person person = securityService.currentUser().getPerson();
		return service.initiateSelfHelpGuideResponse(guide, person).toString();
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "answer", method = RequestMethod.GET)
	public @ResponseBody
	boolean answer(
			final @RequestParam("selfHelpGuideResponseId") UUID selfHelpGuideResponseId,
			final @RequestParam("selfHelpGuideQuestionId") UUID selfHelpGuideQuestionId,
			final @RequestParam("response") boolean response) throws Exception {
		final SelfHelpGuideResponse selfHelpGuideResponse = service
				.get(selfHelpGuideResponseId);
		final SelfHelpGuideQuestion selfHelpGuideQuestion = selfHelpGuideQuestionService
				.get(selfHelpGuideQuestionId);

		return service.answerSelfHelpGuideQuestion(
				selfHelpGuideResponse,
				selfHelpGuideQuestion, response);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
