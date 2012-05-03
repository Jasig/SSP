package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalStepDetail;

/**
 * Data access class for the JournalStepDetail reference entity.
 */
@Repository
public class JournalStepDetailDao extends
		ReferenceAuditableCrudDao<JournalStepDetail>
		implements AuditableCrudDao<JournalStepDetail> {

	public JournalStepDetailDao() {
		super(JournalStepDetail.class);
	}
}
