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
package org.jasig.ssp.service.impl; // NOPMD

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.SendFailedException;
import javax.validation.constraints.NotNull;

import org.codehaus.plexus.util.StringUtils;
import org.jasig.ssp.dao.TaskDao;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.AbstractRestrictedPersonAssocAuditableService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.GoalTO;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.transferobject.reports.EntityCountByCoachSearchForm;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

@Service
@Transactional
public class TaskServiceImpl
		extends AbstractRestrictedPersonAssocAuditableService<Task>
		implements TaskService {

	@Autowired
	private transient TaskDao dao;

	@Autowired
	private transient MessageService messageService;

	@Autowired
	private transient MessageTemplateService messageTemplateService;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient ConfigService configService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskServiceImpl.class);

	@Override
	protected TaskDao getDao() {
		return dao;
	}

	private int getNumberOfDaysPriorForTaskReminder() {
		final String numVal = configService
				.getByNameNull("numberOfDaysPriorForTaskReminder");
		if (!StringUtils.isEmpty(numVal) && StringUtils.isNumeric(numVal)) {
			return Integer.valueOf(numVal);
		}
		return 14;
	}

	@Override
	public Task save(final Task obj) throws ObjectNotFoundException {
		return getDao().save(obj);
	}

	@Override
	public List<Task> getAllForPerson(final Person person,
			final boolean complete,
			final SspUser requester,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), complete,
				requester, sAndP);
	}

	@Override
	public List<Task> getAllForSessionId(final String sessionId,
			final SortingAndPaging sAndP) {
		return getDao().getAllForSessionId(sessionId, sAndP);
	}

	@Override
	public List<Task> getAllForSessionId(final String sessionId,
			final boolean complete,
			final SortingAndPaging sAndP) {
		return getDao().getAllForSessionId(sessionId, complete, sAndP);
	}

	@Override
	public List<Task> getAllWhichNeedRemindersSent(final SortingAndPaging sAndP) {
		return getDao().getAllWhichNeedRemindersSent(sAndP);
	}

	@Override
	public void markTaskComplete(final Task task) {
		task.setCompletedDate(new Date());
		getDao().save(task);
	}

	@Override
	public void markTaskIncomplete(final Task task) {
		task.setCompletedDate(null);
		getDao().save(task);
	}

	@Override
	public void markTaskCompletion(final Task task, final boolean complete) {
		if (complete) {
			markTaskComplete(task);
		} else {
			markTaskIncomplete(task);
		}
	}

	@Override
	public void setReminderSentDateToToday(final Task task) {
		task.setReminderSentDate(new Date());
		getDao().save(task);
	}

	@Override
	public List<Task> getAllForPersonAndChallengeReferral(final Person person,
			final boolean complete, final ChallengeReferral challengeReferral,
			final SspUser requester,
			final SortingAndPaging sAndP) {
		return dao.getAllForPersonIdAndChallengeReferralId(person.getId(),
				complete, challengeReferral.getId(),
				requester, sAndP);
	}

	@Override
	public List<Task> getAllForSessionIdAndChallengeReferral(
			final String sessionId, final boolean complete,
			final ChallengeReferral challengeReferral,
			final SortingAndPaging sAndP) {
		return dao.getAllForSessionIdAndChallengeReferralId(sessionId,
				complete, challengeReferral.getId(), sAndP);
	}

	@Override
	public Map<String, List<Task>> getAllGroupedByTaskGroup(
			final Person person,
			final SspUser requester,
			final SortingAndPaging sAndP) {

		final Map<String, List<Task>> grouped = Maps.newTreeMap();
		final PagingWrapper<Task> tasksForPerson = dao
				.getAllForPersonId(person.getId(), requester,
						sAndP);

		for (final Task task : tasksForPerson.getRows()) {
			final String group = task.getGroup();
			List<Task> tasksForGroup;
			if (grouped.keySet().contains(group)) {
				tasksForGroup = grouped.get(group);
			} else {
				tasksForGroup = new ArrayList<Task>(); // NOPMD by jon.adams
				grouped.put(group, tasksForGroup);
			}

			tasksForGroup.add(task);
		}

		return grouped;
	}

	@Override
	public Task createForPersonWithChallengeReferral(final Challenge challenge,
			final ChallengeReferral challengeReferral, final Person person,
			final String sessionId) throws ObjectNotFoundException,
			ValidationException {

		// Create, fill, and persist a new Task
		final Task task = new Task();

		task.setChallenge(challenge);
		task.setChallengeReferral(challengeReferral);
		task.setPerson(person);
		task.setSessionId(sessionId);
		task.setDescription(challengeReferral.getPublicDescription());
		task.setName(challengeReferral.getName());
		task.setLink(challengeReferral.getLink());

		setDefaultConfidentialityLevel(task, challenge);

		create(task);

		return task;
	}

	private void setDefaultConfidentialityLevel(final Task task,
			final Challenge challenge) {
		if (challenge != null
				&& challenge.getDefaultConfidentialityLevel() != null) {
			task.setConfidentialityLevel(challenge
					.getDefaultConfidentialityLevel());
		}

		if (task.getConfidentialityLevel() == null) {
			try {
				task.setConfidentialityLevel(confidentialityLevelService
						.get(ConfidentialityLevel.CONFIDENTIALITYLEVEL_EVERYONE));
			} catch (final ObjectNotFoundException e) {
				LOGGER.error(
						"Unable to find the default confidentiality level", e);
			}
		}

	}

	@Override
	public Task createCustomTaskForPerson(final String name,
			final String description,
			final Person student,
			final String sessionId)
			throws ObjectNotFoundException, ValidationException {
		final Task customTask = new Task();
		customTask.setDescription(description);
		customTask.setPerson(student);
		customTask.setName(name);
		setDefaultConfidentialityLevel(customTask, null);

		create(customTask);

		return customTask;
	}

	@Override
	public void sendNoticeToStudentOnCustomTask(final Task customTask)
			throws ObjectNotFoundException,
			SendFailedException, ValidationException {

		final SubjectAndBody subjAndBody = messageTemplateService
				.createStudentIntakeTaskMessage(customTask);

		messageService.createMessage(customTask.getPerson(), null, subjAndBody);
	}

	@Override
	public void sendTasksForPersonToEmail(@NotNull final List<Task> tasks,
			final List<Goal> goals, final Person student,
			final List<String> emailAddresses, final List<Person> recipients)
			throws ObjectNotFoundException {

		final List<TaskTO> taskTOs = TaskTO.toTOList(tasks);
		final List<GoalTO> goalTOs = GoalTO.toTOList(goals);

		final SubjectAndBody subjAndBody = messageTemplateService
				.createActionPlanMessage(student, taskTOs, goalTOs);

		if (emailAddresses != null) {
			for (final String address : emailAddresses) {
				messageService.createMessage(address, null, subjAndBody);
			}
		}

		if (recipients != null) {
			for (final Person recipient : recipients) {
				messageService.createMessage(
						recipient.getPrimaryEmailAddress(),
						null, subjAndBody);
			}
		}
	}

	@Override
	public List<Task> getTasksForPersonIfNoneSelected(
			final List<UUID> selectedIds, final Person person,
			final SspUser requester, final String sessionId,
			final SortingAndPaging sAndP) {
		/*
		 * If tasks are selected, get them, otherwise return the tasks for the
		 * person, (just for the session if it is the anon user).
		 */
		if (selectedIds != null && !selectedIds.isEmpty()) {
			return get(selectedIds, requester, sAndP);
		}

		if (person.getId() == SspUser.ANONYMOUS_PERSON_ID) {
			return getAllForSessionId(sessionId, sAndP);
		}

		return (List<Task>) getAllForPerson(person, requester, sAndP).getRows();
	}

	@Override
	public void sendAllTaskReminderNotifications() {

		if ( Thread.currentThread().isInterrupted() ) {
			LOGGER.info("Abandoning sendAllTaskReminderNotifications because of thread interruption");
			return;
		}

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		LOGGER.info("BEGIN : sendTaskReminderNotifications()");

		try {

			// Calculate reminder window start date
			final Calendar now = Calendar.getInstance();
			now.setTime(new Date());

			final Calendar startDateCalendar = Calendar.getInstance();
			final Calendar dueDateCalendar = Calendar.getInstance();

			// Send reminders for custom action plan tasks
			final List<Task> tasks = getAllWhichNeedRemindersSent(sAndP);

			for (final Task task : tasks) {

				if ( Thread.currentThread().isInterrupted() ) {
					LOGGER.info("Abandoning sendAllTaskReminderNotifications because of thread interruption");
					break;
				}

				// Calculate reminder window start date
				startDateCalendar.setTime(task.getDueDate());
				startDateCalendar.add(Calendar.HOUR,
						getNumberOfDaysPriorForTaskReminder() * 24 * -1);

				// Due date
				dueDateCalendar.setTime(task.getDueDate());

				if (now.after(startDateCalendar)
						&& (now.before(dueDateCalendar))) {

					SubjectAndBody subjAndBody;
					if (task.getType().equals(Task.CUSTOM_ACTION_PLAN_TASK)) {
						subjAndBody = messageTemplateService
								.createCustomActionPlanTaskMessage(task);
					} else {
						subjAndBody = messageTemplateService
								.createActionPlanStepMessage(task);
					}

					messageService.createMessage(task.getPerson(), null,
							subjAndBody);

					setReminderSentDateToToday(task);
				}
			}

		} catch (final Exception e) {
			LOGGER.error("ERROR : sendTaskReminderNotifications() : {}",
					e.getMessage(), e);
		}

		LOGGER.info("END : sendTaskReminderNotifications()");
	}

	@Override
	public Long getTaskCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {
		return dao.getTaskCountForCoach(coach, createDateFrom, createDateTo, studentTypeIds);
	}
	 
	
	@Override
	public Long getStudentTaskCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds) {
		return dao.getStudentTaskCountForCoach(coach, createDateFrom, createDateTo, studentTypeIds);
	}
	
	@Override
	public PagingWrapper<EntityStudentCountByCoachTO> getStudentTaskCountForCoaches(EntityCountByCoachSearchForm form) {
		return dao.getStudentTaskCountForCoaches(form);
	}
}
