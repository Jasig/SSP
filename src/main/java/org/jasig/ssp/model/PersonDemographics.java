package org.jasig.ssp.model; // NOPMD by jon.adams on 5/24/12 1:34 PM

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.ChildCareArrangement;
import org.jasig.ssp.model.reference.Citizenship;
import org.jasig.ssp.model.reference.EmploymentShifts;
import org.jasig.ssp.model.reference.Ethnicity;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.model.reference.MaritalStatus;
import org.jasig.ssp.model.reference.VeteranStatus;

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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonDemographics // NOPMD by jon.adams on 5/24/12 1:34 PM
		extends AbstractAuditable
		implements Auditable {

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
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "marital_status_id", nullable = true)
	private MaritalStatus maritalStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "ethnicity_id", nullable = true)
	private Ethnicity ethnicity;

	@Enumerated(EnumType.STRING)
	private Genders gender;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "citizenship_id", nullable = true)
	private Citizenship citizenship;

	@Column(length = 50)
	@Size(max = 50)
	private String countryOfCitizenship;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "veteran_status_id", nullable = true)
	private VeteranStatus veteranStatus;

	@Column
	private boolean primaryCaregiver;

	@Column
	private int numberOfChildren;

	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "child_care_arrangement_id", nullable = true)
	private ChildCareArrangement childCareArrangement;

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

	public boolean isAbilityToBenefit() {
		return abilityToBenefit;
	}

	public void setAbilityToBenefit(final boolean abilityToBenefit) {
		this.abilityToBenefit = abilityToBenefit;
	}

	public String getAnticipatedStartTerm() {
		return anticipatedStartTerm;
	}

	public void setAnticipatedStartTerm(final String anticipatedStartTerm) {
		this.anticipatedStartTerm = anticipatedStartTerm;
	}

	public String getAnticipatedStartYear() {
		return anticipatedStartYear;
	}

	public void setAnticipatedStartYear(final String anticipatedStartYear) {
		this.anticipatedStartYear = anticipatedStartYear;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(final boolean isLocal) {
		local = isLocal;
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

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(final MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Ethnicity getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(final Ethnicity ethnicity) {
		this.ethnicity = ethnicity;
	}

	public Genders getGender() {
		return gender;
	}

	public void setGender(final Genders gender) {
		this.gender = gender;
	}

	public Citizenship getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(final Citizenship citizenship) {
		this.citizenship = citizenship;
	}

	public String getCountryOfCitizenship() {
		return countryOfCitizenship;
	}

	public void setCountryOfCitizenship(final String countryOfCitizenship) {
		this.countryOfCitizenship = countryOfCitizenship;
	}

	public VeteranStatus getVeteranStatus() {
		return veteranStatus;
	}

	public void setVeteranStatus(final VeteranStatus veteranStatus) {
		this.veteranStatus = veteranStatus;
	}

	public boolean isPrimaryCaregiver() {
		return primaryCaregiver;
	}

	public void setPrimaryCaregiver(final boolean primaryCaregiver) {
		this.primaryCaregiver = primaryCaregiver;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(final int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public ChildCareArrangement getChildCareArrangement() {
		return childCareArrangement;
	}

	public void setChildCareArrangement(
			final ChildCareArrangement childCareArrangement) {
		this.childCareArrangement = childCareArrangement;
	}

	public String getChildAges() {
		return childAges;
	}

	public void setChildAges(final String childAges) {
		this.childAges = childAges;
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

	public String getPlaceOfEmployment() {
		return placeOfEmployment;
	}

	public void setPlaceOfEmployment(final String placeOfEmployment) {
		this.placeOfEmployment = placeOfEmployment;
	}

	public EmploymentShifts getShift() {
		return shift;
	}

	public void setShift(final EmploymentShifts shift) {
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

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @param maritalStatus
	 *            Marital status
	 * @param ethnicity
	 *            Ethnicity
	 * @param citizenship
	 *            Citizenship
	 * @param veterenStatus
	 *            Veteran status
	 * @param coach
	 *            Coach, if any
	 * @param childCareArrangement
	 *            Child Care Arrangements
	 */
	public void overwrite(final PersonDemographics source,
			final MaritalStatus maritalStatus, final Ethnicity ethnicity,
			final Citizenship citizenship, final VeteranStatus veterenStatus,
			final Person coach,
			final ChildCareArrangement childCareArrangement) {
		setAbilityToBenefit(source.isAbilityToBenefit());
		setAnticipatedStartTerm(source.getAnticipatedStartTerm());
		setAnticipatedStartYear(source.getAnticipatedStartYear());
		setLocal(source.isLocal());
		setCountryOfResidence(source.getCountryOfResidence());
		setPaymentStatus(source.getPaymentStatus());
		setEthnicity(source.getEthnicity());
		setGender(source.getGender());
		setCitizenship(source.getCitizenship());
		setCountryOfCitizenship(source.getCountryOfCitizenship());
		setPrimaryCaregiver(source.isPrimaryCaregiver());
		setNumberOfChildren(source.getNumberOfChildren());
		setChildAges(source.getChildAges());
		setChildCareNeeded(source.isChildCareNeeded());
		setEmployed(source.isEmployed());
		setPlaceOfEmployment(source.getPlaceOfEmployment());
		setShift(source.getShift());
		setWage(source.getWage());
		setTotalHoursWorkedPerWeek(source.getTotalHoursWorkedPerWeek());

		setMaritalStatus(maritalStatus);
		setEthnicity(ethnicity);
		setCitizenship(citizenship);
		setVeteranStatus(veterenStatus);
		setChildCareArrangement(childCareArrangement);
	}

	@Override
	protected int hashPrime() {
		return 11;
	};

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:13 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonDemographics
		result *= abilityToBenefit ? 7 : 11;
		result *= hashField("anticipatedStartTerm", anticipatedStartTerm);
		result *= hashField("anticipatedStartYear", anticipatedStartYear);
		result *= local ? 3 : 5;
		result *= hashField("countryOfResidence", countryOfResidence);
		result *= hashField("paymentStatus", paymentStatus);
		result *= hashField("maritalStatus", maritalStatus);
		result *= hashField("ethnicity", ethnicity);
		result *= gender == null ? "gender".hashCode() : gender.hashCode();
		result *= hashField("citizenship", citizenship);
		result *= hashField("countryOfCitizenship", countryOfCitizenship);
		result *= hashField("veteranStatus", veteranStatus);
		result *= primaryCaregiver ? 13 : 17;
		result *= hashField("numberOfChildren", numberOfChildren);
		result *= hashField("childCareArrangement", childCareArrangement);
		result *= hashField("childAges", childAges);
		result *= childCareNeeded ? 19 : 23;
		result *= employed ? 29 : 31;
		result *= hashField("placeOfEmployment", placeOfEmployment);
		result *= shift == null ? "shift".hashCode() : shift.hashCode();
		result *= hashField("wage", wage);
		result *= hashField("totalHoursWorkedPerWeek", totalHoursWorkedPerWeek);

		return result;
	}
}