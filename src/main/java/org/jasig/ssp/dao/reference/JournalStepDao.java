package org.jasig.ssp.dao.reference;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the JournalStep reference entity.
 */
@Repository
public class JournalStepDao extends
		AbstractReferenceAuditableCrudDao<JournalStep>
		implements AuditableCrudDao<JournalStep> {

	public JournalStepDao() {
		super(JournalStep.class);
	}

	public PagingWrapper<JournalStep> getAllForJournalTrack(
			final UUID journalTrackId,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria();
		final Criteria subQuery = criteria
				.createCriteria("journalTrackJournalSteps");
		subQuery.add(Restrictions.eq("journalTrack.id", journalTrackId));

		return processCriteriaWithPaging(criteria, sAndP);
	}
}
