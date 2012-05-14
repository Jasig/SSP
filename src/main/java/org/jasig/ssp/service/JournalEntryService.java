package org.jasig.ssp.service;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface JournalEntryService extends PersonAssocService<JournalEntry> {

	/**
	 * Get all journal entries for the Person
	 */
	@Override
	PagingWrapper<JournalEntry> getAllForPerson(Person person,
			SortingAndPaging sAndP);
}
