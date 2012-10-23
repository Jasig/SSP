package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.DisabilityType;

/**
 * Data access class for the DisabilityType reference entity.
 */
@Repository
public class DisabilityTypeDao extends AbstractReferenceAuditableCrudDao<DisabilityType>
		implements AuditableCrudDao<DisabilityType> {

	public DisabilityTypeDao() {
		super(DisabilityType.class);
	}
}
