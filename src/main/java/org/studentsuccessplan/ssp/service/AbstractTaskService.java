package org.studentsuccessplan.ssp.service;

import java.util.List;

import org.studentsuccessplan.ssp.model.AbstractTask;
import org.studentsuccessplan.ssp.model.Person;

public interface AbstractTaskService<T extends AbstractTask>
		extends AuditableCrudService<T> {

	List<T> getAllForPersonId(Person person);

	List<T> getAllForPersonId(Person person, boolean complete);

	List<T> getAllForSessionId(String sessionId);

	List<T> getAllForSessionId(String sessionId, boolean complete);

	List<T> getAllWhichNeedRemindersSent();

	void markTaskComplete(T taskId);

	void markTaskIncomplete(T taskId);

	void markTaskCompletion(T task, boolean complete);

	void setReminderSentDateToToday(T taskId);

}