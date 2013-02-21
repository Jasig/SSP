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

import java.util.List;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.PersonStaffDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Goal controller
 */
@Controller
@RequestMapping("/1/personStaffDetails")
public class PersonStaffDetailsController
		extends AbstractBaseController {

	/**
	 * Construct an instance with specific classes for use by the super class
	 * methods.
	 */
	protected PersonStaffDetailsController() {
		super();
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonStaffDetailsController.class);

	@Autowired
	protected transient PersonStaffDetailsService service;


	@RequestMapping(value = "/homeDepartments", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	List<String> getAllHomeDepartments(
			final @RequestParam(required = false) ObjectStatus status) {
		final List<String> homeDepartments = service
				.getAllHomeDepartments(status == null ? ObjectStatus.ACTIVE : status);

		return homeDepartments;
	}


	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}