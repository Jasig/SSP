package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class PersonSpecialServiceGroupDao
		extends AbstractPersonAssocAuditableCrudDao<PersonSpecialServiceGroup>
		implements PersonAssocAuditableCrudDao<PersonSpecialServiceGroup> {

	/**
	 * Constructor
	 */
	public PersonSpecialServiceGroupDao() {
		super(PersonSpecialServiceGroup.class);
	}

	public PagingWrapper<PersonSpecialServiceGroup> getAllForPersonIdAndSpecialServiceGroupId(
			final UUID personId, final UUID specialServiceGroupId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.eq("specialServiceGroup.id",
				specialServiceGroupId));
		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}
}