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
			Term term = termService.getByCode(termCode);
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
