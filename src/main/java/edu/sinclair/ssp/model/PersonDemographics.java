package edu.sinclair.ssp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.model.reference.EmploymentShifts;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.model.reference.Genders;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.model.reference.VeteranStatus;

/**
 * Students should have some demographic information stored for use in
 * notifications to appropriate users, and for reporting purposes.
 * 
 * Students may have one associated demographic instance (one-to-one mapping).
 * Non-student users should never have any demographic information associated to
 * them.
 * 
 * @author jon.adams
 */
@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonDemographics extends Auditable implements Serializable {

	private static final long serialVersionUID = 3252611289245443664L;

	@Column
	private boolean abilityToBenefit;

	@Column(length = 25)
	@Size(max = 25)
	private String anticipatedStartTerm;

	@Column(length = 4)
	@Size(max = 4)
	private String anticipatedStartYear;

	private boolean local;

	@Column(length = 50)
	@Size(max = 50)
	private String countryOfResidence;

	@Column(length = 25)
	@Size(max = 25)
	private String paymentStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marital_status_id", nullable = true, insertable = true, updatable = true)
	private MaritalStatus maritalStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ethnicity_id", nullable = true, insertable = true, updatable = true)
	private Ethnicity ethnicity;

	@Enumerated(EnumType.STRING)
	private Genders gender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "citizenship_id", nullable = true, insertable = true, updatable = true)
	private Citizenship citizenship;

	@Column(length = 50)
	@Size(max = 50)
	private String countryOfCitizenship;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "veteran_status_id", nullable = true, insertable = true, updatable = true)
	private VeteranStatus veteranStatus;

	@Column
	private boolean primaryCaregiver;

	@Column
	private int numberOfChildren;

	@Column(length = 50)
	@Size(max = 50)
	private String childAges;

	@Column
	private boolean childCareNeeded;

	private boolean employed;

	@Column(length = 50)
	@Size(max = 50)
	private String placeOfEmployment;

	@Enumerated(EnumType.STRING)
	private EmploymentShifts shift;

	@Column(length = 50)
	@Size(max = 50)
	private String wage;

	@Column(length = 3)
	@Size(max = 3)
	private String totalHoursWorkedPerWeek;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coach_id", nullable = true, insertable = true, updatable = true)
	private Person coach;

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
		return local;
	}

	public void setLocal(boolean isLocal) {
		local = isLocal;
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

	public Genders getGender() {
		return gender;
	}

	public void setGender(Genders gender) {
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

	public EmploymentShifts getShift() {
		return shift;
	}

	public void setShift(EmploymentShifts shift) {
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

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithCollections(PersonDemographics)
	 */
	public void overwrite(PersonDemographics source,
			MaritalStatus maritalStatus, Ethnicity ethnicity,
			Citizenship citizenship, VeteranStatus veterenStatus, Person coach) {
		this.setAbilityToBenefit(source.isAbilityToBenefit());
		this.setAnticipatedStartTerm(source.getAnticipatedStartTerm());
		this.setAnticipatedStartYear(source.getAnticipatedStartYear());
		this.setLocal(source.isLocal());
		this.setCountryOfResidence(source.getCountryOfResidence());
		this.setPaymentStatus(source.getPaymentStatus());
		this.setEthnicity(source.getEthnicity());
		this.setGender(source.getGender());
		this.setCitizenship(source.getCitizenship());
		this.setCountryOfCitizenship(source.getCountryOfCitizenship());
		this.setPrimaryCaregiver(source.isPrimaryCaregiver());
		this.setNumberOfChildren(source.getNumberOfChildren());
		this.setChildAges(source.getChildAges());
		this.setChildCareNeeded(source.isChildCareNeeded());
		this.setEmployed(source.isEmployed());
		this.setPlaceOfEmployment(source.getPlaceOfEmployment());
		this.setShift(source.getShift());
		this.setWage(source.getWage());
		this.setTotalHoursWorkedPerWeek(source.getTotalHoursWorkedPerWeek());

		this.setMaritalStatus(maritalStatus);
		this.setEthnicity(ethnicity);
		this.setCitizenship(citizenship);
		this.setVeteranStatus(veterenStatus);
		this.setCoach(coach);
	}
}
