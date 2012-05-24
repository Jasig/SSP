package org.jasig.ssp.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.model.reference.EarlyAlertReferral;

/**
 * EarlyAlertResponse model
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EarlyAlertResponse
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = 7109630326339706214L;

	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String earlyAlertOutcomeOtherDescription;

	@Column(nullable = true, length = 64000)
	@Size(max = 64000)
	private String comment;

	/**
	 * Associated earlyAlert. Changes to this EarlyAlert are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "early_alert_id", nullable = false)
	private EarlyAlert earlyAlert;

	/**
	 * Associated earlyAlertOutcome. Changes to this EarlyAlertOutcome are not
	 * persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "early_alert_outcome_id", nullable = false)
	private EarlyAlertOutcome earlyAlertOutcome;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "early_alert_response_early_alert_outreach",
			joinColumns = @JoinColumn(name = "early_alert_response_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_outreach_id"))
	private Set<EarlyAlertOutreach> earlyAlertOutreachIds;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "early_alert_response_early_alert_referral",
			joinColumns = @JoinColumn(name = "early_alert_response_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_referral_id"))
	private Set<EarlyAlertReferral> earlyAlertReferralIds;

	/**
	 * @return the earlyAlertOutcomeOtherDescription
	 */
	public String getEarlyAlertOutcomeOtherDescription() {
		return earlyAlertOutcomeOtherDescription;
	}

	/**
	 * @param earlyAlertOutcomeOtherDescription
	 *            the earlyAlertOutcomeOtherDescription to set
	 */
	public void setEarlyAlertOutcomeOtherDescription(
			final String earlyAlertOutcomeOtherDescription) {
		this.earlyAlertOutcomeOtherDescription = earlyAlertOutcomeOtherDescription;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}

	public EarlyAlert getEarlyAlert() {
		return earlyAlert;
	}

	public void setEarlyAlert(final EarlyAlert earlyAlert) {
		this.earlyAlert = earlyAlert;
	}

	/**
	 * @return the earlyAlertOutcome
	 */
	public EarlyAlertOutcome getEarlyAlertOutcome() {
		return earlyAlertOutcome;
	}

	/**
	 * @param earlyAlertOutcome
	 *            the earlyAlertOutcome to set
	 */
	public void setEarlyAlertOutcome(final EarlyAlertOutcome earlyAlertOutcome) {
		this.earlyAlertOutcome = earlyAlertOutcome;
	}

	/**
	 * @return the earlyAlertOutreachIds
	 */
	public Set<EarlyAlertOutreach> getEarlyAlertOutreachIds() {
		return earlyAlertOutreachIds;
	}

	/**
	 * @param earlyAlertOutreachIds
	 *            the earlyAlertOutreachIds to set
	 */
	public void setEarlyAlertOutreachIds(
			final Set<EarlyAlertOutreach> earlyAlertOutreachIds) {
		this.earlyAlertOutreachIds = earlyAlertOutreachIds;
	}

	/**
	 * @return the earlyAlertReferralIds
	 */
	public Set<EarlyAlertReferral> getEarlyAlertReferralIds() {
		return earlyAlertReferralIds;
	}

	/**
	 * @param earlyAlertReferralIds
	 *            the earlyAlertReferralIds to set
	 */
	public void setEarlyAlertReferralIds(
			final Set<EarlyAlertReferral> earlyAlertReferralIds) {
		this.earlyAlertReferralIds = earlyAlertReferralIds;
	}

	@Override
	protected int hashPrime() {
		return 233;
	};

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 3:17 PM
		int result = hashPrime();

		// Auditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// EarlyAlertResponse
		result *= StringUtils.isEmpty(earlyAlertOutcomeOtherDescription) ? "earlyAlertOutcomeOtherDescription"
				.hashCode()
				: earlyAlertOutcomeOtherDescription.hashCode();
		result *= StringUtils.isEmpty(comment) ? "comment".hashCode() : comment
				.hashCode();
		result *= (earlyAlert == null) || (earlyAlert.getId() == null) ? "earlyAlert"
				.hashCode()
				: earlyAlert.getId().hashCode();
		result *= (earlyAlertOutcome == null)
				|| (earlyAlertOutcome.getId() == null) ? "earlyAlertOutcome"
				.hashCode() : earlyAlertOutcome.getId().hashCode();
		result *= earlyAlertOutreachIds == null
				|| earlyAlertOutreachIds.isEmpty() ? "earlyAlertOutreachIds"
				.hashCode() : earlyAlertOutreachIds.hashCode();
		result *= earlyAlertReferralIds == null
				|| earlyAlertReferralIds.isEmpty() ? "earlyAlertReferralIds"
				.hashCode() : earlyAlertReferralIds.hashCode();

		return result;
	}
}
