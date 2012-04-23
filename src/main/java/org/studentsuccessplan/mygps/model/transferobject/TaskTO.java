package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;

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

	private ConfidentialityLevel confidentialityLevel = null;

	/**
	 * Empty constructor
	 */
	public TaskTO() {
	}

	public TaskTO(final Task task) {
		challengeId = task.getChallenge().getId();
		challengeReferralId = task.getChallengeReferral().getId();
		completed = (task.getCompletedDate() == null) ? false : true;
		deletable = true;
		dueDate = task.getDueDate();

		if (task.getChallengeReferral() == null) {
			name = task.getName();
			details = task.getDescription();
			description = task.getDescription();
		} else {
			name = task.getChallengeReferral().getName();
			details = task.getChallengeReferral().getPublicDescription();
			description = task.getChallengeReferral().getPublicDescription();
		}

		if (description != null) {
			description = description.replaceAll("\\<.*?>", "");
		}

		if (task.getType().equals(Task.ACTION_PLAN_TASK)) {
			id = TASKTO_ID_PREFIX_ACTION_PLAN_TASK + TASKTO_ID_PREFIX_DELIMITER
					+ task.getId();
		} else if (task.getType().equals(Task.SSP_ACTION_PLAN_TASK)) {
			id = TASKTO_ID_PREFIX_SSP_ACTION_PLAN_TASK
					+ TASKTO_ID_PREFIX_DELIMITER + task.getId();
		} else if (task.getType().equals(Task.CUSTOM_ACTION_PLAN_TASK)) {
			id = TASKTO_ID_PREFIX_CUSTOM_ACTION_PLAN_TASK
					+ TASKTO_ID_PREFIX_DELIMITER + task.getId();
		} else {
			throw new IllegalArgumentException("Invalid Task type");
		}

		type = task.getType();
		confidentialityLevel = task.getConfidentialityLevel();
	}

	public static List<TaskTO> tasksToTaskTOs(final List<Task> tasks) {
		final List<TaskTO> taskTOs = new ArrayList<TaskTO>();

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

	public void setId(final String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(final String details) {
		this.details = details;
	}

	public Date getDueDate() {
		return dueDate == null ? null : new Date(dueDate.getTime());
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(final boolean completed) {
		this.completed = completed;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(final boolean deletable) {
		this.deletable = deletable;
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(final UUID challengeId) {
		this.challengeId = challengeId;
	}

	public UUID getChallengeReferralId() {
		return challengeReferralId;
	}

	public void setChallengeReferralId(final UUID challengeReferralId) {
		this.challengeReferralId = challengeReferralId;
	}

	/**
	 * @return the confidentialityLevel
	 */
	public ConfidentialityLevel getConfidentialityLevel() {
		return confidentialityLevel;
	}

	/**
	 * @param confidentialityLevel
	 *            the confidentialityLevel to set
	 */
	public void setConfidentialityLevel(
			final ConfidentialityLevel confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}
}