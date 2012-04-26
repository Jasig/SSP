package org.studentsuccessplan.mygps.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import org.studentsuccessplan.mygps.model.transferobject.TaskReportTO;
import org.studentsuccessplan.mygps.model.transferobject.TaskTO;
import org.studentsuccessplan.mygps.model.transferobject.TaskTOComparator;
import org.studentsuccessplan.ssp.dao.reference.ChallengeReferralDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.service.MessageService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.TaskService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Maps;

@Service
@Transactional
public class TaskManager {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ChallengeReferralDao challengeReferralDao;

	@Autowired
	private MessageService messageService;

	@Autowired
	private PersonService personService;

	@Autowired
	private SecurityService securityService;

	@Value("#{configProperties.numberOfDaysPriorForTaskReminder}")
	private int numberOfDaysPriorForTaskReminder;

	@Value("#{configProperties.serverExternalPath}")
	private String serverExternalPath;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskManager.class);

	@Transactional(readOnly = false)
	public TaskTO createTaskForChallengeReferral(UUID challengeId,
			UUID challengeReferralId) {

		// Create, fill, and persist a new Task
		Task task = new Task();

		task.setChallenge(new Challenge(challengeId));
		task.setChallengeReferral(challengeReferralDao.get(challengeReferralId));
		task.setPerson(securityService.currentUser().getPerson());
		task.setSessionId(securityService.getSessionId());
		task.setDescription("");

		taskService.create(task);

		return new TaskTO(task);
	}

	@Transactional(readOnly = false)
	public boolean deleteTask(String taskId) throws ObjectNotFoundException {
		// Determine what type of task this is from the id
		String[] arrTaskId = taskId.split(TaskTO.TASKTO_ID_PREFIX_DELIMITER);
		UUID id = UUID.fromString(arrTaskId[1]);
		taskService.delete(id);
		return true;
	}

	@Transactional(readOnly = false)
	public boolean email(final String emailAddress) {

		// Get all tasks to be placed in the action plan email.
		final List<TaskTO> taskTOs = new ArrayList<TaskTO>();

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		if (securityService.isAuthenticated()) {
			Person student = securityService.currentUser().getPerson();
			taskTOs.addAll(TaskTO.tasksToTaskTOs(taskService
					.getAllForPersonId(student, false, sAndP)));
		} else {
			taskTOs.addAll(TaskTO.tasksToTaskTOs(taskService
					.getAllForSessionId(securityService.getSessionId(),
							true, sAndP)));
		}

		Collections.sort(taskTOs, new TaskTOComparator());

		Map<String, Object> templateParameters = Maps.newHashMap();
		Person student = securityService.currentUser().getPerson();
		templateParameters.put("fullName", student.getFullName());
		templateParameters.put("taskTOs", taskTOs);

		try {
			messageService.createMessage(emailAddress,
					MessageTemplate.ACTION_PLAN_EMAIL_ID, templateParameters);
			return true;
		} catch (Exception e) {
			LOGGER.error("ERROR : email() : {}", e);
			return false;
		}

	}

	public List<TaskTO> getAllTasks() {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<TaskTO> taskTOs = new ArrayList<TaskTO>();

		if (securityService.isAuthenticated()) {
			Person student = securityService.currentUser().getPerson();

			taskTOs.addAll(TaskTO.tasksToTaskTOs(taskService
					.getAllForPersonId(student, sAndP)));
		} else {
			taskTOs.addAll(TaskTO.tasksToTaskTOs(taskService
					.getAllForSessionId(securityService.getSessionId(), sAndP)));
		}

		return taskTOs;
	}

	@Transactional(readOnly = false)
	public TaskTO createCustom(String name, String description) {

		Task customTask = new Task();

		Person student = securityService.currentUser().getPerson();

		customTask.setCreatedDate(new Date());
		customTask.setCreatedBy(student);
		customTask.setDescription(description);
		customTask.setObjectStatus(ObjectStatus.ACTIVE);
		customTask.setPerson(student);
		customTask.setName(name);
		customTask.setSessionId(securityService.getSessionId());

		taskService.create(customTask);

		return new TaskTO(customTask);
	}

	@Transactional(readOnly = false)
	public TaskTO createTaskForStudent(String name, String description,
			String studentId, Date dueDate, UUID messageTemplateId)
			throws Exception {

		Person student = personService.personFromUserId(studentId);

		if (student == null) {
			LOGGER.error("Unable to acquire person for supplied student id "
					+ studentId);
			throw new Exception(
					"Unable to acquire person for supplied student id "
							+ studentId);
		}

		Task customTask = new Task();

		customTask.setCreatedDate(new Date());
		customTask.setCreatedBy(personService
				.get(Person.SYSTEM_ADMINISTRATOR_ID));
		customTask.setDescription(description);
		customTask.setObjectStatus(ObjectStatus.ACTIVE);
		customTask.setPerson(student);
		customTask.setName(name);
		customTask.setSessionId(securityService.getSessionId());
		customTask.setDueDate(dueDate);

		taskService.create(customTask);

		TaskTO taskTO = new TaskTO(customTask);

		if (!messageTemplateId
				.equals(MessageTemplate.TASK_AUTO_CREATED_EMAIL_ID)
				&& !messageTemplateId
						.equals(MessageTemplate.NEW_STUDENT_INTAKE_TASK_EMAIL_ID)) {
			// exit without sending message
			return taskTO;
		}

		// Template parameters
		Map<String, Object> templateParameters = new HashMap<String, Object>();
		templateParameters.put("fullName", student.getFullName());
		templateParameters.put("name", name);

		// fix links in description
		String linkedDescription = description.replaceAll("href=\"/MyGPS/",
				"href=\"" + serverExternalPath + "/MyGPS/");
		templateParameters.put("description", linkedDescription);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		templateParameters.put("dueDate", format.format(dueDate));

		try {
			messageService.createMessage(
					student, messageTemplateId,
					templateParameters);
		} catch (Exception e) {
			LOGGER.error("ERROR : email() : {}", e);
		}

		return taskTO;
	}

	@Transactional(readOnly = false)
	public TaskTO markTask(String taskId, Boolean complete)
			throws ObjectNotFoundException {

		TaskTO taskTO = null;

		String[] arrTaskId = taskId.split(TaskTO.TASKTO_ID_PREFIX_DELIMITER);
		UUID id = UUID.fromString(arrTaskId[1]);

		Task task = taskService.get(id);
		taskService.markTaskCompletion(task, complete);
		taskTO = new TaskTO(task);

		return taskTO;
	}

	@Transactional(readOnly = false)
	public void sendTaskReminderNotifications() {

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
			List<Task> tasks = taskService
					.getAllWhichNeedRemindersSent(sAndP);

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

					taskService.setReminderSentDateToToday(task);
				}
			}

		} catch (Exception e) {
			LOGGER.error("ERROR : sendTaskReminderNotifications() : {}",
					e.getMessage(), e);
		}

		LOGGER.info("END : sendTaskReminderNotifications()");
	}

	public List<TaskReportTO> getActionPlanReportData() {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();

		if (securityService.isAuthenticated()) {
			Person student = securityService.currentUser().getPerson();
			taskReportTOs.addAll(TaskReportTO.tasksToTaskReportTOs(taskService
					.getAllForPersonId(student, false, sAndP)));

		} else {
			taskReportTOs
					.addAll(TaskReportTO.tasksToTaskReportTOs(taskService
							.getAllForSessionId(securityService.getSessionId(),
									false, sAndP)));
		}

		Collections.sort(taskReportTOs);

		return taskReportTOs;

	}
}
