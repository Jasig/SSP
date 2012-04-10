package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.MaritalStatus;

/**
 * Data access class for the MaritalStatus reference entity.
 */
@Repository
public class MaritalStatusDao extends ReferenceAuditableCrudDao<MaritalStatus>
		implements AuditableCrudDao<MaritalStatus> {

	public MaritalStatusDao() {
		super(MaritalStatus.class);
	}
}
