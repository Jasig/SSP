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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.jasig.ssp.model.Auditable;

/**
 * Category reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Category extends AbstractReference implements Auditable {

	private static final long serialVersionUID = 4274075255831698154L;

	@OneToMany(mappedBy = "category")
	private Set<ChallengeCategory> challengeCategories = new HashSet<ChallengeCategory>(
			0);

	/**
	 * Constructor
	 */
	public Category() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */
	public Category(final UUID id) {
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

	public Category(final UUID id, final String name) {
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
	public Category(final UUID id, final String name, final String description) {
		super(id, name, description);
	}

	public Set<ChallengeCategory> getChallengeCategories() {
		return challengeCategories;
	}

	public void setChallengeCategories(
			final Set<ChallengeCategory> challengeCategories) {
		this.challengeCategories = challengeCategories;
	}

	@Override
	protected int hashPrime() {
		return 43;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		// This code is not much different than the base class version, but we
		// are being explicit here so it isn't forgotten if any properties are
		// added later.

		// collections are not included here

		return hashPrime() * super.hashCode();
	}
}