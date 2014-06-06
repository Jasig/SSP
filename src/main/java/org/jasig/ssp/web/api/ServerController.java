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
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.service.ServerService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyFormatting;
import org.jasig.ssp.util.DateTimeUtils;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Controller
@RequestMapping("/1/server")
public class ServerController extends AbstractBaseController {
	
	@Autowired
	private ServerService serverService;


	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerController.class);

	@RequestMapping(value = "/datetime", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	Map<String,Object> getDateTimeProfile() {
		
		return serverService.getDateTimeProfile();
	}
	
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	Map<String,Object> getVersionProfile(HttpServletRequest  request) throws IOException {
		return serverService.getVersionProfile();
	}

	// We want this particular config to be available anonymously, and the
	// caller doesn't need all the extra junk that comes with a "normal"
	// config API response, so we introduced this API as a way to bypass
	// both the permisssions and the "junk" on the config API for this
	// one entry in particular
	@RequestMapping(value = "/clientTimeout", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	Map<String,Object> getClientTimeout(HttpServletRequest request) {
		
		return serverService.getClientTimeout();
	}
	
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
