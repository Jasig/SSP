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

import org.jasig.ssp.model.Person;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;

public class EarlyAlertStudentReportTO extends BaseStudentReportTO {

	/**
	 * Construct a transfer object from a related model instance
	 * 
	 * @param model
	 *            Initialize instance from the data in this model
	 */
	public EarlyAlertStudentReportTO(final Person model, Long total, Long pending, Long closed) {	
		super(model);
		this.total = total;
		this.closed = closed;
		this.pending = pending;
	}
	
	public EarlyAlertStudentReportTO(EarlyAlertStudentReportTO model)
	{
		super(model);
		this.total = model.getTotal();
		this.closed = model.getClosed();
		this.pending = model.getPending();
	}
	
	public EarlyAlertStudentReportTO()
	{
		
	}
	
	private Long total;
	private Long pending;
	private Long closed;
	private UUID earlyAlertId;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getPending() {
		return pending;
	}

	public void setPending(Long pending) {
		this.pending = pending;
	}

	public Long getClosed() {
		return closed;
	}

	public void setClosed(Long closed) {
		this.closed = closed;
	}
	
	public Long getOpen() {
		return total - closed;
	}
	
	public UUID getEarlyAlertId() {
		return earlyAlertId;
	}

	public void setEarlyAlertId(UUID earlyAlertId) {
		this.earlyAlertId = earlyAlertId;
	}

	@Override
	public boolean equals(Object obj){
		if (!(BaseStudentReportTO.class.isInstance(obj))) {
			return false;
		}
		return ((BaseStudentReportTO)obj).getId().equals(getId());
	}
	
	
	protected int hashPrime() {
		return 853;
	}
	
	@Override
	public int hashCode()
	{
		int result = hashPrime();

		result = super.hashCode() * result;
		

		return result;
	}

}
