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

import java.util.Date;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.dao.ScheduledApplicationTaskStatusDao;
import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.jasig.ssp.model.ScheduledTaskStatus;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.ScheduledApplicationTaskStatusService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduledApplicationTaskStatusServiceImpl extends
		AbstractAuditableCrudService<ScheduledApplicationTaskStatus> implements ScheduledApplicationTaskStatusService {
	
	@Autowired
	private transient ScheduledApplicationTaskStatusDao dao;
	
	@Override
	protected AuditableCrudDao<ScheduledApplicationTaskStatus> getDao() {
		return dao;
	}

	@Override
	public ScheduledApplicationTaskStatus save(
			ScheduledApplicationTaskStatus obj) throws ObjectNotFoundException,
			ValidationException {
		return dao.save(obj);
	}

	@Override
	public ScheduledApplicationTaskStatus beginTask(String taskName) {
		ScheduledApplicationTaskStatus taskStatus = dao.getByTaskName(taskName);
		if(taskStatus == null){
			taskStatus = new ScheduledApplicationTaskStatus();
		}

		taskStatus.setCompletedDate(null);
		taskStatus.setStartDate(new Date());
		taskStatus.setStatus(ScheduledTaskStatus.RUNNING);
		taskStatus.setTaskName(taskName);
		return dao.save(taskStatus);
	}

	@Override
	public ScheduledApplicationTaskStatus completeTask(String taskName) {
		ScheduledApplicationTaskStatus taskStatus = dao.getByTaskName(taskName);
		if(taskStatus == null){
			return null;
		}
		taskStatus.setCompletedDate(new Date());
		taskStatus.setStatus(ScheduledTaskStatus.COMPLETED);
		return dao.save(taskStatus);
	}
	
	@Override
	public ScheduledApplicationTaskStatus interruptTask(String taskName) {
		ScheduledApplicationTaskStatus taskStatus = dao.getByTaskName(taskName);
		if(taskStatus == null){
			return null;
		}
		taskStatus.setCompletedDate(new Date());
		taskStatus.setStatus(ScheduledTaskStatus.INTERRUPTED);
		return  dao.save(taskStatus);
	}

	@Override
	public ScheduledApplicationTaskStatus failTask(String taskName) {
		ScheduledApplicationTaskStatus taskStatus = dao.getByTaskName(taskName);
		if(taskStatus == null){
			return null;
		}
		taskStatus.setCompletedDate(new Date());
		taskStatus.setStatus(ScheduledTaskStatus.FAILED);
		return dao.save(taskStatus);
		
	}
	
	@Override
	public ScheduledApplicationTaskStatus getByName(String taskName){
		return dao.getByTaskName(taskName);
	}
	
	@Override
	public ScheduledApplicationTaskStatus resetTask(String taskName) {
		ScheduledApplicationTaskStatus taskStatus = dao.getByTaskName(taskName);
		if(taskStatus == null){
			taskStatus = new ScheduledApplicationTaskStatus();
		}

		taskStatus.setCompletedDate(null);
		taskStatus.setStartDate(null);
		taskStatus.setStatus(null);
		taskStatus.setTaskName(taskName);
		return dao.save(taskStatus);
	}
	
	

}
