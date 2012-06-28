package org.jasig.ssp.service;

import org.jasig.ssp.dao.RestrictedPersonAssocAuditableDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.RestrictedPersonAssocAuditable;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public abstract class AbstractRestrictedPersonAssocAuditableService<T extends RestrictedPersonAssocAuditable>
		extends AbstractPersonAssocAuditableService<T>
		implements RestrictedPersonAssocAuditableService<T> {

	@Override
	public PagingWrapper<T> getAllForPerson(final Person person,
			final SspUser requestor,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), requestor, sAndP);
	}

	/**
	 * this method will throw UnsupportedOperationException. Use
	 * getAllForPerson with person, requestor and sAndP instead.
	 */
	@Override
	public PagingWrapper<T> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		throw new UnsupportedOperationException(
				"For Restricted Person Associated Auditables, you must call getAllForPersonId and supply a requestor");
	}

	@Override
	protected abstract RestrictedPersonAssocAuditableDao<T> getDao();

}
