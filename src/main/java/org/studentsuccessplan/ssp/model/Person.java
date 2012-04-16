package org.studentsuccessplan.ssp.model;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;
import org.studentsuccessplan.ssp.model.tool.PersonTool;

import com.google.common.collect.Sets;

/**
 * A Person entity.
 * 
 * Usually represents either a user of the backend system, or a student.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person extends Auditable implements Serializable {

	private static final long serialVersionUID = 4122282021549627683L;

	/**
	 * Static, super administrator account identifier. Only used by IT and
	 * support staff, never by students or users of the system.
	 */
	public static final UUID SYSTEM_ADMINISTRATOR_ID = UUID
			.fromString("58ba5ee3-734e-4ae9-b9c5-943774b4de41");

	/**
	 * First name; required.
	 * 
	 * Maximum length of 50.
	 */
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String firstName;

	/**
	 * Middle initial.
	 * 
	 * Optional; maximum length of 1.
	 */
	@Column(nullable = true, length = 1)
	@Size(max = 1)
	private String middleInitial;

	/**
	 * Last name; required.
	 * 
	 * Maximum length of 50.
	 */
	@Column(nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String lastName;

	/**
	 * Birth date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birth_date")
	@Past
	private Date birthDate;

	/**
	 * Primary e-mail address; required.
	 * 
	 * Can not be null or empty. Maximum length of 100 characters.
	 */
	@Column(length = 100)
	@NotNull
	@NotEmpty
	@Size(max = 100)
	private String primaryEmailAddress;

	/**
	 * Secondary e-mail address.
	 * 
	 * Optional. Maximum length of 100 characters.
	 */
	@Column(length = 100)
	@Size(max = 100)
	private String secondaryEmailAddress;

	/**
	 * User name.
	 * 
	 * Maximum length of 25.
	 */
	@Column(length = 25)
	@Size(max = 25)
	private String username;

	/**
	 * User Id Secondary Id for used to identify the user in secondary systems
	 * like ldap.
	 * 
	 * Maximum length of 25.
	 */
	@Column(length = 25)
	@Size(max = 25)
	private String userId;

	/**
	 * Home phone number.
	 * 
	 * Maximum length of 25.
	 */
	@Column(length = 25)
	@Size(max = 25)
	private String homePhone;

	/**
	 * Work phone number.
	 * 
	 * Maximum length of 25.
	 */
	@Column(length = 25)
	@Size(max = 25)
	private String workPhone;

	/**
	 * Cell (mobile) phone number.
	 * 
	 * Maximum length of 25.
	 */
	@Column(length = 25)
	@Size(max = 25)
	private String cellPhone;

	/**
	 * Address line 1.
	 * 
	 * Maximum length of 50.
	 */
	@Column(length = 50, name = "address_line_1")
	@Size(max = 50)
	private String addressLine1;

	/**
	 * Address line 2.
	 * 
	 * Maximum length of 50.
	 */
	@Column(length = 50, name = "address_line_2")
	@Size(max = 50)
	private String addressLine2;

	/**
	 * City.
	 * 
	 * Maximum length of 50.
	 */
	@Column(length = 50)
	@Size(max = 50)
	private String city;

	/**
	 * State code (abbreviated to 2 characters).
	 * 
	 * Maximum length of 2.
	 */
	@Column(length = 2)
	@Size(max = 2)
	private String state;

	/**
	 * ZIP/postal code.
	 * 
	 * Maximum length of 10.
	 */
	@Column(length = 10)
	@Size(max = 10)
	private String zipCode;

	/**
	 * Photo URL.
	 * 
	 * Maximum length of 100.
	 */
	@Column(length = 100)
	@Size(max = 100)
	private String photoUrl;

	/**
	 * School identifier.
	 * 
	 * Maximum length of 50.
	 */
	@Column(length = 50)
	@Size(max = 50)
	private String schoolId;

	/**
	 * Marks the user account able to authenticate in the system.
	 * 
	 * Usually only marked false for former administrators, counselors, and
	 * non-students who no longer use the system anymore.
	 */
	@Nullable()
	private boolean enabled;

	/**
	 * Demographics about a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable()
	@ManyToOne()
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "person_demographics_id", unique = true, nullable = true)
	private PersonDemographics demographics;

	/**
	 * Education goal for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable()
	@ManyToOne()
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "person_education_goal_id", unique = true, nullable = true)
	private PersonEducationGoal educationGoal;

	/**
	 * Education plan for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable()
	@ManyToOne()
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "person_education_plan_id", unique = true, nullable = true)
	private PersonEducationPlan educationPlan;

	/**
	 * Education Levels for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	@Cascade(value = CascadeType.ALL)
	private Set<PersonEducationLevel> educationLevels;

	/**
	 * Any funding sources for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	@Cascade(CascadeType.ALL)
	private Set<PersonFundingSource> fundingSources;

	/**
	 * Any Challenges for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable()
	@OneToMany(mappedBy = "person")
	@Cascade(CascadeType.ALL)
	private Set<PersonChallenge> challenges;

	@Nullable()
	@OneToMany(mappedBy = "person")
	@Cascade(value = CascadeType.ALL)
	private Set<PersonTool> tools;

	@Nullable()
	@OneToMany(mappedBy = "person")
	@Cascade(value = CascadeType.ALL)
	private Set<PersonConfidentialityDisclosureAgreement> confidentialityDisclosureAgreements;

	@Nullable()
	@OneToMany(mappedBy = "person")
	@Cascade(value = CascadeType.ALL)
	private Set<Task> tasks;

	@Nullable()
	@OneToMany(mappedBy = "person")
	@Cascade(value = CascadeType.ALL)
	private Set<CustomTask> customTasks;

	/**
	 * Strengths
	 * 
	 * Maximum length of 4000.
	 */
	@Column(length = 4000)
	@Size(max = 4000)
	private String strengths;

	@Transient
	public Set<AbstractTask> getTasksAndCustomTasks() {
		Set<AbstractTask> tasks = Sets.newHashSet();
		tasks.addAll(getTasks());
		tasks.addAll(getCustomTasks());
		return tasks;
	}

	/**
	 * Initialize a Person.
	 * 
	 * Does not generated an ID, but does initialize empty sets.
	 */
	public Person() {
		super();
		challenges = new HashSet<PersonChallenge>();
		fundingSources = new HashSet<PersonFundingSource>();
		educationLevels = new HashSet<PersonEducationLevel>();
	}

	/**
	 * Initialize a Person with the specified ID and empty sets.
	 * 
	 * @param id
	 *            Identifier
	 */
	public Person(UUID id) {
		super(id);
		challenges = new HashSet<PersonChallenge>();
		fundingSources = new HashSet<PersonFundingSource>();
		educationLevels = new HashSet<PersonEducationLevel>();
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getEmailAddressWithName() {
		return getFullName() + " <" + primaryEmailAddress + ">";
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

	public Set<PersonTool> getTools() {
		return tools;
	}

	public void setTools(Set<PersonTool> tools) {
		this.tools = tools;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<PersonConfidentialityDisclosureAgreement> getConfidentialityDisclosureAgreements() {
		return confidentialityDisclosureAgreements;
	}

	public void setConfidentialityDisclosureAgreements(
			Set<PersonConfidentialityDisclosureAgreement> confidentialityDisclosureAgreements) {
		this.confidentialityDisclosureAgreements = confidentialityDisclosureAgreements;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public Set<CustomTask> getCustomTasks() {
		return customTasks;
	}

	public void setCustomTasks(Set<CustomTask> customTasks) {
		this.customTasks = customTasks;
	}

	/**
	 * @return the strengths
	 */
	public String getStrengths() {
		return strengths;
	}

	/**
	 * @param strengths
	 *            the strengths to set
	 */
	public void setStrengths(String strengths) {
		this.strengths = strengths;
	}
}
