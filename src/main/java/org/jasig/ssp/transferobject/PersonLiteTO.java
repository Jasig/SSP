package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.UUID;

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

	public PersonLiteTO() {
		super();
	}

	public PersonLiteTO(final UUID id, final String firstName,
			final String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public PersonLiteTO(final Person person) {
		if (person == null) {
			return;
		}

		this.id = person.getId();
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
	}

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
}
