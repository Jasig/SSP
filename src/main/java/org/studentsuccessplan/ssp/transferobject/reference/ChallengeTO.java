package org.studentsuccessplan.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeChallengeReferral;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

@JsonIgnoreProperties(value = { "selfHelpGuideQuestions" })
public class ChallengeTO extends AbstractReferenceTO<Challenge> implements
		TransferObject<Challenge>, Serializable {

	private static final long serialVersionUID = 2320351255526248904L;

	/**
	 * This is the text that will be used in a selfHelpGuideQuestion.
	 */
	private String selfHelpGuideQuestion;

	/**
	 * Just a reference to the questions that reference this Challenge. Think of
	 * as selfHelpQuideChallenges
	 */
	private List<SelfHelpGuideQuestionTO> selfHelpGuideQuestions;

	/**
	 * Public description of the challenge
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	private String selfHelpGuideDescription;

	private boolean showInStudentIntake;

	private boolean showInSelfHelpSearch;

	private String tags;

	private UUID defaultConfidentialityLevelId;

	private List<ChallengeReferralTO> challengeChallengeReferrals;

	public ChallengeTO() {
		super();
	}

	public ChallengeTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChallengeTO(final Challenge model) {
		super();
		from(model);
	}

	public static List<ChallengeTO> toTOList(
			final Collection<Challenge> models) {
		final List<ChallengeTO> tObjects = Lists.newArrayList();
		for (Challenge model : models) {
			tObjects.add(new ChallengeTO(model));
		}
		return tObjects;
	}

	@Override
	public final void from(@NotNull final Challenge model) {
		super.from(model);

		selfHelpGuideQuestion = model.getSelfHelpGuideQuestion();

		selfHelpGuideQuestions = Lists.newArrayList();
		if (model.getSelfHelpGuideQuestions() != null) {
			for (SelfHelpGuideQuestion question : model
					.getSelfHelpGuideQuestions()) {
				selfHelpGuideQuestions
						.add(new SelfHelpGuideQuestionTO(question));
			}
		}

		selfHelpGuideDescription = model.getSelfHelpGuideDescription();
		showInStudentIntake = model.isShowInStudentIntake();
		showInSelfHelpSearch = model.isShowInSelfHelpSearch();
		tags = model.getTags();

		if (model.getDefaultConfidentialityLevel() != null) {
			defaultConfidentialityLevelId = model
					.getDefaultConfidentialityLevel()
					.getId();
		}

		if ((model.getChallengeChallengeReferrals() == null)
				|| model.getChallengeChallengeReferrals().isEmpty()) {
			challengeChallengeReferrals = new ArrayList<ChallengeReferralTO>();
		} else {
			final List<ChallengeReferralTO> referralTOs = Lists.newArrayList();
			for (ChallengeChallengeReferral challengeReferral : model
					.getChallengeChallengeReferrals()) {
				referralTOs.add(new ChallengeReferralTO(challengeReferral
						.getChallengeReferral()));
			}
			challengeChallengeReferrals = referralTOs;
		}
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

	public List<SelfHelpGuideQuestionTO> getSelfHelpGuideQuestions() {
		return selfHelpGuideQuestions;
	}

	public void setSelfHelpGuideQuestions(
			final List<SelfHelpGuideQuestionTO> selfHelpGuideQuestions) {
		this.selfHelpGuideQuestions = selfHelpGuideQuestions;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public List<ChallengeReferralTO> getChallengeChallengeReferrals() {
		return challengeChallengeReferrals;
	}

	public void setChallengeChallengeReferrals(
			final List<ChallengeReferralTO> challengeChallengeReferrals) {
		this.challengeChallengeReferrals = challengeChallengeReferrals;
	}

	public long getReferralCount() {
		return challengeChallengeReferrals == null ? 0
				: challengeChallengeReferrals.size();
	}

	public void setReferralCount(final int referralCount) {
		/* ignore this since it is auto-calculated */
	}

	public UUID getDefaultConfidentialityLevelId() {
		return defaultConfidentialityLevelId;
	}

	public void setDefaultConfidentialityLevelId(
			final UUID defaultConfidentialityLevelId) {
		this.defaultConfidentialityLevelId = defaultConfidentialityLevelId;
	}

}
