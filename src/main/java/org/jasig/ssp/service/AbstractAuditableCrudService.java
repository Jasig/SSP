package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base class which provides a building block for creating an
 * AuditableCrudService
 * 
 * @param <T>
 *            Any class that extends Auditable
 */
@Transactional
public abstract class AbstractAuditableCrudService<T extends Auditable>
		implements AuditableCrudService<T> {

	/**
	 * Need access to the data access instance, so make children provide it.
	 * 
	 * @return
	 */
	protected abstract AuditableCrudDao<T> getDao();

	@Override
	public PagingWrapper<T> getAll(SortingAndPaging sAndP) {
		return getDao().getAll(sAndP);
	}

	@Override
	public T get(UUID id) throws ObjectNotFoundException {
		final T obj = getDao().get(id);
		if (ObjectStatus.ACTIVE.equals(obj.getObjectStatus())) {
			return obj;
		}

		throw new ObjectNotFoundException(id, this.getClass().getName());
	}

	@Override
	public T create(T obj) throws ObjectNotFoundException {
		return getDao().save(obj);
	}

	@Override
	/**
	 * Save is dependent on children
	 */
	public abstract T save(T obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		T current = getDao().get(id);

		if (!ObjectStatus.DELETED.equals(current.getObjectStatus())) {
			// Object found and is not already deleted, set it and save change.
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}
}
