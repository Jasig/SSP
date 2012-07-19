package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Person;

import com.google.common.collect.Lists;

/**
 * Encapsulate simple Person properties.
 * 
 * @author jon.adams
 */
public class CoachPersonLiteTO implements Serializable {

	private static final long serialVersionUID = 2921442272658399L;

	private UUID id;

	private String firstName;

	private String lastName;

	private String primaryEmailAddress;

	private String workPhone;

	private String officeLocation;

	private String departmentName;

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
			@NotNull final String firstName, @NotNull final String lastName,
			@NotNull final String primaryEmailAddress,
			final String officeLocation, final String departmentName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.primaryEmailAddress = primaryEmailAddress;
		this.officeLocation = officeLocation;
		this.departmentName = departmentName;
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
}