package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;

import com.google.common.collect.Sets;

/**
 * Early Alert transfer object
 * 
 * @author jon.adams
 * 
 */
public class EarlyAlertResponseTO
		extends AuditableTO<EarlyAlertResponse>
		implements TransferObject<EarlyAlertResponse>, Serializable {

	private static final long serialVersionUID = -4281524583857509499L;

	private UUID earlyAlertId;

	private String earlyAlertOutcomeOtherDescription;

	private String comment;

	private Set<UUID> earlyAlertOutreachIds;

	private Set<UUID> earlyAlertOutcomeIds;

	/**
	 * Empty constructor
	 */
	public EarlyAlertResponseTO() {
		super();
	}

	/**
	 * Construct a transfer object based on the specified model.
	 * 
	 * @param earlyAlertResponse
	 *            Model to copy from
	 */
	public EarlyAlertResponseTO(final EarlyAlertResponse earlyAlertResponse) {
		super();
		from(earlyAlertResponse);
	}

	@Override
	public final void from(final EarlyAlertResponse earlyAlertResponse) {
		super.from(earlyAlertResponse);

		earlyAlertOutcomeOtherDescription = earlyAlertResponse
				.getEarlyAlertOutcomeOtherDescription();
		comment = earlyAlertResponse.getComment();

		earlyAlertId = earlyAlertResponse.getEarlyAlert() == null ? null
				: earlyAlertResponse
						.getEarlyAlert().getId();

		earlyAlertOutreachIds = Sets.newHashSet();
		earlyAlertOutcomeIds = Sets.newHashSet();

		for (EarlyAlertOutreach obj : earlyAlertResponse
				.getEarlyAlertOutreachIds()) {
			earlyAlertOutreachIds.add(obj.getId());
		}

		for (EarlyAlertOutcome obj : earlyAlertResponse
				.getEarlyAlertOutcomeIds()) {
			earlyAlertOutcomeIds.add(obj.getId());
		}
	}

	/**
	 * Convert a collection of models to transfer objects.
	 * 
	 * @param earlyAlertResponses
	 *            Models to copy
	 * @return Transfer object equivalent of the models
	 */
	public static List<EarlyAlertResponseTO> toTOList(
			final Collection<EarlyAlertResponse> earlyAlertResponses) {
		final List<EarlyAlertResponseTO> earlyAlertResponseTOs = new ArrayList<EarlyAlertResponseTO>();
		if ((earlyAlertResponses != null) && !earlyAlertResponses.isEmpty()) {
			for (EarlyAlertResponse earlyAlertResponse : earlyAlertResponses) {
				earlyAlertResponseTOs.add(new EarlyAlertResponseTO( // NOPMD
						earlyAlertResponse));
			}
		}

		return earlyAlertResponseTOs;
	}

	/**
	 * @return the SuggestionOtherDescription
	 */
	public String getEarlyAlertOutcomeOtherDescription() {
		return earlyAlertOutcomeOtherDescription;
	}

	/**
	 * @param earlyAlertOutcomeOtherDescription
	 *            the SuggestionOtherDescription to set
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

	/**
	 * Gets the earlyAlert identifier
	 * 
	 * @return The earlyAlert identifier
	 */
	public UUID getEarlyAlertId() {
		return earlyAlertId;
	}

	/**
	 * EarlyAlert identifier
	 * 
	 * @param earlyAlertId
	 *            EarlyAlert identifier
	 */
	public void setEarlyAlertId(final UUID earlyAlertId) {
		this.earlyAlertId = earlyAlertId;
	}

	/**
	 * @return The list of EarlyAlertReasons
	 */
	public Set<UUID> getEarlyAlertOutreachIds() {
		return earlyAlertOutreachIds;
	}

	/**
	 * @param earlyAlertOutreachIds
	 *            The list of EarlyAlertReasons to set
	 */
	public void setEarlyAlertOutreachIds(
			final Set<UUID> earlyAlertOutreachIds) {
		this.earlyAlertOutreachIds = earlyAlertOutreachIds;
	}

	/**
	 * @return The list of EarlyAlertOutcomes
	 */
	public Set<UUID> getEarlyAlertOutcomeIds() {
		return earlyAlertOutcomeIds;
	}

	/**
	 * @param earlyAlertOutcomeIds
	 *            The list of EarlyAlertOutcomes to set
	 */
	public void setEarlyAlertOutcomeIds(
			final Set<UUID> earlyAlertOutcomeIds) {
		this.earlyAlertOutcomeIds = earlyAlertOutcomeIds;
	}
}
