package org.jasig.ssp.service;

import org.jasig.ssp.dao.PersonAssocAuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * Abstract service for Auditable models that are associated with a Person.
 * 
 * @param <T>
 *            An {@link Auditable} model
 */
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
	public T save(final T obj) throws ObjectNotFoundException,
			ValidationException {
		return getDao().save(obj);
	}
}