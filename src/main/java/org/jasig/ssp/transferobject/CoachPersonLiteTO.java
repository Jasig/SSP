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
package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.CoachCaseloadRecordCountForProgramStatus;
import org.jasig.ssp.model.Person;

import com.google.common.collect.Lists;

/**
 * Encapsulate simple Person properties.
 * 
 * @author jon.adams
 */
public class CoachPersonLiteTO implements Serializable {

	private static final long serialVersionUID = 2921442272658399L;

	/**
	 * Compares names only. I.e. two {@link CoachPersonLiteTO}s with the same
	 * first, last, and middle names are considered equivalent, even if they
	 * represent different people. This is often problematic when using this
	 * as the comparator in a {@link java.util.SortedSet} or
	 * {@link java.util.SortedMap}. Consider
	 * {@link CoachPersonLiteTONameAndIdComparator}
	 * for cases when you really want to sort a list of
	 * {@link CoachPersonLiteTO}s and not inadvertently drop entries with
	 * duplicate names.
	 */
	public static class CoachPersonLiteTONameComparator implements Comparator<CoachPersonLiteTO> {
		@Override
		public int compare(CoachPersonLiteTO o1, CoachPersonLiteTO o2) {
			return nameOf(o1).compareTo(nameOf(o2));
		}

		public int compare(CoachPersonLiteTO p, CoachCaseloadRecordCountForProgramStatus c) {
			return nameOf(p).compareTo(nameOf(c));
		}

		String nameOf(CoachPersonLiteTO p) {
			return new StringBuilder()
					.append(StringUtils.trimToEmpty(p.getLastName()))
					.append(StringUtils.trimToEmpty(p.getFirstName()))
					.toString();
		}

		String nameOf(CoachCaseloadRecordCountForProgramStatus coachStatusCount) {
			return new StringBuilder()
					.append(StringUtils.trimToEmpty(coachStatusCount.getCoachLastName()))
					.append(StringUtils.trimToEmpty(coachStatusCount.getCoachFirstName()))
					.toString();
		}

	}

	public static final CoachPersonLiteTONameComparator COACH_PERSON_LITE_TO_NAME_COMPARATOR =
			new CoachPersonLiteTONameComparator();

	public static class CoachPersonLiteTONameAndIdComparator implements Comparator<CoachPersonLiteTO> {

		@Override
		public int compare(CoachPersonLiteTO o1, CoachPersonLiteTO o2) {
			int nameCompare = COACH_PERSON_LITE_TO_NAME_COMPARATOR.compare(o1, o2);
			if ( nameCompare != 0 ) {
				return nameCompare;
			}
			return compareUUIDs(o1.getId(), o2.getId());
		}

		public int compare(CoachPersonLiteTO p, CoachCaseloadRecordCountForProgramStatus c) {
			int nameCompare = COACH_PERSON_LITE_TO_NAME_COMPARATOR.compare(p, c);
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

	public static final CoachPersonLiteTONameAndIdComparator COACH_PERSON_LITE_TO_NAME_AND_ID_COMPARATOR =
			new CoachPersonLiteTONameAndIdComparator();

	private UUID id;

	private String firstName;

	private String lastName;

	private String primaryEmailAddress;

	private String workPhone;

	private String officeLocation;

	private String departmentName;
	
	private String photoUrl;

	/**
	 * Empty constructor. Should only ever be used for unit tests or ORMs.
	 */
	public CoachPersonLiteTO() {
		super();
	}

	/**
	 * Construct a simple Person instance with the specified properties
	 * 
	 * @param id
	 *            Identifier; required
	 * @param firstName
	 *            First name; required; 80 characters or less
	 * @param lastName
	 *            Last name; required; 80 characters or less
	 * @param primaryEmailAddress
	 *            primary e-mail address; required
	 * @param officeLocation
	 *            office location
	 * @param departmentName
	 *            department name
	 */
	public CoachPersonLiteTO(@NotNull final UUID id,
			@NotNull final String firstName,
			@NotNull final String lastName,
			@NotNull final String primaryEmailAddress,
			final String officeLocation,
			final String departmentName,
			final String workPhone,
			final String photoUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryEmailAddress = primaryEmailAddress;
		this.officeLocation = officeLocation;
		this.departmentName = departmentName;
		this.workPhone = workPhone;
		this.photoUrl = photoUrl;
	}

	/**
	 * Construct a simple Person from the specified model
	 * 
	 * @param person
	 *            The Person model to copy
	 */
	public CoachPersonLiteTO(@NotNull final Person person) {
		if (person == null) {
			throw new IllegalArgumentException(
					"Person required when construcing a new simple PersonLiteTO.");
		}

		id = person.getId();
		firstName = person.getFirstName();
		lastName = person.getLastName();
		primaryEmailAddress = person.getPrimaryEmailAddress();
		photoUrl =  person.getPhotoUrl();
		if(person.getStaffDetails() != null){
			officeLocation = person.getStaffDetails().getOfficeLocation();
			departmentName = person.getStaffDetails().getDepartmentName();
		}
		// officeLocation = null; // TODO: load data from external source
		// departmentName = null; // TODO: load data from external source
	}

	/**
	 * Convert a collection of models to a List of equivalent transfer objects.
	 * 
	 * @param models
	 *            A collection of models to convert to transfer objects
	 * @return List of equivalent transfer objects
	 */
	public static List<CoachPersonLiteTO> toTOList(
			@NotNull final Collection<Person> models) {
		final List<CoachPersonLiteTO> tos = Lists.newArrayList();
		for (final Person model : models) {
			tos.add(new CoachPersonLiteTO(model)); // NOPMD
		}

		return tos;
	}

	/**
	 * Gets the id
	 * 
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id
	 *            the id; required
	 */
	public void setId(@NotNull final UUID id) {
		this.id = id;
	}

	/**
	 * Gets the first name
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name
	 * 
	 * @param firstName
	 *            the first name; required; 80 characters or less
	 */
	public void setFirstName(@NotNull final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name
	 * 
	 * @param lastName
	 *            the last name; required; 80 characters or less
	 */
	public void setLastName(@NotNull final String lastName) {
		this.lastName = lastName;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(@NotNull final String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(final String workPhone) {
		this.workPhone = workPhone;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(final String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(final String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * Not <code>equals()</code> because the convention seems to be to
	 * not do that for TO classes.
	 *
	 * @param o
	 * @return
	 */
	public boolean equalsAllFields(Object o) {
		if (this == o) return true;
		if (!(o instanceof CoachPersonLiteTO)) return false;

		CoachPersonLiteTO that = (CoachPersonLiteTO) o;

		if (departmentName != null ? !departmentName.equals(that.departmentName) : that.departmentName != null)
			return false;
		if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
			return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null)
			return false;
		if (officeLocation != null ? !officeLocation.equals(that.officeLocation) : that.officeLocation != null)
			return false;
		if (primaryEmailAddress != null ? !primaryEmailAddress.equals(that.primaryEmailAddress) : that.primaryEmailAddress != null)
			return false;
		if (workPhone != null ? !workPhone.equals(that.workPhone) : that.workPhone != null)
			return false;
		if (photoUrl != null ? !photoUrl.equals(that.photoUrl) : that.photoUrl != null)
			return false;

		return true;
	}

	/**
	 * Not conventional to override <code>toString()</code> in TO classes,
	 * but makes debugging tests so much easier.
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "CoachPersonLiteTO{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", primaryEmailAddress='" + primaryEmailAddress + '\'' +
				", workPhone='" + workPhone + '\'' +
				", officeLocation='" + officeLocation + '\'' +
				", departmentName='" + departmentName + '\'' +
				'}';
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * @param photoUrl the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public String getFullName(){
		return firstName + " " + lastName;
	}
}