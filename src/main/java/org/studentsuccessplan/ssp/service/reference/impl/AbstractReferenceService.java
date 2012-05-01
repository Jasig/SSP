package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.UUID;

import org.studentsuccessplan.ssp.dao.reference.ReferenceAuditableCrudDao;
import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public abstract class AbstractReferenceService<T extends Auditable>
		implements AuditableCrudService<T> {

	public AbstractReferenceService(Class<T> modelClass) {
		this.modelClass = modelClass;
	}

	private Class<T> modelClass;

	protected abstract ReferenceAuditableCrudDao<T> getDao();

	@Override
	public PagingWrapper<T> getAll(SortingAndPaging sAndP) {
		return getDao().getAll(sAndP);
	}

	@Override
	public T get(final UUID id) throws ObjectNotFoundException {
		final T obj = getDao().get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, modelClass.getName());
		}

		return obj;
	}

	@Override
	public T create(final T obj) {
		return getDao().save(obj);
	}

	@Override
	public T save(final T obj) throws ObjectNotFoundException {
		return getDao().save(obj);
	}

	@Override
	public void delete(final UUID id) throws ObjectNotFoundException {
		T current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}
}
