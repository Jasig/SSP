package org.studentsuccessplan.ssp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SelfHelpGuideQuestionResponse extends Auditable implements
		Serializable {
	private static final long serialVersionUID = -6385278568384602029L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "self_help_guide_response_id", nullable = false)
	private SelfHelpGuideResponse selfHelpGuideResponse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "self_help_guide_question_id", nullable = false)
	private SelfHelpGuideQuestion selfHelpGuideQuestion;

	@Column(nullable = false)
	private Boolean response;

	@Column(nullable = false)
	private Boolean earlyAlertSent;

	public SelfHelpGuideResponse getSelfHelpGuideResponse() {
		return selfHelpGuideResponse;
	}

	public void setSelfHelpGuideResponse(
			SelfHelpGuideResponse selfHelpGuideResponse) {
		this.selfHelpGuideResponse = selfHelpGuideResponse;
	}

	public SelfHelpGuideQuestion getSelfHelpGuideQuestion() {
		return selfHelpGuideQuestion;
	}

	public void setSelfHelpGuideQuestion(
			SelfHelpGuideQuestion selfHelpGuideQuestion) {
		this.selfHelpGuideQuestion = selfHelpGuideQuestion;
	}

	public Boolean getResponse() {
		return response;
	}

	public void setResponse(Boolean response) {
		this.response = response;
	}

	public Boolean getEarlyAlertSent() {
		return earlyAlertSent;
	}

	public void setEarlyAlertSent(Boolean earlyAlertSent) {
		this.earlyAlertSent = earlyAlertSent;
	}

	@Override
	protected int hashPrime() {
		return 29;
	};
}
