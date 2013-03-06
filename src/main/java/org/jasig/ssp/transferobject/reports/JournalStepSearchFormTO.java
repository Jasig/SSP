package org.jasig.ssp.transferobject.reports;

import java.util.List;
import java.util.UUID;

public class JournalStepSearchFormTO extends PersonSearchFormTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2006828374589398077L;
	
	private List<UUID> journalStepDetailIds;
	
	
	private Boolean hasStepDetails;
	
	/**
	 * 
	 */
	public JournalStepSearchFormTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @return the journalSteps
	 */
	public List<UUID> getJournalStepDetailIds() {
		return journalStepDetailIds;
	}
	/**
	 * @param journalStepDetails the journalSteps to set
	 */
	public  void setJournalStepDetailIds(List<UUID> journalStepDetailsId) {
		this.journalStepDetailIds = journalStepDetailsId;
	}


	/**
	 * @return the hasStep
	 */
	public Boolean getGetStepDetails() {
		return hasStepDetails;
	}


	/**
	 * @param hasStep the hasStep to set
	 */
	public void setHasStepDetails(Boolean hasStep) {
		this.hasStepDetails = hasStep;
	}

	

}
