package edu.sinclair.ssp.model;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.*;

public class Student {

	private UUID id;
	
	private Person person;
	
	private boolean abilityToBenefit;
	
	private String anticipatedStartTerm;
	
	private String anticipatedStartYear;
	
	private boolean isLocal;
	
	private String countryOfResidence;
	
	private String paymentStatus;
	
	private MaritalStatus maritalStatus;
	
	private Ethnicity ethnicity;
	
	private String gender;
	
	private Citizenship citizenship;
	
	private String countryOfCitizenship;
	
	private VeteranStatus veteranStatus;
	
	private boolean primaryCaregiver;
	
	private int numberOfChildren;
	
	private String childAges;
	
	private boolean childCareNeeded;
	
	private boolean employed;
	
	private String placeOfEmployment;
	
	private String shift;
	
	private String wage;
	
	private String totalHoursWorkedPerWeek;
	
	private Person coach;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public boolean isAbilityToBenefit() {
		return abilityToBenefit;
	}

	public void setAbilityToBenefit(boolean abilityToBenefit) {
		this.abilityToBenefit = abilityToBenefit;
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

	public boolean isLocal() {
		return isLocal;
	}

	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
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

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Ethnicity getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(Ethnicity ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Citizenship getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(Citizenship citizenship) {
		this.citizenship = citizenship;
	}

	public String getCountryOfCitizenship() {
		return countryOfCitizenship;
	}

	public void setCountryOfCitizenship(String countryOfCitizenship) {
		this.countryOfCitizenship = countryOfCitizenship;
	}

	public VeteranStatus getVeteranStatus() {
		return veteranStatus;
	}

	public void setVeteranStatus(VeteranStatus veteranStatus) {
		this.veteranStatus = veteranStatus;
	}

	public boolean isPrimaryCaregiver() {
		return primaryCaregiver;
	}

	public void setPrimaryCaregiver(boolean primaryCaregiver) {
		this.primaryCaregiver = primaryCaregiver;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public String getChildAges() {
		return childAges;
	}

	public void setChildAges(String childAges) {
		this.childAges = childAges;
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

	public Person getCoach() {
		return coach;
	}

	public void setCoach(Person coach) {
		this.coach = coach;
	}

}
