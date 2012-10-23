package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.DisabilityStatus;

/**
 * Data access class for the DisabilityStatus reference entity.
 */
@Repository
public class DisabilityStatusDao extends AbstractReferenceAuditableCrudDao<DisabilityStatus>
		implements AuditableCrudDao<DisabilityStatus> {

	public DisabilityStatusDao() {
		super(DisabilityStatus.class);
	}
}
