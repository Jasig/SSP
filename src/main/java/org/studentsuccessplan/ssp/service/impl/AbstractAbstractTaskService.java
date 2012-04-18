package org.studentsuccessplan.ssp.service.impl;

import java.util.Date;
import java.util.List;

import org.studentsuccessplan.ssp.dao.AbstractTaskDao;
import org.studentsuccessplan.ssp.model.AbstractTask;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.service.AbstractAuditableCrudService;
import org.studentsuccessplan.ssp.service.AbstractTaskService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

/**
 * Base class for creating the Task Services
 * 
 * @author daniel.bower
 * 
 * @param <T>
 *            A detailed type of Task that inherits from {@link AbstractTask}.
 */
public abstract class AbstractAbstractTaskService<T extends AbstractTask>
		extends AbstractAuditableCrudService<T> implements
		AbstractTaskService<T> {

	@Override
	protected abstract AbstractTaskDao<T> getDao();

	@Override
	public List<T> getAllForPersonId(Person person, SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public List<T> getAllForPersonId(Person person, boolean complete,
			SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), complete, sAndP);
	}

	@Override
	public List<T> getAllForSessionId(String sessionId, SortingAndPaging sAndP) {
		return getDao().getAllForSessionId(sessionId, sAndP);
	}

	@Override
	public List<T> getAllForSessionId(String sessionId, boolean complete,
			SortingAndPaging sAndP) {
		return getDao().getAllForSessionId(sessionId, complete, sAndP);
	}

	@Override
	public List<T> getAllWhichNeedRemindersSent(SortingAndPaging sAndP) {
		return getDao().getAllWhichNeedRemindersSent(sAndP);
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
