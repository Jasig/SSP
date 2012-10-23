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

import java.util.UUID;

import org.jasig.ssp.dao.reference.EarlyAlertReasonDao;
import org.jasig.ssp.model.reference.EarlyAlertReason;
import org.jasig.ssp.service.reference.EarlyAlertReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EarlyAlertReason service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class EarlyAlertReasonServiceImpl extends
		AbstractReferenceService<EarlyAlertReason>
		implements EarlyAlertReasonService {

	@Autowired
	transient private EarlyAlertReasonDao dao;

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final EarlyAlertReasonDao dao) {
		this.dao = dao;
	}

	@Override
	protected EarlyAlertReasonDao getDao() {
		return dao;
	}

	@Override
	public EarlyAlertReason load(final UUID id) {
		return dao.load(id);
	}
}
