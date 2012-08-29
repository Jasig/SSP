package org.jasig.ssp.service;

import java.util.Date;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;

/**
 * JournalEntry service
 */
public interface JournalEntryService
		extends RestrictedPersonAssocAuditableService<JournalEntry> {

	public Long getCountForCoach(Person coach, Date createDateFrom, Date createDateTo);

	public Long getStudentCountForCoach(Person coach, Date createDateFrom, Date createDateTo);  
}