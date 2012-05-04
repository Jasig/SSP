package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
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
	@SuppressWarnings("unchecked")
	public PagingWrapper<T> getAll(final ObjectStatus status) {
		final List<T> list = createCriteria(new SortingAndPaging(status))
				.list();
		return new PagingWrapper<T>(list);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria().setProjection(
				Projections.rowCount()).uniqueResult();

		return new PagingWrapper<T>(totalRows, createCriteria(sAndP).list());
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
		final Criteria query = createCriteria();

		if (sAndP != null) {
			sAndP.addAll(query);
		}

		return query;
	}
}
