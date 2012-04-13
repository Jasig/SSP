package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeChallengeReferral;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

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

	public ChallengeTO(final UUID id) {
		super(id);
	}

	public ChallengeTO(final UUID id, final String name) {
		super(id, name);
	}

	public ChallengeTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChallengeTO(final Challenge model) {
		super();
		fromModel(model);
	}

	public String getSelfHelpGuideQuestion() {
		return selfHelpGuideQuestion;
	}

	public void setSelfHelpGuideQuestion(final String selfHelpGuideQuestion) {
		this.selfHelpGuideQuestion = selfHelpGuideQuestion;
	}

	public String getSelfHelpGuideDescription() {
		return selfHelpGuideDescription;
	}

	public void setSelfHelpGuideDescription(
			final String selfHelpGuideDescription) {
		this.selfHelpGuideDescription = selfHelpGuideDescription;
	}

	public boolean isShowInStudentIntake() {
		return showInStudentIntake;
	}

	public void setShowInStudentIntake(final boolean showInStudentIntake) {
		this.showInStudentIntake = showInStudentIntake;
	}

	public boolean isShowInSelfHelpSearch() {
		return showInSelfHelpSearch;
	}

	public void setShowInSelfHelpSearch(final boolean showInSelfHelpSearch) {
		this.showInSelfHelpSearch = showInSelfHelpSearch;
	}

	public Set<SelfHelpGuideQuestion> getSelfHelpGuideQuestions() {
		return selfHelpGuideQuestions;
	}

	public void setSelfHelpGuideQuestions(
			final Set<SelfHelpGuideQuestion> selfHelpGuideQuestions) {
		this.selfHelpGuideQuestions = selfHelpGuideQuestions;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public Set<ChallengeReferralTO> getChallengeChallengeReferrals() {
		return challengeChallengeReferrals;
	}

	public void setChallengeChallengeReferrals(
			final Set<ChallengeReferralTO> challengeChallengeReferrals) {
		this.challengeChallengeReferrals = challengeChallengeReferrals;
		setReferralCount(challengeChallengeReferrals == null ? 0
				: challengeChallengeReferrals.size());
	}

	public long getReferralCount() {
		return referralCount;
	}

	public void setReferralCount(final int referralCount) {
		this.referralCount = referralCount;
	}

	@Override
	public final void fromModel(final Challenge model) {
		super.fromModel(model);

		selfHelpGuideQuestion = model.getSelfHelpGuideQuestion();
		selfHelpGuideQuestions = model.getSelfHelpGuideQuestions();
		selfHelpGuideDescription = model.getSelfHelpGuideDescription();
		showInStudentIntake = model.isShowInStudentIntake();
		showInSelfHelpSearch = model.isShowInSelfHelpSearch();
		tags = model.getTags();

		if ((model.getChallengeChallengeReferrals() == null)
				|| model.getChallengeChallengeReferrals().isEmpty()) {
			setChallengeChallengeReferrals(new HashSet<ChallengeReferralTO>(0));
		} else {
			final Set<ChallengeReferralTO> set = new HashSet<ChallengeReferralTO>(
					model.getChallengeChallengeReferrals().size());
			for (ChallengeChallengeReferral challengeReferral : model
					.getChallengeChallengeReferrals()) {
				set.add(new ChallengeReferralTO(challengeReferral
						.getChallengeReferral()));
			}

			setChallengeChallengeReferrals(set);
		}
	}

	@Override
	public Challenge addToModel(final Challenge model) {
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

		return model;
	}

	@Override
	public Challenge asModel() {
		return addToModel(new Challenge());
	}

	public static List<ChallengeTO> listToTOList(final List<Challenge> models) {
		final List<ChallengeTO> tos = Lists.newArrayList();
		for (Challenge model : models) {
			tos.add(new ChallengeTO(model));
		}

		return tos;
	}

}
