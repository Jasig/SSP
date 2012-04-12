package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeChallengeReferral;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

public class ChallengeTO extends AbstractReferenceTO<Challenge> implements
		TransferObject<Challenge> {

	/**
	 * This is the text that will be used in a selfHelpGuideQuestion.
	 */
	private String selfHelpGuideQuestion;

	/**
	 * Just a reference to the questions that reference this Challenge. Think of
	 * as selfHelpQuideChallenges
	 */
	private Set<SelfHelpGuideQuestion> selfHelpGuideQuestions = new HashSet<SelfHelpGuideQuestion>(
			0);

	/**
	 * Public description of the challenge
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	private String selfHelpGuideDescription;

	private boolean showInStudentIntake;

	private boolean showInSelfHelpSearch;

	private String tags;

	private Set<ChallengeReferralTO> challengeChallengeReferrals = new HashSet<ChallengeReferralTO>(
			0);

	private int referralCount = 0;

	public ChallengeTO() {
		super();
	}

	public ChallengeTO(UUID id) {
		super(id);
	}

	public ChallengeTO(UUID id, String name) {
		super(id, name);
	}

	public ChallengeTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public String getSelfHelpGuideQuestion() {
		return selfHelpGuideQuestion;
	}

	public void setSelfHelpGuideQuestion(String selfHelpGuideQuestion) {
		this.selfHelpGuideQuestion = selfHelpGuideQuestion;
	}

	public String getSelfHelpGuideDescription() {
		return selfHelpGuideDescription;
	}

	public void setSelfHelpGuideDescription(String selfHelpGuideDescription) {
		this.selfHelpGuideDescription = selfHelpGuideDescription;
	}

	public boolean isShowInStudentIntake() {
		return showInStudentIntake;
	}

	public void setShowInStudentIntake(boolean showInStudentIntake) {
		this.showInStudentIntake = showInStudentIntake;
	}

	public boolean isShowInSelfHelpSearch() {
		return showInSelfHelpSearch;
	}

	public void setShowInSelfHelpSearch(boolean showInSelfHelpSearch) {
		this.showInSelfHelpSearch = showInSelfHelpSearch;
	}

	public Set<SelfHelpGuideQuestion> getSelfHelpGuideQuestions() {
		return selfHelpGuideQuestions;
	}

	public void setSelfHelpGuideQuestions(
			Set<SelfHelpGuideQuestion> selfHelpGuideQuestions) {
		this.selfHelpGuideQuestions = selfHelpGuideQuestions;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Set<ChallengeReferralTO> getChallengeChallengeReferrals() {
		return challengeChallengeReferrals;
	}

	public void setChallengeChallengeReferrals(
			Set<ChallengeReferralTO> challengeChallengeReferrals) {
		this.challengeChallengeReferrals = challengeChallengeReferrals;
		setReferralCount(challengeChallengeReferrals == null ? 0
				: challengeChallengeReferrals.size());
	}

	public long getReferralCount() {
		return referralCount;
	}

	public void setReferralCount(int referralCount) {
		this.referralCount = referralCount;
	}

	public void fromModel(Challenge model) {
		super.fromModel(model);

		setSelfHelpGuideQuestion(model.getSelfHelpGuideQuestion());
		setSelfHelpGuideQuestions(model.getSelfHelpGuideQuestions());
		setSelfHelpGuideDescription(model.getSelfHelpGuideDescription());
		setShowInStudentIntake(model.isShowInStudentIntake());
		setShowInSelfHelpSearch(model.isShowInSelfHelpSearch());
		setTags(model.getTags());

		if (model.getChallengeChallengeReferrals() == null
				|| model.getChallengeChallengeReferrals().isEmpty()) {
			setChallengeChallengeReferrals(new HashSet<ChallengeReferralTO>(0));
		} else {
			Set<ChallengeReferralTO> set = new HashSet<ChallengeReferralTO>(
					model.getChallengeChallengeReferrals().size());
			for (ChallengeChallengeReferral challengeReferral : model
					.getChallengeChallengeReferrals()) {
				ChallengeReferralTO crt = new ChallengeReferralTO();
				crt.fromModel(challengeReferral.getChallengeReferral());
				set.add(crt);
			}

			setChallengeChallengeReferrals(set);
		}
	}

	public void addToModel(Challenge model) {
		super.addToModel(model);

		model.setSelfHelpGuideQuestion(getSelfHelpGuideQuestion());
		model.setSelfHelpGuideQuestions(getSelfHelpGuideQuestions());
		model.setSelfHelpGuideDescription(getSelfHelpGuideDescription());
		model.setShowInStudentIntake(isShowInStudentIntake());
		model.setShowInSelfHelpSearch(isShowInSelfHelpSearch());
		model.setTags(getTags());

		// TODO Implement deep set copy if necessary
		/*
		 * if (getChallengeChallengeReferrals() == null ||
		 * getChallengeChallengeReferrals().isEmpty())
		 * 
		 * {
		 * 
		 * model.setChallengeChallengeReferrals(new
		 * HashSet<ChallengeChallengeReferral>( 0));
		 * 
		 * } else {
		 * 
		 * Set<ChallengeChallengeReferral> set = new
		 * HashSet<ChallengeChallengeReferral>(
		 * getChallengeChallengeReferrals().size());
		 * 
		 * for (ChallengeReferralTO challengeReferral :
		 * getChallengeChallengeReferrals())
		 * 
		 * {
		 * 
		 * ChallengeChallengeReferral crt = new ChallengeChallengeReferral();
		 * 
		 * crt.addToModel(challengeReferral);
		 * 
		 * set.add(crt);
		 * 
		 * }
		 * 
		 * model.setChallengeChallengeReferrals(set);
		 * 
		 * }
		 */
	}

	@Override
	public void pullAttributesFromModel(Challenge model) {
		fromModel(model);
	}

	@Override
	public Challenge pushAttributesToModel(Challenge model) {
		addToModel(model);
		return model;
	}

	@Override
	public Challenge asModel() {
		return pushAttributesToModel(new Challenge());
	}
}
