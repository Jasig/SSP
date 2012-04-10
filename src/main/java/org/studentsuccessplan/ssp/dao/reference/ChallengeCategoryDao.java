package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.ChallengeCategory;

/**
 * Data access class for the ChallengeCategory reference entity.
 */
@Repository
public class ChallengeCategoryDao extends
		ReferenceAuditableCrudDao<ChallengeCategory>
		implements AuditableCrudDao<ChallengeCategory> {

	public ChallengeCategoryDao() {
		super(ChallengeCategory.class);
	}
}
