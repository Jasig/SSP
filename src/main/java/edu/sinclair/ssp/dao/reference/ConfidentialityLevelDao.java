package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.ConfidentialityLevel;

/**
 * Data access class for the ConfidentialityLevel reference entity.
 */
@Repository
public class ConfidentialityLevelDao extends
		ReferenceAuditableCrudDao<ConfidentialityLevel> implements
AuditableCrudDao<ConfidentialityLevel> {

	public ConfidentialityLevelDao() {
		super(ConfidentialityLevel.class);
	}
}
