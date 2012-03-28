package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.Challenge;

/**
 * Data access class for the Challenge reference entity.
 */
@Repository
public class ChallengeDao extends ReferenceAuditableCrudDao<Challenge>
		implements AuditableCrudDao<Challenge> {

	public ChallengeDao() {
		super(Challenge.class);
	}
}
