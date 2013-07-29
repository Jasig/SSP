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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jasig.ssp.model.external.PlanStatus;
import org.jasig.ssp.model.external.Term;

public class SearchPlanTO {
	
	private String subjectAbbreviation;
	private String formattedCourse;
	private String number;
	private PlanStatus planStatus;
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
	public SearchPlanTO(PlanStatus planStatus, String subjectAbbreviation, String number, String formattedCourse, List<Term> terms,
			Date dateFrom, Date dateTo) {
		super();
		this.subjectAbbreviation = subjectAbbreviation;
		this.number = number;
		this.formattedCourse = formattedCourse;
		this.planStatus = planStatus;
		this.terms = terms;
		if(terms != null && !terms.isEmpty() && terms.size() == 1 && dateFrom == null){
			this.dateFrom = terms.get(0).getStartDate();
		} else {
			this.dateFrom = dateFrom;
		}
		
		if(terms != null && !terms.isEmpty() && terms.size() == 1 && dateTo == null){
			this.dateTo = terms.get(0).getEndDate();
		} else {
			this.dateTo = dateTo;
		}
	}
	
	/**
	 * @return the planStatus
	 */
	public PlanStatus getPlanStatus() {
		return planStatus;
	}

	/**
	 * @param planStatus the planStatus to set
	 */
	public void setPlanStatus(PlanStatus planStatus) {
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
	 * @return the formattedCourse
	 */
	public String getFormattedCourse() {
		return formattedCourse;
	}

	/**
	 * @param formattedCourse the formattedCourse to set
	 */
	public void setFormattedCourse(String formattedCourse) {
		this.formattedCourse = formattedCourse;
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
	 * @return the terms
	 */
	public List<String> getTermCodes() {
		List<String> termCodes = new ArrayList<String>();
		if(terms != null && !terms.isEmpty()){
			for(Term term:terms){
				termCodes.add(term.getCode());
			}
		}
		return termCodes;
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
