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
package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.Auditable;

/**
 * EducationGoal reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EducationGoal
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -306887708908281830L;

	@Column(length = 255)
	@Size(max = 255)
	private String otherDescription;

	/**
	 * Constructor
	 */
	public EducationGoal() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public EducationGoal(final UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 */

	public EducationGoal(final UUID id, final String name) {
		super(id, name);
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	/**
	 * Set other description. Maximum 255 characters.
	 * 
	 * @param otherDescription
	 *            Maximum 255 characters.
	 */
	public void setOtherDescription(final String otherDescription) {
		this.otherDescription = otherDescription;
	}

	@Override
	protected int hashPrime() {
		return 83;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		return hashPrime() * super.hashCode()
				* hashField("otherDescription", otherDescription);
	}
}