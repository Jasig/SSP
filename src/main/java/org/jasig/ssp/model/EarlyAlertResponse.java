package org.jasig.ssp.model;

import java.io.Serializable;
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

import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;

/**
 * EarlyAlertResponse
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EarlyAlertResponse extends Auditable implements Serializable {

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

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "early_alert_response_early_alert_outreach",
			joinColumns = @JoinColumn(name = "early_alert_response_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_outreach_id"))
	private Set<EarlyAlertOutreach> earlyAlertOutreachIds;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "early_alert_response_early_alert_outcome",
			joinColumns = @JoinColumn(name = "early_alert_response_id"),
			inverseJoinColumns = @JoinColumn(name = "early_alert_outcome_id"))
	private Set<EarlyAlertOutcome> earlyAlertOutcomeIds;

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
	 * @return the earlyAlertOutcomeIds
	 */
	public Set<EarlyAlertOutcome> getEarlyAlertOutcomeIds() {
		return earlyAlertOutcomeIds;
	}

	/**
	 * @param earlyAlertOutcomeIds
	 *            the earlyAlertOutcomeIds to set
	 */
	public void setEarlyAlertOutcomeIds(
			final Set<EarlyAlertOutcome> earlyAlertOutcomeIds) {
		this.earlyAlertOutcomeIds = earlyAlertOutcomeIds;
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
		result *= earlyAlertOutcomeOtherDescription == null ? "earlyAlertOutcomeOtherDescription"
				.hashCode()
				: earlyAlertOutcomeOtherDescription
						.hashCode();
		result *= comment == null ? "comment".hashCode() : comment.hashCode();
		result *= earlyAlert == null || earlyAlert.getId() == null ? "earlyAlert"
				.hashCode()
				: earlyAlert.getId().hashCode();
		result *= earlyAlertOutreachIds == null ? "earlyAlertOutreachIds"
				.hashCode() : earlyAlertOutreachIds.hashCode();
		result *= earlyAlertOutcomeIds == null ? "earlyAlertOutcomeIds"
				.hashCode() : earlyAlertOutcomeIds.hashCode();

		return result;
	}
}
