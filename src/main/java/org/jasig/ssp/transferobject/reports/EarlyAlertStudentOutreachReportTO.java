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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.EarlyAlertOutreach;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.PersonTO;

/**
 * AddressLabelSearch transfer object
 */
public class EarlyAlertStudentOutreachReportTO
		implements Serializable {

	private static final long serialVersionUID = 3118831549819428989L;

	private CoachPersonLiteTO coach;
	
	private String coachFirstName;
	private String coachLastName;
	private String coachMiddleName;
	private String coachSchoolId;
	private UUID  coachId;
		
	private UUID earlyAlertId;
	
	private List<UUID> earlyAlertIds = new ArrayList<UUID>();

	private String earlyAlertOutreachName;
	
	private Long countPhoneCalls = 0L;
	
	private Long countLetter = 0L;
	
	private Long countText = 0L;
	
	private Long countEmail = 0L;
	
	private Long countInPerson = 0L;
	
	public EarlyAlertStudentOutreachReportTO(Person coach, Long countPhoneCalls, Long countLetter,
			Long countText, Long countEmail, Long countInPerson) {
		super();
		this.coach = new CoachPersonLiteTO(coach);
		this.countPhoneCalls = countPhoneCalls;
		this.countLetter = countLetter;
		this.countText = countText;
		this.countEmail = countEmail;
		this.countInPerson = countInPerson;
	}
	
	public EarlyAlertStudentOutreachReportTO() {
		super();
	}

	public Long getTotalEarlyAlerts() {
		return earlyAlertIds.size() + 0L;
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

	public String getCoachFirstName() {
		return coachFirstName;
	}

	public void setCoachFirstName(String coachFirstName) {
		this.coachFirstName = coachFirstName;
	}

	public String getCoachLastName() {
		return coachLastName;
	}

	public void setCoachLastName(String coachLastName) {
		this.coachLastName = coachLastName;
	}

	public String getCoachMiddleName() {
		return coachMiddleName;
	}

	public void setCoachMiddleName(String coachMiddleName) {
		this.coachMiddleName = coachMiddleName;
	}

	public UUID getCoachId() {
		return coachId;
	}
	
	public String getCoachName() {
		StringBuffer coachName = new StringBuffer(coachFirstName);
		coachName.append(coachMiddleName == null || coachMiddleName.length() == 0 ? "":" " + coachMiddleName);
		coachName.append(coachLastName == null || coachLastName.length() == 0 ? "":" " + coachLastName);
		return coachName.toString();
	}

	public void setCoachId(UUID coachId) {
		this.coachId = coachId;
	}
	
	public UUID getEarlyAlertId() {
		return earlyAlertId;
	}

	public void setEarlyAlertId(UUID earlyAlertId) {
		this.earlyAlertId = earlyAlertId;
		addEarlyAlertIds(earlyAlertId);
	}
	
	public void addEarlyAlertIds(UUID earlyAlertId){
		if(!earlyAlertIds.contains(earlyAlertId))
			earlyAlertIds.add(earlyAlertId);
	}
	
	public void addEarlyAlertIds(List<UUID> earlyAlertIdsToAdd){
		for(UUID earlyAlertIdToAdd:earlyAlertIdsToAdd)
			addEarlyAlertIds(earlyAlertIdToAdd);
	}
	
	public List<UUID> getEarlyAlertIds(){
		return earlyAlertIds;
	}

	public String getEarlyAlertOutreachName() {
		return earlyAlertOutreachName;
	}

	public void setEarlyAlertOutreachName(String earlyAlertOutreachName) {

		if(earlyAlertOutreachName.equals("Phone Call")){
			setCountPhoneCalls(getCountPhoneCalls() + 1L);
		}
		if(earlyAlertOutreachName.equals("Email")){
			setCountEmail(getCountEmail() + 1L);
		}
		
		if(earlyAlertOutreachName.equals("In Person")){
			setCountInPerson(getCountInPerson() + 1L);
		}
		
		if(earlyAlertOutreachName.equals("Letter")){
			setCountLetter(getCountLetter() + 1L);
		}
		
		if(earlyAlertOutreachName.equals("Text")){
			setCountText(getCountText() + 1L);
		}
	}
	
	@Override
	public boolean equals(Object obj){
		if (!(EarlyAlertStudentOutreachReportTO.class.isInstance(obj))
				|| !(getClass().equals(obj.getClass()))) {
			return false;
		}
		return ((EarlyAlertStudentOutreachReportTO)obj).getCoachId().equals(getCoachId());
	}
	
	
	protected int hashPrime() {
		return 57;
	}
	
	public int hashCode()
	{
		int result = hashPrime();

		result *= coachId == null ? "coachId".hashCode() : coachId
				.hashCode();
		result *= StringUtils.isEmpty(coachLastName) ? "coachLastName"
				.hashCode() : coachLastName.hashCode();
		result *= StringUtils.isEmpty(coachFirstName) ? "coachFirstName"
				.hashCode()
				: coachFirstName.hashCode();
		result *= StringUtils.isEmpty(coachMiddleName) ? "coachMiddleName".hashCode()
				: coachMiddleName.hashCode();
		result *= coachSchoolId == null ? "coachSchoolId".hashCode() : coachSchoolId
				.hashCode();

		return result;
	}

	
	public void processDuplicate(EarlyAlertStudentOutreachReportTO reportTO){
			setCountPhoneCalls(getCountPhoneCalls() + reportTO.getCountPhoneCalls());
			setCountEmail(getCountEmail()  + reportTO.getCountEmail());
			setCountInPerson(getCountInPerson()  + reportTO.getCountInPerson());
			setCountLetter(getCountLetter()  + reportTO.getCountLetter());		
			setCountText(getCountText()  + reportTO.getCountText());
			addEarlyAlertIds(reportTO.getEarlyAlertIds());
	}

}