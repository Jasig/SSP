package edu.sinclair.ssp.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person extends Auditable {

	public static UUID SYSTEM_ADMINISTRATOR_ID = UUID
			.fromString("58ba5ee3-734e-4ae9-b9c5-943774b4de41");

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String firstName;

	@Column(nullable = true, length = 1)
	@Size(max = 1)
	private String middleInitial;

	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birth_date")
	private Date birthDate;

	@Column(length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String primaryEmailAddress;

	@Column(length = 100)
	@Size(max = 100)
	private String secondaryEmailAddress;

	@Column(length = 25)
	@Size(max = 25)
	private String username;

	@Column(length = 25)
	@Size(max = 25)
	private String homePhone;

	@Column(length = 25)
	@Size(max = 25)
	private String workPhone;

	@Column(length = 25)
	@Size(max = 25)
	private String cellPhone;

	@Column(length = 50, name = "address_line_1")
	@Size(max = 50)
	private String addressLine1;

	@Column(length = 50, name = "address_line_2")
	@Size(max = 50)
	private String addressLine2;

	@Column(length = 50)
	@Size(max = 50)
	private String city;

	@Column(length = 2)
	@Size(max = 2)
	private String state;

	@Column(length = 10)
	@Size(max = 10)
	private String zipCode;

	@Column(length = 100)
	@Size(max = 100)
	private String photoUrl;

	@Column(length = 50)
	@Size(max = 50)
	private String schoolId;

	@Nullable()
	/**
	 * Marks the user account able to authenticate in the system.
	 * 
	 * Usually only marked false for former administrators, counselors, 
	 * and non-students who no longer use the system anymore.
	 */
	private boolean enabled;

	@Nullable()
	@ManyToOne()
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "person_demographics_id", unique = true, nullable = true)
	private PersonDemographics demographics;

	@Nullable()
	@ManyToOne()
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "person_education_goal_id", unique = true, nullable = true)
	private PersonEducationGoal educationGoal;

	@Nullable()
	@ManyToOne()
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "person_education_plan_id", unique = true, nullable = true)
	private PersonEducationPlan educationPlan;

	/**
	 * Education levels. Changes to this set are persisted.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	private Set<PersonEducationLevel> educationLevels;

	/**
	 * Funding sources. Changes to this set are persisted.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	private Set<PersonFundingSource> fundingSources;

	/**
	 * Challenges. Changes to this set are persisted.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	private Set<PersonChallenge> challenges;

	public Person() {
	}

	public Person(UUID id) {
		super(id);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}

	public void setSecondaryEmailAddress(String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public PersonDemographics getDemographics() {
		return demographics;
	}

	public void setDemographics(PersonDemographics demographics) {
		this.demographics = demographics;
	}

	public PersonEducationGoal getEducationGoal() {
		return educationGoal;
	}

	public void setEducationGoal(PersonEducationGoal educationGoal) {
		this.educationGoal = educationGoal;
	}

	public PersonEducationPlan getEducationPlan() {
		return educationPlan;
	}

	public void setEducationPlan(PersonEducationPlan educationPlan) {
		this.educationPlan = educationPlan;
	}

	public Set<PersonEducationLevel> getEducationLevels() {
		return educationLevels;
	}

	public void setEducationLevels(Set<PersonEducationLevel> educationLevels) {
		this.educationLevels = educationLevels;
	}

	public Set<PersonFundingSource> getFundingSources() {
		return fundingSources;
	}

	public void setFundingSources(Set<PersonFundingSource> fundingSources) {
		this.fundingSources = fundingSources;
	}

	public Set<PersonChallenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(Set<PersonChallenge> challenges) {
		this.challenges = challenges;
	}

}
