package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;

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
