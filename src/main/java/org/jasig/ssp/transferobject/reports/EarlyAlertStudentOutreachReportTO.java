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

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.PersonTO;

/**
 * AddressLabelSearch transfer object
 */
public class EarlyAlertStudentOutreachReportTO
		implements Serializable {

	private static final long serialVersionUID = 3118831549819428989L;

	private CoachPersonLiteTO coach;

	private Long totalEarlyAlerts;
	
	private Long countPhoneCalls;
	
	private Long countLetter;
	
	private Long countText;
	
	private Long countEmail;
	
	private Long countInPerson;
	
	public EarlyAlertStudentOutreachReportTO(Person coach,
			Long totalEarlyAlerts, Long countPhoneCalls, Long countLetter,
			Long countText, Long countEmail, Long countInPerson) {
		super();
		this.coach = new CoachPersonLiteTO(coach);
		this.totalEarlyAlerts = totalEarlyAlerts;
		this.countPhoneCalls = countPhoneCalls;
		this.countLetter = countLetter;
		this.countText = countText;
		this.countEmail = countEmail;
		this.countInPerson = countInPerson;
	}

	public Long getTotalEarlyAlerts() {
		return totalEarlyAlerts;
	}

	public void setTotalEarlyAlerts(Long totalEarlyAlerts) {
		this.totalEarlyAlerts = totalEarlyAlerts;
	}

	public Long getCountPhoneCalls() {
		return countPhoneCalls;
	}

	public void setCountPhoneCalls(Long countPhoneCalls) {
		this.countPhoneCalls = countPhoneCalls;
	}

	public Long getCountLetter() {
		return countLetter;
	}

	public void setCountLetter(Long countLetter) {
		this.countLetter = countLetter;
	}

	public Long getCountText() {
		return countText;
	}

	public void setCountText(Long countText) {
		this.countText = countText;
	}

	public Long getCountInPerson() {
		return countInPerson;
	}

	public void setCountInPerson(Long countInPerson) {
		this.countInPerson = countInPerson;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CoachPersonLiteTO getCoach() {
		return coach;
	}

	public void setCoach(CoachPersonLiteTO coach) {
		this.coach = coach;
	}

	public Long getCountEmail() {
		return countEmail;
	}

	public void setCountEmail(Long countEmail) {
		this.countEmail = countEmail;
	}

}