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
	
	private List<UUID> strengthIds;

	private Boolean sendToPrimaryEmail;
	
	private String primaryEmail;
	
	private Boolean sendToSecondaryEmail;
	
	private String secondaryEmail;
	
	private Boolean sendToAdditionalEmail;
	
	private String additionalEmail;

	public List<UUID> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(final List<UUID> taskIds) {
		this.taskIds = taskIds;
	}

	public Boolean getSendToPrimaryEmail() {
		return sendToPrimaryEmail;
	}

	public void setSendToPrimaryEmail(Boolean sendToPrimaryEmail) {
		this.sendToPrimaryEmail = sendToPrimaryEmail;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public Boolean getSendToSecondaryEmail() {
		return sendToSecondaryEmail;
	}

	public void setSendToSecondaryEmail(Boolean sendToSecondaryEmail) {
		this.sendToSecondaryEmail = sendToSecondaryEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public Boolean getSendToAdditionalEmail() {
		return sendToAdditionalEmail;
	}

	public void setSendToAdditionalEmail(Boolean sendToAdditionalEmail) {
		this.sendToAdditionalEmail = sendToAdditionalEmail;
	}

	public String getAdditionalEmail() {
		return additionalEmail;
	}

	public void setAdditionalEmail(String additionalEmail) {
		this.additionalEmail = additionalEmail;
	}

	public List<UUID> getGoalIds() {
		return goalIds;
	}

	public void setGoalIds(final List<UUID> goalIds) {
		this.goalIds = goalIds;
	}

	public List<UUID> getStrengthIds() {
		return strengthIds;
	}

	public void setStrengthIds(List<UUID> strengthIds) {
		this.strengthIds = strengthIds;
	}
}