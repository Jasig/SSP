package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;

import com.google.common.collect.Lists;

/**
 * Encapsulate simple Person properties.
 * 
 * @author jon.adams
 */
public class ExternalPersonLiteTO implements Serializable,
		ExternalDataTO<ExternalFacultyCourseRoster> {

	private static final long serialVersionUID = -4413298604867700246L;

	private String schoolId;

	private String firstName;

	private String middleName;

	private String lastName;

	/**
	 * Empty constructor. Should only ever be used for unit tests or ORMs.
	 */
	public ExternalPersonLiteTO() {
		super();
	}

	/**
	 * Construct a simple ExternalPerson instance with the specified properties
	 * 
	 * @param schoolId
	 *            Identifier; required
	 * @param firstName
	 *            First name; required; 50 characters or less
	 * @param middleName
	 *            Middle name; optional; 50 characters or less
	 * @param lastName
	 *            Last name; required; 50 characters or less
	 */
	public ExternalPersonLiteTO(@NotNull final String schoolId,
			@NotNull final String firstName, final String middleName,
			@NotNull final String lastName) {
		this.schoolId = schoolId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	/**
	 * Construct a simple Person from the specified model
	 * 
	 * @param externalFacultyCourseRoster
	 *            The ExternalFacultyCourseRoster model to copy
	 */
	public ExternalPersonLiteTO(
			@NotNull final ExternalFacultyCourseRoster externalFacultyCourseRoster) {
		if (externalFacultyCourseRoster == null) {
			throw new IllegalArgumentException(
					"Person required when construcing a new simple ExternalPersonLiteTO.");
		}

		schoolId = externalFacultyCourseRoster.getFacultySchoolId();
		firstName = externalFacultyCourseRoster.getFirstName();
		middleName = externalFacultyCourseRoster.getMiddleName();
		lastName = externalFacultyCourseRoster.getLastName();
	}

	@Override
	public final void from(final ExternalFacultyCourseRoster model) {
		schoolId = model.getFacultySchoolId();
		firstName = model.getFirstName();
		middleName = model.getMiddleName();
		lastName = model.getLastName();
	}

	/**
	 * Convert a collection of models to a List of equivalent transfer objects.
	 * 
	 * @param models
	 *            A collection of models to convert to transfer objects
	 * @return List of equivalent transfer objects
	 */
	public static List<ExternalPersonLiteTO> toTOList(
			@NotNull final Collection<ExternalFacultyCourseRoster> models) {
		final List<ExternalPersonLiteTO> tos = Lists.newArrayList();
		for (final ExternalFacultyCourseRoster model : models) {
			tos.add(new ExternalPersonLiteTO(model)); // NOPMD
		}

		return tos;
	}

	/**
	 * Gets the schoolId
	 * 
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}

	/**
	 * Sets the schoolId
	 * 
	 * @param schoolId
	 *            the schoolId; required
	 */
	public void setSchoolId(@NotNull final String schoolId) {
		this.schoolId = schoolId;
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
	 * Gets the middle name
	 * 
	 * @return the middle name
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets the middle name
	 * 
	 * @param middleName
	 *            the middle name; required; 80 characters or less
	 */
	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
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
}