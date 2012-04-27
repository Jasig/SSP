package org.studentsuccessplan.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SelfHelpGuideQuestionTO extends
		AbstractReferenceTO<SelfHelpGuideQuestion>
		implements TransferObject<SelfHelpGuideQuestion>, Serializable {

	private static final long serialVersionUID = 6074881529172652403L;

	private int questionNumber;

	private boolean critical;

	private boolean mandatory;

	private UUID challengeId;

	private UUID selfHelpGuideId;

	public SelfHelpGuideQuestionTO() {
		super();
	}

	public SelfHelpGuideQuestionTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SelfHelpGuideQuestionTO(SelfHelpGuideQuestion model) {
		super();
		from(model);
	}

	public static List<SelfHelpGuideQuestionTO> toTOList(
			final Collection<SelfHelpGuideQuestion> models) {
		final List<SelfHelpGuideQuestionTO> tObjects = Lists.newArrayList();
		for (SelfHelpGuideQuestion model : models) {
			tObjects.add(new SelfHelpGuideQuestionTO(model));
		}
		return tObjects;
	}

	@Override
	public final void from(final SelfHelpGuideQuestion model) {
		super.from(model);
		setQuestionNumber(model.getQuestionNumber());
		setCritical(model.isCritical());
		setMandatory(model.isMandatory());

		if (model.getSelfHelpGuide() != null) {
			setSelfHelpGuideId(model.getSelfHelpGuide().getId());
		}

		if (model.getChallenge() != null) {
			setChallengeId(model.getChallenge().getId());
		}
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public boolean isCritical() {
		return critical;
	}

	public void setCritical(boolean critical) {
		this.critical = critical;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(UUID challengeId) {
		this.challengeId = challengeId;
	}

	public UUID getSelfHelpGuideId() {
		return selfHelpGuideId;
	}

	public void setSelfHelpGuideId(UUID selfHelpGuideId) {
		this.selfHelpGuideId = selfHelpGuideId;
	}

}
