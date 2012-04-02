package edu.sinclair.ssp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.model.reference.ChallengeReferral;

@Entity
public class Task extends AbstractTask {

	@ManyToOne
	@ForeignKey(name = "FK_TaskRoadmap_Challenge")
	@JoinColumn(name = "challengeId", updatable = false)
	private Challenge challenge;

	@ManyToOne
	@ForeignKey(name = "FK_TaskRoadmap_ChallengeReferral")
	@JoinColumn(name = "challengeReferralId", updatable = false)
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
}
