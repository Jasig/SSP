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
package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Person;

import com.google.common.collect.Lists;

public class EmailStudentRequestTO implements Serializable {




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UUID studentId;
	
	private UUID confidentialityLevelId;
	
	private Boolean createJournalEntry;
	
	private Boolean sendToPrimaryEmail;
	
	private String primaryEmail;
	
	private Boolean sendToSecondaryEmail;
	
	private String secondaryEmail;
	
	private Boolean sendToAdditionalEmail;
	
	private String additionalEmail;
	
	private String emailSubject;
	
	private String emailBody;

	public UUID getStudentId() {
		return studentId;
	}

	public void setStudentId(UUID studentId) {
		this.studentId = studentId;
	}

	public UUID getConfidentialityLevelId() {
		return confidentialityLevelId;
	}

	public void setConfidentialityLevelId(UUID confidentialityLevelId) {
		this.confidentialityLevelId = confidentialityLevelId;
	}

	public Boolean getCreateJournalEntry() {
		return createJournalEntry;
	}

	public void setCreateJournalEntry(Boolean createJournalEntry) {
		this.createJournalEntry = createJournalEntry;
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

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
}