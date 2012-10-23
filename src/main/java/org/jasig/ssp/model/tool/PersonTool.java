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
package org.jasig.ssp.model.tool;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.AbstractAuditable;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssocAuditable;

/**
 * PersonTool model
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersonTool
		extends AbstractAuditable
		implements PersonAssocAuditable {

	private static final long serialVersionUID = 1L;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@Enumerated(EnumType.STRING)
	private Tools tool;

	public PersonTool() {
		super();
	}

	public PersonTool(final Person person, final Tools tool) {
		super();
		this.person = person;
		this.tool = tool;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}

	public Tools getTool() {
		return tool;
	}

	public void setTool(final Tools tool) {
		this.tool = tool;
	}

	@Override
	protected int hashPrime() {
		return 41;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// PersonTool
		result *= (person == null) || (person.getId() == null) ? "person"
				.hashCode() : person.getId().hashCode();
		result *= tool == null ? "tool".hashCode() : tool.hashCode();

		return result;
	}
}