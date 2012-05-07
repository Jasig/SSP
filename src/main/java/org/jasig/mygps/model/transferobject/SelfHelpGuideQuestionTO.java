package org.jasig.mygps.model.transferobject;

import java.io.Serializable;
import java.util.UUID;

import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.transferobject.TransferObject;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;

/**
 * Transfer object very similar to
 * {@link org.jasig.ssp.transferobject.reference.SelfHelpGuideQuestionTO} except
 * that it maps back to a formal Challenge instance (instead of only the
 * ChallengeId) so the MyGPS TO model does not need to be changed, since it is
 * different from the SSP front-end transfer object.
 * 
 * @author jon.adams
 * 
 */
public class SelfHelpGuideQuestionTO extends
		AbstractReferenceTO<SelfHelpGuideQuestion>
		implements TransferObject<SelfHelpGuideQuestion>, Serializable {

	private static final long serialVersionUID = 6074881529172652403L;

	private int questionNumber;

	private boolean critical;

	private boolean mandatory;

	private ChallengeTO challenge;

	private UUID selfHelpGuideId;

	public SelfHelpGuideQuestionTO(final SelfHelpGuideQuestion model) {
		super();
		from(model);
	}

	@Override
	public final void from(final SelfHelpGuideQuestion model) {
		super.from(model);
		questionNumber = model.getQuestionNumber();
		critical = model.isCritical();
		mandatory = model.isMandatory();

		if (model.getSelfHelpGuide() != null) {
			selfHelpGuideId = model.getSelfHelpGuide().getId();
		}

		if (model.getChallenge() != null) {
			challenge = new ChallengeTO(model.getChallenge());
		}
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(final int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public boolean isCritical() {
		return critical;
	}

	public void setCritical(final boolean critical) {
		this.critical = critical;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	public UUID getChallengeId() {
		return challenge == null ? null : challenge.getId();
	}

	/**
	 * @return the challenge
	 */
	public ChallengeTO getChallenge() {
		return challenge;
	}

	/**
	 * @param challenge
	 *            the challenge to set
	 */
	public void setChallenge(final ChallengeTO challenge) {
		this.challenge = challenge;
	}

	public UUID getSelfHelpGuideId() {
		return selfHelpGuideId;
	}

	public void setSelfHelpGuideId(final UUID selfHelpGuideId) {
		this.selfHelpGuideId = selfHelpGuideId;
	}
}
