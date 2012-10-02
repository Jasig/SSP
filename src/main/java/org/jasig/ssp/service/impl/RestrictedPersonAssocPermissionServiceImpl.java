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
package org.jasig.ssp.service.impl;

import java.util.Collection;

import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.RestrictedPersonAssocPermissionService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestrictedPersonAssocPermissionServiceImpl implements
		RestrictedPersonAssocPermissionService {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(RestrictedPersonAssocPermissionServiceImpl.class);

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Override
	public void checkPermissionForModelGivesExcpetion(
			final RestrictedPersonAssocAuditable model, final SspUser requestor) {

		if (!checkPermissionForModel(model, requestor)) {
			throw new AccessDeniedException("Access is denied for Model.");
		}
	}

	@Override
	public boolean checkPermissionForModel(
			final RestrictedPersonAssocAuditable model, final SspUser requestor) {

		final Collection<GrantedAuthority> permissions = requestor
				.getAuthorities();

		final Collection<ConfidentialityLevel> grantedLevels = confidentialityLevelService
				.filterConfidentialityLevelsFromGrantedAuthorities(permissions);

		if (grantedLevels.contains(model.getConfidentialityLevel())
				|| (model.getCreatedBy().equals(
						requestor.getPerson()))) {
			return true;
		}

		LOGGER.debug("Required ConfidentialityLevel not present: {}", model
				.getConfidentialityLevel().getName());
		return false;
	}
}