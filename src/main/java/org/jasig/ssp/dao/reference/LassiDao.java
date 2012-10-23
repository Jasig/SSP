package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.Lassi;

/**
 * Data access class for the Lassi reference entity.
 */
@Repository
public class LassiDao extends AbstractReferenceAuditableCrudDao<Lassi>
		implements AuditableCrudDao<Lassi> {

	public LassiDao() {
		super(Lassi.class);
	}
}
