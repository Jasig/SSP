package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.DisabilityAccommodation;

/**
 * Data access class for the DisabilityAccommodation reference entity.
 */
@Repository
public class DisabilityAccommodationDao extends AbstractReferenceAuditableCrudDao<DisabilityAccommodation>
		implements AuditableCrudDao<DisabilityAccommodation> {

	public DisabilityAccommodationDao() {
		super(DisabilityAccommodation.class);
	}
}
