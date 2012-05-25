package org.jasig.ssp.transferobject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.transferobject.reference.ChallengeTO;

import com.google.common.collect.Lists;

public class SelfHelpGuideResponseTO
		extends AbstractAuditableTO<SelfHelpGuideResponse>
		implements TransferObject<SelfHelpGuideResponse> {

	private String summaryText;
	private List<ChallengeTO> challengesIdentified;
	private boolean earlyAlertSent, cancelled, completed;
	private UUID personId, selfHelpGuideId;

	public SelfHelpGuideResponseTO() {
		super();
	}

	public SelfHelpGuideResponseTO(final SelfHelpGuideResponse model) {
		super();
		from(model);
	}

	@Override
	public final void from(final SelfHelpGuideResponse model) {
		super.from(model);

		summaryText = (model.getSelfHelpGuide() == null ? null : model
				.getSelfHelpGuide().getSummaryText());
		earlyAlertSent = model.isEarlyAlertSent();

		if ((model.getPerson() != null)
				&& (model.getPerson().getId() != null)) {
			personId = model.getPerson().getId();
		}
	}

	public static List<SelfHelpGuideResponseTO> toTOList(
			final Collection<SelfHelpGuideResponse> models) {
		final List<SelfHelpGuideResponseTO> tos = Lists.newArrayList();
		for (final SelfHelpGuideResponse model : models) {
			tos.add(new SelfHelpGuideResponseTO(model)); // NOPMD by jon.adams
		}
		return tos;
	}

	public String getSummaryText() {
		return summaryText;
	}

	public void setSummaryText(final String summaryText) {
		this.summaryText = summaryText;
	}

	public List<ChallengeTO> getChallengesIdentified() {
		return challengesIdentified;
	}

	public void setChallengesIdentified(
			final List<ChallengeTO> challengesIdentified) {
		this.challengesIdentified = challengesIdentified;
	}

	public boolean isTriggeredEarlyAlert() {
		return earlyAlertSent;
	}

	public void setTriggeredEarlyAlert(final boolean triggeredEarlyAlert) {
		earlyAlertSent = triggeredEarlyAlert;
	}

	public boolean isEarlyAlertSent() {
		return earlyAlertSent;
	}

	public void setEarlyAlertSent(final boolean earlyAlertSent) {
		this.earlyAlertSent = earlyAlertSent;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(final boolean completed) {
		this.completed = completed;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public UUID getSelfHelpGuideId() {
		return selfHelpGuideId;
	}

	public void setSelfHelpGuideId(final UUID selfHelpGuideId) {
		this.selfHelpGuideId = selfHelpGuideId;
	}
}