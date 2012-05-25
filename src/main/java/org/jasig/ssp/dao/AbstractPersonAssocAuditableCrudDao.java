package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.PersonAssocAuditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public abstract class AbstractPersonAssocAuditableCrudDao<T extends PersonAssocAuditable>
		extends AbstractAuditableCrudDao<T>
		implements PersonAssocAuditableCrudDao<T> {

	protected AbstractPersonAssocAuditableCrudDao(final Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	public PagingWrapper<T> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		return processCriteriaWithPaging(criteria, sAndP);
	}
}
