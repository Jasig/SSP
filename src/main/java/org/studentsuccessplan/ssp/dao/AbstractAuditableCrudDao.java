package org.studentsuccessplan.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public abstract class AbstractAuditableCrudDao<T extends Auditable> implements
		AuditableCrudDao<T> {
	@Autowired
	protected SessionFactory sessionFactory;

	protected Class<T> persistentClass;

	protected AbstractAuditableCrudDao(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(ObjectStatus status) {
		return createCriteria(
				new SortingAndPaging(status))
				.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return createCriteria(
				new SortingAndPaging(status, firstResult, maxResults, sort,
						sortDirection, null))
				.list();
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

	protected Criteria createCriteria(SortingAndPaging sAndP) {
		Criteria query = createCriteria();

		if (sAndP != null) {
			sAndP.addAll(query);
		}

		return query;
	}

}
