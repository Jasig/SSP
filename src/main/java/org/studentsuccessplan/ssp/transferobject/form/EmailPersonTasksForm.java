package org.studentsuccessplan.ssp.transferobject.form;

import java.util.List;
import java.util.UUID;

/**
 * Command Object for the email method of the PersonTaskController
 * Only one of either recipientEmailAddresses or recipientIds is required
 * 
 */
public class EmailPersonTasksForm {
	private List<UUID> taskIds;

	private List<String> recipientEmailAddresses;
	private List<UUID> recipientIds;

	public List<UUID> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(List<UUID> taskIds) {
		this.taskIds = taskIds;
	}

	public List<String> getRecipientEmailAddresses() {
		return recipientEmailAddresses;
	}

	public void setRecipientEmailAddresses(
			List<String> recipientEmailAddresses) {
		this.recipientEmailAddresses = recipientEmailAddresses;
	}

	public List<UUID> getRecipientIds() {
		return recipientIds;
	}

	public void setRecipientIds(List<UUID> recipientIds) {
		this.recipientIds = recipientIds;
	}
}
