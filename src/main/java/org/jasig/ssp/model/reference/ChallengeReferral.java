package org.jasig.ssp.model.reference;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;
import org.jasig.ssp.model.Auditable;

/**
 * ChallengeReferral reference object.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ChallengeReferral
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 5543482960025703474L;

	/**
	 * Public Description
	 * 
	 * Optional, null allowed, max length 64000 characters.
	 */
	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String publicDescription;

	@Column(nullable = false)
	private Boolean showInSelfHelpGuide;

	@Column(nullable = false)
	private Boolean showInStudentIntake;

	@OneToMany(mappedBy = "challengeReferral")
	@Where(clause = "object_status <> 3")
	private Set<ChallengeChallengeReferral> challengeChallengeReferrals =
			new HashSet<ChallengeChallengeReferral>(0);

	/**
	 * Constructor
	 */
	public ChallengeReferral() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identifier; required
	 */

	public ChallengeReferral(final UUID id) {
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

	public ChallengeReferral(final UUID id, final String name) {
		super(id, name);
	}

	public String getPublicDescription() {
		return publicDescription;
	}

	public void setPublicDescription(final String publicDescription) {
		this.publicDescription = publicDescription;
	}

	public Set<ChallengeChallengeReferral> getChallengeChallengeReferrals() {
		return challengeChallengeReferrals;
	}

	public void setChallengeChallengeReferrals(
			final Set<ChallengeChallengeReferral> challengeChallengeReferrals) {
		this.challengeChallengeReferrals = challengeChallengeReferrals;
	}

	public Boolean isShowInSelfHelpGuide() {
		return showInSelfHelpGuide;
	}

	public void setShowInSelfHelpGuide(final Boolean showInSelfHelpGuide) {
		this.showInSelfHelpGuide = showInSelfHelpGuide;
	}

	/**
	 * @return the showInStudentIntake
	 */
	public Boolean isShowInStudentIntake() {
		return showInStudentIntake;
	}

	/**
	 * @param showInStudentIntake
	 *            the showInStudentIntake to set, can be null
	 */
	public void setShowInStudentIntake(final Boolean showInStudentIntake) {
		this.showInStudentIntake = showInStudentIntake;
	}

	@Override
	protected int hashPrime() {
		return 61;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime() * super.hashCode();

		result *= hashField("publicDescription", publicDescription);
		result *= showInSelfHelpGuide == null && showInSelfHelpGuide ? 5 : 11;
		result *= showInStudentIntake == null && showInStudentIntake ? 3 : 17;

		// collections are not included here

		return result;
	}
}