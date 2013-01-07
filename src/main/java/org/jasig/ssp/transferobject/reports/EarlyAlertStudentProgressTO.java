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
package org.jasig.ssp.transferobject.reports;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.PersonTO;

/**
 * AddressLabelSearch transfer object
 */
public class EarlyAlertStudentProgressTO extends PersonReportTO {

	/**
	 * Construct a transfer object from a related model instance
	 * 
	 * @param model
	 *            Initialize instance from the data in this model
	 */
	public EarlyAlertStudentProgressTO(final Person model, String registrationStatus, Long earlyAlertsInitialTerm, Long earlyAlertsComparisonTerm) {		
		super(model);
		this.registrationStatus = registrationStatus;
		this.earlyAlertsComparisonTerm = earlyAlertsComparisonTerm;
		this.earlyAlertsInitialTerm = earlyAlertsInitialTerm;
	}
	
	private String registrationStatus;
	private Long earlyAlertsInitialTerm;
	private Long earlyAlertsComparisonTerm;
	
	public String getRegistrationStatus() {
		return registrationStatus;
	}
	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	public Long getEarlyAlertsInitialTerm() {
		return earlyAlertsInitialTerm;
	}
	public void setEarlyAlertsInitialTerm(Long earlyAlertsInitialTerm) {
		this.earlyAlertsInitialTerm = earlyAlertsInitialTerm;
	}
	public Long getEarlyAlertsComparisonTerm() {
		return earlyAlertsComparisonTerm;
	}
	public void setEarlyAlertsComparisonTerm(Long earlyAlertsComparisonTerm) {
		this.earlyAlertsComparisonTerm = earlyAlertsComparisonTerm;
	}

}