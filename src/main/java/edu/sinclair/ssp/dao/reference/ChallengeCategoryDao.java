package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.ChallengeCategory;

/**
 * Data access class for the ChallengeCategory reference entity.
 */
@Repository
public class ChallengeCategoryDao extends
		ReferenceAuditableCrudDao<ChallengeCategory> implements
AuditableCrudDao<ChallengeCategory> {

	protected ChallengeCategoryDao() {
		super(ChallengeCategory.class);
	}
}
