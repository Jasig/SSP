package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;

/**
 * Data access class for the SelfHelpGuideGroup reference entity.
 */
@Repository
public class SelfHelpGuideGroupDao extends
		ReferenceAuditableCrudDao<SelfHelpGuideGroup>
		implements AuditableCrudDao<SelfHelpGuideGroup> {

	public SelfHelpGuideGroupDao() {
		super(SelfHelpGuideGroup.class);
	}
}
