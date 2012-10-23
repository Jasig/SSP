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

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jasig.ssp.model.AbstractAuditable;
import org.jasig.ssp.model.Auditable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ChallengeChallengeReferral
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = -1482715931640054820L;

	@ManyToOne()
	@JoinColumn(name = "challenge_id", nullable = false)
	private Challenge challenge;

	@ManyToOne()
	@JoinColumn(name = "challenge_referral_id", nullable = false)
	private ChallengeReferral challengeReferral;

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(final Challenge challenge) {
		this.challenge = challenge;
	}

	public ChallengeReferral getChallengeReferral() {
		return challengeReferral;
	}

	public void setChallengeReferral(final ChallengeReferral challengeReferral) {
		this.challengeReferral = challengeReferral;
	}

	@Override
	protected int hashPrime() {
		return 59;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:35 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// ChallengeChallengeReferral
		result *= hashField("challenge", challenge);
		result *= hashField("challengeReferral", challengeReferral);

		return result;
	}
}