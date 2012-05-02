package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;

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
