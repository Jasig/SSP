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

	public PersonLiteTO(@NotNull final CoachPersonLiteTO coachTO) {
		if ( coachTO == null ) {
			throw new IllegalArgumentException(
					"Coach required when construcing a new simple PersonLiteTO.");
		}
		id = coachTO.getId();
		firstName = coachTO.getFirstName();
		lastName = coachTO.getLastName();
	}

	/**
	 * Convert a collection of models to a List of equivalent transfer objects.
	 * 
	 * @param models
	 *            A collection of models to convert to transfer objects
	 * @return List of equivalent transfer objects
	 */
	public static List<PersonLiteTO> toTOList(
			@NotNull final Collection<Person> models) {
		final List<PersonLiteTO> tos = Lists.newArrayList();
		for (final Person model : models) {
			tos.add(new PersonLiteTO(model)); // NOPMD
		}

		return tos;
	}

	public static List<PersonLiteTO> toTOListFromCoachTOs(
			@NotNull final Collection<CoachPersonLiteTO> coachTOs) {
		final List<PersonLiteTO> tos = Lists.newArrayList();
		for ( final CoachPersonLiteTO coachTO : coachTOs ) {
			tos.add(new PersonLiteTO(coachTO));
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

}