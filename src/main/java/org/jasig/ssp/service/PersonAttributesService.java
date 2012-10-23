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
package org.jasig.ssp.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.security.PersonAttributesResult;

/**
 * Allows SSP to access attributes for people in the community that may come
 * from other systems on campus.
 * 
 * @author awills
 */
public interface PersonAttributesService {

	PersonAttributesResult getAttributes(HttpServletRequest req,
			HttpServletResponse res, String username)
			throws ObjectNotFoundException;

	PersonAttributesResult getAttributes(String username)
			throws ObjectNotFoundException;

	PersonAttributesResult getAttributes(String username, PortletRequest portletRequest)
			throws ObjectNotFoundException;
	
	List<Map<String, Object>> searchForUsers(HttpServletRequest req, HttpServletResponse res, 
			Map<String,String> query);

	/*
	 * @returns usernames of coaches
	 */
	Collection<String> getCoaches();

}
