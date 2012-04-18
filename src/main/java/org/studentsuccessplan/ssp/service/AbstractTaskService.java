package org.studentsuccessplan.ssp.service;

import java.util.List;

import org.studentsuccessplan.ssp.model.AbstractTask;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface AbstractTaskService<T extends AbstractTask>
		extends AuditableCrudService<T> {

	List<T> getAllForPersonId(Person person, SortingAndPaging sAndP);

	List<T> getAllForPersonId(Person person, boolean complete,
			SortingAndPaging sAndP);

	List<T> getAllForSessionId(String sessionId, SortingAndPaging sAndP);

	List<T> getAllForSessionId(String sessionId, boolean complete,
			SortingAndPaging sAndP);

	List<T> getAllWhichNeedRemindersSent(SortingAndPaging sAndP);

	void markTaskComplete(T taskId);

	void markTaskIncomplete(T taskId);

	void markTaskCompletion(T task, boolean complete);

	void setReminderSentDateToToday(T taskId);

}