package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the ProgramStatus reference entity.
 */
@Repository
public class ProgramStatusDao extends
		AbstractReferenceAuditableCrudDao<ProgramStatus>
		implements AuditableCrudDao<ProgramStatus> {

	/**
	 * Constructor that initializes the instance with the specific type for use
	 * by the base class methods.
	 */
	public ProgramStatusDao() {
		super(ProgramStatus.class);
	}
}
