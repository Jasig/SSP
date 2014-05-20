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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.jasig.mygps.business.EarlyAlertManager;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reports.DisabilityServicesReportTO;
import org.jasig.ssp.util.hibernate.BatchProcessor;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractAuditableCrudDao.class);

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
		BatchProcessor<UUID, T> processor =  new BatchProcessor<UUID,T>(ids, sAndP);
		do{
			final Criteria criteria = createCriteria();
			processor.process(criteria, "id");
		}while(processor.moreToProcess());

		return processor.getPagedResults();
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
		LOGGER.error("A hard delete of type "+obj.getClass()+" with the id of "+obj.getId()+" is being executed!");
		sessionFactory.getCurrentSession().delete(obj);
	}
}