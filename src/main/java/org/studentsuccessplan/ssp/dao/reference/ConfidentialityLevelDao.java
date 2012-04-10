package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;

/**
 * Data access class for the ConfidentialityLevel reference entity.
 */
@Repository
public class ConfidentialityLevelDao extends
		ReferenceAuditableCrudDao<ConfidentialityLevel>
		implements AuditableCrudDao<ConfidentialityLevel> {

	public ConfidentialityLevelDao() {
		super(ConfidentialityLevel.class);
	}
}
