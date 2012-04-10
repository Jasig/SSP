package org.studentsuccessplan.ssp.dao;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.AbstractTask;

public abstract class AbstractTaskDao<T extends AbstractTask> extends
		AbstractAuditableCrudDao<T> implements
		AuditableCrudDao<T> {

	protected AbstractTaskDao(Class<T> persistentClass) {
		super(persistentClass);
	}

	public List<T> getAllForPersonId(UUID personId) {
		// only return ObjectStatus.ACTIVE
		return null;
	}

	public List<T> getAllForPersonId(UUID personId, boolean complete) {
		// completion just looks for null completed date
		// only return ObjectStatus.ACTIVE
		return null;
	}

	public List<T> getAllForSessionId(String sessionId) {
		// only return ObjectStatus.ACTIVE
		return null;
	}

	public List<T> getAllForSessionId(String sessionId,
			boolean complete) {
		// completion just looks for null completed date
		// only return ObjectStatus.ACTIVE
		return null;
	}

	public List<T> getAllWhichNeedRemindersSent() {
		/*
		 * where completedDate is null " +
		 * "and reminderSentDate is null " +
		 * "and dueDate is not null " +
		 * "and dueDate > current_timestamp()
		 */
		return null;
	}

	public void markTaskComplete(UUID taskId) {

	}

	public void markTaskIncomplete(UUID taskId) {

	}

	public void setReminderSentDateToToday(UUID taskId) {

	}
}
