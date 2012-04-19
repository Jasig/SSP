package org.studentsuccessplan.ssp.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.studentsuccessplan.ssp.model.AbstractTask;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

/**
 * Base class for reading AbstractTasks from the Database
 * 
 * @param <T>
 *            Any task class that extends AbstractTask
 */
public abstract class AbstractTaskDao<T extends AbstractTask> extends
		AbstractAuditableCrudDao<T> implements AuditableCrudDao<T> {

	protected AbstractTaskDao(final Class<T> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllForPersonId(final UUID personId,
			final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("person.id", personId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllForPersonId(final UUID personId,
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
	public List<T> getAllForSessionId(final String sessionId,
			final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.eq("sessionId", sessionId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllForSessionId(final String sessionId,
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
	public List<T> getAllWhichNeedRemindersSent(final SortingAndPaging sAndP) {
		Criteria criteria = createCriteria(sAndP);
		criteria.add(Restrictions.isNull("completedDate"));
		criteria.add(Restrictions.isNull("reminderSentDate"));
		criteria.add(Restrictions.isNotNull("dueDate"));
		criteria.add(Restrictions.gt("dueDate", new Date()));
		return criteria.list();
	}

}
