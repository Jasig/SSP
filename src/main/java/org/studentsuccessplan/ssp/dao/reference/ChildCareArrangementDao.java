package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;

/**
 * Data access class for the ChildCareArrangement reference entity.
 */
@Repository
public class ChildCareArrangementDao extends
		ReferenceAuditableCrudDao<ChildCareArrangement>
		implements AuditableCrudDao<ChildCareArrangement> {

	public ChildCareArrangementDao() {
		super(ChildCareArrangement.class);
	}
}
