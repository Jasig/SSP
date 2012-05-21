package org.jasig.ssp.dao.reference;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ChallengeCategory;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the ChallengeCategory reference entity.
 */
@Repository
public class ChallengeCategoryDao extends
		AbstractReferenceAuditableCrudDao<ChallengeCategory>
		implements AuditableCrudDao<ChallengeCategory> {

	public ChallengeCategoryDao() {
		super(ChallengeCategory.class);
	}

	public PagingWrapper<ChallengeCategory> getAllForCategory(
			final UUID categoryId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("category.id", categoryId));
		return processCriteriaWithPaging(query, sAndP);
	}

	public PagingWrapper<ChallengeCategory> getAllForChallengeAndCategory(
			final UUID challengeId, final UUID categoryId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("category.id", categoryId));
		query.add(Restrictions.eq("challenge.id", challengeId));
		return processCriteriaWithPaging(query, sAndP);
	}
}
