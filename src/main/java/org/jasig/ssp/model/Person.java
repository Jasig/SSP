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

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.model.tool.PersonTool;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * A Person entity.
 * 
 * Usually represents either a user of the back-end system, or a student.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="person_class",
		discriminatorType= DiscriminatorType.STRING
)
@DiscriminatorValue("user")
public class Person extends AbstractAuditable implements Auditable { // NOPMD

	private static final long serialVersionUID = 4159658337332259029L;

	private static final String DATABASE_TABLE_NAME = "person";

	/**
	 * Compares names only. I.e. two {@link Person}s with the same first, last,
	 * and middle names are considered equivalent, even if they represent
	 * different people. This is often problematic when using this
	 * as the comparator in a {@link java.util.SortedSet} or
	 * {@link java.util.SortedMap}. Consider {@link PersonNameAndIdComparator}
	 * for cases when you really want to sort a list of {@link Person}s and
	 * not inadvertently drop entries with duplicate names.
	 */
	public static class PersonNameComparator implements Comparator<Person> {
		@Override
		public int compare(Person o1, Person o2) {
			return nameOf(o1).compareTo(nameOf(o2));
		}

		public int compare(Person p, CoachCaseloadRecordCountForProgramStatus c) {
			return nameOf(p).compareTo(nameOf(c));
		}

		String nameOf(Person p) {
			return new StringBuilder()
					.append(StringUtils.trimToEmpty(p.getLastName()))
					.append(StringUtils.trimToEmpty(p.getFirstName()))
					.append(StringUtils.trimToEmpty(p.getMiddleName()))
					.toString();
		}

		String nameOf(CoachCaseloadRecordCountForProgramStatus coachStatusCount) {
			return new StringBuilder()
					.append(StringUtils.trimToEmpty(coachStatusCount.getCoachLastName()))
					.append(StringUtils.trimToEmpty(coachStatusCount.getCoachFirstName()))
					.append(StringUtils.trimToEmpty(coachStatusCount.getCoachMiddleName()))
					.toString();
		}
	}

	public static final PersonNameComparator PERSON_NAME_COMPARATOR =
			new PersonNameComparator();

	public static class PersonNameAndIdComparator implements Comparator<Person> {

		@Override
		public int compare(Person o1, Person o2) {
			int nameCompare = PERSON_NAME_COMPARATOR.compare(o1, o2);
			if ( nameCompare != 0 ) {
				return nameCompare;
			}
			return compareUUIDs(o1.getId(), o2.getId());
		}

		public int compare(Person p, CoachCaseloadRecordCountForProgramStatus c) {
			int nameCompare = PERSON_NAME_COMPARATOR.compare(p, c);
			if ( nameCompare != 0 ) {
				return nameCompare;
			}
			return compareUUIDs(p.getId(), c.getCoachId());
		}

		private int compareUUIDs(UUID uuid1, UUID uuid2) {
			if ( uuid1 == uuid2 ) {
				return 0;
			}
			if ( uuid1 == null ) {
				return -1;
			}
			if ( uuid2 == null ) {
				return 1;
			}
			return uuid1.compareTo(uuid2);
		}
	}

	public static final PersonNameAndIdComparator PERSON_NAME_AND_ID_COMPARATOR =
			new PersonNameAndIdComparator();

	/**
	 * Static, super administrator account identifier. Only used by IT and
	 * support staff, never by students or users of the system.
	 */
	public static final UUID SYSTEM_ADMINISTRATOR_ID = UUID
			.fromString("58ba5ee3-734e-4ae9-b9c5-943774b4de41");

	/**
	 * Static, "external" account identifier. Only used in Auditable properties
	 * when data is imported from the views from external data sources.
	 */
	public static final UUID EXTERNAL_DATA_ID = UUID
			.fromString("a9a337fc-c35e-4bcc-91a8-06de3b6b441e");

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
	@Column(nullable = true, length = 50)
	@Size(max = 50)
	private String middleName;

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
	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date")
	private Date birthDate;

	/**
	 * Primary e-mail address; required.
	 * 
	 * Maximum length of 100 characters.
	 */
	@Column(length = 100)
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
	 * User name. Used to identify the user in secondary systems like LDAP.
	 * 
	 * Maximum length of 50.
	 */
	@NotNull
	@NotEmpty
	@Column(length = 50)
	@Size(max = 50)
	private String username;

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

	@Nullable
	private Boolean nonLocalAddress;	
	
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

	@Nullable
	private Boolean alternateAddressInUse;	
	
	/**
	 * Alternate Address line 1.
	 * 
	 * Maximum length of 50.
	 */
	@Column(length = 50, name = "alternate_address_line_1")
	@Size(max = 50)
	private String alternateAddressLine1;

	/**
	 * Alternate Address line 2.
	 * 
	 * Maximum length of 50.
	 */
	@Column(length = 50, name = "alternate_address_line_2")
	@Size(max = 50)
	private String alternateAddressLine2;

	/**
	 * Alternate Address City.
	 * 
	 * Maximum length of 50.
	 */
	@Column(length = 50, name = "alternate_address_city")
	@Size(max = 50)
	private String alternateAddressCity;

	/**
	 * Alternate Address State code (abbreviated to 2 characters).
	 * 
	 * Maximum length of 2.
	 */
	@Column(length = 2, name = "alternate_address_state")
	@Size(max = 2)
	private String alternateAddressState;

	/**
	 * Alternate Address ZIP/postal code.
	 * 
	 * Maximum length of 10.
	 */
	@Column(length = 10, name = "alternate_address_zip_code")
	@Size(max = 10)
	private String alternateAddressZipCode;	

	/**
	 * Alternate Address Country.
	 * 
	 * Maximum length of 50.
	 */
	@Column(length = 50, name = "alternate_address_country")
	@Size(max = 50)
	private String alternateAddressCountry;	
	
	/**
	 * Photo URL.
	 * 
	 * Maximum length of 250.
	 */
	@Nullable
	@Column(length = 250)
	@Size(max = 250)
	private String photoUrl;

	/**
	 * School identifier for the student. A.k.a. Student ID.
	 * 
	 * Maximum length of 50.
	 */
	@NotNull
	@NotEmpty
	@Column(length = 50)
	@Size(max = 50)
	private String schoolId;

	/**
	 * Marks the user account able to authenticate in the system.
	 * 
	 * Usually only marked false for former administrators, counselors, and
	 * non-students who no longer use the system anymore.
	 */
	@Nullable
	private Boolean enabled = true;

	@Nullable
	private Boolean abilityToBenefit;

	@Nullable
	@Column(length = 20)
	@Size(max = 20)
	private String anticipatedStartTerm;

	@Nullable
	private Integer anticipatedStartYear;

	@Column(length = 20)
	@Size(max = 20)
	private String actualStartTerm;

	@Nullable
	private Integer actualStartYear;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date studentIntakeRequestDate;
	
	@Column(length = 50)
	@Size(max = 50)
	@Nullable
	private String residencyCounty;
	
	@Column(length = 1, name = "f1_status")
	@Size(max = 1)
	@Nullable
	private String f1Status;

	/**
	 * Set when last someone completed the student intake tool for this person.
	 */
	private Date studentIntakeCompleteDate;

	/**
	 * Strengths
	 * 
	 * Maximum length of 4000.
	 */
	@Column(length = 4000)
	@Size(max = 4000)
	private String strengths;

	/**
	 * Information/details about a staff member.
	 * 
	 * Should be null for students.
	 */
	@Nullable
	@ManyToOne
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "person_staff_details_id", unique = true, nullable = true)
	private PersonStaffDetails staffDetails;

	/**
	 * Demographics about a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@ManyToOne
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "person_demographics_id", unique = true, nullable = true)
	private PersonDemographics demographics;

	/**
	 * Education goal for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@ManyToOne
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "person_education_goal_id", unique = true, nullable = true)
	private PersonEducationGoal educationGoal;

	/**
	 * Education plan for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@ManyToOne
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "person_education_plan_id", unique = true, nullable = true)
	private PersonEducationPlan educationPlan;	
	
	/**
	 * Education Levels for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<PersonEducationLevel> educationLevels;

	/**
	 * Any funding sources for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private Set<PersonFundingSource> fundingSources;

	/**
	 * Any Challenges for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private Set<PersonChallenge> challenges;

	/**
	 * Disability record for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@ManyToOne
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "person_disability_id", unique = true, nullable = true)
	private PersonDisability disability;	
	
	/**
	 * Any Disability Agencies for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private Set<PersonDisabilityAgency> disabilityAgencies;	

	/**
	 * Any Disability Types for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private Set<PersonDisabilityType> disabilityTypes;	

	/**
	 * Any Disability Accommodations for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private Set<PersonDisabilityAccommodation> disabilityAccommodations;

	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<PersonTool> tools;

	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<PersonConfidentialityDisclosureAgreement> confidentialityDisclosureAgreements;

	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<Task> tasks;

	@Nullable
	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "coach_id", nullable = true)
	private Person coach;
	
	@Nullable
	@OneToMany(fetch = FetchType.LAZY, mappedBy="person")
	@Cascade({ CascadeType.LOCK })
	private List<Plan> plans;

	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<PersonServiceReason> serviceReasons;

	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<PersonSpecialServiceGroup> specialServiceGroups;

	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<PersonReferralSource> referralSources;

	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME, orphanRemoval = true)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<PersonProgramStatus> programStatuses;

	@Nullable
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_type_id", nullable = true)
	private StudentType studentType;

	@Nullable
	@OneToMany(mappedBy = DATABASE_TABLE_NAME)
	@Cascade(value = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.SAVE_UPDATE })
	private Set<EarlyAlert> earlyAlerts;

	@Transient
	private RegistrationStatusByTerm currentRegistrationStatus;
	
	@Transient
	private List<RegistrationStatusByTerm> currentAndFutureRegistrationStatuses;	

	@Transient
	private Number activeAlertsCount;

	@Transient
	private Number closedAlertsCount;


	/**
	 * Initialize a Person.
	 * 
	 * Does not generated an ID, but does initialize empty sets.
	 */
	public Person() {
		super();
		initializeSets();
	}

	/**
	 * Initialize a Person with the specified ID and empty sets.
	 * 
	 * @param id
	 *            Identifier
	 */
	public Person(final UUID id) {
		super();
		setId(id);
		initializeSets();

	}

	private void initializeSets() {
		challenges = Sets.newHashSet();
		disabilityAgencies = Sets.newHashSet();
		disabilityAccommodations = Sets.newHashSet();
		disabilityTypes = Sets.newHashSet();
		fundingSources = Sets.newHashSet();
		educationLevels = Sets.newHashSet();
		tools = Sets.newHashSet();
		confidentialityDisclosureAgreements = Sets.newHashSet();
		tasks = Sets.newHashSet();
		serviceReasons = Sets.newHashSet();
		specialServiceGroups = Sets.newHashSet();
		referralSources = Sets.newHashSet();
		programStatuses = Sets.newHashSet();
		earlyAlerts = Sets.newHashSet();
		plans = Lists.newArrayList();
	}

	/**
	 * Gets the full name
	 * 
	 * @return The first name and last name concatenated with a space in
	 *         between.
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Gets the e-mail address with the full name in the standard full e-mail
	 * address syntax.
	 * 
	 * @return The e-mail address with the full name.
	 */
	public String getEmailAddressWithName() {
		return getFullName() + " <" + primaryEmailAddress + ">";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(@NotNull final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(@NotNull final String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate == null ? null : new Date(birthDate.getTime());
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate == null ? null : new Date(
				birthDate.getTime());
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(@NotNull final String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}

	public void setSecondaryEmailAddress(final String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(@NotNull @NotEmpty final String username) {
		this.username = username;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(final String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(final String workPhone) {
		this.workPhone = workPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(final String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Boolean getNonLocalAddress() {
		return nonLocalAddress;
	}

	public void setNonLocalAddress(final Boolean nonLocalAddress) {
		this.nonLocalAddress = nonLocalAddress;
	}	
	
	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(final String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(final String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public Boolean getAlternateAddressInUse() {
		return alternateAddressInUse;
	}

	public void setAlternateAddressInUse(final Boolean alternateAddressInUse) {
		this.alternateAddressInUse = alternateAddressInUse;
	}
	
	public String getAlternateAddressLine1() {
		return alternateAddressLine1;
	}

	public void setAlternateAddressLine1(final String alternateAddressLine1) {
		this.alternateAddressLine1 = alternateAddressLine1;
	}

	public String getAlternateAddressLine2() {
		return alternateAddressLine2;
	}

	public void setAlternateAddressLine2(final String alternateAddressLine2) {
		this.alternateAddressLine2 = alternateAddressLine2;
	}

	public String getAlternateAddressCity() {
		return alternateAddressCity;
	}

	public void setAlternateAddressCity(final String alternateAddressCity) {
		this.alternateAddressCity = alternateAddressCity;
	}

	public String getAlternateAddressState() {
		return alternateAddressState;
	}

	public void setAlternateAddressState(final String alternateAddressState) {
		this.alternateAddressState = alternateAddressState;
	}

	public String getAlternateAddressZipCode() {
		return alternateAddressZipCode;
	}

	public void setAlternateAddressZipCode(final String alternateAddressZipCode) {
		this.alternateAddressZipCode = alternateAddressZipCode;
	}	

	public String getAlternateAddressCountry() {
		return alternateAddressCountry;
	}

	public void setAlternateAddressCountry(final String alternateAddressCountry) {
		this.alternateAddressCountry = alternateAddressCountry;
	}		
	
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(final String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * Gets the SchoolID (a.k.a. Student ID given by the school)
	 * 
	 * @return the SchoolID
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * Sets the SchoolID (a.k.a. Student ID given by the school)
	 * 
	 * @param schoolId
	 *            the SchoolID (a.k.a. Student ID given by the school);
	 *            required; maximum length of 50 characters
	 */
	public void setSchoolId(@NotNull final String schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * Gives the database value of the column, which may be null.
	 * 
	 * @return
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	
	/**
	 * Indicates whether the account is disabled according to the business rules 
	 * of SSP.
	 * 
	 * @return
	 */
	public boolean isDisabled() {
		return enabled != null && enabled.equals(Boolean.FALSE);
	}

	public void setEnabled(final Boolean enabled) {
		this.enabled = enabled;
	}

	public PersonStaffDetails getStaffDetails() {
		return staffDetails;
	}

	public void setStaffDetails(final PersonStaffDetails staffDetails) {
		this.staffDetails = staffDetails;
	}

	public PersonDemographics getDemographics() {
		return demographics;
	}

	public void setDemographics(final PersonDemographics demographics) {
		this.demographics = demographics;
	}

	public PersonDisability getDisability() {
		return disability;
	}

	public void setDisability(final PersonDisability disability) {
		this.disability = disability;
	}	
	
	public PersonEducationGoal getEducationGoal() {
		return educationGoal;
	}

	public void setEducationGoal(final PersonEducationGoal educationGoal) {
		this.educationGoal = educationGoal;
	}

	public PersonEducationPlan getEducationPlan() {
		return educationPlan;
	}

	public void setEducationPlan(final PersonEducationPlan educationPlan) {
		this.educationPlan = educationPlan;
	}

	public Set<PersonEducationLevel> getEducationLevels() {
		return educationLevels;
	}

	public void setEducationLevels(
			final Set<PersonEducationLevel> educationLevels) {
		this.educationLevels = educationLevels;
	}

	public Set<PersonFundingSource> getFundingSources() {
		return fundingSources;
	}

	public void setFundingSources(final Set<PersonFundingSource> fundingSources) {
		this.fundingSources = fundingSources;
	}

	public Set<PersonChallenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(final Set<PersonChallenge> challenges) {
		this.challenges = challenges;
	}

	public Set<PersonDisabilityAgency> getDisabilityAgencies() {
		return disabilityAgencies;
	}

	public void setDisabilityAgencies(final Set<PersonDisabilityAgency> disabilityAgencies) {
		this.disabilityAgencies = disabilityAgencies;
	}

	public Set<PersonDisabilityType> getDisabilityTypes() {
		return disabilityTypes;
	}

	public void setDisabilityTypes(final Set<PersonDisabilityType> disabilityTypes) {
		this.disabilityTypes = disabilityTypes;
	}

	public Set<PersonDisabilityAccommodation> getDisabilityAccommodations() {
		return disabilityAccommodations;
	}

	public void setDisabilityAccommodations(
			final Set<PersonDisabilityAccommodation> disabilityAccommodations) {
		this.disabilityAccommodations = disabilityAccommodations;
	}	
	
	public Set<PersonTool> getTools() {
		return tools;
	}

	public void setTools(final Set<PersonTool> tools) {
		this.tools = tools;
	}

	public Set<PersonConfidentialityDisclosureAgreement> getConfidentialityDisclosureAgreements() {
		return confidentialityDisclosureAgreements;
	}

	public void setConfidentialityDisclosureAgreements(
			final Set<PersonConfidentialityDisclosureAgreement> confidentialityDisclosureAgreements) {
		this.confidentialityDisclosureAgreements = confidentialityDisclosureAgreements;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(final Set<Task> tasks) {
		this.tasks = tasks;
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
	public void setStrengths(final String strengths) {
		this.strengths = strengths;
	}

	public Person getCoach() {
		return coach;
	}

	public void setCoach(final Person coach) {
		this.coach = coach;
	}

	public Set<PersonServiceReason> getServiceReasons() {
		return serviceReasons;
	}

	public void setServiceReasons(final Set<PersonServiceReason> serviceReasons) {
		this.serviceReasons = serviceReasons;
	}

	public Set<PersonSpecialServiceGroup> getSpecialServiceGroups() {
		return specialServiceGroups;
	}

	public void setSpecialServiceGroups(
			final Set<PersonSpecialServiceGroup> specialServiceGroups) {
		this.specialServiceGroups = specialServiceGroups;
	}

	public Set<PersonReferralSource> getReferralSources() {
		return referralSources;
	}

	public void setReferralSources(
			final Set<PersonReferralSource> referralSources) {
		this.referralSources = referralSources;
	}

	public Boolean getAbilityToBenefit() {
		return abilityToBenefit;
	}

	public void setAbilityToBenefit(final Boolean abilityToBenefit) {
		this.abilityToBenefit = abilityToBenefit;
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

	public String getActualStartTerm() {
		return actualStartTerm;
	}

	public void setActualStartTerm(final String actualStartTerm) {
		this.actualStartTerm = actualStartTerm;
	}

	public Integer getActualStartYear() {
		return actualStartYear;
	}

	public void setActualStartYear(final Integer actualStartYear) {
		this.actualStartYear = actualStartYear;
	}

	public Date getStudentIntakeRequestDate() {
		return studentIntakeRequestDate == null ? null : new Date(
				studentIntakeRequestDate.getTime());
	}

	public void setStudentIntakeRequestDate(final Date studentIntakeRequestDate) {
		this.studentIntakeRequestDate = studentIntakeRequestDate == null ? null
				: new Date(studentIntakeRequestDate.getTime());
	}

	public StudentType getStudentType() {
		return studentType;
	}

	public void setStudentType(final StudentType studentType) {
		this.studentType = studentType;
	}

	public Set<PersonProgramStatus> getProgramStatuses() {
		return programStatuses;
	}

	public String getCurrentProgramStatusName() {
		Set<PersonProgramStatus> programStatuses = getProgramStatuses();
		if ( programStatuses == null || programStatuses.isEmpty() ) {
			return null;
		}
		for ( PersonProgramStatus programStatus : programStatuses ) {
			if ( !(programStatus.isExpired()) ) {
				return programStatus.getProgramStatus().getName();
			}
		}
		return null;
	}

	public void setProgramStatuses(
			final Set<PersonProgramStatus> programStatuses) {
		this.programStatuses = programStatuses;
	}

	public Date getStudentIntakeCompleteDate() {
		return studentIntakeCompleteDate == null ? null : new Date(
				studentIntakeCompleteDate.getTime());
	}

	public void setStudentIntakeCompleteDate(
			final Date studentIntakeCompleteDate) {
		this.studentIntakeCompleteDate = studentIntakeCompleteDate == null ? null
				: new Date(studentIntakeCompleteDate.getTime());
	}

	public Set<EarlyAlert> getEarlyAlerts() {
		return earlyAlerts;
	}

	public void setEarlyAlerts(final Set<EarlyAlert> earlyAlerts) {
		this.earlyAlerts = earlyAlerts;
	}

	public RegistrationStatusByTerm getCurrentRegistrationStatus() {
		return currentRegistrationStatus;
	}

	public void setCurrentRegistrationStatus(
			final RegistrationStatusByTerm currentRegistrationStatus) {
		this.currentRegistrationStatus = currentRegistrationStatus;
	}

	public Number getActiveAlertsCount() {
		return activeAlertsCount;
	}

	public void setActiveAlertsCount(Number activeAlertsCount) {
		this.activeAlertsCount = activeAlertsCount;
	}

	public Number getClosedAlertsCount() {
		return closedAlertsCount;
	}

	public void setClosedAlertsCount(Number closedAlertsCount) {
		this.closedAlertsCount = closedAlertsCount;
	}

	public String getNullSafeOfficeLocation() {
		return getStaffDetails() == null ? null
				: getStaffDetails().getOfficeLocation();
	}

	public String getNullSafeDepartmentName() {
		return getStaffDetails() == null ? null
				: getStaffDetails().getDepartmentName();
	}

	@Override
	protected int hashPrime() {
		return 3;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:25 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// Person
		result *= hashField("firstName", firstName);
		result *= hashField("middleName", middleName);
		result *= hashField("lastName", lastName);
		result *= hashField("birthDate", birthDate);
		result *= hashField("primaryEmailAddress", primaryEmailAddress);
		result *= hashField("secondaryEmailAddress", secondaryEmailAddress);
		result *= hashField("primaryEmailAddress", primaryEmailAddress);
		result *= hashField("homePhone", homePhone);
		result *= hashField("workPhone", workPhone);
		result *= hashField("cellPhone", cellPhone);
		result *= nonLocalAddress == null ? 2
				: (nonLocalAddress ? 5 : 3);
		result *= hashField("addressLine1", addressLine1);
		result *= hashField("addressLine2", addressLine2);
		result *= hashField("city", city);
		result *= hashField("state", state);
		result *= hashField("zipCode", zipCode);
		result *= alternateAddressInUse == null ? 7
				: (alternateAddressInUse ? 13 : 11);
		result *= hashField("alternateAddressLine1", alternateAddressLine1);
		result *= hashField("alternateAddressLine2", alternateAddressLine2);
		result *= hashField("alternateAddressCity", alternateAddressCity);
		result *= hashField("alternateAddressState", alternateAddressState);
		result *= hashField("alternateAddressZipCode", alternateAddressZipCode);
		result *= hashField("alternateAddressCountry", alternateAddressCountry);
		result *= hashField("photoUrl", photoUrl);
		result *= hashField("schoolId", schoolId);
		result *= hashField("username", username);
		result *= hashField("strengths", strengths);
		result *= hashField("coach", coach);
		// result *= hashField("studentType", studentType);
		result *= hashField("anticipatedStartTerm", anticipatedStartTerm);
		result *= hashField("anticipatedStartYear", anticipatedStartYear);
		// result *= hashField("actualStartTerm", actualStartTerm);
		result *= hashField("actualStartYear", actualStartYear);
		result *= enabled == null ? "enabled".hashCode() : (enabled ? 19 : 17);
		result *= abilityToBenefit == null ? "abilityToBenefit".hashCode()
				: (abilityToBenefit ? 29 : 23);
		result *= hashField("studentIntakeRequestDate",
				studentIntakeRequestDate);
		result *= hashField("studentIntakeCompleteDate",
				studentIntakeCompleteDate);
		result *= hashField("residencyCounty",
					residencyCounty);		
		result *= hashField("f1Status",
				f1Status);

		// not all fields included. only the business or non-expensive set
		// fields are included in the hashCode

		return result;
	}

	@Override
	public String toString() {
		return "Name: \"" + firstName + " " + lastName + "\" Id: "
				+ super.toString();
	}

	public List<RegistrationStatusByTerm> getCurrentAndFutureRegistrationStatuses() {
		return currentAndFutureRegistrationStatuses;
	}

	public void setCurrentAndFutureRegistrationStatuses(
			List<RegistrationStatusByTerm> currentAndFutureRegistrationStatuses) {
		this.currentAndFutureRegistrationStatuses = currentAndFutureRegistrationStatuses;
	}

	/**
	 * @return the residencyCounty
	 */
	public String getResidencyCounty() {
		return residencyCounty;
	}

	/**
	 * @param residencyCounty the residencyCounty to set
	 */
	public void setResidencyCounty(String residencyCounty) {
		this.residencyCounty = residencyCounty;
	}

	/**
	 * @return the f1Status
	 */
	public String getF1Status() {
		return f1Status;
	}

	/**
	 * @param f1Status the f1Status to set
	 */
	public void setF1Status(String f1Status) {
		this.f1Status = f1Status;
	}

}