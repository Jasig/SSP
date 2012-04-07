package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.SelfHelpGuideGroup;

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
