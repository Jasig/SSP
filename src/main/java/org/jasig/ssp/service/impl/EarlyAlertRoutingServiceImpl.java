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

import java.util.UUID;

import org.jasig.ssp.dao.EarlyAlertRoutingDao;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.EarlyAlertRoutingService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertRouting service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class EarlyAlertRoutingServiceImpl extends
		AbstractAuditableCrudService<EarlyAlertRouting>
		implements EarlyAlertRoutingService {

	@Autowired
	transient private EarlyAlertRoutingDao dao;

	@Autowired
	transient private CampusService campusService;

	@Override
	protected EarlyAlertRoutingDao getDao() {
		return dao;
	}

	@Override
	public PagingWrapper<EarlyAlertRouting> getAllForCampus(
			final Campus campus,
			final SortingAndPaging sAndP) {
		return getDao().getAllForCampusId(campus.getId(), sAndP);
	}

	@Override
	public EarlyAlertRouting save(final EarlyAlertRouting obj)
			throws ObjectNotFoundException {
		return getDao().save(obj);
	}

	@Override
	public void checkCampusIds(final UUID campusId, final EarlyAlertRouting obj)
			throws IllegalArgumentException, ObjectNotFoundException {
		if (campusId == null) {
			throw new IllegalArgumentException("Missing Campus id.");
		}

		final Campus campus = campusService.get(campusId);

		if (obj != null) {
			if (obj.getCampus() == null) {
				throw new IllegalArgumentException(
						"Missing EarlyAlertRouting.Campus.");
			}

			if (!campus.getId().equals(obj.getCampus().getId())) {
				throw new IllegalArgumentException(
						"Campus identifier and EarlyAlertRouting.Campus.Id did not match.");
			}
		}
	}
}