package org.jasig.ssp.dao.reference;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class JournalStepJournalStepDetailDao extends
		AbstractReferenceAuditableCrudDao<JournalStepJournalStepDetail>
		implements AuditableCrudDao<JournalStepJournalStepDetail> {

	public JournalStepJournalStepDetailDao() {
		super(JournalStepJournalStepDetail.class);
	}

	public PagingWrapper<JournalStepJournalStepDetail> getAllForJournalStep(
			final UUID journalStepId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("journalStep.id", journalStepId));
		return processCriteriaWithPaging(query, sAndP);
	}

	public PagingWrapper<JournalStepJournalStepDetail> getAllForJournalStepDetailAndJournalStep(
			final UUID journalStepDetailId, final UUID journalStepId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("journalStep.id", journalStepId));
		query.add(Restrictions.eq("journalStepDetail.id", journalStepDetailId));
		return processCriteriaWithPaging(query, sAndP);
	}
}
