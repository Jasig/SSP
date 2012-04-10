package org.studentsuccessplan.mygps.model.transferobject;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

public class SelfHelpGuideResponseTO {

	private UUID id;
	private String summaryText;
	private List<ChallengeTO> challengesIdentified;
	private boolean triggeredEarlyAlert;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	public String getSummaryText() {
		return summaryText;
	}
	public void setSummaryText(String summaryText) {
		this.summaryText = summaryText;
	}
	public List<ChallengeTO> getChallengesIdentified() {
		return challengesIdentified;
	}
	public void setChallengesIdentified(List<ChallengeTO> challengesIdentified) {
		this.challengesIdentified = challengesIdentified;
	}
	public boolean isTriggeredEarlyAlert() {
		return triggeredEarlyAlert;
	}
	public void setTriggeredEarlyAlert(boolean triggeredEarlyAlert) {
		this.triggeredEarlyAlert = triggeredEarlyAlert;
	}
}
