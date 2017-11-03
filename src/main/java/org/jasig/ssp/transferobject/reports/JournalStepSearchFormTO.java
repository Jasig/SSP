/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.reports;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JournalStepSearchFormTO extends PersonSearchFormTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2006828374589398077L;
	
	private List<UUID> journalStepDetailIds;

	private List<UUID> journalSourceIds;
	
	private Boolean hasStepDetails;
	
	private Date journalCreateDateFrom;
	
	private Date journalCreateDateTo;

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

	public Date getJournalCreateDateFrom() {
		return journalCreateDateFrom == null ? null : new Date(
				journalCreateDateFrom.getTime());
	}

	public void setJournalCreateDateFrom(Date journalCreateDateFrom) {
		this.journalCreateDateFrom = journalCreateDateFrom == null ? null : new Date(
				journalCreateDateFrom.getTime());
	}

	public Date getJournalCreateDateTo() {
		return journalCreateDateTo == null ? null : new Date(
				journalCreateDateTo.getTime());
	}

	public void setJournalCreateDateTo(Date journalCreateDateTo) {
		this.journalCreateDateTo = journalCreateDateTo == null ? null : new Date(
				journalCreateDateTo.getTime());
	}

	public List<UUID> getJournalSourceIds() {
		return journalSourceIds;
	}

	public void setJournalSourceIds(List<UUID> journalSourceIds) {
		this.journalSourceIds = journalSourceIds;
	}
}
