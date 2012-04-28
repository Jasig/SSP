package org.studentsuccessplan.ssp.model.reference;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.studentsuccessplan.ssp.model.Auditable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ChallengeChallengeReferral extends Auditable implements
		Serializable {
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

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public ChallengeReferral getChallengeReferral() {
		return challengeReferral;
	}

	public void setChallengeReferral(ChallengeReferral challengeReferral) {
		this.challengeReferral = challengeReferral;
	}

	@Override
	protected int hashPrime() {
		return 59;
	};

}
