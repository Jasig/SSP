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

import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.dao.TaskDao;
import org.jasig.ssp.dao.TaskMessageEnqueueDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.TaskMessageEnqueueTOFactory;
import org.jasig.ssp.model.TaskMessageEnqueue;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.TaskMessageEnqueueTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskMessageEnqueueTOFactoryImpl extends
		AbstractAuditableTOFactory<TaskMessageEnqueueTO, TaskMessageEnqueue>
		implements TaskMessageEnqueueTOFactory {

	public TaskMessageEnqueueTOFactoryImpl() {
		super(TaskMessageEnqueueTO.class, TaskMessageEnqueue.class);
	}

	@Autowired
	private transient TaskMessageEnqueueDao dao;
	
	@Autowired
	private transient TaskDao taskDao;
	
	@Autowired
	private transient MessageDao messageDao;


	@Override
	protected TaskMessageEnqueueDao getDao() {
		return dao;
	}

	@Override
	public TaskMessageEnqueue from(final TaskMessageEnqueueTO tObject)
			throws ObjectNotFoundException {
		final TaskMessageEnqueue model = super.from(tObject);

		model.setTaskDueDate(tObject.getTaskDueDate());
		model.setTaskDueDate(tObject.getTaskDueDate());
		model.setTaskDueDate(tObject.getTaskDueDate());

		model.setTask(tObject.getTaskId() == null ? null :
			taskDao.get(tObject.getTaskId()));
		
		model.setMessage(tObject.getMessageId() == null ? null :
			messageDao.get(tObject.getMessageId()));
		return model;
	}
}