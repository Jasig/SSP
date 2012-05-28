package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Person;

/**
 * Encapsulate simple Person properties.
 * 
 * @author jon.adams
 */
public class PersonLiteTO implements Serializable {

	private static final long serialVersionUID = 2921442272658399L;

	private UUID id;

	private String firstName;

	private String lastName;

	/**
	 * Empty constructor. Should only ever be used for unit tests or ORMs.
	 */
	public PersonLiteTO() {
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
	 */
	public PersonLiteTO(@NotNull final UUID id,
			@NotNull final String firstName, @NotNull final String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Construct a simple Person from the specified model
	 * 
	 * @param person
	 *            The Person model to copy
	 */
	public PersonLiteTO(@NotNull final Person person) {
		if (person == null) {
			throw new IllegalArgumentException(
					"Person required when construcing a new simple PersonLiteTO.");
		}

		id = person.getId();
		firstName = person.getFirstName();
		lastName = person.getLastName();
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
}