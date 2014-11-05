/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject; // NOPMD

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.PersonDemographics;

public class PersonDemographicsTO
		extends AbstractAuditableTO<PersonDemographics>
		implements TransferObject<PersonDemographics> {

	@NotNull
	private UUID personId;

	private UUID coachId, maritalStatusId, militaryAffiliationId, ethnicityId,
			raceId, citizenshipId, veteranStatusId, childCareArrangementId;
	private Boolean local, primaryCaregiver,
			childCareNeeded, employed;
	private Integer numberOfChildren;
	private String countryOfResidence, paymentStatus, gender,
			countryOfCitizenship,
			childAges, placeOfEmployment, shift, wage, totalHoursWorkedPerWeek;

	private BigDecimal balanceOwed;

	public PersonDemographicsTO() {
		super();
	}

	public PersonDemographicsTO(final PersonDemographics model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonDemographics model) {
		super.from(model);

		if (model.getMaritalStatus() != null) {
			maritalStatusId = model.getMaritalStatus().getId();
		}

		if (model.getMilitaryAffiliation() != null) {
			militaryAffiliationId = model.getMilitaryAffiliation().getId();
		}
		
		if (model.getEthnicity() != null) {
			ethnicityId = model.getEthnicity().getId();
		}
		
		if (model.getRace() != null) {
			raceId = model.getRace().getId();
		}

		if (model.getCitizenship() != null) {
			citizenshipId = model.getCitizenship().getId();
		}

		if (model.getVeteranStatus() != null) {
			veteranStatusId = model.getVeteranStatus().getId();
		}

		if (model.getChildCareArrangement() != null) {
			childCareArrangementId = model.getChildCareArrangement().getId();
		}

		local = model.getLocal();
		primaryCaregiver = model.getPrimaryCaregiver();
		childCareNeeded = model.getChildCareNeeded();
		employed = model.getEmployed();
		numberOfChildren = model.getNumberOfChildren();
		balanceOwed = model.getBalanceOwed();
		countryOfResidence = model.getCountryOfResidence();
		paymentStatus = model.getPaymentStatus();
		if (model.getGender() != null) {
			gender = model.getGender().getCode();
		}

		countryOfCitizenship = model.getCountryOfCitizenship();
		childAges = model.getChildAges();
		placeOfEmployment = model.getPlaceOfEmployment();
		if (model.getShift() != null) {
			shift = model.getShift().getCode();
		}

		wage = model.getWage();
		totalHoursWorkedPerWeek = model.getTotalHoursWorkedPerWeek();
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(final UUID coachId) {
		this.coachId = coachId;
	}

	public UUID getMaritalStatusId() {
		return maritalStatusId;
	}

	public void setMaritalStatusId(final UUID maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public UUID getMilitaryAffiliationId() {
		return militaryAffiliationId;
	}

	public void setMilitaryAffiliationId(final UUID militaryAffiliationId) {
		this.militaryAffiliationId = militaryAffiliationId;
	}	
	
	public UUID getEthnicityId() {
		return ethnicityId;
	}

	public void setEthnicityId(final UUID ethnicityId) {
		this.ethnicityId = ethnicityId;
	}
	
	public UUID getRaceId() {
		return raceId;
	}
	
	public void setRaceId(final UUID raceId) {
		this.raceId = raceId;
	}

	public UUID getCitizenshipId() {
		return citizenshipId;
	}

	public void setCitizenshipId(final UUID citizenshipId) {
		this.citizenshipId = citizenshipId;
	}

	public UUID getVeteranStatusId() {
		return veteranStatusId;
	}

	public void setVeteranStatusId(final UUID veteranStatusId) {
		this.veteranStatusId = veteranStatusId;
	}

	public UUID getChildCareArrangementId() {
		return childCareArrangementId;
	}

	public void setChildCareArrangementId(final UUID childCareArrangementId) {
		this.childCareArrangementId = childCareArrangementId;
	}

	public Boolean getLocal() {
		return local;
	}

	public void setLocal(final Boolean local) {
		this.local = local;
	}

	public Boolean getPrimaryCaregiver() {
		return primaryCaregiver;
	}

	public void setPrimaryCaregiver(final Boolean primaryCaregiver) {
		this.primaryCaregiver = primaryCaregiver;
	}

	public Boolean getChildCareNeeded() {
		return childCareNeeded;
	}

	public void setChildCareNeeded(final Boolean childCareNeeded) {
		this.childCareNeeded = childCareNeeded;
	}

	public Boolean getEmployed() {
		return employed;
	}

	public void setEmployed(final Boolean employed) {
		this.employed = employed;
	}

	public BigDecimal getBalanceOwed() {
		return balanceOwed;
	}

	public void setBalanceOwed(final BigDecimal balanceOwed) {
		this.balanceOwed = balanceOwed;
	}

	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	public void setCountryOfResidence(final String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(final String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(final String gender) {
		this.gender = gender;
	}

	public String getCountryOfCitizenship() {
		return countryOfCitizenship;
	}

	public void setCountryOfCitizenship(final String countryOfCitizenship) {
		this.countryOfCitizenship = countryOfCitizenship;
	}

	public String getChildAges() {
		return childAges;
	}

	public void setChildAges(final String childAges) {
		this.childAges = childAges;
	}

	public String getPlaceOfEmployment() {
		return placeOfEmployment;
	}

	public void setPlaceOfEmployment(final String placeOfEmployment) {
		this.placeOfEmployment = placeOfEmployment;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(final String shift) {
		this.shift = shift;
	}

	public String getWage() {
		return wage;
	}

	public void setWage(final String wage) {
		this.wage = wage;
	}

	public String getTotalHoursWorkedPerWeek() {
		return totalHoursWorkedPerWeek;
	}

	public void setTotalHoursWorkedPerWeek(final String totalHoursWorkedPerWeek) {
		this.totalHoursWorkedPerWeek = totalHoursWorkedPerWeek;
	}

	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(final Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
}