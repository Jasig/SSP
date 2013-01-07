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
package org.jasig.ssp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.DisabilityType;

/**
 * Students may have zero or multiple Disability Agencies.
 * 
 * The PersonDisabilityType entity is an associative mapping between a student
 * (Person) and any Disability Agencies they have.
 * 
 * Non-student users should never have any assigned Disability Agencies.
 * 
 * @author shawn.gormley
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonDisabilityType
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -1349765434053823165L;

	@Column(length = 255)
	@Size(max = 255)
	private String description;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@ManyToOne
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "disability_type_id", updatable = false, nullable = false)
	private DisabilityType disabilityType;

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public DisabilityType getDisabilityType() {
		return disabilityType;
	}

	public void setDisabilityType(final DisabilityType disabilityType) {
		this.disabilityType = disabilityType;
	}

	/**
	 * Overwrites simple properties with the parameter's properties.
	 * 
	 * @param source
	 *            Source to use for overwrites.
	 */
	public void overwrite(final PersonDisabilityType source) {
		setDescription(source.getDescription());
	}

	@Override
	protected int hashPrime() {
		return 419;
	}

	@Override
	final public int hashCode() { // NOPMD
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("description", description);
		result *= hashField("person", person);
		result *= hashField("disabilityType", disabilityType);

		return result;
	}
}