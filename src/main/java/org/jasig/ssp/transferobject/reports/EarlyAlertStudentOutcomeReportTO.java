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

import java.util.UUID;


/**
 * AddressLabelSearch transfer object
 */
public class EarlyAlertStudentOutcomeReportTO extends EarlyAlertStudentReportTO  {
	
	public static final UUID STUDENT_RESPONDED = UUID.fromString("12a58804-45dc-40f2-b2f5-d7e4403acee1");
	public static final UUID WAITING_FOR_RESPONSE = UUID.fromString("7148606f-9034-4538-8fc2-c852a5c912ee");
	public static final UUID STUDENT_DID_NOT_RESPOND = UUID.fromString("9a98ff78-92af-4681-8111-adb3300cbe1c");
	public static final UUID NOT_AN_EA_CLASS = UUID.fromString("14e390d5-2371-48b4-a9de-2d35bc18e868");
	public static final UUID DUPLICATE_EA_NOTICE = UUID.fromString("14e390d5-2371-48b4-a9de-2d35bc18e868");
	private static final long serialVersionUID = 3118831549819428989L;

	private Long totalAllReponses;
	
	private Long studentResponded;
	
	private Long waitingForResponse;
	
	private Long notEAClass;
	
	private Long duplicateEANotice;
	
	private Long studentDidNotRespond;

	public EarlyAlertStudentOutcomeReportTO()
	{
		
	}
	
	
	public Long getTotalAllReponses() {
		return totalAllReponses;
	}



	public void setTotalAllReponses(Long totalAllReponses) {
		this.totalAllReponses = totalAllReponses;
	}



	public Long getStudentResponded() {
		return studentResponded;
	}



	public void setStudentResponded(Long studentResponded) {
		this.studentResponded = studentResponded;
	}



	public Long getWaitingForResponse() {
		return waitingForResponse;
	}



	public void setWaitingForResponse(Long waitingForResponse) {
		this.waitingForResponse = waitingForResponse;
	}



	public Long getNotEAClass() {
		return notEAClass;
	}



	public void setNotEAClass(Long notEAClass) {
		this.notEAClass = notEAClass;
	}



	public Long getDuplicateEANotice() {
		return duplicateEANotice;
	}



	public void setDuplicateEANotice(Long duplicateEANotice) {
		this.duplicateEANotice = duplicateEANotice;
	}



	public Long getStudentDidNotRespond() {
		return studentDidNotRespond;
	}



	public void setStudentDidNotRespond(Long studentDidNotRespond) {
		this.studentDidNotRespond = studentDidNotRespond;
	}



	@Override
	public boolean equals(Object obj){
		if (!(BaseStudentReportTO.class.isInstance(obj))
				|| !(getClass().equals(obj.getClass()))) {
			return false;
		}
		return ((BaseStudentReportTO)obj).getId().equals(getId());
	}
	
	
	protected int hashPrime() {
		return 19;
	}
	
	@Override
	public int hashCode()
	{
		int result = hashPrime();

		result = super.hashCode() * result;
		

		return result;
	}
	
}