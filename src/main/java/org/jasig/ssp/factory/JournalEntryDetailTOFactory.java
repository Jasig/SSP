package org.jasig.ssp.factory;

import java.util.Collection;
import java.util.Set;

import org.jasig.ssp.model.JournalEntry;
import org.jasig.ssp.model.JournalEntryDetail;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.JournalEntryDetailTO;

public interface JournalEntryDetailTOFactory extends
		TOFactory<JournalEntryDetailTO, JournalEntryDetail> {
	/**
	 * Convert transfer objects to equivalent models.
	 * 
	 * @param tObjects
	 *            collection of transfer objects to convert
	 * @param journalEntry
	 *            parent JournalEntry instance
	 * @return Set of equivalent models
	 * @throws ObjectNotFoundException
	 *             If any referenced data could not be found.
	 */
	Set<JournalEntryDetail> asSet(
			final Collection<JournalEntryDetailTO> tObjects,
			final JournalEntry journalEntry) throws ObjectNotFoundException;
}
