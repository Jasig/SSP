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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Abstract CRUD DAO class for models with an associated Person reference
 * 
 * @param <T>
 *            Auditable model
 */
public abstract class AbstractPersonAssocAuditableCrudDao<T extends PersonAssocAuditable>
		extends AbstractAuditableCrudDao<T>
		implements PersonAssocAuditableCrudDao<T> {

	protected AbstractPersonAssocAuditableCrudDao(final Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	public PagingWrapper<T> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}
}