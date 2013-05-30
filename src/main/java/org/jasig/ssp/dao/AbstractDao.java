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

import javax.validation.constraints.NotNull;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractDao<T> {

	/**
	 * String constant for unchecked casting warnings that occur all over the
	 * DAO layer
	 */
	public static final String UNCHECKED = "unchecked";

	@Autowired
	protected transient SessionFactory sessionFactory;

	protected transient Class<T> persistentClass;
	
	@Value("#{configProperties.db_batchsize}")
	private int batchsize = 300;
	
	@Value("#{configProperties.cacheLifeSpanInMillis}")
	private long cacheLifeSpanInMillis = 86400000;

	public AbstractDao(@NotNull final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param status
	 *            Object status
	 * 
	 * @return All entities in the database filtered by the supplied status.
	 */
	public PagingWrapper<T> getAll(final ObjectStatus status) {
		return processCriteriaWithStatusSortingAndPaging(createCriteria(),
				new SortingAndPaging(status));
	}

	/**
	 * Retrieve every instance in the database filtered by the supplied status,
	 * sorting, and paging parameters.
	 * 
	 * @param sAndP
	 *            SortingAndPaging
	 * 
	 * @return All entities in the database filtered by the supplied status.
	 */
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		return processCriteriaWithStatusSortingAndPaging(createCriteria(),
				sAndP);
	}

	/**
	 * Create a new, simple Criteria instance for the specific class
	 * 
	 * @return A new Criteria instance for the specific class
	 */
	protected Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(
				this.persistentClass);
	}

	protected Query createHqlQuery(String query) {
		return sessionFactory.getCurrentSession().createQuery(query);
	}

	/**
	 * Create a new Criteria instance for the specific class filtered by the
	 * specified sorting, paging, and status filters.
	 * 
	 * @param sAndP
	 *            Sorting, paging, and status filters
	 * @return A new Criteria instance for the specific class
	 */
	protected Criteria createCriteria(final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();

		if (sAndP != null) {
			sAndP.addAll(query);
		}

		return query;
	}

	/**
	 * Run a query and get the total rows and results with out having to define
	 * the Restrictions twice
	 */
	protected <Q> PagingWrapper<Q> processCriteriaWithSortingAndPaging(
			@NotNull final Criteria query, final SortingAndPaging sAndP,
			final boolean filterByStatus) {

		// get the query results total count
		Long totalRows = null; // NOPMD by jon on 5/20/12 4:42 PM

		if (sAndP != null) {
			totalRows = sAndP.applySortingAndPagingToPagedQuery(query,
					filterByStatus);
		}

		// Query results
		@SuppressWarnings(UNCHECKED)
		final List<Q> results = query.list();
		// If there is no total yet, take it from the size of the results
		if (null == totalRows) {
			totalRows = Long.valueOf(results.size());
		}

		return new PagingWrapper<Q>(totalRows, results);
	}

	/**
	 * Run a query and get the total rows and results with out having to define
	 * the Restrictions twice
	 */
	protected PagingWrapper<T> processCriteriaWithStatusSortingAndPaging(
			@NotNull final Criteria query, final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(query, sAndP, true);
	}

	protected int getBatchsize() {
		return batchsize;
	}

	public long getCacheLifeSpanInMillis() {
		return cacheLifeSpanInMillis;
	}

	public void setCacheLifeSpanInMillis(long cacheLifeSpanInMillis) {
		this.cacheLifeSpanInMillis = cacheLifeSpanInMillis;
	}
}