package org.jasig.ssp.service;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;

/**
 * JournalEntry service
 */
public interface JournalEntryService
		extends RestrictedPersonAssocAuditableService<JournalEntry> {

	public Long getCountForCoach(Person currPerson);

	public Long getStudentCountForCoach(Person currPerson);  
}