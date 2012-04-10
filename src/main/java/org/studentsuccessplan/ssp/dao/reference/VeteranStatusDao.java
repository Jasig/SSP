package org.studentsuccessplan.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.studentsuccessplan.ssp.dao.AuditableCrudDao;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;

/**
 * Data access class for the VeteranStatus reference entity.
 */
@Repository
public class VeteranStatusDao extends ReferenceAuditableCrudDao<VeteranStatus>
		implements AuditableCrudDao<VeteranStatus> {

	public VeteranStatusDao() {
		super(VeteranStatus.class);
	}
}
