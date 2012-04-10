package org.studentsuccessplan.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.model.Task;

@Repository
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
