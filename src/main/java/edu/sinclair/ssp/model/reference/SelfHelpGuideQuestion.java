package edu.sinclair.ssp.model.reference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.sinclair.ssp.model.SelfHelpGuideQuestionResponse;

/**
 * SelfHelpGuideQuestion reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// :TODO rename to SelfHelpGuideChallenge
public class SelfHelpGuideQuestion extends AbstractReference implements
Serializable {
	@Column(nullable = false)
	private int questionNumber;

	@Column(nullable = false)
	private boolean critical;

	@Column(nullable = false)
	private boolean mandatory;

	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private Challenge challenge;

	@ManyToOne
	@JoinColumn(nullable = false, updatable = false)
	private SelfHelpGuide selfHelpGuide;

	@OneToMany(mappedBy = "selfHelpGuideQuestion")
	private Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses = new HashSet<SelfHelpGuideQuestionResponse>(
			0);

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public SelfHelpGuideQuestion() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public SelfHelpGuideQuestion(UUID id) {
		super(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 */

	public SelfHelpGuideQuestion(UUID id, String name) {
		super(id, name);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 150 characters
	 */
	public SelfHelpGuideQuestion(UUID id, String name, String description) {
		super(id, name, description);
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

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public SelfHelpGuide getSelfHelpGuide() {
		return selfHelpGuide;
	}

	public void setSelfHelpGuide(SelfHelpGuide selfHelpGuide) {
		this.selfHelpGuide = selfHelpGuide;
	}

	public Set<SelfHelpGuideQuestionResponse> getSelfHelpGuideQuestionResponses() {
		return selfHelpGuideQuestionResponses;
	}

	public void setSelfHelpGuideQuestionResponses(
			Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses) {
		this.selfHelpGuideQuestionResponses = selfHelpGuideQuestionResponses;
	}

}
