package org.jasig.ssp.dao;

import org.jasig.ssp.model.JournalEntry;
import org.springframework.stereotype.Repository;

@Repository
public class JournalEntryDao
		extends AbstractRestrictedPersonAssocAuditableCrudDao<JournalEntry>
		implements RestrictedPersonAssocAuditableDao<JournalEntry> {

	protected JournalEntryDao() {
		super(JournalEntry.class);
	}

}
