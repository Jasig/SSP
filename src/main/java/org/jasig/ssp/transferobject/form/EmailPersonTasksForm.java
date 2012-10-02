/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.form;

import java.util.List;
import java.util.UUID;

/**
 * Command Object for the email method of the PersonTaskController.
 * 
 * Only one of either recipientEmailAddresses or recipientIds is required.
 */
public class EmailPersonTasksForm {
	private List<UUID> taskIds;

	private List<UUID> goalIds;

	private List<String> recipientEmailAddresses;

	private List<UUID> recipientIds;

	public List<UUID> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(final List<UUID> taskIds) {
		this.taskIds = taskIds;
	}

	public List<UUID> getGoalIds() {
		return goalIds;
	}

	public void setGoalIds(final List<UUID> goalIds) {
		this.goalIds = goalIds;
	}

	public List<String> getRecipientEmailAddresses() {
		return recipientEmailAddresses;
	}

	public void setRecipientEmailAddresses(
			final List<String> recipientEmailAddresses) {
		this.recipientEmailAddresses = recipientEmailAddresses;
	}

	public List<UUID> getRecipientIds() {
		return recipientIds;
	}

	public void setRecipientIds(final List<UUID> recipientIds) {
		this.recipientIds = recipientIds;
	}
}