package org.studentsuccessplan.mygps.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.studentsuccessplan.ssp.dao.MessageDao;
import org.studentsuccessplan.ssp.dao.reference.ChallengeReferralDao;
import org.studentsuccessplan.ssp.dao.reference.MessageTemplateDao;
import org.studentsuccessplan.ssp.model.CustomTask;
import org.studentsuccessplan.ssp.model.Message;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.service.CustomTaskService;
import org.studentsuccessplan.ssp.service.MessageService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.TaskService;
import org.studentsuccessplan.ssp.util.VelocityTemplateHelper;

@Service
public class TaskManager {

	@Autowired
	private TaskService taskService;

	@Autowired
	private CustomTaskService customTaskService;

	@Autowired
	private ChallengeReferralDao challengeReferralDao;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private MessageService messageManager;

	@Autowired
	private MessageTemplateDao messageTemplateDao;

	@Autowired
	private PersonService personService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private VelocityTemplateHelper velocityTemplateHelper;

	@Value("#{configProperties.numberOfDaysPriorForTaskReminder}")
	private int numberOfDaysPriorForTaskReminder;

	@Value("#{configProperties.serverExternalPath}")
	private String serverExternalPath;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TaskManager.class);

	@Transactional(readOnly = false)
	public TaskTO createTaskForChallengeReferral(UUID challengeId,
			UUID challengeReferralId) {

		Task task = new Task();

		task.setChallenge(new Challenge(challengeId));
		task.setChallengeReferral(challengeReferralDao
				.get(challengeReferralId));
		task.setCreatedBy(securityService.currentlyLoggedInSspUser()
				.getPerson());
		task.setCreatedDate(new Date());
		task.setObjectStatus(ObjectStatus.ACTIVE);
		task.setPerson(securityService.currentlyLoggedInSspUser()
				.getPerson());
		task.setSessionId(securityService.getSessionId());

		taskService.create(task);

		return new TaskTO(task);
	}

	@Transactional(readOnly = false)
	public boolean deleteTask(String taskId) throws ObjectNotFoundException {

		// Determine what type of task this is from the id

		String[] arrTaskId = taskId.split(TaskTO.TASKTO_ID_PREFIX_DELIMITER);
		String taskType = arrTaskId[0];
		UUID id = UUID.fromString(arrTaskId[1]);

		if (taskType.equals(TaskTO.TASKTO_ID_PREFIX_ACTION_PLAN_TASK)) {

			taskService.delete(id);

		} else if (taskType
				.equals(TaskTO.TASKTO_ID_PREFIX_CUSTOM_ACTION_PLAN_TASK)) {

			customTaskService.delete(id);

		} else if (taskType
				.equals(TaskTO.TASKTO_ID_PREFIX_SSP_ACTION_PLAN_TASK)) {

			// Do nothing, not deletable as in SSP

		}

		return true;
	}

	@Transactional(readOnly = false)
	public boolean email(String emailAddress) {

		try {

			// Get all tasks to be placed in the action plan email.
			List<TaskTO> taskTOs = new ArrayList<TaskTO>();

			if (securityService.isAuthenticated()) {
				Person student = securityService.currentlyLoggedInSspUser()
						.getPerson();
				taskTOs.addAll(TaskTO.tasksToTaskTOs(
						taskService.getAllForPersonId(student, false)));
				taskTOs.addAll(TaskTO.customTasksToTaskTOs(
						customTaskService.getAllForPersonId(student, false)));
			} else {
				taskTOs.addAll(TaskTO
						.tasksToTaskTOs(
						taskService.getAllForSessionId(
								securityService.getSessionId(), true)));
				taskTOs.addAll(TaskTO
						.customTasksToTaskTOs(
						customTaskService.getAllForSessionId(
								securityService.getSessionId(), true)));
			}

			Collections.sort(taskTOs, new TaskTOComparator());

			// Get the message template
			MessageTemplate messageTemplate = messageTemplateDao
					.get(MessageTemplate.ACTION_PLAN_EMAIL_ID);

			// Template parameters
			HashMap<String, Object> templateParameters = new HashMap<String, Object>();

			Person student = securityService.currentlyLoggedInSspUser()
					.getPerson();

			templateParameters.put("fullName", student.getFullName());
			templateParameters.put("taskTOs", taskTOs);

			// Create message, add to queue for delivery
			messageManager.createMessage(emailAddress,
					velocityTemplateHelper.generateContentFromTemplate(
							messageTemplate.getSubject(), templateParameters),
					velocityTemplateHelper.generateContentFromTemplate(
							messageTemplate.getBody(), templateParameters));

			return true;
		} catch (Exception e) {
			LOGGER.error("ERROR : email() : {}", e);
			return false;
		}
	}

	public List<TaskTO> getAllTasks() {

		List<TaskTO> taskTOs = new ArrayList<TaskTO>();

		if (securityService.isAuthenticated()) {

			Person student = securityService.currentlyLoggedInSspUser()
					.getPerson();

			taskTOs.addAll(TaskTO
					.tasksToTaskTOs(
					taskService.getAllForPersonId(student)));
			taskTOs.addAll(TaskTO
					.customTasksToTaskTOs(
					customTaskService.getAllForPersonId(student)));

		} else {

			taskTOs.addAll(TaskTO
					.tasksToTaskTOs(
					taskService.getAllForSessionId(
							securityService.getSessionId())));
			taskTOs.addAll(TaskTO
					.customTasksToTaskTOs(
					customTaskService.getAllForSessionId(
							securityService.getSessionId())));
		}

		return taskTOs;
	}

	@Transactional(readOnly = false)
	public TaskTO createCustom(String name, String description) {

		CustomTask customTask = new CustomTask();

		Person student = securityService.currentlyLoggedInSspUser().getPerson();

		customTask.setCreatedDate(new Date());
		customTask.setCreatedBy(student);
		customTask.setDescription(description);
		customTask.setObjectStatus(ObjectStatus.ACTIVE);
		customTask.setPerson(student);
		customTask.setName(name);
		customTask.setSessionId(securityService.getSessionId());

		customTaskService.create(customTask);

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

		CustomTask customTask = new CustomTask();

		customTask.setCreatedDate(new Date());
		customTask.setCreatedBy(
				personService.get(Person.SYSTEM_ADMINISTRATOR_ID));
		customTask.setDescription(description);
		customTask.setObjectStatus(ObjectStatus.ACTIVE);
		customTask.setPerson(student);
		customTask.setName(name);
		customTask.setSessionId(securityService.getSessionId());
		customTask.setDueDate(dueDate);

		customTaskService.create(customTask);

		TaskTO taskTO = new TaskTO(customTask);

		if (!messageTemplateId
				.equals(MessageTemplate.TASK_AUTO_CREATED_EMAIL_ID)
				&& !messageTemplateId
						.equals(MessageTemplate.NEW_STUDENT_INTAKE_TASK_EMAIL_ID)) {
			// exit without sending message
			return taskTO;
		}

		// Get the message template
		MessageTemplate messageTemplate = messageTemplateDao
				.get(messageTemplateId);

		// Template parameters
		HashMap<String, Object> templateParameters = new HashMap<String, Object>();
		templateParameters.put("fullName", student.getFullName());
		templateParameters.put("name", name);

		// fix links in description
		String linkedDescription = description.replaceAll("href=\"/MyGPS/",
				"href=\"" + serverExternalPath + "/MyGPS/");
		templateParameters.put("description", linkedDescription);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		templateParameters.put("dueDate", format.format(dueDate));

		try {
			// Create message, add to queue for delivery
			messageManager.createMessage(student.getPrimaryEmailAddress(),
					velocityTemplateHelper.generateContentFromTemplate(
							messageTemplate.getSubject(), templateParameters),
					velocityTemplateHelper.generateContentFromTemplate(
							messageTemplate.getBody(), templateParameters));
		} catch (Exception e) {
			LOGGER.error("Unable to send email", e);
		}

		return taskTO;
	}

	@Transactional(readOnly = false)
	public TaskTO markTask(String taskId, Boolean complete)
			throws ObjectNotFoundException {

		TaskTO taskTO = null;

		String[] arrTaskId = taskId.split(TaskTO.TASKTO_ID_PREFIX_DELIMITER);
		String taskType = arrTaskId[0];
		UUID id = UUID.fromString(arrTaskId[1]);

		if (taskType.equals(TaskTO.TASKTO_ID_PREFIX_ACTION_PLAN_TASK)) {

			Task task = taskService.get(id);
			taskService.markTaskCompletion(task, complete);

			taskTO = new TaskTO(task);

		} else if (taskType
				.equals(TaskTO.TASKTO_ID_PREFIX_CUSTOM_ACTION_PLAN_TASK)) {

			CustomTask customTask = customTaskService.get(id);
			customTaskService.markTaskCompletion(customTask, complete);

			taskTO = new TaskTO(customTask);

		} else if (taskType
				.equals(TaskTO.TASKTO_ID_PREFIX_SSP_ACTION_PLAN_TASK)) {

			Task task = taskService.get(id);
			taskService.markTaskCompletion(task, complete);

			taskTO = new TaskTO(task);
		}

		return taskTO;
	}

	@Transactional(readOnly = false)
	public void sendTaskReminderNotifications() {

		LOGGER.info("BEGIN : sendTaskReminderNotifications()");

		try {

			MessageTemplate actionPlanStepMessageTemplate = messageTemplateDao
					.get(MessageTemplate.ACTION_PLAN_STEP_ID);
			MessageTemplate customTaskMessageTemplate = messageTemplateDao
					.get(MessageTemplate.CUSTOM_ACTION_PLAN_TASK_ID);

			// Calculate reminder window start date
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());

			Calendar startDateCalendar = Calendar.getInstance();
			Calendar dueDateCalendar = Calendar.getInstance();

			// Send reminders for custom action plan tasks
			List<CustomTask> customTasks = customTaskService
					.getAllWhichNeedRemindersSent();

			for (CustomTask customTask : customTasks) {

				// Calculate reminder window start date
				startDateCalendar.setTime(customTask.getDueDate());
				startDateCalendar.add(Calendar.HOUR,
						numberOfDaysPriorForTaskReminder * 24 * -1);

				// Due date
				dueDateCalendar.setTime(customTask.getDueDate());

				if (now.after(startDateCalendar)
						&& (now.before(dueDateCalendar))) {

					Message message = new Message();
					HashMap<String, Object> templateParameters = new HashMap<String, Object>();

					message.setBody(velocityTemplateHelper
							.generateContentFromTemplate(
									customTaskMessageTemplate.getBody(),
									templateParameters));
					message.setCreatedBy(personService
							.get(Person.SYSTEM_ADMINISTRATOR_ID));
					message.setCreatedDate(new Date());
					message.setRecipient(customTask.getPerson());
					message.setSender(personService
							.get(Person.SYSTEM_ADMINISTRATOR_ID));
					message.setSubject(velocityTemplateHelper
							.generateContentFromTemplate(
									customTaskMessageTemplate.getSubject(),
									templateParameters));

					messageDao.save(message);

					customTaskService.setReminderSentDateToToday(customTask);
				}
			}

			// Send reminders for action plan steps
			List<Task> actionPlanSteps = taskService
					.getAllWhichNeedRemindersSent();

			for (Task actionPlanStep : actionPlanSteps) {

				// Calculate reminder window start date
				startDateCalendar.setTime(actionPlanStep.getDueDate());
				startDateCalendar.add(Calendar.HOUR,
						numberOfDaysPriorForTaskReminder * 24 * -1);

				// Due date
				dueDateCalendar.setTime(actionPlanStep.getDueDate());

				if (now.after(startDateCalendar) &&
						(now.before(dueDateCalendar))) {

					Message message = new Message();
					HashMap<String, Object> templateParameters = new
							HashMap<String, Object>();

					message.setBody(velocityTemplateHelper.
							generateContentFromTemplate
							(actionPlanStepMessageTemplate.getBody(),
									templateParameters));
					message.setCreatedBy(personService
							.get(Person.SYSTEM_ADMINISTRATOR_ID));
					message.setCreatedDate(new Date());
					message.setRecipient(actionPlanStep.getPerson());
					message.setSender(personService
							.get(Person.SYSTEM_ADMINISTRATOR_ID));
					message.setSubject(velocityTemplateHelper.
							generateContentFromTemplate
							(actionPlanStepMessageTemplate.getSubject(),
									templateParameters));

					messageDao.save(message);

					taskService.setReminderSentDateToToday(actionPlanStep);
				}

			}
		} catch (Exception e) {
			LOGGER.error("ERROR : sendTaskReminderNotifications() : {}",
					e.getMessage(), e);
		}

		LOGGER.info("END : sendTaskReminderNotifications()");
	}

	public List<TaskReportTO> getActionPlanReportData() {

		List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();

		if (securityService.isAuthenticated()) {
			Person student = securityService.currentlyLoggedInSspUser()
					.getPerson();
			taskReportTOs.addAll(
					TaskReportTO.tasksToTaskReportTOs(
							taskService.getAllForPersonId(
									student, false)));
			taskReportTOs
					.addAll(
					TaskReportTO.customTasksToTaskReportTOs(
							customTaskService.getAllForPersonId(
									student, false)));

		} else {

			taskReportTOs.addAll(
					TaskReportTO.tasksToTaskReportTOs(
							taskService.getAllForSessionId(
									securityService.getSessionId(), false)));
			taskReportTOs
					.addAll(
					TaskReportTO.customTasksToTaskReportTOs(
							customTaskService.getAllForSessionId(
									securityService.getSessionId(), false)));

		}

		Collections.sort(taskReportTOs);

		return taskReportTOs;

	}

	public String getServerExternalPath() {
		return serverExternalPath;
	}

	public void setServerExternalPath(String serverExternalPath) {
		this.serverExternalPath = serverExternalPath;
	}
}
