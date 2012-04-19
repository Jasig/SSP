package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

	/**
	 * Empty constructor
	 */
	public TaskReportTO() {
	}

	public TaskReportTO(Task task) {
		setChallengeName(task.getChallenge().getName());
		setChallengeReferralName(task.getChallengeReferral().getName());
		setCreatedBy(task.getCreatedBy().getId());
		setDescription(task.getChallengeReferral().getPublicDescription());
		setDueDate(null);
		setType(task.getType());
	}

	public TaskReportTO(CustomTask customTask) {
		setChallengeName("Custom Action Plan Task");
		setChallengeReferralName(customTask.getName());
		setCreatedBy(customTask.getCreatedBy().getId());
		setDescription(customTask.getDescription());
		setDueDate(customTask.getDueDate());
		setType(customTask.getType());
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
