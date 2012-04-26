package org.studentsuccessplan.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;

public class TaskTO
		extends AuditableTO<Task>
		implements TransferObject<Task>, Serializable, NamedTO {

	private static final long serialVersionUID = 5796302591576434925L;

	private String type;
	private String name, description;
	private boolean completed, deletable;
	private Date dueDate, completedDate, reminderSentDate;
	private UUID personId, challengeId, challengeReferralId;
	private ConfidentialityLevel confidentialityLevel;

	/**
	 * Empty constructor
	 */
	public TaskTO() {
		super();
	}

	public TaskTO(final Task task) {
		super();
		fromModel(task);
	}

	@Override
	public void fromModel(final Task task) {
		super.fromModel(task);

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
		
		confidentialityLevel = task.getConfidentialityLevel();

		if (description != null) {
			description = description.replaceAll("\\<.*?>", "");
		}
	}

	@Override
	public Task addToModel(final Task task) {
		super.addToModel(task);
		task.setName(name);
		task.setDescription(description);
		task.setDeletable(deletable);
		task.setDueDate(dueDate);
		task.setCompletedDate(completedDate);
		task.setReminderSentDate(reminderSentDate);
		task.setPerson(new Person(personId));
		task.setChallenge(new Challenge(challengeId));
		task.setChallengeReferral(new ChallengeReferral(challengeReferralId));
		return task;
	}

	@Override
	public Task asModel() {
		Task task = new Task();
		super.addToModel(task);
		return task;
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
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Date getReminderSentDate() {
		return reminderSentDate;
	}

	public void setReminderSentDate(Date reminderSentDate) {
		this.reminderSentDate = reminderSentDate;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
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