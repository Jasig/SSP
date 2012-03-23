package edu.sinclair.ssp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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

import edu.sinclair.ssp.model.tool.IntakeForm;

@Entity
@Table(schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person extends Auditable implements Serializable {

	private static final long serialVersionUID = 4122282021549627683L;

	public static final UUID SYSTEM_ADMINISTRATOR_ID = UUID
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
	 * Education levels.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	@Cascade(value = CascadeType.ALL)
	private Set<PersonEducationLevel> educationLevels;

	/**
	 * Funding sources.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	private Set<PersonFundingSource> fundingSources;

	/**
	 * Challenges.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	private Set<PersonChallenge> challenges;

	public Person() {
		challenges = new HashSet<PersonChallenge>();
		fundingSources = new HashSet<PersonFundingSource>();
		educationLevels = new HashSet<PersonEducationLevel>();
	}

	public Person(UUID id) {
		super(id);
		challenges = new HashSet<PersonChallenge>();
		fundingSources = new HashSet<PersonFundingSource>();
		educationLevels = new HashSet<PersonEducationLevel>();
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
		return birthDate == null ? null : new Date(birthDate.getTime());
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate == null ? null : new Date(
				birthDate.getTime());
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

	/**
	 * Overwrites simple properties with the parameter's properties. Does not
	 * include the Enabled property.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwriteWithEnabled(Person)
	 * @see overwriteWithEnabledAndCollections(Person)
	 */
	public void overwrite(Person source) {
		this.setFirstName(source.getFirstName());
		this.setMiddleInitial(source.getMiddleInitial());
		this.setLastName(source.getLastName());
		this.setBirthDate(source.getBirthDate());
		this.setPrimaryEmailAddress(source.getPrimaryEmailAddress());
		this.setSecondaryEmailAddress(source.getSecondaryEmailAddress());
		this.setUsername(source.getUsername());
		this.setHomePhone(source.getHomePhone());
		this.setWorkPhone(source.getWorkPhone());
		this.setCellPhone(source.getCellPhone());
		this.setAddressLine1(source.getAddressLine1());
		this.setAddressLine2(source.getAddressLine2());
		this.setCity(source.getCity());
		this.setState(source.getState());
		this.setZipCode(source.getZipCode());
		this.setPhotoUrl(source.getPhotoUrl());
		this.setSchoolId(source.getSchoolId());
	}

	/**
	 * Overwrites simple properties with the parameter's properties, including
	 * the Enabled property.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 * @see overwriteWithCollections(Person)
	 * @see overwriteWithEnabledAndCollections(Person)
	 */
	public void overwriteWithEnabled(Person source) {
		this.overwrite(source);

		this.setEnabled(source.isEnabled());
	}

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties, including the Enabled property.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 * @see overwriteWithCollections(Person)
	 * @see overwriteWithEnabledAndCollections(Person)
	 */
	public void overwriteWithEnabledAndCollections(Person source) {
		this.overwriteWithEnabled(source);
		this.overwriteWithCollections(source);
	}

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties, but not the Enabled property.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 * @see overwriteWithEnabledAndCollections(Person)
	 */
	public void overwriteWithCollections(Person source) {
		this.overwrite(source);

		this.getDemographics().overwriteWithCollections(
				source.getDemographics());
		this.getEducationGoal().overwriteWithCollections(
				source.getEducationGoal());
		this.getEducationPlan().overwriteWithCollections(
				source.getEducationPlan());

		this.overwriteWithCollectionsEducationLevels(
				source.getEducationLevels(), this);
		this.overwriteWithCollectionsFundingSources(source.getFundingSources(),
				this);
		this.overwriteWithCollectionsChallenges(source.getChallenges(), this);
	}

	/**
	 * Overwrites simple and collection properties with the parameter's
	 * properties, but not the Enabled property.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 * @see overwrite(Person)
	 * @see overwriteWithEnabledAndCollections(Person)
	 */
	public void overwriteWithCollections(IntakeForm source) {
		this.overwrite(source.getPerson());

		this.getDemographics().overwriteWithCollections(
				source.getPerson().getDemographics());
		this.getEducationGoal().overwriteWithCollections(
				source.getPerson().getEducationGoal());
		this.getEducationPlan().overwriteWithCollections(
				source.getPerson().getEducationPlan());

		this.overwriteWithCollectionsEducationLevels(
				source.getPersonEducationLevels(), this);
		this.overwriteWithCollectionsFundingSources(
				source.getPersonFundingSources(), this);
		this.overwriteWithCollectionsChallenges(source.getPersonChallenges(),
				this);
	}

	private void overwriteWithCollectionsEducationLevels(
			Set<PersonEducationLevel> source, Person person) {
		Set<PersonEducationLevel> thisSet = this.getEducationLevels();
		Set<PersonEducationLevel> toRemove = new HashSet<PersonEducationLevel>();

		// iterate through set to overwrite updating matching items
		for (PersonEducationLevel thisItem : thisSet) {
			if (!source.contains(thisItem)) {
				toRemove.add(thisItem);
			}

			for (PersonEducationLevel sourceItem : source) {
				if (sourceItem.equals(thisItem)) {
					thisItem.overwriteWithPersonAndEducationLevel(sourceItem,
							person);
				}
			}
		}

		// remove all items not in new set from the current set
		thisSet.removeAll(toRemove);

		// find all items that need added
		for (PersonEducationLevel sourceItem : source) {
			if (!thisSet.contains(sourceItem)) {
				PersonEducationLevel newItem = new PersonEducationLevel();
				newItem.overwriteWithPersonAndEducationLevel(sourceItem, person);
				thisSet.add(newItem);
			}
		}
	}

	private void overwriteWithCollectionsFundingSources(
			Set<PersonFundingSource> source, Person person) {
		Set<PersonFundingSource> thisSet = this.getFundingSources();
		Set<PersonFundingSource> toRemove = new HashSet<PersonFundingSource>();

		// iterate through set to overwrite updating matching items
		for (PersonFundingSource thisItem : thisSet) {
			if (!source.contains(thisItem)) {
				toRemove.add(thisItem);
			}

			for (PersonFundingSource sourceItem : source) {
				if (sourceItem.equals(thisItem)) {
					thisItem.overwriteWithPersonAndFundingSource(sourceItem,
							person);
				}
			}
		}

		// remove all items not in new set from the current set
		thisSet.removeAll(toRemove);

		// find all items that need added
		for (PersonFundingSource sourceItem : source) {
			if (!thisSet.contains(sourceItem)) {
				PersonFundingSource newItem = new PersonFundingSource();
				newItem.overwriteWithPersonAndFundingSource(sourceItem, person);
				thisSet.add(newItem);
			}
		}
	}

	private void overwriteWithCollectionsChallenges(
			Set<PersonChallenge> source, Person person) {
		Set<PersonChallenge> thisSet = this.getChallenges();
		Set<PersonChallenge> toRemove = new HashSet<PersonChallenge>();

		// iterate through set to overwrite updating matching items
		for (PersonChallenge thisItem : thisSet) {
			if (!source.contains(thisItem)) {
				toRemove.add(thisItem);
			}

			for (PersonChallenge sourceItem : source) {
				if (sourceItem.equals(thisItem)) {
					thisItem.overwriteWithPerson(sourceItem,
							person);
				}
			}
		}

		// remove all items not in new set from the current set
		thisSet.removeAll(toRemove);

		// find all items that need added
		for (PersonChallenge sourceItem : source) {
			if (!thisSet.contains(sourceItem)) {
				PersonChallenge newItem = new PersonChallenge();
				newItem.overwriteWithPerson(sourceItem, person);
				thisSet.add(newItem);
			}
		}
	}
}
