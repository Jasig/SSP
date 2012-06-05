package org.jasig.ssp.dao;

import org.jasig.ssp.model.JournalEntryDetail;
import org.springframework.stereotype.Repository;

@Repository
public class JournalEntryDetailDao
		extends AbstractAuditableCrudDao<JournalEntryDetail>
		implements AuditableCrudDao<JournalEntryDetail> {

	protected JournalEntryDetailDao() {
		super(JournalEntryDetail.class);
	}

}
