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
package org.jasig.ssp.transferobject; // NOPMD

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.PersonReferralSource;
import org.jasig.ssp.model.PersonServiceReason;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.model.reference.ServiceReason;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.transferobject.reference.ReferenceLiteTO;

import com.google.common.collect.Lists;
import org.jasig.ssp.util.collections.Pair;

/**
 * Person transfer object
 */
public class PersonTO // NOPMD
		extends AbstractAuditableTO<Person>
		implements TransferObject<Person> {

	@NotNull
	private String firstName;

	private String middleName;

	@NotNull
	private String lastName;

	private Date birthDate;

	private String primaryEmailAddress;

	private String secondaryEmailAddress;

	@NotNull
	private String username;

	private String homePhone;

	private String workPhone;

	private String cellPhone;

	@Nullable
	private Boolean nonLocalAddress;
	
	private String addressLine1;

	private String addressLine2;

	private String city;

	private String state;

	private String zipCode;
	
	@Nullable
	private Boolean alternateAddressInUse;

	private String alternateAddressLine1;

	private String alternateAddressLine2;

	private String alternateAddressCity;

	private String alternateAddressState;

	private String alternateAddressZipCode;
	
	private String alternateAddressCountry;
	
	private String photoUrl;

	private String schoolId;

	private Boolean enabled;

	private Date studentIntakeCompleteDate;

	private ReferenceLiteTO<StudentType> studentType;

	private CoachPersonLiteTO coach;

	private String strengths;

	@Nullable
	private Boolean abilityToBenefit;

	@Nullable
	@Size(max = 20)
	private String anticipatedStartTerm;

	@Nullable
	private Integer anticipatedStartYear;

	@Nullable
	@Size(max = 20)
	private String actualStartTerm;

	@Nullable
	private Integer actualStartYear;

	private Date studentIntakeRequestDate;

	private List<ReferenceLiteTO<SpecialServiceGroup>> specialServiceGroups;

	private List<ReferenceLiteTO<ReferralSource>> referralSources;

	private List<ReferenceLiteTO<ServiceReason>> serviceReasons;

	private List<ReferenceLiteTO<ConfidentialityLevel>> confidentialityLevels;

	private List<String> permissions;

	private String currentProgramStatusName;

	private boolean registeredForCurrentTerm;

	/**
	 * Empty constructor
	 */
	public PersonTO() {
		super();
	}

	/**
	 * Construct a transfer object from a related model instance
	 * 
	 * @param model
	 *            Initialize instance from the data in this model
	 */
	public PersonTO(final Person model) {
		super();
		from(model);
	}

	@Override
	public final void from(final Person model) { // NOPMD
		super.from(model);

		firstName = model.getFirstName();
		middleName = model.getMiddleName();
		lastName = model.getLastName();
		birthDate = model.getBirthDate();
		primaryEmailAddress = model.getPrimaryEmailAddress();
		secondaryEmailAddress = model.getSecondaryEmailAddress();
		username = model.getUsername();
		homePhone = model.getHomePhone();
		workPhone = model.getWorkPhone();
		cellPhone = model.getCellPhone();
		nonLocalAddress = model.getNonLocalAddress();
		addressLine1 = model.getAddressLine1();
		addressLine2 = model.getAddressLine2();
		city = model.getCity();
		state = model.getState();
		zipCode = model.getZipCode();
		alternateAddressInUse = model.getAlternateAddressInUse();
		alternateAddressLine1 = model.getAlternateAddressLine1();
		alternateAddressLine2 = model.getAlternateAddressLine2();
		alternateAddressCity = model.getAlternateAddressCity();
		alternateAddressState = model.getAlternateAddressState();
		alternateAddressZipCode = model.getAlternateAddressZipCode();
		alternateAddressCountry = model.getAlternateAddressCountry();
		photoUrl = model.getPhotoUrl();
		schoolId = model.getSchoolId();
		enabled = !(model.isDisabled());
		studentIntakeCompleteDate = model.getStudentIntakeCompleteDate();

		final Person coachPerson = model.getCoach();
		if (coachPerson == null) {
			coach = null; // NOPMD
		} else {
			coach = new CoachPersonLiteTO(coachPerson.getId(),
					coachPerson.getFirstName(), coachPerson.getLastName(),
					coachPerson.getPrimaryEmailAddress(),
					coachPerson.getNullSafeOfficeLocation(),
					coachPerson.getNullSafeDepartmentName(),
					coachPerson.getWorkPhone());
		}

		strengths = model.getStrengths();
		abilityToBenefit = model.getAbilityToBenefit();
		anticipatedStartTerm = model.getAnticipatedStartTerm();
		anticipatedStartYear = model.getAnticipatedStartYear();
		actualStartTerm = model.getActualStartTerm();
		actualStartYear = model.getActualStartYear();
		studentIntakeRequestDate = model.getStudentIntakeRequestDate();
		studentType = model.getStudentType() == null ? null
				: new ReferenceLiteTO<StudentType>(model.getStudentType());

		if ((null != model.getSpecialServiceGroups())
				&& !(model.getSpecialServiceGroups().isEmpty())) {
			final List<Pair<SpecialServiceGroup, ObjectStatus>>
					specialServiceGroupsFromModel = Lists.newArrayList();
			for (final PersonSpecialServiceGroup pssg : model
					.getSpecialServiceGroups()) {
				Pair<SpecialServiceGroup, ObjectStatus> statusedSsg =
						new Pair<SpecialServiceGroup, ObjectStatus>(
								pssg.getSpecialServiceGroup(),
								pssg.getObjectStatus());
				specialServiceGroupsFromModel.add(statusedSsg);
			}

			specialServiceGroups = ReferenceLiteTO
					.toTOAssociationList(specialServiceGroupsFromModel);
		}

		if ((null != model.getReferralSources())
				&& !(model.getReferralSources().isEmpty())) {
			final List<Pair<ReferralSource,ObjectStatus>> referralSourcesFromModel
					= Lists.newArrayList();
			for (final PersonReferralSource prs : model.getReferralSources()) {
				Pair<ReferralSource, ObjectStatus> statusedRs =
						new Pair<ReferralSource, ObjectStatus>(
								prs.getReferralSource(),
								prs.getObjectStatus());
				referralSourcesFromModel.add(statusedRs);
			}

			referralSources = ReferenceLiteTO
					.toTOAssociationList(referralSourcesFromModel);
		}

		if ((null != model.getServiceReasons())
				&& !(model.getServiceReasons().isEmpty())) {
			final List<Pair<ServiceReason,ObjectStatus>> serviceReasonsFromModel
					= Lists .newArrayList();
			for (final PersonServiceReason psr : model.getServiceReasons()) {
				Pair<ServiceReason, ObjectStatus> statusedSr =
						new Pair<ServiceReason, ObjectStatus>(
								psr.getServiceReason(),
								psr.getObjectStatus());
				serviceReasonsFromModel.add(statusedSr);
			}

			serviceReasons = ReferenceLiteTO
					.toTOAssociationList(serviceReasonsFromModel);
		}

		if ((null != model.getProgramStatuses())
				&& !(model.getProgramStatuses().isEmpty())) {
			for (final PersonProgramStatus psr : model.getProgramStatuses()) {
				if (!psr.isExpired()) {
					if (StringUtils.isNotBlank(currentProgramStatusName)) {
						// uh oh! found a second, non-expired program status

						// TODO: create exception that can be thrown at runtime
						// due to assertion-like errors (invalid business rules)
						// because of a situation that indicate a bug in the
						// system or database
						throw new RuntimeException( // NOPMD
								"Multiple non-expired program statuses were found for student (person_id) "
										+ model.getId());
					}

					currentProgramStatusName = psr.getProgramStatus().getName();
				}
			}
		}

		if ((null == model.getCurrentRegistrationStatus())
				|| (model.getCurrentRegistrationStatus()
						.getRegisteredCourseCount() < 1)) {
			registeredForCurrentTerm = false;
		} else {
			registeredForCurrentTerm = true;
		}
	}

	/**
	 * Convert a collection of models to a List of equivalent transfer objects.
	 * 
	 * @param models
	 *            A collection of models to convert to transfer objects
	 * @return List of equivalent transfer objects
	 */
	public static List<PersonTO> toTOList(
			@NotNull final Collection<Person> models) {
		final List<PersonTO> tos = Lists.newArrayList();
		for (final Person model : models) {
			tos.add(new PersonTO(model)); // NOPMD by jon.adams on 5/13/12
		}

		return tos;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
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

	public void setLastName(final String lastName) {
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

	public void setPrimaryEmailAddress(final String primaryEmailAddress) {
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

	/**
	 * the user name for authentication and identification purposes
	 * 
	 * <p>
	 * Required to be non-empty and unique.
	 * 
	 * @param username
	 *            the user name, required, not empty, unique amongst all Person
	 *            instances
	 */
	public void setUsername(@NotNull final String username) {
		if (!StringUtils.isNotBlank(username)) {
			throw new IllegalArgumentException("username can not be empty.");
		}

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
	 *            the SchoolID (a.k.a. Student ID given by the school); maximum
	 *            length of 50 characters
	 */
	public void setSchoolId(final String schoolId) {
		this.schoolId = schoolId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(final Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getStudentIntakeCompleteDate() {
		return studentIntakeCompleteDate == null ? null : new Date(
				studentIntakeCompleteDate.getTime());
	}

	public void setStudentIntakeCompleteDate(
			final Date studentIntakeCompleteDate) {
		this.studentIntakeCompleteDate = (studentIntakeCompleteDate == null ? null
				: new Date(studentIntakeCompleteDate.getTime()));
	}

	public String getStrengths() {
		return strengths;
	}

	public void setStrengths(final String strengths) {
		this.strengths = strengths;
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

	public Date getStudentIntakeRequestDate() {
		return studentIntakeRequestDate == null ? null : new Date(
				studentIntakeRequestDate.getTime());
	}

	public void setStudentIntakeRequestDate(final Date studentIntakeRequestDate) {
		this.studentIntakeRequestDate = studentIntakeRequestDate == null ? null
				: new Date(studentIntakeRequestDate.getTime());
	}

	public ReferenceLiteTO<StudentType> getStudentType() {
		return studentType;
	}

	public void setStudentType(
			final ReferenceLiteTO<StudentType> studentType) {
		this.studentType = studentType;
	}

	public List<ReferenceLiteTO<SpecialServiceGroup>> getSpecialServiceGroups() {
		return specialServiceGroups;
	}

	public void setSpecialServiceGroups(
			final List<ReferenceLiteTO<SpecialServiceGroup>> specialServiceGroups) {
		this.specialServiceGroups = specialServiceGroups;
	}

	public List<ReferenceLiteTO<ReferralSource>> getReferralSources() {
		return referralSources;
	}

	public void setReferralSources(
			final List<ReferenceLiteTO<ReferralSource>> referralSources) {
		this.referralSources = referralSources;
	}

	public List<ReferenceLiteTO<ServiceReason>> getServiceReasons() {
		return serviceReasons;
	}

	public void setServiceReasons(
			final List<ReferenceLiteTO<ServiceReason>> serviceReasons) {
		this.serviceReasons = serviceReasons;
	}

	public CoachPersonLiteTO getCoach() {
		return coach;
	}

	public void setCoach(final CoachPersonLiteTO coach) {
		this.coach = coach;
	}

	public List<ReferenceLiteTO<ConfidentialityLevel>> getConfidentialityLevels() {
		return confidentialityLevels;
	}

	public void setConfidentialityLevels(
			final List<ReferenceLiteTO<ConfidentialityLevel>> confidentialityLevels) {
		this.confidentialityLevels = confidentialityLevels;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(final List<String> permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the current program status, if any
	 */
	public String getCurrentProgramStatusName() {
		return currentProgramStatusName;
	}

	/**
	 * Sets the current program status. Can be null. Changes here are ignored;
	 * use the Program Status API instead.
	 * 
	 * @param currentProgramStatusName
	 *            the current program status, if any
	 */
	public void setCurrentProgramStatusName(
			final String currentProgramStatusName) {
		this.currentProgramStatusName = currentProgramStatusName;
	}

	public boolean isRegisteredForCurrentTerm() {
		return registeredForCurrentTerm;
	}

	public void setRegisteredForCurrentTerm(
			final boolean registeredForCurrentTerm) {
		this.registeredForCurrentTerm = registeredForCurrentTerm;
	}

}