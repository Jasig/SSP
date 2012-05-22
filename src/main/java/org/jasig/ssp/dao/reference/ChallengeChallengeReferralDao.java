package org.jasig.ssp.dao.reference;

import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ChallengeChallengeReferral;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

@Repository
public class ChallengeChallengeReferralDao
		extends AbstractReferenceAuditableCrudDao<ChallengeChallengeReferral>
		implements AuditableCrudDao<ChallengeChallengeReferral> {

	public ChallengeChallengeReferralDao() {
		super(ChallengeChallengeReferral.class);
	}

	public PagingWrapper<ChallengeChallengeReferral> getAllForChallenge(
			final UUID challengeId, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("challenge.id", challengeId));
		return processCriteriaWithPaging(query, sAndP);
	}

	public PagingWrapper<ChallengeChallengeReferral> getAllforChallengeReferralAndChallenge(
			final UUID challengeReferralId, final UUID challengeId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("challengeReferral.id", challengeReferralId));
		query.add(Restrictions.eq("challenge.id", challengeId));
		return processCriteriaWithPaging(query, sAndP);
	}

}
