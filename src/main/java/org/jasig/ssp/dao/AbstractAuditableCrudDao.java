package org.jasig.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
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
		extends AbstractDao<T> implements AuditableCrudDao<T> {

	protected AbstractAuditableCrudDao(final Class<T> persistentClass) {
		super(persistentClass);
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

	@SuppressWarnings(UNCHECKED)
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

	@Override
	public PagingWrapper<T> get(final List<UUID> ids,
			final SortingAndPaging sAndP) {
		if ((ids == null) || ids.isEmpty()) {
			throw new IllegalArgumentException(
					"List of ids can not be null or empty.");
		}

		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("id", ids));
		return processCriteriaWithPaging(criteria, sAndP);
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

}