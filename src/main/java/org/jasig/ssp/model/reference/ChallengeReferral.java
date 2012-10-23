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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.Auditable;

/**
 * ChallengeReferral reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ChallengeReferral
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 5543482960025703474L;

	/**
	 * Public Description
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String publicDescription;

	@Column(nullable = false)
	private Boolean showInSelfHelpGuide;

	@Column(nullable = false)
	private Boolean showInStudentIntake;

	@OneToMany(mappedBy = "challengeReferral")
	private Set<ChallengeChallengeReferral> challengeChallengeReferrals =
			new HashSet<ChallengeChallengeReferral>(0);

	/**
	 * Constructor
	 */
	public ChallengeReferral() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public ChallengeReferral(final UUID id) {
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

	public ChallengeReferral(final UUID id, final String name) {
		super(id, name);
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	public void setPublicDescription(final String publicDescription) {
		this.publicDescription = publicDescription;
	}

	public Set<ChallengeChallengeReferral> getChallengeChallengeReferrals() {
		return challengeChallengeReferrals;
	}

	public void setChallengeChallengeReferrals(
			final Set<ChallengeChallengeReferral> challengeChallengeReferrals) {
		this.challengeChallengeReferrals = challengeChallengeReferrals;
	}

	public Boolean getShowInSelfHelpGuide() {
		return showInSelfHelpGuide;
	}

	public void setShowInSelfHelpGuide(final Boolean showInSelfHelpGuide) {
		this.showInSelfHelpGuide = showInSelfHelpGuide;
	}

	/**
	 * @return the showInStudentIntake
	 */
	public Boolean getShowInStudentIntake() {
		return showInStudentIntake;
	}

	/**
	 * @param showInStudentIntake
	 *            the showInStudentIntake to set, can be null
	 */
	public void setShowInStudentIntake(final Boolean showInStudentIntake) {
		this.showInStudentIntake = showInStudentIntake;
	}

	@Override
	protected int hashPrime() {
		return 61;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime() * super.hashCode();

		result *= hashField("publicDescription", publicDescription);
		result *= showInSelfHelpGuide != null && showInSelfHelpGuide ? 5 : 11;
		result *= showInStudentIntake != null && showInStudentIntake ? 3 : 17;

		// collections are not included here

		return result;
	}
}