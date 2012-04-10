package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.VeteranStatus;

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
