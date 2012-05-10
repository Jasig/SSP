package org.jasig.ssp.service;

import java.util.List;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface JournalEntryService extends PersonAssocService<JournalEntry> {

	/**
	 * Get all journal entries for the Person
	 */
	@Override
	List<JournalEntry> getAllForPerson(Person person, SortingAndPaging sAndP);
}
