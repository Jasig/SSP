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
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Basic CRUD (create, read, update, delete) methods for {@link Auditable}
 * models.
 * 
 * @param <T>
 *            Any model class that extends {@link Auditable}
 */
public abstract class AbstractAuditableCrudDao<T extends Auditable>
		extends AbstractDao<T>
		implements AuditableCrudDao<T> {

	protected AbstractAuditableCrudDao(final Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	@SuppressWarnings(UNCHECKED)
	public T get(final UUID id) throws ObjectNotFoundException {
		final T obj = (T) sessionFactory.getCurrentSession().get(
				this.persistentClass,
				id);

		if (obj != null) {
			return obj;
		}

		throw new ObjectNotFoundException(id, persistentClass.getName());
	}

	@Override
	public PagingWrapper<T> get(final List<UUID> ids,
			final SortingAndPaging sAndP) {
		if ((ids == null) || ids.isEmpty()) {
			throw new IllegalArgumentException(
					"List of ids can not be null or empty.");
		}

		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("id", ids));
		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}

	@SuppressWarnings(UNCHECKED)
	@Override
	public T load(final UUID id) {
		return (T) sessionFactory.getCurrentSession().load(
				this.persistentClass, id);
	}

	@SuppressWarnings(UNCHECKED)
	@Override
	public T save(final T obj) {
		final Session session = sessionFactory.getCurrentSession();
		if (obj.getId() == null) {
			session.saveOrUpdate(obj);
			session.flush(); // make sure constraint violations are checked now
			return obj;
		}

		return (T) session.merge(obj);
	}

	@Override
	public void delete(final T obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}
}