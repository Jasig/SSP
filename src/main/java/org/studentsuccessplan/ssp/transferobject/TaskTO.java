package org.studentsuccessplan.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Task;

public class TaskTO
		extends AuditableTO<Task>
		implements TransferObject<Task>, Serializable, NamedTO {

	private static final long serialVersionUID = 5796302591576434925L;

	private String type;
	private String name, description;
	private boolean completed, deletable;
	private Date dueDate, completedDate, reminderSentDate;
	private UUID personId, challengeId, challengeReferralId,
			confidentialityLevel;

	/**
	 * Empty constructor
	 */
	public TaskTO() {
		super();
	}

	public TaskTO(final Task task) {
		super();
		from(task);
	}

	@Override
	public void from(final Task task) {
		super.from(task);

		setType(task.getType());
		setCompleted((task.getCompletedDate() != null) ? true : false);
		setDeletable(task.isDeletable());
		setDueDate(task.getDueDate());

		if (task.getChallenge() != null) {
			setChallengeId(task.getChallenge().getId());
		}

		if (task.getChallengeReferral() != null) {
			setChallengeReferralId(task.getChallengeReferral().getId());
			setName(task.getChallengeReferral().getName());
			setDescription(task.getChallengeReferral().getPublicDescription());
		} else {
			setName(task.getName());
			setDescription(task.getDescription());
		}

		if (task.getConfidentialityLevel() != null) {
			confidentialityLevel = task.getConfidentialityLevel().getId();
		}

		if (description != null) {
			description = description.replaceAll("\\<.*?>", "");
		}
	}

	public static List<TaskTO> toTOList(final Collection<Task> tasks) {
		final List<TaskTO> taskTOs = new ArrayList<TaskTO>();
		if ((tasks != null) && !tasks.isEmpty()) {
			for (Task task : tasks) {
				taskTOs.add(new TaskTO(task));
			}
		}
		return taskTOs;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
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

	public Date getCompletedDate() {
		return completedDate == null ? null : new Date(completedDate.getTime());
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate == null ? null : new Date(
				completedDate.getTime());
	}

	public Date getReminderSentDate() {
		return reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public void setReminderSentDate(Date reminderSentDate) {
		this.reminderSentDate = reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public UUID getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			final UUID confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}
}
