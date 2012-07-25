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

	private UUID coachId, maritalStatusId, ethnicityId,
			citizenshipId, veteranStatusId, childCareArrangementId;
	private boolean abilityToBenefit, local, primaryCaregiver,
			childCareNeeded, employed;
	private Integer numberOfChildren, anticipatedStartYear, actualStartYear;
	private String anticipatedStartTerm, actualStartTerm,
			countryOfResidence, paymentStatus, gender, countryOfCitizenship,
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

		if (model.getEthnicity() != null) {
			ethnicityId = model.getEthnicity().getId();
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

		abilityToBenefit = model.isAbilityToBenefit();
		local = model.isLocal();
		primaryCaregiver = model.isPrimaryCaregiver();
		childCareNeeded = model.isChildCareNeeded();
		employed = model.isEmployed();
		numberOfChildren = model.getNumberOfChildren();
		anticipatedStartTerm = model.getAnticipatedStartTerm();
		anticipatedStartYear = model.getAnticipatedStartYear();
		actualStartTerm = model.getActualStartTerm();
		actualStartYear = model.getActualStartYear();
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

	public UUID getEthnicityId() {
		return ethnicityId;
	}

	public void setEthnicityId(final UUID ethnicityId) {
		this.ethnicityId = ethnicityId;
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

	public boolean isAbilityToBenefit() {
		return abilityToBenefit;
	}

	public void setAbilityToBenefit(final boolean abilityToBenefit) {
		this.abilityToBenefit = abilityToBenefit;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(final boolean local) {
		this.local = local;
	}

	public boolean isPrimaryCaregiver() {
		return primaryCaregiver;
	}

	public void setPrimaryCaregiver(final boolean primaryCaregiver) {
		this.primaryCaregiver = primaryCaregiver;
	}

	public boolean isChildCareNeeded() {
		return childCareNeeded;
	}

	public void setChildCareNeeded(final boolean childCareNeeded) {
		this.childCareNeeded = childCareNeeded;
	}

	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(final boolean employed) {
		this.employed = employed;
	}

	public String getAnticipatedStartTerm() {
		return anticipatedStartTerm;
	}

	public void setAnticipatedStartTerm(final String anticipatedStartTerm) {
		this.anticipatedStartTerm = anticipatedStartTerm;
	}

	public Integer getAnticipatedStartYear() {
		return anticipatedStartYear;
	}

	public void setAnticipatedStartYear(final Integer anticipatedStartYear) {
		this.anticipatedStartYear = anticipatedStartYear;
	}

	public Integer getActualStartYear() {
		return actualStartYear;
	}

	public void setActualStartYear(final Integer actualStartYear) {
		this.actualStartYear = actualStartYear;
	}

	public String getActualStartTerm() {
		return actualStartTerm;
	}

	public void setActualStartTerm(final String actualStartTerm) {
		this.actualStartTerm = actualStartTerm;
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