package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ChallengeCategory;

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
}
