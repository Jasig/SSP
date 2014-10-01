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

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.ssp.transferobject.PersonSearchRequestTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkEmailStudentRequestForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID confidentialityLevelId;
	
	private Boolean createJournalEntry;
	
	private Boolean sendToPrimaryEmail;

	private Boolean sendToSecondaryEmail;
	
	private Boolean sendToAdditionalEmail;

	private String additionalEmail;
	
	private List<String> recipientEmailAddresses;
	
	private String coachEmail;
	
	private String emailSubject;
	
	private String emailBody;
	
	private PersonSearchRequestTO criteria;
	
	private Boolean useStrictValidation = true;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BulkEmailStudentRequestForm.class);
	
	
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

	public Boolean getSendToSecondaryEmail() {
		return sendToSecondaryEmail;
	}

	public void setSendToSecondaryEmail(Boolean sendToSecondaryEmail) {
		this.sendToSecondaryEmail = sendToSecondaryEmail;
	}

	public String getCoachEmail() {
		return coachEmail;
	}

	public void setCoachEmail(String coachEmail) {
		this.coachEmail = coachEmail;
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

	public Boolean getUseStrictValidation() {
		return useStrictValidation;
	}

	public void setUseStrictValidation(Boolean useStrictValidation) {
		this.useStrictValidation = useStrictValidation;
	}

	public String getAdditionalEmail() {
		return additionalEmail;
	}

	public void setAdditionalEmail(String additionalEmail) {
		this.additionalEmail = additionalEmail;
	}

	public Boolean getSendToAdditionalEmail() {
		return sendToAdditionalEmail;
	}

	public void setSendToAdditionalEmail(Boolean sendToAdditionalEmail) {
		this.sendToAdditionalEmail = sendToAdditionalEmail;
	}

	public boolean hasEmailSubject() {
		return StringUtils.isNotBlank(emailSubject);
	}
	public boolean hasEmailBody() {
		return StringUtils.isNotBlank(emailBody);
	}
	public boolean hasSendToPrimaryEmail() {
		return getSendToPrimaryEmail() != null && getSendToPrimaryEmail();
	}
	public boolean hasSendToSecondaryEmail() {
		return getSendToSecondaryEmail() != null && getSendToSecondaryEmail();
	}
	public boolean hasNonCcDeliveryAddress() {
		return hasSendToPrimaryEmail() || hasSendToSecondaryEmail();
	}

	public PersonSearchRequestTO getCriteria() {
		return criteria;
	}

	public void setCriteria(PersonSearchRequestTO criteria) {
		this.criteria = criteria;
	}

}