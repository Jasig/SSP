package org.jasig.ssp.service.reference.impl;

import java.util.UUID;

import org.jasig.ssp.dao.reference.ReferenceAuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base reference model service methods
 * 
 * @param <T>
 *            Model type
 */
@Transactional
public abstract class AbstractReferenceService<T extends Auditable>
		implements AuditableCrudService<T> {

	/**
	 * Constructor that accepts the specific class types to be used by this
	 * abstract class's methods.
	 * 
	 * @param modelClass
	 *            Model type (class instance of <T>)
	 */
	public AbstractReferenceService(final Class<T> modelClass) {
		this.modelClass = modelClass;
	}

	final private transient Class<T> modelClass;

	/**
	 * Gets the DAO instance
	 * 
	 * @return the DAO instance
	 */
	protected abstract ReferenceAuditableCrudDao<T> getDao();

	@Override
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
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
		final T current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}
}
