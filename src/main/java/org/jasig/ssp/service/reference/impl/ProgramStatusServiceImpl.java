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

import org.jasig.ssp.dao.reference.ProgramStatusDao;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * ProgramStatus service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class ProgramStatusServiceImpl extends
		AbstractReferenceService<ProgramStatus>
		implements ProgramStatusService {

	// Note that care currently needs to be taken to ensure this matches
	// up w/ a corresponding value in Constants.js
	private static final UUID ACTIVE_STATUS_UUID =
			UUID.fromString("b2d12527-5056-a51a-8054-113116baab88");

	@Autowired
	transient private ProgramStatusDao dao;

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final ProgramStatusDao dao) {
		this.dao = dao;
	}

	@Override
	protected ProgramStatusDao getDao() {
		return dao;
	}

	@Override
	public ProgramStatus getActiveStatus() throws ObjectNotFoundException {
		return get(ACTIVE_STATUS_UUID);
	}
}