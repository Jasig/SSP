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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.Auditable;

/**
 * ConfidentialityDisclosureAgreement reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ConfidentialityDisclosureAgreement
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -2992853251827812441L;

	/**
	 * text of the agreement
	 * 
	 * Optional, but null not allowed, max length 64000 characters.
	 */
	@Column(nullable = false, length = 64000)
	@Size(max = 64000)
	private String text;

	/**
	 * Constructor
	 */
	public ConfidentialityDisclosureAgreement() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public ConfidentialityDisclosureAgreement(final UUID id) {
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

	public ConfidentialityDisclosureAgreement(final UUID id,
			@NotNull final String name) {
		super(id, name);
	}

	public String getText() {
		return text;
	}

	public void setText(@NotNull final String text) {
		this.text = text;
	}

	@Override
	protected int hashPrime() {
		return 73;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		return hashPrime() * super.hashCode() * hashField("text", text);
	}
}