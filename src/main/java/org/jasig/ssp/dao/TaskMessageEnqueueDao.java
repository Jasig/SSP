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
package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.TaskMessageEnqueue;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.util.hibernate.BatchProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * CRUD methods for the PersonChallenge model.
 */
@Repository
public class TaskMessageEnqueueDao extends AbstractAuditableCrudDao<TaskMessageEnqueue> {

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;
	
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractRestrictedPersonAssocAuditableCrudDao.class);
	/**
	 * Constructor
	 */
	public TaskMessageEnqueueDao() {
		super(TaskMessageEnqueue.class);
	}

	@SuppressWarnings("unchecked")
	public List<TaskMessageEnqueue> getAllForTask(Task task) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("task.id", task.getId()));

		return (List<TaskMessageEnqueue>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskMessageEnqueue> getAllForIds(List<UUID> ids) {
		BatchProcessor<UUID, TaskMessageEnqueue> processor =  new BatchProcessor<UUID, TaskMessageEnqueue>(ids);
		
		do{
			final Criteria criteria = createCriteria();
			processor.process(criteria, "id");
		}while(processor.moreToProcess());

		return processor.getResults();
	}


	public TaskMessageEnqueue get(UUID id) throws ObjectNotFoundException {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("id", id));
		return (TaskMessageEnqueue)criteria.uniqueResult();
	}
		
}