package edu.sinclair.mygps.business;

import java.util.ArrayList;
import java.util.List;

import edu.sinclair.mygps.model.transferobject.TaskReportTO;
import edu.sinclair.mygps.model.transferobject.TaskTO;
import edu.sinclair.ssp.model.AbstractTask;
import edu.sinclair.ssp.model.CustomTask;
import edu.sinclair.ssp.model.Task;

public class TaskFactory {

	public static List<TaskTO> tasksToTaskTOs(List<Task> tasks) {

		List<TaskTO> taskTOs = new ArrayList<TaskTO>();

		for (Task task : tasks) {
			taskTOs.add(taskToTaskTO(task));
		}

		return taskTOs;

	}

	public static TaskTO taskToTaskTO(Task task) {

		TaskTO taskTO = new TaskTO();

		taskTO.setChallengeId(task.getChallenge().getId());
		taskTO.setChallengeReferralId(task.getChallengeReferral()
				.getId());
		taskTO.setCompleted((task.getCompletedDate() != null) ? true : false);
		taskTO.setDeletable(true);

		if (task.getChallengeReferral().getPublicDescription() != null) {
			taskTO.setDescription(task.getChallengeReferral()
					.getPublicDescription().replaceAll("\\<.*?>", ""));
		}

		taskTO.setDetails(task.getChallengeReferral().getPublicDescription());
		taskTO.setDueDate(null);
		// taskTO.setId(Constants.TASKTO_ID_PREFIX_ACTION_PLAN_TASK +
		// Constants.TASKTO_ID_PREFIX_DELIMITER + task.getId());
		taskTO.setId(task.getId());
		taskTO.setName(task.getChallengeReferral().getName());
		taskTO.setType(AbstractTask.ACTION_PLAN_TASK);

		return taskTO;

	}

	public static List<TaskTO> customTasksToTaskTOs(List<CustomTask> customTasks) {

		List<TaskTO> taskTOs = new ArrayList<TaskTO>();

		for (CustomTask customTask : customTasks) {
			taskTOs.add(customTaskToTaskTO(customTask));
		}

		return taskTOs;

	}

	public static TaskTO customTaskToTaskTO(CustomTask customTask) {

		TaskTO taskTO = new TaskTO();

		taskTO.setChallengeId(null);
		taskTO.setChallengeReferralId(null);
		taskTO.setCompleted((customTask.getCompletedDate() != null) ? true
				: false);
		taskTO.setDeletable(true);

		if (customTask.getDescription() != null) {
			taskTO.setDescription(customTask.getDescription().replaceAll(
					"\\<.*?>", ""));
		}

		taskTO.setDetails(customTask.getDescription());
		taskTO.setDueDate(customTask.getDueDate());
		// taskTO.setId(Constants.TASKTO_ID_PREFIX_CUSTOM_ACTION_PLAN_TASK +
		// Constants.TASKTO_ID_PREFIX_DELIMITER + customTask.getId());
		taskTO.setId(customTask.getId());
		taskTO.setName(customTask.getName());
		taskTO.setType(AbstractTask.CUSTOM_ACTION_PLAN_TASK);

		return taskTO;

	}

	public static List<TaskTO> objectsToTaskTOs(List<Object[]> objects) {

		List<TaskTO> taskTOs = new ArrayList<TaskTO>();

		for (Object[] object : objects) {
			taskTOs.add(objectToTaskTO(object));
		}

		return taskTOs;

	}

	public static List<TaskReportTO> objectsToTaskReportTOs(
			List<Object[]> objects) {

		List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();

		for (Object[] object : objects) {
			taskReportTOs.add(objectToTaskReportTO(object));
		}

		return taskReportTOs;

	}

	public static TaskTO objectToTaskTO(Object[] object) {
		return actionPlanStepToTaskTO(objectToActionPlanStep(object));
	}

	public static TaskReportTO objectToTaskReportTO(Object[] object) {
		return actionPlanStepToTaskReportTO(objectToActionPlanStep(object));
	}

	public static List<Task> objectsToActionPlanSteps(List<Object[]> objects) {

		List<Task> actionPlanSteps = new ArrayList<Task>();

		for (Object[] object : objects) {
			actionPlanSteps.add(objectToActionPlanStep(object));
		}

		return actionPlanSteps;
	}

	public static Task objectToActionPlanStep(Object[] object) {
		/*
		 * :TODO fix action plan step
		 * UUID id = UUID.fromString(String.valueOf(object[0]));
		 * String challengeName = String.valueOf(object[1]);
		 * UUID challengeReferralId =
		 * UUID.fromString(String.valueOf(object[2]));
		 * String challengeReferralName = String.valueOf(object[3]);
		 * String actionDescription = ((object[4] != null) ?
		 * String.valueOf(object[4]) : null);
		 * Date dueDate = ((object[5] != null) ? (Date)object[5] : null);
		 * Date completedDate = ((object[6] != null) ? (Date)object[6] : null);
		 * UUID personId = UUID.fromString(String.valueOf(object[7]));
		 * 
		 * ActionPlanStep actionPlanStep = new ActionPlanStep();
		 * 
		 * actionPlanStep.setActionDescription(actionDescription);
		 * actionPlanStep.setChallengeName(challengeName);
		 * actionPlanStep.setChallengeReferralId(challengeReferralId);
		 * actionPlanStep.setChallengeReferralName(challengeReferralName);
		 * actionPlanStep.setCompletedDate(completedDate);
		 * actionPlanStep.setDueDate(dueDate);
		 * actionPlanStep.setId(id);
		 * actionPlanStep.setPerson(new Person(personId));
		 * 
		 * return actionPlanStep;
		 */
		return null;
	}

	public static TaskTO actionPlanStepToTaskTO(Task actionPlanStep) {
		/*
		 * :TODO fix action plan step
		 * TaskTO taskTO = new TaskTO();
		 * 
		 * taskTO.setChallengeId(null);
		 * taskTO.setChallengeReferralId(null);
		 * taskTO.setCompleted((actionPlanStep.getCompletedDate() != null) ?
		 * true : false);
		 * taskTO.setDeletable(false);
		 * taskTO.setDescription(actionPlanStep.getActionDescription());
		 * taskTO.setDetails(actionPlanStep.getActionDescription());
		 * taskTO.setDueDate(actionPlanStep.getDueDate());
		 * // taskTO.setId(Constants.TASKTO_ID_PREFIX_SSP_ACTION_PLAN_TASK +
		 * // Constants.TASKTO_ID_PREFIX_DELIMITER + actionPlanStep.getId());
		 * taskTO.setId(actionPlanStep.getId());
		 * taskTO.setName(actionPlanStep.getChallengeReferralName());
		 * taskTO.setType(AbstractTask.SSP_ACTION_PLAN_TASK);
		 * 
		 * return taskTO;
		 */
		return null;
	}

	public static TaskReportTO actionPlanStepToTaskReportTO(Task actionPlanStep) {
		/*
		 * :TODO fix action plan step
		 * TaskReportTO taskReportTO = new TaskReportTO();
		 * 
		 * taskReportTO.setChallengeName(actionPlanStep.getChallengeName());
		 * taskReportTO.setChallengeReferralName(actionPlanStep.
		 * getChallengeReferralName());
		 * taskReportTO.setCreatedBy(actionPlanStep.getCreatedBy().getId());
		 * taskReportTO.setDescription(actionPlanStep.getActionDescription());
		 * taskReportTO.setDueDate(actionPlanStep.getDueDate());
		 * taskReportTO.setType(AbstractTask.SSP_ACTION_PLAN_TASK);
		 * 
		 * return taskReportTO;
		 */
		return null;
	}

	public static List<TaskReportTO> tasksToTaskReportTOs(List<Task> tasks) {

		List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();

		for (Task task : tasks) {
			taskReportTOs.add(taskToTaskReportTO(task));
		}

		return taskReportTOs;

	}

	public static TaskReportTO taskToTaskReportTO(Task task) {

		TaskReportTO taskReportTO = new TaskReportTO();

		taskReportTO.setChallengeName(task.getChallenge().getName());
		taskReportTO.setChallengeReferralName(task.getChallengeReferral()
				.getName());
		taskReportTO.setCreatedBy(task.getCreatedBy().getId());
		taskReportTO.setDescription(task.getChallengeReferral()
				.getPublicDescription());
		taskReportTO.setDueDate(null);
		taskReportTO.setType(AbstractTask.ACTION_PLAN_TASK);

		return taskReportTO;

	}

	public static List<TaskReportTO> customTasksToTaskReportTOs(
			List<CustomTask> customTasks) {

		List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();

		for (CustomTask customTask : customTasks) {
			taskReportTOs.add(customTaskToTaskReportTO(customTask));
		}

		return taskReportTOs;

	}

	public static TaskReportTO customTaskToTaskReportTO(CustomTask customTask) {

		TaskReportTO taskReportTO = new TaskReportTO();

		taskReportTO.setChallengeName("Custom Action Plan Task");
		taskReportTO.setChallengeReferralName(customTask.getName());
		taskReportTO.setCreatedBy(customTask.getCreatedBy().getId());
		taskReportTO.setDescription(customTask.getDescription());
		taskReportTO.setDueDate(customTask.getDueDate());
		taskReportTO.setType(AbstractTask.CUSTOM_ACTION_PLAN_TASK);

		return taskReportTO;

	}

}
