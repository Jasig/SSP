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
package org.jasig.ssp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.external.TermService;

/*
 * Use this class to determine Date Range Given dates or termcode
 */
public class DateTerm {
	final SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
	Term term;
	Date startDate;
	Date endDate;
	
	
	public DateTerm(Date createDateFrom, Date createDateTo, String termCode, TermService termService) 
			throws ObjectNotFoundException{
		startDate = createDateFrom;
		endDate = createDateTo;		
		if(termCode != null && termCode.length()> 0) {
			term = termService.getByCode(termCode);
			startDate = term.getStartDate();
			endDate = term.getEndDate();
		}
	}

	public String getTermName(){
		if(term != null)
			return term.getName();
		return "";
	}
	
	public String getTermCode(){
		if(term != null)
			return term.getCode();
		return "";
	}

	public Term getTerm() {
		return term;
	}


	public void setTerm(Term term) {
		this.term = term;
	}


	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public String startDateString(){
		if(startDate != null)
			return sdf.format(startDate);
		return "";
	}

	public String endDateString(){
		if(endDate != null)
			return sdf.format(endDate);
		return "";
	}
	
}
