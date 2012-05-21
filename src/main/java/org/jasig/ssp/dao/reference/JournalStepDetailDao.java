package org.jasig.ssp.dao.reference;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the JournalStepDetail reference entity.
 */
@Repository
public class JournalStepDetailDao extends
		AbstractReferenceAuditableCrudDao<JournalStepDetail>
		implements AuditableCrudDao<JournalStepDetail> {

	public JournalStepDetailDao() {
		super(JournalStepDetail.class);
	}

	public PagingWrapper<JournalStepDetail> getAllForJournalStep(
			final UUID journalStepId,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria();
		final Criteria subQuery = criteria
				.createCriteria("journalStepJournalStepDetails");
		subQuery.add(Restrictions.eq("journalStep.id", journalStepId));

		return processCriteriaWithPaging(criteria, sAndP);
	}
}
