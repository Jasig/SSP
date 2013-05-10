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
package org.jasig.ssp.transferobject; // NOPMD

import java.util.UUID;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jasig.ssp.model.PersonDisability;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyDeserializer;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlySerializer;
import org.jasig.ssp.util.DateTimeUtils;
import org.jasig.ssp.util.SspTimeZones;

public class PersonDisabilityTO
		extends AbstractAuditableTO<PersonDisability>
		implements TransferObject<PersonDisability> {

	@NotNull
	private UUID personId;

	private UUID disabilityStatusId;

	private Boolean releaseSigned, recordsRequested,
					referForScreening, eligibleLetterSent,
					ineligibleLetterSent, noDocumentation,
					inadequateDocumentation, noDisability,
					noSpecialEd, onMedication;

	private String intakeCounselor, referredBy, contactName, recordsRequestedFrom, documentsRequestedFrom,
				   rightsAndDuties, tempEligibilityDescription, medicationList, 
				   functionalLimitations;

	@JsonSerialize(using = DateOnlySerializer.class)
	@JsonDeserialize(using = DateOnlyDeserializer.class)
	private Date eligibleLetterDate, ineligibleLetterDate;
	
	public PersonDisabilityTO() {
		super();
	}

	public PersonDisabilityTO(final PersonDisability model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonDisability model) {
		super.from(model);

		if (model.getDisabilityStatus() != null) {
			disabilityStatusId = model.getDisabilityStatus().getId();
		}

		releaseSigned = model.getReleaseSigned();
		recordsRequested = model.getRecordsRequested();
		referForScreening = model.getReferForScreening();
		referredBy = model.getReferredBy();
		intakeCounselor = model.getIntakeCounselor();
		eligibleLetterSent = model.getEligibleLetterSent();
		eligibleLetterDate = model.getEligibleLetterDate();
		ineligibleLetterSent = model.getIneligibleLetterSent();
		ineligibleLetterDate = model.getIneligibleLetterDate();
		noDocumentation = model.getNoDocumentation();
		inadequateDocumentation = model.getInadequateDocumentation();
		noDisability = model.getNoDisability();
		noSpecialEd = model.getNoSpecialEd();
		onMedication = model.getOnMedication();
		contactName = model.getContactName();
		recordsRequestedFrom = model.getRecordsRequestedFrom();
		documentsRequestedFrom = model.getDocumentsRequestedFrom();
		rightsAndDuties = model.getRightsAndDuties();
		tempEligibilityDescription = model.getTempEligibilityDescription();
		medicationList = model.getMedicationList();
		functionalLimitations = model.getFunctionalLimitations();
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getDisabilityStatusId() {
		return disabilityStatusId;
	}

	public void setDisabilityStatusId(UUID disabilityStatusId) {
		this.disabilityStatusId = disabilityStatusId;
	}

	public Boolean getReleaseSigned() {
		return releaseSigned;
	}

	public void setReleaseSigned(Boolean releaseSigned) {
		this.releaseSigned = releaseSigned;
	}

	public Boolean getRecordsRequested() {
		return recordsRequested;
	}

	public void setRecordsRequested(Boolean recordsRequested) {
		this.recordsRequested = recordsRequested;
	}

	public Boolean getReferForScreening() {
		return referForScreening;
	}

	public void setReferForScreening(Boolean referForScreening) {
		this.referForScreening = referForScreening;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public String getIntakeCounselor() {
		return intakeCounselor;
	}

	public void setIntakeCounselor(String intakeCounselor) {
		this.intakeCounselor = intakeCounselor;
	}

	public Boolean getEligibleLetterSent() {
		return eligibleLetterSent;
	}

	public void setEligibleLetterSent(Boolean eligibleLetterSent) {
		this.eligibleLetterSent = eligibleLetterSent;
	}

	public Boolean getIneligibleLetterSent() {
		return ineligibleLetterSent;
	}

	public void setIneligibleLetterSent(Boolean ineligibleLetterSent) {
		this.ineligibleLetterSent = ineligibleLetterSent;
	}

	public Boolean getNoDocumentation() {
		return noDocumentation;
	}

	public void setNoDocumentation(Boolean noDocumentation) {
		this.noDocumentation = noDocumentation;
	}

	public Boolean getInadequateDocumentation() {
		return inadequateDocumentation;
	}

	public void setInadequateDocumentation(Boolean inadequateDocumentation) {
		this.inadequateDocumentation = inadequateDocumentation;
	}

	public Boolean getNoDisability() {
		return noDisability;
	}

	public void setNoDisability(Boolean noDisability) {
		this.noDisability = noDisability;
	}

	public Boolean getNoSpecialEd() {
		return noSpecialEd;
	}

	public void setNoSpecialEd(Boolean noSpecialEd) {
		this.noSpecialEd = noSpecialEd;
	}

	public Boolean getOnMedication() {
		return onMedication;
	}

	public void setOnMedication(Boolean onMedication) {
		this.onMedication = onMedication;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getRecordsRequestedFrom() {
		return recordsRequestedFrom;
	}

	public void setRecordsRequestedFrom(String recordsRequestedFrom) {
		this.recordsRequestedFrom = recordsRequestedFrom;
	}

	public String getDocumentsRequestedFrom() {
		return documentsRequestedFrom;
	}

	public void setDocumentsRequestedFrom(String documentsRequestedFrom) {
		this.documentsRequestedFrom = documentsRequestedFrom;
	}

	public String getRightsAndDuties() {
		return rightsAndDuties;
	}

	public void setRightsAndDuties(String rightsAndDuties) {
		this.rightsAndDuties = rightsAndDuties;
	}

	public String getTempEligibilityDescription() {
		return tempEligibilityDescription;
	}

	public void setTempEligibilityDescription(String tempEligibilityDescription) {
		this.tempEligibilityDescription = tempEligibilityDescription;
	}

	public String getMedicationList() {
		return medicationList;
	}

	public void setMedicationList(String medicationList) {
		this.medicationList = medicationList;
	}

	public String getFunctionalLimitations() {
		return functionalLimitations;
	}

	public void setFunctionalLimitations(String functionalLimitations) {
		this.functionalLimitations = functionalLimitations;
	}

	public Date getEligibleLetterDate() {
		return eligibleLetterDate;
	}

	public void setEligibleLetterDate(Date eligibleLetterDate) {
		this.eligibleLetterDate = eligibleLetterDate;
	}

	public Date getIneligibleLetterDate() {
		return ineligibleLetterDate;
	}

	public void setIneligibleLetterDate(Date ineligibleLetterDate) {
		this.ineligibleLetterDate = ineligibleLetterDate;
	}

	@JsonProperty
	@JsonSerialize(using = DateOnlySerializer.class)
	public Date getOdsRegistrationDate() {
		return DateTimeUtils.midnightOn(getCreatedDate());
	}
}