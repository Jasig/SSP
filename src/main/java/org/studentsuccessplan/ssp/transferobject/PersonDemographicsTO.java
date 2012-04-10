package org.studentsuccessplan.ssp.transferobject;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.PersonDemographics;
import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.model.reference.EmploymentShifts;
import org.studentsuccessplan.ssp.model.reference.Ethnicity;
import org.studentsuccessplan.ssp.model.reference.Genders;
import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;

public class PersonDemographicsTO extends AuditableTO<PersonDemographics>
		implements TransferObject<PersonDemographics> {

	private UUID personId, coachId, maritalStatusId, ethnicityId,
			citizenshipId, veteranStatusId;
	private boolean abilityToBenefit, isLocal, primaryCaregiver,
			childCareNeeded, employed;
	private int numberOfChildren;
	private String anticipatedStartTerm, anticipatedStartYear,
			countryOfResidence, paymentStatus, gender, countryOfCitizenship,
			childAges, placeOfEmployment, shift, wage, totalHoursWorkedPerWeek;

	public PersonDemographicsTO() {
		super();
	}

	public PersonDemographicsTO(PersonDemographics model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(PersonDemographics model) {
		super.fromModel(model);

		if (model.getCoach() != null) {
			setCoachId(model.getCoach().getId());
		}
		if (model.getMaritalStatus() != null) {
			setMaritalStatusId(model.getMaritalStatus().getId());
		}
		if (model.getEthnicity() != null) {
			setEthnicityId(model.getEthnicity().getId());
		}
		if (model.getCitizenship() != null) {
			setCitizenshipId(model.getCitizenship().getId());
		}
		if (model.getVeteranStatus() != null) {
			setVeteranStatusId(model.getVeteranStatus().getId());
		}
		setAbilityToBenefit(model.isAbilityToBenefit());
		setLocal(model.isLocal());
		setPrimaryCaregiver(model.isPrimaryCaregiver());
		setChildCareNeeded(model.isChildCareNeeded());
		setEmployed(model.isEmployed());
		setNumberOfChildren(model.getNumberOfChildren());
		setAnticipatedStartTerm(model.getAnticipatedStartTerm());
		setAnticipatedStartYear(model.getAnticipatedStartYear());
		setCountryOfResidence(model.getCountryOfResidence());
		setPaymentStatus(model.getPaymentStatus());
		if (model.getGender() != null) {
			setGender(model.getGender().getCode());
		}
		setCountryOfCitizenship(model.getCountryOfCitizenship());
		setChildAges(model.getChildAges());
		setPlaceOfEmployment(model.getPlaceOfEmployment());
		if (model.getShift() != null) {
			setShift(model.getShift().getCode());
		}
		setWage(model.getWage());
		setTotalHoursWorkedPerWeek(model.getTotalHoursWorkedPerWeek());
	}

	@Override
	public PersonDemographics pushAttributesToModel(PersonDemographics model) {
		super.addToModel(model);

		if (getCoachId() != null) {
			model.setCoach(new Person(getCoachId()));
		}
		if (getMaritalStatusId() != null) {
			model.setMaritalStatus(new MaritalStatus(getMaritalStatusId()));
		}
		if (getEthnicityId() != null) {
			model.setEthnicity(new Ethnicity(getEthnicityId()));
		}
		if (getCitizenshipId() != null) {
			model.setCitizenship(new Citizenship(getCitizenshipId()));
		}
		if (getVeteranStatusId() != null) {
			model.setVeteranStatus(new VeteranStatus(getVeteranStatusId()));
		}
		model.setAbilityToBenefit(isAbilityToBenefit());
		model.setLocal(isLocal());
		model.setPrimaryCaregiver(isPrimaryCaregiver());
		model.setChildCareNeeded(isChildCareNeeded());
		model.setEmployed(isEmployed());
		model.setNumberOfChildren(getNumberOfChildren());
		model.setAnticipatedStartTerm(getAnticipatedStartTerm());
		model.setAnticipatedStartYear(getAnticipatedStartYear());
		model.setCountryOfResidence(getCountryOfResidence());
		model.setPaymentStatus(getPaymentStatus());
		if (getGender() != null) {
			model.setGender(Genders.valueOf(getGender()));
		}
		model.setCountryOfCitizenship(getCountryOfCitizenship());
		model.setChildAges(getChildAges());
		model.setPlaceOfEmployment(getPlaceOfEmployment());
		if (getShift() != null) {
			model.setShift(EmploymentShifts.valueOf(getShift()));
		}
		model.setWage(getWage());
		model.setTotalHoursWorkedPerWeek(getTotalHoursWorkedPerWeek());
		return model;
	}

	@Override
	public PersonDemographics asModel() {
		return pushAttributesToModel(new PersonDemographics());
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}

	public UUID getMaritalStatusId() {
		return maritalStatusId;
	}

	public void setMaritalStatusId(UUID maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public UUID getEthnicityId() {
		return ethnicityId;
	}

	public void setEthnicityId(UUID ethnicityId) {
		this.ethnicityId = ethnicityId;
	}

	public UUID getCitizenshipId() {
		return citizenshipId;
	}

	public void setCitizenshipId(UUID citizenshipId) {
		this.citizenshipId = citizenshipId;
	}

	public UUID getVeteranStatusId() {
		return veteranStatusId;
	}

	public void setVeteranStatusId(UUID veteranStatusId) {
		this.veteranStatusId = veteranStatusId;
	}

	public boolean isAbilityToBenefit() {
		return abilityToBenefit;
	}

	public void setAbilityToBenefit(boolean abilityToBenefit) {
		this.abilityToBenefit = abilityToBenefit;
	}

	public boolean isLocal() {
		return isLocal;
	}

	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}

	public boolean isPrimaryCaregiver() {
		return primaryCaregiver;
	}

	public void setPrimaryCaregiver(boolean primaryCaregiver) {
		this.primaryCaregiver = primaryCaregiver;
	}

	public boolean isChildCareNeeded() {
		return childCareNeeded;
	}

	public void setChildCareNeeded(boolean childCareNeeded) {
		this.childCareNeeded = childCareNeeded;
	}

	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(boolean employed) {
		this.employed = employed;
	}

	public String getAnticipatedStartTerm() {
		return anticipatedStartTerm;
	}

	public void setAnticipatedStartTerm(String anticipatedStartTerm) {
		this.anticipatedStartTerm = anticipatedStartTerm;
	}

	public String getAnticipatedStartYear() {
		return anticipatedStartYear;
	}

	public void setAnticipatedStartYear(String anticipatedStartYear) {
		this.anticipatedStartYear = anticipatedStartYear;
	}

	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountryOfCitizenship() {
		return countryOfCitizenship;
	}

	public void setCountryOfCitizenship(String countryOfCitizenship) {
		this.countryOfCitizenship = countryOfCitizenship;
	}

	public String getChildAges() {
		return childAges;
	}

	public void setChildAges(String childAges) {
		this.childAges = childAges;
	}

	public String getPlaceOfEmployment() {
		return placeOfEmployment;
	}

	public void setPlaceOfEmployment(String placeOfEmployment) {
		this.placeOfEmployment = placeOfEmployment;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getWage() {
		return wage;
	}

	public void setWage(String wage) {
		this.wage = wage;
	}

	public String getTotalHoursWorkedPerWeek() {
		return totalHoursWorkedPerWeek;
	}

	public void setTotalHoursWorkedPerWeek(String totalHoursWorkedPerWeek) {
		this.totalHoursWorkedPerWeek = totalHoursWorkedPerWeek;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

}
