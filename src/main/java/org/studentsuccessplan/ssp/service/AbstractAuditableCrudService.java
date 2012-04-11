package org.studentsuccessplan.ssp.service;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;

/**
 * Base class which provides a building block for creating an
 * AuditableCrudService
 */
public abstract class AbstractAuditableCrudService<T extends Auditable>
		implements AuditableCrudService<T> {

	/**
	 * Need access to the dao, so make children provide it.
	 * 
	 * @return
	 */
	protected abstract AuditableCrudDao<T> getDao();

	@Override
	public List<T> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return getDao().getAll(status, firstResult, maxResults, sort,
				sortDirection);
	}

	@Override
	public T get(UUID id) throws ObjectNotFoundException {
		final T obj = getDao().get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "AbstractTask");
		}

		return obj;
	}

	@Override
	public T create(T obj) {
		return getDao().save(obj);
	}

	@Override
	/**
	 * Save is dependent on children
	 */
	public abstract T save(T obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		T current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}
}
