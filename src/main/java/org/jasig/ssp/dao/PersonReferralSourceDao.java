package org.jasig.ssp.dao;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.PersonReferralSource;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class PersonReferralSourceDao
		extends AbstractPersonAssocAuditableCrudDao<PersonReferralSource>
		implements PersonAssocAuditableCrudDao<PersonReferralSource> {

	/**
	 * Constructor
	 */
	public PersonReferralSourceDao() {
		super(PersonReferralSource.class);
	}

	public PagingWrapper<PersonReferralSource> getAllForPersonIdAndReferralSourceId(
			final UUID personId, final UUID referralSourceId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.eq("referralSource.id",
				referralSourceId));
		return processCriteriaWithPaging(criteria, sAndP);
	}
}
