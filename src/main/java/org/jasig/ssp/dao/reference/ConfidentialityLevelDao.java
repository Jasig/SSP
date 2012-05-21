package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ConfidentialityLevel;

/**
 * Data access class for the ConfidentialityLevel reference entity.
 */
@Repository
public class ConfidentialityLevelDao extends
		AbstractReferenceAuditableCrudDao<ConfidentialityLevel>
		implements AuditableCrudDao<ConfidentialityLevel> {

	public ConfidentialityLevelDao() {
		super(ConfidentialityLevel.class);
	}
}
