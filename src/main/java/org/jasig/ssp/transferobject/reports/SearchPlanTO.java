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

import java.util.Date;
import java.util.List;

import org.jasig.ssp.model.external.Term;

public class SearchPlanTO {
	
	private String subjectAbbreviation;
	private String number;
	private String planStatus;
	private List<Term> terms;
	private Date dateFrom;
	private Date dateTo;
	
	
	/**
	 * @param subjectAbbreviation
	 * @param number
	 * @param terms
	 * @param dateFrom
	 * @param dateTo
	 */
	public SearchPlanTO(String subjectAbbreviation, String number, String planStatus, List<Term> terms,
			Date dateFrom, Date dateTo) {
		super();
		this.subjectAbbreviation = subjectAbbreviation;
		this.number = number;
		this.planStatus = planStatus;
		this.terms = terms;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	
	/**
	 * @return the planStatus
	 */
	public String getPlanStatus() {
		return planStatus;
	}

	/**
	 * @param planStatus the planStatus to set
	 */
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	/**
	 * @return the subjectAbbreviation
	 */
	public String getSubjectAbbreviation() {
		return subjectAbbreviation;
	}
	/**
	 * @param subjectAbbreviation the subjectAbbreviation to set
	 */
	public void setSubjectAbbreviation(String subjectAbbreviation) {
		this.subjectAbbreviation = subjectAbbreviation;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the terms
	 */
	public List<Term> getTerms() {
		return terms;
	}
	/**
	 * @param terms the terms to set
	 */
	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
	/**
	 * @return the dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}
	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	/**
	 * @return the dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}
	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	

}
