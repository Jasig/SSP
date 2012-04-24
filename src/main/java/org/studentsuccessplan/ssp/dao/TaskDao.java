package org.studentsuccessplan.ssp.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Repository
public class TaskDao extends
		AbstractAuditableCrudDao<Task> implements AuditableCrudDao<Task> {

	protected TaskDao() {
		super(Task.class);
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForPersonId(final UUID personId,
			final boolean complete, final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));

		if (complete) {
			criteria.add(Restrictions.isNull("completedDate"));
		} else {
			criteria.add(Restrictions.isNotNull("completedDate"));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForSessionId(final String sessionId,
			final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("sessionId", sessionId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForSessionId(final String sessionId,
			final boolean complete, final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("sessionId", sessionId));

		if (complete) {
			criteria.add(Restrictions.isNull("completedDate"));
		} else {
			criteria.add(Restrictions.isNotNull("completedDate"));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllWhichNeedRemindersSent(final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.isNull("completedDate"));
		criteria.add(Restrictions.isNull("reminderSentDate"));
		criteria.add(Restrictions.isNotNull("dueDate"));
		criteria.add(Restrictions.gt("dueDate", new Date()));
		return criteria.list();
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
}
