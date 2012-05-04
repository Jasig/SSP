package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.JournalStep;

/**
 * Data access class for the JournalStep reference entity.
 */
@Repository
public class JournalStepDao extends
		ReferenceAuditableCrudDao<JournalStep>
		implements AuditableCrudDao<JournalStep> {

	public JournalStepDao() {
		super(JournalStep.class);
	}
}
