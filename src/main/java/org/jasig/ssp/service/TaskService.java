package org.studentsuccessplan.ssp.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface TaskService
		extends PersonAssocService<Task> {

	/**
	 * Get all tasks for the Person
	 */
	@Override
	List<Task> getAllForPerson(Person person, SortingAndPaging sAndP);

	/**
	 * Get all tasks for the Person in the given completion state
	 * 
	 * @param person
	 * @param complete
	 * @param sAndP
	 *            sorting and paging options
	 * @return All tasks for the Person in the given completion state
	 */
	List<Task> getAllForPerson(Person person, boolean complete,
			SortingAndPaging sAndP);

	/**
	 * Get all tasks for the Session
	 * 
	 * @param sessionId
	 * @param sAndP
	 * @return All tasks for the Session
	 */
	List<Task> getAllForSessionId(String sessionId, SortingAndPaging sAndP);

	/**
	 * Get all tasks for the Session in the given completion state
	 * 
	 * @param sessionId
	 * @param complete
	 * @param sAndP
	 * @return All tasks for the Session in the given completion state
	 */
	List<Task> getAllForSessionId(String sessionId, boolean complete,
			SortingAndPaging sAndP);

	/**
	 * Get all tasks which need to have reminders sent
	 * 
	 * @param sAndP
	 * @return All tasks which need to have reminders sent
	 */
	List<Task> getAllWhichNeedRemindersSent(SortingAndPaging sAndP);

	/**
	 * Mark the task as complete
	 * 
	 * @param taskId
	 */
	void markTaskComplete(Task taskId);

	/**
	 * Mark the task as incomplete
	 * 
	 * @param taskId
	 */
	void markTaskIncomplete(Task taskId);

	/**
	 * Mark the task with the given completion status;
	 * 
	 * @param task
	 * @param complete
	 */
	void markTaskCompletion(Task task, boolean complete);

	/**
	 * Set the sent date for the task to today
	 * 
	 * @param taskId
	 */
	void setReminderSentDateToToday(Task taskId);

	/**
	 * Get all tasks for the person, challenge referral, and completion status.
	 * 
	 * @param person
	 * @param complete
	 * @param challengeReferral
	 * @param sAndP
	 * @return All tasks for the person, challenge referral, and completion
	 *         status.
	 */
	List<Task> getAllForPersonAndChallengeReferral(Person person,
			boolean complete, ChallengeReferral challengeReferral,
			SortingAndPaging sAndP);

	/**
	 * Get all tasks for the session, challenge referral, and completion status.
	 * 
	 * @param sessionId
	 * @param complete
	 * @param challengeReferral
	 * @param sAndP
	 *            sorting and paging options
	 * @return All tasks for the session, challenge referral, and completion
	 *         status.
	 */
	List<Task> getAllForSessionIdAndChallengeReferral(
			String sessionId, boolean complete,
			ChallengeReferral challengeReferral, SortingAndPaging sAndP);

	/**
	 * Get all tasks for the person, grouped by Task Group
	 * 
	 * @param person
	 *            Person
	 * @param sAndP
	 *            sorting and paging options
	 * @return All tasks for the person, grouped by Task Group
	 */
	Map<String, List<Task>> getAllGroupedByTaskGroup(Person person,
			SortingAndPaging sAndP);

	/**
	 * Create a task for a person using the challenge referral and sessionId
	 * 
	 * @param challenge
	 * @param challengeReferral
	 * @param person
	 * @param sessionId
	 * @return Created task
	 */
	Task createForPersonWithChallengeReferral(Challenge challenge,
			ChallengeReferral challengeReferral, Person person, String sessionId);

	/**
	 * Create a custom task for a person
	 * 
	 * @param name
	 * @param description
	 * @param student
	 * @param sessionId
	 * @return Created task
	 */
	Task createCustomTaskForPerson(String name, String description,
			Person student, String sessionId);

	/**
	 * Send a notice to the student about the task using the messageTemplate
	 * 
	 * @param customTask
	 * @param messageTemplateId
	 * @throws Exception
	 */
	void sendNoticeToStudentOnCustomTask(Task customTask,
			UUID messageTemplateId) throws Exception;

	/**
	 * Send a list of the given tasks to each emailAddress and each recipient.
	 * 
	 * @param tasks
	 * @param student
	 * @param emailAddresses
	 * @param recipients
	 * @throws Exception
	 */
	void sendTasksForPersonToEmail(final List<Task> tasks,
			final Person student, final List<String> emailAddresses,
			final List<Person> recipients) throws Exception;

	/**
	 * Get tasks from taskIds
	 * 
	 * @param taskIds
	 * @param sAndP
	 *            sorting and paging options
	 * @return Tasks from task ids
	 */
	List<Task> getTasksInList(List<UUID> taskIds, SortingAndPaging sAndP);

	/**
	 * Sends taskReminders for all tasks in the task reminder window
	 */
	void sendAllTaskReminderNotifications();

	/**
	 * If tasks are selected, get them, otherwise return the tasks for the
	 * person, (just for the session if it is the anonymous user).
	 * 
	 * @param selectedIds
	 * @param person
	 * @param sessionId
	 * @param sAndP
	 *            sorting and paging options
	 * @return Selected tasks, or all tasks for the anonymous user.
	 */
	List<Task> getTasksForPersonIfNoneSelected(
			final List<UUID> selectedIds, final Person person,
			final String sessionId, final SortingAndPaging sAndP);
}