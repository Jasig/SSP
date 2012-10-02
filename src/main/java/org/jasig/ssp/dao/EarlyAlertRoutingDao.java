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

import java.util.UUID;

import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the EarlyAlertRouting entity.
 * 
 * @author jon.adams
 */
@Repository
public class EarlyAlertRoutingDao
		extends AbstractAuditableCrudDao<EarlyAlertRouting>
		implements AuditableCrudDao<EarlyAlertRouting> {

	/**
	 * Construct an instance with the specified specific class for use by base
	 * class methods.
	 */
	public EarlyAlertRoutingDao() {
		super(EarlyAlertRouting.class);
	}

	/**
	 * Gets all instances for the specified {@link Campus}, filtered by the
	 * specified filters.
	 * 
	 * @param campusId
	 *            Campus identifier
	 * @param sAndP
	 *            Sorting and paging filters, if any
	 * @return All instance for the specified Person with any specified filters
	 *         applied.
	 */
	public PagingWrapper<EarlyAlertRouting> getAllForCampusId(
			final UUID campusId, final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(createCriteria(sAndP).add(
				Restrictions.eq("campus.id", campusId)), sAndP, true);
	}
}