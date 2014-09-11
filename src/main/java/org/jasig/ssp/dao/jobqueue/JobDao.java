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
package org.jasig.ssp.dao.jobqueue;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.jobqueue.Job;
import org.jasig.ssp.model.jobqueue.WorkflowStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * DAO for the {@link Job} model
 */
@Repository
public class JobDao extends AbstractAuditableCrudDao<Job> implements
		AuditableCrudDao<Job> {

	/**
	 * Constructor that initializes the instance with the specific class types
	 * for super class method use.
	 */
	public JobDao() {
		super(Job.class);
	}

	@SuppressWarnings("unchecked")
	public List<Job> getNextQueuedJobsForExecution(int i, String processId) {
		 return sessionFactory
			.getCurrentSession()
			.createQuery(
					"from Job where workflowStoppedDate is null and (workflowStatus in (:queued,:scheduling,:executing) and scheduledByProcess != :processId) or (scheduledByProcess = :processId and retryCount > 0 and workflowStatus in (:executing))  order by createdDate")
			.setMaxResults(i).setString("queued", WorkflowStatus.QUEUED.toString()).setString("scheduling", WorkflowStatus.SCHEDULING.toString()).setString("executing", WorkflowStatus.EXECUTING.toString())
			.setString("processId", processId)
			.list();
	}
}