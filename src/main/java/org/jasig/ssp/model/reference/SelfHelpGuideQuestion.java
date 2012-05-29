package org.jasig.ssp.model.reference;

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

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.SelfHelpGuideQuestionResponse;

/**
 * SelfHelpGuideQuestion reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// :TODO rename to SelfHelpGuideChallenge
public class SelfHelpGuideQuestion
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = -3535904390712079266L;

	@Column(nullable = false)
	private int questionNumber;

	@Column(nullable = false)
	private boolean critical;

	@Column(nullable = false)
	private boolean mandatory;

	@ManyToOne
	@JoinColumn(name = "challenge_id", nullable = false, updatable = false)
	private Challenge challenge;

	@ManyToOne
	@JoinColumn(name = "self_help_guide_id", nullable = false, updatable = false)
	private SelfHelpGuide selfHelpGuide;

	@OneToMany(mappedBy = "selfHelpGuideQuestion")
	private Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses = new HashSet<SelfHelpGuideQuestionResponse>(
			0);

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
	public SelfHelpGuideQuestion(final UUID id) {
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
	public SelfHelpGuideQuestion(final UUID id, final String name) {
		super(id, name);
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

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(final Challenge challenge) {
		this.challenge = challenge;
	}

	public SelfHelpGuide getSelfHelpGuide() {
		return selfHelpGuide;
	}

	public void setSelfHelpGuide(final SelfHelpGuide selfHelpGuide) {
		this.selfHelpGuide = selfHelpGuide;
	}

	public Set<SelfHelpGuideQuestionResponse> getSelfHelpGuideQuestionResponses() {
		return selfHelpGuideQuestionResponses;
	}

	public void setSelfHelpGuideQuestionResponses(
			final Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses) {
		this.selfHelpGuideQuestionResponses = selfHelpGuideQuestionResponses;
	}

	@Override
	protected int hashPrime() {
		return 137;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:20 PM
		int result = hashPrime() * super.hashCode();

		result *= hashField("questionNumber", questionNumber);
		result *= critical ? 3 : 5;
		result *= mandatory ? 7 : 11;
		result *= hashField("challenge", challenge);
		result *= hashField("selfHelpGuide", selfHelpGuide);

		// collections are not included here

		return result;
	}
}