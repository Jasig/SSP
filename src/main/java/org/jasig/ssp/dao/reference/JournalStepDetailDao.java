package org.jasig.ssp.dao.reference;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
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

	/**
	 * Get all JournalStepDetails for the specified JournalStep.
	 * 
	 * <p>
	 * Filters out associations that have been deleted, but not any associations
	 * that may point to deleted Details or Steps.
	 * 
	 * @param journalStepId
	 *            JournalStep identifier
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return All specified associations between JournalStepDetails and
	 *         JournalSteps. (Referenced Details or Steps may be
	 *         {@link ObjectStatus#INACTIVE} though.)
	 */
	public PagingWrapper<JournalStepDetail> getAllForJournalStep(
			final UUID journalStepId,
			final SortingAndPaging sAndP) {

		final Criteria criteria = createCriteria();
		final Criteria subQuery = criteria.createCriteria(
				"journalStepJournalStepDetails")
				.add(Restrictions.eq("journalStep.id", journalStepId));
		sAndP.addStatusFilterToCriteria(subQuery);

		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}
}