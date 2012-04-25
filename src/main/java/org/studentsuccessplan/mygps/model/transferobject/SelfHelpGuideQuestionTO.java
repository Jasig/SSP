package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.studentsuccessplan.ssp.model.SelfHelpGuideQuestionResponse;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.reference.AbstractReferenceTO;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

import com.google.common.collect.Sets;

@JsonIgnoreProperties(value = { "selfHelpGuide" })
public class SelfHelpGuideQuestionTO extends
		AbstractReferenceTO<SelfHelpGuideQuestion> implements Serializable {

	private static final long serialVersionUID = 6074881529172652403L;

	private int questionNumber;

	private boolean critical;

	private boolean mandatory;

	private ChallengeTO challenge;

	private SelfHelpGuide selfHelpGuide;

	private Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses = new HashSet<SelfHelpGuideQuestionResponse>(
			0);

	/**
	 * @return the questionNumber
	 */
	public int getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * @param questionNumber
	 *            the questionNumber to set
	 */
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * @return the critical
	 */
	public boolean isCritical() {
		return critical;
	}

	/**
	 * @param critical
	 *            the critical to set
	 */
	public void setCritical(boolean critical) {
		this.critical = critical;
	}

	/**
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory
	 *            the mandatory to set
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
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
	public void setChallenge(ChallengeTO challenge) {
		this.challenge = challenge;
	}

	/**
	 * @return the selfHelpGuide
	 */
	public SelfHelpGuide getSelfHelpGuide() {
		return selfHelpGuide;
	}

	/**
	 * @param selfHelpGuide
	 *            the selfHelpGuide to set
	 */
	public void setSelfHelpGuide(SelfHelpGuide selfHelpGuide) {
		this.selfHelpGuide = selfHelpGuide;
	}

	/**
	 * @return the selfHelpGuideQuestionResponses
	 */
	public Set<SelfHelpGuideQuestionResponse> getSelfHelpGuideQuestionResponses() {
		return selfHelpGuideQuestionResponses;
	}

	/**
	 * @param selfHelpGuideQuestionResponses
	 *            the selfHelpGuideQuestionResponses to set
	 */
	public void setSelfHelpGuideQuestionResponses(
			Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses) {
		this.selfHelpGuideQuestionResponses = selfHelpGuideQuestionResponses;
	}

	@Override
	public SelfHelpGuideQuestion asModel() {
		return pushAttributesToModel(new SelfHelpGuideQuestion());
	}

	public SelfHelpGuideQuestion pushAttributesToModel(
			SelfHelpGuideQuestion model) {
		addToModel(model);
		return model;
	}

	@Override
	public void fromModel(SelfHelpGuideQuestion model) {
		super.fromModel(model);

		setQuestionNumber(model.getQuestionNumber());
		setCritical(model.isCritical());
		setMandatory(model.isMandatory());
		ChallengeTO challenge = new ChallengeTO();
		challenge.fromModel(model.getChallenge());
		setChallenge(challenge);
		setSelfHelpGuide(model.getSelfHelpGuide());
		setSelfHelpGuideQuestionResponses(model
				.getSelfHelpGuideQuestionResponses());
	}

	public static Set<SelfHelpGuideQuestionTO> listToTOSet(
			List<SelfHelpGuideQuestion> models) {
		Set<SelfHelpGuideQuestionTO> tos = Sets.newHashSet();
		for (SelfHelpGuideQuestion model : models) {
			SelfHelpGuideQuestionTO obj = new SelfHelpGuideQuestionTO();
			obj.fromModel(model);
			tos.add(obj);
		}

		return tos;
	}

	public static Set<SelfHelpGuideQuestionTO> setToTOSet(
			Set<SelfHelpGuideQuestion> models) {
		Set<SelfHelpGuideQuestionTO> tos = Sets.newHashSet();
		for (SelfHelpGuideQuestion model : models) {
			SelfHelpGuideQuestionTO obj = new SelfHelpGuideQuestionTO();
			obj.fromModel(model);
			tos.add(obj);
		}

		return tos;
	}
}
