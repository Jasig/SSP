package org.jasig.ssp.dao.reference;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class JournalTrackJournalStepDao
		extends AbstractReferenceAuditableCrudDao<JournalTrackJournalStep>
		implements AuditableCrudDao<JournalTrackJournalStep> {

	public JournalTrackJournalStepDao() {
		super(JournalTrackJournalStep.class);
	}

	public PagingWrapper<JournalTrackJournalStep> getAllForJournalTrack(
			final UUID journalTrackId, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("journalTrack.id", journalTrackId));
		return processCriteriaWithPaging(query, sAndP);
	}

	public PagingWrapper<JournalTrackJournalStep> getAllForJournalTrackAndJournalStep(
			final UUID journalTrackId, final UUID journalStepId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("journalTrack.id", journalTrackId));
		query.add(Restrictions.eq("journalStep.id", journalStepId));
		return processCriteriaWithPaging(query, sAndP);
	}

}
