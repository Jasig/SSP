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
	protected transient SessionFactory sessionFactory;

	protected transient Class<T> persistentClass;

	protected AbstractAuditableCrudDao(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(final ObjectStatus status) {
		return createCriteria(
				new SortingAndPaging(status))
				.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(final SortingAndPaging sAndP) {
		return createCriteria(sAndP)
				// new SortingAndPaging(status, firstResult, maxResults, sort,
				// sortDirection, null))
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final UUID id) {
		return (T) sessionFactory.getCurrentSession().get(this.persistentClass,
				id);
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

	protected Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(
				this.persistentClass);
	}

	protected Criteria createCriteria(final SortingAndPaging sAndP) {
		Criteria query = createCriteria();

		if (sAndP != null) {
			sAndP.addAll(query);
		}

		return query;
	}

}
