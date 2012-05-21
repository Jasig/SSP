package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.ChildCareArrangement;

/**
 * Data access class for the ChildCareArrangement reference entity.
 */
@Repository
public class ChildCareArrangementDao extends
		AbstractReferenceAuditableCrudDao<ChildCareArrangement>
		implements AuditableCrudDao<ChildCareArrangement> {

	public ChildCareArrangementDao() {
		super(ChildCareArrangement.class);
	}
}
