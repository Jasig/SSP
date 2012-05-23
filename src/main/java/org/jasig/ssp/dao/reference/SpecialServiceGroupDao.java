package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.SpecialServiceGroup;

/**
 * Data access class for the SpecialServiceGroup reference entity.
 */
@Repository
public class SpecialServiceGroupDao extends AbstractReferenceAuditableCrudDao<SpecialServiceGroup>
		implements AuditableCrudDao<SpecialServiceGroup> {

	public SpecialServiceGroupDao() {
		super(SpecialServiceGroup.class);
	}
}
