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
 * JournalSource reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class JournalSource
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 1586445096161244662L;

	/**
	 * JournalSource EarlyAlert identifier
	 */
	public static final UUID JOURNALSOURCE_EARLYALERT_ID = UUID
			.fromString("b2d07a00-5056-a51a-80b5-f725f1c5c3e2");

	/**
	 * Empty constructor
	 */
	public JournalSource() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public JournalSource(final UUID id) {
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

	public JournalSource(final UUID id, final String name) {
		super(id, name);
	}

	@Override
	protected int hashPrime() {
		return 211;
	}

	// default hashCode okay if no extra fields are added
}