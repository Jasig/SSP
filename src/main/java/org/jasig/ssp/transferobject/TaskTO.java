package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Task;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;

/**
 * Task transfer object
 */
public class TaskTO
		extends AbstractAuditableTO<Task>
		implements TransferObject<Task>, Serializable, NamedTO {

	private static final long serialVersionUID = 5796302591576434925L;

	private String type;

	private String name, description;

	private boolean deletable;

	private Date dueDate, completedDate, reminderSentDate;

	private UUID personId, challengeId, challengeReferralId;

	private ConfidentialityLevelLiteTO confidentialityLevel;

	public TaskTO() {
		super();
	}

	public TaskTO(final Task task) {
		super();
		from(task);
	}

	@Override
	public final void from(final Task task) {
		super.from(task);

		type = task.getType();
		deletable = task.isDeletable();
		dueDate = task.getDueDate();
		completedDate = task.getCompletedDate();
		reminderSentDate = task.getReminderSentDate();

		name = task.getName();
		description = task.getDescription() == null ? null : task
				.getDescription().replaceAll("\\<.*?>", "");

		if (task.getChallenge() != null) {
			challengeId = task.getChallenge().getId();
		}

		if (task.getChallengeReferral() != null) {
			challengeReferralId = task.getChallengeReferral().getId();
		}

		confidentialityLevel = ConfidentialityLevelLiteTO.fromModel(
				task.getConfidentialityLevel());
	}

	public static List<TaskTO> toTOList(final Collection<Task> tasks) {
		final List<TaskTO> taskTOs = new ArrayList<TaskTO>();
		if ((tasks != null) && !tasks.isEmpty()) {
			for (final Task task : tasks) {
				taskTOs.add(new TaskTO(task)); // NOPMD
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

	/**
	 * Has this Task been completed?
	 * 
	 * <p>
	 * Generated field that will always be true if completedDate is non-null.
	 * 
	 * @return true if Task has been completed, determined by a non-null
	 *         completedDate
	 */
	public boolean isCompleted() {
		return completedDate != null;
	}

	/**
	 * Side affect: Sets or nulls the completedDate if necessary.
	 * 
	 * <p>
	 * It is advised to use {@link #setCompletedDate(Date)} explicitly instead
	 * of changing the value via this setter.
	 * 
	 * @param completed
	 *            Is this task completed or not?
	 */
	public void setCompleted(final boolean completed) {
		if (completed && completedDate == null) {
			// Completed date not already set, so set it to now.
			completedDate = new Date();
		} else if (!completed) {
			// Ensure completed date is not set.
			completedDate = null; // NOPMD
		}
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

	/**
	 * Completed date.
	 * 
	 * <p>
	 * Side affect: the result of {@link #isCompleted()} is generated based on
	 * this value.
	 * 
	 * @param completedDate
	 *            Completed date
	 */
	public void setCompletedDate(final Date completedDate) {
		this.completedDate = completedDate == null ? null : new Date(
				completedDate.getTime());
	}

	public Date getReminderSentDate() {
		return reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public void setReminderSentDate(final Date reminderSentDate) {
		this.reminderSentDate = reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public ConfidentialityLevelLiteTO getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			final ConfidentialityLevelLiteTO confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}
}