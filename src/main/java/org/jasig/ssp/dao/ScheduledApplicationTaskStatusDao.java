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

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduledApplicationTaskStatusDao extends
		AbstractAuditableCrudDao<ScheduledApplicationTaskStatus> {

	protected ScheduledApplicationTaskStatusDao() {
		super(ScheduledApplicationTaskStatus.class);
	}
	
	public ScheduledApplicationTaskStatus getByTaskName(String taskName) throws HibernateException{
		Criteria criteria = createCriteria().add(Restrictions.eq("taskName", taskName));
		return (ScheduledApplicationTaskStatus) criteria.uniqueResult();
	}
	
}
