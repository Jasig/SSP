package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAuditableCrudDao<T extends Auditable> implements
		AuditableCrudDao<T> {

	@Autowired
	protected transient SessionFactory sessionFactory;

	protected transient Class<T> persistentClass;

	protected AbstractAuditableCrudDao(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	public PagingWrapper<T> getAll(final ObjectStatus status) {
		return processCriteriaWithPaging(createCriteria(),
				new SortingAndPaging(status));
	}

	@Override
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		return processCriteriaWithPaging(createCriteria(), sAndP);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final UUID id) throws ObjectNotFoundException {
		final T obj = (T) sessionFactory.getCurrentSession().get(
				this.persistentClass,
				id);

		if (obj != null) {
			return obj;
		}

		throw new ObjectNotFoundException(id, persistentClass.getName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public T load(final UUID id) {
		return (T) sessionFactory.getCurrentSession().load(
				this.persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T save(final T obj) {
		if (obj.getId() == null) {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
			return obj;
		}

		return (T) sessionFactory.getCurrentSession().merge(obj);
	}

	@Override
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
		@SuppressWarnings("unchecked")
		final List<T> results = query.list();

		// If there is no total yet, take it from the size of the results
		if (null == totalRows) {
			totalRows = Long.valueOf(results.size());
		}

		return new PagingWrapper<T>(totalRows, results);
	}
}
