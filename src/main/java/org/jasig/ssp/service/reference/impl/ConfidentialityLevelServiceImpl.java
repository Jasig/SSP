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
package org.jasig.ssp.service.reference.impl;

import java.util.Collection;

import org.jasig.ssp.dao.reference.ConfidentialityLevelDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ConfidentialityLevelServiceImpl extends
		AbstractReferenceService<ConfidentialityLevel>
		implements ConfidentialityLevelService {

	@Autowired
	transient private ConfidentialityLevelDao dao;

	protected void setDao(final ConfidentialityLevelDao dao) {
		this.dao = dao;
	}

	@Override
	protected ConfidentialityLevelDao getDao() {
		return dao;
	}

	@Override
	public ConfidentialityLevel create(final ConfidentialityLevel obj)
			throws ObjectNotFoundException, ValidationException {
		throw new UnsupportedOperationException(
				"This can't work unless groups are assigned differently than through the portlet filter.");
	}

	@Override
	public Collection<String> confidentialityLevelsAsPermissions() {

		final Collection<String> asStrings = Lists.newArrayList();

		/*
		 * final PagingWrapper<ConfidentialityLevel> levels = getAll(new
		 * SortingAndPaging( ObjectStatus.ACTIVE));
		 * 
		 * for (ConfidentialityLevel level : levels) {
		 * asStrings.add(level.getRoleId().asPermissionString()); }
		 */

		for (final DataPermissions permission : DataPermissions.values()) {
			asStrings.add(permission.asPermissionString());
		}

		return asStrings;
	}

	@Override
	public Collection<GrantedAuthority> confidentialityLevelsAsGrantedAuthorities() {

		final Collection<GrantedAuthority> asAuths = Lists.newArrayList();

		/*
		 * final PagingWrapper<ConfidentialityLevel> levels = getAll(new
		 * SortingAndPaging(ObjectStatus.ACTIVE));
		 * 
		 * for (ConfidentialityLevel level : levels) {
		 * asAuths.add(level.getRoleId().asGrantedAuthority()); }
		 */

		for (final DataPermissions permission : DataPermissions.values()) {
			asAuths.add(permission.asGrantedAuthority());
		}

		return asAuths;
	}

	@Override
	public Collection<ConfidentialityLevel> filterConfidentialityLevelsFromPermissions(
			final Collection<String> authorities) {

		final Collection<ConfidentialityLevel> filtered = Lists.newArrayList();

		final Collection<ConfidentialityLevel> levels = getAll(
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows();

		for (final ConfidentialityLevel level : levels) {
			if (authorities
					.contains(level.getPermission().asPermissionString())) {
				filtered.add(level);
			}
		}
		return filtered;
	}

	@Override
	public Collection<ConfidentialityLevel> filterConfidentialityLevelsFromGrantedAuthorities(
			final Collection<GrantedAuthority> authorities) {

		final Collection<ConfidentialityLevel> filtered = Lists.newArrayList();

		final Collection<ConfidentialityLevel> levels = getAll(
				new SortingAndPaging(
						ObjectStatus.ACTIVE)).getRows();

		for (final ConfidentialityLevel level : levels) {
			for (GrantedAuthority auth : authorities) {
				if (auth.getAuthority().equals(
						level.getPermission().asPermissionString())) {
					filtered.add(level);
					break;
				}
			}

		}

		return filtered;
	}

	@Override
	public Collection<ConfidentialityLevel> confidentialityLevelsForSspUser(
			final SspUser user) {
		if (user != null) {
			return filterConfidentialityLevelsFromGrantedAuthorities(user
					.getAuthorities());
		}

		return null;
	}
}