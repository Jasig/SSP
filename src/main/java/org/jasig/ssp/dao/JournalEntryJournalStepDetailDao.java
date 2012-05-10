package org.jasig.ssp.dao;

import org.jasig.ssp.model.JournalEntryJournalStepDetail;
import org.springframework.stereotype.Repository;

@Repository
public class JournalEntryJournalStepDetailDao
		extends AbstractAuditableCrudDao<JournalEntryJournalStepDetail>
		implements AuditableCrudDao<JournalEntryJournalStepDetail> {

	protected JournalEntryJournalStepDetailDao() {
		super(JournalEntryJournalStepDetail.class);
	}

}
