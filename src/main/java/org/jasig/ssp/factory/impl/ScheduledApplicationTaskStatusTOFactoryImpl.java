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
package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.ScheduledApplicationTaskStatusDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.ScheduledApplicationTaskStatusTOFactory;
import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.ScheduledApplicationTaskStatusTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ScheduledApplicationTaskStatusTOFactoryImpl
		extends
		AbstractAuditableTOFactory<ScheduledApplicationTaskStatusTO, ScheduledApplicationTaskStatus>
		implements ScheduledApplicationTaskStatusTOFactory {
	
	@Autowired
	private transient ScheduledApplicationTaskStatusDao dao;
	
	public ScheduledApplicationTaskStatusTOFactoryImpl() {
		super(ScheduledApplicationTaskStatusTO.class, ScheduledApplicationTaskStatus.class);
	}

	@Override
	public ScheduledApplicationTaskStatus from(final ScheduledApplicationTaskStatusTO tObject) throws ObjectNotFoundException {
		final ScheduledApplicationTaskStatus model = super.from(tObject);
		model.setStartDate(tObject.getStartDate());
		model.setCompletedDate(tObject.getCompletedDate());
		model.setStatus(tObject.getStatus());
		model.setTaskName(tObject.getTaskName());
		return model;
	}

	@Override
	protected ScheduledApplicationTaskStatusDao getDao() {
		return dao;
	}

}
