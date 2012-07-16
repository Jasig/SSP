package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.PersonServiceReason;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Person association to ServiceReason DAO
 */
@Repository
public class PersonServiceReasonDao
		extends AbstractPersonAssocAuditableCrudDao<PersonServiceReason>
		implements PersonAssocAuditableCrudDao<PersonServiceReason> {

	/**
	 * Constructor
	 */
	public PersonServiceReasonDao() {
		super(PersonServiceReason.class);
	}

	public PagingWrapper<PersonServiceReason> getAllForPersonIdAndServiceReasonId(
			final UUID personId, final UUID serviceReasonId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.eq("serviceReason.id",
				serviceReasonId));
		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}
}