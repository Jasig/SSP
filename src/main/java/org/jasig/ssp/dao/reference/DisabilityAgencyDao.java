package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.DisabilityAgency;

/**
 * Data access class for the DisabilityAgency reference entity.
 */
@Repository
public class DisabilityAgencyDao extends AbstractReferenceAuditableCrudDao<DisabilityAgency>
		implements AuditableCrudDao<DisabilityAgency> {

	public DisabilityAgencyDao() {
		super(DisabilityAgency.class);
	}
}
