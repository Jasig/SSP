package org.jasig.ssp.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Task DAO
 */
@Repository
public class TaskDao extends
		AbstractAuditableCrudDao<Task> implements AuditableCrudDao<Task> {

	protected TaskDao() {
		super(Task.class);
	}

	@SuppressWarnings("unchecked")
	public PagingWrapper<Task> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		final long totalRows = (Long) createCriteria()
				.add(Restrictions.eq("person.id", personId))
				.setProjection(Projections.rowCount())
				.uniqueResult();

		return new PagingWrapper<Task>(totalRows,
				createCriteria(sAndP)
						.add(Restrictions.eq("person.id", personId))
						.list());
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForPersonId(final UUID personId,
			final boolean complete, final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
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
		final Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("sessionId", sessionId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Task> getAllForSessionId(final String sessionId,
			final boolean complete, final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
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
		final Criteria criteria = createCriteria(sAndP);
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

	@SuppressWarnings("unchecked")
	public List<Task> getTasksInList(final List<UUID> taskIds,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria(sAndP);
		// :TODO this ought to start working correctly when ids are switched to
		// Long?
		criteria.add(Restrictions.in("id", taskIds));
		return criteria.list();
	}
}
