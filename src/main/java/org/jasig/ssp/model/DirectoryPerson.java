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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.external.AbstractExternalData;
import org.jasig.ssp.model.external.ExternalData;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.model.reference.StudentType;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class DirectoryPerson extends AbstractExternalData implements ExternalData, Serializable { // NOPMD


	/**
	 * 
	 */
	private static final long serialVersionUID = -7348180045637529279L;

	/**
	 * Compares names only. I.e. two {@link DirectoryPerson}s with the same first, last,
	 * and middle names are considered equivalent, even if they represent
	 * different people. This is often problematic when using this
	 * as the comparator in a {@link java.util.SortedSet} or
	 * {@link java.util.SortedMap}. Consider {@link PersonNameAndIdComparator}
	 * for cases when you really want to sort a list of {@link DirectoryPerson}s and
	 * not inadvertently drop entries with duplicate names.
	 */
	public static class PersonNameComparator implements Comparator<DirectoryPerson> {
		@Override
		public int compare(DirectoryPerson o1, DirectoryPerson o2) {
			return nameOf(o1).compareTo(nameOf(o2));
		}

		public int compare(DirectoryPerson p, CoachCaseloadRecordCountForProgramStatus c) {
			return nameOf(p).compareTo(nameOf(c));
		}

		String nameOf(DirectoryPerson p) {
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

	public static class PersonNameAndIdComparator implements Comparator<DirectoryPerson> {

		@Override
		public int compare(DirectoryPerson o1, DirectoryPerson o2) {
			int nameCompare = PERSON_NAME_COMPARATOR.compare(o1, o2);
			if ( nameCompare != 0 ) {
				return nameCompare;
			}
			return compareUUIDs(o1.getPersonId(), o2.getPersonId());
		}

		public int compare(DirectoryPerson p, CoachCaseloadRecordCountForProgramStatus c) {
			int nameCompare = PERSON_NAME_COMPARATOR.compare(p, c);
			if ( nameCompare != 0 ) {
				return nameCompare;
			}
			return compareUUIDs(p.getPersonId(), c.getCoachId());
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

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

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


	@Nullable
	@Type(type = "uuid-custom")
	private UUID coachId;
	
	@Nullable
	private String coachFirstName;
	
	@Nullable
	private String coachLastName;
	
	@Nullable
	@Type(type = "uuid-custom")
	private UUID personId;

	@Nullable
	private String studentTypeName;
	
	@Nullable
	private String programStatusName;

	@Nullable
	private Integer currentRegistrationStatus;

	@Nullable
	private Integer activeAlertsCount;

	@Nullable
	private Integer closedAlertsCount;
	
	@Nullable
	private String declaredMajor;
	
	@Nullable
	private BigDecimal creditHoursEarned;
	
	@Nullable
	private BigDecimal gradePointAverage;
	
	@Nullable
	private String sapStatusCode;
	
	@Nullable
	private Integer earlyAlertResponseDueCount;
	
	@Nullable
	private Integer earlyAlertResponseCurrentCount;
	

	/**
	 * Initialize a Person.
	 * 
	 * Does not generated an ID, but does initialize empty sets.
	 */
	public DirectoryPerson() {
		super();
	}

	/**
	 * Initialize a Person with the specified ID and empty sets.
	 * 
	 * @param id
	 *            Identifier
	 */
	public DirectoryPerson(final UUID id) {
		super();
		setId(id);
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
	
	public List<String> getEmailAddresses() {
		List<String> emailAddresses = new ArrayList<String>();
		if(StringUtils.isNotBlank(primaryEmailAddress)){
			emailAddresses.add(primaryEmailAddress);
		}
		if(StringUtils.isNotBlank(secondaryEmailAddress))
			emailAddresses.add(secondaryEmailAddress);
		return emailAddresses;
	}
	
	
	public Boolean hasEmailAddresses(){
		if(StringUtils.isNotBlank(primaryEmailAddress) || StringUtils.isNotBlank(secondaryEmailAddress))
			return true;
		return false;
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
		this.username = StringUtils.lowerCase(username);
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

	public UUID getCoachId() {
		return coachId;
	}

	public void setCoachId(final UUID coachId) {
		this.coachId = coachId;
	}

	public String getCoachFirstName() {
		return coachFirstName;
	}

	public void setCoachFirstName(String coachFirstName) {
		this.coachFirstName = coachFirstName;
	}

	public String getCoachLastName() {
		return coachLastName;
	}

	public void setCoachLastName(String coachLastName) {
		this.coachLastName = coachLastName;
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

	public String getStudentTypeName() {
		return studentTypeName;
	}

	public String getProgramStatusName() {
		return programStatusName;
	}

	public void setProgramStatusName(String programStatusName) {
		this.programStatusName = programStatusName;
	}

	public void setStudentTypeName(final String studentTypeName) {
		this.studentTypeName = studentTypeName;
	}


	public String getDeclaredMajor() {
		return declaredMajor;
	}

	public void setDeclaredMajor(String declaredMajor) {
		this.declaredMajor = declaredMajor;
	}

	public BigDecimal getCreditHoursEarned() {
		return creditHoursEarned;
	}

	public void setCreditHoursEarned(BigDecimal creditHoursEarned) {
		this.creditHoursEarned = creditHoursEarned;
	}

	public BigDecimal getGradePointAverage() {
		return gradePointAverage;
	}

	public void setGradePointAverage(BigDecimal gradePointAverage) {
		this.gradePointAverage = gradePointAverage;
	}

	public String getSapStatusCode() {
		return sapStatusCode;
	}

	public void setSapStatusCode(String sapStatusCode) {
		this.sapStatusCode = sapStatusCode;
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

	public Integer getCurrentRegistrationStatus() {
		return currentRegistrationStatus;
	}

	public void setCurrentRegistrationStatus(
			final Integer currentRegistrationStatus) {
		this.currentRegistrationStatus = currentRegistrationStatus;
	}

	public Number getActiveAlertsCount() {
		return activeAlertsCount;
	}

	public void setActiveAlertsCount(Integer activeAlertsCount) {
		this.activeAlertsCount = activeAlertsCount;
	}

	public Number getClosedAlertsCount() {
		return closedAlertsCount;
	}

	public void setClosedAlertsCount(Integer closedAlertsCount) {
		this.closedAlertsCount = closedAlertsCount;
	}



	protected int hashPrime() {
		return 3;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:25 PM
		int result = hashPrime();

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
		result *= hashField("coachId", coachId);
		// result *= hashField("studentType", studentType);
		result *= hashField("anticipatedStartTerm", anticipatedStartTerm);
		result *= hashField("anticipatedStartYear", anticipatedStartYear);
		// result *= hashField("actualStartTerm", actualStartTerm);
		result *= hashField("actualStartYear", actualStartYear);
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
	protected final int hashField(final String name, final UUID value) {
		return (value == null ? name.hashCode() : value.hashCode());
	}

	protected final int hashField(final String name, final ObjectStatus value) {
		return (value == null ? name.hashCode() : value.hashCode());
	}

	protected final int hashField(final String name, final String value) {
		return (StringUtils.isEmpty(value) ? name.hashCode() : value.hashCode());
	}

	protected final int hashField(final String name, final int value) {
		return (value == 0 ? name.hashCode() : value);
	}

	protected final int hashField(final String name, final Number value) {
		return (value == null ? name.hashCode() : value.hashCode());
	}
	
	protected final int hashField(final String name, final Date value) {
		return ((value == null) || (value.getTime() == 0) ? name.hashCode()
				: value
						.hashCode());
	}
	

	protected final int hashField(final String name, final Auditable value) {
		return ((value == null) || (value.getId() == null) ? name.hashCode()
				: value.getId().hashCode());
	}

	@Override
	public String toString() {
		return "Name: \"" + firstName + " " + lastName + "\" Id: "
				+ super.toString();
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