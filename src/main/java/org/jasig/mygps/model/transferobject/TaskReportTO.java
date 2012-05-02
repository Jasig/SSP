package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

	public TaskReportTO(final Task task) {
		if (task.getType().equals(Task.CUSTOM_ACTION_PLAN_TASK)) {
			challengeName = "Custom Action Plan Task";
			challengeReferralName = task.getName();
			description = task.getDescription();
			dueDate = task.getDueDate();
		} else {
			challengeName = task.getChallenge().getName();
			challengeReferralName = task.getChallengeReferral().getName();
			description = task.getChallengeReferral().getPublicDescription();
			dueDate = null;
		}

		createdBy = task.getCreatedBy().getId();
		type = task.getType();
	}

	public static List<TaskReportTO> tasksToTaskReportTOs(final List<Task> tasks) {
		final List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();
		for (Task task : tasks) {
			taskReportTOs.add(new TaskReportTO(task));
		}
		return taskReportTOs;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getChallengeName() {
		return challengeName;
	}

	public void setChallengeName(final String challengeName) {
		this.challengeName = challengeName;
	}

	public String getChallengeReferralName() {
		return challengeReferralName;
	}

	public void setChallengeReferralName(final String challengeReferralName) {
		this.challengeReferralName = challengeReferralName;
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

	public UUID getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final UUID createdBy) {
		this.createdBy = createdBy;
	}

	protected int hashPrime() {
		return 61;
	}

	@Override
	public int hashCode() {
		int result = hashPrime();

		result *= type == null ? "type".hashCode() : type.hashCode();
		result *= challengeName == null ? "challengeName".hashCode()
				: challengeName.hashCode();
		result *= challengeReferralName == null ? "challengeReferralName"
				.hashCode() : challengeReferralName.hashCode();
		result *= description == null ? "description".hashCode() : description
				.hashCode();
		result *= createdBy == null ? "createdBy".hashCode() : createdBy
				.hashCode();
		result *= dueDate == null ? "dueDate".hashCode() : dueDate.hashCode();

		return result;
	}

	@Override
	final public boolean equals(final Object obj) {
		if (this == obj) {
			// exact references that point to the same place in memory are
			// always equal
			return true;
		}

		if (!(TaskReportTO.class.isInstance(obj))
				|| !(getClass().equals(obj.getClass()))) {
			return false;
		}

		final TaskReportTO other = (TaskReportTO) obj;

		return hasSameDomainSignature(other);
	}

	/**
	 * This method MUST be implemented for each class and must compare to all
	 * properties that define an equal instance for business rule comparison
	 * purposes.
	 * 
	 * @param other
	 *            The object to compare
	 * @return True if properties for business equality are all equal.
	 */
	private boolean hasSameDomainSignature(final Object other) {
		return hashCode() == other.hashCode();
	}

	@Override
	public String toString() {
		return challengeName == null ? super.toString() : challengeName;
	}

	@Override
	public int compareTo(final TaskReportTO taskReportTO) {
		return challengeName.compareTo(taskReportTO.getChallengeName());
	}
}
