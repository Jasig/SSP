package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.AbstractTask;
import org.studentsuccessplan.ssp.model.CustomTask;
import org.studentsuccessplan.ssp.model.Task;

public class TaskReportTO implements Comparable<TaskReportTO>, Serializable {

	private static final long serialVersionUID = 914070219523802658L;

	private String type;
	private String challengeName;
	private String challengeReferralName;
	private String description;
	private UUID createdBy;
	private Date dueDate;

	public TaskReportTO() {
	}

	public TaskReportTO(Task task) {
		this.setChallengeName(task.getChallenge().getName());
		this.setChallengeReferralName(task.getChallengeReferral().getName());
		this.setCreatedBy(task.getCreatedBy().getId());
		this.setDescription(task.getChallengeReferral().getPublicDescription());
		this.setDueDate(null);

		// :TODO how do determine between ACTION_PLAN_TASK and
		// SSP_ACTION_PLAN_TASK
		this.setType(AbstractTask.ACTION_PLAN_TASK);
		// this.setType(AbstractTask.SSP_ACTION_PLAN_TASK);
	}

	public TaskReportTO(CustomTask customTask) {
		this.setChallengeName("Custom Action Plan Task");
		this.setChallengeReferralName(customTask.getName());
		this.setCreatedBy(customTask.getCreatedBy().getId());
		this.setDescription(customTask.getDescription());
		this.setDueDate(customTask.getDueDate());
		this.setType(AbstractTask.CUSTOM_ACTION_PLAN_TASK);
	}

	public static List<TaskReportTO> tasksToTaskReportTOs(List<Task> tasks) {
		List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();
		for (Task task : tasks) {
			taskReportTOs.add(new TaskReportTO(task));
		}
		return taskReportTOs;
	}

	public static List<TaskReportTO> customTasksToTaskReportTOs(
			List<CustomTask> customTasks) {
		List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();
		for (CustomTask customTask : customTasks) {
			taskReportTOs.add(new TaskReportTO(customTask));
		}
		return taskReportTOs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChallengeName() {
		return challengeName;
	}

	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}

	public String getChallengeReferralName() {
		return challengeReferralName;
	}

	public void setChallengeReferralName(String challengeReferralName) {
		this.challengeReferralName = challengeReferralName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate == null ? null : new Date(dueDate.getTime());
	}

	public UUID getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
	}

	@Override
	public int compareTo(TaskReportTO taskReportTO) {
		return challengeName.compareTo(taskReportTO.getChallengeName());
	}

}
