package org.jasig.ssp.model.reference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Challenge reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Challenge extends AbstractReference implements Serializable {

	private static final long serialVersionUID = 5610544634433661561L;

	/**
	 * This is the text that will be used in a selfHelpGuideQuestion.
	 */
	@Column(length = 64000)
	private String selfHelpGuideQuestion;

	/**
	 * Just a reference to the questions that reference this Challenge. Think of
	 * as selfHelpQuideChallenges
	 */
	@OneToMany(mappedBy = "challenge")
	private Set<SelfHelpGuideQuestion> selfHelpGuideQuestions = new HashSet<SelfHelpGuideQuestion>(
			0);

	/**
	 * Public description of the challenge
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String selfHelpGuideDescription;

	@Column(nullable = false)
	private boolean showInStudentIntake;

	@Column(nullable = false)
	private boolean showInSelfHelpSearch;

	@OneToMany(mappedBy = "challenge")
	private Set<ChallengeChallengeReferral> challengeChallengeReferrals = new HashSet<ChallengeChallengeReferral>(
			0);

	@Column(length = 255)
	private String tags;

	/**
	 * Education plan for a student.
	 * 
	 * Should be null for non-student users.
	 */
	@Nullable()
	@ManyToOne()
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "confidentiality_level_id", nullable = true)
	private ConfidentialityLevel defaultConfidentialityLevel;

	/**
	 * Constructor
	 */
	public Challenge() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public Challenge(final UUID id) {
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

	public Challenge(final UUID id, final String name) {
		super(id, name);
	}

	/**
	 * Constructor for a Challenge model instance.
	 * 
	 * @param id
	 *            Identifier; required
	 * @param name
	 *            Name; required; max 100 characters
	 * @param description
	 *            Description; max 64000 characters
	 */
	public Challenge(final UUID id, final String name, final String description) {
		super(id, name, description);
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

	public Set<ChallengeChallengeReferral> getChallengeChallengeReferrals() {
		return challengeChallengeReferrals;
	}

	public void setChallengeChallengeReferrals(
			final Set<ChallengeChallengeReferral> challengeChallengeReferrals) {
		this.challengeChallengeReferrals = challengeChallengeReferrals;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	/**
	 * @return the defaultConfidentialityLevel
	 */
	public ConfidentialityLevel getDefaultConfidentialityLevel() {
		return defaultConfidentialityLevel;
	}

	/**
	 * @param defaultConfidentialityLevel
	 *            the defaultConfidentialityLevel to set
	 */
	public void setDefaultConfidentialityLevel(
			final ConfidentialityLevel defaultConfidentialityLevel) {
		this.defaultConfidentialityLevel = defaultConfidentialityLevel;
	}

	@Override
	protected int hashPrime() {
		return 47;
	};
}
