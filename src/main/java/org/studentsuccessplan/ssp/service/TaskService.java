package org.studentsuccessplan.ssp.service;

import java.util.List;
import java.util.Map;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface TaskService
		extends AuditableCrudService<Task> {

	List<Task> getAllForPersonId(Person person, SortingAndPaging sAndP);

	List<Task> getAllForPersonId(Person person, boolean complete,
			SortingAndPaging sAndP);

	List<Task> getAllForSessionId(String sessionId, SortingAndPaging sAndP);

	List<Task> getAllForSessionId(String sessionId, boolean complete,
			SortingAndPaging sAndP);

	List<Task> getAllWhichNeedRemindersSent(SortingAndPaging sAndP);

	void markTaskComplete(Task taskId);

	void markTaskIncomplete(Task taskId);

	void markTaskCompletion(Task task, boolean complete);

	void setReminderSentDateToToday(Task taskId);

	List<Task> getAllForPersonAndChallengeReferral(Person person,
			boolean complete, ChallengeReferral challengeReferral,
			SortingAndPaging sAndP);

	List<Task> getAllForSessionIdAndChallengeReferral(
			String sessionId, boolean complete,
			ChallengeReferral challengeReferral, SortingAndPaging sAndP);

	Map<String, List<Task>> getAllGroupedByTaskGroup(Person person,
			SortingAndPaging sAndP);

}