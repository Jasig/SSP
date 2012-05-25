package org.jasig.ssp.service;

import org.jasig.ssp.dao.PersonAssocAuditableCrudDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public abstract class AbstractPersonAssocAuditableService<T extends PersonAssocAuditable>
		extends AbstractAuditableCrudService<T>
		implements PersonAssocAuditableService<T> {

	@Override
	protected abstract PersonAssocAuditableCrudDao<T> getDao();

	@Override
	public PagingWrapper<T> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public T save(final T obj)
			throws ObjectNotFoundException {
		return getDao().save(obj);
	}
}
