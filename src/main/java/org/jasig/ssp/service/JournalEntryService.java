package org.jasig.ssp.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;

/**
 * JournalEntry service
 */
public interface JournalEntryService
		extends RestrictedPersonAssocAuditableService<JournalEntry> {

	public Long getCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds);

	public Long getStudentCountForCoach(Person coach, Date createDateFrom, Date createDateTo, List<UUID> studentTypeIds);  
}