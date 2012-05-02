package org.jasig.ssp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.TaskDao;
import org.jasig.ssp.dao.reference.ConfidentialityLevelDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Maps;

@Service
@Transactional
public class TaskServiceImpl extends AbstractAuditableCrudService<Task>
		implements TaskService {

	@Autowired
	private transient TaskDao dao;

	@Autowired
	private transient ConfidentialityLevelDao confidentialityLevelDao;

	@Autowired
	private transient MessageService messageService;

	@Value("#{configProperties.serverExternalPath}")
	private String serverExternalPath;

	private String studentUIPath = "MyGPS";

	@Value("#{configProperties.numberOfDaysPriorForTaskReminder}")
	private int numberOfDaysPriorForTaskReminder;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskServiceImpl.class);

	@Override
	protected TaskDao getDao() {
		return dao;
	}

	@Override
	public Task save(final Task obj) throws ObjectNotFoundException {
		final Task current = getDao().get(obj.getId());

		current.setChallenge(obj.getChallenge());
		current.setChallengeReferral(obj.getChallengeReferral());

		current.setCompletedDate(obj.getCompletedDate());
		current.setDescription(obj.getDescription());
		current.setDueDate(obj.getDueDate());
		current.setObjectStatus(obj.getObjectStatus());
		current.setPerson(obj.getPerson());
		current.setReminderSentDate(obj.getReminderSentDate());
		current.setSessionId(obj.getSessionId());

		if (obj.getConfidentialityLevel() == null) {
			current.setConfidentialityLevel(null);
		} else {
			current.setConfidentialityLevel(confidentialityLevelDao.load(obj
					.getConfidentialityLevel().getId()));
		}

		return getDao().save(current);
	}

	@Override
	public List<Task> getAllForPerson(final Person person,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public List<Task> getAllForPerson(final Person person,
			final boolean complete,
			final SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), complete, sAndP);
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
	public List<Task> getTasksInList(final List<UUID> taskIds,
			final SortingAndPaging sAndP) {
		return getDao().getTasksInList(taskIds, sAndP);
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
			final SortingAndPaging sAndP) {
		return dao.getAllForPersonIdAndChallengeReferralId(person.getId(),
				complete, challengeReferral.getId(), sAndP);
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
			final SortingAndPaging sAndP) {
		final Map<String, List<Task>> grouped = Maps.newTreeMap();
		final List<Task> tasksForPerson = dao
				.getAllForPersonId(person.getId(), sAndP);
		for (Task task : tasksForPerson) {
			final String group = task.getGroup();
			final List<Task> tasksForGroup;
			if (!grouped.keySet().contains(group)) {
				tasksForGroup = new ArrayList<Task>();
				grouped.put(group, tasksForGroup);
			} else {
				tasksForGroup = grouped.get(group);
			}
			tasksForGroup.add(task);
		}
		return grouped;
	}

	@Override
	public Task createForPersonWithChallengeReferral(final Challenge challenge,
			final ChallengeReferral challengeReferral, final Person person,
			final String sessionId) {

		// Create, fill, and persist a new Task
		final Task task = new Task();

		task.setChallenge(challenge);
		task.setChallengeReferral(challengeReferral);
		task.setPerson(person);
		task.setSessionId(sessionId);
		task.setDescription("");

		create(task);

		return task;
	}

	@Override
	public Task createCustomTaskForPerson(final String name,
			final String description,
			final Person student, final String sessionId) {
		final Task customTask = new Task();
		customTask.setDescription(description);
		customTask.setPerson(student);
		customTask.setName(name);

		create(customTask);

		return customTask;
	}

	@Override
	public void sendNoticeToStudentOnCustomTask(final Task customTask,
			final UUID messageTemplateId) throws Exception {

		if (!messageTemplateId
				.equals(MessageTemplate.TASK_AUTO_CREATED_EMAIL_ID)
				&& !messageTemplateId
						.equals(MessageTemplate.NEW_STUDENT_INTAKE_TASK_EMAIL_ID)) {
			// exit without sending message
			return;
		}

		// Template parameters
		final Map<String, Object> templateParameters = new HashMap<String, Object>();
		templateParameters
				.put("fullName", customTask.getPerson().getFullName());
		templateParameters.put("name", customTask.getName());

		// fix links in description
		final String linkedDescription = customTask.getDescription()
				.replaceAll(
						"href=\"/" + studentUIPath + "/",
						"href=\"" + serverExternalPath + "/" + studentUIPath
								+ "/");
		templateParameters.put("description", linkedDescription);

		final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		templateParameters.put("dueDate",
				format.format(customTask.getDueDate()));

		messageService.createMessage(customTask.getPerson(),
				messageTemplateId, templateParameters);
	}

	/**
	 * Send a list of the given tasks to each emailAddress and each recipient.
	 */
	@Override
	public void sendTasksForPersonToEmail(final List<Task> tasks,
			final Person student, final List<String> emailAddresses,
			final List<Person> recipients) throws Exception {

		if ((tasks == null) || (tasks.isEmpty())) {
			return;
		}

		final List<TaskTO> taskTOs = TaskTO.toTOList(tasks);

		final Map<String, Object> templateParameters = Maps.newHashMap();
		templateParameters.put("fullName", student.getFullName());
		templateParameters.put("taskTOs", taskTOs);

		if (emailAddresses != null) {
			for (String address : emailAddresses) {
				messageService.createMessage(address,
						MessageTemplate.ACTION_PLAN_EMAIL_ID,
						templateParameters);
			}
		}

		if (recipients != null) {
			for (Person recipient : recipients) {
				messageService.createMessage(
						recipient.getPrimaryEmailAddress(),
						MessageTemplate.ACTION_PLAN_EMAIL_ID,
						templateParameters);
			}
		}
	}

	/**
	 * If tasks are selected, get them, otherwise return the tasks for the
	 * person,
	 * (just for the session if it is the anon user).
	 */
	@Override
	public List<Task> getTasksForPersonIfNoneSelected(
			final List<UUID> selectedIds, final Person person,
			final String sessionId, final SortingAndPaging sAndP) {
		final List<Task> tasks;

		if ((selectedIds != null) && (selectedIds.isEmpty())) {
			tasks = getTasksInList(selectedIds, sAndP);
		} else {
			if (person.getId() == SspUser.ANONYMOUS_PERSON_ID) {
				tasks = getAllForSessionId(sessionId, sAndP);
			} else {
				tasks = getAllForPerson(person, sAndP);
			}
		}

		return tasks;
	}

	@Override
	public void sendAllTaskReminderNotifications() {

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

			for (Task task : tasks) {

				// Calculate reminder window start date
				startDateCalendar.setTime(task.getDueDate());
				startDateCalendar.add(Calendar.HOUR,
						numberOfDaysPriorForTaskReminder * 24 * -1);

				// Due date
				dueDateCalendar.setTime(task.getDueDate());

				if (now.after(startDateCalendar)
						&& (now.before(dueDateCalendar))) {

					UUID templateId;
					if (task.getType().equals(Task.CUSTOM_ACTION_PLAN_TASK)) {
						templateId = MessageTemplate.CUSTOM_ACTION_PLAN_TASK_ID;
					} else {
						templateId = MessageTemplate.ACTION_PLAN_STEP_ID;
					}

					messageService.createMessage(task.getPerson(), templateId,
							new HashMap<String, Object>());

					setReminderSentDateToToday(task);
				}
			}

		} catch (Exception e) {
			LOGGER.error("ERROR : sendTaskReminderNotifications() : {}",
					e.getMessage(), e);
		}

		LOGGER.info("END : sendTaskReminderNotifications()");
	}
}
