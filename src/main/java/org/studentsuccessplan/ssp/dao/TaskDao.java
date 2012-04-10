package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.Task;

public class TaskDao extends AbstractTaskDao<Task> {

	public List<Task> getAllForPersonIdAndChallengeReferralId(UUID personId,
			boolean complete, UUID challengeReferralId) {
		// completion just looks for null completed date
		// only return ObjectStatus.ACTIVE
		return null;
	}

	public List<Task> getAllForSessionIdAndChallengeReferralId(
			String sessionId,
			boolean complete, UUID challengeReferralId) {
		// completion just looks for null completed date
		// only return ObjectStatus.ACTIVE
		return null;
	}
}
