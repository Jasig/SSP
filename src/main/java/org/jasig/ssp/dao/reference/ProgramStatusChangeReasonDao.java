package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ProgramStatusChangeReason;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the ProgramStatusChangeReason reference entity.
 */
@Repository
public class ProgramStatusChangeReasonDao extends
		AbstractReferenceAuditableCrudDao<ProgramStatusChangeReason>
		implements AuditableCrudDao<ProgramStatusChangeReason> {

	public ProgramStatusChangeReasonDao() {
		super(ProgramStatusChangeReason.class);
	}
}