package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.transferobject.reference.TaskGroupTO;

public class TaskTO implements Serializable {

	private static final long serialVersionUID = 5796302591576434925L;

	public static final String TASKTO_ID_PREFIX_DELIMITER = ":";
	public static final String TASKTO_ID_PREFIX_ACTION_PLAN_TASK = Task.ACTION_PLAN_TASK;
	public static final String TASKTO_ID_PREFIX_CUSTOM_ACTION_PLAN_TASK = Task.CUSTOM_ACTION_PLAN_TASK;
	public static final String TASKTO_ID_PREFIX_SSP_ACTION_PLAN_TASK = Task.SSP_ACTION_PLAN_TASK;

	private String id;
	private String type;
	private String name;
	private String description;
	private String details;
	private Date dueDate;
	private boolean completed;
	private boolean deletable;
	private UUID challengeId;
	private UUID challengeReferralId;

	private List<TaskGroupTO> groups;

	/**
	 * Empty constructor
	 */
	public TaskTO() {
	}

	public TaskTO(Task task) {
		setChallengeId(task.getChallenge().getId());
		setChallengeReferralId(task.getChallengeReferral().getId());
		setCompleted((task.getCompletedDate() != null) ? true : false);
		setDeletable(true);
		setDueDate(task.getDueDate());

		if (task.getChallengeReferral() != null) {
			setName(task.getChallengeReferral().getName());
			setDetails(task.getChallengeReferral().getPublicDescription());
			setDescription(task.getChallengeReferral().getPublicDescription());
		} else {
			setName(task.getName());
			setDetails(task.getDescription());
			setDescription(task.getDescription());
		}

		if (description != null) {
			description = description.replaceAll("\\<.*?>", "");
		}

		if (task.getType().equals(Task.ACTION_PLAN_TASK)) {
			setId(TASKTO_ID_PREFIX_ACTION_PLAN_TASK
					+ TASKTO_ID_PREFIX_DELIMITER
					+ task.getId());
		} else if (task.getType().equals(Task.SSP_ACTION_PLAN_TASK)) {
			setId(TASKTO_ID_PREFIX_SSP_ACTION_PLAN_TASK
					+ TASKTO_ID_PREFIX_DELIMITER + task.getId());
		} else if (task.getType().equals(Task.CUSTOM_ACTION_PLAN_TASK)) {
			setId(TASKTO_ID_PREFIX_CUSTOM_ACTION_PLAN_TASK
					+ TASKTO_ID_PREFIX_DELIMITER + task.getId());
		} else {
			throw new IllegalArgumentException("Invalid Task type");
		}

		setType(task.getType());
	}

	public static List<TaskTO> tasksToTaskTOs(List<Task> tasks) {
		List<TaskTO> taskTOs = new ArrayList<TaskTO>();

		if ((tasks != null) && !tasks.isEmpty()) {
			for (Task task : tasks) {
				taskTOs.add(new TaskTO(task));
			}
		}

		return taskTOs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getDueDate() {
		return dueDate == null ? null : new Date(dueDate.getTime());
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(UUID challengeId) {
		this.challengeId = challengeId;
	}

	public UUID getChallengeReferralId() {
		return challengeReferralId;
	}

	public void setChallengeReferralId(UUID challengeReferralId) {
		this.challengeReferralId = challengeReferralId;
	}

	public List<TaskGroupTO> getGroups() {
		return groups;
	}

	public void setGroups(List<TaskGroupTO> groups) {
		this.groups = groups;
	}
}
