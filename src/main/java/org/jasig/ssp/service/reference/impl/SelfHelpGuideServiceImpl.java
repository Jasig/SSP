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

import java.util.List;

import org.jasig.ssp.dao.reference.SelfHelpGuideDao;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SelfHelpGuide implementation service
 */
@Service
@Transactional
public class SelfHelpGuideServiceImpl extends
		AbstractReferenceService<SelfHelpGuide>
		implements SelfHelpGuideService {

	public SelfHelpGuideServiceImpl() {
		super();
	}

	public SelfHelpGuideServiceImpl(final SelfHelpGuideDao dao,
			final SecurityService securityService) {
		super();
		this.dao = dao;
		this.securityService = securityService;
	}

	@Autowired
	private transient SelfHelpGuideDao dao;

	@Autowired
	private transient SecurityService securityService;

	@Override
	public PagingWrapper<SelfHelpGuide> getAll(final SortingAndPaging sAndP) {
		if (securityService.isAuthenticated()) {
			return dao.getAll(sAndP);
		} else {
			return new PagingWrapper<SelfHelpGuide>(
					dao.findAllActiveForUnauthenticated());
		}
	}

	@Override
	public List<SelfHelpGuide> getBySelfHelpGuideGroup(
			final SelfHelpGuideGroup selfHelpGuideGroup) {
		return dao
				.findAllActiveBySelfHelpGuideGroup(selfHelpGuideGroup.getId());
	}

	@Override
	protected SelfHelpGuideDao getDao() {
		return dao;
	}
}
