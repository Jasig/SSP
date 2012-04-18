package org.studentsuccessplan.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Repository
public class TaskDao extends AbstractTaskDao<Task> {

	protected TaskDao() {
		super(Task.class);
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForPersonIdAndChallengeReferralId(
			final UUID personId,
			final boolean complete, final UUID challengeReferralId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.eq("challengeReferral.id",
				challengeReferralId));

		if (complete) {
			criteria.add(Restrictions.isNull("completedDate"));
		} else {
			criteria.add(Restrictions.isNotNull("completedDate"));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForSessionIdAndChallengeReferralId(
			final String sessionId, final boolean complete,
			final UUID challengeReferralId, final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("sessionId", sessionId));
		criteria.add(Restrictions.eq("challengeReferral.id",
				challengeReferralId));

		if (complete) {
			criteria.add(Restrictions.isNull("completedDate"));
		} else {
			criteria.add(Restrictions.isNotNull("completedDate"));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForTaskGroupId(UUID taskGroupId,
			boolean complete, UUID createdBy, SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.createCriteria("taskGroups")
				.add(Restrictions.eq("id", taskGroupId));

		if (complete) {
			criteria.add(Restrictions.isNull("completedDate"));
		} else {
			criteria.add(Restrictions.isNotNull("completedDate"));
		}

		return criteria.list();
	}
}
