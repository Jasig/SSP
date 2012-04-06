package edu.sinclair.mygps.model.transferobject;

import java.util.Date;
import java.util.UUID;

public class TaskReportTO implements Comparable<TaskReportTO> {

	private String type;
	private String challengeName;
	private String challengeReferralName;
	private String description;
	private UUID createdBy;
	private Date dueDate;

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
		return dueDate;
	}

	public UUID getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public int compareTo(TaskReportTO taskReportTO) {
		return challengeName.compareTo(taskReportTO.getChallengeName());
	}

}
