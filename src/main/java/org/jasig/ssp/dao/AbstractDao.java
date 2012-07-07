package org.jasig.ssp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDao<T> {

	/**
	 * String constant for unchecked casting warnings that occur all over the
	 * DAO layer
	 */
	public static final String UNCHECKED = "unchecked";

	@Autowired
	protected transient SessionFactory sessionFactory;

	protected transient Class<T> persistentClass;

	public AbstractDao(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public void delete(final T obj) {
		sessionFactory.getCurrentSession().delete(obj);
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
	protected PagingWrapper<T> processCriteriaWithPaging(
			final Criteria query, final SortingAndPaging sAndP) {
		if (sAndP != null) {
			sAndP.addStatusFilterToCriteria(query);
		}

		// get the query results total count
		Long totalRows = null; // NOPMD by jon on 5/20/12 4:42 PM

		// Only query for total count if query is paged
		if ((sAndP != null) && sAndP.isPaged()) {
			totalRows = (Long) query.setProjection(
					Projections.rowCount()).uniqueResult();

			// clear the count projection from the query
			query.setProjection(null);
		}

		// Add Sorting and Paging
		if (sAndP != null) {
			sAndP.addPagingToCriteria(query);
			sAndP.addSortingToCriteria(query);
		}

		// Query results
		@SuppressWarnings(UNCHECKED)
		final List<T> results = query.list();

		// If there is no total yet, take it from the size of the results
		if (null == totalRows) {
			totalRows = Long.valueOf(results.size());
		}

		return new PagingWrapper<T>(totalRows, results);
	}
}
