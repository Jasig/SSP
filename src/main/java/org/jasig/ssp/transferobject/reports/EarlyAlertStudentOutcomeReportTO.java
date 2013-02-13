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

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.PersonTO;

/**
 * AddressLabelSearch transfer object
 */
public class EarlyAlertStudentOutcomeReportTO extends EarlyAlertStudentReportTO  {

	private static final long serialVersionUID = 3118831549819428989L;

	private Long response;
	
	private Long noResponse;
	
	private Long waitingForResponse;
	
	private Long notEALClass;
	
	private Long duplicate;

	public EarlyAlertStudentOutcomeReportTO(EarlyAlertStudentReportTO model,
			Long response, Long noResponse, Long waitingForResponse,
			Long notEALClass, Long duplicate) {
		super(model);
		this.response = response;
		this.noResponse = noResponse;
		this.waitingForResponse = waitingForResponse;
		this.notEALClass = notEALClass;
		this.duplicate = duplicate;
	}

	public Long getResponse() {
		return response;
	}

	public void setResponse(Long response) {
		this.response = response;
	}

	public Long getNoResponse() {
		return noResponse;
	}

	public void setNoResponse(Long noResponse) {
		this.noResponse = noResponse;
	}

	public Long getWaitingForResponse() {
		return waitingForResponse;
	}

	public void setWaitingForResponse(Long waitingForResponse) {
		this.waitingForResponse = waitingForResponse;
	}

	public Long getNotEALClass() {
		return notEALClass;
	}

	public void setNotEALClass(Long notEALClass) {
		this.notEALClass = notEALClass;
	}

	public Long getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(Long duplicate) {
		this.duplicate = duplicate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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