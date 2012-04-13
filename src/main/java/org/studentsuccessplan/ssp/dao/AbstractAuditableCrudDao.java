package org.studentsuccessplan.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;

public abstract class AbstractAuditableCrudDao<T extends Auditable> implements
		AuditableCrudDao<T> {
	@Autowired
	protected SessionFactory sessionFactory;

	protected Class<T> persistentClass;

	protected AbstractAuditableCrudDao(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	public List<T> getAll(ObjectStatus status) {
		return getAllWithDefault(status, null, null, null, null, null);
	}

	@Override
	public List<T> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return getAllWithDefault(status, firstResult, maxResults,
				sortDirection, sortDirection, null);
	}

	@SuppressWarnings("unchecked")
	protected List<T> getAllWithDefault(ObjectStatus status,
			Integer firstResult, Integer maxResults, String sort,
			String sortDirection, String defaultSortProperty) {
		Criteria query = createCriteria();

		if (firstResult != null && firstResult.intValue() >= 0) {
			query.setFirstResult(firstResult);
		}

		if (maxResults != null && maxResults.intValue() > 0) {
			query.setMaxResults(maxResults);
		}

		if (StringUtils.isEmpty(defaultSortProperty)) {
			query = addOrderToCriteria(query, sort, sortDirection);
		} else {
			query = addOrderToCriteria(query, sort, sortDirection,
					defaultSortProperty);
		}

		// Add object status filter
		if (status != ObjectStatus.ALL) {
			query.add(Restrictions.eq("objectStatus", status));
		}

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(UUID id) {
		return (T) sessionFactory.getCurrentSession().get(this.persistentClass,
				id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T save(T obj) {
		if (obj.getId() != null) {
			obj = (T) sessionFactory.getCurrentSession().merge(obj);
		} else {
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
		}

		return obj;
	}

	@Override
	public void delete(T obj) {
		sessionFactory.getCurrentSession().delete(obj);
	}

	protected Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(
				this.persistentClass);
	}

	protected Criteria addOrderToCriteria(Criteria criteria, String sort,
			String sortDirection, String defaultSortProperty) {
		return addOrderToCriteria(criteria,
				StringUtils.isEmpty(sort) ? defaultSortProperty : sort,
				sortDirection);
	}

	protected Criteria addOrderToCriteria(Criteria criteria, String sort,
			String sortDirection) {
		if (StringUtils.isEmpty(sort)) {
			return criteria;
		}

		// Clean/validate sort direction input
		if (StringUtils.isEmpty(sortDirection)) {
			sortDirection = "ASC"; // Default to ascending
		} else {
			sortDirection = sortDirection.toUpperCase();
		}

		// Add sort property with the specified order
		if (sortDirection == "DESC" || sortDirection == "DESCENDING") {
			criteria.addOrder(Order.desc(sort));
		} else {
			// If not descending, assume ascending
			criteria.addOrder(Order.asc(sort));
		}

		return criteria;
	}
}
