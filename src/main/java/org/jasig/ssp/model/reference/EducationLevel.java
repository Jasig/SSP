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

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.jasig.ssp.model.Auditable;

/**
 * Education Level reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EducationLevel
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 2382076420560584469L;

	public static final UUID NO_DIPLOMA_NO_GED_ID = UUID
			.fromString("5d967ba0-e086-4426-85d5-29bc86da9295");
	public static final UUID GED_ID = UUID
			.fromString("710add1c-7b53-4cbe-86cb-8d7c5837d68b");
	public static final UUID HIGH_SCHOOL_GRADUATION_ID = UUID
			.fromString("f4780d23-fd8a-4758-b772-18606dca32f0");
	public static final UUID SOME_COLLEGE_CREDITS_ID = UUID
			.fromString("c5111182-9e2f-4252-bb61-d2cfa9700af7");
	public static final UUID OTHER_ID = UUID
			.fromString("247165ae-3db4-4679-ac95-ca96488c3b27");

	// More Education Levels exist in the database that are not included above

	/**
	 * Empty constructor
	 */
	public EducationLevel() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public EducationLevel(final UUID id) {
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
	public EducationLevel(final UUID id, final String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public EducationLevel(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	@Override
	protected int hashPrime() {
		return 97;
	}

	// default hashCode okay if no extra fields are added
}