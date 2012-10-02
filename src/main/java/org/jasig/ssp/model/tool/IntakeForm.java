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

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonAssoc;

/**
 * The model for the Intake Form tool.
 * 
 * Currently only a simple wrapper around a fully-normalized {@link Person}.
 */
public class IntakeForm implements PersonAssoc {

	/**
	 * Person with the full tree of data, down to only using identifiers
	 * (non-full objects) when a circular dependency (usually a reference back
	 * to a Person instance) or reference data (system-level lookups like
	 * Challenges, etc.).
	 */
	private Person person;

	/**
	 * Gets the full Person instance.
	 * 
	 * @return the full Person instance
	 */
	@Override
	public Person getPerson() {
		return person;
	}

	/**
	 * Sets the full Person instance.
	 * 
	 * @param person
	 *            Person instance
	 */
	@Override
	public void setPerson(final Person person) {
		this.person = person;
	}
}