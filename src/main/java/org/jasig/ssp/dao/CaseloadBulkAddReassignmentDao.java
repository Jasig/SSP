/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jasig.ssp.model.CaseloadBulkAddReassignment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CaseloadBulkAddReassignmentDao
		extends AbstractDao<CaseloadBulkAddReassignment> {

	public CaseloadBulkAddReassignmentDao() {
		super(CaseloadBulkAddReassignment.class);
	}

	protected CaseloadBulkAddReassignmentDao(Class<CaseloadBulkAddReassignment> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings(UNCHECKED)
	public CaseloadBulkAddReassignment create(final CaseloadBulkAddReassignment obj) {
		final Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		session.flush(); // make sure constraint violations are checked now
		return obj;
	}

	public void truncate() {
		final Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("DELETE FROM CaseloadBulkAddReassignment");
		query.executeUpdate();
	}

	public List<CaseloadBulkAddReassignment> getAll() {
		Criteria criteria = createCriteria();
		return criteria.list();
	}
}