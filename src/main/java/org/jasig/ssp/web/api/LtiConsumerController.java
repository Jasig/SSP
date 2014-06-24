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
package org.jasig.ssp.web.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.LtiConsumerTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.security.lti.LtiConsumer;
import org.jasig.ssp.model.security.lti.LtiLaunchRequest;
import org.jasig.ssp.model.security.lti.LtiLaunchResponse;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.exception.UserNotAuthorizedException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.impl.PersonAttributesSearchException;
import org.jasig.ssp.service.security.lti.LtiConsumerService;
import org.jasig.ssp.transferobject.LtiConsumerTO;
import org.jasig.ssp.transferobject.LtiConsumerTestTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.util.security.lti.LtiLaunchErrorHandler;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/1/lti")
public class LtiConsumerController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LtiConsumerController.class);
	
	private static final String SUCCESS_FIELD = "success";
	private static final String URL_FIELD = "URL";
	private static final String MESSAGE_FIELD = "message";

	@Autowired
	private transient LtiConsumerService service;

	@Autowired
	private transient LtiConsumerTOFactory factory;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private LtiLaunchErrorHandler ltiLaunchErrorHandler;

	@RequestMapping(value = "/tc", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_API_KEY_READ)
	public @ResponseBody
	PagedResponse<LtiConsumerTO> getAll(
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection) {
		// Run getAll
		final PagingWrapper<LtiConsumer> clients = service
				.getAll(SortingAndPaging.createForSingleSortWithPaging(status,
						0, -1, sort, sortDirection, null));

		return new PagedResponse<LtiConsumerTO>(true, clients.getResults(),
				factory.asTOList(clients.getRows()));
	}

	@RequestMapping(value = "/tc/{id}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_API_KEY_READ)
	public @ResponseBody
	LtiConsumerTO get(final @PathVariable UUID id)
			throws ObjectNotFoundException {
		final LtiConsumer model = service.get(id);
		if (model == null) {
			return null;
		}

		return new LtiConsumerTO(model);
	}

	@PreAuthorize(Permission.SECURITY_API_KEY_WRITE)
	@RequestMapping(value = "/tc", method = RequestMethod.POST)
	public @ResponseBody
	LtiConsumerTO create(@Valid @RequestBody final LtiConsumerTO obj)
			throws ObjectNotFoundException, ValidationException {

		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send an entity with an ID to the create method. Did you mean to use the save method instead?");
		}

		LtiConsumer model = service.create(obj);
		return model == null ? null : factory.from(model);
	}

	@PreAuthorize(Permission.SECURITY_API_KEY_WRITE)
	@RequestMapping(value = "/tc/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	LtiConsumerTO save(@PathVariable final UUID id,
			@Valid @RequestBody final LtiConsumerTO obj)
			throws ValidationException, ObjectNotFoundException {
		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		if (obj.getId() == null) {
			obj.setId(id);
		}
		LtiConsumer model = service.save(obj);
		return model == null ? null : factory.from(model);
	}

	@PreAuthorize(Permission.IS_AUTHENTICATED)
	@RequestMapping(value = "/launch/live", method = RequestMethod.POST)
	public
	String liveLaunch(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws IOException, ServletException {
		return liveLaunch(httpRequest, httpResponse, null);
	}

	@PreAuthorize(Permission.IS_AUTHENTICATED)
	@RequestMapping(value = "/launch/live/target/{target:.+}", method = RequestMethod.POST)
	public
	String liveLaunch(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
					  @PathVariable final String target)
			throws IOException, ServletException {

		try {
			LtiLaunchResponse response = doLaunch(httpRequest, target);
			return "redirect:" + response.getRedirectUrl();
		} catch ( Exception e ) {
			ltiLaunchErrorHandler.handleLaunchError(httpRequest, httpResponse, e, LtiLaunchErrorHandler.LaunchMode.LIVE);
		}

		return null;
	}

	@PreAuthorize(Permission.IS_AUTHENTICATED)
	@RequestMapping(value = "/launch/test", method = RequestMethod.POST)
	public @ResponseBody
	LtiConsumerTestTO testLaunch(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws IOException, ServletException {
		return testLaunch(httpRequest, httpResponse, null);
	}

	@PreAuthorize(Permission.IS_AUTHENTICATED)
	@RequestMapping(value = "/launch/test/target/{target:.+}", method = RequestMethod.POST)
	public @ResponseBody
	LtiConsumerTestTO testLaunch(HttpServletRequest httpRequest,
								 HttpServletResponse httpResponse,
								 @PathVariable final String target)
			throws IOException, ServletException {

		try {
			LtiLaunchResponse response = doLaunch(httpRequest, target);
			final LtiConsumerTestTO testResult = new LtiConsumerTestTO();
			testResult.setResult_description(response.getRedirectUrl());
			testResult.setResult_code("OK"); // D2L spec requires OK or FAILURE
			return testResult;
		} catch (Exception e) {
			ltiLaunchErrorHandler.handleLaunchError(httpRequest, httpResponse, e, LtiLaunchErrorHandler.LaunchMode.TEST);
		}

		return null;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	private LtiLaunchResponse doLaunch(HttpServletRequest httpRequest,
			String target) throws UserNotEnabledException, UserNotAuthorizedException,
			PersonAttributesSearchException, RuntimeException {

		LtiLaunchRequest request = new LtiLaunchRequest();
		@SuppressWarnings("unchecked")
		Enumeration<String> iter = httpRequest.getParameterNames();
		while (iter.hasMoreElements()) {
			String key = (String) iter.nextElement();
			request.setParameter(key, httpRequest.getParameter(key));
		}
		request.setTarget(target);

		return service.processLaunch(request);
	}

}
