package org.studentsuccessplan.ssp.service.impl;

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
import org.studentsuccessplan.ssp.dao.TaskDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.service.AbstractAuditableCrudService;
import org.studentsuccessplan.ssp.service.MessageService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.TaskService;
import org.studentsuccessplan.ssp.transferobject.TaskTO;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Maps;

@Service
public class TaskServiceImpl
		extends AbstractAuditableCrudService<Task>
		implements TaskService {

	@Autowired
	private TaskDao dao;

	@Autowired
	private MessageService messageService;

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
	public Task save(Task obj) throws ObjectNotFoundException {
		Task current = getDao().get(obj.getId());

		current.setChallenge(obj.getChallenge());
		current.setChallengeReferral(obj.getChallengeReferral());

		current.setCompletedDate(obj.getCompletedDate());
		current.setDescription(obj.getDescription());
		current.setDueDate(obj.getDueDate());
		current.setObjectStatus(obj.getObjectStatus());
		current.setPerson(obj.getPerson());
		current.setReminderSentDate(obj.getReminderSentDate());
		current.setSessionId(obj.getSessionId());

		return getDao().save(current);
	}

	@Override
	public List<Task> getAllForPerson(Person person, SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public List<Task> getAllForPerson(Person person, boolean complete,
			SortingAndPaging sAndP) {
		return getDao().getAllForPersonId(person.getId(), complete, sAndP);
	}

	@Override
	public List<Task> getAllForSessionId(String sessionId,
			SortingAndPaging sAndP) {
		return getDao().getAllForSessionId(sessionId, sAndP);
	}

	@Override
	public List<Task> getAllForSessionId(String sessionId, boolean complete,
			SortingAndPaging sAndP) {
		return getDao().getAllForSessionId(sessionId, complete, sAndP);
	}

	@Override
	public List<Task> getAllWhichNeedRemindersSent(SortingAndPaging sAndP) {
		return getDao().getAllWhichNeedRemindersSent(sAndP);
	}

	@Override
	public List<Task> getTasksInList(List<UUID> taskIds, SortingAndPaging sAndP) {
		return getDao().getTasksInList(taskIds, sAndP);
	}

	@Override
	public void markTaskComplete(Task task) {
		task.setCompletedDate(new Date());
		getDao().save(task);
	}

	@Override
	public void markTaskIncomplete(Task task) {
		task.setCompletedDate(null);
		getDao().save(task);
	}

	@Override
	public void markTaskCompletion(Task task, boolean complete) {
		if (complete) {
			markTaskComplete(task);
		} else {
			markTaskIncomplete(task);
		}
	}

	@Override
	public void setReminderSentDateToToday(Task task) {
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
			final String sessionId,
			final boolean complete,
			final ChallengeReferral challengeReferral,
			final SortingAndPaging sAndP) {
		return dao.getAllForSessionIdAndChallengeReferralId(sessionId,
				complete, challengeReferral.getId(), sAndP);
	}

	@Override
	public Map<String, List<Task>> getAllGroupedByTaskGroup(Person person,
			SortingAndPaging sAndP) {
		Map<String, List<Task>> grouped = Maps.newTreeMap();
		List<Task> tasksForPerson = dao
				.getAllForPersonId(person.getId(), sAndP);
		for (Task task : tasksForPerson) {
			String group = task.getGroup();
			List<Task> tasksForGroup;
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
	public Task createForPersonWithChallengeReferral(Challenge challenge,
			ChallengeReferral challengeReferral, Person person, String sessionId) {

		// Create, fill, and persist a new Task
		Task task = new Task();

		task.setChallenge(challenge);
		task.setChallengeReferral(challengeReferral);
		task.setPerson(person);
		task.setSessionId(sessionId);
		task.setDescription("");

		create(task);

		return task;
	}

	@Override
	public Task createCustomTaskForPerson(String name, String description,
			Person student, String sessionId) {
		Task customTask = new Task();
		customTask.setDescription(description);
		customTask.setPerson(student);
		customTask.setName(name);

		create(customTask);

		return customTask;
	}

	@Override
	public void sendNoticeToStudentOnCustomTask(Task customTask,
			UUID messageTemplateId) throws Exception {

		if (!messageTemplateId
				.equals(MessageTemplate.TASK_AUTO_CREATED_EMAIL_ID)
				&& !messageTemplateId
						.equals(MessageTemplate.NEW_STUDENT_INTAKE_TASK_EMAIL_ID)) {
			// exit without sending message
			return;
		}

		// Template parameters
		Map<String, Object> templateParameters = new HashMap<String, Object>();
		templateParameters
				.put("fullName", customTask.getPerson().getFullName());
		templateParameters.put("name", customTask.getName());

		// fix links in description
		String linkedDescription = customTask.getDescription().replaceAll(
				"href=\"/" + studentUIPath + "/",
				"href=\"" + serverExternalPath + "/" + studentUIPath + "/");
		templateParameters.put("description", linkedDescription);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
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

		if ((tasks == null) || (tasks.size() == 0)) {
			return;
		}

		final List<TaskTO> taskTOs = TaskTO.tasksToTaskTOs(tasks);

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

	@Override
	public void sendAllTaskReminderNotifications() {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		LOGGER.info("BEGIN : sendTaskReminderNotifications()");

		try {

			// Calculate reminder window start date
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());

			Calendar startDateCalendar = Calendar.getInstance();
			Calendar dueDateCalendar = Calendar.getInstance();

			// Send reminders for custom action plan tasks
			List<Task> tasks = getAllWhichNeedRemindersSent(sAndP);

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
