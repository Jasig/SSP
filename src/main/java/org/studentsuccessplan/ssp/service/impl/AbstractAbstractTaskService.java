package org.studentsuccessplan.ssp.service.impl;

import java.util.Date;
import java.util.List;

import org.studentsuccessplan.ssp.dao.AbstractTaskDao;
import org.studentsuccessplan.ssp.model.AbstractTask;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.AbstractAuditableCrudService;
import org.studentsuccessplan.ssp.service.AbstractTaskService;

/**
 * Base class for creating the Task Services
 * 
 * @author daniel
 * 
 * @param <T>
 */
public abstract class AbstractAbstractTaskService<T extends AbstractTask>
		extends AbstractAuditableCrudService<T>
		implements AbstractTaskService<T> {

	@Override
	protected abstract AbstractTaskDao<T> getDao();

	@Override
	public List<T> getAllForPersonId(Person person) {
		return getDao().getAllForPersonId(person.getId());
	}

	@Override
	public List<T> getAllForPersonId(Person person, boolean complete) {
		return getDao().getAllForPersonId(person.getId(), complete);
	}

	@Override
	public List<T> getAllForSessionId(String sessionId) {
		return getDao().getAllForSessionId(sessionId);
	}

	@Override
	public List<T> getAllForSessionId(String sessionId,
			boolean complete) {
		return getDao().getAllForSessionId(sessionId, complete);
	}

	@Override
	public List<T> getAllWhichNeedRemindersSent() {
		return getDao().getAllWhichNeedRemindersSent();
	}

	@Override
	public void markTaskComplete(T task) {
		task.setCompletedDate(new Date());
		getDao().save(task);
	}

	@Override
	public void markTaskIncomplete(T task) {
		task.setCompletedDate(null);
		getDao().save(task);
	}

	@Override
	public void markTaskCompletion(T task, boolean complete) {
		if (complete) {
			markTaskComplete(task);
		} else {
			markTaskIncomplete(task);
		}
	}

	@Override
	public void setReminderSentDateToToday(T task) {
		task.setReminderSentDate(new Date());
		getDao().save(task);
	}
}
