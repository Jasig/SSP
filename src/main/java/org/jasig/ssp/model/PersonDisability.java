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
package org.jasig.ssp.model; // NOPMD

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.DisabilityStatus;


/**
 * Students should have some disability information stored for use in
 * Disability Services reports.
 * 
 * Students may have one associated disability instance (one-to-one mapping).
 * Non-student users should never have any disability information associated to
 * them.
 * 
 * @author shawn.gormley
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonDisability // NOPMD
		extends AbstractAuditable implements Auditable {

	private static final long serialVersionUID = 3252611289245443664L;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "disability_status_id", nullable = true)
	private DisabilityStatus disabilityStatus;	

	/**
	 * Associated person.
	 * 
	 */
	@Column(length = 50)
	@Size(max = 50)
	private String intakeCounselor;

	/**
	 * Associated person.
	 * 
	 */
	@Column(length = 50)
	@Size(max = 50)
	private String referredBy;

	@Column(length = 50)
	@Size(max = 50)
	private String contactName;
	
	private Boolean releaseSigned;	

	private Boolean recordsRequested;

	// cannot use "from" in column names. See https://issues.jasig.org/browse/SSP-727
	@Column(name = "records_requested_contact", length = 50)
	@Size(max = 50)
	private String recordsRequestedFrom;
	
	private Boolean referForScreening;

	// cannot use "from" in column names. See https://issues.jasig.org/browse/SSP-727
	@Column(name = "documents_requested_contact", length = 50)
	@Size(max = 50)
	private String documentsRequestedFrom;	
	
	@Column(length = 50)
	@Size(max = 50)
	private String rightsAndDuties;		
	
	private Boolean eligibleLetterSent;	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date eligibleLetterDate;	

	private Boolean ineligibleLetterSent;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date ineligibleLetterDate;		

	private Boolean noDocumentation;	
	
	private Boolean inadequateDocumentation;		
	
	private Boolean noDisability;		
	
	private Boolean noSpecialEd;		

	@Column(length = 50)
	@Size(max = 50)
	private String tempEligibilityDescription;

	private Boolean onMedication;	

	@Column(length = 50)
	@Size(max = 50)
	private String medicationList;	

	@Column(length = 50)
	@Size(max = 50)
	private String functionalLimitations;	
	
	public DisabilityStatus getDisabilityStatus() {
		return disabilityStatus;
	}

	public void setDisabilityStatus(final DisabilityStatus disabilityStatus) {
		this.disabilityStatus = disabilityStatus;
	}

	public String getIntakeCounselor() {
		return intakeCounselor;
	}

	public void setIntakeCounselor(final String intakeCounselor) {
		this.intakeCounselor = intakeCounselor;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(final String referredBy) {
		this.referredBy = referredBy;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(final String contactName) {
		this.contactName = contactName;
	}

	public Boolean getReleaseSigned() {
		return releaseSigned;
	}

	public void setReleaseSigned(final Boolean releaseSigned) {
		this.releaseSigned = releaseSigned;
	}

	public Boolean getRecordsRequested() {
		return recordsRequested;
	}

	public void setRecordsRequested(final Boolean recordsRequested) {
		this.recordsRequested = recordsRequested;
	}

	public String getRecordsRequestedFrom() {
		return recordsRequestedFrom;
	}

	public void setRecordsRequestedFrom(final String recordsRequestedFrom) {
		this.recordsRequestedFrom = recordsRequestedFrom;
	}

	public Boolean getReferForScreening() {
		return referForScreening;
	}

	public void setReferForScreening(final Boolean referForScreening) {
		this.referForScreening = referForScreening;
	}

	public String getDocumentsRequestedFrom() {
		return documentsRequestedFrom;
	}

	public void setDocumentsRequestedFrom(final String documentsRequestedFrom) {
		this.documentsRequestedFrom = documentsRequestedFrom;
	}

	public String getRightsAndDuties() {
		return rightsAndDuties;
	}

	public void setRightsAndDuties(final String rightsAndDuties) {
		this.rightsAndDuties = rightsAndDuties;
	}

	public Boolean getEligibleLetterSent() {
		return eligibleLetterSent;
	}

	public void setEligibleLetterSent(final Boolean eligibleLetterSent) {
		this.eligibleLetterSent = eligibleLetterSent;
	}

	public Date getEligibleLetterDate() {
		return eligibleLetterDate;
	}

	public void setEligibleLetterDate(final Date eligibleLetterDate) {
		this.eligibleLetterDate = eligibleLetterDate;
	}

	public Boolean getIneligibleLetterSent() {
		return ineligibleLetterSent;
	}

	public void setIneligibleLetterSent(final Boolean ineligibleLetterSent) {
		this.ineligibleLetterSent = ineligibleLetterSent;
	}

	public Date getIneligibleLetterDate() {
		return ineligibleLetterDate;
	}

	public void setIneligibleLetterDate(final Date ineligibleLetterDate) {
		this.ineligibleLetterDate = ineligibleLetterDate;
	}

	public Boolean getNoDocumentation() {
		return noDocumentation;
	}

	public void setNoDocumentation(final Boolean noDocumentation) {
		this.noDocumentation = noDocumentation;
	}

	public Boolean getInadequateDocumentation() {
		return inadequateDocumentation;
	}

	public void setInadequateDocumentation(final Boolean inadequateDocumentation) {
		this.inadequateDocumentation = inadequateDocumentation;
	}

	public Boolean getNoDisability() {
		return noDisability;
	}

	public void setNoDisability(final Boolean noDisability) {
		this.noDisability = noDisability;
	}

	public Boolean getNoSpecialEd() {
		return noSpecialEd;
	}

	public void setNoSpecialEd(final Boolean noSpecialEd) {
		this.noSpecialEd = noSpecialEd;
	}

	public String getTempEligibilityDescription() {
		return tempEligibilityDescription;
	}

	public void setTempEligibilityDescription(final String tempEligibilityDescription) {
		this.tempEligibilityDescription = tempEligibilityDescription;
	}

	public Boolean getOnMedication() {
		return onMedication;
	}

	public void setOnMedication(final Boolean onMedication) {
		this.onMedication = onMedication;
	}

	public String getMedicationList() {
		return medicationList;
	}

	public void setMedicationList(final String medicationList) {
		this.medicationList = medicationList;
	}

	public String getFunctionalLimitations() {
		return functionalLimitations;
	}

	public void setFunctionalLimitations(final String functionalLimitations) {
		this.functionalLimitations = functionalLimitations;
	}

	@Override
	protected int hashPrime() {
		return 431;
	}

	@Override
	final public int hashCode() { // NOPMD
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonDemographics
		result *= hashField("disabilityStatus", disabilityStatus);
		result *= hashField("intakeCounselor", intakeCounselor);
		result *= hashField("referredBy", referredBy);
		result *= releaseSigned == null ? 2
				: (releaseSigned ? 5 : 3);
		result *= recordsRequested == null ? 7
				: (recordsRequested ? 13 : 11);
		result *= hashField("recordsRequestedFrom", recordsRequestedFrom);
		result *= referForScreening == null ? 17
				: (referForScreening ? 23 : 19);
		result *= hashField("documentsRequestedFrom", documentsRequestedFrom);
		result *= hashField("rightsAndDuties", rightsAndDuties);
		result *= eligibleLetterSent == null ? 29
				: (eligibleLetterSent ? 37 : 31);
		result *= hashField("eligibleLetterDate", eligibleLetterDate);
		result *= ineligibleLetterSent == null ? 41
				: (ineligibleLetterSent ? 47 : 43);
		result *= hashField("ineligibleLetterDate", ineligibleLetterDate);
		result *= noDocumentation == null ? 43
				: (noDocumentation ? 61 : 59);
		result *= inadequateDocumentation == null ? 67
				: (inadequateDocumentation ? 73 : 71);
		result *= noDisability == null ? 79
				: (noDisability ? 89 : 83);
		result *= noSpecialEd == null ? 97
				: (noSpecialEd ? 103 : 101);
		result *= hashField("tempEligibilityDescription", tempEligibilityDescription);
		result *= onMedication == null ? 107
				: (onMedication ? 113 : 109);
		result *= hashField("medicationList", medicationList);
		result *= hashField("functionalLimitations", functionalLimitations);

		return result;
	}
}