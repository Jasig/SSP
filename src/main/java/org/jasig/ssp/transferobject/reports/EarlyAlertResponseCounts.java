package org.jasig.ssp.transferobject.reports;

public class EarlyAlertResponseCounts {

	Long totalResponses = 0L;
	Long totalEARespondedTo = 0L;
	Long totalEARespondedToNotClosed = 0L;
	
	public Long getTotalResponses() {
		return totalResponses;
	}
	public void setTotalResponses(Long totalResponses) {
		this.totalResponses = totalResponses;
	}
	public Long getTotalEARespondedTo() {
		return totalEARespondedTo;
	}
	public void setTotalEARespondedTo(Long totalEARespondedTo) {
		this.totalEARespondedTo = totalEARespondedTo;
	}
	public Long getTotalEARespondedToNotClosed() {
		return totalEARespondedToNotClosed;
	}
	public void setTotalEARespondedToNotClosed(Long totalEARespondedToNotClosed) {
		this.totalEARespondedToNotClosed = totalEARespondedToNotClosed;
	}
}
