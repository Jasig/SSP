package org.studentsuccessplan.ssp.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.studentsuccessplan.ssp.model.AbstractTask;
import org.studentsuccessplan.ssp.model.ObjectStatus;

//:TODO paging for all of these
/**
 * Base class for reading AbstractTasks from the Database
 * 
 * @param <T>
 *            Any task class that extends AbstractTask
 */
public abstract class AbstractTaskDao<T extends AbstractTask> extends
		AbstractAuditableCrudDao<T> implements AuditableCrudDao<T> {

	protected AbstractTaskDao(Class<T> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllForPersonId(UUID personId) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		criteria.add(Restrictions.eq("person.id", personId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllForPersonId(UUID personId, boolean complete) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		criteria.add(Restrictions.eq("person.id", personId));

		if (complete) {
			criteria.add(Restrictions.isNull("completedDate"));
		} else {
			criteria.add(Restrictions.isNotNull("completedDate"));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllForSessionId(String sessionId) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		criteria.add(Restrictions.eq("sessionId", sessionId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllForSessionId(String sessionId, boolean complete) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		criteria.add(Restrictions.eq("sessionId", sessionId));

		if (complete) {
			criteria.add(Restrictions.isNull("completedDate"));
		} else {
			criteria.add(Restrictions.isNotNull("completedDate"));
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAllWhichNeedRemindersSent() {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		criteria.add(Restrictions.isNull("completedDate"));
		criteria.add(Restrictions.isNull("reminderSentDate"));
		criteria.add(Restrictions.isNotNull("dueDate"));
		criteria.add(Restrictions.gt("dueDate", new Date()));
		return criteria.list();
	}

}
