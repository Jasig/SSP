/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface TaskService
		extends RestrictedPersonAssocAuditableService<Task> {

	/**
	 * Get all tasks for the Person in the given completion state
	 * 
	 * @param person
	 * @param complete
	 * @param requester
	 * @param sAndP
	 *            sorting and paging options
	 * @return All tasks for the Person in the given completion state
	 */
	List<Task> getAllForPerson(Person person, boolean complete,
			SspUser requester, SortingAndPaging sAndP);

	/**
	 * Get all tasks for the Session
	 * 
	 * @param sessionId
	 * @param sAndP
	 * @return All tasks for the Session
	 */
	List<Task> getAllForSessionId(final String sessionId,
			final SortingAndPaging sAndP);

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
	 * @param requester
	 * @param sAndP
	 * @return All tasks for the person, challenge referral, and completion
	 *         status.
	 */
	List<Task> getAllForPersonAndChallengeReferral(Person person,
			boolean complete, ChallengeReferral challengeReferral,
			SspUser requester,
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
	 * @param requester
	 * @param sAndP
	 *            sorting and paging options
	 * @return All tasks for the person, grouped by Task Group
	 */
	Map<String, List<Task>> getAllGroupedByTaskGroup(Person person,
			SspUser requester,
			SortingAndPaging sAndP);

	/**
	 * Create a task for a person using the challenge referral and sessionId
	 * 
	 * @param challenge
	 * @param challengeReferral
	 * @param person
	 * @param sessionId
	 * @return Created task
	 * @throws ObjectNotFoundException
	 *             If referenced data does not exist
	 * @throws ValidationException
	 */
	Task createForPersonWithChallengeReferral(Challenge challenge,
			ChallengeReferral challengeReferral, Person person, String sessionId)
			throws ObjectNotFoundException, ValidationException;

	/**
	 * Create a custom task for a person
	 * 
	 * @param name
	 * @param description
	 * @param student
	 * @param sessionId
	 * @return Created task
	 * @throws ObjectNotFoundException
	 * @throws ValidationException
	 */
	Task createCustomTaskForPerson(String name, String description,
			Person student, String sessionId) throws ObjectNotFoundException,
			ValidationException;

	/**
	 * Send a notice to the student about the task
	 * 
	 * @param customTask
	 * @throws ObjectNotFoundException
	 * @throws ValidationException
	 * @throws SendFailedException
	 */
	void sendNoticeToStudentOnCustomTask(Task customTask
			) throws ObjectNotFoundException,
					ValidationException, SendFailedException;

	/**
	 * Send a list of the given tasks to each emailAddress and each recipient.
	 * 
	 * @param tasks
	 *            the tasks
	 * @param goals
	 *            the goals
	 * @param student
	 *            the student
	 * @param emailAddresses
	 *            e-mail addresses
	 * @param recipients
	 *            the recipients
	 * @throws ObjectNotFoundException
	 *             If the specified tasks or any referenced data could not be
	 *             found.
	 * @throws ValidationException
	 *             If there were any validation errors.
	 */
	void sendTasksForPersonToEmail(final List<Task> tasks,
			final List<Goal> goals,
			final Person student, final List<String> emailAddresses, 
			final List<Person> recipients) throws ObjectNotFoundException,
			ValidationException;

	/**
	 * Sends taskReminders for all tasks in the task reminder window
	 */
	void sendAllTaskReminderNotifications();

	/**
	 * If tasks are selected, get them, otherwise return the tasks for the
	 * person, (just for the session if it is the anonymous user).
	 * 
	 * @param selectedIds
	 *            Selected {@link Task} identifiers
	 * @param person
	 *            the person
	 * @param requester
	 *            the requester
	 * @param sessionId
	 *            session identifier
	 * @param sAndP
	 *            sorting and paging options
	 * @return Selected tasks, or all tasks for the anonymous user.
	 */
	List<Task> getTasksForPersonIfNoneSelected(
			final List<UUID> selectedIds, final Person person,
			final SspUser requester, final String sessionId,
			final SortingAndPaging sAndP);

	
	Long getTaskCountForCoach(Person coach, Date createDateFrom,
			Date createDateTo, List<UUID> studentTypeIds);
	
	Long getStudentTaskCountForCoach(Person coach, Date createDateFrom,
			Date createDateTo, List<UUID> studentTypeIds);
	
	PagingWrapper<EntityStudentCountByCoachTO> getStudentTaskCountForCoaches(EntityCountByCoachSearchForm form); 

}