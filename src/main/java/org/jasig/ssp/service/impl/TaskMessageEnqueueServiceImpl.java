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

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.dao.TaskMessageEnqueueDao;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.TaskMessageEnqueue;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.TaskMessageEnqueueService;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskMessageEnqueueServiceImpl extends
		AbstractAuditableCrudService<TaskMessageEnqueue> implements TaskMessageEnqueueService {

	@Autowired 
	private TaskMessageEnqueueDao dao;
	
	@Override
	protected AuditableCrudDao<TaskMessageEnqueue> getDao() {
		return dao;
	}

	@Override
	public TaskMessageEnqueue save(TaskMessageEnqueue obj)
			throws ObjectNotFoundException, ValidationException {
		return dao.save(obj);
	}

	@Override
	public List<TaskMessageEnqueue> getAllFromIds(List<UUID> ids) {
		return dao.getAllForIds(ids);
	}

	@Override
	public List<TaskMessageEnqueue> getAllForTask(Task task) {
		return dao.getAllForTask(task);
	}

}
