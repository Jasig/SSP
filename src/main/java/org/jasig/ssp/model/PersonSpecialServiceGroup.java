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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.reference.SpecialServiceGroup;

/**
 * Assign a Person to a SpecialServiceGroup
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonSpecialServiceGroup
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = -3685614932117902730L;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "special_service_group_id", updatable = false, nullable = false)
	private SpecialServiceGroup specialServiceGroup;

	public PersonSpecialServiceGroup() {
		super();
	}

	public PersonSpecialServiceGroup(final Person person,
			final SpecialServiceGroup specialServiceGroup) {
		super();
		this.person = person;
		this.specialServiceGroup = specialServiceGroup;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public SpecialServiceGroup getSpecialServiceGroup() {
		return specialServiceGroup;
	}

	public void setSpecialServiceGroup(
			final SpecialServiceGroup specialServiceGroup) {
		this.specialServiceGroup = specialServiceGroup;
	}

	@Override
	protected int hashPrime() {
		return 283;
	}

	@Override
	final public int hashCode() { // NOPMD
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// PersonSpecialServiceGroup
		result *= hashField("person", person);
		result *= hashField("specialServiceGroup", specialServiceGroup);

		return result;
	}
}