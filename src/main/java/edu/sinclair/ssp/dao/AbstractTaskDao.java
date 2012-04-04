package edu.sinclair.ssp.dao;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.AbstractTask;

public abstract class AbstractTaskDao<T extends AbstractTask> {

	public T get(UUID id) {
		return null;
	}

	public List<T> getAllForPersonId(UUID personId, boolean complete) {
		// completion just looks for null completed date
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

	public void save(T task) {

	}
}
