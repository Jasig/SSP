/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.model.reference.EarlyAlertReferral;

import com.google.common.collect.Sets;

/**
 * Early Alert transfer object
 * 
 * @author jon.adams
 * 
 */
public class EarlyAlertResponseTO
		extends AbstractAuditableTO<EarlyAlertResponse>
		implements TransferObject<EarlyAlertResponse>, Serializable {

	private static final long serialVersionUID = -4281524583857509499L;

	private UUID earlyAlertId;

	private UUID earlyAlertOutcomeId;

	private String earlyAlertOutcomeOtherDescription;

	private String comment;

	private Set<UUID> earlyAlertOutreachIds;

	private Set<UUID> earlyAlertReferralIds;
	
	private boolean closed;

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
				: earlyAlertResponse.getEarlyAlert().getId();

		earlyAlertOutcomeId = earlyAlertResponse.getEarlyAlertOutcome() == null ? null
				: earlyAlertResponse.getEarlyAlertOutcome().getId();

		earlyAlertOutreachIds = Sets.newHashSet();
		earlyAlertReferralIds = Sets.newHashSet();

		for (EarlyAlertOutreach obj : earlyAlertResponse
				.getEarlyAlertOutreachIds()) {
			earlyAlertOutreachIds.add(obj.getId());
		}

		for (EarlyAlertReferral obj : earlyAlertResponse
				.getEarlyAlertReferralIds()) {
			earlyAlertReferralIds.add(obj.getId());
		}
		closed = earlyAlertResponse.getEarlyAlert().getClosedById() != null;
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
	 * @return the earlyAlertOutcomeId
	 */
	public UUID getEarlyAlertOutcomeId() {
		return earlyAlertOutcomeId;
	}

	/**
	 * @param earlyAlertOutcomeId
	 *            the earlyAlertOutcomeId to set
	 */
	public void setEarlyAlertOutcomeId(final UUID earlyAlertOutcomeId) {
		this.earlyAlertOutcomeId = earlyAlertOutcomeId;
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
	 * @return The list of EarlyAlertReferrals
	 */
	public Set<UUID> getEarlyAlertReferralIds() {
		return earlyAlertReferralIds;
	}

	/**
	 * @param earlyAlertReferralIds
	 *            The list of EarlyAlertReferrals to set
	 */
	public void setEarlyAlertReferralIds(
			final Set<UUID> earlyAlertReferralIds) {
		this.earlyAlertReferralIds = earlyAlertReferralIds;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
}
